package cn.effine.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import cn.effine.model.Column;
import cn.effine.model.Table;
import cn.effine.utils.TypeConvertUtil;

public class db {

	public static Connection connection = null;
	public static Statement statement = null;


	/**
	 * 获取数据库连接
	 *
	 * @param type
	 *            数据库类型
	 * @param url
	 *            数据库地址URL
	 * @param port
	 *            数据库端口
	 * @param username
	 *            用户名
	 * @param password
	 *            密码
	 */
	private static void getConnection(String type, String url, Integer port, String username, String password) {
		// 驱动程序名
		String driver = null;
		StringBuilder urlstart = new StringBuilder();
		
		// mysql数据库
		if (type.equals("mysql")) {
			driver = "com.mysql.jdbc.Driver";
			urlstart.append("jdbc:mysql://");
			urlstart.append(url);
			urlstart.append(":");
			urlstart.append(port);
			urlstart.append("/");
			urlstart.append("shopping");
			urlstart.append("?characterEncoding=UTF-8");
		}
		try {
			Class.forName(driver);	// 加载驱动程序
			connection = DriverManager.getConnection(urlstart.toString(), username, password);	// 连续数据库
			if (!connection.isClosed()){
				statement = connection.createStatement(); 	// statement用来执行SQL语句
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取数据库所有表名列表
	 *
	 * @param dataBaseName
	 *            数据库名
	 * @return 数据库表名列表
	 */
	public static List<String> getTable(String dataBaseName) {
		getConnection("mysql", "localhost", 3306, "root", "aichuan");
		List<String> list = new ArrayList<String>();
		String useSQL = "use " + dataBaseName;
		String sql = " SHOW TABLES";
		String name = null;
		ResultSet rs = null;
		try {
			statement.executeQuery(useSQL);
			rs = statement.executeQuery(sql);
			while (rs.next()) {
				name = rs.getNString(1);
				name = new String(name.getBytes("ISO-8859-1"), "utf-8");
				list.add(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(null != rs){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	/**
	 * 获取数据库表的列
	 *
	 * @param tableNames
	 *            数据库表名列表
	 * @return
	 */
	public static List<Table> getColumn(List<String> tableNames) {
		List<Table> listTb = new ArrayList<Table>();
		try {
			for (int i = 0; i < tableNames.size(); i++) {
				String tableName = tableNames.get(i);
				if (tableName != null && !tableName.equals("")) {
					String sql = "show columns from " + tableName;
					ResultSet rs = statement.executeQuery(sql);
					int length = 0;
					Table tb = getTableByTableName(tableName);

					List<Column> list = new ArrayList<Column>();
					while (rs.next()) {
						String column_field = null;
						String column_type = null;
						column_field = rs.getString("field");
						column_type = rs.getString("type");
						String pk = rs.getString("key");
						length = getLength(length, column_type);
						column_field = new String(column_field.getBytes("ISO-8859-1"), "utf-8");
						column_type = new String(column_type.getBytes("ISO-8859-1"), "utf-8");
						Column p = new Column();
						p.setType(TypeConvertUtil.getType(column_type));
						p.setName(column_field);
						p.setColumn(getCloumnName(column_field));
						p.setLength(length);
						list.add(p);
					}
					tb.setPropertyList(list);
					listTb.add(tb);
					// rs.close();
				}
			}
			// closeConn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listTb;
	}
	
	public static List<String> getDatabase() {
		List<String> list = new ArrayList<String>();
		try {
			String sql = "SHOW databases";
			ResultSet rs = statement.executeQuery(sql);
			String name = null;
			int order = 1;
			while (rs.next()) {
				name = rs.getNString(order);
				name = new String(name.getBytes("ISO-8859-1"), "utf-8");
				list.add(name);
			}
			// rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public static Table getColumnByOneTable(String tableName) {
		Table tb = null;
		try {
			String sql = "show columns from " + tableName;
			ResultSet rs = statement.executeQuery(sql);
			int length = 0;
			tb = getTableByTableName(tableName);

			List<Column> list = new ArrayList<Column>();
			while (rs.next()) {
				String column_field = null;
				String column_type = null;
				column_field = rs.getString("field");
				column_type = rs.getString("type");
				String pk = rs.getString("key");
				length = getLength(length, column_type);

				column_field = new String(column_field.getBytes("ISO-8859-1"),
						"GB2312");
				column_type = new String(column_type.getBytes("ISO-8859-1"),
						"GB2312");
				Column p = new Column();
				p.setType(TypeConvertUtil.getType(column_type));
				p.setName(column_field);
				p.setColumn(getCloumnName(column_field));
				p.setLength(length);
				list.add(p);
			}
			tb.setPropertyList(list);
			// rs.close();
			// closeConn();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return tb;
	}

	private static int getLength(int length, String column_type) {
		if (column_type.contains("(")) {
			length = Integer.parseInt(column_type.substring(
					column_type.indexOf("(") + 1, column_type.indexOf(")")));
		}
		return length;
	}

	public static Table getTableByTableName(String tableName) {
		Table tb = new Table();
		String[] spTbName = tableName.split("_");
		tb.setTableName(tableName);
		if (spTbName.length > 1) {
			tb.setPackageName(spTbName[0]);
			tb.setModelName(getModelName(spTbName));
		} else {
			tb.setPackageName("");
			tb.setModelName(spTbName[0]);
		}
		return tb;
	}

	public static String getModelName(String[] name) {
		StringBuffer sb = new StringBuffer();
		for (int i = 1; i < name.length; i++) {
			String s = name[i].toLowerCase();
			String index = s.substring(0, 1).toUpperCase();
			String el = s.substring(1);
			sb.append(index + el);
		}
		return sb.toString();
	}

	public static String getCloumnName(String name) {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		return sb.toString();
	}
}
