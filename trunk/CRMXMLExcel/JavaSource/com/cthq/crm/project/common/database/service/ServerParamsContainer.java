/*
 * Created on 2010-1-31
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.cthq.crm.project.common.database.service;

import java.util.Stack;

/**
 * @author db2admin
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ServerParamsContainer {
	protected Stack methodParamStack = new Stack();
	private String methodNm = "";
	public void put(long _param) {
		methodParamStack.push(new Long(_param));
	}
	public void put(byte _param) {
		methodParamStack.push(new Byte(_param));
	}
	public void put(float _param) {
		methodParamStack.push(new Float(_param));
	}
	public void put(int _param) {
		methodParamStack.push(new Integer(_param));
	}
	public void put(double _param) {
		methodParamStack.push(new Double(_param));
	}
	public void put(boolean _param) {
		methodParamStack.push(new Boolean(_param));
	}
	public void put(Object item) {
		methodParamStack.push(item);
	}

	public Object pop() {
		return methodParamStack.pop();
	}
	public void setMethodNm(String _nm) {
		this.methodNm = _nm;
	}
	public String getMethodNm() {
		return this.methodNm;
	}
	public int getParamsCount() {
		return methodParamStack.size();
	}
	public boolean isEmpty() {
		return this.methodParamStack.isEmpty();
	}
	public void clear() {
		this.methodParamStack.clear();
	}
}
