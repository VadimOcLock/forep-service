package ru.fortum.forep.controllers

import ru.fortum.forep.calculator.models.FtpDataResult
import ru.fortum.forep.calculator.models.settings.CommonSettings
import ru.fortum.forep.calculator.models.settings.DataFile

import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths

class FtpController(private val _commonSettings: CommonSettings) {

// region api
    fun getData(dataFiles: List<DataFile>):List<FtpDataResult>
    {
        var list = mutableListOf<FtpDataResult>()
        for (dataFile in dataFiles)
        {
            var filePath = Paths.get(_commonSettings.dataDirectory,
                "${dataFile.file.name}.${dataFile.file.extension}")

            var file   = File(filePath.toUri())
            val stream = FileInputStream(file)

            list.add(FtpDataResult (dataFile = dataFile, stream = stream))
        }
        return list
    }
// endregion
}