package ru.fortum.forep.calculator.models.settings

import ru.fortum.forep.calculator.models.FileModel

class ExtendedSettings(val templates: MutableMap<Int, String> = mutableMapOf(),
                       val data: MutableMap<String, FileModel> = mutableMapOf(),
                       var template: TemplateSettings,
                       var templateDirectory: String) {

    fun addTemplatePathByUnit(bu: Int, path: String?) {
        if (templates.containsKey(bu)) return
        if (path.isNullOrEmpty()) return
        //
        templates[bu] = path
    }

    fun addDataPathByName(fileModel: FileModel) {
        if (data.containsKey(fileModel.name)) return
        //
        data[fileModel.name] = fileModel
    }
}