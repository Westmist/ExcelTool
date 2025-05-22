package org.mingxuan.util;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookType;
import org.mingxuan.config.AppConfig;
import org.mingxuan.meta.Meta;
import org.mingxuan.meta.MetaType;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static org.mingxuan.constant.Mark.*;

/**
 * 读文件工具
 */
public class ReadExcelUtils {
    /**
     * 过滤非excel文件
     */
    public static boolean filterFile(Path path, BasicFileAttributes attributes) {
        if (attributes.isDirectory() || attributes.isSymbolicLink()) {
            return false;
        }
        String fileName = path.getFileName().toString();
        //不是临时文件
        return !fileName.startsWith(TEMPORARY)
                //是xlsx的文件
                && fileName.endsWith(XSSFWorkbookType.XLSX.getExtension())
                // 不包含.
                && !fileName.contains(POINT);
    }

    public static Stream<Meta> readExcel(Path path) {
        try (InputStream is = Files.newInputStream(path); Workbook workbook = new XSSFWorkbook(is)) {
            //获取文件名
            String excelName = path.getFileName().toString().split(POINT)[0];
            return StreamSupport.stream(workbook.spliterator(), true)
                    //过滤没有包含-的表
                    .filter(sheet -> {
                        if (sheet.getSheetName().contains(SHU_XIAN)) {
                            return true;
                        }
                        System.err.println("sheet: " + excelName + ":" + sheet.getSheetName() + "不包含|");
                        return false;
                    })
                    //创建表对应的Meta
                    .map(sheet -> createMeta(sheet, excelName));
        } catch (IOException e) {
            throw new RuntimeException(path.getFileName().toString() + "导出失败！" + e);
        }
    }

    private static Meta createMeta(Sheet sheet, String excelName) {
        AppConfig.Head head = AppConfig.CONFIG.getHead();
        String packageName = head.getPackageName();
        List<String> imports = head.getImps();
        Meta meta = new Meta();
        meta.setPackageName(packageName);
        meta.setImps(imports);
        meta.setExcelName(excelName);
        String[] sheetSplit = sheet.getSheetName().split("\\|");
        meta.setSheetName(sheetSplit[1]);
        meta.setSheetDescribe(sheetSplit[0]);
        meta.setSheet(sheet);
        meta.setMetaType(MetaType.type(excelName));
        meta.setMetaName(StringUtil.metaName(meta.getSheetName()));
        // 填充
        meta.getMetaType().genMeta(meta);
        return meta;
    }

}
