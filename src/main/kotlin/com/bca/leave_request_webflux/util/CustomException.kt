package com.bca.leave_request_webflux.util

class EmployeeNotFoundException(message: String): RuntimeException(message)

class EmployeeAlreadyExistException(message: String): RuntimeException(message)

class DatabaseException(message: String, cause: Throwable) : RuntimeException(message)