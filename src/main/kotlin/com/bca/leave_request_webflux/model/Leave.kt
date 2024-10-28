package com.bca.leave_request_webflux.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("leave")
data class Leave(
    @Id val leaveId: Long,
    val name: String
)
