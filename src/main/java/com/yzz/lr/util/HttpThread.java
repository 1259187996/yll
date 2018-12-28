package com.yzz.lr.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpException;

public class HttpThread implements Runnable {

	private JSONObject json;

	private String url;

	public HttpThread(String url, JSONObject json) {
		this.url = url;
		this.json = json;
	}

	@Override
	public void run() {
		try {
			HttpRequest.postJson(url, json, "UTF-8");
		} catch (HttpException e) {
			System.out.println("发送HTTP请求出错");
			e.printStackTrace();
		}

	}
}
