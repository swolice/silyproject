package dms.dao;

import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

public interface DaoCallback {

	public Object doTrascation(BaseDao dao)  throws DataAccessException ;
}
