package ru.fortum.forep.calculator.models

import kotlinx.serialization.Serializable

@Serializable
class FileModel(var name: String, var extension: String, var path: String, var newExtension: String)