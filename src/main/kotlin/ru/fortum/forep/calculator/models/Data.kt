package ru.fortum.forep.calculator.models

class Data(var attrs: List<AttrModel> = listOf(),
           var fqrs01: List<FqrModel01> = listOf(),
           var fqrs10: List<FqrModel10> = listOf(),
           var fqrs11: List<FqrModel11> = listOf())