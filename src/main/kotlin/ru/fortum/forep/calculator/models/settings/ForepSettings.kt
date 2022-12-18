package ru.fortum.forep.calculator.models.settings

import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

import ru.fortum.forep.calculator.models.settings.CommonSettings

class ForepSettings(val common: CommonSettings = CommonSettings(
    "C:\\Users\\Vadim\\IdeaProjects\\kotlin\\Data",
    "C:\\Users\\Vadim\\IdeaProjects\\kotlin\\ftp_csv",
    "C:\\Users\\Vadim\\IdeaProjects\\kotlin\\output",
    "xlsx"
))
{
    val templatesSettings: List<TemplateSettings> = getTemplateSettings()

    companion object {
        fun getTemplateSettings() : List<TemplateSettings> {
            // 1
            var report01 = TemplateSettings(
                calcType         = 1,
                isCalculated     = false,
                isDebugMode      = true,
                execDates        = listOf( "08.12",),
                templateName     = "1_Отчет по отсутствиям-2.xlsx",
                code             = "Отчет по отсутствиям",
                outputDirectory  = "1_Отчет по отсутствиям-2",
                outputFileName   = "Отчет по отсутствиям.xlsx",
                sourceSheetName  = "Источник данных",
                calculatedSheets = listOf(
                    "форма_Отчет по болезни"
                ),
                sourceFormulaColumn = 5,
                sourceValueColumn   = 11,
                baseBusinessUnit    = 1000,
                businessUnits       = listOf(),
                dataFiles = listOf(
                    ru.fortum.forep.calculator.models.settings.DataFile(
                        file = ru.fortum.forep.calculator.models.FileModel(
                            name = "FOR_QLIK_BUKRS_ATTR_PBW",
                            extension = "csv",
                            "",
                            ""
                        )
                    ),
                    ru.fortum.forep.calculator.models.settings.DataFile(
                        file = ru.fortum.forep.calculator.models.FileModel(
                            name = "FOR_QLIK_R01_PBW",
                            extension = "csv",
                            "",
                            ""
                        )
                    )
                ),
            )
            // 4
            var report04 = TemplateSettings(
                calcType         = 4,
                isCalculated     = true,
                isDebugMode      = true,
                execDates        = listOf("14.12"),
                templateName     = "4T_Форма_П_2_сведения_об_инвестициях_в_нефинансовые_активы_new.xlsx",
                code             = "Форма_П_2_сведения_об_инвестициях_в_нефинансовые_активы",
                outputDirectory  = "4T_Форма_П_2_сведения_об_инвестициях_в_нефинансовые_активы_new",
                outputFileName   = "Форма_П_2_сведения_об_инвестициях_в_нефинансовые_активы_БЕ_%BUSINESS_UNIT%.xlsx",
                sourceSheetName  = "Источники данных",
                calculatedSheets = listOf(
                    "Титульный лист (new)",
                    "Раздел 1 (new)",
                    "Раздел 2 (new)",
                    "Справочно (new)"
                ),
                sourceFormulaColumn = 5,
                sourceValueColumn   = 12,
                baseBusinessUnit    = 1000,
                businessUnits       = listOf(1000, 1100, 1200, 1300, 1400, 1600, 1900, 2100, 2200, 2600, 2900, 5600, 5700, 5800, 7100, 7900, 8100, 8200, 8300, 8400, 9100),
                dataFiles = listOf(
                    ru.fortum.forep.calculator.models.settings.DataFile(
                        file = ru.fortum.forep.calculator.models.FileModel(
                            name = "FOR_QLIK_BUKRS_ATTR_PBW",
                            extension = "csv",
                            "",
                            ""
                        )
                    ),
                    ru.fortum.forep.calculator.models.settings.DataFile(
                        file = ru.fortum.forep.calculator.models.FileModel(
                            name = "FOR_QLIK_R04_PBW",
                            extension = "csv",
                            "",
                            ""
                        )
                    )
                ),
            )
            // 10
            var report101 = TemplateSettings(
                        calcType         = 10,
                        isCalculated     = false,
                        isDebugMode      = true,
                        execDates        = listOf( "03.25",),
                        templateName     = "10_Сведения о доходах и расходах.xlsx",
                        code             = "Сведения о доходах и расходах производителей электрической тепловой энергии",
                        outputDirectory  = "10_Сведения о доходах и расходах производителей электрической тепловой энергии",
                        outputFileName   = "Отчет №1 по ПАО Фортум.xlsx",
                        sourceSheetName  = "Ист.данных прилож1.31, ф.4.31",
                        calculatedSheets = listOf(
                            "прил.1.31 ф. 4.31 стр1",
                            "прил.1.31 ф. 4.31 стр2",
                        ),
                        sourceFormulaColumn = 5,
                        sourceValueColumn   = 11,
                        baseBusinessUnit    = 1000,
                        businessUnits       = listOf(1000,1100,1200,1300,1400,1600,1900,2100,2200,2600,2900,7100,7900,),
                        dataFiles = listOf(
                            ru.fortum.forep.calculator.models.settings.DataFile(
                                file = ru.fortum.forep.calculator.models.FileModel(
                                    name = "FOR_QLIK_BUKRS_ATTR_PBW",
                                    extension = "csv",
                                    "",
                                    ""
                                )
                            ),
                            ru.fortum.forep.calculator.models.settings.DataFile(
                                file = ru.fortum.forep.calculator.models.FileModel(
                                    name = "FOR_QLIK_R10_PBW",
                                    extension = "csv",
                                    "",
                                    ""
                                )
                            )
                        ),
                    )
            var report102 = report101.clone()
            report102.businessUnits = listOf(1100, 1200, 1300, 1400, 1600, 1900,);
            report102.outputFileName = "Отчет №2 Филиал Энергосистема Урал.xlsx";
            //
            var report103 = report101.clone();
            report103.businessUnits =  listOf(2100, 2200, 2600, 2900,);
            report103.outputFileName = "Отчет №3 Филиал Энергосистема Западная сибирь.xlsx";
            //
            var report104 = report101.clone();
            report104.businessUnits =  listOf( 7100, 7900, );
            report104.outputFileName = "Отчет №4 Филиал Альтернативная энергетика.xlsx";
            // 11
            var report11 = TemplateSettings(
                        calcType            = 11,
                        isCalculated        = true,
                        isDebugMode         = true,
                        execDates           = listOf("03.25",),
                        templateName        = "11_Сведения_о_затратах_на_производство.xlsx",
                        code                = "Сведения о затратах на производство",
                        outputDirectory     = "11_Сведения о затратах на производство",
                        outputFileName      = "Сведения о затратах на производство ЭЭ и ТЭ_БЕ_%BUSINESS_UNIT%.xlsx",
                        sourceSheetName     = "ист.данных 1_38",
                        calculatedSheets    = listOf(
                            "Прилож.1.38, Ф.4.38 стр.1",
                            "Прилож.1.38, Ф.4.38 стр. 2",
                            "Форма отч.в Гистэк",
                        ),
                        sourceFormulaColumn = 5,
                        sourceValueColumn   = 11,
                        baseBusinessUnit    = 1000,
                        businessUnits       = listOf(1100,1200,1300,1400,1600,2100,2200,2600,7100,),
                        dataFiles = listOf(
                            ru.fortum.forep.calculator.models.settings.DataFile(
                                file = ru.fortum.forep.calculator.models.FileModel(
                                    name = "FOR_QLIK_BUKRS_ATTR_PBW",
                                    extension = "csv",
                                    "",
                                    ""
                                )
                            ),
                            ru.fortum.forep.calculator.models.settings.DataFile(
                                file = ru.fortum.forep.calculator.models.FileModel(
                                    name = "FOR_QLIK_R11_PBW",
                                    extension = "csv",
                                    "",
                                    ""
                                )
                            )
                        ),
            )

            return listOf(
                // 01
                report01,
                // 04
                report04,
                // 11
                report11
            )
        }
    }
    fun toJson() : String = Json.encodeToString(templatesSettings)
}