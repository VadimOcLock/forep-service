package ru.fortum.forep.calculator.mappers

import org.apache.poi.ss.usermodel.Cell
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.ss.usermodel.Row

class TypeMapper {

    companion object {

        fun getStringValue(cell: Cell?): String?
        {
            return when(cell?.cellType) {
                        CellType.STRING  -> cell.stringCellValue
                        CellType.NUMERIC -> "${cell.numericCellValue}"
                        CellType.FORMULA -> "${cell.cellFormula}"
                        else             -> null
                    }
        }
        fun getValue(cell: Cell): Any
        {
            return when(cell.cellType) {
                        CellType.STRING  -> cell.stringCellValue
                        CellType.NUMERIC -> cell.numericCellValue
                        else             -> 1
            }
        }
//        fun map(row: Row, address: ColumnAddress):RowTemplateItem
//        {
//            if (row.Cells.Count == 0) return null;
//            //
//            var rowTemplateItem = new RowTemplateItem();
//            foreach (var cell in row.Cells)
//            {
//                if (cell.ColumnIndex      == address.Index)
//                    rowTemplateItem.Index = GetStringValue(cell);
//                else if (cell.ColumnIndex == address.Title)
//                    rowTemplateItem.Title = GetStringValue(cell);
//                else if (cell.ColumnIndex == address.Units)
//                    rowTemplateItem.Units = GetStringValue(cell);
//                else if (cell.ColumnIndex == address.Variability)
//                    rowTemplateItem.Variability = GetStringValue(cell);
//                else if (cell.ColumnIndex == address.Source)
//                    rowTemplateItem.Source = GetStringValue(cell);
//                else if (cell.ColumnIndex == address.Calculation)
//                    rowTemplateItem.Calculation = GetStringValue(cell);
//            }
//            rowTemplateItem.CellsCount = row.Cells.Count;
//
//            return rowTemplateItem;
//        }
//        fun SetValue( cell:Cell, t:CellType, d:String)
//        {
//            when (t)
//            {
//                case CellType.Numeric:
//                {
//                    var decimalType = double
//                        .TryParse(d, NumberStyles.Any, CultureInfo.InvariantCulture, out var dt);
//                    if(decimalType)
//                        cell.SetCellValue(dt);
//                    else
//                        cell.SetCellValue(d);
//                    break;
//                }
//                case CellType.String:
//                cell.SetCellValue(d);
//                break;
//            }
//        }
        fun setValue(cell:Cell, v:Any)
        {
            if (v == null) return
            //
            if (v is Double)
                cell.setCellValue(v as Double);
            else if (v is String)
                cell.setCellValue(v as String);
//            else if (v is Double)
//                cell.setCellValue(decimal.ToDouble((Doubleecimal)v));
        }
    }
}