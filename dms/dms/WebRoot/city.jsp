<div class="city_list" id="city_list" style="display: none;" onmouseover="this.style.display='block';"  onmouseout="this.style.display='none';">
  	<%
		String sql = "select c1.id,c1.city_name,t1.num from city c1,"
				+ "(select c.id,count(t.id) as num from city c,info t WHERE c.id=t.city and t.end_time>now() group by c.id) t1 "
				+ "where c1.id=t1.id order by c1.sort_order ";
		java.util.Date now=new java.util.Date ();
		java.util.List list=util.DbUtil.getDao().list(sql, null,-1,-1);

			int city=2;

			try{
				city=Integer.parseInt(request.getParameter("city"));
			}catch(Exception e){
				try{
					city=Integer.parseInt((String)request.getSession().getAttribute("SESSION_CITY"));
				}catch(Exception e1){
					city=2;
				}
			}

		
		if (list!=null)
		for (int i=0;i<list.size();i++){
		Object o=list.get(i);
		Object[] objs=(Object[])o;
		Number id=(Number)objs[0];
		String name=(String)objs[1];
		Number num=(Number)objs[2];
		if (city==id.intValue()){
		%>
		<%=name%> <span class='count'>(<%=num%>)</span>&nbsp;
		<%
	  }else{
	  %>
	  <a href="index.do?method=list&city=<%=id%>"><%=name%><span class='count'>(<%=num%>)</span></a>&nbsp;
		<%
		}
		if (i%5==4) out.println("<br/>");		
    }%>
  </div>