package com.test.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.test.bean.Info;
import com.test.bean.UserInfo;
import com.test.service.UserService;

@Controller
@RequestMapping("/users")
public class UsersController {
	Logger logger = Logger.getLogger(UsersController.class);

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(HttpServletRequest request,
			HttpServletResponse response) {
		Info result = new Info();

		try {
			UserInfo user = new UserInfo();
			user.setName(request.getParameter("name"));
			user.setEmail(request.getParameter("email"));
			user.setPhone(request.getParameter("phone"));
			user.setPsd(request.getParameter("psd"));

			UserService userService = new UserService();
			result = userService.hasUser(user);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			String regex = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
			if ("Failed".equals(result.getStatus())) {
				response.getWriter().write(result.toString());
				logger.info("注册失败,用户名或者手机号有重复.");
			} else if (!user.getEmail().matches(regex)) {
				result.setStatus("Failed");
				result.setMessage("邮件格式不规则.");

				response.getWriter().write(result.toString());
			} else {
				int num = userService.addUser(user);

				if (1 == num) {
					result.setStatus("SUCCESS");
					result.setMessage("注册成功.");

					response.getWriter().write(result.toString());
					logger.info(request.getParameter("name") + "注册系统成功.");
				} else {
					result.setStatus("Failed");
					result.setMessage("注册失败.");

					response.getWriter().write(result.toString());
					logger.info(request.getParameter("name") + "注册系统失败.");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public void login(HttpServletRequest request, HttpServletResponse response) {
		Info result = new Info();

		try {
			UserInfo user = new UserInfo();
			user.setName(request.getParameter("name"));
			user.setPsd(request.getParameter("psd"));

			UserService userService = new UserService();
			result = userService.canLogin(user);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			response.getWriter().write(result.toString());

			logger.info(user.getName() + "登录测试系统.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getInfo", method = RequestMethod.GET)
	public void getInfo(HttpServletRequest request, HttpServletResponse response) {
		Info result = new Info();

		try {
			String name = request.getParameter("name");

			UserService userService = new UserService();
			result = userService.getInfo(name);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			response.getWriter().write(result.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "/updateInfo", method = RequestMethod.POST)
	public void updateInfo(HttpServletRequest request, HttpServletResponse response){
		Info result = new Info();
		
			int id = Integer.valueOf(request.getParameter("id"));
			String name = request.getParameter("name");
			String password = request.getParameter("oldpsd");
			String newPassword = request.getParameter("newpsd");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			
			System.out.println(id + "\n" + name + "\n" + password + "\n" + email + "\n" + phone);
			
	}
	
	
}
