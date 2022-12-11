package ru.fortum.forep.controllers

import ru.fortum.forep.calculator.builders.FileBuilder
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths

class ElmaClient(templateLink: String,
                 private val _templateLink: String = templateLink) {
    // region api
    fun getTemplate(templateFile: String): ByteArrayInputStream
    {
        var path = Paths.get(_templateLink, templateFile);
        val file = File(path.toUri());
        val fileStream = FileInputStream(file);

        var d = FileBuilder.getClonedStream2(fileStream)

        return  d;
    }
    // endregion
}