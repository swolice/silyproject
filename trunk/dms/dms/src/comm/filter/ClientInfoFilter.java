package comm.filter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;
import comm.filter.counter.WebsAccess;

public class ClientInfoFilter extends HttpServlet implements Filter {
    private FilterConfig filterConfig;

    // Handle the passed-in FilterConfig
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    // Process the request/response pair
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain filterChain) throws IOException,
            ServletException {
        // Logger.getLogger().debug("Start ClientInfoFilter...");
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession();
        // Logger.getLogger().debug("URI=" + req.getRequestURI());
        if (!req.getRequestURI().contains("ClientInfoServlet")
            && !req.getRequestURI().contains("clientinfo.jsp")
            && !req.getRequestURI().endsWith(".js")
            && !req.getRequestURI().contains(".css")
            && !req.getRequestURI().contains(".gif")
            && !req.getRequestURI().contains(".jpg")
            && !req.getRequestURI().contains("js.do")
                ) {

            if (session.getAttribute("ClientInfo") != null) {
                WebsAccess.getInstance().process(req);
                filterChain.doFilter(request, response);
            } else {
                String backurl = req.getRequestURL().toString();
                String parameter = req.getQueryString();
                if (parameter != null)
                    backurl = backurl + "?" + parameter;
                backurl = backurl.replaceAll("&", "@@");
                //System.out.println("backurl="+backurl);
                req.getRequestDispatcher(
                        "/comm/clientinfo.jsp?backurl=" + backurl).forward(req,
                        res);
            }
        } else {
            filterChain.doFilter(request, response);
        }
    }

    // Clean up resources
    public void destroy() {
    }
}
