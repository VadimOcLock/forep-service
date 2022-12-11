package ru.fortum.forep.calculator.builders

import org.apache.poi.ss.usermodel.*
import ru.fortum.forep.calculator.mappers.TypeMapper
import ru.fortum.forep.calculator.models.CalculationResult
import ru.fortum.forep.calculator.models.ObjectNames
import ru.fortum.forep.calculator.models.items.CalculationResultItem
import ru.fortum.forep.calculator.models.settings.ServiceSettings
import java.nio.file.Files

import java.nio.file.Paths
import kotlin.io.path.name
import kotlin.io.path.pathString

class ReportBuilder: FileBuilder() {

    companion object {
        fun fillBooks(calculatedSheets: List<String>, results: List<CalculationResult>?)
        {
            if (results != null) {
                for (result in results)
                    result.workbook?.let { result.calculationResults?.let { it1 -> fillBook(calculatedSheets, it, it1) }}
            }
        }
        fun fillBook(calculatedSheets: List<String>, workbook: Workbook, resultItems: List<CalculationResultItem>)
        {
            var d = resultItems.associateBy { it.address }  /*.Address.Replace(",", ".")*/

            for (sheet in calculatedSheets.mapNotNull { workbook.getSheet(it) }) {
                fillSheet(sheet, d)
            }
        }
        private fun fillSheet(sheet: Sheet, resultItems: Map<String?, CalculationResultItem>)
        {
            for (rowIndex in 0 until sheet.lastRowNum - 1){
                val row = sheet.getRow(rowIndex) ?: continue
                fillRow(row, resultItems)
            }
        }
        private fun fillRow(row: Row, resultItems: Map<String?, CalculationResultItem>)
        {
            if (row == null) { /* // todo: log */ return; }
            if (row.count() < 1) return;
            //
            row.forEach{ fillRowItem(it, resultItems) }
        }
        private fun fillRowItem(cell: Cell, resultItems: Map<String?, CalculationResultItem>)
        {
            //Debug.WriteLine($"адрес:  {cell.Address.FormatAsString()}: {TypeMapper.GetStringValue(cell)}");
            if (cell.cellStyle.fillBackgroundColorColor == null) return;
            //
            var v = TypeMapper.getStringValue(cell)
            if (v.isNullOrEmpty()) return
            // ? воспринимает целые числа из текста в NUMERIC 1 -> 1.0 ?
            if (cell.cellType == CellType.NUMERIC)
                v = "${cell.numericCellValue.toInt()}"
            if (!resultItems.containsKey(v))
            {
                //Debug.WriteLine($"=== адрес не найден в словаре:  {v}"); todo:
                cell.setCellValue("-")
                return
            }
            //
            var v1 = resultItems[v]?.value
            if (v1 == null)
            {
                cell.setCellValue("")
                return
            }
            TypeMapper.setValue(cell, v1)
            cell.cellStyle.fillPattern = FillPatternType.NO_FILL
        }
    }
    // region api
    fun buildAggregateDirectory(workDirectory: String): String
    {
        // 1
        var folderName = ObjectNames.getFolderNameWithDate(ServiceSettings.AggregateReportDirectory);
        // 2
        var folderPath = Paths.get(workDirectory, folderName)
        if (Files.exists(folderPath)) return folderPath.pathString
        // 3
        var directory = Files.createDirectory(folderPath)

        return directory.pathString
    }
    fun buildTemplateDirectory(aggregateDirectory: String, outputDirectory: String): String
    {
        var combinePath = Paths.get(aggregateDirectory, outputDirectory)
        if (Files.exists(combinePath)) return combinePath.pathString
        //
        var directory = Files.createDirectory(combinePath)
        return directory.pathString
    }
    // endregion
}