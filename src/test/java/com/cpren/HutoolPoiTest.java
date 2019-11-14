package com.cpren;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.cron.pattern.CronPatternUtil;
import cn.hutool.db.DbUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.WorkbookUtil;
import com.cpren.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ThreadUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.SheetUtil;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author cdxu@iyunwen.com on 2019/8/30
 */
public class HutoolPoiTest {
    @Autowired
    private DataSource dataSource;

    @Test
    public void main() throws IOException {
        // 创建工作空间
        Workbook book = WorkbookUtil.createBook(true);
        Font font = book.createFont();
        font.setFontName("楷体");
        CellStyle cellStyle = book.createCellStyle();
        cellStyle.setFont(font);
        DataFormat dataFormat = book.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        CellStyle cellStyle1 = book.createCellStyle();
        cellStyle1.setDataFormat(format);
        cellStyle1.setFont(font);

        // 创建工作簿
        Sheet sheet = book.createSheet();
        int columnWidth = sheet.getColumnWidth(0);
        System.out.println("第一列的宽度:"+columnWidth);
        sheet.setColumnWidth(1,columnWidth*2);
        // 创建行
        Row row = sheet.createRow(0);
        // 创建单元格
        Cell cell = row.createCell(0);
        Cell cell2 = row.createCell(1);

        cell.setCellStyle(cellStyle);
        cell.setCellValue("江南小镇123");
        cell2.setCellStyle(cellStyle1);
        cell2.setCellValue(new Date());
        FileUtil.del("d:/rcp.xlsx");
        book.write(new FileOutputStream(new File("d:/rcp.xlsx")));
        book.close();
    }

    @Test
    public void readExcel() throws JsonProcessingException {
        ExcelReader excelReader = new ExcelReader(new File("d:/rcp.xlsx"),0);
        List<List<Object>> read = excelReader.read();
        for(List oj: read) {
            JSON parse = JSONUtil.parse(oj);
            ObjectMapper objectMapper = new ObjectMapper();
            String s = objectMapper.writeValueAsString(oj);
            System.out.println(parse);
            System.out.println(s);
        }
    }

    public void convert(String url, List<String> lists) throws IOException {
        Connection connect = Jsoup.connect(url);
        Document document = connect.get();
        Elements tr = document.select("tr a");
        for(Element element : tr) {
            String href = element.attr("href");
            if(href.equals("../")){
                continue;
            }
            if(href.endsWith("/")) {
                System.out.println(href);
                lists.add(href);
                convert(href, lists);
            }
        }
    }

    @Test
    public void rcp() throws IOException {
        List<String> lists = new ArrayList<>();
        convert("http://maven.faqrobot.org/nexus/content/groups/public/com/iyunwen/v5/", lists);
        boolean contains = lists.contains("http://maven.faqrobot.org/nexus/content/groups/public/antlr/antlr/");
        System.out.println(contains);
    }

}
