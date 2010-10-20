package comm.dialect;

import java.sql.Types;

import org.hibernate.Hibernate;

public class MySQLDialect extends org.hibernate.dialect.MySQLDialect {
	public MySQLDialect() {
		super();
		registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
	}
}
