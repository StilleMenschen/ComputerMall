package com.group9.computer_mall;

import com.group9.computer_mall.ShoppingCarAdapter.PayListener;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingCarFragment extends Fragment
		implements PayListener, OnClickListener, OnCheckedChangeListener, OnLayoutChangeListener {

	public static final String TAG = "ShoppingCarFragment";

	private Context mContext;
	private View parent;
	private MyApp app;
	private ShoppingCarAdapter adapter;// ���ﳵ������
	private ListView listView;// ���ﳵ��ͼ
	private CheckBox checkBox;// ȫѡ��Ʒ�ĸ�ѡ��
	private TextView textView;// ��ʾ��Ʒ�۸���ı�
	private Button button;// ����ť

	public ShoppingCarFragment(Context context) {
		mContext = context;
		app = (MyApp) mContext.getApplicationContext();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		parent = inflater.inflate(R.layout.fragment_shopping_car, container, false);
		initView();
		return parent;
	}

	private void initView() {
		listView = (ListView) parent.findViewById(R.id.lv_shopping_car);
		checkBox = (CheckBox) parent.findViewById(R.id.cb_all);
		textView = (TextView) parent.findViewById(R.id.tv_total);
		button = (Button) parent.findViewById(R.id.btn_pay);
		checkBox.setOnClickListener(this);
		checkBox.setOnCheckedChangeListener(this);
		button.setOnClickListener(this);
		textView.setText("��0");
		// ��ʼ�����ﳵ��ͼ��������������
		adapter = new ShoppingCarAdapter(mContext, this, app.array);
		listView.setAdapter(adapter);
		// �������ﳵListView��ͼ�仯
		listView.addOnLayoutChangeListener(this);
	}

	// ��ʾ�۸�
	private void changeTotalDisplay() {
		textView.setText("��" + adapter.getTotal());
	}

	// ȫѡ��ѡ��������жϹ��ﳵ�е���Ʒ�Ƿ�Ϊȫ��ѡ��/����ѡ��/ȫ��δѡ��
	private void changeCheckBox() {
		if (ShoppingCarAdapter.ALL_CHECKED == adapter.getAllChecked()) {
			checkBox.setChecked(true);
		} else {
			checkBox.setChecked(false);
		}
	}

	// �ص����ﳵListView�и�ѡ��ѡ��״̬�ı���ʹ�õĽӿ�
	@Override
	public void onCheckedChange(int position, boolean isChecked) {
		changeCheckBox();
		changeTotalDisplay();
	}

	// ���ﳵ�����ϵĸ�ѡ�������
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Log.d(TAG, "onCheckedChanged");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_pay:
			if (ShoppingCarAdapter.NO_CHECKED == adapter.getAllChecked()) {
				break;
			}
			adapter.clear();
			Toast.makeText(mContext, "����ɹ�!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.cb_all:
			adapter.setAllChecked(checkBox.isChecked());
			break;
		}
	}

	// ������ǰ���ﳵFragmentҳ���Ƿ�����
	@Override
	public void onHiddenChanged(boolean hidden) {
		Log.w(TAG, "onHiddenChanged---" + hidden);
		// hiddenΪtrue���ʾ��������
		if (hidden) {
			app.clearData();// �����Ʒ�����¼
		} else {
			adapter.addData(app.array);// �����Ʒ��¼
		}
	}

	// �����ﳵListView��ͼ�����ı�ʱ������������
	@Override
	public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight,
			int oldBottom) {
		Log.d(TAG, "onLayoutChange");
		changeCheckBox();
		adapter.preparedTotal();// ֪ͨadapter�����ܼ�
		changeTotalDisplay();
	}

}
