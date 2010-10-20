package action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.BeanUtil;

import comm.SpringFactory;
import comm.page.Page;
import dms.data.Info;
import form.InfoForm;

import logic.InfoLogic;

public class InfoAction extends BmsAction {
	public InfoLogic getLogic() {
		return (InfoLogic) SpringFactory.getInstance().getBean("infoLogic");
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
		InfoForm infoForm = (InfoForm) form;

		try {
			Info info = getLogic().edit(infoForm);
			request.setAttribute("title", "信息编辑成功！");
			request.setAttribute("backUrl", "info.do?method=list&p=1");
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
		Info info = getLogic().get(BeanUtil.getLong(request, "id", 0l));
		if (info == null) {
			request.setAttribute("errMsg", "没有找到该团购信息！");
			return mapping.findForward("error");
		}
		request.setAttribute("obj", info);
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

		String stime=request.getParameter("stime");
		String etime=request.getParameter("etime");
		int city=BeanUtil.getInt(request,"city",0);
		int domain=BeanUtil.getInt(request,"domain", 0);
		
		if (request.getParameter("p")!=null){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			stime=sdf.format(new Date());
		}
		
		int count = getLogic().count(stime,etime,city,domain);
		int ps = BeanUtil.getInt(request, "pageSize", 0);
		if (ps > 0)
			pageSize = ps;
		Page page = new Page(curPage, pageSize, count);

		request.setAttribute("page", page);

		List<Info> list = getLogic().list(stime,etime,city,domain,page.getStartRow(), pageSize);

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
			Info info = getLogic().delete(BeanUtil.getLong(request, "id", 0l));
			request.setAttribute("obj", info);
			request.setAttribute("title", "删除成功！");
			request.setAttribute("backUrl", "info.do?method=list&p=1");
		} catch (Exception e) {
			request.setAttribute("errTitle", "删除出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}
	
}
