package com.asyncapi.demo.domains.services.model

import java.time.ZoneId
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name= "services")
class Service(
        @Id
        @Column(unique = true, nullable = false)
        var name: String = "",
        @Column(unique = false, nullable = false)
        var address: String = "",
        @Column(name = "registered_at", unique = false, nullable = false)
        var registeredAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("UTC+3")),
        @Column(name = "health_check_endpoint", unique = false, nullable = false)
        var healthCheckEndpoint: String = "",
        @Column(name = "is_enabled", unique = false, nullable = false)
        var isEnabled: Boolean = false
)