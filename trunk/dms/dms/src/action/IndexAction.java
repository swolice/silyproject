package action;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.IndexLogic;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import util.BeanUtil;

import comm.SpringFactory;
import comm.page.Page;

import dms.data.Comment;
import dms.data.Group;
import dms.data.Info;
import dms.data.Service;
import dms.data.Sites;
import dms.data.Userinfo;

public class IndexAction extends DispatchAction {
	public IndexLogic getLogic() {
		return (IndexLogic) SpringFactory.getInstance().getBean("indexLogic");
	}

	private static int pageSize = 100;

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
		
		List<Group> gList=getLogic().listGroup((long)city);
		request.setAttribute("gList",gList);
		
		request.getSession().setAttribute("SESSION_CITY", "" + city);
		String[] groups=request.getParameterValues("group");
		
		int category = BeanUtil.getInt(request, "category", -1);
		String key = request.getParameter("key");
		int priceDegree = BeanUtil.getInt(request, "pd", 0);
		int discountDegree = BeanUtil.getInt(request, "dd", 0);
		String sort = request.getParameter("sort");
		int sortType = BeanUtil.getInt(request, "sortType", 0);

		request.setAttribute("sortType",sortType);
		
		
		if (category>=0){
		int count = getLogic().count(city, key, category, priceDegree,
				discountDegree,groups);
		int ps = BeanUtil.getInt(request, "pageSize", 100);
		if (ps > 0)
			pageSize = ps;
		Page page = new Page(curPage, pageSize, count);
		request.setAttribute("page", page);
		request.setAttribute("page"+category, page);

		List<Info> list = getLogic().list(city, key, category, priceDegree,
				discountDegree, sort, sortType, page.getStartRow(), pageSize,groups);

		request.setAttribute("list", list);
		request.setAttribute("list"+category, list);
		request.setAttribute("category",category);
		}else{//首页 未选择分类
			for (int i=0;i<4;i++){
				int count = getLogic().count(city, key, i, priceDegree,
						discountDegree,groups);
				int ps = BeanUtil.getInt(request, "pageSize", 9);
				if (ps > 0)
					pageSize = ps;
				Page page = new Page(curPage, pageSize, count);
				request.setAttribute("page"+i, page);

				List<Info> list = getLogic().list(city, key, i, priceDegree,
						discountDegree, sort, sortType, page.getStartRow(), pageSize,groups);

				request.setAttribute("list"+i, list);
			}
		}
		
