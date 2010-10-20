package action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.UserLogic;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.BeanUtil;

import comm.SpringFactory;
import comm.page.Page;


public class UserAction extends BmsAction {
	public UserLogic getLogic() {
		return (UserLogic) SpringFactory.getInstance().getBean("userLogic");
	}



	/**
	 * 列表
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	private static int pageSize = 15;
	
	
	public Date getStartTime(String s){
		try{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date d=sdf.parse(s);
		d.setHours(0);
		d.setMinutes(0);
		d.setSeconds(0);
		return d;
		}catch(Exception e){
//			e.printStackTrace();
		}
		return null;
	}

	
	public Date getEndTime(String s){
		try{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date d=sdf.parse(s);
		d.setHours(23);
		d.setMinutes(59);
		d.setSeconds(59);
		return d;
		}catch(Exception e){
//			e.printStackTrace();
		}
		return null;
	}
	
	public String dateString(Date d){
		try{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(d);
		}catch(Exception e){
			return "";
		}
	}
	
	public ActionForward list(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String scurPage = request.getParameter("pageNo");
		int curPage = 1;
		try {
			curPage = Integer.parseInt(scurPage);
		} catch (Exception ex) {
			curPage = 1;
		}

		String username=request.getParameter("username");
		String email=request.getParameter("email");
		String startTime=request.getParameter("startTime");
		String endTime=request.getParameter("endTime");
		
		Date sTime=getStartTime(startTime);
		Date eTime=getEndTime(endTime);
		
		request.setAttribute("username",username);
		request.setAttribute("email",email);
		if (sTime!=null){
		request.setAttribute("startTime",dateString(sTime));
		}
		if (eTime!=null){
		request.setAttribute("endTime",dateString(eTime));
		}

		int count = getLogic().count(username,email,sTime,eTime);
		int ps = BeanUtil.getInt(request, "pageSize", 0);
		if (ps > 0)
			pageSize = ps;
		Page page = new Page(curPage, pageSize, count);

		request.setAttribute("page", page);

		List list = getLogic().list(username,email,sTime,eTime,
				page.getStartRow(), pageSize);

		request.setAttribute("list", list);
		return mapping.findForward("list");

	}


	/**
	 * 删除
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			getLogic().delete(BeanUtil.getLong(request, "id", 0l));
			request.setAttribute("title", "删除成功！");
			request.setAttribute("backUrl", "user.do?method=list");
		} catch (Exception e) {
			request.setAttribute("errTitle", "删除出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}
	
	public ActionForward suspend(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			getLogic().suspend(BeanUtil.getLong(request, "id", 0l));
			request.setAttribute("title", "挂起成功！");
			request.setAttribute("backUrl", "user.do?method=list");
		} catch (Exception e) {
			request.setAttribute("errTitle", "挂起出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}
	
	
	public ActionForward active(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			getLogic().active(BeanUtil.getLong(request, "id", 0l));
			request.setAttribute("title", "激活成功！");
			request.setAttribute("backUrl", "user.do?method=list");
		} catch (Exception e) {
			request.setAttribute("errTitle", "激活出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}
	
}
