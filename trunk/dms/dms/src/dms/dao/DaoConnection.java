package dms.dao;

import java.sql.Connection;


public interface DaoConnection {
    public Object execute(Connection conn);
}
