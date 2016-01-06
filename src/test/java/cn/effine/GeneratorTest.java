package cn.effine;

import cn.effine.generate.Generater;

public class GeneratorTest {

	public static void main(String[] args) {
		
		System.out.println(" --- start --- ");
		
		// Mac
		// Generater.generateAll("shopping", "/study");

		// windows
		Generater.generateAll("shopping", "C://study");
		
		System.out.println("--- finish --- ");
		
	}
}
