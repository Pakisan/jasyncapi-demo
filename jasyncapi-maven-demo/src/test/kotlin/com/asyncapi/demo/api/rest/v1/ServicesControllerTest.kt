package com.asyncapi.demo.api.rest.v1

import com.asyncapi.demo.AsyncAPIDemoApplication
import com.asyncapi.demo.api.rest.v1.dto.Response
import com.asyncapi.demo.domains.services.model.Service
import com.asyncapi.demo.domains.services.service.ServicesService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@WebAppConfiguration
@SpringBootTest(classes = [AsyncAPIDemoApplication::class])
class ServicesControllerTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext
    @Autowired
    private lateinit var servicesService: ServicesService
    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val mockMvc: MockMvc by lazy {
        MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun `get services`() {
        val expectedResponse = Response(200, servicesService.list())

        mockMvc.get(ServicesController.endpoint)
            .andDo { print() }
            .andExpect {
                this.status { HttpStatus.OK }
                this.content { contentType(MediaType.APPLICATION_JSON_UTF8) }
                this.content { json(objectMapper.writeValueAsString(expectedResponse), true) }
            }
    }

    @Test
    fun `get existed service`() {
        val expectedResponse = Response(200, servicesService.get("redis"))

        mockMvc.get("${ServicesController.endpoint}/redis")
            .andDo { print() }
            .andExpect {
                this.status { HttpStatus.OK }
                this.content { contentType(MediaType.APPLICATION_JSON_UTF8) }
                this.content { json(objectMapper.writeValueAsString(expectedResponse), true) }
            }
    }

    @Test
    fun `get non existent service`() {
        val expectedResponse = Response(404, null)

        mockMvc.get("${ServicesController.endpoint}/redis-2")
            .andDo { print() }
            .andExpect {
                this.status { HttpStatus.NOT_FOUND }
                this.content { contentType(MediaType.APPLICATION_JSON_UTF8) }
                this.content { json(objectMapper.writeValueAsString(expectedResponse), true) }
            }
    }

}
