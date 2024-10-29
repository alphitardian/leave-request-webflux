package com.bca.leave_request_webflux.repository

import com.bca.leave_request_webflux.model.Employee
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
interface EmployeeRepository : ReactiveCrudRepository<Employee, Long> {

	@Query("select e from Employee e where e.name = :name and e.password = :password")
	fun findEmployeeByNameAndPassword(name: String, password: String): Mono<Employee>
}