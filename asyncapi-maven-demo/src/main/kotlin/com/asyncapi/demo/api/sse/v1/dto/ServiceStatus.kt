package com.asyncapi.demo.api.sse.v1.dto

import java.time.ZonedDateTime

data class ServiceStatus(
    val name: String,
    val cpuUsage: Double,
    val memUsage: Double,
    val status: Status,
    val time: ZonedDateTime
)
