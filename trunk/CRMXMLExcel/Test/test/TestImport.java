/*
 * Created on 2010-6-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package test;

import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

import com.cthq.crm.project.common.database.service.IBaseServiceHodler;
import com.cthq.crm.project.common.database.service.ServerParamsContainer;

/**
 * @author Administrator
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class TestImport extends AbstractDependencyInjectionSpringContextTests {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractSpringContextTests#isFileLoadContext()
	 */
	protected boolean isFileLoadContext() {
		// TODO Auto-generated method stub
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.test.AbstractDependencyInjectionSpringContextTests#getConfigLocations()
	 */
	protected String[] getConfigLocations() {
		// TODO Auto-generated method stub
		return new String[] {
				"F:/crmwork/20100325/CRMProject/OSSWebNEW/finishBatch/test/jdbcSupport.xml",
				"F:/crmwork/20100325/CRMProject/OSSWebNEW/finishBatch/test/sysServiceHodlerConf.xml" };
	}

	public void testFinishBatch() {
//		try {
//			IBaseServiceHodler serviceHolder = (IBaseServiceHodler)this.applicationContext.getBean("serviceHolder");
////			ExcelBaseService service = new ExcelBaseService();
////			ServerParamsContainer serviceContainer = new ServerParamsContainer();
////			serviceContainer.setMethodNm("getFinishBatUUID");
////			serviceHolder.execute(service, serviceContainer);
//		} catch(Exception ex) {
//			ex.printStackTrace();
//		}
	}

	
}
