package com.test.bean;

import com.alibaba.fastjson.JSONObject;

public class Info {
	private String status="";
	private String message="";
	private JSONObject infos;
	private String datas;
	
	
	public Info() {
	}

	public String toString(){
		JSONObject result = new JSONObject();
		
		result.put("status", status);
		result.put("message", message);
		result.put("datas", datas);
		
		return result.toJSONString();
	}
	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getmessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JSONObject getInfos() {
		return infos;
	}

	public void setInfos(JSONObject infos) {
		this.infos = infos;
		this.datas = infos.toJSONString();
		
		System.out.println(datas);
	}
}
