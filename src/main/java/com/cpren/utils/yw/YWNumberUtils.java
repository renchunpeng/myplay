package com.cpren.utils.yw;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @describe 数值校验/转换工具类
 * @date 2017-01-23 18:18
 * @author gao.chenyang
 * @version 4.0
 * @class org.faqrobot.common.utils
 */
public class YWNumberUtils {

	private YWNumberUtils(){}
	private static Random random = new Random();

	/** random_number : 只有数字 */
	public static final int RANDOM_NUMBER = 0;
	/** random_english : 只有英文字符 */
	public static final int RANDOM_ENGLISH = 1;
	/** random_numeng : 数字加英文 */
	public static final int RANDOM_NUMENG = 2;

	/**
	 * 获取比m小的随机数
	 * @date 2016年12月8日 上午11:08:25
	 * @author 孟理  Email:lmeng@iyunwen.com
	 * @version V4.0
	 * @param m
	 * @return int
	 */
	public static int getRandomInt(int m){
		return random.nextInt(m);
	}

	/**
	 * 获取指定类型的指定长度的随机数
	 * @param randomType 类型0-纯数字，1-纯英文（小写），2-数字加英文（小写）
	 * @param length 随机数长度
	 * @author 张荣松
	 * @created 2016-2-24 上午10:34:36
	 * @return String
	 */
	public static String getRandomCode(Integer randomType, Integer length) {
		if (length == null || length <= 0) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		switch (randomType) {
			case RANDOM_NUMBER:
				for (int i = 0; i < length; i++) {
					sb.append((int) (Math.random() * 10));
				}
				break;
			case RANDOM_ENGLISH:
				for (int i = 0; i < length; i++) {
					sb.append((char) ((int) (Math.random() * 26) + 65));
				}
				break;
			case RANDOM_NUMENG:
				for (int i = 0; i < length; i++) {
					int randomInt = (int) (Math.random() * 36);
					if (randomInt < 10) {
						sb.append(randomInt);
					} else {
						sb.append((char) (randomInt + 55));
					}
				}
				break;
			default:
				break;
		}
		return sb.toString().toLowerCase();
	}

