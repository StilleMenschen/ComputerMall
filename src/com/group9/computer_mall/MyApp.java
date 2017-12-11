package com.group9.computer_mall;

import java.util.LinkedList;

import android.app.Application;

public class MyApp extends Application {

	public static final String TAG = "MyApp";

	public boolean isLogin = false;// 登录状态
	public String user;// 登录名
	public LinkedList<Commodity> array = new LinkedList<Commodity>();// 缓存商品信息的数组

	// 用于清空商品信息缓存
	public void clearData() {
		if (array.size() > 0) {
			array.clear();
		}
	}
}
