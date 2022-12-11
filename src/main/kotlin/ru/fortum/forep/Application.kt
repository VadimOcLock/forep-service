package ru.fortum.forep

import freemarker.cache.ClassTemplateLoader
import io.ktor.server.engine.*
import io.ktor.server.cio.*

import io.ktor.server.application.*
import io.ktor.server.freemarker.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.http.HttpStatusCode
import io.ktor.server.resources.*

import ru.fortum.forep.plugins.configureRouting
import ru.fortum.forep.plugins.configureSerialization

fun main() {

    embeddedServer(CIO, port = 8080, host = "0.0.0.0") {
        install(FreeMarker){
            templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        }
        install(StatusPages) {
            exception<Throwable> { call, cause ->
                call.respondText(text = "500: $cause" , status = HttpStatusCode.InternalServerError)
            }
        }
        install(Resources)
        configureRouting()
        configureSerialization()
    }.start(wait = true)
}
