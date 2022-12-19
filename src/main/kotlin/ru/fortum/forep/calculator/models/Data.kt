package ru.fortum.forep.calculator.models

class Data(var attrs: List<AttrModel> = listOf(),
           var fqrs01: List<FqrModel01> = listOf(),
           var fqrs04: List<FqrModel04> = listOf(),
           var fqrs04OKVED: List<FqrModel04OKVED> = listOf(),
           var fqrs04_02: List<FqrModel04_02> = listOf(),
           var fqrs10: List<FqrModel10> = listOf(),
           var fqrs11: List<FqrModel11> = listOf())