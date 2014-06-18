package test;

import generate.generateThing;
import db.db;

public class test {
	
	
	public static void main(String[] args) throws Exception{
		db.initBruce();
//		ConnectServer.init();
//     	generateThing.generateAll("yunlu","D://opt//dir33");
		generateThing.generateOneModle("yl_user", "D://opt//dir88");
	}
	
	
}


