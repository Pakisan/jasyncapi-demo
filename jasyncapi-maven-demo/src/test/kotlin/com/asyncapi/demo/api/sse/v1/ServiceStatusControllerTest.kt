package com.asyncapi.demo.api.sse.v1

import com.asyncapi.demo.AsyncAPIDemoApplication
import com.asyncapi.demo.api.sse.v1.dto.ServiceStatus
import com.asyncapi.demo.domains.services.service.ServicesService
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpMethod
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import java.util.concurrent.CountDownLatch

@Transactional
@SpringBootTest(
        properties = ["server.port=${ServiceStatusControllerTest.port}"],
        classes = [AsyncAPIDemoApplication::class],
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class ServiceStatusControllerTest {

    @Autowired
    private lateinit var serviceService: ServicesService
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `subscribe to services statuses`() {
        val servicesStatuses = mutableListOf<ServiceStatus>()
        val expectedMessagesCount = CountDownLatch(1)

        val restTemplate = RestTemplate()

        GlobalScope.async { restTemplate.execute("http://localhost:$port/api/v1/service/status", HttpMethod.GET, null, { httpResponse ->
            httpResponse.body.bufferedReader(Charsets.UTF_8).forEachLine {
                if (it.isNotEmpty()) {
                    println("response: $it")
                    val json = it.replaceRange(0..4, "")
                    val statuses = objectMapper.readValue(json, object: TypeReference<List<ServiceStatus>>() {})

                    servicesStatuses.addAll(statuses)
                    expectedMessagesCount.countDown()
                }
            }
        }) }

        expectedMessagesCount.await()
        Assertions.assertEquals(serviceService.list().size, servicesStatuses.size)
        Assertions.assertEquals(serviceService.list().map { it.name }, servicesStatuses.map { it.name })
    }

    @Test
    fun `subscribe to service statuses`() {
        val serviceName = "redis"
        val servicesStatuses = mutableListOf<ServiceStatus>()
        val expectedMessagesCount = CountDownLatch(1)

        val restTemplate = RestTemplate()

        GlobalScope.async { restTemplate.execute("http://localhost:$port/api/v1/service/$serviceName/status", HttpMethod.GET, null, { httpResponse ->
            httpResponse.body.bufferedReader(Charsets.UTF_8).forEachLine {
                if (it.isNotEmpty()) {
                    println("response: $it")
                    val json = it.replaceRange(0..4, "")
                    val statuses = objectMapper.readValue(json, object: TypeReference<List<ServiceStatus>>() {})

                    servicesStatuses.addAll(statuses)
                    expectedMessagesCount.countDown()
                }
            }
        }) }

        expectedMessagesCount.await()
        Assertions.assertEquals(1, servicesStatuses.size)
        Assertions.assertEquals(serviceService.get(serviceName)?.name, servicesStatuses.first().name)
    }

    companion object {

        const val port: Int = 8888

    }

}