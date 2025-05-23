package org.mingxuan.meta;

import lombok.Data;
import org.apache.poi.ss.usermodel.Sheet;
import org.mingxuan.project.JsonUtil;

import java.util.*;

import static org.mingxuan.meta.MetaType.HORIZONTAL;


@Data
public class Meta {

    /**
     * 包名
     */
    private String packageName;

    /**
     * 导入的包名
     */
    private List<String> imps;

    /**
     * 表类型
     */
    private MetaType metaType;

    /**
     * 主键
     */
    private Field snField;

    /**
     * 主键类型
     */
    @Deprecated
    private String snType;

    /**
     * excel-sheet
     */
    private Sheet sheet;

    /**
     * excel 文件名
     */
    private String excelName;

    /**
     * sheet表名
     */
    private String sheetName;

    /**
     * sheet表描述
     */
    private String sheetDescribe;

    /**
     * java类名
     */
    private String metaName;

    /**
     * 字段列表
     */
    private List<Field> fields = new ArrayList<>();

    /**
     * 数据
     */
    private List<List<Object>> data = new ArrayList<>();
    /**
     * 导出类型
     */
    private Set<OutputType> outputTypeSet = new HashSet<>();

    public String toJson() {
        return metaType == HORIZONTAL ? doHorizontalToJson() : doVerticalToJson();
    }

    private String doHorizontalToJson() {
        List<Map<String, Object>> jsonData = new ArrayList<>();
        for (List<Object> row : data) {
            Map<String, Object> rowMap = new LinkedHashMap<>();
            for (int j = 0; j < row.size(); j++) {
                Field field = fields.get(j);
                if (!OutputType.S.included(field.getOutputTypeSet())) {
                    continue;
                }
                rowMap.put(field.getName(), row.get(j));
            }
            jsonData.add(rowMap);
        }
        return JsonUtil.toJson(jsonData);
    }

    private String doVerticalToJson() {
        Map<String, Object> jsonData = new HashMap<>();
        List<Object> dataFirst = data.getFirst();
        for (int i = 0; i < fields.size(); i++) {
            Field field = fields.get(i);
            jsonData.put(field.getName(), dataFirst.get(i));
        }
        return JsonUtil.toJson(jsonData);
    }


}
