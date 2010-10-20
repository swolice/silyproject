package test;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DbUtil;

import dms.data.Sites;

public class TestJsp {
	public static void main(String[] args) {
		HttpServletRequest request=null;
		HttpServletResponse response=null;

		String sql="select * from sites order by id desc";
		List<Sites> list=DbUtil.getDao().list(sql,null,-1,-1,new Sites());
		if (list!=null&&list.size()>0){
			for (Sites site:list){
			System.out.print("<option value='"+site.getId()+"'>"+site.getSiteName()+"</option>");
			}
		}
		
		
	}

}
