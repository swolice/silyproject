/*
 * 创建日期：2009/12/02
 * CommonDao.java
 */
package com.cthq.crm.project.common.database.service;

import com.cthq.crm.project.common.database.access.DbBaseDao;


/**
 * 基本业务数据库访问类
 * @author 蒋峰
 */
public  abstract class BizCommonDao implements IBaseDao {
	protected DbBaseDao baseDao;
	

	/* (non-Javadoc)
	 * @see com.cthq.crm.report.common.buss.dao.IBaseDao#setBaseDao(com.cthq.crm.report.common.buss.dao.BaseDao)
	 */
	public void setBaseDao(DbBaseDao _dao) {
		baseDao = _dao;
	}
	
}
