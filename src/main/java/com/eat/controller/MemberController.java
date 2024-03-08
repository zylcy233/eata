package com.eat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eat.service.MemberService;
import org.apache.log4j.Logger;

import com.eat.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//@WebServlet(urlPatterns = "/member")
//@Controller
@RestController
@RequestMapping("/member")
public class MemberController extends HttpServlet {
	@Autowired
	Dao dao;
	Logger logger=Logger.getLogger(String.valueOf(MemberController.class));

	@Autowired
	MemberService memberService;

	@GetMapping("/check")
	void check(HttpServletRequest req,HttpServletResponse resp) throws IOException {
		String zymemid=req.getParameter("zymemid");
		logger.debug("zymemid:"+zymemid);
		PrintWriter out=resp.getWriter();
		if (memberService.checkMemid(zymemid)) {
			out.print("have");
		} else {
			out.print("meiyou");
		}
	}
	@PostMapping("/register")
	String register(HttpServletRequest req, HttpServletResponse resp, @RequestParam Map map) throws IOException {
		logger.debug("map:"+map);
		if (memberService.register(map)) {
//			resp.sendRedirect("../login.html");
			return "success";

		} else {
//			resp.sendRedirect("register.html");
			return "failure";
		}
	}
	@GetMapping("/login")
	String login(HttpServletRequest req,HttpServletResponse resp,@RequestParam Map map) throws IOException {
		logger.debug("map:"+map);
//		PrintWriter out=resp.getWriter();
		String zymemid=req.getParameter("zymemid");
		map.put("usertype",2);//会员
		logger.debug("map:"+map);
		if (memberService.login(map)) {
			req.getSession().setAttribute("islogin", map);
			// 会话连接
//			out.print("login success!");
			return "success";
		} else {
			req.getSession().setAttribute("islogin", "null");
			// 会话连接
//			out.print("login failure!");
			return "failure";
		}
	}

	@GetMapping("/islogin")
	void islogin(HttpServletRequest req,HttpServletResponse response) throws IOException {
		String islogin= String.valueOf(req.getSession().getAttribute("islogin"));//取得islogin对象的值
		logger.debug("islogin:"+islogin);
		PrintWriter out=response.getWriter();
		if(islogin==null) {
			islogin="htc_null";
		}
		out.print(islogin);
	}
	/**
	 * 可以把方法写在doPost()方法中,在doGet()方法中调用执行,这样,无论你提交的是post还是get方法都可以执行
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// login/register
		//获取请求参数
		String op = req.getParameter("op");
		String zymemid = req.getParameter("zymemid");
		logger.debug("zymemid:"+zymemid);
		String zymempass = req.getParameter("zymempass");
		logger.debug("zymempass:"+zymempass);
		String zymemalias = req.getParameter("zymemalias");
		logger.debug("zymemalias:"+zymemalias);
		// menu
		String zyname = req.getParameter("zyname");
		String zyprice = req.getParameter("zyprice");
		String zypic = req.getParameter("zypic");
		String zyremark = req.getParameter("zyremark");
		//PrintWriter它的实例就是向前台的页面输出结果
		//获取一个向浏览器输出的对象
		PrintWriter out = resp.getWriter();
		String sql = null;// 初始化
//		resp.getWriter().print(sql);
		dao.exeUpdate(sql);
		if (op == null) {
			op = "";
		}
		switch (op) {
		case "login":
			sql = "select * from zyMember where zymemid='" + zymemid + "'" + " and zymempass='" + zymempass + "'";
			logger.debug(sql);
			if (dao.isExist(sql)) {
				req.getSession().setAttribute("islogin", zymemid);
				// 会话连接
				out.print("login success!");
			} else {
				req.getSession().setAttribute("islogin", "htc_null");
				// 会话连接
				out.print("login failure!");
			}
			break;
		case "logout":
			//
			req.getSession().setAttribute("islogin", "htc_null");// //对islogin对象赋值
			resp.sendRedirect("login.html");
		break;	
		case "islogin":
			//getSession会话连接
			String islogin=(String) req.getSession().getAttribute("islogin");//取得islogin对象的值
			if(islogin==null) {
				islogin="htc_null";
			}
			out.print(islogin);
			break;
		case "check":
			sql = "select * from zyMember where zymemid='" + zymemid + "'";
			logger.debug(sql);
			if (dao.isExist(sql)) {
				out.print("have");
			} else {
				out.print("meiyou");
			}
			break;
		case "register":
			sql = "insert into zyMember(zymemid,zymempass,zymemalias) values('" + zymemid + "','" + zymempass + "','" + zymemalias
					+ "')";
			logger.debug(sql);
			if (dao.exeUpdate(sql)) {
				// out.print("register success");
				resp.sendRedirect("login.html");

			} else {
				// out.print("register fail!");
				resp.sendRedirect("register.html");
			}
			break;
		
		}
	}
}
