package action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import logic.HomeLogic;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import util.BeanUtil;

import comm.SpringFactory;
import comm.page.Page;
import dms.data.Message;
import dms.data.Userinfo;

public class HomeAction extends LoginCheckAction {

	public HomeLogic getLogic() {
		return (HomeLogic) SpringFactory.getInstance().getBean("homeLogic");
	}

	public ActionForward msgCount(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		int count = 0;

		Userinfo info = getUser(request.getSession());
		count = getLogic().msgCount(info.getId());
		BeanUtil.writeString(response, "" + count);

		return null;
	}

	public ActionForward modify(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String password = request.getParameter("password");
			String password1 = request.getParameter("password1");
			String email = request.getParameter("email");

			Userinfo info = getUser(request.getSession());
			getLogic().modify(info.getId(), password, password1, email);
			request.setAttribute("errMsg", "修改成功！");

		} catch (Exception e) {
			request.setAttribute("errMsg", e.getMessage());
		}

		return mapping.findForward("home");
	}
	
	private int pageSize=15;
	public ActionForward comment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			int commentType=BeanUtil.getInt(request,"type",0);
			Userinfo info = getUser(request.getSession());

			String scurPage = request.getParameter("pageNo");
			int curPage = 1;
			try {
				curPage = Integer.parseInt(scurPage);
			} catch (Exception ex) {
				curPage = 1;
			}

			int count = getLogic().countComment(info.getId(), commentType);
			int ps = BeanUtil.getInt(request, "pageSize", 0);
			if (ps > 0)
				pageSize = ps;
			Page page = new Page(curPage, pageSize, count);

			request.setAttribute("page", page);
			
			
			List list=getLogic().listComment(info.getId(), commentType, page.getStartRow(), pageSize);
			List list1=getLogic().newComment(commentType,0, 1);
			
			request.setAttribute("list",list);
			request.setAttribute("list1",list1);
			
				if (commentType==0){
				return mapping.findForward("comment");
				}else{
					return mapping.findForward("comment1");
				}
		} catch (Exception e) {
			request.setAttribute("errMsg", e.getMessage());
		}

		return mapping.findForward("home");
	}
	
	
	public ActionForward sendMsg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Long id=BeanUtil.getLong(request,"tid",0l);
			Userinfo info = getLogic().getUserinfo(id);

			request.setAttribute("target",info);
			return mapping.findForward("sendMsg");
		} catch (Exception e) {
			request.setAttribute("errMsg", e.getMessage());
		}

		return mapping.findForward("sendMsg");
	}
	
	
	public ActionForward send(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			String target=request.getParameter("target");
			String title=request.getParameter("title");
			String content=request.getParameter("content");

			Userinfo info = getUser(request.getSession());
			
			Message m=getLogic().send(info.getId(), target, title, content);

			request.setAttribute("errMsg","发送成功！");
			return mapping.findForward("sendMsg");
		} catch (Exception e) {
			request.setAttribute("errMsg", e.getMessage());
		}

		return mapping.findForward("sendMsg");
	}
	
	
	
	public ActionForward msg(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Userinfo info = getUser(request.getSession());
			
			String scurPage = request.getParameter("pageNo");
			int curPage = 1;
			try {
				curPage = Integer.parseInt(scurPage);
			} catch (Exception ex) {
				curPage = 1;
			}

			int count = getLogic().countMessage(info.getId());
			int ps = BeanUtil.getInt(request, "pageSize", 0);
			if (ps > 0)
				pageSize = ps;
			Page page = new Page(curPage, pageSize, count);

			request.setAttribute("page", page);
			
			List list=getLogic().listMsg(info.getId(), page.getStartRow(), pageSize);

			request.setAttribute("list",list);
			return mapping.findForward("mymsg");
		} catch (Exception e) {
			request.setAttribute("errMsg", e.getMessage());
		}

		return mapping.findForward("mymsg");
	}
	
	
	
	

}
