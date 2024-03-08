package com.eat.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.eat.service.CartService;
import org.apache.log4j.Logger;

import com.eat.dao.Dao;
import com.eat.util.MyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
//
//@WebServlet(urlPatterns = "/cart")
//@Controller
@RestController
@RequestMapping("/cart")
public class CartController extends HttpServlet {
	@Autowired
	Dao dao ;
	 Logger logger=Logger.getLogger(String.valueOf(CartController.class));
	/**
	 * 可以把方法写在doPost()方法中,在doGet()方法中调用执行,这样,无论你提交的是post还是get方法都可以执行
	 */
	@Autowired
	CartService cartService;

	@PostMapping("/settlement")
	void settlement(HttpServletRequest req, @RequestParam Map map){
		logger.debug(map);
		Map m = (Map) req.getSession().getAttribute("islogin");
		Date now = new Date();
		logger.debug(now);
		String orderid=now+(String) m.get("zymemid");
		m.put("orderid",orderid);
		m.put("date",now);


	}



	@GetMapping("/insert")
	String insert(HttpServletRequest req, HttpServletResponse response, @RequestParam Map map) throws IOException {
		logger.debug("map:"+map);
		Map m = (Map) req.getSession().getAttribute("islogin");
		m.put("zymenuid",map.get("zymenuid"));
		logger.debug("m:"+m);
//		PrintWriter out=response.getWriter();
		if (cartService.insertCart(m) ){
//			out.print("addcart success!");
//			logger.debug("OK");
			return "success";
		} else {
//			out.print("addcart failure!");
//			logger.debug("Fail");
			return "failure";
		}

	}
	@PostMapping("/getcart")
	List getcart(HttpServletRequest req,HttpServletResponse resp,@RequestParam Map map) throws IOException {
		logger.debug("map:"+map);
		String zyname = req.getParameter("zyname");
		logger.debug("zyname:"+zyname);
		Map m = (Map) req.getSession().getAttribute("islogin");
		logger.debug("m:"+m);
		int usertype= (int) m.get("usertype");
		if (usertype==1)
			map.put("zymemid",map.get("zymemid"));

		logger.debug("map:"+map);

		resp.setContentType("application/json;charset=UTF-8");
//		PrintWriter out=resp.getWriter();
		if (zyname == null) {
			zyname = "";
		}
//		out.println(MyUtil.toJSON(cartService.getcart(zyname,zymemid)));
//		return cartService.getcart(zyname,zymemid);
		return cartService.getcart(m,zyname);
	}
	@GetMapping("/addcart")
	String addcart(HttpServletRequest req,HttpServletResponse response,@RequestParam Map map) throws IOException {
//		PrintWriter out=response.getWriter();
		logger.debug("map:"+map);
		String zymenuid = req.getParameter("zymenuid");
		String zymemid = (String) req.getSession().getAttribute("islogin");
		logger.debug("zymenuid:"+zymenuid);
		logger.debug("zymemid:"+zymemid);
		if (cartService.deleteCart(map,zymenuid,zymemid)) {
//			out.print("delete success!");
			logger.debug("OK");
			return "success";
		} else {
//			out.print("delete failure!");
			logger.debug("Fail");
			return "failure";
		}
	}

	@GetMapping("/delete")
	String delete(HttpServletRequest req,HttpServletResponse response,@RequestParam Map map) throws IOException {
//		PrintWriter out=response.getWriter();
		logger.debug("map:"+map);
		String zymenuid = req.getParameter("zymenuid");
		String zymemid = (String) req.getSession().getAttribute("islogin");
		logger.debug("zymenuid:"+zymenuid);
		logger.debug("zymemid:"+zymemid);
		if (cartService.deleteCart(map,zymenuid,zymemid)) {
//			out.print("delete success!");
			logger.debug("OK");
			return "success";
		} else {
//			out.print("delete failure!");
			logger.debug("Fail");
			return "failure";
		}
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// req.setCharacterEncoding("utf-8");
		doPost(req, resp);
	}
	//
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//获取html页面中op对象的值
		String op = req.getParameter("op");
		String id = req.getParameter("id");
		String zymenuid = req.getParameter("zymenuid");
		logger.debug("zymenuid:"+zymenuid);
		/**
		 * response顾名思义就是服务器对浏览器的相应，PrintWriter它的实例就是向前台的JSP页面输出结果，比如
out.print("Hello World")；
		 */
		PrintWriter out = resp.getWriter();
		String sql = null;
		if (op == null) {
			op = "";
		}
		/**
		 * 通过req.getsession()获得session 对象 再调用它的getAttibute（String key）方法来获得含有关键字“islogin”的对象！
		 */
		String zymemid = (String) req.getSession().getAttribute("islogin");
		switch (op) {
		case "insert":
			sql = "insert into zycart(zymenuid,zymemid) values('" + zymenuid + "', '" + zymemid + "')";
			logger.debug(sql);
			if (dao.exeUpdate(sql)) {
				out.print("addcart success!");
			} else {
				out.print("addcart failure!");
			}
			break;
		case "delete":
			sql = "delete from zycart where zymenuid=" + zymenuid+" and zymemid='"+zymemid+"'";
			logger.debug(sql);
			if (dao.exeUpdate(sql)) {
				out.print("delete success!");
			} else {
				out.print("delete failure!");
			}
			break;
		case "getcart":
			String zyname = req.getParameter("zyname");
			if (zyname == null) {
				zyname = "";
			}
			sql = "select zymenu.zyid,zypic,zyname,zyprice,zyremark " + " from zymenu,zycart" + " where zymenu.zyid=zycart.zymenuid"
					+ " and zymemid='" + zymemid + "'" + " and zyname like '%" + zyname + "%'";
			logger.debug(sql);
			List lst = dao.getData(sql);
			// resp.getWriter().print(lst.size());
			
			out.println(MyUtil.toJSON(lst));
			break;
		default:
			break;
		}

	}
}
