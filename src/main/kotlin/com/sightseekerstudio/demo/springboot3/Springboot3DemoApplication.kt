package com.sightseekerstudio.demo.springboot3

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
class Springboot3DemoApplication

fun main(args: Array<String>) {
	runApplication<Springboot3DemoApplication>(*args)
}

@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(value = ["scheduling.enabled"], havingValue = "true", matchIfMissing = true)
@EnableScheduling
class SchedulingConfiguration
