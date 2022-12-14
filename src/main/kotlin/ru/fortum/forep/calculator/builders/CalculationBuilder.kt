package ru.fortum.forep.calculator.builders

import ru.fortum.forep.calculator.models.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CalculationBuilder(
    data: Data,
    val attr: Attr = Attr(data),
    val fqr01: Fqr01 = Fqr01(data),
    val fqr04: Fqr04 = Fqr04(data),
    val fqr10: Fqr10 = Fqr10(data),
    val fqr11: Fqr11 = Fqr11(data)
)

open class BaseCalculationBuilder(val data: Data, val AmountK: Double = 1000.0)
class Attr(data: Data) : BaseCalculationBuilder(data) {
    private var _attrModelsByDate: MutableMap<String, AttrModel> = mutableMapOf()

    // region api
    // init:
    fun init(bu: Int, date: Int) {
        var code = getCode(bu, date)
        if (_attrModelsByDate.containsKey(code)) return
        //
        var v = data.attrs.filter { it.compCode == bu }
        var r = v.filter { it.dateFrom < date && it.dateTo > date }
        var rr = r.first()
        //
        _attrModelsByDate[code] = rr
    }

    // get:
    fun getAttr(bu: Int, date: Int): AttrModel? {
        var code = getCode(bu, date)

        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]
    }

    /// <summary>  </summary>
    fun getYear(date: Int, yearDelta: String): String? {
        var i = yearDelta.toIntOrNull()
        var year = (date / 10000) - (i ?: 0)
        var yearStr = "${year}".substring(2)

        return yearStr;
    }

    fun getZqtext1(bu: Int, date: Int): String? {
        var code = getCode(bu, date)
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zqtext1
    }

    fun getZqtext2(bu: Int, date: Int): String? {
        var code = getCode(bu, date)
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zqtext2
    }

    fun getZqtext3(bu: Int, date: Int): String? {
        var code = getCode(bu, date)
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zqtext3
    }

    fun getZqtext4(bu: Int, date: Int): String? {
        var code = getCode(bu, date)
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zqtext4
    }

    fun getPostalCd(bu: Int, date: Int): String? {
        var code = getCode(bu, date);
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.postalCd
    }

    fun getZacOgrn(bu: Int, date: Int): String? {
        var code = getCode(bu, date);
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zacOgrn
    }

    fun getZacInn(bu: Int, date: Int): String? {
        var code = getCode(bu, date);
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zacInn
    }

    fun getZacOkpg(bu: Int, date: Int): String? {
        var code = getCode(bu, date)
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zacOkpg
    }

    fun getZacOktmo(bu: Int, date: Int): String? {
        var code = getCode(bu, date);
        return if (!_attrModelsByDate.containsKey(code)) null else _attrModelsByDate[code]?.zacOktmo;
    }
    // endregion

    // region methods
    fun getCode(bu: Int, date: Int): String {
        return "${bu}_${date}"
    }
    // endregion
}

class Fqr01(
    data: Data,
    private var _models: List<FqrModel01> = mutableListOf()
) : BaseCalculationBuilder(data) {
    // region api
    fun init() {
        _models = data.fqrs01
    }

    fun getZpersQty1(ztypeKf: String?, fiscPerMCount: Int): Double {
        val fiscPer = fiscDate(fiscPerMCount)
//        val fiscPer = 2022009
        var result = _models.filter {
            it.ztypeKf.equals(ztypeKf, true) &&
                    it.fiscPer == fiscPer
        }.sumOf { it.zpersQty }

        return result
    }

    fun getZpersQty2(ztypeKf: String?, bus: List<Int>): Double {
        var result = _models.filter {
            it.ztypeKf.equals(ztypeKf, true) &&
                    bus.contains(it.compCode)
        }.sumOf { it.zpersQty }

        return result
    }

    fun getZpersQty3(ztypeKf: String?, fiscPerMCount: Int, bus: List<Int>): Double {
        val fiscPer = fiscDate(fiscPerMCount)
//       val fiscPer = 2022009
        var result = _models.filter {
            it.ztypeKf.equals(ztypeKf, true) &&
                    it.fiscPer == fiscPer &&
                    bus.contains(it.compCode)
        }.sumOf { it.zpersQty }

        return result
    }

    fun getZwrkHrs(ztypeKf: String?, fiscPerMCount: Int): Double {
        val fiscPer = fiscDate(fiscPerMCount)
//        val fiscPer = 2022009
        var result = _models.filter {
            it.ztypeKf.equals(ztypeKf, true) &&
                    it.fiscPer == fiscPer
        }.sumOf { it.zwrkHrs }

        return result
    }

    private fun fiscDate(mCount: Int): Int? {
        if (mCount < 0) return null

        var fiscYear = mCount / 12
        var fiscMonth = mCount % 12
        var curYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyy")).toInt()
        var curMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).toInt()

        if (fiscMonth >= curMonth) {
            fiscYear++
            fiscMonth = 12 + curMonth - fiscMonth
        }
        curYear -= fiscYear
        curMonth -= fiscMonth
        var curMonthStr = curMonth.toString()
        if (curMonth < 10) curMonthStr = "0$curMonthStr"

        var sb = StringBuilder()
        sb.append(curYear).append("0").append(curMonthStr)

        return sb.toString().toInt()
    }

    // endregion
}

