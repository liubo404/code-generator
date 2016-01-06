package cn.effine.utils;

/**
 * 字符串处理
 */
public class StringCustomUtils {

	/**
	 * 将指定位置字母转换成大写
	 *
	 * @param str
	 *            源字符串
	 * @param order
	 *            待转换字母位置
	 * @return 转换后的字符串
	 */
	public static String upperCase(String str, int order) {
		String upperCase = str.substring(order, order + 1).toUpperCase();
		String pre = str.substring(0, order);
		String remnand = str.substring(order + 1);
		return pre + upperCase + remnand;
	}
}
