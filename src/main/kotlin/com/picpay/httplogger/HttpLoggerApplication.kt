package com.picpay.httplogger

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.cloud.netflix.zuul.EnableZuulProxy

@EnableZuulProxy
@SpringBootApplication
class HttpLoggerApplication

fun main(args: Array<String>) {
    SpringApplication.run(HttpLoggerApplication::class.java, *args)
}
