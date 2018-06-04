package com.saurabhtotey.pimorse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PimorseApplication

fun main(args: Array<String>) {
    runApplication<PimorseApplication>(*args)
}
