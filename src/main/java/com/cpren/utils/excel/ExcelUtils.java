package com.cpren.utils.excel;

import com.cpren.utils.yw.YWDateUtils;
import com.cpren.utils.yw.YWStringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description Excel操作工具类
 * @Author 谷和金  hjgu@iyunwen.com
 * @Date 2018/5/31 15:26
 */
public class ExcelUtils extends BaseOutDataUtils {
    /**
     * 日志记录器.
     */
    private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    /**
     * 表格导出最大行数
     */
    public static final Integer EXCEL_MAX_ROW = 65530;

    /**
     * 禁止实例化
     */
    private ExcelUtils() {

    }

    /**
     * 创建表格(有序号列，有颜色)
     * 根据导出数据量,返回高低版本的excel文件对象
     *
     * @param <T>
     * @param data            需要导出的数据
     * @param sheetName       导出数据所在的 excel文件中单个sheet的名字
     * @param columnNames     表格的列名
     * @param propertyNames   与表格列名对应的 对象的成员变量名字
     * @param firstColumnName 首列title名称
     * @throws IOException
     */
    private static <T> Workbook excelExport(Workbook wb, List<T> data, String title, String sheetName, String[] columnNames,
                                            String[] propertyNames, int[] defaultColWidth, String firstColumnName) throws IOException {

        if (CollectionUtils.isEmpty(data)) {
            data = new ArrayList<T>();
        }
        logger.debug("开始创建表格");
        if (null == wb) {
            // 根据导出数据量创建excel
            if (data.size() > EXCEL_MAX_ROW) {
                wb = new XSSFWorkbook();
            } else {
                wb = new HSSFWorkbook();
            }
        }
        // 创建sheet
        Sheet sheet = wb.createSheet(sheetName);
        sheet.setDefaultRowHeightInPoints(15);
        // 创建一行
        Row rowTitle = sheet.createRow(0);
        rowTitle.setHeightInPoints(40);
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnNames.length));

        // 获取字体样式
        Font font = getTitleFontStyle(wb, 12);
        // 获取标题样式
        CellStyle titleStyle = getTitleStyle(wb, font);
        // 获取单数内容单元格样式
        CellStyle cellStyleSingular = getSingularCellStyle(wb);
        // 获取双数内容单元格样式
        CellStyle cellStyleDual = getDualCellStyle(wb);

        CreationHelper createHelper = wb.getCreationHelper();
        // 获取单数内容单元格样式--日期格式
        CellStyle dateStyleSingular = getSingularCellStyle(wb);
        // 获取双数内容单元格样式--日期格式
        CellStyle dateStyleDual = getDualCellStyle(wb);
        dateStyleSingular.setDataFormat(createHelper.createDataFormat().getFormat(YWDateUtils.DATE_TIME_FORMAT));
        dateStyleDual.setDataFormat(createHelper.createDataFormat().getFormat(YWDateUtils.DATE_TIME_FORMAT));

        // 大标题样式
        Font titleFont = getTitleFontStyle(wb, 16);
        Cell titleCell = rowTitle.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(getBigTitleStyle(wb, titleFont));

        // 填充标题栏
        Row titleCell_ = sheet.createRow(1);

        // 插入首列, 将原本title列数组元素往后移一位，序号列在第一列展示
        String[] newColumnNames = new String[columnNames.length + 1];
        newColumnNames[0] = firstColumnName;
        for (int i = 0; i < columnNames.length; i++) {
            newColumnNames[i + 1] = columnNames[i];
        }
        BaseOutDataUtils.setTitle(titleCell_, titleStyle, newColumnNames);

        // 首列默认宽度
        int[] newDefaultColWidth = new int[defaultColWidth.length + 1];
        newDefaultColWidth[0] = sheet.getColumnWidth(0);
        for (int i = 0; i < defaultColWidth.length; i++) {
            newDefaultColWidth[i + 1] = defaultColWidth[i];
        }
        defaultColWidth = newDefaultColWidth;


        int index = 2;
        // 填充单元格
        int colNum;
        for (int i = 0; i < data.size(); i++) {
            colNum = 0;
            T item = data.get(i);
            Row row = sheet.createRow(i + 2);
            row.setHeightInPoints(20);
            if (propertyNames != null) {

                // 序号列
                Cell id = row.createCell(colNum);
                id.setCellValue(index - 2 + 1.0);
                id.setCellStyle(index % 2 == 0 ? cellStyleDual : cellStyleSingular);
                colNum++;

                // 获取对象的成员变量的值
                for (String propertyName : propertyNames) {
                    // 获取对象的成员变量的值
                    Cell cell = row.createCell(colNum);
                    Object tem = invokeGetterMethod(item, propertyName);

                    if (tem instanceof Date) {
                        cell.setCellValue((Date) tem);
                        cell.setCellStyle(index % 2 == 0 ? dateStyleDual : dateStyleSingular);
                    } else if (tem instanceof Double) {
                        String ress = String.format("%.2f", tem);
                        cell.setCellValue(ress + "");
                        cell.setCellStyle(index % 2 == 0 ? cellStyleDual : cellStyleSingular);
                    } else {
                        if (tem == null) {
                            cell.setCellValue("");
                            cell.setCellStyle(index % 2 == 0 ? cellStyleDual : cellStyleSingular);
                        } else {
                            cell.setCellValue(tem + "");
                            cell.setCellStyle(index % 2 == 0 ? cellStyleDual : cellStyleSingular);
                        }
                    }

                    colNum++;
                }
                index++;
            }
        }

        // 设置宽度
        for (int i = 1; i < newColumnNames.length; i++) {
            sheet.setColumnWidth(i, defaultColWidth[i]);
        }
        return wb;
    }

    /**
     * 创建表格(有序号列，有颜色)
     * 默认sheet页名称: sheet1; 默认第一页名称: 序号; 默认列宽: 60*100
     *
     * @param <T>
     * @param data          需要导出的数据
     * @param columnNames   表格的列名
     * @param propertyNames 与表格列名对应的 对象的成员变量名字
     * @throws IOException
     */
    public static <T> Workbook excelExport(List<T> data, String title, String[] columnNames, String[] propertyNames) throws IOException {

        int[] defaultColWidth = new int[columnNames.length];
        for (int i = 0; i < defaultColWidth.length; i++) {
            defaultColWidth[i] = 60 * 100;
        }

        return excelExport(null, data, title, "sheet1", columnNames, propertyNames, defaultColWidth, "序号");
    }

    /**
     * 创建表格(有序号列，有颜色)
     * 默认第一页名称: 序号; 默认列宽: 60*100
     *
     * @param data          需要导出的数据
     * @param columnNames   表格的列名
     * @param propertyNames 与表格列名对应的 对象的成员变量名字
     * @throws IOException
     */
    public static <T> Workbook excelExport(List<T> data, String title, String sheetName, String[] columnNames, String[] propertyNames) throws IOException {

        int[] defaultColWidth = new int[columnNames.length];
        for (int i = 0; i < defaultColWidth.length; i++) {
            defaultColWidth[i] = 60 * 100;
        }

        return excelExport(null, data, title, sheetName, columnNames, propertyNames, defaultColWidth, "序号");
    }

    /**
     * 创建表格(有序号列，有颜色)
     * 默认第一页名称: 序号; 默认列宽: 60*100
     *
     * @param data          需要导出的数据
     * @param columnNames   表格的列名
     * @param propertyNames 与表格列名对应的 对象的成员变量名字
     * @throws IOException
     */
    public static <T> Workbook excelExport(List<T> data, String title, String sheetName, String[] columnNames, String[] propertyNames, int[] defaultColWidth) throws IOException {
        return excelExport(null, data, title, sheetName, columnNames, propertyNames, defaultColWidth, "序号");
    }

    /**
     * 创建表格(有序号列，有颜色)
     * 默认第一页名称: 序号; 默认列宽: 60*100
     *
     * @param <T>
     * @param data          需要导出的数据
     * @param columnNames   表格的列名
     * @param propertyNames 与表格列名对应的 对象的成员变量名字
     * @throws IOException
     */
    public static <T> Workbook excelExport(Workbook workbook, List<T> data, String title, String sheetName, String[] columnNames, String[] propertyNames) throws IOException {

        int[] defaultColWidth = new int[columnNames.length];
        for (int i = 0; i < defaultColWidth.length; i++) {
            defaultColWidth[i] = 60 * 100;
        }

        return excelExport(workbook, data, title, sheetName, columnNames, propertyNames, defaultColWidth, "序号");
    }

    /**
     * 返回excel文件对象（第0列插入一列）有序号，有颜色
     *
     * @param <T>
     * @param data 需要导出的数据
     * @param title 表格title行（第一行）
     * @param sheetName 导出数据所在的 excel文件中单个sheet的名字
     * @param columnNames 表格的列名
     * @param propertyNames 与表格列名对应的 对象的成员变量名字
     * @param firstColumnName 首列title名称（第二行第一列）
     * @param fileName 导出的文件名
     */
    public static <T> void excelExport(List<T> data, String title, String sheetName, String[] columnNames,
                                       String[] propertyNames, int[] defaultColWidth, String firstColumnName,
                                       String fileName, HttpServletRequest request,
                                       HttpServletResponse response) {

        Workbook workbook = null;
        OutputStream os = null;
        try {
            // 如果数据为空，或小于指定条数时，为xls格式，否则为xlsx格式
            if (null == data || data.size() < EXCEL_MAX_ROW){
                logger.debug("导出格式后缀名【.xls】");
                response.setContentType("application/vnd.ms-excel");
                response.addHeader("Content-Disposition", "attachment; filename=\""
                        + getEncodeName(getFileName(fileName) + ".xls" , request) + "\"");
                workbook = excelExport(null, data, title, sheetName, columnNames, propertyNames, defaultColWidth, firstColumnName, EXCEL_XLS);
            } else {
                logger.debug("导出格式后缀名【.xlsx】");
                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.addHeader("Content-Disposition", "attachment; filename=\""
                        + getEncodeName(getFileName(fileName) + ".xlsx" , request) + "\"");
                workbook = excelExport(null, data, title, sheetName, columnNames, propertyNames, defaultColWidth, firstColumnName, EXCEL_XLSX);
            }
            os = response.getOutputStream();
            workbook.write(os);
            response.flushBuffer();
        } catch (Exception e) {
            logger.warn("表格导出失败,失败原因: " + e);
        } finally {
            try {
                if (null != workbook){
                    workbook.close();
                }
                if (null != os) {
                    os.close();
                }
            } catch (IOException e) {
                logger.warn("表格导出关闭失败,失败原因: " + e);
            }
        }
    }

    public static <T> Workbook excelExport(Workbook wb, List<T> data, String title, String sheetName, String[] columnNames,
                                           String[] propertyNames, int[] defaultColWidth, String firstColumnName,
                                           long isxlsx) throws IOException {
        logger.debug("开始创建表格");

        // 创建excel
        if (null == wb) {
            if (isxlsx == EXCEL_XLSX) {
                wb = new XSSFWorkbook();
            } else {
                wb = new HSSFWorkbook();
            }
        }
        // 创建sheet
        Sheet sheet = wb.createSheet(sheetName);
        sheet.setDefaultRowHeightInPoints(15);

        // 创建一行
        Row rowTitle = sheet.createRow(0);
        rowTitle.setHeightInPoints(40);
        // 合并单元格
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnNames.length));
        // 获取字体样式
        Font font = getFontStyle(wb);
        // 获取标题样式
        CellStyle titleStyle = getTitleStyle(wb, font);
        // 获取单数内容单元格样式
        CellStyle cellStyleSingular = getSingularCellStyle(wb);
        // 获取双数内容单元格样式
        CellStyle cellStyleDual = getDualCellStyle(wb);
        Cell titleCell = rowTitle.createCell(0);
        titleCell.setCellValue(title);
        titleCell.setCellStyle(getBigTitleStyle(wb, font));

        // 填充标题栏
        Row titleCell_ = sheet.createRow(1);

        // 插入首列, 将原本title列数组元素往后移一位，序号列在第一列展示
        String[] newColumnNames = new String[columnNames.length + 1];
        newColumnNames[0] = firstColumnName;
        for (int i = 0; i < columnNames.length; i++) {
            newColumnNames[i+1] = columnNames[i];
        }
        BaseOutDataUtils.setTitle(titleCell_, titleStyle, newColumnNames);

        // 首列默认宽度
        int[] newDefaultColWidth = new int[defaultColWidth.length + 1];

        newDefaultColWidth[0] = sheet.getColumnWidth(0);

        for (int i = 0; i < defaultColWidth.length; i++) {
            newDefaultColWidth[i+1] = defaultColWidth[i];
        }
        defaultColWidth = newDefaultColWidth;

        int colNum = 0;
        if (data != null) {

            CreationHelper createHelper = wb.getCreationHelper();
            int index = 2;
            // 填充单元格
            for (int i = 0; i < data.size(); i++) {
                colNum = 0;
                T item = data.get(i);
                Row row = sheet.createRow(i + 2);
                row.setHeightInPoints(20);
                if (propertyNames != null) {

                    // 序号列
                    Cell id = row.createCell(colNum);
                    id.setCellValue(index - 2 + 1.0);
                    id.setCellStyle(index % 2 == 0 ? cellStyleDual : cellStyleSingular);
                    colNum ++;

                    // 获取对象的成员变量的值
                    for (String propertyName : propertyNames) {
                        // 获取对象的成员变量的值
                        Cell cell = row.createCell(colNum);
                        Object tem = invokeGetterMethod(item, propertyName);

                        if (tem instanceof Date) {
                            // 获取单数内容单元格样式
                            CellStyle dateStyleSingular = getSingularCellStyle(wb);
                            // 获取双数内容单元格样式
                            CellStyle dateStyleDual = getDualCellStyle(wb);
                            dateStyleSingular.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
                            dateStyleDual.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
                            cell.setCellValue((Date) tem);
                            cell.setCellStyle(index % 2 == 0 ? dateStyleDual : dateStyleSingular);
                        } else if (tem instanceof Double) {
                            String ress = "";
                            if (tem != null) {
                                ress = String.format("%.2f", tem);
                            }
                            cell.setCellValue(ress + "");
                            cell.setCellStyle(cellStyleSingular);
                        } else {
                            if (tem == null) {
                                cell.setCellValue("");
                                cell.setCellStyle(cellStyleSingular);
                            } else {
                                cell.setCellValue(tem + "");
                                cell.setCellStyle(cellStyleSingular);
                            }
                        }

                        colNum++;
                    }
                    index ++;
                }
            }
        }

        // 设置宽度
        for (int i = 1; i < newColumnNames.length; i++) {
            sheet.setColumnWidth(i, defaultColWidth[i]);
        }
        return wb;
    }

    /**
     *
     * @date:2018年7月12日
     * @param name 需要设置Excel的 功能名称
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String getFileName(String name) {
        String time = (new SimpleDateFormat("yyyMMddHHmmss")).format(new Date(System.currentTimeMillis()));
        return "模板:" + name + time;
    }

    /**
     * 下载Excel文件
     *
     * @param workbook 工作簿
     * @param fileName 导出的文件名
     * @param request 请求
     * @param response 响应
     */
    public static void downloadWorkbook(Workbook workbook, String fileName, HttpServletRequest request, HttpServletResponse response) {

        try {
            if (workbook instanceof XSSFWorkbook) {
                logger.debug("导出格式后缀名【.xlsx】");
                //response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                response.setContentType("application/octet-stream;charset=UTF-8");
                response.addHeader("Content-Disposition", "attachment; filename=" + ExcelUtils.getEncodeName(fileName + ".xlsx", request) + "");

                workbook.write(response.getOutputStream());
            } else {
                logger.debug("导出格式后缀名【.xls】");
                //response.setContentType("application/vnd.ms-excel");
                response.setContentType("application/octet-stream;charset=UTF-8");
                response.addHeader("Content-Disposition", "attachment; filename="+ ExcelUtils.getEncodeName(fileName + ".xls", request) + "");

                workbook.write(response.getOutputStream());
            }
            response.flushBuffer();
        } catch (Exception e) {
            logger.warn("表格导出失败,失败原因: " + e);
        } finally {
            close(workbook, null);
        }
    }


    /**
     * 反射调用对象的getter方法
     *
     * @param target       对象
     * @param propertyName 对象的成员变量名字
     */
    private static Object invokeGetterMethod(Object target, String propertyName) {
        String getterMethodName = "get" + YWStringUtils.toUpperFirstCase(propertyName);
        return invokeMethod(target, getterMethodName, new Class[]{}, new Object[]{});
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符.
     */
    private static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes,
                                       final Object[] parameters) {
        Method method = getDeclaredMethod(object, methodName, parameterTypes);
        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] parameterType "
                    + parameterTypes + " on target [" + object + "]");
        }

        method.setAccessible(true);

        try {
            return method.invoke(object, parameters);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod.
     * <p>
     * 如向上转型到Object仍无法找到, 返回null.
     */
    private static Method getDeclaredMethod(Object object, String methodName, Class<?>[] parameterTypes) {
        Assert.notNull(object, "object不能为空");

        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {// NOSONAR
                // Method不在当前类定义,继续向上转型
                logger.debug("获取对象的DeclaredMethod异常：" + e);
            }
        }
        return null;
    }

    /**
     * 根据文件路径 生成对应的表格格式
     *
     * @param path 文件路径
     * @return 为空表示生成文件名失败
     * @throws IOException
     */
    public static Workbook getSheets(String path) throws IOException {
        logger.debug("开始判断表格格式");
        if (null == path || "".equals(path.trim())) {
            return null;
        }
        Workbook workbook;
        try {
            workbook = new XSSFWorkbook(path);
        } catch (Exception e) {
            logger.debug("导入表格异常,异常原因: " + e);
            workbook = new HSSFWorkbook(new FileInputStream(new File(path)));
        }
        return workbook;
    }

    /**
     * 文件名编码
     *
     * @param fileName 文件名
     * @param request  请求
     * @return 编码后的文件名
     * @throws UnsupportedEncodingException
     */
    private static String getEncodeName(String fileName, HttpServletRequest request) throws UnsupportedEncodingException {
        return URLEncoder.encode(fileName, "UTF-8");
    }

    /**
     * 关闭流操作
     *
     * @param workbook excel工作簿对象
     * @param os       输出流
     */
    public static void close(Workbook workbook, OutputStream os) {
        try {
            logger.debug("关闭workbook、OutputStream");
            if (null != workbook) {
                workbook.close();
            }
            if (null != os) {
                os.close();
            }
        } catch (IOException e) {
            logger.warn("关闭异常,异常原因: " + e);
        }
    }

    /**
     * 获取导出文件后缀名类型
     * @param flag true：高版本xlsx  false：低版本xls
     * @param fileName 文件名
     * @param request 请求
     * @param response 响应
     * @return 0表格后缀名为xls  1表格后缀名为xlsx
     * @throws IOException
     */
    public static long getSuffixType(Boolean flag, String fileName, HttpServletRequest request,
                                     HttpServletResponse response) throws IOException{
        // 如果数据为空，或小于指定条数时，为xls格式，否则为xlsx格式
        if (flag){
            logger.debug("导出格式后缀名【.xlsx】");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.addHeader("Content-Disposition", "attachment; filename=\""
                    + ExcelUtils.getEncodeName(getFileName(fileName) + ".xlsx" , request) + "\"");

            return ExcelUtils.EXCEL_XLSX;
        } else {
            logger.debug("导出格式后缀名【.xls】");
            response.setContentType("application/vnd.ms-excel");
            response.addHeader("Content-Disposition", "attachment; filename=\""
                    + ExcelUtils.getEncodeName(getFileName(fileName) + ".xls" , request) + "\"");

            return ExcelUtils.EXCEL_XLS;
        }
    }
}
