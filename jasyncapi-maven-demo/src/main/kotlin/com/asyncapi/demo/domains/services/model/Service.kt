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
        var registeredAt: ZonedDateTime = ZonedDateTime.now(ZoneId.of("Europe/Moscow")),
        @Column(name = "health_check_endpoint", unique = false, nullable = false)
        var healthCheckEndpoint: String = "",
        @Column(name = "is_enabled", unique = false, nullable = false)
        var isEnabled: Boolean = false
) {

        override fun equals(other: Any?): Boolean {
                other ?: return false
                if (other !is Service) {
                        return false
                }

                return if (other.name != this.name) {
                        false
                } else if (other.address != this.address) {
                        false
                } else if (other.registeredAt != this.registeredAt) {
                        false
                } else if (other.healthCheckEndpoint != this.healthCheckEndpoint) {
                        false
                } else other.isEnabled == this.isEnabled
        }

        override fun hashCode(): Int {
                return name.hashCode() +
                        address.hashCode() +
                        registeredAt.hashCode() +
                        healthCheckEndpoint.hashCode() +
                        isEnabled.hashCode()
        }

}