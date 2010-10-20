package com.cthq.crm.report.excel.support;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;

import org.apache.velocity.Template;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.TemplateInitException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.RuntimeSingleton;
import org.apache.velocity.runtime.parser.ParseException;

public class ReportTemplate extends Template {
	private VelocityException errorCondition = null;
	private String vmData = "";
	public void setVmData(String _vm){
		vmData = _vm;
	}
	 public boolean process() throws ResourceNotFoundException, ParseErrorException, IOException {
			rsvc = RuntimeSingleton.getRuntimeServices();
			try {
				rsvc.init();
			} catch (Exception e) {
				e.printStackTrace();
			}
		 try {
             BufferedReader br = new BufferedReader(  new StringReader(vmData));
             data = rsvc.parse( br, name);
             initDocument();
             return true;
         } catch ( ParseException pex ){
             errorCondition =  new ParseErrorException( pex );
             throw errorCondition;
         } catch ( TemplateInitException pex ) {
             errorCondition = new ParseErrorException( pex );
             throw errorCondition;
         } catch( RuntimeException e ){
             throw new RuntimeException("Exception thrown processing Template "+getName(), e);
         } finally {
         }
	}

}
