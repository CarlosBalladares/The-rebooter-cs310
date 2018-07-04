package com.cs310.TheRebooter.server;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class OAuthResponseHandler extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		//printURL(req);//for debugging purposes
		String error = (String) req.getParameter("error");
		String code = (String) req.getParameter("code" );
		String service = (String) req.getParameter("service");
		if(code== null || service==null){
			req.getRequestDispatcher("/TheRebooter.html").forward(req, resp);
		}else if(error!=null){
			req.getRequestDispatcher("/TheRebooter.html").forward(req, resp);
		}else{
			req.setAttribute("code", code);
			req.setAttribute("service", service);
			req.getRequestDispatcher("therebooter/user").forward(req, resp);
		}
	}
	
	private void printURL(HttpServletRequest req){
		String uri = req.getScheme() + "://" + // "http" + "://
				req.getServerName() + // "myhost"
				":" + // ":"
				req.getServerPort() + // "8080"
				req.getRequestURI() + // "/people"
				"?" + // "?"
				req.getQueryString();
		System.out.println(uri);
	}
}