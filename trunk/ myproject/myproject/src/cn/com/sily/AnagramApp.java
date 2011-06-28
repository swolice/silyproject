/** 
 * 文件名：		AnagramApp.java 
 * 
 * 版本信息: 	v1.0
 * 日期：		2011-6-16 
 * Copyright:  	Copyright(c) 2010
 * Corporation:	2011 
 * Company：		广州正道科技有限公司  
 */
package cn.com.sily;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

/** 
 * 名称：	AnagramApp 
 * 描述： 
 * 创建人：	吉仕军
 * 创建时间：	2011-6-16 上午08:52:33
 * 修改人：	
 * 修改时间：	
 * 修改备注： 
 * @version 1.0
 */
public class AnagramApp {

	static int size;
	static int count;
	static char[] arrChar = new char[100];
	static Set<String> set = new HashSet<String>();
	/**
	 * @param args
	 */

	public static void main(String[] args) {
		String inStr = getInputStr();
		size = inStr.length();
		
		for (int i = 0; i < size; i++) {
			arrChar[i] = inStr.charAt(i);
		}
		doAnagram(size);
		System.out.println(set.size());
	}
	
	private static void doAnagram(int newSize){
		if( 1 == newSize){
			return;
		}else{
			for (int i = 0; i < newSize; i++) {
				doAnagram(newSize - 1);
				if(2 == newSize){
					displayWords();
				}
				rotate(newSize);
			}
		}
	}
	
	static void rotate(int newSize){
		int i;
		int position = size - newSize;
		char temp = arrChar[position];
		for (i = position+1; i < size; i++) {
			arrChar[i-1] = arrChar[i];
		}
		arrChar[i-1] = temp;
	}
	
	
	static void displayWords(){
		if(count<99){
			System.out.print(" ");
		}
		if(count < 9){
			System.out.print(" ");
		}
		System.out.print(++count + " ");
		String word = "";
		for (int i = 0; i < size; i++) {
			System.out.print(arrChar[i]);
			word+=arrChar[i];
		}
		set.add(word);
		System.out.flush();
		if(count % 6 == 0 ){
			System.out.println(" ");
		}
	}
	
	public static String getInputStr(){
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(reader);
		try {
			String reStr = bufferedReader.readLine();
			return reStr;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
