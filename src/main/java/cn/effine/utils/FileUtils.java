package cn.effine.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {

	public static boolean CreateFile(String destFileName) {
		File file = new File(destFileName);
		if (file.exists()) {
			file.delete();
			return false;
		}
		if (destFileName.endsWith(File.separator)) {
			return false;
		}
		if (!file.getParentFile().exists()) {
			if (!file.getParentFile().mkdirs()) {
				return false;
			}
		}

		try {
			if (file.createNewFile()) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 创建目录
	 *
	 * @param srcDir
	 *            待创建目录
	 * @return Boolean
	 */
	public static boolean createDir(String srcDir) {
		File file = new File(srcDir);
		if(file.exists())
			return true;
		return file.mkdirs(); 
	}

	public static String createTempFile(String prefix, String suffix,
			String dirName) {
		File tempFile = null;
		try {
			if (dirName == null) {
				tempFile = File.createTempFile(prefix, suffix);
				return tempFile.getCanonicalPath();
			} else {
				File dir = new File(dirName);
				if (!dir.exists()) {
					if (!FileUtils.createDir(dirName)) {
						return null;
					}
				}
				tempFile = File.createTempFile(prefix, suffix, dir);
				return tempFile.getCanonicalPath();
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static boolean deletefile(String delpath)
			throws FileNotFoundException, IOException {
		try {

			File file = new File(delpath);
			if (!file.isDirectory()) {
				System.out.println("1");
				file.delete();
			} else if (file.isDirectory()) {
				System.out.println("2");
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "/" + filelist[i]);
					if (!delfile.isDirectory()) {
						System.out.println("path=" + delfile.getPath());
						System.out.println("absolutepath="
								+ delfile.getAbsolutePath());
						System.out.println("name=" + delfile.getName());
						delfile.delete();
					} else if (delfile.isDirectory()) {
						deletefile(delpath + "" + filelist[i]);
					}
				}
				file.delete();

			}

		} catch (FileNotFoundException e) {
			System.out.println("deletefile() Exception:" + e.getMessage());
		}
		return true;
	}

	/**
	 * 读取文件
	 *
	 * @param filepath
	 *            带读取文件目录
	 * @param pathMap
	 * @return
	 */
	public static Map<Integer, String> readfile(String filepath, Map<Integer, String> pathMap){
		if (pathMap == null) {
			pathMap = new HashMap<Integer, String>();
		}
		File file = new File(filepath);
		if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				if (!filelist[i].endsWith(".java.vm")) {
					continue;
				}
				File readfile = new File(filepath + "/" + filelist[i]);
				if (!readfile.isDirectory()) {
					pathMap.put(pathMap.size(), readfile.getPath());

				} else if (readfile.isDirectory()) {
					readfile(filepath + "/" + filelist[i], pathMap);
				}
			}
		}else{
			pathMap.put(pathMap.size(), file.getPath());
		}
		return pathMap;
	}

	public static void write(String file, String context) {
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			output.write(context);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void delAll(String path) throws Exception {
		File f = new File(path);
		if (f.isDirectory()) {
			String[] children = f.list();
			for (String folder : children) {
				String newPath = path + "/" + folder;
				delAll(newPath);
			}
		}
		f.delete();
	}
	
	/**
	 * 根据当前操作系统获取配置的输出路径
	 * 
	 * @return 输出路径
	 */
	public static String getOutpath(){
		String os = System.getProperty("os.name").toLowerCase();
		return os.startsWith("win") ? PropertiesUtils.getProp("outpath.win") : PropertiesUtils.getProp("outpath.linux");
	}
}