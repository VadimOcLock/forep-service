package ru.fortum.forep.calculator.models.settings

class ServiceSettings(var workDirectory: String) {

    companion object {
        // const:
        const val AggregateReportDirectory:String = "reports"
        const val BusinessUnitReplace:String = "%BUSINESS_UNIT%"
    }
}