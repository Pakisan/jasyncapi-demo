package com.asyncapi.demo.domains.services.repository

import com.asyncapi.demo.domains.services.model.Service
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.data.repository.findByIdOrNull
import java.time.ZoneId
import java.time.ZonedDateTime

@DataJpaTest
class ServicesRepositoryTest {

    @Autowired
    private lateinit var servicesRepository: ServicesRepository

    @Test
    fun `save with default properties`() {
        val expectedService = Service(name="redis")
        servicesRepository.save(expectedService)

        val savedService = servicesRepository.findByIdOrNull(expectedService.name)
        Assertions.assertEquals(expectedService, savedService)

        Assertions.assertEquals(savedService!!.address, "")
        Assertions.assertEquals(savedService.healthCheckEndpoint, "")
        Assertions.assertEquals(savedService.registeredAt.zone, ZoneId.of("Europe/Moscow"))
        Assertions.assertEquals(savedService.isEnabled, false)
    }

    @Test
    fun save() {
        val expectedService = Service(
                "redis",
                "https://redis.infra.games.shop",
                ZonedDateTime.now(ZoneId.of("Europe/Moscow")),
                "/health",
                true
        )
        servicesRepository.save(expectedService)

        val savedService = servicesRepository.findByIdOrNull(expectedService.name)
        Assertions.assertEquals(expectedService, savedService)
    }

    @Test
    fun `enable service`() {
        val expectedService = Service(
                "redis",
                "https://redis.infra.games.shop",
                ZonedDateTime.now(ZoneId.of("Europe/Moscow")),
                "/health",
                false
        )
        servicesRepository.save(expectedService)

        expectedService.isEnabled = true
        servicesRepository.enableService(expectedService.name)

        Assertions.assertEquals(expectedService, servicesRepository.findByIdOrNull(expectedService.name))
    }

    @Test
    fun `disable service`() {
        val expectedService = Service(
                "redis",
                "https://redis.infra.games.shop",
                ZonedDateTime.now(ZoneId.of("Europe/Moscow")),
                "/health",
                true
        )
        servicesRepository.save(expectedService)

        expectedService.isEnabled = false
        servicesRepository.disableService(expectedService.name)

        Assertions.assertEquals(expectedService, servicesRepository.findByIdOrNull(expectedService.name))
    }

}