package com.bca.leave_request_webflux.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("request")
data class Request(
    @Id val requestId: Long,
    val employeeId: Long,
    val leaveId: Long
)
