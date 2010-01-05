/*
 * Created on 2009-10-14
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.crm;

import java.io.File;

/**
 * @author sily
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TSplit {
	
	public static void main(String[] args) {
//		String str = "01;02;03;";
//		
//		System.out.println(str.split(";")[0] + "--" + str.split(";")[2] + "--" +str.split(";")[1]);
		TSplit ts = new TSplit();
		System.out.print(ts.aa());
		File file = new File(ts.aa());
		if(!file.exists()){
			file.mkdirs();
		}
		
	}
	
	
	public String aa(){
		String tmpdir = this.getClass().getClassLoader().getResource("").getPath();
		String aa =	tmpdir.split("WEB-INF")[0] + "temp/";
		return aa;
	}
}
