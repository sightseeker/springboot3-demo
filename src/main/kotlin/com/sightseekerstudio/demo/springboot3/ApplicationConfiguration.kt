package com.sightseekerstudio.demo.springboot3

import com.fasterxml.jackson.databind.ObjectMapper
import com.kintone.client.util.concurrent.CustomExecutors
import io.micrometer.context.ContextExecutorService
import io.micrometer.tracing.Tracer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Clock
import java.util.concurrent.ExecutorService

@Configuration(proxyBeanMethods = false)
class MicroMeterConfig {
    @Bean
    fun objectMapper(): ObjectMapper {
        return ObjectMapper()
    }

    @Bean
    fun clock(): Clock = Clock.systemUTC()

    @Bean
    fun executorService(): ExecutorService {
        val executorService = CustomExecutors.newCachedThreadPool()
        return ContextExecutorService.wrap(executorService)
    }

    @Bean
    fun spanAspect(tracer: Tracer): ScheduledSpanAspect {
        return ScheduledSpanAspect(tracer)
    }
}
