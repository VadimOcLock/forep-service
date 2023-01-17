package ru.fortum.forep.calculator.controllers

import java.io.FileInputStream

import ru.fortum.forep.calculator.models.Data

class CsvParser(val data: Data = Data()) {

    // region api
    open fun parse(name: String, stream: FileInputStream) {

        if (name.contains(AttrName)) {
            if (!data.attrs.isNullOrEmpty()) return
            data.attrs = parseAttr(stream)
        } else if (name.contains(FqrName01)){
            if (!data.fqrs01.isNullOrEmpty()) return
            data.fqrs01 = parseFqr01(stream)
        } else if (name.contains(FqrName02)){
            if (!data.fqrs02.isNullOrEmpty()) return
            data.fqrs02 = parseFqr02(stream)
        } else if (name.contains(FqrName04)){
            if (!data.fqrs04.isNullOrEmpty()) return
            data.fqrs04 = parseFqr04(stream)
        } else if (name.contains(FqrName04OKVED)){
            if (!data.fqrs04OKVED.isNullOrEmpty()) return
            data.fqrs04OKVED = parseFqr04OKVED(stream)
        } else if (name.contains(FqrName04_02)){
            if (!data.fqrs04_02.isNullOrEmpty()) return
            data.fqrs04_02 = parseFqr04_02(stream)
        } else if (name.contains(FqrName10)) {
            if (!data.fqrs10.isNullOrEmpty()) return
            data.fqrs10 = parseFqr10(stream)
        } else if (name.contains(FqrName11)) {
            if (!data.fqrs11.isNullOrEmpty()) return
            data.fqrs11 = parseFqr11(stream)
        }
    }

