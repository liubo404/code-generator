package test;

import main.java.com.verphen.db.db;
import main.java.com.verphen.generate.generateThing;

public class test {
	
	
	public static void main(String[] args) throws Exception{
		db.initBruce();
//		ConnectServer.init();
//     	generateThing.generateAll("yunlu","D://opt//dir33");
		generateThing.generateOneModle("yl_user", "D://opt//dir88");
	}
	
	
}


