package com.asyncapi.demo.api.sse.v1

import com.asyncapi.demo.domains.statuses.service.ServiceStatusService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter
import java.util.concurrent.Executors


@CrossOrigin
@RestController("SSEController.v1")
@RequestMapping("/api/v1/service")
class ServiceStatusController {

    @Autowired
    private lateinit var objectMapper: ObjectMapper
    @Autowired
    private lateinit var serviceStatusService: ServiceStatusService

    @Suppress("Duplicates")
    @GetMapping("/status", MediaType.APPLICATION_STREAM_JSON_VALUE)
    fun serviceStatuses(): SseEmitter {
        val sseEmitter = SseEmitter()
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            while (true) {
                try {
                    val serviceStatuses = serviceStatusService.getStatuses()
                    val data = objectMapper.writeValueAsString(serviceStatuses)

                    sseEmitter.send(data, MediaType.APPLICATION_STREAM_JSON)

                    Thread.sleep(2000)
                } catch(exception: Exception) {
                    sseEmitter.completeWithError(exception)
                }
            }
        }

        return sseEmitter
    }

    @Suppress("Duplicates")
    @GetMapping("/{serviceId}/status", MediaType.TEXT_EVENT_STREAM_VALUE)
    fun serviceStatus(@PathVariable serviceId: String): SseEmitter {
        val sseEmitter = SseEmitter()
        val executor = Executors.newSingleThreadExecutor()
        executor.execute {
            try {
                val serviceStatuses = serviceStatusService.getStatus(serviceId)
                val data = objectMapper.writeValueAsString(listOf(serviceStatuses))

                sseEmitter.send(data, MediaType.APPLICATION_STREAM_JSON)
            } catch(exception: Exception) {
                sseEmitter.completeWithError(exception)
            }
        }

        return sseEmitter
    }

}