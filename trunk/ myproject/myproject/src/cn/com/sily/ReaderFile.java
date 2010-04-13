package cn.com.sily;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import cn.com.yumincun.SendEmail;

public class ReaderFile {

	public static void main(String[] args) {
		File file = new File("D:\\我的桌面\\黄帝内经原文翻译对照版");

		if (file.isDirectory()) {
			File[] files = file.listFiles();

			for (int i = 0; i < files.length; i++) {
				for (int j = i + 1; j < files.length; j++) {
					if (getHeaderNum(files[i].getName()) > getHeaderNum(files[j]
							.getName())) {
						File temp = files[j];
						files[j] = files[i];
						files[i] = temp;
					}
				}
			}
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i].getName());
			
				try {
					InputStream is = new FileInputStream(files[i]);
					InputStreamReader bis = new InputStreamReader(is);
					BufferedReader br = new BufferedReader(bis);
					StringBuffer sb = new StringBuffer();
					String ss = "";
					while ((ss = br.readLine()) != null) {
						if(!"".equals(ss.trim())){
							sb.append(ss);
						}
					}
					new SendEmail().sendMail(files[i].getName(), sb.toString());
					is.close();
					bis.close();
					br.close();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}	
	}

	public static int getHeaderNum(String ss) {
		String a = ss.substring(0, ss.indexOf("."));
		return Integer.parseInt(a);
	}
	
	/**
	 * 生成小于50k文件
	 * @param files
	 */
	public static void createTxt(File[] files){
		try {
			OutputStream os = new FileOutputStream(new File("d:/hdnj0.txt"),true);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
					os));
			StringBuffer sb = new StringBuffer();
			int j = 0;
			for (int i = 0; i < files.length; i++) {
				System.out.println(files[i].getName());
				
				InputStream is = new FileInputStream(files[i]);
				InputStreamReader bis = new InputStreamReader(is);
				BufferedReader br = new BufferedReader(bis);
				String ss = "";
				while ((ss = br.readLine()) != null) {
					if(!"".equals(ss.trim())){
						sb.append(ss);
						int a = sb.length();
						if(a>20*1024){
							System.out.println(a);
							os = new FileOutputStream(new File("d:/hdnj"+(j++)+"_"+a+".txt"),true);
							bw = new BufferedWriter(new OutputStreamWriter(os));
							sb = new StringBuffer();
							sb.append(ss);
						}
						bw.write(ss);
						bw.write("\r\n");
						bw.flush();
					}
				}
				is.close();
				bis.close();
				br.close();
			}
			os.close();
			bw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
