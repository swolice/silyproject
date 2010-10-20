package test;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Types;
import java.util.List;

import util.DbUtil;

import logic.SiteLogic;
import comm.SpringFactory;
import dms.data.Sites;

import form.SiteForm;

public class TestEdit {

	public static void main(String[] args) {
//		SiteForm form = new SiteForm();
//		form.setSiteName("aaa11");
//		SiteLogic logic = (SiteLogic) SpringFactory.getInstance().getBean(
//				"siteLogic");
//		try {
//			Sites sites = logic.edit(form);
//			System.out.println(sites.getId());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		try {
//			
//			Types t;
//			String sql="select id from sites ";
//			
//			int i=DbUtil.getDao().count(sql,null);
//			
//			System.out.println(i);
			
			System.out.println(URLDecoder.decode("%7c"));
			
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		
	}

}
