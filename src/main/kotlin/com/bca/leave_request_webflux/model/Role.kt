package com.bca.leave_request_webflux.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("role")
data class Role(
    @Id val roleId: Long,
    val name: String
)
