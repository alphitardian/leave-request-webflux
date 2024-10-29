package com.bca.leave_request_webflux.controller

import com.bca.leave_request_webflux.dto.request.LoginRequestDTO
import com.bca.leave_request_webflux.dto.request.RegisterRequestDTO
import com.bca.leave_request_webflux.service.EmployeeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/employee")
class EmployeeController(private val employeeService: EmployeeService) {

	@PostMapping("/login")
	fun login(@RequestBody loginRequestDTO: LoginRequestDTO) = employeeService.login(loginRequestDTO)

	@PostMapping("/register")
	fun register(@RequestBody registerRequestDTO: RegisterRequestDTO) = employeeService.register(registerRequestDTO)

	@GetMapping
	fun getAllEmployees() = employeeService.getAllEmployees()
}