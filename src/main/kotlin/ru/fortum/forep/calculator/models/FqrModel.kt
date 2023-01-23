package ru.fortum.forep.calculator.models

open class FqrModel01(
    var compCode: Int,
    var fiscPer: Int,
    var fiscVarnt: String,
    var persArea: String,
    var zimonIntRv: Int,
    var zperNum: Int,
    var ztypeKf: String,
    var zpersQty: Double,
    var zwrkHrs: Double
)

open class FqrModel02(
    var compCode: Int,
    var fiscPer: Int,
    var fiscVarnt: String,
    var persArea: String,
    var zimonIntRv: Int,
    var zperNum: Int,
    var ztypeKf: String,
    var zpersQty: Double
)

open class FqrModel03(
    var zperNum: Int,
    var fiscPer: Int,
    var fiscVarnt: String,
    var compCode: Int,
    var persArea: String,
    var ztypeKf: String,
    var amount: Double,
    var zwrkHrs: Double,
    var zpersQty: Double
)

open class FqrModel03OKVED(
    var zacOkved: String,
    var compCode: Int,
    var zqText1: String,
    var zqText2: String,
    var zqText3: String,
    var zqText4: String,
)

open class FqrModel04(
    var compCode: Int,
    var fiscPer: Int,
    var fiscVarnt: String,
    var zperNum: Int,
    var ztypeKf: String,
    var zqKf: Double
)

open class FqrModel04OKVED(
    var compCode: Int,
    var zokPo: Double,
    var zokVed: String
)

data class FqrModel04_02(
    var fiscPer: Int,
    var compCode: Int,
    var vendor: Double,
    var zqText: String,
    var zacInn: Double,
    var amount: Double
)

open class FqrModel10(
    var index: Int,
    var compCode: Int,
    var fiscYear: Int,
    var zrepNum: Int,
    var ztypeKf: String,
    var amount: Double
)

class FqrModel11(
    var fiscPer: Int,
    index: Int,
    compCode: Int,
    fiscYear: Int,
    zrepNum: Int,
    ztypeKf: String,
    amount: Double
) : ru.fortum.forep.calculator.models.FqrModel10(index, compCode, fiscYear, zrepNum, ztypeKf, amount)

