package ru.fortum.forep

import io.ktor.http.*
import io.ktor.client.request.*
import kotlin.test.*
import io.ktor.server.testing.*
import ru.fortum.forep.calculator.models.ObjectNames

import ru.fortum.forep.plugins.configureRouting

class ApplicationTest {
    @Test fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            // assertEquals("Hello World!", bodyAsText())
        }
    }
    @Test fun test() = testApplication {

        val r1 = ru.fortum.forep.calculator.models.ObjectNames.getFileNameWithDate("file name1")
        val r2 = ru.fortum.forep.calculator.models.ObjectNames.getFolderNameWithDate("file name2")
        println(r1); println(r2);
        //


    }
}