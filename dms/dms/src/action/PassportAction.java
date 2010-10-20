package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.IndexLogic;
import logic.PassportLogic;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import comm.SpringFactory;
import dms.data.Info;
import dms.data.Userinfo;
import form.RegForm;

public class PassportAction extends DispatchAction {
	public PassportLogic getLogic() {
		return (PassportLogic) SpringFactory.getInstance().getBean(
				"passportLogic");
	}

	public ActionForward reg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RegForm regForm = (RegForm) form;

		String num = request.getParameter("code");

		if (num == null
				|| request.getSession() == null
				|| !num.equals((String) request.getSession().getAttribute(
						"CheckCode"))) {
			request.setAttribute("errMsg", "验证码错误");
			return mapping.findForward("regerr");
		}

		try {
			Userinfo info = getLogic().reg(regForm);
			request.setAttribute("user", info);
			request.getSession().setAttribute("SESSION_USER", info);

			IndexLogic log = (IndexLogic) SpringFactory.getInstance().getBean(
					"indexLogic");

			int city = 2;

			try {
				city = Integer.parseInt(request.getParameter("city"));
			} catch (Exception e) {
				try {
					city = Integer.parseInt((String) request.getSession()
							.getAttribute("SESSION_CITY"));
				} catch (Exception e1) {
					city = 2;
				}
			}

			request.getSession().setAttribute("SESSION_CITY", "" + city);

			List<Info> list = log.list(city, "", 0, 0, 0, "", 0, 0, 8,new String[]{});

			request.setAttribute("list", list);

		} catch (Exception e) {
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("regerr");
		}

		return mapping.findForward("regok");
	}

	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		RegForm regForm = (RegForm) form;

		try {
			Userinfo info = getLogic().login(regForm);
			request.getSession().setAttribute("SESSION_USER", info);
		} catch (Exception e) {
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("loginerr");
		}

		return mapping.findForward("loginok");
	}

	public ActionForward reset(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String email = request.getParameter("email");
		try {
			if (email != null && email.length() > 0) {
				getLogic().reset(email);
				IndexLogic log = (IndexLogic) SpringFactory.getInstance()
						.getBean("indexLogic");

				int city = 2;

				try {
					city = Integer.parseInt(request.getParameter("city"));
				} catch (Exception e) {
					try {
						city = Integer.parseInt((String) request.getSession()
								.getAttribute("SESSION_CITY"));
					} catch (Exception e1) {
						city = 2;
					}
				}

				request.getSession().setAttribute("SESSION_CITY", "" + city);

				List<Info> list = log.list(city, "", 0, 0, 0, "", 0, 0, 8,new String[]{});

				request.setAttribute("list", list);

			} else {
				request.setAttribute("errMsg", "邮件地址为空！");
				return mapping.findForward("reseterr");
			}
		} catch (Exception e) {
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("reseterr");
		}
		return mapping.findForward("resetok");
	}

}
