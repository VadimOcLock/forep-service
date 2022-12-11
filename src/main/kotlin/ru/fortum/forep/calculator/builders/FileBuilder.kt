package ru.fortum.forep.calculator.builders

import org.apache.poi.ss.usermodel.Workbook
import ru.fortum.forep.calculator.models.CalculationResult
import java.io.*
import java.nio.file.Paths
import kotlin.io.path.pathString

open class FileBuilder {
    companion object {
        fun getClonedStream(stream: ByteArrayInputStream): InputStream {

            stream.mark(0)

            val buff = ByteArray(8000)
            var bytesRead = 0
            val bao = ByteArrayOutputStream()
            while (stream.read(buff).also { bytesRead = it } != -1) {
                bao.write(buff, 0, bytesRead)
            }
            val data = bao.toByteArray()
            val bin = ByteArrayInputStream(data)

            stream.reset()

            return bin
        }
        fun getClonedStream2(stream: FileInputStream): ByteArrayInputStream {

            val buff = ByteArray(8000)
            var bytesRead = 0
            val bao = ByteArrayOutputStream()
            while (stream.read(buff).also { bytesRead = it } != -1) {
                bao.write(buff, 0, bytesRead)
            }
            val data = bao.toByteArray()
            val bin = ByteArrayInputStream(data)

            return bin
        }
        fun save(results: List<CalculationResult>?, directory: String)
        {
            if (results != null) {
                for (result in results) {
                    var filePath = Paths.get(directory, result.fileName)
                    saveWorkbook(result.workbook, filePath.pathString)
                }
            }
        }
        private fun saveWorkbook(workbook: Workbook?, newFilePath: String?)
        {
            FileOutputStream(newFilePath).use { fileOut -> workbook?.write(fileOut) }
        }
    }
}