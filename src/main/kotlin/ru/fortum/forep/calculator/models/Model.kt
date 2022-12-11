package ru.fortum.forep.calculator.models
import kotlinx.serialization.Serializable
// region parameters
/** Параметры главной страницы */
data class IndexParameters(val searchParameters: ru.fortum.forep.calculator.models.SearchParameters,
                           val employees: List<ru.fortum.forep.calculator.models.Employer>)
/** Search Parameters */
@Serializable
data class SearchParameters(val surname: String,
                            val name: String,
                            val departmentId: String,
                            val positionId: String,
                            val phone: String,
                            val extended: String,
                            val departmentName: String,
                            val positionName: String)
@Serializable
data class EmployeeParameters(val employee: ru.fortum.forep.calculator.models.Employer,
                              val photoPath: String)
// endregion

// region dto
/** Подразделение */
@Serializable
data class OrgItem(val id: String,
                   val name: String,
                   val type: String,
                   val parentId: String)
/** Контакты */
@Serializable
data class Contacts(val userId: String,
                    val email: String,
                    val telBusiness: String,
                    val telMobile: String,
                    val telInternal: String,
                    val telIp: String)
/** сотрудники */
@Serializable
data class Employer(val id: String,
                    val persNo: String,
                    val firstName: String,
                    val midName: String,
                    val lastName: String,
                    val orgUnit: ru.fortum.forep.calculator.models.OrgItem?,
                    val position: ru.fortum.forep.calculator.models.OrgItem?,
                    val headPosition: ru.fortum.forep.calculator.models.OrgItem?,
                    val group: String,
                    val photoId: String,
                    val contacts: ru.fortum.forep.calculator.models.Contacts?)
// endregion
