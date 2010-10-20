/*
 * Created on 2010-1-29
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cthq.crm.project.common.database.access;

import java.sql.SQLException;

/**
 * @author db2admin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public interface IDbAccess {
	/**
	 * 释放连接数据库的资源
	 *
	 */
	public void dispose();
}
