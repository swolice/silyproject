package sample.notice;

import sample.ExcelCellMergesLogic;
import sample.ThreadServerPool;

import com.cthq.crm.excel.write.ExcelSTLWrite;
import com.cthq.crm.report.excel.support.ExcelReportException;

public class testLogic {
	public static void main(String args[]) {
		
		ExcelSTLWrite excelSTLWr = new ExcelSTLWrite();
		  try {
				excelSTLWr.init("G:\\tmp\\payoutNotice.xls", "G:\\tmp\\payoutNoticetest.xls");
			} catch (Exception e) {
				throw new ExcelReportException("initExcel error", e);
			}
		notice01 nt01 = new notice01();	
		nt01.setExcelSTLWrite(excelSTLWr, 0);
		//nt01.process();
		
		notice02 nt02 = new notice02();
		nt02.setExcelSTLWrite(excelSTLWr, 1);
//		/nt02.process();
		
		notice03 nt03 = new notice03();
		nt03.setExcelSTLWrite(excelSTLWr, 2);
//		nt03.process();
		
		notice04 nt04 = new notice04();
		nt04.setExcelSTLWrite(excelSTLWr, 3);
//		nt04.process();		

		notice05 nt05 = new notice05();
		nt05.setExcelSTLWrite(excelSTLWr, 4);
//		nt05.process();	
		
		  ThreadServerPool thread = new ThreadServerPool();
		  thread.run(nt01);
		  thread.run(nt02);
		  thread.run(nt03);
		  thread.run(nt04);
		  thread.run(nt05);
		  
//		  
		  while(true) {
			  if ( nt01.isFinish() && nt02.isFinish() && nt03.isFinish() && nt04.isFinish() && nt05.isFinish() ) {
//				  excelSTLWr.dispose();
				  break;
			  }
			 
		  }
		excelSTLWr.dispose();
	}
}
