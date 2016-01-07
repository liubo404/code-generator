package cn.effine.model;

/**
 * 数据库表中的列
 */
public class Column {

	private String name; // 列名
	private String column; //
	private String type; // 类型
	private int length; // 长度

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
}
