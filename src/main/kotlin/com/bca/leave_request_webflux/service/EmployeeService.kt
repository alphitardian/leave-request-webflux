package com.bca.leave_request_webflux.service

import com.bca.leave_request_webflux.dto.request.LoginRequestDTO
import com.bca.leave_request_webflux.dto.request.RegisterRequestDTO
import com.bca.leave_request_webflux.dto.response.LoginResponseDTO
import com.bca.leave_request_webflux.dto.response.RegisterResponseDTO
import com.bca.leave_request_webflux.model.Employee
import com.bca.leave_request_webflux.repository.EmployeeRepository
import com.bca.leave_request_webflux.util.*
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@Service
class EmployeeService(private val employeeRepository: EmployeeRepository) {

	fun login(loginRequestDTO: LoginRequestDTO): Mono<ResponseEntity<ResponseMessage>> {
		return Mono.just(loginRequestDTO)
			.flatMap { request -> this.employeeRepository.findEmployeeByNameAndPassword(request.name, request.password) }
			.switchIfEmpty(Mono.error(EmployeeNotFoundException("Employee not found. Please Register.")))
			.map(this::mapToLoginResponseDTO)
			.map { response -> this.mapToResponseEntity("Login Success", response, HttpStatus.OK) }
			.onErrorResume { exception ->
				when (exception) {
					is EmployeeNotFoundException -> {
						val response = this.mapToLoginResponseDTO(null)
						return@onErrorResume Mono.just(this.mapToResponseEntity("Login Failed", response, HttpStatus.NOT_FOUND))
					}
					else -> Mono.error(RuntimeException("Unknown error."))
				}
			}
	}

	fun register(registerRequestDTO: RegisterRequestDTO): Mono<ResponseEntity<ResponseMessage>> {
		return Mono.just(registerRequestDTO)
			.map { request -> this.mapToEmployee(request) }
			.flatMap(employeeRepository::save)
			.map(this::mapToRegisterResponseDTO)
			.map { response -> this.mapToResponseEntity("Register Success", response, HttpStatus.CREATED) }
			.onErrorResume { exception ->
				when (exception) {
					is EmployeeAlreadyExistException -> {
						val response = this.mapToLoginResponseDTO(null)
						return@onErrorResume Mono.just(this.mapToResponseEntity("Employee Already Exist", response, HttpStatus.OK))
					}
					else -> Mono.error(DatabaseException("Failed to save.", exception))
				}
			}
	}

	fun getAllEmployees() = this.employeeRepository.findAll()

	private fun mapToLoginResponseDTO(employee: Employee?): LoginResponseDTO {
		if (employee != null) {
			return LoginResponseDTO(LoginStatus.SUCCESS)
		}

		return LoginResponseDTO(LoginStatus.NOT_REGISTERED)
	}

	private fun mapToEmployee(registerRequestDTO: RegisterRequestDTO): Employee {
		return Employee(
			id = null,
			name = registerRequestDTO.name,
			password = registerRequestDTO.password,
			roleId = registerRequestDTO.roleId
		)
	}

	private fun mapToRegisterResponseDTO(employee: Employee?): RegisterResponseDTO {
		return RegisterResponseDTO(employee?.name ?: "", employee?.roleId ?: 1)
	}

	private fun mapToResponseEntity(message: String, data: Any?, statusCode: HttpStatusCode): ResponseEntity<ResponseMessage> {
		return ResponseEntity
			.status(statusCode)
			.body(
				ResponseMessage(
					status = statusCode.value(),
					message = message,
					data = data
				)
			)
	}
}