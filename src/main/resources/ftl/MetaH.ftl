<#--包名-->
package ${packageName};

<#--导入-->
<#list imps as imp>
import ${imp};
</#list>

/**
* ${sheetDescribe}
* 源文件名：${excelName}-${sheetName}
* 该文件为生成的文件，修改无效
*
<#list fields as field>
* @param ${field.name} ${field.describe}
</#list>
*/
public record ${metaName}(
<#list fields as field>
        ${field.dataType.javaTypeName} ${field.name} <#if field_has_next>,</#if>
</#list>
) {
    public volatile static Map<${snType}, ${metaName}> dataMap = new LinkedHashMap<>();

    public static void load(String json) {
        ${metaName}[] dataArr = JsonUtils.fromJson(json, ${metaName}[].class);
        Map<${snType}, ${metaName}> newDataMap = new LinkedHashMap<>();
        for (${metaName} data : dataArr) {
            newDataMap.put(data.sn, data);
        }
        dataMap = newDataMap;
    }

    public static ${metaName} get(${snType} sn) {
        return dataMap.get(sn);
    }

    public static Collection<${metaName}> getAll() {
        return dataMap.values();
    }

    public static Collection<${snType}> getAllKey() {
        return dataMap.keySet();
    }

    public static Stream<${metaName}> stream() {
        return dataMap.values().stream();
    }

    public static Stream<${metaName}> stream(java.util.function.Predicate<${metaName}> filter) {
        return stream().filter(filter);
    }

    public static List<${metaName}> filterToList(java.util.function.Predicate<${metaName}> filter) {
        return stream(filter).collect(Collectors.toList());
    }

    public static boolean contains(${snType} sn) {
        return dataMap.containsKey(sn);
    }

}