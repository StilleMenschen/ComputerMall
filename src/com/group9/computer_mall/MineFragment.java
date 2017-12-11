package com.group9.computer_mall;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * ��������ҳ��
 * @author Hasee
 */
public class MineFragment extends Fragment {
	public static final String TAG = "MineFragment";
	private Context mContext;
	private View parent;
	private TextView tv;
	private TextView about;
	private MyApp app;

	public MineFragment(Context context) {
		mContext = context;
		app = (MyApp) mContext.getApplicationContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		parent = inflater.inflate(R.layout.fragment_mine, container, false);
		initView();
		return parent;
	}

	// ��ʼ������
	private void initView() {
		tv = (TextView) parent.findViewById(R.id.tv_user);
		parent.findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!app.isLogin) {// �ж��Ƿ��¼
					Intent intent = new Intent(mContext, LoginActivity.class);
					startActivity(intent);// ������¼ҳ��
				}
			}
		});
		about = (TextView) parent.findViewById(R.id.aboutUs);
		about.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				final AlertDialog.Builder normalDialog = new AlertDialog.Builder(mContext);
				normalDialog.setIcon(R.drawable.group_nine);
				normalDialog.setTitle("�ھ���");
				normalDialog.setMessage("��Ա����ʢ��,�ֹ�,Τ���");
				normalDialog.setPositiveButton("ȷ��", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
				// ��ʾ
				normalDialog.show();
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		if (app.isLogin) {// �ж��Ƿ��¼�����������û���
			tv.setText(app.user);
			Log.i(TAG, "Set User:" + app.user);
		}
	}

}
