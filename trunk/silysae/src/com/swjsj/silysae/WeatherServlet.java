package com.swjsj.silysae;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.sina.sae.fetchurl.SaeFetchurl;


public class WeatherServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public WeatherServlet() {
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
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String url = request.getParameter("url");
		SaeFetchurl fetchUrl = new SaeFetchurl();
		String content = fetchUrl.fetch(url);
		content  = new String(content.getBytes("iso8859-1"),"gb2312");
		
		Document doc = Jsoup.parse(content);
		setUrl(doc,"href","link");
		setUrl(doc,"src","script");
		setUrl(doc.body(),"src","img");
		
		Elements styles = doc.getElementsByTag("style");
		String style = styles.first().html();
		String css = ".wdj_width{background:url('http://www.weather.gov.cn/images/monitor/qwj.png') repeat-x scroll 0 0 transparent;bottom: 22px !important;*bottom:12px;height: 4px;left: 65px;line-height: 0;padding: 0;position: absolute;z-index:10;}";
		style += "\n\r" + css; 
		styles.html(style);
		
		response.setContentType("text/html");  
        response.setHeader("Cache-Control", "no-cache"); 
        response.setCharacterEncoding("gb2312");
        PrintWriter out = response.getWriter();
        String html = doc.html();
        
        String cityname = request.getParameter("cityname");
        
        try {
			XmlReaderByJsoup.sendMail(cityname);
		} catch (Exception e) {
			Logger.getLogger(this.getClass()).info("发送邮件出错 ");
		}
        
        Logger.getLogger(this.getClass()).info(cityname);
        
        out.write(html + XmlReaderByJsoup.threeDayWeather(cityname));
	}
	

	
	
	private void setUrl(Element doc,String attr,String tag){
		Elements es = doc.getElementsByTag(tag);
		Iterator it = es.iterator();
		while(it.hasNext()){
			Element e = (Element)it.next();
			String val =  e.attr(attr);
			if("".equals(val)){
				continue;
			}
			e.attr(attr, "http://www.weather.gov.cn" + val );
		}
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	

}