    // endregion
    companion object {
        // region const:
        const val AttrName :String= "FOR_QLIK_BUKRS_ATTR_PBW"
        const val FqrName01 :String= "FOR_QLIK_R01_PBW"
        const val FqrName02 :String= "FOR_QLIK_R02_PBW"
        const val FqrName04 :String= "FOR_QLIK_R04_PBW"
        const val FqrName04OKVED :String= "FOR_QLIK_R04_OKVED_PBW"
        const val FqrName04_02 :String= "FOR_QLIK_R04_2_PBW"
        const val FqrName10:String= "FOR_QLIK_R10_PBW"
        const val FqrName11:String= "FOR_QLIK_R11_PBW"
        // endregion
        // attr:
        fun parseAttr(stream : FileInputStream): List<ru.fortum.forep.calculator.models.AttrModel>
        {
            var result = mutableListOf<ru.fortum.forep.calculator.models.AttrModel>()
            parseStream(stream) { index, line -> result.add(parseAttrLine(index, line)) }

            return result
        }
        fun parseAttrLine(i: Int, line : String): ru.fortum.forep.calculator.models.AttrModel
        {
            var l = line.split(";")
            return ru.fortum.forep.calculator.models.AttrModel(
                index = i,
                //
                compCode = l[0].toInt(),
                dateFrom = l[1].toInt(),
                dateTo = l[2].toInt(),
                postalCd = l[3],
                city = l[4],
                zacInn = l[6],
                zacOgrn = l[8],
                zacOkpg = l[13],
                zacOkpo = l[14],
                zacOktmo = l[15],
                zqtext1 = l[18],
                zqtext2 = l[19],
                zqtext3 = l[20],
                zqtext4 = l[21],
            )
        }
        // fqr01:
        fun parseFqr01(stream: FileInputStream): List<ru.fortum.forep.calculator.models.FqrModel01> {
            var result = mutableListOf<ru.fortum.forep.calculator.models.FqrModel01>()
            parseStream(stream) { index, line -> result.add(parseFqrLine01(index, line)); }

            return result
        }

        fun parseFqrLine01(index: Int, line: String): ru.fortum.forep.calculator.models.FqrModel01 {
            var l = line.split(";")
            return ru.fortum.forep.calculator.models.FqrModel01(
                compCode = l[0].toInt(),
                fiscPer = l[1].toInt(),
                fiscVarnt = l[2],
                persArea = l[3],
                zimonIntRv = l[4].toInt(),
                zperNum = l[5].toInt(),
                ztypeKf = l[6],
                zpersQty = l[7].toDouble(),
                zwrkHrs = l[8].toDouble()
            )
        }
        // fqr01:
        fun parseFqr02(stream: FileInputStream): List<ru.fortum.forep.calculator.models.FqrModel02> {
            var result = mutableListOf<ru.fortum.forep.calculator.models.FqrModel02>()
            parseStream(stream) { index, line -> result.add(parseFqrLine02(index, line)); }

            return result
        }

        fun parseFqrLine02(index: Int, line: String): ru.fortum.forep.calculator.models.FqrModel02 {
            var l = line.split(";")
            return ru.fortum.forep.calculator.models.FqrModel02(
                compCode = l[0].toInt(),
                fiscPer = l[1].toInt(),
                fiscVarnt = l[2],
                persArea = l[3],
                zimonIntRv = l[4].toInt(),
                zperNum = l[5].toInt(),
                ztypeKf = l[6],
                zpersQty = l[7].toDouble()
            )
        }
        // fqr04:
        fun parseFqr04(stream: FileInputStream): List<ru.fortum.forep.calculator.models.FqrModel04> {
            var result = mutableListOf<ru.fortum.forep.calculator.models.FqrModel04>()
            parseStream(stream) { index, line -> result.add(parseFqrLine04(index, line)); }

            return result
        }

        fun parseFqrLine04(index: Int, line: String): ru.fortum.forep.calculator.models.FqrModel04 {
            var l = line.split(";")
            return ru.fortum.forep.calculator.models.FqrModel04(
                compCode = l[0].toInt(),
                fiscPer = l[1].toInt(),
                fiscVarnt = l[2],
                zperNum = l[3].toInt(),
                ztypeKf = l[4],
                zqKf = l[5].toDouble()
            )
        }
        // fqr04OKVED:
        fun parseFqr04OKVED(stream: FileInputStream): List<ru.fortum.forep.calculator.models.FqrModel04OKVED> {
            var result = mutableListOf<ru.fortum.forep.calculator.models.FqrModel04OKVED>()
            parseStream(stream) { index, line -> result.add(parseFqrLine04OKVED(index, line)); }

            return result
        }

        fun parseFqrLine04OKVED(index: Int, line: String): ru.fortum.forep.calculator.models.FqrModel04OKVED {
            var l = line.split(";")
            return ru.fortum.forep.calculator.models.FqrModel04OKVED(
                compCode = l[0].toInt(),
                zokPo = l[1].toDouble(),
                zokVed = l[2]
            )
        }
        // fqr04_02:
        fun parseFqr04_02(stream: FileInputStream): List<ru.fortum.forep.calculator.models.FqrModel04_02> {
            var result = mutableListOf<ru.fortum.forep.calculator.models.FqrModel04_02>()
            parseStream(stream) { index, line -> result.add(parseFqrLine04_02(index, line)); }

            return result
        }

        fun parseFqrLine04_02(index: Int, line: String): ru.fortum.forep.calculator.models.FqrModel04_02 {
            var l = line.split(";")
            return ru.fortum.forep.calculator.models.FqrModel04_02(
                fiscPer = l[0].toInt(),
                compCode = l[1].toInt(),
                vendor = l[2].toDouble(),
                zqText = l[3],
                zacInn = l[4].toDouble(),
                amount = l[5].toDouble()
            )
        }
        // fqr10:
        fun parseFqr10(stream : FileInputStream) : List<ru.fortum.forep.calculator.models.FqrModel10>
        {
            var result = mutableListOf<ru.fortum.forep.calculator.models.FqrModel10>()
            parseStream(stream) { index, line -> result.add(parseFqrLine10(index, line)); }

            return result
        }
        fun parseFqrLine10(i: Int, line: String): ru.fortum.forep.calculator.models.FqrModel10
        {
            var l = line.split(";")
            return ru.fortum.forep.calculator.models.FqrModel10(
                index = i,
                //
                zrepNum = l[0].toInt(),
                fiscYear = l[1].toInt(),
                compCode = l[2].toInt(),
                ztypeKf = l[3],
                amount = l[4].toDouble(),
            )
        }
        // fqr11:
        fun parseFqr11(stream:FileInputStream):List<ru.fortum.forep.calculator.models.FqrModel11>
        {
            var result = mutableListOf<ru.fortum.forep.calculator.models.FqrModel11>()
            parseStream(stream) { index, line -> result.add(parseFqrLine11(index, line)); }

            return result;
        }
        fun parseFqrLine11(i: Int,  line: String): ru.fortum.forep.calculator.models.FqrModel11
        {
            var l = line.split(";")
            return ru.fortum.forep.calculator.models.FqrModel11(
                index = i,
                //
                zrepNum = l[0].toInt(),
                fiscYear = l[1].toInt(),
                fiscPer = l[2].toInt(),
                compCode = l[3].toInt(),
                ztypeKf = l[4],
                amount = l[5].toDouble(),
            )
        }
        // region utils
        inline fun parseStream(stream: FileInputStream, crossinline act:(Int, String) -> Unit): Boolean
        {
            var i = 0
            stream.reader().forEachLine {
                if (i == 0)
                    i++
                else
                    act(i++, it)
            }
            return true;
        }
        // endregion
    }
}