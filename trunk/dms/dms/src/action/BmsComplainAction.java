package action;

import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.ComplainLogic;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.BeanUtil;

import comm.SpringFactory;
import comm.page.Page;
import dms.data.Complain;

public class BmsComplainAction extends BmsAction{
	public ComplainLogic getLogic() {
		return (ComplainLogic) SpringFactory.getInstance().getBean(
				"complainLogic");
	}

	private static int pageSize = 15;

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

		int count = getLogic().count();
		int ps = BeanUtil.getInt(request, "pageSize", 0);
		if (ps > 0)
			pageSize = ps;
		Page page = new Page(curPage, pageSize, count);

		request.setAttribute("page", page);

		List list = getLogic().list(page.getStartRow(), pageSize);

		request.setAttribute("list", list);
		return mapping.findForward("list");

	}
	
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Complain c = getLogic().get(BeanUtil.getLong(request, "id", 0l));
		if (c == null) {
			request.setAttribute("errMsg", "没有找到该投诉！");
			return mapping.findForward("error");
		}
		request.setAttribute("obj", c);
		return mapping.findForward("view");
	}

	
	public ActionForward reply(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			getLogic().reply(BeanUtil.getLong(request, "id", 0l),request.getParameter("content"));
			request.setAttribute("title", "回复成功！");
			request.setAttribute("backUrl", "complain.do?method=list");
		} catch (Exception e) {
			request.setAttribute("errTitle", "回复出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}
		
		
		javax.servlet.http.Cookie c;
		
		return mapping.findForward("success");
		
	}
	
	

	
	
}
