/**
 * @author effine
 * @Date 2015年6月11日  下午3:04:52
 * @email verphen#gmail.com
 */

package cn.effine.utils;

import java.io.File;

/**
 * 本地目录操作类
 */
public class NativeDirUtils {

	/**
	 * 创建目录
	 *
	 * @param pathname
	 *            待创建的目标目录
	 * @return Boolean
	 */
	public static boolean createDir(String pathname) {
		File dir = new File(pathname);
		if (!dir.exists()) {
			// File.separator在不同的操作系统的表现：windows("\")、linux("/")
			if (!pathname.endsWith(File.separator))
				pathname = pathname + File.separator;
			return dir.mkdirs();
		}
		return true;
	}

}
