
/**
 * @author effine
 * @Date 2016年1月7日  上午11:43:01
 * @email verphen#gmail.com
 * @site http://www.effine.cn
 */

package cn.effine.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import cn.effine.model.Table;

/**
 * 模板工具类
 */
public class TemplateUtils {
	
	/**
	 * 加载模板
	 *
	 * @param templatePath
	 *            模板目录
	 * @param table
	 *            表名
	 * @param templateName
	 *            模板名称
	 * @return 模板渲染的字符串
	 */
	public static String loadTemplate(String templatePath, Table table, String templateName) {
		String fileDir = null;
		try {
			fileDir = URLDecoder.decode(templatePath, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		VelocityEngine velocityEngine = new VelocityEngine();
		Properties properties = new Properties();
		properties.setProperty(velocityEngine.FILE_RESOURCE_LOADER_PATH, fileDir);
		velocityEngine.init(properties); // 初始化
		
		// 3.把数据填入上下文
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("modelName", table.getModelName());
		model.put("tableName", table.getTableName());
		model.put("PropertyList", table.getPropertyList());
		model.put("model", table.getModelName().toLowerCase());
		String packageName = table.getPackageName();
		if (StringUtils.isNotEmpty(packageName)) {
			model.put("packageName", "." + packageName);
			model.put("namespace", Constants.defaultPackage + "/" + packageName);
		} else {
			model.put("packageName", packageName);
			model.put("namespace", Constants.defaultPackage);
		}
		model.put("domain", Constants.defaultPackage);
		return VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "utf-8", model);
	}
}


