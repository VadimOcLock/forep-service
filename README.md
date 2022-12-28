# ОПИСАНИЕ МЕТОДОВ

Данный документ предназначен для описания основных методов, их ключевых слов, используемых в проекте для создания отчётности.

Описание методов разбито на 2 части : 
* определение метода в таблице Excel
* определение метода в коде

Формулы должны быть указаны в Excel файле формата xlsx.

# СОДЕРЖАНИЕ

1. Отчёт №1 - Отчёт по отсутстивям
2. Отчёт №4 - Сведения об инвестициях в нефинансовые активы
3. Отчёт №10 - Сведения о доходах и расходах
4. Отчёт №11 - Сведения о затратах на производство

## Отчёт №1 - Отчёт по отсутстивям



## Отчёт №4 - Сведения об инвестициях в нефинансовые активы

### period
Определение метода: `period()`

Возвращает значение актуального квартала и года в следующем формате

`[квартал][год]` Например : `042022`

Значение квартала определяется следующим образом :

01 квартал - 01, 02, 03 месяц

02 квартал - 04, 05, 06 месяц

03 квартал - 07, 08, 09 месяц

04 квартал - 10, 11, 12 месяц

Для использования данного метода недобходимо обратиться к объекту fqr04.

Пример формулы в Excel: `'=fqr04.period()`

### zqtext1

Определение метода: `getZqtext1(bu: Int, date: Int)`

bu - балансовая единица, date - актуальная дата в формате `[год][месяц][день]`

Возвращает значение наименование балансовой единицы, если акутальная дата входит во временной диапазон действительности.

Для использования данного метода недобходимо обратиться к объекту attr.

Пример формулы в Excel: `'=attr.zqtext1()`

