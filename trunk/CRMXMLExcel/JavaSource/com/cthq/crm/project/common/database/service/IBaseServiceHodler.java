/*
 * 创建日期：2009/12/02
 * IBaseService.java
 */
package com.cthq.crm.project.common.database.service;

import com.cthq.crm.project.common.database.access.DbBaseDao;


/**
 * 业务访问接口
 * @author 蒋峰
 */
public interface IBaseServiceHodler {
	public void setBaseDao(DbBaseDao _dao);
	public Object execute(Object clazz, String method,  Object[] arguments) throws Exception;
	public Object execute(Object clazz, ServerParamsContainer _svrParamsContainer) throws Exception;
}
