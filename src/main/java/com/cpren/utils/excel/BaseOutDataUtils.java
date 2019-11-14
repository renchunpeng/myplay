package com.cpren.utils.excel;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFSimpleShape;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFSimpleShape;

/**
 * 导出基类
 */
public class BaseOutDataUtils {

	/** 0：表格后缀名为xls */
	public static final long EXCEL_XLS = 0;

	/** 1：表格后缀名为xlsx */
	public static final long EXCEL_XLSX = 1;

	/**
	 * 获取通用版本字体样式（宋体）
	 */
	public static Font getCellFontStyle(Workbook workbook) {
		// 创建字体对象
	    Font font = workbook.createFont();
	    // 设置字号
	    font.setFontHeightInPoints((short) 10);
	    // 设置字体样式
	    font.setFontName("宋体");
	    return font;
	}

	/**
	 * 获取通用版本字体样式（Arial）
	 * @param workbook 工作簿
	 * @author 张媛媛 2018-07-26
	 */
	public static Font getCellFontStyleArial(Workbook workbook) {
		// 创建字体对象
		Font font = workbook.createFont();
		// 设置字号
		font.setFontHeightInPoints((short) 10);
		// 设置字体样式
		font.setFontName("Arial");
		return font;
	}

	/**
	 * 设置title
	 * @param titles 表格title数组
	 * @author zyy 2018/10/19 9:43
	 */
	public static void setTitle(Row row, CellStyle style, String titles[]) {
		for (int i = 0; i < titles.length; i++) {
			Cell cell = row.createCell(i);
			cell.setCellValue(titles[i]);
			cell.setCellStyle(style);
		}
	}

	/**
	 * 获取通用字体样式
	 * @param workbook 工作簿
	 * @return 字体样式对象
	 */
	public static Font getFontStyle(Workbook workbook) {
		// 创建字体对象
		Font font = workbook.createFont();
		// 设置字体颜色
		/*font.setColor(HSSFColor.BLUE_GREY.index);
		// 设置字号
		font.setFontHeightInPoints((short) 16);
		*/
		// 设置字体样式
		font.setFontName("楷体");
		return font;
	}

	/**
	 * 获取通用字体样式
	 * @param workbook 工作簿
	 * @param fontSize 字体大小
	 * @return 字体样式对象
	 */
	public static Font getTitleFontStyle(Workbook workbook, int fontSize) {
		// 创建字体对象
		Font font = workbook.createFont();
		// 设置字号
		font.setFontHeightInPoints((short) fontSize);
		// 设置字体样式
		font.setFontName("楷体");
		return font;
	}

	/**
	 * 获取通用标题样式对象 设置水平居左 设置垂直居中
	 * @param workbook 工作簿
	 * @param font 字体
	 * @return 样式对象
	 */
	public static CellStyle getTitleStyle(Workbook workbook, Font font) {
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setFont(font);
		titleStyle.setAlignment(HorizontalAlignment.CENTER);// 设置水平居左
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);// 设置垂直居中

