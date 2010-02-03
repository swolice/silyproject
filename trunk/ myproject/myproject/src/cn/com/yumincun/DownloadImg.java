package cn.com.yumincun;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadImg {

	static String str = "http://thehardcorelounge.com/free/reality/india/image/";
	
	public static void main(String[] args) {
		URL url = null;
		try {
			url = new URL(str);
			URI uri = url.toURI();
			File file = new File(uri);
			if(file.isDirectory()){
				File[] ff = file.listFiles();
				for(File f : ff){
					System.out.println(f.getAbsolutePath());
				}
			}
//			URLConnection c = url.openConnection();
//			c.connect();
//			InputStream is = c.getInputStream();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
