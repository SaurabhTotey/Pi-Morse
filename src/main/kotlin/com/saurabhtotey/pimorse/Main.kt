package com.saurabhtotey.pimorse

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

//Necessary Spring Boot class
@SpringBootApplication
class Main

/**
 * Entry point of the program
 */
fun main(args: Array<String>) {
    runApplication<Main>(*args)
}
