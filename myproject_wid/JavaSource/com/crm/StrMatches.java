/*
 * Created on 2009-10-20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.crm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author sily
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class StrMatches {

	public static void main(String[] args) {
//		String str = "12345.00";
//		System.out.println(str.matches("[0-9]+[.]?[0-9]*"));
		
//		Pattern pattern = Pattern.compile("\\d{3}");
//		if(pattern.matcher("123").matches()){
//			System.out.println("true");
//		}else{
//			System.out.println("false");[^ \f\n\r\t\v]。
//		}
		
//		Pattern pattern = Pattern.compile("([^|]*\\|){9}[^|]*");
//		Pattern pattern1 = Pattern.compile("([^|\\s]+\\|){4}[^|\\s]+");
//		Pattern pattern2 = Pattern.compile("([^|]*\\|){4}[^|]*");
//		String siInfoRow = "46003000|111|0006|12321中文|1";
//		if(!pattern1.matcher(siInfoRow).matches()){
//			System.out.println("true");
//		}else{
//			System.out.println("false");
//		}
		
		String ss = "sfas";
		System.out.println(ss.substring(1,6));
//		
//		String siInfoRowAttr[] = siInfoRow.split("\\|");
//		if(siInfoRow.endsWith("|")&&siInfoRowAttr.length != 9){
//			
//			System.out.println("true   " + siInfoRowAttr.length);
//		}
		
//		int a = readFileHeaderAndEnd("e:/ILH_SIInfo_YYYYMMDDXXX.NNNN","UTF-8");
//		
//		System.out.println(a);
	}
	
	
	private static int readFileHeaderAndEnd(String filename, String charset) {
		int lines = 0;
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(filename, "r");
			long len = rf.length();
			long start = rf.getFilePointer();
			long nextend = start + len - 1;
			String line = "";
			rf.seek(nextend);
			int c = -1;
			while (nextend > start) {
				c = rf.read();
				if (c == '\n' || c == '\r') {
					line = rf.readLine();
					if (line != null && !"".equals(line.trim())) {
						lines++;
					}
					nextend--;
					rf.seek(nextend);
					continue;
				}
				nextend--;
				rf.seek(nextend);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rf != null)
					rf.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lines;
	}
}
