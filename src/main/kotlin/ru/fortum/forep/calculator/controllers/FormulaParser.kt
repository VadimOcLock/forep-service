package ru.fortum.forep.calculator.controllers

import ru.fortum.forep.calculator.builders.CalculationBuilder

class FormulaParser(calculation: CalculationBuilder,
                    private val _calculation: CalculationBuilder = calculation,
                    private val _f: Map<String, String> = mapOf(
                        AttrName  to AttrName,
                        FqrName01 to FqrName01,
                        FqrName10 to  FqrName10,
                        FqrName11 to  FqrName11
                    )
) {
    companion object {
        // region constants
        // classes:
        const val AttrName:String  = "attr";
        const val FqrName10:String = "fqr10";
        const val FqrName11:String = "fqr11";
        const val FqrName01:String = "fqr01";
        // methods:
        const val AmountMethodName:String = "amount";
        // endregion

        // method(1000, 1200, 1300) -> { 1000, 1200, 1300 }
        private fun parseParameters(formula:String):List<String>?
        {
            if (formula.isNullOrEmpty()) return null
            //
            var f1 = formula.replace(")", "")
            var f2 = f1.split(",")

            return f2
        }
    }
    // region api
    fun parse(formula: String, bu: Int, date: Int):Any?
    {
        var formulaModel = parseFormulaModel(formula, bu, date)

        return calculate(formulaModel);
    }
    // endregion

    // region methods
    private fun parseFormulaModel(formula: String, bu: Int, date: Int): ru.fortum.forep.calculator.models.FormulaModel?
    {
        if (formula.isNullOrEmpty()) return null;
        if (formula.length < 3) return null;
        //
        var formula2 = formula.trim();
        if (!formula.substring(0, 1).equals("=")) return null;
        //
        var formula3 = formula2.substring(1);
        var f = formula3.split("(");
        if (f.size < 2) return null;
        //
        var model = ru.fortum.forep.calculator.models.FormulaModel(
            bu = bu,
            date = date,
            callSignature = f[0],
            parameters = f[1],
            execClass = "",
            execMethod = "",
            methodSplit = mutableListOf()
        )

        model.methodSplit = model.callSignature.split(".");
        if (model.methodSplit.size <= 1) return null;
        if (!_f.containsKey(model.methodSplit[0])) return null;

        model.execClass  = model.methodSplit[0];
        model.execMethod = model.methodSplit[1];

        return model;
    }
    private fun calculate(m: ru.fortum.forep.calculator.models.FormulaModel?):Any?
    {
        if (m == null) return null

        if (m.execClass.equals(AttrName))
        {
            if (m.execMethod.equals("year"))
            {
                var parameters = parseParameters(m.parameters) ?: return null
                return _calculation.attr.getYear(m.date, parameters[0]);
            }
            else if (m.execMethod.equals("postalcd"))
                return _calculation.attr.getPostalCd(getBu(m), m.date);
            else if (m.execMethod.equals("zqtext1"))
                return _calculation.attr.getZqtext1(getBu(m), m.date);
            else if (m.execMethod.equals("zqtext2"))
                return _calculation.attr.getZqtext2(getBu(m), m.date);
            else if (m.execMethod.equals("zqtext3"))
                return _calculation.attr.getZqtext3(getBu(m), m.date);
            else if (m.execMethod.equals("zqtext4"))
                return _calculation.attr.getZqtext4(getBu(m), m.date);
            else if (m.execMethod.equals("zacogrn"))
                return _calculation.attr.getZacOgrn(getBu(m), m.date);
            else if (m.execMethod.equals("zacinn"))
                return _calculation.attr.getZacInn(getBu(m), m.date);
            else if (m.execMethod.equals("zacokpg"))
                return _calculation.attr.getZacOkpg(getBu(m), m.date);
            else if (m.execMethod.equals("zacoktmo"))
                return _calculation.attr.getZacOktmo(getBu(m), m.date);
        }
        else if (m.execClass.equals(FqrName01))
        {
            if (m.execMethod.equals("zpersqty1"))
            {
                var parameters = parseParameters(m.parameters) ?: return null
                return _calculation.fqr01.getZpersQty1(parameters[0], parameters[1].toInt())
            }
            if (m.execMethod.equals("zpersqty2"))
            {
                var parameters = (parseParameters(m.parameters) ?: return null).toMutableList()
                parameters[1] = parameters[1].replace("{", "")
                parameters[parameters.size - 1] = parameters[parameters.size - 1].replace("}", "")
                var bus = parameters.filter { !it.equals(parameters[0], true) }.map { it.toInt() }

                return _calculation.fqr01.getZpersQty2(parameters[0], bus);
            }
//            if (m.execMethod.equals("zpersqty3"))
//            {
//                var parameters = parseParameters(m.parameters) ?: return null
//                return _calculation.fqr01.getZpersQty3(parameters);
//            }
            if (m.execMethod.equals("zwrkhrs"))
            {
                var parameters = parseParameters(m.parameters) ?: return null
                return _calculation.fqr01.getZwrkHrs(parameters[0], parameters[1].toInt())
            }
        }
        else if (m.execClass.equals(FqrName10))
        {
            if (m.execMethod.equals(AmountMethodName))
            {
                var parameters = parseParameters(m.parameters) ?: return null
                return _calculation.fqr10.getAmount(parameters[0]);
            }
        }
        else if (m.execClass.equals(FqrName11))
        {
            if (m.execMethod.equals(AmountMethodName))
            {
                var parameters = parseParameters(m.parameters) ?: return null
                return _calculation.fqr11.getAmount(m.bu, parameters[0]);
            }
        }

        return null
    }
    // endregion

    // region utils
    private fun getBu(m: ru.fortum.forep.calculator.models.FormulaModel):Int
    {
        var parameters = parseParameters(m.parameters)
        var buCustom = m.bu
        if (parameters?.size!! <= 0) return buCustom
        //
        if (!parameters[0].isNullOrEmpty())
            buCustom = parameters[0].toInt()

        return buCustom
    }
    // endregion
}