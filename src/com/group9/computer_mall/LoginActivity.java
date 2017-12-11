package com.group9.computer_mall;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * ��½����
 * 
 * @author Hasee
 */
public class LoginActivity extends Activity {

	private EditText mUserName;// �û���
	private EditText mPassword;// �û�����
	private Button mSubmit;// �ύ��ť
	private MyApp app;// ȫ����Application

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_login);

		app = (MyApp) getApplicationContext();

		// ��ȡ�����ļ��е�UIԪ�ض���
		mUserName = (EditText) this.findViewById(R.id.login_username);
		mPassword = (EditText) this.findViewById(R.id.login_password);
		mSubmit = (Button) this.findViewById(R.id.login_submit);

		// ���ð�ť���֮����¼�����
		mSubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ��ȡ�༭���е��û�������������
				String userName = mUserName.getText().toString();
				String password = mPassword.getText().toString();
				// �ж�������û����������Ƿ���ȷ
				if ("admin".equals(userName) && "123456".equals(password)) {
					Toast.makeText(getApplicationContext(), "��¼�ɹ���", Toast.LENGTH_SHORT).show();
					app.isLogin = true;// ���õ�¼״̬
					app.user = "admin";// ���õ�¼��
				} else {
					Toast.makeText(getApplicationContext(), "��¼ʧ�ܣ��û��������벻��ȷ��", Toast.LENGTH_SHORT).show();
				}
				finish();
			}
		});
	}

}