		return mapping.findForward("list");
		// if (request.getParameter("type") == null) {
		// return mapping.findForward("list");
		// } else {
		// return mapping.findForward("list1");
		// }
	}

	public Boolean isIpAddress(String s) {
		String regex = "(((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))[.](((2[0-4]\\d)|(25[0-5]))|(1\\d{2})|([1-9]\\d)|(\\d))";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(s);
		return m.matches();
	}

	public String getIp(HttpServletRequest request) {
		String address = request.getHeader("X-Forwarded-For");
		if (address != null && isIpAddress(address)) {
			return address;
		}
		return request.getRemoteAddr();

	}

	public ActionForward promote(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {
			Long id = BeanUtil.getLong(request, "id", 0l);

			if (id > 0) {
				Info info = getLogic().promote(id, getIp(request));
				BeanUtil.writeString(response, "" + info.getPromoteCount());
			}
		} catch (Exception e) {
			BeanUtil.writeError(response, e.getMessage());
		}

		return null;
	}

	public ActionForward mapView(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String scurPage = request.getParameter("pageNo");
		int curPage = 1;
		try {
			curPage = Integer.parseInt(scurPage);
		} catch (Exception ex) {
			curPage = 1;
		}

		int category = BeanUtil.getInt(request, "category", -1);
		String key = request.getParameter("key");
		int priceDegree = BeanUtil.getInt(request, "pd", 0);
		int discountDegree = BeanUtil.getInt(request, "dd", 0);
		String sort = request.getParameter("sort");
		int sortType = BeanUtil.getInt(request, "sortType", 0);
		String[] groups=request.getParameterValues("group");

		int mlat = BeanUtil.getInt(request, "mlat", 0);
		int mlon = BeanUtil.getInt(request, "mlon", 0);
		int nlat = BeanUtil.getInt(request, "nlat", 0);
		int nlon = BeanUtil.getInt(request, "nlon", 0);

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
		
		List<Group> gList=getLogic().listGroup((long)city);
		request.setAttribute("gList",gList);

		int count = getLogic().countMap(city, key, category, priceDegree,
				discountDegree,groups);
		int ps = BeanUtil.getInt(request, "pageSize", 0);
		if (ps > 0)
			pageSize = ps;
		Page page = new Page(curPage, pageSize, count);

		request.setAttribute("page", page);

		request.getSession().setAttribute("SESSION_CITY", "" + city);

		List<Info> list = getLogic().listMap(city, key, category, priceDegree,
				discountDegree, sort, sortType, page.getStartRow(), pageSize,0,0,0,0,groups);

		request.setAttribute("list", list);
		return mapping.findForward("mapView");
		// if (request.getParameter("type") == null) {
		// return mapping.findForward("list");
		// } else {
		// return mapping.findForward("list1");
		// }
	}

	public ActionForward rangeSearch(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String scurPage = request.getParameter("pageNo");
		int curPage = 1;
		try {
			curPage = Integer.parseInt(scurPage);
		} catch (Exception ex) {
			curPage = 1;
		}
		String[] groups=request.getParameterValues("group");

		int category = BeanUtil.getInt(request, "category", -1);
		String key = BeanUtil.getUtfString(request, "key"); // request.getParameter("key");
		int priceDegree = BeanUtil.getInt(request, "pd", 0);
		int discountDegree = BeanUtil.getInt(request, "dd", 0);
		String sort = request.getParameter("sort");
		int sortType = BeanUtil.getInt(request, "sortType", 0);

		int mlat = BeanUtil.getDouble(request, "mlat", 0.0d).intValue();
		int mlon = BeanUtil.getDouble(request, "mlon", 0.0d).intValue();
		int nlat = BeanUtil.getDouble(request, "nlat", 0.0d).intValue();
		int nlon = BeanUtil.getDouble(request, "nlon", 0.0d).intValue();

		System.out.println(mlat + "," + mlon);
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

		List<Group> gList=getLogic().listGroup((long)city);
		request.setAttribute("gList",gList);
		
		int count = getLogic().count(city, key, category, priceDegree,
				discountDegree, mlat, mlon, nlat, nlon,groups);
		int ps = BeanUtil.getInt(request, "pageSize", 0);
		if (ps > 0)
			pageSize = ps;
		Page page = new Page(curPage, pageSize, count);

		request.setAttribute("page", page);

		request.getSession().setAttribute("SESSION_CITY", "" + city);

		List<Info> list = getLogic().list(city, key, category, priceDegree,
				discountDegree, sort, sortType, page.getStartRow(), pageSize,
				mlat, mlon, nlat, nlon,groups);

		request.setAttribute("list", list);
		return mapping.findForward("mapView1");
		// if (request.getParameter("type") == null) {
		// return mapping.findForward("list");
		// } else {
		// return mapping.findForward("list1");
		// }
	}

	public ActionForward detail(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long id = BeanUtil.getLong(request, "id", 0l);
		Info info = getLogic().get(id);

		getLogic().click(id);
		
		int city=2;
		
//		try {
//			city = Integer.parseInt((String) request.getSession()
//					.getAttribute("SESSION_CITY"));
//		} catch (Exception e1) {
//			city = 2;
//		}
		try{
		Service service=getLogic().getCityService(info.getCity().longValue(),info.getSiteId().longValue() ) ;
		request.setAttribute("service", service);
		}catch(Exception e){
			
		}
		
		request.setAttribute("obj", info);
		return mapping.findForward("detail");
	}

	public ActionForward site(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long id = BeanUtil.getLong(request, "id", 0l);
		Sites site = getLogic().getSite(id);

		request.setAttribute("obj", site);
		
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
		
		
		try{
			Service service=getLogic().getCityService((long)city,site.getId() ) ;
//			System.out.println("11111111111111*********************************service:"+service);
			request.setAttribute("service", service);
			}catch(Exception e){
				e.printStackTrace();
			}
		
		
		double[] ss=getLogic().getStar((long)city,id);
		
		if (ss!=null&&ss.length==4){
			request.setAttribute("cityStar",ss[0]);
			request.setAttribute("cityView",ss[1]);
			request.setAttribute("star",ss[2]);
			request.setAttribute("view",ss[3]);
		}

		String scurPage = request.getParameter("pageNo");
		int curPage = 1;
		try {
			curPage = Integer.parseInt(scurPage);
		} catch (Exception ex) {
			curPage = 1;
		}

		int count = getLogic().countBySite(id);
		int ps = BeanUtil.getInt(request, "pageSize", 0);
		if (ps > 0)
			pageSize = ps;
		Page page = new Page(curPage, pageSize, count);

		request.setAttribute("page", page);

		List<Info> list = getLogic().listBySite(id, page.getStartRow(),
				pageSize);

		request.setAttribute("list", list);
		request.setAttribute("now", new Date());
		return mapping.findForward("site");
	}

	protected Userinfo getUser(HttpSession session) {
		if (session == null)
			return null;
		return (Userinfo) session.getAttribute("SESSION_USER");
	}

	public ActionForward comment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Long id = BeanUtil.getLong(request, "id", 0l);
		int type = BeanUtil.getInt(request, "type", 0);
		String content = BeanUtil.getUtfString(request, "content");
		int viewType=BeanUtil.getInt(request,"viewType",0);

		Userinfo user = getUser(request.getSession());
		try {
			Comment c = getLogic().comment(user, content, type,viewType,id.intValue());
			SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 hh点mm分");

			String s = c.getContent() + "<br>" + sdf.format(c.getCreateTime())
					+ "  " + getLogic().getUserName(c.getUserId().longValue());
			BeanUtil.writeString(response, s);
		} catch (Exception e) {
			e.printStackTrace();
			BeanUtil.writeError(response, e.getMessage());
		}
		return null;
	}

	public ActionForward listComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Long id = BeanUtil.getLong(request, "id", 0l);
		int type = BeanUtil.getInt(request, "type", 0);
		String scurPage = request.getParameter("pageNo");
		int curPage = 1;
		try {
			curPage = Integer.parseInt(scurPage);
		} catch (Exception ex) {
			curPage = 1;
		}
		int count = getLogic().countComment(type, id.intValue());
		int ps = BeanUtil.getInt(request, "pageSize", 0);
		if (ps > 0)
			pageSize = ps;
		Page page = new Page(curPage, pageSize, count);

		request.setAttribute("page", page);
		
		List list = getLogic().listComment(type, id.intValue(), page.getStartRow(),
				pageSize);

		request.setAttribute("list", list);
		return mapping.findForward("listComment");
	}

	public ActionForward city(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Long id = BeanUtil.getLong(request, "id", 2l);
		request.setAttribute("cityId", id);

		List list = getLogic().listCity();
		List list1 = getLogic().listSiteCity(id.intValue());

		request.setAttribute("list", list);
		request.setAttribute("list1", list1);
		return mapping.findForward("nav");
	}

	public ActionForward top(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List list1 = getLogic().topSale(0, 5);
		List list2 = getLogic().topClick(0, 5);

		request.setAttribute("list1", list1);
		request.setAttribute("list2", list2);
		return mapping.findForward("top");
	}

	public ActionForward newComment(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List list1 = getLogic().newComment1(0, 0, 5);
		List list2 = getLogic().newComment1(1, 0, 5);

		request.setAttribute("list1", list1);
		request.setAttribute("list2", list2);

		return mapping.findForward("newComment");
	}

	public ActionForward test(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<Info> list = getLogic().listTest(-1, -1);
		request.setAttribute("list", list);

		List<Comment> list1 = getLogic().listTestInfo(0, 50);
		List<Comment> list2 = getLogic().listTestSites(0, 50);

		request.setAttribute("list1", list1);
		request.setAttribute("list2", list2);
		return mapping.findForward("test");
	}
	
	
	public ActionForward service(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Service> list=getLogic().listService();
		request.setAttribute("list",list);
		
		return mapping.findForward("service");
	}


}
