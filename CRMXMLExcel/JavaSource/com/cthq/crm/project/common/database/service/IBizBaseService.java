/*
 * IBaseService.java
 * 创建日期：2009/12/02
 */
package com.cthq.crm.project.common.database.service;

import com.cthq.crm.project.common.database.access.DbBaseDao;

/**
 * 业务逻辑接口类
 * @author 蒋峰
 */
public interface IBizBaseService {
	//设置数据库访问的基本类型
	public void setBaseDao(DbBaseDao _dao);
	//设置实际业务数据访问类型
	public void createBizDao();
}
