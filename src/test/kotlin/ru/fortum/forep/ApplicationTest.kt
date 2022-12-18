package ru.fortum.forep

import io.ktor.http.*
import io.ktor.client.request.*
import kotlin.test.*
import io.ktor.server.testing.*
import ru.fortum.forep.calculator.models.ObjectNames

import ru.fortum.forep.plugins.configureRouting
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
            // assertEquals("Hello World!", bodyAsText())
        }
    }

    @Test
    fun test() = testApplication {

        val r1 = ru.fortum.forep.calculator.models.ObjectNames.getFileNameWithDate("file name1")
        val r2 = ru.fortum.forep.calculator.models.ObjectNames.getFolderNameWithDate("file name2")
        println(r1); println(r2);
        //
    }

    @Test
    fun timeRanges() {
        fun getPeriodFromMonth(month: Int): Int {
            return when (month) {
                in 1..3 -> 1
                in 4..6 -> 2
                in 7..9 -> 3
                in 10..12 -> 4
                else -> 0
            }
        }


        fun getFiscFirstMonthPreviousYear(): Int =
            "${(LocalDate.now().format(DateTimeFormatter.ofPattern("yyy")).toInt() - 1)}001".toInt()

        fun getFiscCurrentPeriodPreviousYear(): Int {
            var previousYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyy")).toInt() - 1
            var lastMonthOfPeriod =
                getPeriodFromMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).toInt()) * 3
            return if (lastMonthOfPeriod < 10)
                "${previousYear}00${lastMonthOfPeriod}".toInt()
            else
                "${previousYear}0${lastMonthOfPeriod}".toInt()
        }

        println(getFiscCurrentPeriodPreviousYear())

    }
}