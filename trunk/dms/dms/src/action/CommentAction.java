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
import dms.data.Comment;

import logic.CommentLogic;

public class CommentAction extends BmsAction {
	public CommentLogic getLogic() {
		return (CommentLogic) SpringFactory.getInstance().getBean(
				"commentLogic");
	}

	// /**
	// * 添加
	// *
	// * @param mapping
	// * @param form
	// * @param request
	// * @param response
	// * @return
	// * @throws Exception
	// */
	// public ActionForward edit(ActionMapping mapping, ActionForm form,
	// HttpServletRequest request, HttpServletResponse response)
	// throws Exception {
	// CityForm cityForm = (CityForm) form;
	//
	// if (cityForm == null || cityForm.getCityName() == null
	// || "".equals(cityForm.getCityName())) {
	// request.setAttribute("errMsg", "名称必须设置！");
	// return mapping.findForward("error");
	// }
	// try {
	// City city = getLogic().edit(cityForm);
	// request.setAttribute("title", "城市添加成功！");
	// request.setAttribute("backUrl", "city.do?method=list");
	// } catch (Exception e) {
	// e.printStackTrace();
	// request.setAttribute("errMsg", e.getMessage());
	// return mapping.findForward("error");
	// }
	//
	// return mapping.findForward("success");
	// }

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
		Comment city = getLogic().get(BeanUtil.getLong(request, "id", 0l));
		if (city == null) {
			request.setAttribute("errMsg", "没有找到该评论！");
			return mapping.findForward("error");
		}
		request.setAttribute("obj", city);

		request.setAttribute("target", getLogic().commentTarget(city));
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

	public Date getStartTime(String s) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = sdf.parse(s);
			d.setHours(0);
			d.setMinutes(0);
			d.setSeconds(0);
			return d;
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;
	}

	public Date getEndTime(String s) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date d = sdf.parse(s);
			d.setHours(23);
			d.setMinutes(59);
			d.setSeconds(59);
			return d;
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;
	}

	public String dateString(Date d) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			return sdf.format(d);
		} catch (Exception e) {
			return "";
		}
	}

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

		int auditFlag = BeanUtil.getInt(request, "auditFlag", -1);
		int commentType = BeanUtil.getInt(request, "commentType", -1);
		String username = request.getParameter("username");
		String content = request.getParameter("content");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		Date sTime = getStartTime(startTime);
		Date eTime = getEndTime(endTime);

		request.setAttribute("username", username);
		request.setAttribute("content", content);
		request.setAttribute("auditFlag", auditFlag);
		request.setAttribute("commentType", commentType);
		if (sTime != null) {
			request.setAttribute("startTime", dateString(sTime));
		}
		if (eTime != null) {
			request.setAttribute("endTime", dateString(eTime));
		}

		int count = getLogic().count(auditFlag, content, commentType, username,
				sTime, eTime);
		int ps = BeanUtil.getInt(request, "pageSize", 0);
		if (ps > 0)
			pageSize = ps;
		Page page = new Page(curPage, pageSize, count);

		request.setAttribute("page", page);

		List list = getLogic().list(auditFlag, content, commentType, username,
				sTime, eTime, page.getStartRow(), pageSize);

		request.setAttribute("list", list);
		return mapping.findForward("list");

	}

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
			request.setAttribute("backUrl", "comment.do?method=list");
		} catch (Exception e) {
			request.setAttribute("errTitle", "删除出错");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}

	public ActionForward audit(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			int type = BeanUtil.getInt(request, "type", 0);
			if (type == 0)
				getLogic().audit(BeanUtil.getLong(request, "id", 0l));
			else
				getLogic().audit1(BeanUtil.getLong(request, "id", 0l));
			request.setAttribute("title", "操作成功！");
			request.setAttribute("backUrl", "comment.do?method=list");
		} catch (Exception e) {
			request.setAttribute("errTitle", "操作失败");
			request.setAttribute("errMsg", e.getMessage());
			return mapping.findForward("error");
		}

		return mapping.findForward("success");
	}

}
