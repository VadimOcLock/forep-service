package ru.fortum.forep.calculator.models

import org.apache.poi.ss.usermodel.Workbook
import ru.fortum.forep.calculator.models.items.CalculationResultItem

class CalculationResult(var fileName: String?,
                        var workbook: Workbook?,
                        var calculationResults: List<ru.fortum.forep.calculator.models.items.CalculationResultItem>?)