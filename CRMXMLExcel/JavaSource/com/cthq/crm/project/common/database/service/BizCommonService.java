/*
 * Created on 2009-12-2
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cthq.crm.project.common.database.service;

import com.cthq.crm.project.common.database.access.DbBaseDao;

/**
 * @author 蒋峰
 * 业务基础类
 */
public  abstract class BizCommonService implements IBizBaseService{
	protected DbBaseDao baseDao;
	public void setBaseDao(DbBaseDao _dao) {
		this.baseDao = _dao;
	}
}
