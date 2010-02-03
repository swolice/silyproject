package cn.com.yumincun;

import java.io.File;

public class DeleteFile {

	public static void main(String[] args) {
		File files = new File("d:\\eclipse\\downsource\\story\\testpic\\");
		if(files.exists()){
			if(files.isDirectory()){
				int temp = 0;
				File[] ff = files.listFiles();
				for (int i = 0; i < ff.length; i++) {
					if(ff[i].isFile()&&ff[i].length()/1024<=13){
						temp ++ ;
						ff[i].delete();
					}
				}
				System.out.println("删除了"+temp+"个24k的文件");
			}
		}
	}
}
