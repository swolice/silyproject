/** 
 * 文件名：		CheckValidateException.java 
 * 
 * 版本信息: 	v1.0
 * 日期：		2011-6-27 
 * Copyright:  	Copyright(c) 2010
 * Corporation:	2011 
 * Company：		广州正道科技有限公司  
 */
package com.sily.validate;

import java.util.HashMap;
import java.util.Map;

/** 
 * 名称：	CheckValidateException 
 * 描述： 
 * 创建人：	吉仕军
 * 创建时间：	2011-6-27 下午03:37:52
 * 修改人：	
 * 修改时间：	
 * 修改备注： 
 * @version 1.0
 */
public class CheckValidateException extends RuntimeException{

	private String gMessage = null;
	private String validateCode = null;
	private Map checkedInfoMap = new HashMap();
	public CheckValidateException() {
		super();
	}
    public CheckValidateException(String message, Throwable cause,String _code) {
        super(message, cause);
		gMessage = cause.getMessage();
		validateCode = _code;
    }
    public CheckValidateException(Throwable cause,String _code) {
        super(cause);
		gMessage = cause.getMessage();
		validateCode = _code;
    }
	public CheckValidateException(String message, String _code) {
		super(message);
		gMessage = message;
		validateCode = _code;
	}
	public CheckValidateException(String message) {
		super(message);
	}
    public String getMessage() {
    	if (this.gMessage == null) {
    		return "";
    	}
   	 	return this.gMessage;
    }
    public void setValidateCode(String _code) {

    	this.validateCode = _code;
    }
    public String getValidateCode() {
    	if (this.validateCode == null) {
    		return "";
    	}
    	return this.validateCode;
    }
    public String toString(){
    	if (this.gMessage == null) {
    		return "";
    	}
    	return gMessage;
    }
    public void addInfo(String key, String info) {
    	checkedInfoMap.put(key, info);
    }
    public Object getInfo(String key) {
    	return checkedInfoMap.get(key);
    }
}
