package ru.fortum.forep.controllers

import ru.fortum.forep.calculator.builders.CalculationBuilder
import ru.fortum.forep.calculator.builders.FileBuilder
import ru.fortum.forep.calculator.builders.ReportBuilder
import ru.fortum.forep.calculator.calculators.BaseCalculator
import ru.fortum.forep.calculator.controllers.CsvParser
import ru.fortum.forep.calculator.models.FtpDataResult
import ru.fortum.forep.calculator.models.settings.ExtendedSettings
import ru.fortum.forep.calculator.models.settings.ForepSettings
import ru.fortum.forep.calculator.models.settings.ServiceSettings
import ru.fortum.forep.calculator.models.settings.TemplateSettings
import java.nio.file.Paths
import kotlin.io.path.pathString

class ReportController(private val _reportBuilder: ReportBuilder = ReportBuilder()) {

    // region variables
    private val _serviceSettings: ServiceSettings = ServiceSettings(getWorkDirectory())
    private val _settings: ForepSettings = ForepSettings()
    private val _ftpController: FtpController = FtpController(_settings.common)
    private val _elmaClient: ElmaClient = ElmaClient(_settings.common.templateLink)
    private val _csvParser: CsvParser = CsvParser()
    private val _calculationBuilder: CalculationBuilder = CalculationBuilder(_csvParser.data)
    // endregion

    // region api
    fun execute() {
        try
        {
            // 1
            var mainFolder = _reportBuilder
                .buildAggregateDirectory(_serviceSettings.workDirectory)
            // 2
            _settings.templatesSettings.forEach{
                buildTemplate(mainFolder, it)
            }
        }
        catch (e: Exception)
        {
            println(e.message)
            println("error!")
            //Logger.Error(e.ToString());
        }
    }
    // endregion

    // region methods
    private fun buildTemplate(mainFolder: String, tempSettings: TemplateSettings) {

        if (!tempSettings.isCalculated) return
        // 0 extend settings:
        val extSettings = getExtendedSettings(mainFolder, tempSettings)
        // 1 get data:
        val dataStreams = _ftpController.getData(tempSettings.dataFiles)
        // 2 map data:
        mapData(extSettings, dataStreams)
        // 3 get templates:
        val templateStream = _elmaClient.getTemplate(tempSettings.templateName)
        // 4 get calculator:
        val calculator = BaseCalculator.getCalculatorByType(tempSettings.calcType)
        // 5 calculate:
        val results = calculator?.calculate(_calculationBuilder, extSettings, templateStream)
        // 6 fill:
        ReportBuilder.fillBooks(tempSettings.calculatedSheets, results)
        // 7 save local:
        FileBuilder.save(results, extSettings.templateDirectory)
        // 8 send:

        //_elmaClient.
    }
    private fun getExtendedSettings(mainFolder: String, templateSettings: TemplateSettings): ExtendedSettings
    {
        return ExtendedSettings(templateDirectory = _reportBuilder
                        .buildTemplateDirectory(mainFolder, templateSettings.outputDirectory),
                                template = templateSettings,
        )
    }
    private fun mapData(extSettings: ExtendedSettings, dataStreams: List<FtpDataResult>)
    {
        dataStreams.forEach {
            buildFileSettings(extSettings, it);
            _csvParser.parse(it.dataFile.file.name, it.stream)
        }
    }
    private fun buildFileSettings(extSettings: ExtendedSettings, e: FtpDataResult)
    {
        var fileName = "${e.dataFile.file.name}.${e.dataFile.file.extension}"
        var path0     = Paths.get(extSettings.templateDirectory, fileName)
        var path = path0.pathString.replace(e.dataFile.file.extension, _settings.common.dataFileExt2)

        e.dataFile.file.path         = path;
        e.dataFile.file.newExtension = _settings.common.dataFileExt2;
        extSettings.addDataPathByName(e.dataFile.file);
    }
    // endregion

    // region utils
    private fun getWorkDirectory() : String { return "C:\\Users\\Vadim\\IdeaProjects\\kotlin\\work"; }
    // endregion
}