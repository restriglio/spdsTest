package com.testspds.api

data class ApiResponse<T>(
    val isSuccessful : Boolean,
    val response : T,
    val error : String
)