class Fqr04(
    data: Data,
    private val _fqrModelsByBu: MutableMap<Int, List<FqrModel04>> = mutableMapOf(),
    private var _fqrOKVEDModelsByBu: Map<Int, FqrModel04OKVED> = mutableMapOf(),
    private var _fqr04_02ModelsByBu: MutableMap<Int, List<FqrModel04_02>> = mutableMapOf(),
) : BaseCalculationBuilder(data) {
    // region api
    fun init(bu: Int) {
        if (_fqrModelsByBu.containsKey(bu)) return
        //
        var v = data.fqrs04.filter { it.compCode == bu }
        var v_02 = data.fqrs04_02.filter { it.compCode == bu }
        if (v.isEmpty()) return
        //
        _fqrModelsByBu[bu] = v
        _fqrOKVEDModelsByBu = data.fqrs04OKVED.associateBy { it.compCode }
        _fqr04_02ModelsByBu[bu] = v_02
    }

    fun getPeriod(): String {
        var sbPeriod = StringBuilder()
        sbPeriod.append("0${getPeriodFromMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).toInt())}")
        sbPeriod.append(LocalDate.now().format(DateTimeFormatter.ofPattern("yyy")).toInt())
        return sbPeriod.toString()
    }

    fun getZqkf01(bu: Int, ztypeKf: String, diapasonType: Int): Double? {
        if (!_fqrModelsByBu.containsKey(bu)) return 0.0

        var result = _fqrModelsByBu[bu]?.filter {
            it.ztypeKf.equals(ztypeKf, true) &&
                    it.fiscPer in getTimeRange(diapasonType)
        }?.sumOf { it.zqKf }

        return if (result == null) null else result / 1000
    }

    fun getZokVed(bu: Int): String {
        val zokvedStr = _fqrOKVEDModelsByBu[bu]?.zokVed
        if (zokvedStr != null) {
            if (zokvedStr.contains(",")) {
                val zokvedList = zokvedStr.split(", ")
                val sbResult = StringBuilder()
                zokvedList.forEach {
                    if (it == zokvedList.last()) sbResult.append(it) else sbResult.append("$it\n")
                }
                return sbResult.toString()
            }
            return zokvedStr
        }
        return "?????? ????????????????"
    }

    fun getZqText(bu: Int, diapasonType: Int): String? {
        if (!_fqr04_02ModelsByBu.containsKey(bu))
            return "?????? ????????????????"

        var models = _fqr04_02ModelsByBu[bu]?.filter {
            it.compCode == bu &&
                    it.fiscPer in getTimeRange(diapasonType)
        }
        var sb = StringBuilder()
        models?.forEach {
            if (it == models.last()) sb.append(it.zqText) else sb.append("${it.zqText}, ")
        }

        return sb.toString()
    }

    fun getZacInn(bu: Int): Double? {
        if (_fqr04_02ModelsByBu[bu].isNullOrEmpty())
            return 0.0

        var models = _fqr04_02ModelsByBu[bu]?.filter {
            it.compCode == bu &&
                    it.fiscPer in getTimeRange(2)
        }

        //TODO ???????????????? ???????????????????????? ???????????????? ?????????? ????????????????????????.
        return if (models.isNullOrEmpty()) 0.0
        else models[0].zacInn
    }

    fun getAmount(bu: Int, diapasonType: Int): Double {
        if (_fqr04_02ModelsByBu[bu].isNullOrEmpty())
            return 0.0

        var models = _fqr04_02ModelsByBu[bu]?.filter {
            it.compCode == bu &&
                    it.fiscPer in getTimeRange(2)
        }

        //TODO ???????????????? ???????????????????????? ???????????????? ?????????? ????????????????????????.
        return if (models.isNullOrEmpty()) 0.0
        else models[0].amount
    }
    // endregion

    // region methods
    enum class DiapasonType(val diapasonIndex: Int) {
        FIRST_MONTH_CURRENT_YEAR_TO_CURRENT_PERIOD(1),
        CURRENT_PERIOD_CURRENT_YEAR(2),
        FIRST_MONTH_PREVIOUS_YEAR_TO_CURRENT_PERIOD_PREVIOUS_YEAR(3),
        CURRENT_PERIOD_PREVIOUS_YEAR(4)
    }

    private fun getPeriodFromMonth(month: Int): Int {
        return when (month) {
            in 1..3 -> 1
            in 4..6 -> 2
            in 7..9 -> 3
            in 10..12 -> 4
            else -> 0
        }
    }

    private fun getTimeRange(diapasonIndex: Int): IntRange {
        when (diapasonIndex) {
            DiapasonType.FIRST_MONTH_CURRENT_YEAR_TO_CURRENT_PERIOD.diapasonIndex -> {
                return getFiscBeginOfYear()..getFiscPeriodOfCurrentYear()
            }

            DiapasonType.CURRENT_PERIOD_CURRENT_YEAR.diapasonIndex -> {
                return getFiscFirstMonthCurrentPeriod()..getFiscLastMonthCurrentPeriod()
            }

            DiapasonType.FIRST_MONTH_PREVIOUS_YEAR_TO_CURRENT_PERIOD_PREVIOUS_YEAR.diapasonIndex -> {
                return getFiscFirstMonthPreviousYear()..getFiscLastMonthCurrentPeriodPreviousYear()
            }

            DiapasonType.CURRENT_PERIOD_PREVIOUS_YEAR.diapasonIndex -> {
                return getFiscBeginCurrentPeriodPreviousYear()..getFiscLastMonthCurrentPeriodPreviousYear()
            }

            else -> throw IllegalStateException()
        }
    }

    private fun getFiscBeginOfYear(): Int = LocalDate.now().format(DateTimeFormatter.ofPattern("yyy001")).toInt()
    private fun getFiscPeriodOfCurrentYear(): Int =
        LocalDate.now().format(
            DateTimeFormatter.ofPattern(
                "yyy00${
                    getPeriodFromMonth(
                        LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).toInt()
                    )
                }"
            )
        ).toInt()

    private fun getFiscFirstMonthCurrentPeriod(): Int = getFiscMonthCurrentPeriod(2)

    private fun getFiscLastMonthCurrentPeriod(): Int = getFiscMonthCurrentPeriod(0)

    private fun getFiscMonthCurrentPeriod(index: Int): Int {
        var beginPeriodMonth =
            getPeriodFromMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).toInt()) * 3 - index
        return if (beginPeriodMonth < 10)
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyy00${beginPeriodMonth}")).toInt()
        else
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyy0${beginPeriodMonth}")).toInt()
    }

    private fun getFiscFirstMonthPreviousYear(): Int =
        "${(LocalDate.now().format(DateTimeFormatter.ofPattern("yyy")).toInt() - 1)}001".toInt()

    private fun getFiscLastMonthCurrentPeriodPreviousYear(): Int {
        var previousYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyy")).toInt() - 1
        var lastMonthOfPeriod =
            getPeriodFromMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).toInt()) * 3
        return if (lastMonthOfPeriod < 10)
            "${previousYear}00${lastMonthOfPeriod}".toInt()
        else
            "${previousYear}0${lastMonthOfPeriod}".toInt()
    }

    private fun getFiscBeginCurrentPeriodPreviousYear(): Int {
        var previousYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yyy")).toInt() - 1
        var firstMonthOfPeriod =
            getPeriodFromMonth(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")).toInt()) * 3 - 2
        return if (firstMonthOfPeriod < 10)
            "${previousYear}00${firstMonthOfPeriod}".toInt()
        else
            "${previousYear}0${firstMonthOfPeriod}".toInt()
    }
    // endregion
}