	/**
	 * 判断是否是数字
	 * @date 2016年12月13日 上午9:54:32
	 * @author 孟理  Email:lmeng@iyunwen.com
	 * @version V4.0
	 * @param number
	 * @return boolean
	 */
	public static boolean isInt(String number) {
		try {
			Integer.parseInt(number);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @describe 校验字符串是否为整型或浮点型
	 * @author y.you
	 * @date 2018年04月18日 14:16
	 * @version V4.0
	 * @param str 字符
	 * @return true：整型或浮点型；false：字符型
	 */
	public static boolean isNumber(String str) {
		if (YWStringUtils.isBlank(str)) {
			return false;
		}
		int sz = str.length();
		for (int i = 0; i < sz; i++) {
			if ((str.charAt(i) == 45 && i == 0) || (i != 0 && i < (sz - 1) && str.charAt(i) == 46)) {
				continue;
			}
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将数字转换为中文,暂时只支持1-10
	 *
	 * @param number
	 * @return String
	 * @date 2017年1月19日 下午9:49:24
	 * @author 孟理  Email:lmeng@iyunwen.com
	 * @version V4.0
	 */
	public static String convertNumberToString(int number) {
		switch (number) {
			case 1:
				return "一";
			case 2:
				return "二";
			case 3:
				return "三";
			case 4:
				return "四";
			case 5:
				return "五";
			case 6:
				return "六";
			case 7:
				return "七";
			case 8:
				return "八";
			case 9:
				return "九";
			case 10:
				return "十";
			default:
				return "";
		}
	}

	public static boolean isAllNumber(String word) {
		if (YWStringUtils.isBlank(word)) return false;
		out:
		for (int i = 0; i < word.length(); i++) {
			char c = word.charAt(i);
			for (int j = 0; j < cnArr.length; j++) {
				if (c == cnArr[j] || c == cnArr_[j] || c == cnArr__[j]) {
					continue out;
				}
			}
			return false;
		}
		return true;
	}

	/**
	 * 完成4位数转换
	 *
	 * @param num
	 * @return
	 */
	public static String trans(int num) {
		String[] numeric = new String[]{"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
		StringBuilder builder = new StringBuilder();
		builder.append(numeric[num / 1000]).append("千")//
				.append(numeric[num / 100 % 10]).append("百")//
				.append(numeric[num / 10 % 10]).append("十")//
				.append(numeric[num % 10]);
		//去掉了零千....
		int index = -1;
		while ((index = builder.indexOf(numeric[0], index + 1)) != -1) {
			if (index < builder.length() - 1) {
				builder.deleteCharAt(index + 1);
			}
		}
		//去掉双零
		index = 0;
		while ((index = builder.indexOf("零零", index)) != -1) {
			builder.deleteCharAt(index);
		}

		if (builder.length() > 1) {
			//去掉开头的零
			if (builder.indexOf(numeric[0]) == 0) {
				builder.deleteCharAt(0);
			}
			//去掉末尾的零
			if (builder.indexOf(numeric[0]) == builder.length() - 1) {
				builder.deleteCharAt(builder.length() - 1);
			}

		}
		//把开头一十换成十
		if (builder.indexOf("一十") == 0) {
			builder.deleteCharAt(0);
		}
		return builder.toString();
	}

	private static char[] cnArr = new char[]{'一', '二', '三', '四', '五', '六', '七', '八', '九'};
	private static char[] cnArr_ = new char[]{'壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};
	private static char[] cnArr__ = new char[]{'1', '2', '3', '4', '5', '6', '7', '8', '9'};
	// 注:上面三个静态量的数组个数必须一致!!!!

	private static char[] chArr = new char[]{'十', '百', '千', '万', '亿'};

	/**
	 * 中文數字转阿拉伯数组【十万九千零六十  --> 109060】
	 *
	 * @return
	 */
	public static Integer chineseNumber2Int(String chineseNumber) {
		if (chineseNumber == null) return null;
		if (YWStringUtils.isBlank(chineseNumber)) return null;
		int result = 0;
		int temp = 1;//存放一个单位的数字如：十万
		int count = 0;//判断是否有chArr
		boolean is = false;
		for (int i = 0; i < chineseNumber.length(); i++) {
			boolean b = true;//判断是否是chArr
			char c = chineseNumber.charAt(i);
			for (int j = 0; j < cnArr.length; j++) {//非单位，即数字
				if (c == cnArr[j] || c == cnArr_[j]) {
					if (0 != count) {//添加下一个单位之前，先把上一个单位值添加到结果中
						result += temp;
						count = 0;
					}
					is = true;
					// 下标+1，就是对应的值
					temp = j + 1;
					b = false;
					break;
				}
			}
			//单位十、百、千、万、亿
			if (b) {
				for (int j = 0; j < chArr.length; j++) {
					if (c != chArr[j]) continue;
					switch (j) {
						case 0:
							temp *= 10;
							break;
						case 1:
							temp *= 100;
							break;
						case 2:
							temp *= 1000;
							break;
						case 3:
							temp *= 10000;
							break;
						case 4:
							temp *= 100000000;
							break;
						default:
							break;
					}
					count++;
				}
			}
			if (i == chineseNumber.length() - 1) {//遍历到最后一个字符
				result += temp;
			}
		}
		if (!is) return null;
		return result;
	}

	private static Pattern NUMBER_PATTERN = Pattern.compile("[一二两三四五六七八九壹贰叁肆伍陆柒捌玖123456789]万[一二两三四五六七八九壹贰叁肆伍陆柒捌玖123456789](?!(千|仟|百|佰|十|拾))");
	private static Pattern NUMBER_PATTERN_2 = Pattern.compile("[一二两三四五六七八九壹贰叁肆伍陆柒捌玖123456789][千|仟][一二两三四五六七八九壹贰叁肆伍陆柒捌玖123456789](?!(百|佰|十|拾))");
	private static Pattern NUMBER_PATTERN_3 = Pattern.compile("[一二两三四五六七八九壹贰叁肆伍陆柒捌玖123456789][百|佰][一二两三四五六七八九壹贰叁肆伍陆柒捌玖123456789](?![十|拾])");
	private static Pattern NUMBER_PATTERN_4 = Pattern.compile("[零一二两三四五六七八九壹贰叁肆伍陆柒捌玖]");
	private static Pattern NUMBER_PATTERN_5 = Pattern.compile("(?<=(周|星期))[末天日]");
	private static Pattern NUMBER_PATTERN_6 = Pattern.compile("(?<!(周|星期))0?[0-9]?[十|拾][0-9]?");
	private static Pattern NUMBER_PATTERN_7 = Pattern.compile("0?[1-9][百|佰][0-9]?[0-9]?");
	private static Pattern NUMBER_PATTERN_8 = Pattern.compile("0?[1-9][千|仟][0-9]?[0-9]?[0-9]?");
	private static Pattern NUMBER_PATTERN_9 = Pattern.compile("[0-9]+万[0-9]?[0-9]?[0-9]?[0-9]?");

	/**
	 * 该方法可以将字符串中所有的用汉字表示的数字转化为用阿拉伯数字表示的数字 如"这里有一千两百个人，六百零五个来自中国"可以转化为
	 * "这里有1200个人，605个来自中国" 此外添加支持了部分不规则表达方法 如两万零六百五可转化为20650
	 * 两百一十四和两百十四都可以转化为214 一六零加一五八可以转化为160+158 该方法目前支持的正确转化范围是0-99999999
	 * 该功能模块具有良好的复用性
	 *
	 * @param target 待转化的字符串
	 * @return 转化完毕后的字符串
	 */
	public static String numberTranslator(String target) {
		Matcher matcher = NUMBER_PATTERN.matcher(target);
		StringBuffer buffer = new StringBuffer();
		boolean result = matcher.find();
		while (result) {
			String group = matcher.group();
			String[] numberString = group.split("万");
			int num = 0;
			if (numberString.length == 2) {
				num += wordToNumber(numberString[0]) * 10000 + wordToNumber(numberString[1]) * 1000;
			}
			matcher.appendReplacement(buffer, Integer.toString(num));
			result = matcher.find();
		}
		matcher.appendTail(buffer);
		target = buffer.toString();

		matcher = NUMBER_PATTERN_2.matcher(target);
		buffer = new StringBuffer();
		result = matcher.find();
		while (result) {
			String group = matcher.group();
			String[] s = group.split("千|仟");
			int num = 0;
			if (s.length == 2) {
				num += wordToNumber(s[0]) * 1000 + wordToNumber(s[1]) * 100;
			}
			matcher.appendReplacement(buffer, Integer.toString(num));
			result = matcher.find();
		}
		matcher.appendTail(buffer);
		target = buffer.toString();

		matcher = NUMBER_PATTERN_3.matcher(target);
		buffer = new StringBuffer();
		result = matcher.find();
		while (result) {
			String group = matcher.group();
			String[] s = group.split("百|佰");
			int num = 0;
			if (s.length == 2) {
				num += wordToNumber(s[0]) * 100 + wordToNumber(s[1]) * 10;
			}
			matcher.appendReplacement(buffer, Integer.toString(num));
			result = matcher.find();
		}
		matcher.appendTail(buffer);
		target = buffer.toString();

		matcher = NUMBER_PATTERN_4.matcher(target);
		buffer = new StringBuffer();
		result = matcher.find();
		while (result) {
			matcher.appendReplacement(buffer, Integer.toString(wordToNumber(matcher.group())));
			result = matcher.find();
		}
		matcher.appendTail(buffer);
		target = buffer.toString();

		matcher = NUMBER_PATTERN_5.matcher(target);
		buffer = new StringBuffer();
		result = matcher.find();
		while (result) {
			matcher.appendReplacement(buffer, Integer.toString(wordToNumber(matcher.group())));
			result = matcher.find();
		}
		matcher.appendTail(buffer);
		target = buffer.toString();

		matcher = NUMBER_PATTERN_6.matcher(target);
		buffer = new StringBuffer();
		result = matcher.find();
		while (result) {
			String group = matcher.group();
			String[] numberString = group.split("十|拾");
			int num = 0;
			if (numberString.length == 0) {
				num += 10;
			} else if (numberString.length == 1) {
				int ten = Integer.parseInt(numberString[0]);
				if (ten == 0)
					num += 10;
				else
					num += ten * 10;
			} else if (numberString.length == 2) {
				if (numberString[0].equals(""))
					num += 10;
				else {
					int ten = Integer.parseInt(numberString[0]);
					if (ten == 0)
						num += 10;
					else
						num += ten * 10;
				}
				num += Integer.parseInt(numberString[1]);
			}
			matcher.appendReplacement(buffer, Integer.toString(num));
			result = matcher.find();
		}
		matcher.appendTail(buffer);
		target = buffer.toString();

		matcher = NUMBER_PATTERN_7.matcher(target);
		buffer = new StringBuffer();
		result = matcher.find();
		while (result) {
			String group = matcher.group();
			String[] numberString = group.split("百|佰");
			int num = 0;
			if (numberString.length == 1) {
				int hundred = Integer.parseInt(numberString[0]);
				num += hundred * 100;
			} else if (numberString.length == 2) {
				int hundred = Integer.parseInt(numberString[0]);
				num += hundred * 100;
				num += Integer.parseInt(numberString[1]);
			}
			matcher.appendReplacement(buffer, Integer.toString(num));
			result = matcher.find();
		}
		matcher.appendTail(buffer);
		target = buffer.toString();

		matcher = NUMBER_PATTERN_8.matcher(target);
		buffer = new StringBuffer();
		result = matcher.find();
		while (result) {
			String group = matcher.group();
			String[] s = group.split("千|仟");
			int num = 0;
			if (s.length == 1) {
				int thousand = Integer.parseInt(s[0]);
				num += thousand * 1000;
			} else if (s.length == 2) {
				int thousand = Integer.parseInt(s[0]);
				num += thousand * 1000;
				num += Integer.parseInt(s[1]);
			}
			matcher.appendReplacement(buffer, Integer.toString(num));
			result = matcher.find();
		}
		matcher.appendTail(buffer);
		target = buffer.toString();

		matcher = NUMBER_PATTERN_9.matcher(target);
		buffer = new StringBuffer();
		result = matcher.find();
		while (result) {
			String group = matcher.group();
			String[] s = group.split("万");
			int num = 0;
			if (s.length == 1) {
				int tenthousand = Integer.parseInt(s[0]);
				num += tenthousand * 10000;
			} else if (s.length == 2) {
				int tenthousand = Integer.parseInt(s[0]);
				num += tenthousand * 10000;
				num += Integer.parseInt(s[1]);
			}
			matcher.appendReplacement(buffer, Integer.toString(num));
			result = matcher.find();
		}
		matcher.appendTail(buffer);
		target = buffer.toString();

		return target;
	}

	/**
	 * 方法numberTranslator的辅助方法，可将[零-九]正确翻译为[0-9]
	 *
	 * @param s
	 *            大写数字
	 * @return 对应的整形数，如果不是大写数字返回-1
	 */
	private static int wordToNumber(String s) {
		if (s.equals("零") || s.equals("0"))
			return 0;
		else if (s.equals("一") || s.equals("1") || s.equals("壹"))
			return 1;
		else if (s.equals("二") || s.equals("两") || s.equals("2") || s.equals("贰"))
			return 2;
		else if (s.equals("三") || s.equals("3") || s.equals("叁"))
			return 3;
		else if (s.equals("四") || s.equals("4") || s.equals("肆"))
			return 4;
		else if (s.equals("五") || s.equals("5") || s.equals("伍"))
			return 5;
		else if (s.equals("六") || s.equals("6") || s.equals("陆"))
			return 6;
		else if (s.equals("七") || s.equals("天") || s.equals("日") || s.equals("末") || s.equals("7") || s.equals("柒"))
			return 7;
		else if (s.equals("八") || s.equals("8") || s.equals("捌"))
			return 8;
		else if (s.equals("九") || s.equals("9") || s.equals("玖"))
			return 9;
		else
			return -1;
	}

	/**
	 * 计算百分比保留两位小数
	 *
	 * @author rongKuan
	 * @date 2019/6/25
	 */
	public static String division(BigDecimal num1, BigDecimal num2) {
		String rate = "0.00%";
		if (num1 == null || num2 == null || BigDecimal.ZERO.equals(num2)) {
			return "--";
		}
		StringBuilder format = new StringBuilder("0.00");
		if (!BigDecimal.ZERO.equals(num1)) {
			DecimalFormat dec = new DecimalFormat(format.toString());
			rate = dec.format(num1.divide(num2, 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100))) + "%";
		}
		return rate;
	}

	/**
	 * 转换 百分比
	 *
	 * @author rongKuan
	 * @date 2019/6/25
	 */
	public static String transRate(double num1) {
		String rate;
		StringBuilder format = new StringBuilder("0.00");
		DecimalFormat dec = new DecimalFormat(format.toString());
		rate = dec.format(num1 * 100) + "%";
		while (true) {
			if (rate.equals(format + "%")) {
				format = format.append("0");
				DecimalFormat dec1 = new DecimalFormat(format.toString());
				rate = dec1.format(num1 * 100) + "%";
			} else {
				break;
			}
		}
		return rate;
	}

	/**
	 * 精准加法
	 *
	 * @author rongKuan
	 * @date 2019/7/4
	 */
	public static Double add(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.add(b2).doubleValue();
	}
	/**
	 * 精准减法
	 *
	 * @author rongKuan
	 * @date 2019/7/4
	 */
	public static Double sub(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.subtract(b2).doubleValue();
	}
	/**
	 * 精准乘法
	 *
	 * @author rongKuan
	 * @date 2019/7/4
	 */
	public static Double mul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(Double.toString(v1));
		BigDecimal b2 = new BigDecimal(Double.toString(v2));
		return b1.multiply(b2).doubleValue();
	}
}
