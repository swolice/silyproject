package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.RuleLogic;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import util.BeanUtil;

import comm.SpringFactory;
import comm.page.Page;
import dms.data.Info;
import dms.data.Rules;
import form.RuleForm;

public class RuleAction extends BmsAction {
	public RuleLogic getLogic() {
		return (RuleLogic) SpringFactory.getInstance().getBean("ruleLogic");
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
		RuleForm ruleForm = (RuleForm) form;

		if (ruleForm == null || ruleForm.getDomainId() == null
				|| ruleForm.getDomainId() == 0) {
			request.setAttribute("errMsg", "网站必须设置！");
			return mapping.findForward("error");
		}
		try {
			Rules rule = getLogic().edit(ruleForm);
			request.setAttribute("title", "网站添加成功！");
			request.setAttribute("backUrl", "rule.do?method=list");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}
	
	public ActionForward editRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
	
		try {
			Long id=BeanUtil.getLong(request,"id", 0l);
			Long domainId=BeanUtil.getLong(request,"domain", 0l);
			Long cityId=BeanUtil.getLong(request,"city", 0l);
			String url=BeanUtil.getString(request,"url");
			String charset=BeanUtil.getString(request,"charset");
			Rules rules=getLogic().editRule(id, domainId, cityId, url, charset);
			
			return detail(mapping,form,request,response);
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

//		return mapping.findForward("success");
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
		Rules rule = getLogic().get(BeanUtil.getLong(request, "id", 0l));
		if (rule == null) {
			request.setAttribute("errMsg", "没有找到该规则！");
			return mapping.findForward("error");
		}
		request.setAttribute("obj", rule);
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
		
		Integer domainId=BeanUtil.getInt(request,"domainId",0);
		Integer city=BeanUtil.getInt(request,"city",0);
		Integer activeFlag=BeanUtil.getInt(request,"activeFlag", 0);
		
		String scurPage = request.getParameter("pageNo");
		int curPage = 1;
		try {
			curPage = Integer.parseInt(scurPage);
		} catch (Exception ex) {
			curPage = 1;
		}

		int count = getLogic().count(domainId,city,activeFlag);
		int ps = BeanUtil.getInt(request, "pageSize", 0);
		if (ps > 0)
			pageSize = ps;
		Page page = new Page(curPage, pageSize, count);

		request.setAttribute("page", page);

		List<Rules> list = getLogic().list(domainId,city,activeFlag,page.getStartRow(), pageSize);

		request.setAttribute("domainId", domainId);
		request.setAttribute("city", city);
		request.setAttribute("activeFlag", activeFlag);
		
		
		request.setAttribute("list", list);
		return mapping.findForward("list");

	}
	
	public ActionForward copy(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Rules rule=getLogic().copy(BeanUtil.getLong(request,"id",0l));
		
		response.sendRedirect("rule.do?method=detail&id="+rule.getId());
		return null;
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
			Rules rule = getLogic().delete(BeanUtil.getLong(request, "id", 0l));
			request.setAttribute("obj", rule);
			request.setAttribute("title", "删除成功！");
			request.setAttribute("backUrl", "rule.do?method=list");
		} catch (Exception e) {
			request.setAttribute("errTitle", "删除出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}

	public ActionForward deleteRule(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Long id=BeanUtil.getLong(request, "id", 0l);
			String fid=BeanUtil.getString(request,"fid");
			Rules rules=getLogic().deleteRule(id, fid);
			
			return detail(mapping,form,request,response);
		} catch (Exception e) {
			request.setAttribute("errTitle", "删除出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}
	}
	
	
	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Rules rule = getLogic().get(BeanUtil.getLong(request, "id", 0l));
			if (rule == null) {
				request.setAttribute("errMsg", "没有找到该规则！");
				return mapping.findForward("error");
			}
			request.setAttribute("obj", rule);
			return mapping.findForward("detail");
		} catch (Exception e) {
			request.setAttribute("errTitle", "查找详细信息出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

	}

	public ActionForward addSpider(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			int ftype = BeanUtil.getInt(request, "ftype", 0);
			Long id = BeanUtil.getLong(request, "id", 0l);
			String fid = BeanUtil.getString(request, "fid");
			int isNew = BeanUtil.getInt(request, "isNew", 1);
			String reg = BeanUtil.getString(request, "reg");

			Rules rules = getLogic().addSpider(id, fid, ftype, isNew, reg);

			return detail(mapping, form, request, response);
		} catch (Exception e) {
			request.setAttribute("errTitle", "添加信息出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

	}

	public ActionForward editSpider(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Long id = BeanUtil.getLong(request, "id", 0l);
			String fid = BeanUtil.getString(request, "fid");
			int isNew = BeanUtil.getInt(request, "isNew", 1);
			String reg = BeanUtil.getString(request, "reg");

			Rules rules = getLogic().editSpider(id, fid, isNew, reg);

			return detail(mapping, form, request, response);
		} catch (Exception e) {
			request.setAttribute("errTitle", "添加信息出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

	}
	
	public ActionForward addSeg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			int ftype = BeanUtil.getInt(request, "ftype", 0);
			Long id = BeanUtil.getLong(request, "id", 0l);
			String fid = BeanUtil.getString(request, "fid");
			int isNew = BeanUtil.getInt(request, "isNew", 1);
			int isLoop = BeanUtil.getInt(request, "isLoop", 1);
			String start = BeanUtil.getString(request, "start");
			String end = BeanUtil.getString(request, "end");

			Rules rules = getLogic().addSeg(id, fid, ftype, isNew, isLoop,
					start, end);

			return detail(mapping, form, request, response);
		} catch (Exception e) {
			request.setAttribute("errTitle", "添加信息出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

	}
	
	public ActionForward editSeg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Long id = BeanUtil.getLong(request, "id", 0l);
			String fid = BeanUtil.getString(request, "fid");
			int isNew = BeanUtil.getInt(request, "isNew", 1);
			int isLoop = BeanUtil.getInt(request, "isLoop", 1);
			String start = BeanUtil.getString(request, "start");
			String end = BeanUtil.getString(request, "end");

			Rules rules = getLogic().editSeg(id, fid, isNew, isLoop,
					start, end);

			return detail(mapping, form, request, response);
		} catch (Exception e) {
			request.setAttribute("errTitle", "添加信息出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

	}

	public ActionForward addValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			int ftype = BeanUtil.getInt(request, "ftype", 0);
			Long id = BeanUtil.getLong(request, "id", 0l);
			String fid = BeanUtil.getString(request, "fid");
			String name = BeanUtil.getString(request, "name");
			String reg = BeanUtil.getString(request, "reg");

			Rules rules = getLogic().addValue(id, fid, ftype, name, reg);

			return detail(mapping, form, request, response);
		} catch (Exception e) {
			request.setAttribute("errTitle", "添加信息出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

	}
	
	public ActionForward editValue(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Long id = BeanUtil.getLong(request, "id", 0l);
			String fid = BeanUtil.getString(request, "fid");
			String name = BeanUtil.getString(request, "name");
			String reg = BeanUtil.getString(request, "reg");

			Rules rules = getLogic().editValue(id, fid, name, reg);

			return detail(mapping, form, request, response);
		} catch (Exception e) {
			request.setAttribute("errTitle", "添加信息出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

	}
	
	
	public ActionForward crawl(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Long id = BeanUtil.getLong(request, "id", 0l);
			List<Info> list= getLogic().crawl(id);
			
			request.setAttribute("list",list);
//			System.out.println("size :" +list.size());
			
			return mapping.findForward("crawlView");
		} catch (Exception e) {
			request.setAttribute("errTitle", "添加信息出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

	}

}