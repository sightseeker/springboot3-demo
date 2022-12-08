package com.sightseekerstudio.demo.springboot3

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RootRestController {

    @GetMapping("/")
    fun root(): String {
        return "Hello, World !"
    }
}
