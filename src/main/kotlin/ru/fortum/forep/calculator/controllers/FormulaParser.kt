package ru.fortum.forep.calculator.controllers

import ru.fortum.forep.calculator.builders.CalculationBuilder

class FormulaParser(calculation: CalculationBuilder,
                    private val _calculation: CalculationBuilder = calculation,
                    private val _f: Map<String, String> = mapOf(
                        AttrName  to AttrName,
                        FqrName01 to FqrName01,
                        FqrName02 to FqrName02,
                        FqrName03 to FqrName03,
                        FqrName04 to FqrName04,
                        FqrName10 to  FqrName10,
                        FqrName11 to  FqrName11
                    )
) {
    companion object {
        // region constants
        // classes:
        const val AttrName:String  = "attr"
        const val FqrName01:String = "fqr01"
        const val FqrName02:String = "fqr02"
        const val FqrName03:String = "fqr03"
        const val FqrName04:String = "fqr04"
        const val FqrName10:String = "fqr10"
        const val FqrName11:String = "fqr11"
        // methods:
        const val AmountMethodName:String = "amount"
        // endregion

        // method(1000, 1200, 1300) -> { 1000, 1200, 1300 }
        private fun parseParameters(formula: String): List<String>? {
            if (formula.isEmpty()) return null
            //
            val f1 = formula.replace(")", "")

            return f1.split(",")
        }
    }
    // region api
    fun parse(formula: String, bu: Int, date: Int):Any?
    {
        val formulaModel = parseFormulaModel(formula, bu, date)

        return calculate(formulaModel)
    }
    // endregion

    // region methods
    private fun parseFormulaModel(formula: String, bu: Int, date: Int): ru.fortum.forep.calculator.models.FormulaModel?
    {
        if (formula.isEmpty()) return null
        if (formula.length < 3) return null
        //
        val formula2 = formula.trim()
        if (formula.substring(0, 1) != "=") return null
        //
        val formula3 = formula2.substring(1)
        val f = formula3.split("(")
        if (f.size < 2) return null
        //
        val model = ru.fortum.forep.calculator.models.FormulaModel(
            bu = bu,
            date = date,
            callSignature = f[0],
            parameters = f[1],
            execClass = "",
            execMethod = "",
            methodSplit = mutableListOf()
        )

        model.methodSplit = model.callSignature.split(".")
        if (model.methodSplit.size <= 1) return null
        if (!_f.containsKey(model.methodSplit[0])) return null

        model.execClass  = model.methodSplit[0]
        model.execMethod = model.methodSplit[1]

        return model
    }
    private fun calculate(m: ru.fortum.forep.calculator.models.FormulaModel?):Any?
    {
        if (m == null) return null

        if (m.execClass == AttrName)
        {
            if (m.execMethod == "year")
            {
                val parameters = parseParameters(m.parameters) ?: return null
                return _calculation.attr.getYear(m.date, parameters[0])
            }
            if (m.execMethod == "period")
                return _calculation.attr.getPeriod()
            if (m.execMethod == "postalcd")
                return _calculation.attr.getPostalCd(getBu(m), m.date)
            if (m.execMethod == "zqtext1")
                return _calculation.attr.getZqtext1(getBu(m), m.date)
            if (m.execMethod == "zqtext2")
                return _calculation.attr.getZqtext2(getBu(m), m.date)
            if (m.execMethod == "zqtext3")
                return _calculation.attr.getZqtext3(getBu(m), m.date)
            if (m.execMethod == "zqtext4")
                return _calculation.attr.getZqtext4(getBu(m), m.date)
            if (m.execMethod == "zacogrn")
                return _calculation.attr.getZacOgrn(getBu(m), m.date)
            if (m.execMethod == "zacinn")
                return _calculation.attr.getZacInn(getBu(m), m.date)
            if (m.execMethod == "zacokpg")
                return _calculation.attr.getZacOkpg(getBu(m), m.date)
            if (m.execMethod == "zacoktmo")
                return _calculation.attr.getZacOktmo(getBu(m), m.date)
            if (m.execMethod == "zacokpo")
                return _calculation.attr.getZacOkpo(getBu(m), m.date)
        }
        else if (m.execClass == FqrName01)
        {
            if (m.execMethod == "zpersqty1")
            {
                val parameters = parseParameters(m.parameters) ?: return null
                return _calculation.fqr01.getZpersQty1(parameters[0], parameters[1].toInt())
            }
            if (m.execMethod == "zpersqty2")
            {
                val parameters = (parseParameters(m.parameters) ?: return null).toMutableList()
                parameters[1] = parameters[1].replace("{", "")
                parameters[parameters.size - 1] = parameters[parameters.size - 1].replace("}", "")
                val bus = parameters.filter { it != parameters[0] }.map { it.toInt() }

                return _calculation.fqr01.getZpersQty2(parameters[0], bus)
            }
            if (m.execMethod == "zpersqty3")
            {
                val parameters = (parseParameters(m.parameters) ?: return null).toMutableList()
                parameters[2] = parameters[2].replace("{", "")
                parameters[parameters.size - 1] = parameters[parameters.size - 1].replace("}", "")
                val bus = parameters.filter { it != parameters[0] && it != parameters[1] }.map { it.toInt() }

                return _calculation.fqr01.getZpersQty3(parameters[0], parameters[1].toInt(), bus)
            }
            if (m.execMethod == "zwrkhrs")
            {
                val parameters = parseParameters(m.parameters) ?: return null
                return _calculation.fqr01.getZwrkHrs(parameters[0], parameters[1].toInt())
            }
        }
        else if (m.execClass == FqrName02)
        {
            if (m.execMethod == "zpersqty")
            {
                val parameters = parseParameters(m.parameters) ?: return null
                return _calculation.fqr02.getZpersQty(parameters[0])
            }

        }
        else if (m.execClass == FqrName03)
        {
            if (m.execMethod == "zqtext1")
            {
                return _calculation.fqr03.getZqtxext1()
            }
            if (m.execMethod == "zacokved")
            {
                return _calculation.fqr03.getZacOkved()
            }

        }
        else if (m.execClass == FqrName04)
        {
            if (m.execMethod == "zqkf01")
            {
                val parameters = parseParameters(m.parameters) ?: return null
                return _calculation.fqr04.getZqkf01(m.bu, parameters[0], parameters[1].toInt())
            }
            if (m.execMethod == "zokved")
                return _calculation.fqr04.getZokVed(m.bu)
            if (m.execMethod == "zqtext")
            {
                val parameters = parseParameters(m.parameters) ?: return null
                return _calculation.fqr04.getZqText(m.bu, parameters[0].toInt())
            }
            if (m.execMethod == "zacinn")
                return _calculation.fqr04.getZacInn(m.bu)
            if (m.execMethod == "amount")
            {
                val parameters = parseParameters(m.parameters) ?: return null
                return _calculation.fqr04.getAmount(m.bu, parameters[0].toInt())
            }
        }
        else if (m.execClass == FqrName10)
        {
            if (m.execMethod == AmountMethodName)
            {
                val parameters = parseParameters(m.parameters) ?: return null
                return _calculation.fqr10.getAmount(parameters[0])
            }
        }
        else if (m.execClass == FqrName11)
        {
            if (m.execMethod == AmountMethodName)
            {
                val parameters = parseParameters(m.parameters) ?: return null
                return _calculation.fqr11.getAmount(m.bu, parameters[0])
            }
        }

        return null
    }
    // endregion

    // region utils
    private fun getBu(m: ru.fortum.forep.calculator.models.FormulaModel):Int
    {
        val parameters = parseParameters(m.parameters)
        var buCustom = m.bu
        if (parameters?.size!! <= 0) return buCustom
        //
        if (parameters[0].isNotEmpty())
            buCustom = parameters[0].toInt()

        return buCustom
    }
    // endregion
}