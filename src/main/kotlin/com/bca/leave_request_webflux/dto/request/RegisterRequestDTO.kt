package com.bca.leave_request_webflux.dto.request

data class RegisterRequestDTO(
	val name: String,
	val password: String,
	val roleId: Long
)