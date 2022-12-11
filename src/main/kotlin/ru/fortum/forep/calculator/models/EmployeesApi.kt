package ru.fortum.forep.calculator.models

import io.ktor.resources.*
import kotlinx.serialization.Serializable

@Serializable
@Resource("/v1")
class EmployeesApi {

    @Serializable
    @Resource("employer")
    class Employer(val parent: ru.fortum.forep.calculator.models.EmployeesApi = ru.fortum.forep.calculator.models.EmployeesApi()) {
        @Serializable
        @Resource("all")
        class All(val parent: ru.fortum.forep.calculator.models.EmployeesApi.Employer = ru.fortum.forep.calculator.models.EmployeesApi.Employer())

        @Serializable
        @Resource("byPersNo")
        class ByPersNo(val parent: ru.fortum.forep.calculator.models.EmployeesApi.Employer = ru.fortum.forep.calculator.models.EmployeesApi.Employer(), val persNo: String)

        @Serializable
        @Resource("byLastname")
        class ByLastname(val parent: ru.fortum.forep.calculator.models.EmployeesApi.Employer = ru.fortum.forep.calculator.models.EmployeesApi.Employer(), val pattern: String)

        @Serializable
        @Resource("byUnit")
        class ByUnit(val parent: ru.fortum.forep.calculator.models.EmployeesApi.Employer = ru.fortum.forep.calculator.models.EmployeesApi.Employer(), val id: String)

        @Serializable
        @Resource("byPosition")
        class ByPosition(val parent: ru.fortum.forep.calculator.models.EmployeesApi.Employer = ru.fortum.forep.calculator.models.EmployeesApi.Employer(), val id: String)

        @Serializable
        @Resource("changes")
        class Changes(val parent: ru.fortum.forep.calculator.models.EmployeesApi.Employer = ru.fortum.forep.calculator.models.EmployeesApi.Employer(), val from: String)
    }
}
