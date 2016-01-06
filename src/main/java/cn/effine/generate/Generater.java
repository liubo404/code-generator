package cn.effine.generate;

import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

import cn.effine.dao.db;
import cn.effine.model.Table;
import cn.effine.utils.FileUtils;
import cn.effine.utils.MStringUtil;

public class Generater {

	public static final String defaultPackage = "effine";
	public static final String defaultDomainsuffix = "cn";

	/**
	 * 生成所有数据库表对应的MVC文件
	 *
	 * @param DBName
	 *            数据库名
	 * @param outpath
	 *            输出路径
	 */
	public static void generateAll(String DBName, String outpath){
		List<String> tableNameList = db.getTable(DBName);
		List<Table> list = db.getColumn(tableNameList);
		URL url = Thread.currentThread().getClass().getResource("/template");
		if (url == null) {
			System.out.println("url is null");
		} else {
			String filepath = url.getPath();
			
			/*
				if (filepath.startsWith("file:")) {
					if (filepath.length() > 5) {
						filepath = filepath.substring(5);
					}
					filepath = filepath.split("!")[0];
					File file = new File(filepath);
					filepath = file.getParent() + "\\template";
	
				} else {
					System.out.println("is not jar file");
				}
			*/
			
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
		if (url == null) {
			System.out.println("url is null");
		} else {
			String filepath = url.getPath();
			if (filepath.startsWith("file:")) {
				if (filepath.length() > 5) {
					filepath = filepath.substring(5);
				}
				filepath = filepath.split("!")[0];
				File file = new File(filepath);
				filepath = file.getParent() + "\\template";

			} else {
				System.out.println("is not jar file");
			}
			writeOneModelFile(filepath, outpath, tableName);
		}
	}

	private static void writeFile(String templatepath, String filepath, List<Table> list) {
		try {
			String dirName = filepath + File.separator + defaultDomainsuffix + File.separator + Table.domain + File.separator;
			FileUtils.createDir(dirName);
			Map<Integer, String> map = FileUtils.readfile(templatepath, null);
			for (int i = 0; i < map.size(); i++) {
				String template = map.get(i);
				String name = template.substring(template.lastIndexOf(File.separator) + 1);
				String templateName = name.substring(0, name.indexOf("."));
				
				// 创建文件夹
				for (Table tb : list) {
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
					String result = loadTemplate(templatepath, tb, templateName + ".java.vm");
					// 创建文件
					String fileName = null;

					this_folder = this_folder + "/" + templateName;
					if (templateName.equals("model")) {
						fileName = this_folder + "/" + tb.getModelName() + ".java";
					} else if (templateName.equals("queryImpl")) {
						fileName = this_folder + "/" + tb.getModelName() + ".java";
					} else {
						String uptemplateName = MStringUtil.upperCase(templateName, 0);
						fileName = this_folder + "/" + tb.getModelName() + uptemplateName + ".java";
					}
					FileUtils.CreateFile(fileName);
					FileUtils.write(fileName, result);
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 生成一个表对应的文件
	 *
	 * @param templatepath
	 * @param filepath
	 * @param tableName
	 */
	private static void writeOneModelFile(String templatepath, String filepath, String tableName) {

		// 1.读取模板信息，以及创建文件夹
		try {
			String dirName = filepath + "//" + defaultDomainsuffix + "//" + Table.domain + "//";
			FileUtils.createDir(dirName);
			Map<Integer, String> map = FileUtils.readfile(templatepath, null);
			for (int i = 0; i < map.size(); i++) {
				String template = map.get(i);
				String name = template.substring(map.get(i).lastIndexOf("\\") + 1);
				String templateName = name.substring(0, name.indexOf("."));
				// 创建文件夹

				Table table = db.getColumnByOneTable(tableName);

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
				String result = loadTemplate(templatepath, table, templateName + ".java.vm");
				// 创建文件
				String fileName = null;
				if (StringUtils.isNotEmpty(templateName)
						&& templateName.equalsIgnoreCase("queryImpl")) {
					this_folder = this_folder + "/impl";
				} else if (StringUtils.isNotEmpty(templateName)
						&& templateName.equalsIgnoreCase("queryCondition")) {
					this_folder = this_folder + "/condition";
				} else if (StringUtils.isNotEmpty(templateName)
						&& templateName.equalsIgnoreCase("managerImpl")) {
					this_folder = this_folder + "/impl";
				} else if (StringUtils.isNotEmpty(templateName)
						&& templateName.equalsIgnoreCase("daoImpl")) {
					this_folder = this_folder + "/impl";
				} else {
					this_folder = this_folder + "/" + templateName;
				}
				if (templateName.equals("model")) {
					fileName = this_folder + "/" + table.getModelName() + ".java";
				}else {
					String uptemplateName = MStringUtil.upperCase(templateName, 0);
					fileName = this_folder + "/" + table.getModelName() + uptemplateName + ".java";
				}
				FileUtils.CreateFile(fileName);
				FileUtils.write(fileName, result);

			}
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Write file error!");
		}
	}

	/**
	 * 加载模板
	 *
	 * @param templatePath
	 * @param table
	 * @param templateName
	 * @return
	 */
	public static String loadTemplate(String templatePath, Table table, String templateName) {
		String result = "";
		try {
			String fileDir = java.net.URLDecoder.decode(templatePath, "utf-8");
			VelocityEngine velocityEngine = new VelocityEngine();
			Properties properties = new Properties();
			properties.setProperty(velocityEngine.FILE_RESOURCE_LOADER_PATH, fileDir);
			velocityEngine.init(properties); // 初始化

			// 3.把数据填入上下文
			Map<String, Object> cont = new HashMap<String, Object>();
			cont.put("modelName", table.getModelName());
			cont.put("tableName", table.getTableName());
			cont.put("PropertyList", table.getPropertyList());
			cont.put("model", table.getModelName().toLowerCase());
			String packageName = table.getPackageName();
			if (StringUtils.isNotEmpty(packageName)) {
				cont.put("packageName", "." + table.getPackageName());
				cont.put("namespace", table.domain + "/" + table.getPackageName());
			} else {
				cont.put("packageName", table.getPackageName());
				cont.put("namespace", table.domain);
			}
			cont.put("domain", table.domain);
			result = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, templateName, "utf-8", cont);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}