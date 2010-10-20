package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.ComplainLogic;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import util.BeanUtil;

import comm.SpringFactory;
import comm.page.Page;

import dms.data.Complain;
import dms.data.Userinfo;
import form.ComplainForm;

public class ComplainAction extends DispatchAction {
	public ComplainLogic getLogic() {
		return (ComplainLogic) SpringFactory.getInstance().getBean(
				"complainLogic");
	}
	
	protected Userinfo getUser(HttpSession session) {
		if (session == null)
			return null;
		return (Userinfo) session.getAttribute("SESSION_USER");
	}

	public ActionForward create(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ComplainForm cForm = (ComplainForm) form;

		if (cForm.getContent()==null||cForm.getContent().trim().length()<1) {
			request.setAttribute("errMsg", "请输入投诉内容！");
			return mapping.findForward("error");
		}
		
		try {
			Userinfo info=getUser(request.getSession());
			Long uid=0l;
			if (info!=null) uid=info.getId();
			
			Complain city = getLogic().create(cForm,uid);
			request.setAttribute("errMsg", "投诉成功！");
//			request.setAttribute("backUrl", "city.do?method=list");
			request.getRequestDispatcher("/feedback.jsp?id="+request.getParameter("infoId")).forward(request,response);
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

//		return mapping.findForward("success");
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
	
}
