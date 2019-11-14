package com.cpren;

import com.cpren.pojo.ReportUserFootmark;
import com.cpren.utils.excel.ExcelUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cdxu@iyunwen.com on 2019/9/16
 */
public class ExportExcelTest {

    @Test
    public void export() throws IOException {
        Workbook workbook = null;
        String[] titleCustomer = null;
        String[] dbTitle = null;
        List<ReportUserFootmark> maps3 = new ArrayList<>();
        ReportUserFootmark item = new ReportUserFootmark();
        maps3.add(item);
        item.setUserId(123L);
        item.setCityName("南京");
        titleCustomer = new String[]{"用户ID", "用户角色", "用户名", "昵称", "地市", "操作类型", "百科/文库", "知识类型", "知识名称", "领域", "操作时间", "知识编号"};
        dbTitle = new String[]{"userId", "role", "name", "nickName", "cityName", "activeType", "domainType", "type", "question", "fieldName", "searchDate", "knowledgeId"};
        workbook = ExcelUtils.excelExport(workbook, maps3, "导出测试", "导出测试", titleCustomer, dbTitle);

        FileOutputStream file = new FileOutputStream("D:/rcp.xlsx");
        workbook.write(file);
    }
}
