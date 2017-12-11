package com.group9.computer_mall;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 登陆界面
 * 
 * @author Hasee
 */
public class LoginActivity extends Activity {

	private EditText mUserName;// 用户名
	private EditText mPassword;// 用户密码
	private Button mSubmit;// 提交按钮
	private MyApp app;// 全局类Application

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);

		app = (MyApp) getApplicationContext();

		// 获取布局文件中的UI元素对象
		mUserName = (EditText) this.findViewById(R.id.login_username);
		mPassword = (EditText) this.findViewById(R.id.login_password);
		mSubmit = (Button) this.findViewById(R.id.login_submit);

		// 设置按钮点击之后的事件处理
		mSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 获取编辑框中的用户名和密码数据
				String userName = mUserName.getText().toString();
				String password = mPassword.getText().toString();
				// 判断输入的用户名和密码是否正确
				if ("admin".equals(userName) && "123456".equals(password)) {
					Toast.makeText(getApplicationContext(), "登录成功！", Toast.LENGTH_SHORT).show();
					app.isLogin = true;// 设置登录状态
					app.user = "admin";// 设置登录名
				} else {
					Toast.makeText(getApplicationContext(), "登录失败，用户名或密码不正确！", Toast.LENGTH_SHORT).show();
				}
				finish();
			}
		});
	}

}