package com.sightseekerstudio.demo.springboot3

import okhttp3.OkHttpClient
import okhttp3.Request
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RestController

@RestController
class RootRestController {

    companion object {
        private val log = LoggerFactory.getLogger(this::class.java)
    }

    val client = OkHttpClient()

    @GetMapping("/")
    fun root(): String {
        val request = Request.Builder().url("http://localhost:8080/message").build()
        client.newCall(request).execute().use {
            return it.body?.string()
                ?: throw RuntimeException("message endpoint returned empty-body")
        }
    }

    @GetMapping("/message")
    fun getMessage(@RequestHeader headers:Map<String, String>): MessageResponse {
        log.info(headers.toString())
        return MessageResponse("Hello World !")
    }
    data class MessageResponse (val message:String)
}
