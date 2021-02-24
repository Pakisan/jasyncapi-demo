package com.asyncapi.demo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class AsyncAPIDemoApplication

fun main(args: Array<String>) {
    runApplication<AsyncAPIDemoApplication>(*args)
}