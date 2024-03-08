package com.eat.filter;

/**
 * Filter的用途 
1. 解决中文乱码问题 
2. 权限访问控制 
3. 过滤敏感词汇 
4. 压缩响应信息
 */

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;


import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(filterName = "myfilter",urlPatterns="/*")
public class MyFilter implements Filter{//过滤器
	Logger logger=Logger.getLogger(MyFilter.class);
	@Override
	public void destroy() {
		// 
	}
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request= (HttpServletRequest) arg0;
		HttpServletResponse response= (HttpServletResponse) arg1;
//		request.setCharacterEncoding("UTF-8");
		String uri=request.getRequestURI();
		System.out.println(uri);
		String ext=uri.substring(uri.lastIndexOf(".")+1);
		System.out.println(ext);
		boolean bGo="js".equals(ext)
				||"css".equals(ext)
				||"png".equals(ext)
				||"html".equals(ext)
				||"htm".equals(ext)
				||uri.contains("login")
				||uri.contains("register.html")
				||uri.contains("sregister.html")
				||uri.contains("slogin");
		Map m= (Map) request.getSession().getAttribute("islogin");
		logger.debug("m:"+m);
		if (!bGo)
		if (m==null){
			response.getWriter().print("nologin");
			return;
		}
		if (m!=null){
			int usertype= (int) m.get("usertype");
			logger.debug("usertype:"+usertype);
			if (usertype==1){
				boolean allow=uri.contains("menu")
						||uri.contains("seller")
						||uri.contains("sregister")
						||"png".equals(ext);
				if (!allow){
					response.getWriter().print("no privilege");
					return;
				}
			}
			if (usertype==2){
				boolean allow=uri.contains("getmenu")
						||uri.contains("cart")
						||uri.contains("member")
						||uri.contains("dismenu")
						||"png".equals(ext);
				if (!allow){
					response.getWriter().print("no privilege");
					return;
				}
			}
		}
		arg2.doFilter(arg0, arg1);
		
	}
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
