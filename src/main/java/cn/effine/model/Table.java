package cn.effine.model;

import java.util.List;

/**
 * 数据库表
 */
public class Table {
	
	private String modelName;
	private String tableName;// 表名
	private String packageName;
	private List<Column> propertyList;

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<Column> getPropertyList() {
		return propertyList;
	}

	public void setPropertyList(List<Column> propertyList) {
		this.propertyList = propertyList;
	}

}
