package com.asyncapi.demo.domains.services.service

import com.asyncapi.demo.api.stomp.v1.dto.RegisterServiceMessage
import com.asyncapi.demo.domains.services.model.Service
import com.asyncapi.demo.domains.services.repository.ServicesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull

@org.springframework.stereotype.Service
class ServicesService {

    @Autowired
    private lateinit var servicesRepository: ServicesRepository

    fun get(serviceName: String): Service? {
        return servicesRepository.findByIdOrNull(serviceName)
    }

    fun list(): List<Service> {
        return servicesRepository.findAll() as List<Service>
    }

    fun register(registerServiceMessage: RegisterServiceMessage) {
        servicesRepository.save(Service(
                name = registerServiceMessage.name,
                address = registerServiceMessage.address,
                healthCheckEndpoint = registerServiceMessage.healthCheckEndpoint,
                isEnabled = registerServiceMessage.isEnabled,
        ))
    }

    fun enable(name: String) {
        servicesRepository.enableService(name)
    }

    fun disable(name: String) {
        servicesRepository.disableService(name)
    }

}