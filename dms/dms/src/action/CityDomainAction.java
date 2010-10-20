package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.BeanUtil;

import comm.SpringFactory;
import comm.page.Page;
import dms.data.SiteCity;
import form.CityDomainForm;

import logic.CityDomainLogic;

public class CityDomainAction extends BmsAction {
	public CityDomainLogic getLogic() {
		return (CityDomainLogic) SpringFactory.getInstance().getBean("cdLogic");
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
		CityDomainForm cityForm = (CityDomainForm) form;

		if (cityForm == null || cityForm.getUrl() == null
				|| "".equals(cityForm.getUrl())) {
			request.setAttribute("errMsg", "网站URL必须设置！");
			return mapping.findForward("error");
		}
		try {
			SiteCity city = getLogic().edit(cityForm);
			request.setAttribute("title", "城市站点添加成功！");
			request.setAttribute("backUrl", "cd.do?method=list");
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
		SiteCity city = getLogic().get(BeanUtil.getLong(request, "id", 0l));
		if (city == null) {
			request.setAttribute("errMsg", "没有找到该网站城市！");
			return mapping.findForward("error");
		}
		request.setAttribute("obj", city);
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

		Long city = BeanUtil.getLong(request, "city", 0l);
		Long domainId = BeanUtil.getLong(request, "domainId", 0l);

		request.setAttribute("city", city);
		request.setAttribute("domainId", domainId);

		int count = getLogic().count(city.intValue(), domainId.intValue());
		int ps = BeanUtil.getInt(request, "pageSize", 0);
		if (ps > 0)
			pageSize = ps;
		Page page = new Page(curPage, pageSize, count);

		request.setAttribute("page", page);

		List list = getLogic().list(city.intValue(), domainId.intValue(),
				page.getStartRow(), pageSize);

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
			getLogic().delete(BeanUtil.getLong(request, "id", 0l));
			request.setAttribute("title", "删除成功！");
			request.setAttribute("backUrl", "cd.do?method=list");
		} catch (Exception e) {
			request.setAttribute("errTitle", "删除出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}
}
