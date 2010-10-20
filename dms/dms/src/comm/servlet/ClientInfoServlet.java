package comm.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import comm.bean.ClientInfo;
import comm.task.ClientInfoTask;
import util.*;
import comm.*;

public class ClientInfoServlet extends HttpServlet {

    public static String rootPath=null;

        /**
         * Constructor of the object.
         */
        public ClientInfoServlet() {
                super();
        }

        /**
         * Destruction of the servlet. <br>
         */
        public void destroy() {
                super.destroy(); // Just puts "destroy" string in log
                // Put your code here
        }

        /**
         * The doGet method of the servlet. <br>
         *
         * This method is called when a form has its tag value method equals to get.
         *
         * @param request
         *            the request send by the client to the server
         * @param response
         *            the response send by the server to the client
         * @throws ServletException
         *             if an error occurred
         * @throws IOException
         *             if an error occurred
         */
        public void doGet(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {

                doPost(request, response);
        }

        /**
         * The doPost method of the servlet. <br>
         *
         * This method is called when a form has its tag value method equals to
         * post.
         *
         * @param request
         *            the request send by the client to the server
         * @param response
         *            the response send by the server to the client
         * @throws ServletException
         *             if an error occurred
         * @throws IOException
         *             if an error occurred
         */
        public void doPost(HttpServletRequest request, HttpServletResponse response)
                        throws ServletException, IOException {

                    try {
                        if (rootPath == null)
                            rootPath = request.getSession().getServletContext().getRealPath("/");
                    } catch (Exception e) {
                    }

                ClientInfo info = new ClientInfo();
                String ip=request.getRemoteAddr();
                info.setIp(ip);
                info.setSystemType(request.getParameter("SystemType"));
                info.setSystemLanguage(request.getParameter("SystemLanguage"));
                info.setBrowserType(request.getParameter("BrowserType"));
                info.setBrowserLanguage(request.getParameter("BrowserLanguage"));
                info.setCpuClass(request.getParameter("CpuClass"));
                info.setColor(request.getParameter("Color"));
                info.setResolution(request.getParameter("Resolution"));
                Logger.getLogger().debug("ClientInfo:" + info.toString());
                request.getSession().setAttribute(Ini.SESSION_CLIENTINFO, info);
                String backurl = request.getParameter("backurl");
                backurl=backurl.replaceAll("@@", "&");
                //System.out.println("backurl="+backurl);
                //�ö��̻߳��MAC��ַ
                ThreadFactory.getThreadPool().execute(new ClientInfoTask(info));
                // Logger.getLogger().debug("backurl:" + backurl);
                // request.getRequestDispatcher(backurl).forward(request, response);
                response.sendRedirect(backurl);
        }

        /**
         * Initialization of the servlet. <br>
         *
         * @throws ServletException
         *             if an error occurs
         */
        public void init() throws ServletException {
                // Put your code here
        }

}
