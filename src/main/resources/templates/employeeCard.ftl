<#import "ui.ftl" as ui/>

<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="utf-8">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
<script src="https://kit.fontawesome.com/ceab0242af.js" crossorigin="anonymous"></script>

<style>
          table{
            height:100%;
            width:100%;
          }
          .label-header{
            color:white;
            font-size:20;
            margin-left:11px;
          }
</style>

<table border="0">
    <!-- header -->
    <@ui.buildCardHeader />
    <!-- card -->
    <tr>
        <td colspan="2">
            <div class="columns is-vcentered" style="margin-top:10px;">
                <div class="column is-two-fifths">
                    <div class="field is-horizontal">
                        <div class="field-label"></div>
                        <div class="field-body"><label class="label">Контактные данные</label></div>
                    </div>
                    <!-- telInternal -->
                    <div class="field is-horizontal">
                        <div class="field-label is-small">
                            <label class="label">Рабочий номер тел.:</label>
                        </div>
                        <div class="field-body">
                            <div class="field">
                                <p class="control is-expanded has-icons-left">
                                    <#if param.employee.contacts?has_content>
                                    <input class="input is-small" type="text" readonly value="${param.employee.contacts.telInternal!}">
                                    <#else>
                                    <input class="input is-small" type="text" readonly value="">
                                    </#if>
                                    <span class="icon is-left">
                                                <i class="fa fa-phone" aria-hidden="true"></i>
                                            </span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <!-- telBusiness -->
                    <div class="field is-horizontal">
                        <div class="field-label is-small">
                            <label class="label">Корпоративный номер телефона:</label>
                        </div>
                        <div class="field-body">
                            <div class="field">
                                <p class="control is-expanded has-icons-left">
                                    <#if param.employee.contacts?has_content>
                                    <input class="input is-small" type="text" readonly value="${param.employee.contacts.telBusiness!}">
                                    <#else>
                                    <input class="input is-small" type="text" readonly value="">
                                    </#if>
                                    <span class="icon is-left">
                                                <i class="fa fa-phone-square" aria-hidden="true"></i>
                                            </span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <!-- telMobile -->
                    <div class="field is-horizontal">
                        <div class="field-label is-small">
                            <label class="label">Мобильный телефон:</label>
                        </div>
                        <div class="field-body">
                            <div class="field">
                                <p class="control is-expanded has-icons-left">
                                    <#if param.employee.contacts?has_content>
                                    <input class="input is-small" type="text" readonly value="${param.employee.contacts.telMobile!}">
                                    <#else>
                                    <input class="input is-small" type="text" readonly value="">
                                    </#if>
                                    <span class="icon is-left">
                                                <i class="fa fa-mobile" aria-hidden="true"></i>
                                            </span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <!-- telIp -->
                    <div class="field is-horizontal">
                        <div class="field-label is-small">
                            <label class="label">IP телефон:</label>
                        </div>
                        <div class="field-body">
                            <div class="field">
                                <p class="control is-expanded has-icons-left">
                                    <#if param.employee.contacts?has_content>
                                    <input class="input is-small" type="text" readonly value="${param.employee.contacts.telIp!}">
                                    <#else>
                                    <input class="input is-small" type="text" readonly value="">
                                    </#if>
                                    <span class="icon is-left">
                                                <i class="fa fa-fax" aria-hidden="true"></i>
                                            </span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <!-- email -->
                    <div class="field is-horizontal">
                        <div class="field-label is-small">
                            <label class="label">Электронная почта:</label>
                        </div>
                        <div class="field-body">
                            <div class="field">
                                <p class="control is-expanded has-icons-left">
                                    <#if param.employee.contacts?has_content>
                                    <input class="input is-small" type="text" readonly value="${param.employee.contacts.email!}">
                                    <#else>
                                    <input class="input is-small" type="text" readonly value="">
                                    </#if>
                                    <span class="icon is-left">
                                                <i class="fa fa-envelope-o" aria-hidden="true"></i>
                                            </span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <!--  -->
                    <div class="field is-horizontal">
                        <div class="field-label"></div>
                        <div class="field-body"><label class="label">Персональная информация</label></div>
                    </div>
                    <!-- lastName -->
                    <div class="field is-horizontal">
                        <div class="field-label is-small">
                            <label class="label">Фамилия:</label>
                        </div>
                        <div class="field-body">
                            <div class="field">
                                <p class="control is-expanded has-icons-left">
                                    <input class="input is-small" type="text" readonly
                                           value="${param.employee.lastName!}">
                                    <span class="icon is-left">
                                                <i class="fas fa-user"></i>
                                            </span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <!-- firstName -->
                    <div class="field is-horizontal">
                        <div class="field-label is-small">
                            <label class="label">Имя:</label>
                        </div>
                        <div class="field-body">
                            <div class="field">
                                <p class="control is-expanded has-icons-left">
                                    <input class="input is-small" type="text" readonly
                                           value="${param.employee.firstName!}">
                                    <span class="icon is-left">
                                                <i class="fas fa-user"></i>
                                            </span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <!-- midName -->
                    <div class="field is-horizontal">
                        <div class="field-label is-small">
                            <label class="label">Отчество:</label>
                        </div>
                        <div class="field-body">
                            <div class="field">
                                <p class="control is-expanded has-icons-left">
                                    <input class="input is-small" type="text" readonly
                                           value="${param.employee.midName!}">
                                    <span class="icon is-left">
                                                <i class="fas fa-user"></i>
                                            </span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <!-- position -->
                    <div class="field is-horizontal">
                        <div class="field-label is-small">
                            <label class="label">Название должности:</label>
                        </div>
                        <div class="field-body">
                            <div class="field">
                                <p class="control is-expanded has-icons-left">
                                    <#if param.employee.position?has_content>
                                    <input class="input is-small" type="text" readonly value="${(param.employee.position.name)!}">
                                    <#else>
                                    <input class="input is-small" type="text" readonly value="">
                                    </#if>
                                    <span class="icon is-left">
                                                <i class="fa fa-grav" aria-hidden="true"></i>
                                            </span>
                                </p>
                            </div>
                        </div>
                    </div>
                    <!-- orgUnit -->
                    <div class="field is-horizontal">
                        <div class="field-label is-small">
                            <label class="label">Подразделение:</label>
                        </div>
                        <div class="field-body">
                            <div class="field">
                                <p class="control is-expanded has-icons-left">
                                    <#if param.employee.orgUnit?has_content>
                                    <input class="input is-small" type="text" readonly value="${(param.employee.orgUnit.name)!}">
                                    <#else>
                                    <input class="input is-small" type="text" readonly value="">
                                    </#if>
                                    <span class="icon is-left">
                                                <i class="fas fa-users"></i>
                                            </span>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="column">
                    <image src="${param.photoPath!}" style="width:256px;height:256px;"></image>
                </div>
            </div>
        </td>
    </tr>
</table>