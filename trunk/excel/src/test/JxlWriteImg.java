/*
 * Created on 2011-10-11
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

import java.io.File;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import jxl.Workbook;
import jxl.write.WritableImage;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/** 
 * jxl 插入图片(图像格式只支持png) 
 * @author Michael sun 
 */  
public class JxlWriteImg {  
    /** 
     *  
     * @param filePath 
     */  
    private void writeImg(String filePath) throws Exception {  
  
        OutputStream os = null;  
        try {  
            String imgPath = "d:/我的桌面/icon.png";  
//            os = new FileOutputStream(filePath);
            Workbook wb = Workbook.getWorkbook(new File(filePath));
            WritableWorkbook wwb = Workbook.createWorkbook(new File("d:/我的桌面/test1.xls"), wb);  
            WritableSheet ws = wwb.getSheet(0);  
            ws.setName("write img");
            File imgFile = new File(imgPath);  
  
            // WritableImage(col, row, width, height, imgFile);  
            WritableImage image = new WritableImage(0, 4, 6, 1, imgFile);  
            ws.addImage(image);  
            wwb.write();  
            wwb.close();  
  
        } catch (Exception e) {  
        	e.printStackTrace();
            System.out.println(e);  
        } finally {  
            if (null != os) {  
                os.close();  
            }  
        }  
    }  
  
    /** 
     * @param args 
     * @throws Exception 
     */  
    public static void main(String[] args) throws Exception {  
        String filePath = "d:/我的桌面/test.xls";  
        JxlWriteImg jxlWriteImg = new JxlWriteImg();  
        jxlWriteImg.writeImg(filePath);  
    
        
        
        
    }  
    
    
    
  
}  