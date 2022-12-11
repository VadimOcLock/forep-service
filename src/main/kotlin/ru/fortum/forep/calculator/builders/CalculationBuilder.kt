package ru.fortum.forep.calculator.builders

import ru.fortum.forep.calculator.models.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalculationBuilder(data:Data,
                         val attr:  Attr  = Attr(data),
                         val fqr01: Fqr01 = Fqr01(data),
                         val fqr10: Fqr10 = Fqr10(data),
                         val fqr11: Fqr11 = Fqr11(data))

open class BaseCalculationBuilder(val data: Data, val AmountK: Double = 1000.0)
class Attr(data: Data) : BaseCalculationBuilder(data)
{
    private var _attrModelsByDate: MutableMap<String, AttrModel> = mutableMapOf()
    // region api
    // init:
    fun init(bu:Int, date:Int)
    {
        var code = getCode(bu, date)
        if (_attrModelsByDate.containsKey(code)) return
        //
        var v = data.attrs.filter{ it.compCode == bu }
        var r = v.filter{ it.dateFrom < date && it.dateTo > date }
        var rr = r.first()
        //
        _attrModelsByDate[code] = rr
    }
    // get:
    fun getAttr(bu:Int, date:Int): AttrModel?
    {
        var code = getCode(bu, date)

        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]
    }
    /// <summary>  </summary>
    fun getYear(date:Int, yearDelta:String):String?
    {
        var i = yearDelta.toIntOrNull()
        var year = (date / 10000) - (i ?: 0)
        var yearStr = "${year}".substring(2)

        return yearStr;
    }
    fun getZqtext1(bu:Int, date:Int):String?
    {
        var code = getCode(bu, date);
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zqtext1
    }
    fun getZqtext2(bu:Int, date:Int):String?
    {
        var code = getCode(bu, date);
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zqtext2
    }
    fun getZqtext3(bu:Int, date:Int):String?
    {
        var code = getCode(bu, date);
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zqtext3
    }
    fun getZqtext4(bu:Int, date:Int):String?
    {
        var code = getCode(bu, date);
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zqtext4
    }
    fun getPostalCd(bu:Int, date:Int):String?
    {
        var code = getCode(bu, date);
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.postalCd
    }
    fun getZacOgrn(bu:Int, date:Int):String?
    {
        var code = getCode(bu, date);
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zacOgrn
    }
    fun getZacInn(bu:Int, date:Int):String?
    {
        var code = getCode(bu, date);
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zacInn
    }
    fun getZacOkpg(bu:Int, date:Int):String?
    {
        var code = getCode(bu, date)
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zacOkpg
    }
    fun getZacOktmo(bu:Int, date:Int):String?
    {
        var code = getCode(bu, date);
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zacOktmo;
    }
    // endregion

    // region methods
    fun getCode(bu:Int, date:Int):String { return "${bu}_${date}" }
    // endregion
}
class Fqr01(data: Data,
            private var _models: List<FqrModel01> = mutableListOf()
)
    : BaseCalculationBuilder(data)
{
    // region api
    fun init()
    {
        _models = data.fqrs01
    }

    fun getZpersQty1(ztypeKf: String?, fiscPer: Int?) {

    }
    fun getZpersQty2(ztypeKf: String?, bu: List<Int>) {

    }
    fun getZpersQty3(ztypeKf: String?, fiscPer: Int?, bu: List<Int>) {

    }
    fun getZwrkHrs(ztypeKf: String?, fiscPerMCount: Int) : Double {
        var fiscPer = LocalDate.now().format(DateTimeFormatter.ofPattern("yyy0MM")).toInt() - fiscPerMCount
        var result = _models.filter {
            it.ztypeKf.equals(ztypeKf, true) ||
            it.fiscPer == fiscPer
        }.sumOf { it.zwrkHrs }

        return result
    }

    // endregion
}
class Fqr10(data: Data,
            private var _bus: Map<Int, Int> = mutableMapOf(),
            private var _models: List<FqrModel10> = mutableListOf()
)
    : BaseCalculationBuilder(data)
{
    // region api
    fun init(bu: List<Int>)
    {
        _bus    = bu.associateWith { it }
        _models = data.fqrs10.filter{_bus.containsKey(it.compCode)}
    }
    fun getAmount(ztypeKf:String?):Double?
    {
        var result = _models.filter{it.ztypeKf.equals(ztypeKf, ignoreCase = true)}?.sumOf{it.amount};

        return if (result == null) null else result / AmountK
    }
    // endregion
}
 class Fqr11(data: Data,
             private val _fqrModelsByBu: MutableMap<Int, List<FqrModel11>> = mutableMapOf())
     : BaseCalculationBuilder(data)
{
    // region api
    fun init(bu: Int)
    {
        if (_fqrModelsByBu.containsKey(bu)) return;
        //
        var v = data.fqrs11.filter{it.compCode == bu}
        if (v.isEmpty()) return
        //
        _fqrModelsByBu[bu] = v;
    }
    fun getAmount(bu: Int, ztypeKf : String) : Double?
    {
        if (!_fqrModelsByBu.containsKey(bu)) return null
        //
        var result = _fqrModelsByBu[bu]?.filter{it.ztypeKf.equals(ztypeKf, ignoreCase = true)}?.sumOf{it.amount}

        return if (result == null) null else result / AmountK
    }
// endregion
}




