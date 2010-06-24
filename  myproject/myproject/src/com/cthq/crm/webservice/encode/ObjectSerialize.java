/*
 * ObjectSerialize.java
 * 创建日期：2008/08/13
 */
package com.cthq.crm.webservice.encode;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.Serializable;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 将序列化类型的数据转换相应的字节数据流
 * 同时将相应的字节数据流转换为相应的序列化的数据类型
 * @author 蒋峰
 * 
 */
public class ObjectSerialize {
	
	 /**
     * 将序列化的数据转换压缩相应的字节数据流
     * @param valSeri 序列化数据类型
     * @return 序列化数据类型的字节数据
     */
    protected static  byte[] zip(Serializable valSeri)	{
	    if (valSeri == null){
	       return null;
	    }
	    byte valZip[] = null;
	    ByteArrayOutputStream zipByteStrm = null;
	    try{
	        ByteArrayOutputStream valByteStrm = new ByteArrayOutputStream();
	        ObjectOutputStream valObjStrm = new ObjectOutputStream(valByteStrm);
	        valObjStrm.writeObject(valSeri);
	        byte valByte[] = valByteStrm.toByteArray();
	        zipByteStrm = new ByteArrayOutputStream();
	        //字节数据压缩处理
	        GZIPOutputStream zipObj = new GZIPOutputStream(zipByteStrm);
	        zipObj.write(valByte, 0, valByte.length);
	        zipObj.close();
	        valZip = zipByteStrm.toByteArray();
	    } catch(IOException ioe) {
	    	return null;
	    }
	    return valZip;
    }
    
    /**
     * 将序列化字节流转换相应的序列化的数据类型(反序列化处理)
     * @param stream 序列化字节流
     * @return 序列化数据类型
     */
    protected static  Serializable unzip(ByteArrayInputStream stream){
     
        if(stream == null){
        	return null;
        }
        Serializable valSeri = null;
        try {
            GZIPInputStream zipIn = new GZIPInputStream(stream);
            ObjectInputStream valObjStrm = new ObjectInputStream(zipIn);
            valSeri = (Serializable)valObjStrm.readObject();
        } catch(OptionalDataException ode){
        	return null;
        } catch(ClassNotFoundException cne){
        	return null;
        } catch(IOException ioe) {
        	return null;
        }
        return valSeri;
    }
}
