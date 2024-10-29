package com.bca.leave_request_webflux.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table

@Table("employee")
data class Employee(
    @Id
    @Column("id")
    val id: Long?,

    @Column
    val name: String,

    @Column
    val password: String,

    @Column
    val roleId: Long
)
