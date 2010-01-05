/*
 * 创建日期： 2009-12-31
 *
 */
package reportexcel;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * @author 吉仕军
 *	
 */
public class ExcelImage {

	public static void main(String[] args) {
		File _file = new File("d:/http_imgload.jpg"); //读入文件 
		
		WritableWorkbook wwb = null;
		try {
			BufferedImage src = javax.imageio.ImageIO.read(_file); //构造Image对象 
			int width = src.getWidth(null); //得到源图宽 
			int height = src.getHeight(null); //得到源图长   
			wwb = Workbook.createWorkbook(new File("D:/yy_adsl_quote2.xls"));
			WritableSheet[] ws = wwb.getSheets();
			int a = ws.length;
//			WritableImage wi = new WritableImage(10,10,width,height,_file);
//			ws[0].addImage(wi);
			wwb.write();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				if (null != wwb)
					wwb.close();
			} catch (WriteException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			
			
		}
		
	}
}
