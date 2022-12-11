package ru.fortum.forep.calculator.models.settings

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
class TemplateSettings(var calcType: Int,
                       var baseBusinessUnit: Int,
                       var code: String,
                       var sourceSheetName: String,
                       var sourceFormulaColumn: Int,
                       var sourceValueColumn: Int,
                       var templateName: String,
                       var outputDirectory: String,
                       var outputFileName: String,
                       var isCalculated: Boolean,
                       var isDebugMode: Boolean,
                       var calculatedSheets: List<String>,
                       var execDates: List<String>,
                       var businessUnits: List<Int>,
                       var dataFiles: List<ru.fortum.forep.calculator.models.settings.DataFile>)
{
    fun clone(): TemplateSettings {

        val json = Json.encodeToString(this)
        return Json.decodeFromString(json)
    }
}