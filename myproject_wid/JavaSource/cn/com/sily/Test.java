/*
 * Created on 2009-8-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package cn.com.sily;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * @author a
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class Test {

	public static void main(String[] args) {
		try {
			Reader read = new FileReader("e:/link.txt");
			BufferedReader br = new BufferedReader(read);
			String s[] = new String[400];
			String temp = "";
			int i = 0;
			while ((temp = br.readLine()) != null) {
//				if (temp.indexOf("href=") != -1) {
//					System.out.println(temp.replaceAll("\t", ""));
//				}
				System.out.println(temp.replaceAll("\">第[0-9]{3}集",""));
				s[i++] = temp;
			}
			//System.out.println(s.length);
//			for (int j = s.length-1; j > 0; j--) {
//				if (s[j] != null) {
//					//System.out.println(">第"+j+"集");
//					System.out.println(s[j].replaceAll("href=\"",""));
//				}
//			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
