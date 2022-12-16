package ru.fortum.forep.calculator.calculators

import org.apache.poi.xssf.usermodel.XSSFWorkbook
import ru.fortum.forep.calculator.builders.CalculationBuilder
import ru.fortum.forep.calculator.builders.FileBuilder
import ru.fortum.forep.calculator.controllers.FormulaParser
import java.io.ByteArrayInputStream

class Calculator04: BaseCalculator() {
    // region api
    override fun calculate(_calcBuilder: CalculationBuilder,
                           _settings: ru.fortum.forep.calculator.models.settings.ExtendedSettings,
                           stream: ByteArrayInputStream
    ):List<ru.fortum.forep.calculator.models.CalculationResult>
    {
        currentDateInt = getCurrentDate()
        calcBuilder    = _calcBuilder
        calcBuilder!!.attr.init(_settings.template.baseBusinessUnit, currentDateInt)
        calcBuilder!!.fqr04.init(_settings.template.baseBusinessUnit)
        formulaParser  = FormulaParser(calcBuilder!!)
        settings       = _settings

        return listOf(calcItem(stream))
    }
    // endregion
    // region methods
    private fun calcItem(templateStream: ByteArrayInputStream): ru.fortum.forep.calculator.models.CalculationResult
    {
        var destination = FileBuilder.getClonedStream(templateStream)

        val workbook = XSSFWorkbook(destination)
        //
        var evaluator = getEvaluator(workbook)
        settings?.template?.let {
            calc(it.baseBusinessUnit,
                settings!!.template.sourceSheetName,
                settings!!.template.sourceValueColumn,
                workbook, evaluator)
        }
        val fileName = settings?.template?.outputFileName
        val calculationResults = settings?.let {
            getResults(workbook, it.template, evaluator)
                .toList()
        }
        return ru.fortum.forep.calculator.models.CalculationResult(fileName, workbook, calculationResults)
    }
    // endregion
}