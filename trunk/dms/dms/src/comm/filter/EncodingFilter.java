package comm.filter;

import javax.servlet.*;
import javax.servlet.http.*;

import util.Logger;

import java.io.*;
import java.util.*;

public class EncodingFilter extends HttpServlet implements Filter {
        private FilterConfig filterConfig;

        // Handle the passed-in FilterConfig
        public void init(FilterConfig filterConfig) throws ServletException {
                this.filterConfig = filterConfig;
        }

        // Process the request/response pair
        public void doFilter(ServletRequest request, ServletResponse response,
                        FilterChain filterChain) throws IOException, ServletException {
                HttpServletRequest req = (HttpServletRequest) request;
                req.setAttribute("resource_url",req.getRequestURI());
                request.setCharacterEncoding("utf-8");
                filterChain.doFilter(request, response);
        }

        // Clean up resources
        public void destroy() {
        }
}
