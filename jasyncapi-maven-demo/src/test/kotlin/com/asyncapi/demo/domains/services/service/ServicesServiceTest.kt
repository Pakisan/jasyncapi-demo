package com.asyncapi.demo.domains.services.service

import com.asyncapi.demo.domains.services.model.Service
import com.asyncapi.demo.domains.services.repository.ServicesRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.ZoneId
import java.time.ZonedDateTime

@Transactional
@SpringBootTest
class ServicesServiceTest {

    @Autowired
    private lateinit var servicesRepository: ServicesRepository
    @Autowired
    private lateinit var servicesService: ServicesService

    @Test
    fun `get existing service`() {
        val expectedService = Service(
                "redis",
                "https://redis.infra.games.shop",
                ZonedDateTime.now(ZoneId.of("Europe/Moscow")),
                "/health",
                true
        )

        servicesRepository.save(expectedService)

        Assertions.assertEquals(expectedService, servicesService.get(expectedService.name))
    }

    @Test
    fun `get when not exists`() {
        Assertions.assertNull(servicesService.get("redis"))
    }

    @Test
    fun list() {
        val expectedService = Service(
                "redis",
                "https://redis.infra.games.shop",
                ZonedDateTime.now(ZoneId.of("Europe/Moscow")),
                "/health",
                true
        )

        servicesRepository.save(expectedService)

        Assertions.assertEquals(listOf(expectedService), servicesService.list())
    }

    @Test
    fun enable() {
        val expectedService = Service(
                "redis",
                "https://redis.infra.games.shop",
                ZonedDateTime.now(ZoneId.of("Europe/Moscow")),
                "/health",
                false
        )
        servicesRepository.save(expectedService)
        expectedService.isEnabled = true

        servicesService.enable(expectedService.name)
        Assertions.assertEquals(expectedService, servicesService.get(expectedService.name))
    }

    @Test
    fun disable() {
        val expectedService = Service(
                "redis",
                "https://redis.infra.games.shop",
                ZonedDateTime.now(ZoneId.of("Europe/Moscow")),
                "/health",
                true
        )
        servicesRepository.save(expectedService)
        expectedService.isEnabled = false

        servicesService.disable(expectedService.name)
        Assertions.assertEquals(expectedService, servicesService.get(expectedService.name))
    }

}