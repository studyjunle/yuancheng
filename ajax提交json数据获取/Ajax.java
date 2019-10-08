package com.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

public class Ajax extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	//method1(request);
		String parameter = request.getParameter("userInfo");
		System.out.println(parameter);
		JSONObject jsonObject = (JSONObject) JSONObject.parse(parameter);
		System.out.println(jsonObject.get("username"));
	}

	private void method1(HttpServletRequest request) throws IOException {
		//  request.getReader();方法返回一个包含body体数据的BufferedReader；
		    BufferedReader reader = request.getReader();
		    String readerStr = "";// 接收用户端传来的JSON字符串（body体里的数据）
		    String line;
		    while ((line = reader.readLine()) != null){
		        readerStr = readerStr.concat(line);
		    }

		   // 使用阿里的fastjson jar包处理json数据（这里是用map进行接收的，你也可以定义vo层容器类接收）
		    HashMap map = JSONObject.parseObject(readerStr, HashMap.class);     
		    System.out.println(map.get("username"));
	}
}
