package com.group9.computer_mall;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends Activity implements OnCheckedChangeListener {

	private RadioGroup radioGroup;// �����·��İ�ť�˵�
	private Fragment[] mFragments;// װ��ÿһ��ҳ�������
	private int mIndex;// ����ǰҳ�������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// resizeDrawable();
		findView();
		initFragment();
	}

	// Button���޸�DrawableTop|Bottom|Left|Right��ͼƬ�Ĵ�С
	protected void resizeDrawable() {
		RadioButton rb_home = (RadioButton) findViewById(R.id.rb_home);
		RadioButton rb_shopping_car = (RadioButton) findViewById(R.id.rb_cart);
		RadioButton rb_mine = (RadioButton) findViewById(R.id.rb_mine);
		Drawable[] draw;
		Rect r;
		RadioButton[] rbs = { rb_home, rb_shopping_car, rb_mine };
		for (RadioButton rb : rbs) {
			draw = rb.getCompoundDrawables();
			r = new Rect(0, 0, draw[1].getMinimumWidth() * 2 / 3, draw[1].getMinimumHeight() * 2 / 3);
			draw[1].setBounds(r);
			rb.setCompoundDrawables(null, draw[1], null, null);
		}
	}

	// ����ID
	private void findView() {
		radioGroup = (RadioGroup) findViewById(R.id.rg_select);
		radioGroup.setOnCheckedChangeListener(this);
	}

	// ��ʼ��ÿһ��ҳ��
	private void initFragment() {
		HomeFragment homeFragment = new HomeFragment(this);
		ClassifyFragment classifyFragment = new ClassifyFragment();
		ShoppingCarFragment shoppingCarFragment = new ShoppingCarFragment(this);
		MineFragment mineFragment = new MineFragment(this);
		// �ŵ�������
		mFragments = new Fragment[] { homeFragment, classifyFragment, shoppingCarFragment, mineFragment };
		// ʹ��FragmentTransaction��������Fragmentҳ��
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		// �ύ�޸ģ������õ�һ��ҳ�棬����ҳ
		ft.add(R.id.main_content, homeFragment).commit();
		// ��ʼ������
		mIndex = 0;
		setIndexSelected(mIndex);
	}

	// �������������ķ���
	private void setIndexSelected(int index) {
		// �ж�Ҫ���õ������Ƿ��뵱ǰ������һ��
		if (mIndex == index) {
			return;
		}
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		// ���ص�ǰ����
		ft.hide(mFragments[mIndex]);
		// �ж��Ƿ����
		if (!mFragments[index].isAdded()) {
			ft.add(R.id.main_content, mFragments[index]).show(mFragments[index]);
		} else {
			// ��ʾ����
			ft.show(mFragments[index]);
		}
		// �ύ�޸�
		ft.commit();
		// �ٴθ�ֵ
		mIndex = index;
	}

	// �����·���ť��ѡ�иı��¼��������л���ͬҳ��
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_home:// ��ҳ
			setIndexSelected(0);
			break;
		case R.id.rb_classify:// ����
			setIndexSelected(1);
			break;
		case R.id.rb_cart:// ���ﳵ
			setIndexSelected(2);
			break;
		case R.id.rb_mine:// ��������
			setIndexSelected(3);
			break;
		default:
			setIndexSelected(0);
			break;
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			// ����activityΪtask�������׸�����activity��ʱ����Ч,�����������ı�task�е�activity״̬��
			// ���·��ؼ������ø�����HOMEЧ��һ�������µ��Ӧ�û��ǻص�Ӧ���˳�ǰ��״̬��
			moveTaskToBack(false);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
