package com.asyncapi.demo.domains.statuses.service

import com.asyncapi.demo.api.sse.v1.dto.ServiceStatus
import com.asyncapi.demo.api.sse.v1.dto.Status
import com.asyncapi.demo.domains.services.repository.ServicesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.math.round
import kotlin.random.Random

@Service
class ServiceStatusService {

    @Autowired
    private lateinit var servicesRepository: ServicesRepository

    fun getStatus(serviceId: String): ServiceStatus {
        val cpuUsage = mockValue(10.00, 17.00)
        val memUsage = mockValue(18.00, 25.00)
        val time = ZonedDateTime.now(ZoneId.of("UTC+3"))

        return ServiceStatus(serviceId, cpuUsage, memUsage, Status.UP, time)
    }

    fun getStatuses(): List<ServiceStatus> {
        val serviceStatuses = mutableListOf<ServiceStatus>()
        for (service in servicesRepository.findAll()) {
            val cpuUsage = mockValue(10.00, 17.00)
            val memUsage = mockValue(10.00, 25.00)
            val time = ZonedDateTime.now(ZoneId.of("UTC+3"))

            serviceStatuses.add(ServiceStatus(service.name, cpuUsage, memUsage, Status.UP, time))
        }

        return serviceStatuses
    }

    private fun mockValue(from: Double, to: Double): Double {
        return Random.nextDouble(from, to)
//        return round(Random.nextDouble(from, to)) / 100
    }

}