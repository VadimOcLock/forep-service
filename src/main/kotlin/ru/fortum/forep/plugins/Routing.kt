package ru.fortum.forep.plugins

import io.ktor.http.*
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
import io.ktor.server.application.*
import io.ktor.server.response.*

import ru.fortum.forep.controllers.*
fun Application.configureRouting() {

    val controller = ReportController()

    routing {
        // GET:
        get("/reports/build") {

            controller.execute()

            call.respondText("calculations run..")
        }

        static("/static") {
            resources("static")
        }
    }
}