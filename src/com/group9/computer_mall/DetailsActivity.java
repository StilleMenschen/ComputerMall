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

// ��Ʒ��ϸҳ
public class DetailsActivity extends Activity implements OnClickListener {

	private ImageView imageView; // ��ƷͼƬ
	private TextView textView_desc;// ��Ʒ����
	private TextView textView_price;// ��Ʒ�۸�
	private Button btn;// ���빺�ﳵ��ť
	private TextView back;// ���ذ�ť
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
		Intent intent = getIntent(); // ��ȡIntent���ݹ���������
		int id = intent.getIntExtra("resid", R.drawable.temp);
		String desc = intent.getStringExtra("desc");
		String price = intent.getStringExtra("price");
		c = new Commodity(id, desc, price, true);// ����һ����Ʒ
	}

	// �ҳ��ؼ�ID����ֵ
	private void findView() {
		imageView = (ImageView) findViewById(R.id.iv_commodity);
		textView_desc = (TextView) findViewById(R.id.tv_describe);
		textView_price = (TextView) findViewById(R.id.tv_money);
		btn = (Button) findViewById(R.id.btn_add_cart);
		back = (TextView) findViewById(R.id.tv_back);
	}

	// ���ü������ͳ�ʼ������
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
			MyApp app = (MyApp) getApplicationContext(); // ��ȡApplication��ʵ��
			app.array.add(c);// ��Application������ȫ�ֱ���������Ʒ��¼�Ȼ���
			Toast.makeText(this, "�ɹ����빺�ﳵ", Toast.LENGTH_SHORT).show();
			finish();// �ر�Activity����
			break;
		case R.id.tv_back:
			finish();
			break;
		}
	}
}
