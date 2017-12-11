package com.group9.computer_mall;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

// 商品详细页
public class DetailsActivity extends Activity implements OnClickListener {

	private ImageView imageView; // 商品图片
	private TextView textView_desc;// 商品描述
	private TextView textView_price;// 商品价格
	private Button btn;// 加入购物车按钮
	private TextView back;// 返回按钮
	private Commodity c;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_details);
		findView();
		getData();
		initView();
	}

	private void getData() {
		Intent intent = getIntent(); // 获取Intent传递过来的数据
		int id = intent.getIntExtra("resid", R.drawable.temp);
		String desc = intent.getStringExtra("desc");
		String price = intent.getStringExtra("price");
		c = new Commodity(id, desc, price, true);// 创建一个商品
	}

	// 找出控件ID并赋值
	private void findView() {
		imageView = (ImageView) findViewById(R.id.iv_commodity);
		textView_desc = (TextView) findViewById(R.id.tv_describe);
		textView_price = (TextView) findViewById(R.id.tv_money);
		btn = (Button) findViewById(R.id.btn_add_cart);
		back = (TextView) findViewById(R.id.tv_back);
	}

	// 设置监听器和初始化数据
	private void initView() {
		btn.setOnClickListener(this);
		back.setOnClickListener(this);
		imageView.setImageResource(c.imageId);
		textView_desc.setText(c.description);
		textView_price.setText(c.price);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_add_cart:
			MyApp app = (MyApp) getApplicationContext(); // 获取Application类实例
			app.array.add(c);// 在Application中声明全局变量，将商品记录先缓存
			Toast.makeText(this, "成功加入购物车", Toast.LENGTH_SHORT).show();
			finish();// 关闭Activity界面
			break;
		case R.id.tv_back:
			finish();
			break;
		}
	}
}
