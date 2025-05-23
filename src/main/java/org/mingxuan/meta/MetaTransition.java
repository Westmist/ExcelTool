package org.mingxuan.meta;

import org.apache.poi.ss.usermodel.*;
import org.mingxuan.config.AppConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mingxuan.constant.ExcelConst.HORIZONTAL_INDEX.*;
import static org.mingxuan.constant.ExcelConst.VERTICAL_INDEX.*;
import static org.mingxuan.constant.Mark.EMPTY;

public class MetaTransition {

    /**
     * 填充表头
     **/
    private static void fillField(Meta meta) {
        Sheet sheet = meta.getSheet();
        Row outTypeRow = sheet.getRow(OUT_TYPE_ROW);
        Row dataTypeRow = sheet.getRow(DATA_TYPE_ROW);
        Row dataNameRow = sheet.getRow(DATA_NAME_ROW);
        Row dataDescRow = sheet.getRow(DATA_DESC_ROW);


        String snName = AppConfig.CONFIG.getExcel().getSnName();
        for (int i = 0; i < outTypeRow.getLastCellNum(); i++) {
            Cell outType = outTypeRow.getCell(i);
            Cell dataType = dataTypeRow.getCell(i);
            Cell dataName = dataNameRow.getCell(i);
            Cell dataDesc = dataDescRow.getCell(i);

            String outTypeStr = readCell(outType);
            String dataTypeStr = readCell(dataType);
            String dataNameStr = readCell(dataName);
            String dataDescStr = readCell(dataDesc)
                    .replace("\n", "").replace("\r", "");

            if (outTypeStr.isEmpty() || dataTypeStr.isEmpty() || dataNameStr.isEmpty()) {
                continue;
            }
            Set<OutputType> outputTypes = OutputType.of(outTypeStr);
            if (!OutputType.S.included(outputTypes)) {
                continue;
            }

            Field field = new Field();
            field.setIndex(i);
            field.setName(dataNameStr);
            field.setDescribe(dataDescStr);
            field.setDataType(FieldType.getType(dataTypeStr));
            field.setOutputTypeSet(outputTypes);
            meta.getFields().add(field);
            meta.getOutputTypeSet().addAll(field.getOutputTypeSet());
            if (snName.equals(field.getName())) {
                meta.setSnType(field.getDataType().getJavaTypeName());
                meta.setSnField(field);
            }
        }

    }

    /**
     * 填充数据
     **/
    private static void fillData(Meta meta) {
        Sheet sheet = meta.getSheet();
        List<Integer> headIndexList = meta.getFields().stream().map(Field::getIndex).toList();
        for (int i = H_DATA_FIRST_ROW; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            boolean emptyRow = false;
            List<Object> rowData = new ArrayList<>();
            for (int j : headIndexList) {
                Cell cell = row.getCell(j);
                // 主键为空时为空格
                if (meta.getSnField().getIndex() == j && cell == null) {
                    emptyRow = true;
                    break;
                }
                String dataStr = readCell(cell);
                Field field = meta.getFields().stream()
                        .filter(e -> e.getIndex() == j).findFirst().get();
                FieldType fieldType = field.getDataType();
                rowData.add(fieldType.getConvert().convert(dataStr));
            }
            if (!emptyRow) {
                meta.getData().add(rowData);
            }
        }
    }

    public static void horizontal(Meta meta) {
        fillField(meta);
        fillData(meta);
    }

    public static void vertical(Meta meta) {
        Sheet sheet = meta.getSheet();
        List<Object> rowData = new ArrayList<>();
        for (int i = V_DATA_FIRST_ROW; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }

            Cell dataType = row.getCell(DATA_TYPE_COL);
            Cell dataName = row.getCell(DATA_NAME_COL);
            Cell dataValue = row.getCell(DATA_VALUE_COL);
            Cell dataDesc = row.getCell(DATA_DESC_COL);

            String outTypeStr = OutputType.S.name();
            String dataTypeStr = readCell(dataType);
            String dataStr = readCell(dataValue);
            String dataNameStr = readCell(dataName);
            String dataDescStr = readCell(dataDesc).replace("\n", "").replace("\r", "");

            if (dataTypeStr.isEmpty() || dataNameStr.isEmpty()) {
                continue;
            }

            FieldType fieldType = FieldType.getType(dataTypeStr);
            Field field = new Field();
            field.setIndex(i);
            field.setName(dataNameStr);
            field.setDescribe(dataDescStr);
            field.setDataType(fieldType);
            field.setOutputTypeSet(OutputType.of(outTypeStr));
            meta.getFields().add(field);
            meta.getOutputTypeSet().addAll(field.getOutputTypeSet());

            Object fieldData = fieldType.getConvert().convert(dataStr);
            rowData.add(fieldData);
        }
        meta.getData().add(rowData);
    }

    private static String readCell(Cell cell) {
        if (cell == null) {
            return EMPTY;
        }

        CellType cellType = cell.getCellType();
        // 是公式则要先计算
        if (cellType == CellType.FORMULA) {
            FormulaEvaluator evaluator = cell.getSheet().getWorkbook().getCreationHelper().createFormulaEvaluator();
            CellValue cellValue = evaluator.evaluate(cell);
            if (cellValue == null) return EMPTY;
            return switch (cellValue.getCellType()) {
                case STRING -> cellValue.getStringValue().trim();
                case NUMERIC -> String.valueOf((int) cellValue.getNumberValue());
                case BOOLEAN -> String.valueOf(cellValue.getBooleanValue());
                case _NONE, BLANK, ERROR -> EMPTY;
                default -> throw new IllegalStateException("Unexpected value: " + cell.getCellType());
            };
        }

        return switch (cell.getCellType()) {
            case STRING -> cell.getStringCellValue().trim();
            case NUMERIC -> String.valueOf((int) cell.getNumericCellValue());
            case BOOLEAN -> String.valueOf(cell.getBooleanCellValue());
            case _NONE, BLANK, ERROR -> EMPTY;
            default -> throw new IllegalStateException("Unexpected value: " + cell.getCellType());
        };
    }

}
