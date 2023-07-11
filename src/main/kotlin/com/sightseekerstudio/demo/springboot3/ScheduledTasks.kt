package com.sightseekerstudio.demo.springboot3

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class ScheduledTasks {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    @Scheduled(fixedDelay = 100)
    fun delayedTask() {
        log.info("executing delayed task")
        try {
            TimeUnit.MILLISECONDS.sleep(101)
        } catch (e:InterruptedException) {
            log.warn(e.message, e)
        }
    }
}