package cn.effine;

import cn.effine.generate.Generater;

public class GeneratorTest {

	public static void main(String[] args) {
		
		System.out.println(" --- start --- ");
		
		Generater.generateTableListFile("shopping", null);
//		Generater.generateTable("user");
		
		System.out.println("--- finish --- ");
		
	}
}
