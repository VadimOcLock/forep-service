package ru.fortum.forep.calculator.models

import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class ObjectNames {

    companion object {
        // region constants
        const val headerFileName:String = "ftp_headers"
        // endregion

        // region api
        fun getFileNameWithDate(fileName: String, fileExt: String = "txt"): String {

            val odtNow = OffsetDateTime.now(ZoneOffset.UTC)
            val dtFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss-ms")

            return "${fileName}-${odtNow.format(dtFormat)}.${fileExt}"
        }
        fun getFolderNameWithDate(fileName: String): String {

            val odtNow = OffsetDateTime.now(ZoneOffset.UTC)
            val dtFormat: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmssms")

            return "${fileName}-${odtNow.format(dtFormat)}"
        }
        // endregion
    }
}