package com.asyncapi.demo.api.rest.v1

import com.asyncapi.demo.api.rest.v1.dto.Response
import com.asyncapi.demo.domains.services.model.Service
import com.asyncapi.demo.domains.services.service.ServicesService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = [ServicesController.endpoint])
class ServicesController(
        val servicesService: ServicesService
) {

    @GetMapping(path = ["", "/"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun services(): Response<List<Service>> {
        return Response(200, servicesService.list())
    }

    @GetMapping(path = ["/{serviceName}"], produces = [MediaType.APPLICATION_JSON_UTF8_VALUE])
    fun service(@PathVariable serviceName: String): Response<Service?> {
        servicesService.get(serviceName)?.let {
            return Response(200, it)
        }

        return Response(404, null)
    }

    companion object {

        const val endpoint: String = "/api/rest/v1/services"

    }

}