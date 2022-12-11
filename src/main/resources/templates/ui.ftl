<#-- SEARCH HEADER -->
<#macro buildHeader>
    <tr style="height:46px;background-color:hsl(171,100%,41%);" >
        <td colspan="2" style="vertical-align:middle;">
            <label class="label-header">Справочник сотрудников</label>
        </td>
    </tr>
</#macro>

<#-- EMPLOYEE CARD HEADER -->
<#macro buildCardHeader>
    <tr style="height:46px;background-color:hsl(171,100%,41%);" >
        <td colspan="2" style="vertical-align:middle;">
            <label class="label-header">Карточка сотрудника</label>
        </td>
    </tr>
</#macro>

<#-- SEARCH FORM -->
<#macro buildSearchForm parameters>
    <form class="search-form" action="/" method="get" >
        <!-- ФИО -->
        <div class="field has-addons is-horizontal">
            <div class="field-label is-small">
                <label class="label">ФИО:</label>
            </div>
            <div class="field-body">
                <div class="field">
                    <p class="control is-expanded has-icons-left">
                        <input class="input is-small" type="text" name="surname"
                               placeholder="ФИО" value="${parameters.surname!}">
                        <span class="icon is-left">
                                <i class="fas fa-user"></i>
                            </span>
                    </p>
                </div>
                <div class="field">
                    <p class="control">
                        <button class="button is-link is-small">Поиск</button>
                    </p>
                </div>
            </div>
        </div>
    </form>
</#macro>

<#-- EMPLOYEES -->
<#macro buildEmployees employees>
    <div class="table-container">
        <table class="table is-striped is-hoverable is-striped is-fullwidth"
               id="id" border="1px" cellspacing="2" border="1" cellpadding="5" style="border-collapse:collapse;">
            <thead>
            <tr class="tableHeader">
                <th>Фамилия</th>
                <th>Имя</th>
                <th>Отчество</th>
                <th>Подразделение</th>
                <th>Название должности</th>
                <th>Рабочий номер телефона</th>
                <th>Мобильный номер телефона</th>
            </tr>
            </thead>
            <tfoot><tfoot>
            <tbody>
            <#list employees as e>
            <tr>
                <td><a href="/employees/${e.persNo}" target="_parent">${e.lastName!}</a>
                <td>${e.firstName!}
                <td>${e.midName!}
                <#if e.orgUnit?has_content>
                    <td>${e.orgUnit.name!}
                <#else>
                    <td>
                </#if>
                <#if e.position?has_content>
                    <td>${e.position.name!}
                <#else>
                    <td>
                </#if>
                <#if e.contacts?has_content>
                    <td>${e.contacts.telBusiness!}
                    <td>${e.contacts.telMobile!}
                <#else>
                    <td>
                    <td>
                </#if>
            </tr>
            </#list>
            </tbody>
        </table>
    </div>
</#macro>