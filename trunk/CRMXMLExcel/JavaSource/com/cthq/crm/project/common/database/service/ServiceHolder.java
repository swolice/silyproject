/*
 * 创建日期：2009/12/02
 * ServiceFacade.java
 */
package com.cthq.crm.project.common.database.service;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import com.cthq.crm.project.common.database.access.DbBaseDao;


/**
 * 业务访问接口总控转接类
 * @author 蒋峰
 */
public final class ServiceHolder implements IBaseServiceHodler {
	//基本基本数据访问类
	private DbBaseDao baseDao;
	public void setBaseDao(DbBaseDao _dao) {
		this.baseDao = _dao;
	}
	public DbBaseDao getBaseDao() {
		return this.baseDao;
	}
	public ServerParamsContainer getServerParamsContainer() {
		return new ServerParamsContainer();
	}
	
	/* (non-Javadoc)
	 * @see com.cthq.crm.common.buss.service.IBaseService#execute(java.lang.Object, java.lang.String, java.lang.Object[])
	 */
	public  Object execute(Object clazz, String methodNm, Object[] arguments)
			throws Exception {
		try {
			
			//设置业务逻辑访问数据库的基础操作类
			Method setBaseDaoMethod = clazz.getClass().getMethod("setBaseDao", new Class[]{DbBaseDao.class});
			Object objs[] = new Object[]{baseDao};
			setBaseDaoMethod.invoke(clazz, objs);
			
			//创建业务访问处理类
			Method createDaoMethod = clazz.getClass().getMethod("createBizDao", null);
			createDaoMethod.invoke(clazz, null);
			Method method = null;
			if (arguments == null || arguments.length == 0) {
				method = clazz.getClass().getDeclaredMethod(methodNm,null);
				return method.invoke(clazz, null);
			} else {

				Class[] parameterTypes = getParameterTypes(arguments);
				method = clazz.getClass().getDeclaredMethod(methodNm,parameterTypes);
				 return method.invoke(clazz, arguments);
			}
		} catch(Exception ex) {
			throw ex;
		}	
	}
	/**
	 * 获取参数的类型
	 * @param arguments
	 * @return
	 */
	private Class[] getParameterTypes(Object[] arguments) {
		Class[] parameterTypes = new Class[arguments.length];
		for(int i=0;i < arguments.length;i++) {
			if (arguments[i].getClass().equals(Integer.class)) {
				parameterTypes[i] = int.class;
				continue;
			}
			if (arguments[i].getClass().equals(Float.class)) {
				parameterTypes[i] = float.class;
				continue;
			}
			if (arguments[i].getClass().equals(Boolean.class)) {
				parameterTypes[i] = boolean.class;
				continue;
			}
			if (arguments[i].getClass().equals(Byte.class)) {
				parameterTypes[i] = byte.class;
				continue;
			}
			if (arguments[i].getClass().equals(Double.class)) {
				parameterTypes[i] = double.class;
				continue;
			}
			if (arguments[i].getClass().equals(ArrayList.class)) {
				parameterTypes[i] = List.class;
				continue;
			}
			if (arguments[i].getClass().equals(HashMap.class)) {
				parameterTypes[i] = Map.class;
				continue;
			}
			if (arguments[i].getClass().equals(Map.class)) {
				parameterTypes[i] = Map.class;
				continue;
			}
			if (arguments[i].getClass().equals(Hashtable.class)) {
				parameterTypes[i] = Map.class;
				continue;
			}
			parameterTypes[i] = arguments[i].getClass();
		}
		return parameterTypes;
	}
	/* (non-Javadoc)
	 * @see com.cthq.crm.common.core.db.service.IBaseServiceHodler#execute(java.lang.Object, java.lang.String, com.cthq.crm.common.core.db.service.ServerParamsContainer)
	 */
	public Object execute(Object clazz,  ServerParamsContainer _svrParamsContainer) throws Exception {
		Object[] arguments = new Object[0];
		if (_svrParamsContainer.isEmpty()) {
			 return execute(clazz, _svrParamsContainer.getMethodNm(), new Object[0]);
		} else {
			int i=_svrParamsContainer.getParamsCount();
			arguments = new Object[_svrParamsContainer.getParamsCount()];
			for(; !_svrParamsContainer.isEmpty();) {
				--i;
				arguments[i] = _svrParamsContainer.pop();
			}
		}
		 return execute(clazz, _svrParamsContainer.getMethodNm(), arguments);
	}
}
