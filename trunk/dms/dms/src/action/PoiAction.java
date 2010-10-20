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
import dms.data.InfoAddress;
import form.AddressForm;

import logic.PoiLogic;

public class PoiAction extends BmsAction {
	public PoiLogic getLogic() {
		return (PoiLogic) SpringFactory.getInstance().getBean("poiLogic");
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
		AddressForm addrForm = (AddressForm) form;

		try {
			InfoAddress info = getLogic().edit(addrForm);
			request.setAttribute("title", "信息编辑成功！");
			request.setAttribute("backUrl", "poi.do?method=list&infoId="
					+ info.getInfoId());
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
		InfoAddress addr = getLogic().get(BeanUtil.getLong(request, "id", 0l));
		if (addr == null) {
			request.setAttribute("errMsg", "没有找到该地址信息！");
			return mapping.findForward("error");
		}
		request.setAttribute("obj", addr);
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

		Long infoId = BeanUtil.getLong(request, "infoId", 0l);
		request.setAttribute("infoId",infoId);
		int count = getLogic().count(infoId);
		int ps = BeanUtil.getInt(request, "pageSize", 0);
		if (ps > 0)
			pageSize = ps;
		Page page = new Page(curPage, pageSize, count);

		request.setAttribute("page", page);

		List<InfoAddress> list = getLogic().list(infoId, page.getStartRow(),
				pageSize);

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
			InfoAddress addr = getLogic().delete(
					BeanUtil.getLong(request, "id", 0l));
			request.setAttribute("obj", addr);
			request.setAttribute("title", "删除成功！");
			request.setAttribute("backUrl", "poi.do?method=list&infoId="
					+ addr.getInfoId());
		} catch (Exception e) {
			request.setAttribute("errTitle", "删除出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}

}