		// 设置单元格边框样式
		setBorder(titleStyle);
		// 设置单元格边框颜色
		setBorderColor(titleStyle);
		return titleStyle;
	}

	/**
	 * 获取通用标题样式对象 设置水平居中 设置垂直居中
	 * @param workbook 工作簿
	 * @param font 字体
	 * @return 张媛媛 2018-07-26
	 */
	public static CellStyle getTitleStyleCenter(Workbook workbook, Font font) {
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setFont(font);
		/*titleStyle.setAlignment(CellStyle.ALIGN_CENTER);// 设置水平居中
		titleStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 设置垂直居中
		*/
		// 设置单元格边框样式
		setBorder(titleStyle);
		// 设置单元格边框颜色
		setBorderColor(titleStyle);
		return titleStyle;
	}

	/**
	 * 获取通用单数表格样式对象 宋体 设置水平居左 设置垂直居中
	 * @param workbook 工作簿
	 */
	public static CellStyle getSingularCellStyle(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(getCellFontStyle(workbook));
		/*cellStyle.setAlignment(CellStyle.ALIGN_LEFT);// 设置水平居左
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 设置垂直居中
*/
		// 设置单元格边框样式
		setBorder(cellStyle);

		// 设置单元格边框颜色
		setBorderColor(cellStyle);
		return cellStyle;
	}

	/**
	 * 获取通用表格样式对象 字体为Arial 水平居中 垂直居中
	 * @param workbook 工作簿
	 * @author 张媛媛 2018-07-26
	 */
	public static CellStyle getCellStyleArial(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(getCellFontStyleArial(workbook));
		/*cellStyle.setAlignment(CellStyle.ALIGN_CENTER);// 设置水平居中
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 设置垂直居中
*/
		// 设置单元格边框样式
		setBorder(cellStyle);

		// 设置单元格边框颜色
		setBorderColor(cellStyle);
		return cellStyle;
	}

	/**
	 * 设置单元格边框样式
	 */
	private static void setBorder(CellStyle cellStyle){
		/*cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);*/
	}

	/**
	 * 设置单元格边框颜色
	 */
	private static void setBorderColor(CellStyle cellStyle){
		/*cellStyle.setRightBorderColor(HSSFColor.BLUE_GREY.index);
		cellStyle.setLeftBorderColor(HSSFColor.BLUE_GREY.index);
		cellStyle.setTopBorderColor(HSSFColor.BLUE_GREY.index);
		cellStyle.setBottomBorderColor(HSSFColor.BLUE_GREY.index);*/
	}

	/**
	 * 获取通用双数内容单元格样式
	 * @param workbook 工作簿
	 * @author 张媛媛 2018-07-06
	 */
	public static CellStyle getDualCellStyle(Workbook workbook) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setFont(getCellFontStyle(workbook));
		cellStyle.setFillForegroundColor(IndexedColors.LEMON_CHIFFON.getIndex());
		/*cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		cellStyle.setFillBackgroundColor(HSSFColor.GREEN.index);
		cellStyle.setAlignment(CellStyle.ALIGN_LEFT);// 设置水平居中
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);// 设置垂直居中
		// 设置单元格边框样式
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		// 设置单元格边框颜色
		cellStyle.setRightBorderColor(HSSFColor.BLUE_GREY.index);
		cellStyle.setLeftBorderColor(HSSFColor.BLUE_GREY.index);
		cellStyle.setTopBorderColor(HSSFColor.BLUE_GREY.index);
		cellStyle.setBottomBorderColor(HSSFColor.BLUE_GREY.index);*/
		return cellStyle;
	}

	/**
	 * 获取标题行单元格样式
	 * @param workbook 工作簿
	 * @param font 字体
	 * @author 张媛媛 2018-07-06
	 * @return 返回标题行单元格样式
	 */
	public static CellStyle getBigTitleStyle(Workbook workbook, Font font) {
		CellStyle titleStyle = workbook.createCellStyle();
		titleStyle.setFont(font);
		// 设置水平居中
		titleStyle.setAlignment(HorizontalAlignment.CENTER);
		// 设置垂直居中
		titleStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置标题边框样式
		titleStyle.setBorderBottom(BorderStyle.THIN);
		titleStyle.setBorderLeft(BorderStyle.THIN);
		titleStyle.setBorderRight(BorderStyle.THIN);
		titleStyle.setBorderTop(BorderStyle.THIN);
		return titleStyle;
	}


	/**
	 * @Description: 获取单元格格式
	 * @param workbook 工作簿
	 */
	public CellStyle getCellStyle(Workbook workbook) {
		CellStyle style = workbook.createCellStyle();
		/*style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBorderTop(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(CellStyle.BORDER_THIN);
		style.setRightBorderColor(CellStyle.BORDER_THIN);
		style.setAlignment(CellStyle.ALIGN_CENTER);
*/
		return style;
	}

	/**
	 * 生成字体样式
	 */
	public Font getFont(Workbook workbook) {
		Font font = workbook.createFont();
		/*font.setColor(HSSFColor.WHITE.index);
		font.setFontHeightInPoints((short) 12);
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);*/
		return font;
	}

	/**
	 * 设置百分比格式
	 * @param workbook 工作簿
	 * @author 张媛媛 2018-07-25
	 */
	public static CellStyle getPercentStyle(Workbook workbook){

		CellStyle percentStyle = workbook.createCellStyle();
		percentStyle.setFont(getCellFontStyle(workbook));
		/*percentStyle.setAlignment(CellStyle.ALIGN_LEFT);
		percentStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		percentStyle.setBorderBottom(CellStyle.BORDER_THIN);
		percentStyle.setBorderLeft(CellStyle.BORDER_THIN);
		percentStyle.setBorderRight(CellStyle.BORDER_THIN);
		percentStyle.setBorderTop(CellStyle.BORDER_THIN);
		percentStyle.setRightBorderColor(HSSFColor.BLUE_GREY.index);
		percentStyle.setLeftBorderColor(HSSFColor.BLUE_GREY.index);
		percentStyle.setTopBorderColor(HSSFColor.BLUE_GREY.index);
		percentStyle.setBottomBorderColor(HSSFColor.BLUE_GREY.index);
		percentStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00%"));*/
		return percentStyle;
	}

	/**
	 * 画斜线 (由左上到右下的斜线)
	 * @param excelSuffix 版本 0：表格后缀名为xls 1：表格后缀名为xlsx
	 * @param sheet sheet页
	 */
	public static void getPatriarch(long excelSuffix, Sheet sheet, int i, int j){
		if (excelSuffix == EXCEL_XLS){
			// 03版本
			HSSFPatriarch patriarch = (HSSFPatriarch) sheet.createDrawingPatriarch();
			HSSFClientAnchor ab = new HSSFClientAnchor(0, 0, 1023, 255, (short)i, j, (short)i, j);
			HSSFSimpleShape shape1 = patriarch.createSimpleShape(ab);
			shape1.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
			shape1.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID) ;
		} else {
			// 07版本
			XSSFDrawing patriarch = (XSSFDrawing) sheet.createDrawingPatriarch();
			XSSFClientAnchor ab = new XSSFClientAnchor(0, 0, 1023, 255, (short)i, j, (short)i, j);
			XSSFSimpleShape shape1 = patriarch.createSimpleShape(ab);
			shape1.setShapeType(HSSFSimpleShape.OBJECT_TYPE_LINE);
			shape1.setLineStyle(HSSFSimpleShape.LINESTYLE_SOLID);
		}
	}
}
