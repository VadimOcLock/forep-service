package ru.fortum.forep.calculator.models

class FormulaModel(var bu: Int,
                   var date: Int,
                   var methodSplit: List<String>,
                   var callSignature: String,
                   var execClass: String,
                   var execMethod: String,
                   var parameters: String)