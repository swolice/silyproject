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
import dms.data.Service;
import form.ServiceForm;

import logic.ServiceLogic;

public class ServiceAction extends BmsAction {
	public ServiceLogic getLogic() {
		return (ServiceLogic) SpringFactory.getInstance().getBean("serviceLogic");
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
		ServiceForm serviceForm = (ServiceForm) form;

		if (serviceForm == null || serviceForm.getName() == null
				|| "".equals(serviceForm.getName())) {
			request.setAttribute("errMsg", "名称必须设置！");
			return mapping.findForward("error");
		}
		try {
			String path=request.getRealPath("/")+"service/";
			Service service = getLogic().edit(serviceForm,path);
			request.setAttribute("title", "服务添加成功！");
			request.setAttribute("backUrl", "service.do?method=list");
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
		Service service = getLogic().get(BeanUtil.getLong(request, "id", 0l));
		if (service == null) {
			request.setAttribute("errMsg", "没有找到该服务！");
			return mapping.findForward("error");
		}
		request.setAttribute("obj", service);
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

		List list = getLogic().list(page.getStartRow(), pageSize);

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
			request.setAttribute("backUrl", "service.do?method=list");
		} catch (Exception e) {
			request.setAttribute("errTitle", "删除出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}

}
