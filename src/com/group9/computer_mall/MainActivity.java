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

	private RadioGroup radioGroup;// 界面下方的按钮菜单
	private Fragment[] mFragments;// 装载每一个页面的数组
	private int mIndex;// 代表当前页面的索引

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// resizeDrawable();
		findView();
		initFragment();
	}

	// Button中修改DrawableTop|Bottom|Left|Right的图片的大小
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

	// 查找ID
	private void findView() {
		radioGroup = (RadioGroup) findViewById(R.id.rg_select);
		radioGroup.setOnCheckedChangeListener(this);
	}

	// 初始化每一个页面
	private void initFragment() {
		HomeFragment homeFragment = new HomeFragment(this);
		ClassifyFragment classifyFragment = new ClassifyFragment();
		ShoppingCarFragment shoppingCarFragment = new ShoppingCarFragment(this);
		MineFragment mineFragment = new MineFragment(this);
		// 放到数组中
		mFragments = new Fragment[] { homeFragment, classifyFragment, shoppingCarFragment, mineFragment };
		// 使用FragmentTransaction类来管理Fragment页面
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		// 提交修改，并设置第一个页面，即主页
		ft.add(R.id.main_content, homeFragment).commit();
		// 初始化索引
		mIndex = 0;
		setIndexSelected(mIndex);
	}

	// 用来设置索引的方法
	private void setIndexSelected(int index) {
		// 判断要设置的索引是否与当前的索引一致
		if (mIndex == index) {
			return;
		}
		FragmentManager fragmentManager = getFragmentManager();
		FragmentTransaction ft = fragmentManager.beginTransaction();
		// 隐藏当前界面
		ft.hide(mFragments[mIndex]);
		// 判断是否添加
		if (!mFragments[index].isAdded()) {
			ft.add(R.id.main_content, mFragments[index]).show(mFragments[index]);
		} else {
			// 显示界面
			ft.show(mFragments[index]);
		}
		// 提交修改
		ft.commit();
		// 再次赋值
		mIndex = index;
	}

	// 监听下方按钮的选中改变事件，用于切换不同页面
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch (checkedId) {
		case R.id.rb_home:// 主页
			setIndexSelected(0);
			break;
		case R.id.rb_classify:// 分类
			setIndexSelected(1);
			break;
		case R.id.rb_cart:// 购物车
			setIndexSelected(2);
			break;
		case R.id.rb_mine:// 个人中心
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
			// 仅当activity为task根（即首个启动activity）时才生效,这个方法不会改变task中的activity状态，
			// 按下返回键的作用跟按下HOME效果一样；重新点击应用还是回到应用退出前的状态；
			moveTaskToBack(false);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
