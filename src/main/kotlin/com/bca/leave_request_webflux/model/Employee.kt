package com.bca.leave_request_webflux.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("employee")
data class Employee(
    @Id val employeeId: Long,
    val name: String,
    val roleId: Long
)
