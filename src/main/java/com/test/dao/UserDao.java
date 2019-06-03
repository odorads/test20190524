package com.test.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;
import com.test.bean.Info;
import com.test.bean.UserInfo;
import com.test.controller.UsersController;

public class UserDao {
	final String URL = "jdbc:mysql://localhost:3306/pro_server?serverTimezone=UTC&characterEncoding=UTF-8";
	final String user = "root";
	final String psd = "root";

	Statement stmt;
	Logger logger= Logger.getLogger(UserDao.class);
	
	public UserDao() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(URL, user, psd);
	        
	        stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public int addUser(UserInfo tempUser){
        try {
	        String name = tempUser.getName();
	        String email = tempUser.getEmail();
	        String password = tempUser.getPsd();
	        String phone = tempUser.getPhone();
			
			/**
			 * 注意 SQL中 字符串 必须加上 引号，不然会出错
			 */
			String sql = "insert into users(Name, Password, Email, Phone) values ("
	        		+"\""+ name +"\"" +","
					+"\""+ password +"\""+ "," 
	        		+"\""+ email+"\"" + ","
	        		+"\""+ phone +"\""
	        		+ ") ";
			
	        logger.info(sql);
	        
			int rs = stmt.executeUpdate(sql);
			
			return rs;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public Info hasUser(UserInfo tempUser){
        try {
        	Info result = new Info();
        	
	        String name = tempUser.getName();
	        String email = tempUser.getEmail();
	        String password = tempUser.getPsd();
	        String phone = tempUser.getPhone();
			
			/**
			 * 注意 SQL中 字符串 必须加上 引号，不然会出错
			 */
			String sql_name = "select count(*) from users "
					+ "where name = '" + name + "'";
			
			String sql_email = "select count(*) from users "
					+ "where email = '" + email + "'";
			
			String sql_phone = "select count(*) from users "
					+ "where phone = '" + phone + "'";
			
	        logger.info(sql_name);
	        
			ResultSet rs = stmt.executeQuery(sql_name);
			rs.next();
			
			if(rs.getInt("count(*)") > 0){
				result.setStatus("Failed");
				result.setMessage("用户名已被注册,请重新输入");
				
				return result;
			}
			
			logger.info(sql_email);
		        
			rs = stmt.executeQuery(sql_email);
			rs.next();
				
			if(rs.getInt("count(*)") > 0){
				result.setStatus("Failed");
				result.setMessage("邮件已被注册,请重新输入");
					
				return result;
			}
			
			logger.info(sql_phone);
		        
			rs = stmt.executeQuery(sql_phone);
			rs.next();
			
			if(rs.getInt("count(*)") > 0){
				result.setStatus("Failed");
				result.setMessage("手机号已被注册,请重新输入");
				
				return result;
			}	
			
			result.setStatus("SUCCESS");
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Info canLogin(UserInfo tempUser){
		Info result = new Info();
    	
        String name = tempUser.getName();
        String password = tempUser.getPsd();
        
        String sql = "select count(*) from users "
				+ "where name = '" + name + "' and password = '" + password + "'" ;
        
        logger.info(sql);
        
		ResultSet rs;
		try {
			rs = stmt.executeQuery(sql);
			rs.next();
			
			if(1 == rs.getInt("count(*)")){
				result.setStatus("SUCCESS");
				result.setMessage(name + "登录测试系统.");
				
				JSONObject temp = new JSONObject();
				temp.put("name", name);
				
				result.setInfos(temp);
			}else{
				result.setStatus("Failed");
				result.setMessage("用户名或密码错误.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Info getInfo(String name){
		Info result = new Info();
		
		String sql = "select * from users "
				+ "where name = '" + name + "'";
		
		ResultSet rs;
		
		try {
			rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				result.setStatus("SUCCESS");
				result.setMessage(name + "的基本资料.");
				
				JSONObject temp = new JSONObject();
				temp.put("id", rs.getInt(1));
				temp.put("name", rs.getString(2));
				temp.put("password", rs.getString(3));
				temp.put("email", rs.getString(4));
				temp.put("phone", rs.getString(5));
				
				result.setInfos(temp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
