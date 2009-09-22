/*
 * Created on 2009-9-15
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dephub;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author sily
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class TestSplit {

	public static void main(String[] args) {
//		String file = "E:/testbig";
//
//		List list = read(file, "utf-8");
//		System.out.println((String)list.get(0));
//		System.out.println((String)list.get(1));
		String ss = "e:/ILH_SIInfo_YYYYMMDDXXX.NNNN";
		System.out.println(read(ss, "utf-8"));
//		Pattern pattern = Pattern.compile("\\d{11}\\.\\d{4}");
//		boolean b = pattern.matcher(ss).matches();
//		if(b){
//			System.out.println(ss);
//		}
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		Date date = null;
//		try {
//			date = sdf.parse("20090911");
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		
		
		//文件名日期
//		Calendar c = Calendar.getInstance();
//		//当前日期
//		Calendar c1 = Calendar.getInstance();
//		c.setTime(date);
//		c1.setTime(new Date());
//		c1.add(Calendar.DATE,1);
//		if(c.after(c1)){
//			System.out.println("文件名日期大于当前日期+1");
//		}else{
//			System.out.println("文件名日期小于或等于当前日期+1");
//		}
//		c1.add(Calendar.DATE,-8);
//		System.out.println(c1.get(Calendar.DAY_OF_MONTH));
//		System.out.println(c.get(Calendar.DAY_OF_MONTH));
//		if(!c.after(c1)){
//			System.out.println("文件名日期小于或等于当前日期-7");
//			
//		}else{
//			System.out.println("文件名日期大于当前日期-7");
//		}
		//		try {
		//			File file = new File(s);
		//			long fileLength = file.length();
		//			FileInputStream fi = new FileInputStream(file);
		//			Reader reader = new InputStreamReader(fi,"UTF-8");
		//			int int_file= (int)fileLength;
		//			byte[] b = new byte[100];
		//			char[] c = new char[int_file];
		//			int i = -1;
		//			System.out.println(fileLength);
		//			
		//			
		//			int a = reader.read(c,0,1);
		//			System.out.println(c[0]);
//		for (int i = 0; i < 100000; i++) {
//			//System.out.println(i);
//			//reader.read(c,i+1,1);
//			String s = "SI000" + i + "||浙江鸿程" + i + "|zjhc|2|571|2|1|2|备注信息"
//					+ i + "\n\r";
//			method1(file,s);
//		}
//		String s = "46003000|4600300|0001|100000";
//		method1(file,s);
		//			for (i = int_file-1; i > -1; i--) {
		//				System.out.println(c[i]);
		//			}

		//			BufferedReader br = new BufferedReader(reader);
		//			String aaa = "";
		//			while ((aaa = br.readLine()) != null) {
		//				System.out.println(aaa);
		//			}
		//		} catch (FileNotFoundException e) {
		//			e.printStackTrace();
		//		} catch (IOException e) {
		//			e.printStackTrace();
		//		}
		//		String[] ss = s.split("\\" + ",");
		//		for (int i = 0; i < ss.length; i++) {
		//			System.out.println(ss[i]);
		//		}
		//		System.out.println(ss.length);

		//		String ss = "ILH_SIInfo_YYYYMMDDXXX.NNNN";
		//		
		//		String[] array = ss.split("_");
		//		
		//		System.out.println(array[2]);
		//		

	}
	/**
	 * 返回文件头list[0]，和文件尾list[1]
	 */
	public static List read(String filename, String charset) {
		List list = new ArrayList(2);
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(filename, "r");
			long len = rf.length();
			if(len == 0){
				return null;
			}
			long start = rf.getFilePointer();
			long nextend = start + len - 1;
			String line;
			rf.seek(0);
			// 当文件指针退至文件开始处，输出第一行
			String fileHeader = new String(rf.readLine().getBytes("iso-8859-1"), charset);
			list.add(0,fileHeader);
			rf.seek(nextend);
			int c = -1;
			while (nextend > start) {
				c = rf.read();
				if (c == '\n' || c == '\r') {
					line = rf.readLine();
					if (line != null&&!"".equals(line.trim())) {
						String fileEnd = new String(line.getBytes("iso-8859-1"), charset);
						list.add(1,fileEnd);
						return list;
					}
					nextend--;
					rf.seek(nextend);
					continue;
				}
				nextend--;
				rf.seek(nextend);
			}
			list.add(1,"");
			return list;
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
		return null;
	}

	/**
	 * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void method1(String file, String conent) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, true)));
			out.write(conent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 追加文件：使用FileWriter
	 * 
	 * @param fileName
	 * @param content
	 */
	public static void method2(String fileName, String content) {
		try {
			// 打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName, true);
			writer.write(content);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 追加文件：使用RandomAccessFile
	 * 
	 * @param fileName
	 *            文件名
	 * @param content
	 *            追加的内容
	 */
	public static void method3(String fileName, String content) {
		try {
			// 打开一个随机访问文件流，按读写方式
			RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			// 将写文件指针移到文件尾。
			randomFile.seek(fileLength);
			randomFile.writeBytes(content);
			randomFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
