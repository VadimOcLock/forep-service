package ru.fortum.forep.calculator.calculators

import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator
import org.apache.poi.xssf.usermodel.XSSFWorkbook

import ru.fortum.forep.calculator.builders.CalculationBuilder
import ru.fortum.forep.calculator.controllers.FormulaParser
import ru.fortum.forep.calculator.mappers.TypeMapper
import ru.fortum.forep.calculator.models.settings.TemplateSettings
import java.io.ByteArrayInputStream

import java.io.FileInputStream
import java.time.OffsetDateTime
import java.time.ZoneOffset

open class BaseCalculator(var currentDateInt: Int = 0,
                          var calcBuilder: CalculationBuilder? = null,
                          var settings: ru.fortum.forep.calculator.models.settings.ExtendedSettings? = null,
                          var formulaParser: FormulaParser? = null) {
    companion object {
        // static
        fun getCalculatorByType(type: Int) : BaseCalculator?
        {
            return when (type) {
                1 -> Calculator01()
                10 -> Calculator10()
                11 -> Calculator11()
                else -> null
            }
        }
        fun setCellValue(cell: Cell, result:Any)
        {
            if (result == null) return;
            //
            if (result is String)
            {
                cell.setCellType(CellType.STRING);
                cell.setCellValue(result as String);
            }
            else if (result is Double)
            {
                cell.setCellType(CellType.NUMERIC);
                cell.setCellValue(result as Double);
            }
        }
        fun getEvaluator(workbook : XSSFWorkbook): XSSFFormulaEvaluator
        {
            var evaluator = XSSFFormulaEvaluator(workbook);

            return evaluator;
        }
    }
    // region api
    open fun calculate(calculationBuilder: CalculationBuilder,
                       settings: ru.fortum.forep.calculator.models.settings.ExtendedSettings,
                       templateStream: ByteArrayInputStream
    ):List<ru.fortum.forep.calculator.models.CalculationResult>?
    {
        return null;
    }
    /// <summary> format: 20171001 </summary>
    protected fun getCurrentDate():Int
    {
        var date = OffsetDateTime.now(ZoneOffset.UTC)

        return date.year * 10000 + date.monthValue * 100 + date.dayOfMonth;
    }
    protected fun isCellCustomFormula(row: Row, sourceValueColumn: Int):Cell?
    {
        var cell = row.firstOrNull() { it.address.column == sourceValueColumn } ?: return null
        //
        if (cell.cellType != CellType.STRING) return null
        if (cell.cellStyle.fillBackgroundColorColor == null) return null

        return cell
    }
    protected fun isCalculatedRow(evaluator: XSSFFormulaEvaluator, row: Row,
                                  addressColumnIndex: Int, valueColumnIndex: Int): ru.fortum.forep.calculator.models.RowDataCells?
    {
        if (row.count() < 2) return null
        //
        var cellsDict = row.associateBy ({ it.address.column }, { it })
        if (!cellsDict.containsKey(addressColumnIndex)) return null
        if (!cellsDict.containsKey(valueColumnIndex))   return null
        //
        var valueCell = cellsDict[valueColumnIndex];
        if (valueCell?.cellStyle?.fillBackgroundColorColor == null) return null;
        //
        var addressCell = cellsDict[addressColumnIndex];
        if (addressCell?.cellType == CellType.FORMULA)
            evaluator.evaluateInCell(addressCell);
        var str = TypeMapper.getStringValue(addressCell);

        var isContainsLetter = str?.toCharArray()?.any { it.isLetter() }
        if (isContainsLetter != null && isContainsLetter) return null;
        //
        var result = ru.fortum.forep.calculator.models.RowDataCells(addressCell = addressCell, valueCell = valueCell);
        if (str != null)
            if (str.contains(".") || str.contains(","))
                return result

        var b = str?.toIntOrNull()

        return if (b == null) null else result
    }
    protected fun calc(bu: Int, sourceSheetName: String, valueColumnIndex: Int,
                       workbook: Workbook, evaluator: XSSFFormulaEvaluator)
    {
        var sheet = workbook.getSheet(sourceSheetName)
        for (i in 1 until sheet.lastRowNum)
            evaluateCell(bu, sheet.getRow(i), valueColumnIndex, evaluator)

        evaluator.evaluateAll()
    }
    protected fun evaluateCell(bu: Int, row: Row, sourceValueColumn: Int, evaluator: XSSFFormulaEvaluator)
    {
        var cell = isCellCustomFormula(row, sourceValueColumn) ?: return;
        //
        var result = formulaParser!!.parse(cell.stringCellValue, bu, currentDateInt)
        if (result == null) return
        //
        setCellValue(cell, result)
        evaluator.notifyUpdateCell(cell)
    }
    protected fun getResults(workbook: Workbook, settings: TemplateSettings,
                             evaluator: XSSFFormulaEvaluator):List<ru.fortum.forep.calculator.models.items.CalculationResultItem>
    {
        var results = mutableListOf<ru.fortum.forep.calculator.models.items.CalculationResultItem>()
        var sheet   = workbook.getSheet(settings.sourceSheetName)
        for (i in 1 until sheet.lastRowNum)
        {
            var result = getResult(i, sheet, evaluator,
                                   settings.sourceFormulaColumn, settings.sourceValueColumn)
            if (result == null) continue;
            //
            results.add(result)
        }

        return results
    }
    // endregion

    // region methods
    private fun getResult(i: Int,
                          sheet: Sheet,
                          evaluator: XSSFFormulaEvaluator,
                          sourceFormulaColumn: Int,
                          sourceValueColumn: Int): ru.fortum.forep.calculator.models.items.CalculationResultItem?
    {
        var row   = sheet.getRow(i)
        var cells = isCalculatedRow(evaluator, row, sourceFormulaColumn, sourceValueColumn)
        if (cells == null) return null

        if (cells.valueCell == null) return null
        //
        if (cells.valueCell!!.cellType == CellType.FORMULA)
        {
            evaluator.evaluateInCell(cells.valueCell)
            evaluator.notifyUpdateCell(cells.valueCell)
        }
        return ru.fortum.forep.calculator.models.items.CalculationResultItem(
            TypeMapper.getStringValue(cells.addressCell),
            TypeMapper.getValue(cells.valueCell!!)
        )
    }
    // endregion
}