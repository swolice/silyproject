/*
 * RemoteNetworkException.java
 * 创建日期： 2009-9-30
 *
 */
package com.cthq.crm.report.excel.support;

/**
 * @author 蒋峰
 */
public class ExcelReportException extends RuntimeException {
	//异常的消息
	private String gMessage = "";
	//异常的编号
	private String commonCode = "";
	public ExcelReportException() {
		super();
	}
    public ExcelReportException(String message, Throwable cause,String _code) {
        super(message, cause);
		gMessage = cause.getMessage();
		commonCode = _code;
    }
    public ExcelReportException(Throwable cause,String _code) {
        super(cause);
		gMessage = cause.getMessage();
		commonCode = _code;
    }
    
    public ExcelReportException(String _msg,Throwable cause) {
        super(cause);
		gMessage = _msg;
		commonCode = "";
    }
    /**
     * 
     * @param message
     * @param _code
     */
	public ExcelReportException(String message, String _code) {
		super(message);
		gMessage = message;
		commonCode = _code;
	}
	/**
	 * 错误信息
	 */
    public String getMessage() {
    	if (this.gMessage == null) {
    		return "";
    	}
   	 	return this.gMessage;
    }
    /**
     * 异常编码
     * @param _code
     */
    public void setCommonCode(String _code) {
    	this.commonCode = _code;
    }
    /**
     * 异常编码
     * @return
     */
    public String getCommonCode() {
    	if (this.commonCode == null) {
    		return "";
    	}
    	return this.commonCode;
    }
    /**
     * 
     */
    public String toString(){
    	if (this.gMessage == null) {
    		return "";
    	}
    	return gMessage;
    }

}
