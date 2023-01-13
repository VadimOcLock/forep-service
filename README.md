# ОПИСАНИЕ МЕТОДОВ

Данный документ предназначен для описания основных методов, их ключевых слов, используемых в проекте для создания отчётности.

Описание методов разбито на 2 части: 
* определение метода в таблице Excel
* определение метода в коде

Формулы должны быть указаны в Excel файле формата xlsx. Параметры, передаваемые методам должны быть перечислены через запятую, без пробелов.

# СОДЕРЖАНИЕ

1. Отчёт №1 - Отчёт по отсутствиям
2. Отчёт №4 - Сведения об инвестициях в нефинансовые активы
3. Отчёт №10 - Сведения о доходах и расходах
4. Отчёт №11 - Сведения о затратах на производство

## Отчёт №1 - Отчёт по отсутствиям



## Отчёт №4 - Сведения об инвестициях в нефинансовые активы

### period
Определение метода: `getPeriod(): String`

Возвращает значение актуального квартала и года в следующем формате

`[квартал][год]` Например : `042022`

Значение квартала определяется следующим образом:

01 квартал - 01, 02, 03 месяц

02 квартал - 04, 05, 06 месяц

03 квартал - 07, 08, 09 месяц

04 квартал - 10, 11, 12 месяц

Для использования данного метода необходимо обратиться к объекту fqr04. В формуле Excel параметры не передаются.

Пример формулы в Excel: `'=fqr04.period()`

### zqtext1

Определение метода: `getZqtext1(bu: Int, date: Int): String?`

bu - балансовая единица, date - актуальная дата в формате `[год][месяц][день]`

Возвращает значение наименование балансовой единицы `AttrModel.zqtext1`, если актуальная дата входит во временной диапазон действительности.

Для использования данного метода необходимо обратиться к объекту attr. В формуле Excel параметры не передаются.

Пример формулы в Excel: `'=attr.zqtext1()`


### zqtext2

Определение метода: `getZqtext2(bu: Int, date: Int): String?`

bu - балансовая единица, date - актуальная дата в формате `[год][месяц][день]`

Возвращает адрес балансовой единицы `AttrModel.zqtext2`, если актуальная дата входит во временной диапазон действительности.

Для использования данного метода необходимо обратиться к объекту attr. В формуле Excel параметры не передаются.

Пример формулы в Excel: `'=attr.zqtext2()`

### zacokpg

Определение метода: `getZacOkpg(bu: Int, date: Int): String?`

bu - балансовая единица, date - актуальная дата в формате `[год][месяц][день]`

Возвращает код ОКПО организации балансовой единицы `AttrModel.zacOkpg`, если актуальная дата входит во временной диапазон действительности.

Для использования данного метода необходимо обратиться к объекту attr. В формуле Excel параметры не передаются.

Пример формулы в Excel: `'=attr.zacokpg()`

### zqkf01

Определение метода: `getZqkf01(bu: Int, ztypeKf: String, diapasonType: Int): Double?`

bu - балансовая единица, ztypeKf - тип показателя `FqrModel04.ztypeKf`, diapasonType - временной диапазон.

Типы временных диапазонов: 
1. первый месяц отчётного года по отчётный квартал включительно,
2. первый месяц отчетного квартала по последний месяц отчетного квартала,
3. первый месяц предыдущего года по отчетный квартал включительно предыдущего года,
4. первый месяц отчетного квартала предыдущего года по последний месяц отчетного квартала предыдущего года.

Для сравнения временных диапазонов используется поле `FqrModel04.fiscPer`.

Возвращает сумму Amount_1 `FqrModel04.zqKf` всех записей балансовой единицы, где совпадает тип показателя и временной диапазон.

Для использования данного метода необходимо обратиться к объекту fqr04. В формуле Excel первым параметром необходимо передать тип показателя, вторым - тип временного диапазона.

Пример формулы в Excel: `'=fqr04.zqkf01(3,1)`


