package com.group9.computer_mall;

import java.util.LinkedList;

import android.app.Application;

public class MyApp extends Application {

	public static final String TAG = "MyApp";

	public boolean isLogin = false;// ��¼״̬
	public String user;// ��¼��
	public LinkedList<Commodity> array = new LinkedList<Commodity>();// ������Ʒ��Ϣ������

	// ���������Ʒ��Ϣ����
	public void clearData() {
		if (array.size() > 0) {
			array.clear();
		}
	}
}
