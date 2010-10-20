/*
 * Created on 2009-4-29
 */
package com.cthq.crm.project.common.database.access;

import java.sql.CallableStatement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.cthq.crm.project.common.database.exception.DBRunException;

/**
 * @author 蒋峰
 *
 */
public class SqlQuery extends  JdbcDaoSupport implements ISqlQueryOperation {
	
	/**
	 * 
	 * @param obj
	 * @return
	 */
	protected static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @param obj
	 * @return
	 */
	protected static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			if (((String)obj).trim().length() == 0) {
			    return true;
			}
		}
		return false;
	}
	/**
	 * 
	 * @param obj
	 * @return
	 */
	public static String getString(Object obj) {
		if (isNull(obj)) {
			return null;
		}
		if (obj instanceof String) {
			return ((String)obj).trim();
		}
		if (obj instanceof String[]) {
			return ((String[])obj)[0].trim();
		}
		return null; 
	}
	/**
	 * 获取数据库查询记录集合
	 * @param sql 查询SQL文
	 * @return 返回查询结果一览 如果没有获取到数据库记录 返回的结果集合为NULL
	 * @throws Exception
	 */
	public List query(final String sql) {
		Statement stmt = null;
		ResultSet rest = null;
		int intSearchCount = -1;
		try {
			//创建查询上下文
			stmt = this.getConnection().createStatement();
			rest = stmt.executeQuery(sql);
			int count = 0;
			if (rest == null) {
				return null;
			}
			int size = rest.getFetchSize();
			intSearchCount = size;
			return resultSetToList(rest, -1);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		} finally {
			try {
				if (rest != null) {
					rest.close();
				}
				if (stmt != null) {
					stmt.close();
				}
			} catch(Exception ex) {
				throw new DBRunException(ex.getMessage(), ex, "DB-001");
			}	
		}
	}
	/**
	 * 分段获取数据库的查询结果集合
	 * @param sql 查询SQL文
	 * @param intStart 获取记录的检索的数据位置
	 * @param intMaxCount 最大记录数 如果该参数的值设为-1 表示需要将当前的结果全部获取
	 * @return 返回查询结果一览 如果没有获取到数据库记录 返回的结果集合为NULL
	 * @throws Exception
	 */
	public List query(final String sql,  final int  intStart, final int intMaxCount) throws Exception {
		int resultSetType = ResultSet.TYPE_SCROLL_INSENSITIVE;
		int resultSetConcurrency = ResultSet.CONCUR_READ_ONLY;
		Statement stmt = null;
		ResultSet rest = null;
		List resultList  = new ArrayList(2);
		int intSearchCount = -1;
		try {
			//创建查询上下文
			stmt = this.getConnection().createStatement(resultSetType, resultSetConcurrency);
			rest = stmt.executeQuery(sql);
			int count = 0;
			if (rest == null) {
				return null;
			}     
			if(!rest.last())    {
				return null;
			}
			intSearchCount = rest.getRow();
			int intTmpStart = 0;
			int intRec = 0;
			//查询的记录总数>获取记录数的起始位置
			if (intSearchCount > intStart) {
				//记录数中0或1开始
				if (intStart-1 <= 0) {
					rest.beforeFirst();
				} else {
					rest.absolute(intStart-1);
				}
			} else {
				if (intMaxCount != -1) {
					intTmpStart = intSearchCount - intMaxCount-1;
					if (intTmpStart <= 0) {
						rest.beforeFirst();
					} else {
						rest.absolute(intTmpStart);
					}
				}
			}
			//设置查询结果
			resultList.add(0, resultSetToList(rest, intMaxCount));
			//设置当前查询结果的总记录数
			resultList.add(1, String.valueOf(intSearchCount));
		} catch(Exception ex) {
			throw ex;
		} finally {
			if (rest != null) {
				rest.close();
			}
			if (stmt != null) {
				stmt.close();
			}
		}
		return resultList;
	}
	
	public int execute(String sql)  {
		Statement stmt = null;
		try {
			//创建查询上下文
			stmt = this.getConnection().createStatement();
			return stmt.executeUpdate(sql);
		} catch(Exception ex) {
			throw new DBRunException(ex.getMessage(), ex, "DB-001");
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch(Exception ex) {
				throw new DBRunException(ex.getMessage(), ex, "DB-001");
			}
		}
	}
	
	/**
	 * ResultSet结果集rs转换成List集合
	 * 
	 * @param rs
	 *            ResultSet 待转换的ResultSet
	 * @return List
	 * @throws Exception
	 */
	protected List resultSetToList(ResultSet rs, int intMaxCount) throws Exception {
		if (rs == null) {
			return null;
		}
		List ret = new ArrayList();
		int count = 0;
		while (rs.next()) {
			ret.add(resultSetToMap(rs));
			if (intMaxCount != -1) {
				count = count + 1;
				if (count == intMaxCount) {
					break;
				}
			}
		}
		return ret;
	}
	private Map getProcedureParameter(String name)  throws Exception{
		ResultSet rs = null;
		Map paramListMap = new HashMap();
		try {
		     DatabaseMetaData dbmd = this.getConnection().getMetaData();
		     rs = dbmd.getProcedureColumns(null, null,name, null);
		     ResultSetMetaData rsmd = rs.getMetaData();
		      while(rs.next()) {
		    	  ProcedureParam param = new ProcedureParam();
		    	  param.paramNm = rs.getString("COLUMN_NAME");
		    	  param.columnType =  rs.getInt("COLUMN_TYPE");
		    	  param.paramType = -1;
//		    	  if (rs.getInt("COLUMN_TYPE") == 5) {
//		    		  param.paramNm = "RESULT";
//		    		  param.paramType = 4;
//		    	  }
		    	  //in
		    	  if (rs.getInt("COLUMN_TYPE") == 1) {
		    		  param.paramType = 1;
		    	  }
		    	  //out
		    	  if (rs.getInt("COLUMN_TYPE") == 4) {
		    		  param.paramType = 2;
		    	  }
		    	  //in out
		    	  if (rs.getInt("COLUMN_TYPE") == 2) {
		    		  param.paramType = 3;
		    	  }
		    	  param.dataType = rs.getInt("DATA_TYPE");
		    	  param.paramDbType = rs.getString("TYPE_NAME");
		    	  
		    	  param.sequence = rs.getInt("SEQUENCE");
		    	  paramListMap.put(param.paramNm, param);
		      }
		      rs.close();
		   } catch (Exception ex) {
			   throw new DBRunException(ex.getMessage(), ex, "DB-001");
		   } finally  {
				try {
					if (rs != null) {
						rs.close();
					}
				} catch(Exception ex) {
					
				}
				
			}
		   return paramListMap;
	}
	public int exceuteProcedure(String procedureNm, List paramList) throws Exception {
		Map paramListMap = getProcedureParameter(procedureNm);
		CallableStatement procin = null;
		StringBuffer procSql = new StringBuffer();
		try {
			if (paramList.isEmpty()) {
				procSql.append(" begin ")
					.append(procedureNm + ";")
					.append(" end; ");
				 procin = this.getConnection().prepareCall (procSql.toString());
			} else {
				int size = paramList.size();
				procSql.append(" begin ")
						.append(procedureNm + "(");
				for(int i=0;i< size; i++) {
					if (i == size-1) {
						procSql.append(" ? ) ; ");
					} else {
						procSql.append(" ? , ");
					}
					
				}
				procSql.append(" end; ");
				procin = this.getConnection().prepareCall (procSql.toString());
				
			}  
			Iterator iterator = paramList.iterator();
		    for(;iterator.hasNext();) {
		    	ProcedureParam procParam = (ProcedureParam)iterator.next();
		    	ProcedureParam procInitParam = (ProcedureParam)paramListMap.get(procParam.paramNm);
		    	//
		    	procParam.paramType = procInitParam.paramType;
		    	procParam.dataType = procInitParam.dataType;
		    	//IN PARAMETER
		    	if (procParam.paramType == 1) {
		    		procin.setObject(procParam.paramNm, procParam.paramVal);
		    		continue;
		    	}
		    	//OUT PARAMETER
		    	if (procParam.paramType == 2) {
		    		procin.registerOutParameter(procParam.paramNm, procParam.dataType);
		    		continue;
		    	}
		    	// IN/OUT PARAMETER
		    	if (procParam.paramType == 3) {
		    		procin.setObject(procParam.paramNm, procParam.paramVal);
		    		procin.registerOutParameter(procParam.paramNm, procParam.dataType);
		    		continue;
		    	}
		    }
		    
		    procin.execute();
		    
		    Iterator outiterator = paramList.iterator();
		    for(;outiterator.hasNext();) {
		    	ProcedureParam procParam = (ProcedureParam)outiterator.next();
		    	if (procParam.paramType == 2) {
		    		procParam.paramVal = procin.getObject(procParam.paramNm).toString();
		    		continue;
		    	}
		    	if (procParam.paramType == 3) {
		    		procParam.paramVal = procin.getObject(procParam.paramNm).toString();
		    		continue;
		    	}
		    }	
		    
		} catch(Exception ex) {
				throw new DBRunException(ex.getMessage(), ex, "DB-001");
		}
		
		return 0;
	}
	/**
	 * 把java.sql.ResultSet结果集rs转换成Map集合
	 * 
	 * @param rs
	 *            ResultSet 待转换的ResultSet
	 * @return Map
	 * @throws Exception
	 */
	protected Map resultSetToMap(ResultSet rs) throws Exception {
		if (rs == null) {
			return null;
		}
		ResultSetMetaData meta = rs.getMetaData();
		int count = meta.getColumnCount();
		Map map = new HashMap(count);
		for (int i = 1; i <= count; i++) {	
			map.put(meta.getColumnName(i),rs.getObject(i));			
		}		
		return map;
	}	
	
}

