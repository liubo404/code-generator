/**
 * @author effine
 * @Date 2016年1月7日  上午11:44:10
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.utils;

/** 常量 */
public class Constants {

	public static final String defaultPackage = "effine"; // 默认包名
	public static final String defaultDomainsuffix = "cn"; // 默认域名后缀

	/** 文件输出路径  */
	public static String OUT_PATH = null;
	/** 模板路径 */
	public static String TEMPLATE_PATH = null;

	static {
		OUT_PATH = FileUtils.getOutpath();
		TEMPLATE_PATH = null;
	}
}
