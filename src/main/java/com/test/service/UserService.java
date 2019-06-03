package com.test.service;

import com.test.bean.Info;
import com.test.bean.UserInfo;
import com.test.dao.UserDao;

public class UserService {
	UserDao userdao = new UserDao();
	
	public int addUser(UserInfo user){
		return userdao.addUser(user);
	}
	
	public Info hasUser(UserInfo user){
		return userdao.hasUser(user);
	}
	
	public Info canLogin(UserInfo user){
		return userdao.canLogin(user);
	}
	
	public Info getInfo(String name){
		return userdao.getInfo(name);
	}
}
