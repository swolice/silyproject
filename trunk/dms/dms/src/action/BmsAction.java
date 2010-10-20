package action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.springframework.dao.DataAccessException;

import dms.data.Userinfo;

public class BmsAction extends LoginCheckAction {

	public BmsAction() {
	}

//	protected Userinfo getUser(HttpSession session) {
//		if (session == null)
//			return null;
//		return (Userinfo) session.getAttribute("BMS_USER");
//	}

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		if (getUser(request.getSession()) == null) {
			String url = "/login.jsp";
			response.sendRedirect(url);
			return null;
		}
		
		Userinfo user=getUser(request.getSession());
		if (user==null||!"sharp".equals(user.getUsername())){
			String url = "/login.jsp";
			response.sendRedirect(url);
			return null;
		}

		try {
			return super.execute(mapping, form, request, response);
		} catch (DataAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

}
