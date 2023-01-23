package ru.fortum.forep.calculator.calculators

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import ru.fortum.forep.calculator.builders.CalculationBuilder
import ru.fortum.forep.calculator.builders.FileBuilder
import ru.fortum.forep.calculator.controllers.FormulaParser
import ru.fortum.forep.calculator.models.settings.BusinessUnitsPersonal
import ru.fortum.forep.calculator.models.settings.ServiceSettings
import java.io.ByteArrayInputStream

class Calculator03: BaseCalculator() {
    // region api
    companion object {
        var reportIndex = 1
    }
    override fun calculate(_calcBuilder: CalculationBuilder,
                           _settings: ru.fortum.forep.calculator.models.settings.ExtendedSettings,
                           templateStream: ByteArrayInputStream
    ): List<ru.fortum.forep.calculator.models.CalculationResult>
    {
        currentDateInt = getCurrentDate()
        calcBuilder    = _calcBuilder
        calcBuilder!!.attr.init(_settings.template.baseBusinessUnit, currentDateInt)
        formulaParser  = FormulaParser(calcBuilder!!)
        settings       = _settings

        var results = mutableListOf<ru.fortum.forep.calculator.models.CalculationResult>()
        for (bu in settings!!.template.businessUnitsPersonals!!)
            results.add(calcItem(templateStream, bu))

        return results
    }
    // endregion

    // region methods
    private fun calcItem(templateStream: ByteArrayInputStream, bu: List<BusinessUnitsPersonal>): ru.fortum.forep.calculator.models.CalculationResult
    {
        calcBuilder?.attr?.init(bu.first().bu, currentDateInt)

        calcBuilder?.fqr03?.init(bu)
        //
        var destination = FileBuilder.getClonedStream(templateStream)
        val workbook = XSSFWorkbook(destination)
        //
        var evaluator = getEvaluator(workbook)
        settings?.template?.let {
            calc(bu.first().bu,
                it.sourceSheetName,
                settings?.template!!.sourceValueColumn,
                workbook, evaluator)
        }
        val fileName = "$reportIndex. " + settings?.template?.outputFileName
            ?.replace(ServiceSettings.BusinessUnitReplace, "${bu.first().bu}")
        val calculationResults = settings?.let {
            getResults(workbook, it.template, evaluator).toList()
        }
        reportIndex++

        return ru.fortum.forep.calculator.models.CalculationResult(fileName, workbook, calculationResults)
    }
    // endregion
}