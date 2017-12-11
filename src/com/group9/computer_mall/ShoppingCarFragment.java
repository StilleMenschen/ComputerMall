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
	private ShoppingCarAdapter adapter;// 购物车适配器
	private ListView listView;// 购物车视图
	private CheckBox checkBox;// 全选商品的复选框
	private TextView textView;// 显示商品价格的文本
	private Button button;// 购买按钮

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
		textView.setText("￥0");
		// 初始化购物车视图，并设置适配器
		adapter = new ShoppingCarAdapter(mContext, this, app.array);
		listView.setAdapter(adapter);
		// 监听购物车ListView视图变化
		listView.addOnLayoutChangeListener(this);
	}

	// 显示价格
	private void changeTotalDisplay() {
		textView.setText("￥" + adapter.getTotal());
	}

	// 全选复选框监听，判断购物车中的商品是否为全部选中/部分选中/全部未选中
	private void changeCheckBox() {
		if (ShoppingCarAdapter.ALL_CHECKED == adapter.getAllChecked()) {
			checkBox.setChecked(true);
		} else {
			checkBox.setChecked(false);
		}
	}

	// 回调购物车ListView中复选框选中状态改变所使用的接口
	@Override
	public void onCheckedChange(int position, boolean isChecked) {
		changeCheckBox();
		changeTotalDisplay();
	}

	// 购物车界面上的复选框监听器
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
			Toast.makeText(mContext, "购买成功!", Toast.LENGTH_SHORT).show();
			break;
		case R.id.cb_all:
			adapter.setAllChecked(checkBox.isChecked());
			break;
		}
	}

	// 监听当前购物车Fragment页面是否被隐藏
	@Override
	public void onHiddenChanged(boolean hidden) {
		Log.w(TAG, "onHiddenChanged---" + hidden);
		// hidden为true则表示被隐藏了
		if (hidden) {
			app.clearData();// 清空商品缓存记录
		} else {
			adapter.addData(app.array);// 添加商品记录
		}
	}

	// 当购物车ListView视图发生改变时会调用这个方法
	@Override
	public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight,
			int oldBottom) {
		Log.d(TAG, "onLayoutChange");
		changeCheckBox();
		adapter.preparedTotal();// 通知adapter更新总价
		changeTotalDisplay();
	}

}
