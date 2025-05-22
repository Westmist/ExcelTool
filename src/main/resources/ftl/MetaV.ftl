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
    private volatile static ${metaName} meta;

    public static void load(String json) {
        meta = JsonUtils.fromJson(json, ${metaName}.class);
    }

    public static ${metaName} meta() {
        return meta;
    }
}