package cn.effine.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 本地文件操作类
 */
public class NativeFileUtils {
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
					if (!createNativeDir(dirName)) {
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

	public static boolean deletefile(String delpath) throws FileNotFoundException, IOException {
		try {
			File file = new File(delpath);
			if (!file.isDirectory()) {
				file.delete();
			} else if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(delpath + "/" + filelist[i]);
					if (!delfile.isDirectory()) {
						delfile.delete();
					} else if (delfile.isDirectory()) {
						deletefile(delpath + "" + filelist[i]);
					}
				}
				file.delete();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return true;
	}

	public static Map<Integer, String> readfile(String filepath, Map<Integer, String> pathMap) throws Exception {
		if (pathMap == null) {
			pathMap = new HashMap<Integer, String>();
		}
		File file = new File(filepath);
		if (!file.isDirectory()) {
			pathMap.put(pathMap.size(), file.getPath());
		} else if (file.isDirectory()) {
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
		}
		return pathMap;
	}

	static String readtxt(String file) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String str = "";
		String r = br.readLine();
		while (r != null) {
			str += r;
			r = br.readLine();
		}
		br.close();
		return str;
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
	 * 创建本地目录
	 *
	 * @param pathname
	 *            待创建的目标目录路径
	 * @return Boolean
	 */
	public static boolean createNativeDir(String pathname) {
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