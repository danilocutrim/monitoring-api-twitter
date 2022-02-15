package br.com.case.monitoring

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {

    val app = SpringApplication(Application::class.java)
    app.run(*args)
}
