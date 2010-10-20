package util;

import comm.SpringFactory;
import dms.dao.BaseDao;

public class DbUtil {

		public static BaseDao getDao(){
			return (BaseDao)SpringFactory.getInstance().getBean("baseDao");
		}
	
}
