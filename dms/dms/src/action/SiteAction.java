package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import util.BeanUtil;

import logic.SiteLogic;
import comm.SpringFactory;
import comm.page.Page;
import dms.data.Sites;
import form.SiteForm;

public class SiteAction extends BmsAction {
	public SiteLogic getLogic() {
		return (SiteLogic) SpringFactory.getInstance().getBean("siteLogic");
	}

	/**
	 * 添加
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward edit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		SiteForm siteForm = (SiteForm) form;

		System.out.println("siteName:"+request.getParameter("siteName"));
		System.out.println("domain:"+request.getParameter("domain"));
		
		System.out.println("siteName:"+siteForm.getSiteName());
		System.out.println("domain:"+siteForm.getDomain());
		
		if (siteForm == null || siteForm.getSiteName() == null
				|| "".equals(siteForm.getSiteName())) {
			request.setAttribute("errMsg", "网站名称必须设置！");
			return mapping.findForward("error");
		}
		
		String path=request.getRealPath("/")+"sites/";
		try {
			Sites cat = getLogic().edit(siteForm,path);
			request.setAttribute("title", "网站添加成功！");
			request.setAttribute("backUrl", "site.do?method=list");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}

	/**
	 * 修改 查找显示
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward view(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Sites sites = getLogic().get(BeanUtil.getLong(request, "id", 0l));
		if (sites == null) {
			request.setAttribute("errMsg", "没有找到该网站！");
			return mapping.findForward("error");
		}
		request.setAttribute("obj", sites);
		return mapping.findForward("view");
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

		List<Sites> list = getLogic().list(page.getStartRow(), pageSize);

		request.setAttribute("list", list);
		return mapping.findForward("list");

	}

	//
	// /**
	// * 修改单个属性 名称，分组
	// * @param mapping
	// * @param form
	// * @param request
	// * @param response
	// * @return
	// * @throws Exception
	// */
	// public ActionForward modify(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// try {
	// String value = BeanUtil.getString(request, "value");
	// String sid = BeanUtil.getString(request, "id");
	// String v = getLogic().edit(sid, value);
	// BeanUtil.writeString(response, v);
	// } catch (Exception e) {
	// // e.printStackTrace();
	// // throw new Exception(e.getMessage());
	// BeanUtil.writeError(response,e.getMessage());
	// }
	//
	// return null;
	// }
	//
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
			Sites site = getLogic().delete(BeanUtil.getLong(request, "id", 0l));
			request.setAttribute("obj", site);
			request.setAttribute("title", "删除成功！");
			request.setAttribute("backUrl", "site.do?method=list");
		} catch (Exception e) {
			request.setAttribute("errTitle", "删除出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}

}