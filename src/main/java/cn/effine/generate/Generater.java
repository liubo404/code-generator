package cn.effine.generate;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import cn.effine.dao.DatabaseFactory;
import cn.effine.model.Table;
import cn.effine.utils.CommonUtils;
import cn.effine.utils.FileUtils;
import cn.effine.utils.StringCustomUtils;
import cn.effine.utils.TemplateUtils;

public class Generater {

	/**
	 * 生成所有数据库表对应的MVC文件
	 *
	 * @param dbname
	 *            数据库名
	 * @param outpath
	 *            输出路径
	 */
	public static void generateAll(String dbname, String outpath){
		URL url = Thread.currentThread().getClass().getResource("/template");
		if (null != url) {
			String filepath = url.getPath();
			List<String> tableNameList = DatabaseFactory.getTable(dbname);
			List<Table> list = DatabaseFactory.getColumn(tableNameList);
			writeFile(filepath, outpath, list);
		}
	}

	/**
	 * 生成一个数据库表对应的文件
	 *
	 * @param tableName
	 *            表名
	 * @param outpath
	 *            输出路径
	 */
	public static void generateOneModle(String tableName, String outpath){
		URL url = Thread.currentThread().getClass().getResource("/template");
		if(null != url){
			String filepath = url.getPath();
			writeOneModelFile(filepath, outpath, tableName);
		}
	}

	/**
	 * 生成所有表文件
	 *
	 * @param templatepath
	 * @param filepath
	 * @param tableList
	 */
	private static void writeFile(String templatepath, String filepath, List<Table> tableList) {
		String dirName = filepath + File.separator + CommonUtils.defaultDomainsuffix + File.separator + CommonUtils.defaultPackage + File.separator;
		FileUtils.createDir(dirName);
		Map<Integer, String> map = FileUtils.readfile(templatepath, null);
		for (int i = 0; i < map.size(); i++) {
			String template = map.get(i);
			String name = template.substring(template.lastIndexOf(File.separator) + 1);
			String templateName = name.substring(0, name.indexOf("."));
			
			// 创建文件夹
			for (Table table : tableList) {
				String this_folder = dirName;
				// if(StringUtils.isNotEmpty(tb.getPackageName())){//在defaultDomainsuffix//defaultPackage//下面添加数据库提取的一层_..
				// this_folder = this_folder +tb.getPackageName();
				// }
				if (StringUtils.isNotEmpty(templateName) && templateName.equalsIgnoreCase("queryImpl")) {
					this_folder = this_folder + "query";
				}
				if (StringUtils.isNotEmpty(templateName) && templateName.equalsIgnoreCase("queryCondition")) {
					this_folder = this_folder + "query";
				}
				if (StringUtils.isNotEmpty(templateName) && templateName.equalsIgnoreCase("managerImpl")) {
					this_folder = this_folder + "manager";
				}
				// 加载模板
				String result = TemplateUtils.loadTemplate(templatepath, table, templateName + ".java.vm");
				// 创建文件
				String fileName = null;
				
				this_folder = this_folder + "/" + templateName;
				if (templateName.equals("model")) {
					fileName = this_folder + "/" + table.getModelName() + ".java";
				} else if (templateName.equals("queryImpl")) {
					fileName = this_folder + "/" + table.getModelName() + ".java";
				} else {
					String uptemplateName = StringCustomUtils.upperCase(templateName, 0);
					fileName = this_folder + "/" + table.getModelName() + uptemplateName + ".java";
				}
				FileUtils.CreateFile(fileName);
				FileUtils.write(fileName, result);
			}
		}
	}

	/**
	 * 生成一个表对应的文件
	 *
	 * @param templatepath
	 *            模板文件目录
	 * @param outpath
	 *            输出目录
	 * @param tableName
	 *            表名
	 */
	private static void writeOneModelFile(String templatepath, String outpath, String tableName) {
		// 1.读取模板信息，以及创建文件夹
		String dirName = outpath + "//" + CommonUtils.defaultDomainsuffix + "//" + CommonUtils.defaultPackage + "//";
		FileUtils.createDir(dirName);
		Map<Integer, String> map = FileUtils.readfile(templatepath, null);
		for (int i = 0; i < map.size(); i++) {
			String template = map.get(i);
			String name = template.substring(map.get(i).lastIndexOf("\\") + 1);
			String templateName = name.substring(0, name.indexOf("."));
			// 创建文件夹
			
			Table table = DatabaseFactory.getColumnByOneTable(tableName);
			
			String this_folder = dirName;
			// if(StringUtils.isNotEmpty(table.getPackageName())){
			// this_folder = this_folder +table.getPackageName();
			// }
			if (StringUtils.isNotEmpty(templateName) && templateName.equalsIgnoreCase("queryImpl")) {
				this_folder = this_folder + "query";
			}
			if (StringUtils.isNotEmpty(templateName) && templateName.equalsIgnoreCase("queryCondition")) {
				this_folder = this_folder + "query";
			}
			if (StringUtils.isNotEmpty(templateName) && templateName.equalsIgnoreCase("managerImpl")) {
				this_folder = this_folder + "manager";
			}
			if (StringUtils.isNotEmpty(templateName) && templateName.equalsIgnoreCase("daoImpl")) {
				this_folder = this_folder + "dao";
			}
			// 加载模板
			String result = TemplateUtils.loadTemplate(templatepath, table, templateName + ".java.vm");
			// 创建文件
			String fileName = null;
			if (StringUtils.isNotEmpty(templateName) && templateName.equalsIgnoreCase("queryImpl")) {
				this_folder = this_folder + "/impl";
			} else if (StringUtils.isNotEmpty(templateName) && templateName.equalsIgnoreCase("queryCondition")) {
				this_folder = this_folder + "/condition";
			} else if (StringUtils.isNotEmpty(templateName) && templateName.equalsIgnoreCase("managerImpl")) {
				this_folder = this_folder + "/impl";
			} else if (StringUtils.isNotEmpty(templateName) && templateName.equalsIgnoreCase("daoImpl")) {
				this_folder = this_folder + "/impl";
			} else {
				this_folder = this_folder + "/" + templateName;
			}
			if (templateName.equals("model")) {
				fileName = this_folder + "/" + table.getModelName() + ".java";
			}else {
				String uptemplateName = StringCustomUtils.upperCase(templateName, 0);
				fileName = this_folder + "/" + table.getModelName() + uptemplateName + ".java";
			}
			FileUtils.CreateFile(fileName);
			FileUtils.write(fileName, result);
		}
	}

	
}