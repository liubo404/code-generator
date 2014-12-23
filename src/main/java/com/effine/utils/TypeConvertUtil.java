package com.effine.utils;

public class TypeConvertUtil {

	public static String getType(String type) {
		type = type.toLowerCase();
		if (type.equals("text") || type.substring(0, 7).equals("varchar")
				|| type.equals("longtext"))
			return "String";
		else if (type.equals("decimal"))
			return "BigDecimal";
		else if (type.equals("datetime"))
			return "Date";
		else if (type.equals("date"))
			return "Date";
		else if (type.equals("blob"))
			return "BinaryStream";
		else if (type.substring(0, 3).equals("int")
				|| type.substring(0, 8).equals("smallint")
				|| type.substring(0, 7).equals("tinyint")
				|| type.substring(0, 6).equals("bigint"))
			return "Integer";
		else
			return null;
	}
}
