package cn.com.yumincun;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

public class Download {

	public static void main(String[] args) {
		String str = "http://fang.kuxun.cn/search.php?t=Rent&from=%E5%8C%97%E4%BA%AC&q=&cat2=&cat3=%E4%B8%AA%E4%BA%BA&pricel=&priceh=&district=%E6%B5%B7%E6%B7%80&zone=&fromid=&sort=&start=1";
		if(checkFile(str)){
			outFile(str);
			return;
		}
		URL url = null;
		try {
			url = new URL(str);
			URLConnection c = url.openConnection();
			c.connect();
			InputStream is = c.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(is));
			String strhtml = "";
			while (in.readLine() != null) {
				strhtml += in.readLine();
			}
			String lianjie[] = strhtml.split("<");
			for (int i = 0; i < lianjie.length; i++) {
				String istr = lianjie[i].trim();
				int href = istr.indexOf("href=");
				int src = istr.indexOf("src=");
				int script = istr.indexOf("script");
				if((href == -1)&&(src == -1))continue;
				if(script != -1)continue;
				//System.out.println(istr);
				String temp = "";
				if (href != -1) {
					//System.out.println(istr);
					String addstr = "";
					if(istr.indexOf(">")!=-1 && istr.indexOf(">")>href+5){
						addstr = istr.substring(href + 5, istr.indexOf(">"));
					}
					temp = addstr;
					if (addstr.indexOf(" ") != -1) {
						int aa = addstr.indexOf(" ");
						temp = addstr.substring(0, aa);
					}
					if (temp.startsWith("\"")) {
						//System.out.println(temp);
						temp = temp.substring(1, temp.length()-1);
					}
				} 
				if (src != -1) {
					//System.out.println(istr);
					String addstr = "";
					//System.out.println(istr);
					if(istr.indexOf(">")!=-1&&istr.indexOf(">")>src+4){
						addstr = istr.substring(src + 4, istr.indexOf(">"));
					}
					
					temp = addstr;
					if (addstr.indexOf(" ") != -1) {
						temp = addstr.substring(0, addstr.indexOf(" "));
					}
					if (temp.startsWith("\"")) {
						temp = temp.substring(1, temp.length()-1);
					}
				}
				if (temp.equals("")||temp.equals("#"))continue;
				
				if(temp.startsWith("/")){
					if(str.indexOf("/",7)!=-1){
						String basepath = str.substring(0, str.indexOf("/",7));
						temp = basepath + temp;
					}else{
						temp = str + temp;
					}
				}else if (!temp.startsWith("http://")) {
					int mk = 0;
					if(temp.lastIndexOf("/")!= -1){
						mk = temp.substring(temp.lastIndexOf("/")).indexOf(".");
					}else{
						mk = temp.indexOf(".");
					}
					if(str.endsWith("/")){
						temp = str.substring(0,str.lastIndexOf("/")+1) + temp;
					}else if(mk != -1){
						temp = str.substring(0,str.lastIndexOf("/")+1) + temp;
					}else{
						temp = str + "/" + temp;
					}
				}
//				if(checkFile(temp))
//					outFile(temp);
				System.out.println(temp);
			}
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void outFile(String url) {
		try {
			URL u = new URL(url);
			URLConnection c = u.openConnection();
			c.connect();
			InputStream is = c.getInputStream();
			String filename = url.substring(url.lastIndexOf("/") + 1);
			filename = "d:\\sql\\" + filename;
			OutputStream os = new FileOutputStream(filename);
			int tmp = 0;
			while ((tmp = is.read()) != -1) {
				os.write(tmp);
			}
			os.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkFile(String url){
		String str = url.substring(url.lastIndexOf("/"));
		boolean b1 = str.endsWith(".jpg");
		boolean b2 = str.endsWith(".jpeg");
		boolean b3 = str.endsWith(".gif");
		boolean b4 = str.endsWith(".bmp");
		if(b1||b2||b3||b4){
			System.out.println(url);
			return true;
		}else{
			return false;
		}
	}
}
