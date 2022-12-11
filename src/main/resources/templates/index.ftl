<#import "ui.ftl" as ui/>

<html>
    <head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta charset="utf-8">
        <title>Справочник сотрудников</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
        <script src="https://kit.fontawesome.com/ceab0242af.js" crossorigin="anonymous">
        </script>

        <style>
          * {
            margin: 0;
            overflow-x: hidden;
            overflow-y: hidden;
          }
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
    </head>
    <body>
        <table border="0">
            <!-- header -->
            <@ui.buildHeader />
            <!-- search -->
            <tr style="height:10%;">
                <!-- left -->
                <td style="width:800px;" colspan="2">
                    <div style="margin-top:20px;">
                        <@ui.buildSearchForm indexParameters.searchParameters />
                    </div>
                </td>
            </tr>
            <!-- employees -->
            <tr style="height:90%;">
                <td colspan="2">
                    <#if indexParameters.employees?? && indexParameters.employees?size gt 0>
                        <@ui.buildEmployees indexParameters.employees />
                    </#if>
                </td>
            </tr>
        </table>
    </body>
</html>