package com.bca.leave_request_webflux.dto.response

import com.bca.leave_request_webflux.util.LoginStatus

data class LoginResponseDTO(
	val loginStatus: LoginStatus
)
