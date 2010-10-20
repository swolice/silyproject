package com.cthq.crm.project.common.database.access;

public class ProcedureParam {
	//存储工程的参数名称
	public String paramNm;
	//存储过程的参数类型
	public int 	paramType = -1;
	//存储过程的数据类型
	public String paramDbType;
	public String paramVal = "";
	//参数列的类型
	public int columnType = -1;
	//数据参数的数据类型
	public int dataType = -1;
	public int sequence = -1;
}
