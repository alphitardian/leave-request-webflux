package com.bca.leave_request_webflux.service

import com.bca.leave_request_webflux.dto.request.LoginRequestDTO
import com.bca.leave_request_webflux.dto.request.RegisterRequestDTO
import com.bca.leave_request_webflux.dto.response.LoginResponseDTO
import com.bca.leave_request_webflux.dto.response.RegisterResponseDTO
import com.bca.leave_request_webflux.model.Employee
import com.bca.leave_request_webflux.repository.EmployeeRepository
import com.bca.leave_request_webflux.util.LoginStatus
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.springframework.boot.test.context.SpringBootTest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

@SpringBootTest
class EmployeeServiceTest {

	private val mockRepository = mock(EmployeeRepository::class.java)
	private val mockService = EmployeeService(mockRepository)

	private lateinit var employees: List<Employee>

	@BeforeEach
	fun beforeEachTest() {
		this.employees = mutableListOf(
			Employee(1, "Ardian", "12345", 1),
			Employee(2, "John", "12345", 2),
			Employee(3, "Allan", "12345", 1)
		)
	}

	@Test
	fun `test getAllEmployees return employees`() {
		`when`(mockRepository.findAll())
			.thenReturn(Flux.fromIterable(this.employees))

		// Verify Behaviour
		val result = mockService.getAllEmployees()
		StepVerifier.create(result)
			.expectNext(this.employees[0])
			.expectNext(this.employees[1])
			.expectNext(this.employees[2])
			.verifyComplete()
	}

	@Test
	fun `test registerEmployee save employee`() {
		// Mock Data
		val employee = this.employees[0].copy(id = null)

		`when`(mockRepository.save(employee))
			.thenReturn(Mono.just(employee.copy(id = 1)))

		// Verify Behaviour
		val request = RegisterRequestDTO("Ardian", "12345", 1)
		val result = mockService.register(request)
		StepVerifier.create(result)
			.expectNextMatches { employeeResult ->
				val response = employeeResult.body?.data as RegisterResponseDTO
				response.name == "Ardian" && response.role == 1L
			}
			.verifyComplete()
	}

	@Test
	fun `test loginEmployee return employee`() {
		// Mock Data
		val employee = this.employees[0].copy(id = null)

		`when`(mockRepository.findEmployeeByNameAndPassword(employee.name, employee.password))
			.thenReturn(Mono.just(employee.copy(id = 1)))

		// Verify Behaviour
		val request = LoginRequestDTO("Ardian", "12345")
		val result = mockService.login(request)
		StepVerifier.create(result)
			.expectNextMatches { employeeResult ->
				val response = employeeResult.body?.data as LoginResponseDTO
				response.loginStatus == LoginStatus.SUCCESS
			}
			.verifyComplete()
	}
}