class Fqr10(
    data: Data,
    private var _bus: Map<Int, Int> = mutableMapOf(),
    private var _models: List<FqrModel10> = mutableListOf()
) : BaseCalculationBuilder(data) {
    // region api
    fun init(bu: List<Int>) {
        _bus = bu.associateWith { it }
        _models = data.fqrs10.filter { _bus.containsKey(it.compCode) }
    }

    fun getAmount(ztypeKf: String?): Double? {
        var result = _models.filter { it.ztypeKf.equals(ztypeKf, ignoreCase = true) }?.sumOf { it.amount };

        return if (result == null) null else result / AmountK
    }
    // endregion
}

class Fqr11(
    data: Data,
    private val _fqrModelsByBu: MutableMap<Int, List<FqrModel11>> = mutableMapOf()
) : BaseCalculationBuilder(data) {
    // region api
    fun init(bu: Int) {
        if (_fqrModelsByBu.containsKey(bu)) return;
        //
        var v = data.fqrs11.filter { it.compCode == bu }
        if (v.isEmpty()) return
        //
        _fqrModelsByBu[bu] = v
    }

    fun getAmount(bu: Int, ztypeKf: String): Double? {
        if (!_fqrModelsByBu.containsKey(bu)) return null
        //
        var result = _fqrModelsByBu[bu]?.filter { it.ztypeKf.equals(ztypeKf, ignoreCase = true) }?.sumOf { it.amount }

        return if (result == null) null else result / AmountK
    }
// endregion
}




