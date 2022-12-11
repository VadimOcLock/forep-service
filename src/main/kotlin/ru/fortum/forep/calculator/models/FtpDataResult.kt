package ru.fortum.forep.calculator.models

import ru.fortum.forep.calculator.models.settings.DataFile
import java.io.FileInputStream

class FtpDataResult(var stream: FileInputStream, var dataFile: DataFile)