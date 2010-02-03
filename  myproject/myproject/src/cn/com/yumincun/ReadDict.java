package cn.com.yumincun;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadDict {

	public static void main(String[] args) {
		String str = "C:\\Downloads\\Excel Cracker\\theargon.lst";
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(str)));
			String ss = "";
			int i=0;
			while ((ss = reader.readLine()) != null) {
				if(!ss.trim().equals("")){
					System.out.println(ss);
					i++;
					if(i==500){
						break;
					}
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
