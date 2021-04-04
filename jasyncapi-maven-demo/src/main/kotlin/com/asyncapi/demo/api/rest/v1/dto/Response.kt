package com.asyncapi.demo.api.rest.v1.dto

data class Response<PayloadType>(
        val code: Int,
        val payload: PayloadType
)