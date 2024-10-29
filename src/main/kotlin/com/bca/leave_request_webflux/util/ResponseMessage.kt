package com.bca.leave_request_webflux.util

data class ResponseMessage(
	val status: Int,
	val message: String,
	val data: Any?
)
