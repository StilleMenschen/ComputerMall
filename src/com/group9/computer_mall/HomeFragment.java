package com.group9.computer_mall;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * 主页界面
 * @author Hasee
 */
public class HomeFragment extends Fragment {

	public static final String TAG = "HomeFragment";
	// 视图
	private View parent;// 主视图
	private Context mContext;// 应用程序上下文
	private ViewPager vp;// 横幅轮换显示控件，继承自Support V4扩展包
	private LinearLayout ll;// 横幅下的小圆点
	private LinearLayout linearLayout;// 商品展示的控件
	private LayoutInflater mInflater;// 用于实例化xml文件的类
	// 数据
	private List<ImageView> imageList;// 横幅图片ImageView数组
	private ArrayList<ImageView> dotsList;// 横幅下的ImageView小圆点数组
	// 横幅图片资源ID
	private int[] banner_img = { R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4,
			R.drawable.banner5, R.drawable.banner6 };
	// 商品图片资源ID
	private int[] commodity_img = { R.drawable.hp1, R.drawable.hasee2, R.drawable.shinelon3, R.drawable.dell4,
			R.drawable.lenovo5, R.drawable.asus6, R.drawable.msi7, R.drawable.thunderobot8 };
	// 商品描述
	private String[] commodity_desc = { "暗影精灵", "神舟战神", "毁灭者", "灵越游匣", "联想小新", "雷霆勇士", "微星电脑", "雷神911" };
	// 事件
	private InnerHandler handler;

	/**
	 * 处理横幅轮换的Handler，通过不断发送Message给Handler进行图片切换
	 * 
	 * @author Hasee
	 */
	public static class InnerHandler extends Handler {

		private ViewPager vp;

		public InnerHandler(ViewPager viewPager) {
			vp = viewPager;
		}

		@Override
		public void handleMessage(Message msg) {
			// 获取横幅当前显示的页面索引
			int currentItem = vp.getCurrentItem();
			// 切换至下一个页面
			vp.setCurrentItem(++currentItem);
			// 再次调用，5秒后再轮换图片
			sendEmptyMessageDelayed(1, 5000);
		}
	}

	private class MyTask extends AsyncTask<Integer, Void, Void> {

		private Random r;

		public MyTask() {
			r = new Random();
		}

		@Override
		protected Void doInBackground(Integer... params) {
			// 初始化一个10行4列的商品界面
			initGrid(10, 4);
			return null;
		}

		protected int getRandom(int min, int max) {
			return r.nextInt(max) % (max - min + 1) + min;
		}

		@SuppressLint("InflateParams")
		private void initGrid(int rows, int columns) {
			LinearLayout child_row;// 代表一行商品
			LinearLayout child_column;// 代表每行中的每一个商品
			ImageView imageView;// 商品图片
			TextView textView1;// 商品描述
			TextView textView2;// 商品价格
			LinearLayout.LayoutParams params_row;// 线性布局的属性，比如长宽内外边距等
			LinearLayout.LayoutParams params_column;// 线性布局的属性，比如长宽内外边距等
			int resourceCount = 0;
			for (int i = 0; i < rows; i++) {// 第一层循环，表示每一行
				child_row = new LinearLayout(mContext);
				// 设置控件的长宽
				params_row = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				child_row.setLayoutParams(params_row);
				child_row.setWeightSum(columns);// 设置线性布局的比值
				for (int j = 0; j < columns; j++, resourceCount++) {// 第二层循环，表示每一行中的每一列
					// 使用外部已经写好的xml布局实例化一个商品Item元素
					child_column = (LinearLayout) mInflater.inflate(R.layout.goold_layout, null);
					// 查找ID
					imageView = (ImageView) child_column.findViewById(R.id.goold_imageView);
					textView1 = (TextView) child_column.findViewById(R.id.goold_textView1);
					textView2 = (TextView) child_column.findViewById(R.id.goold_textView2);
					// 设置数据
					imageView.setImageResource(commodity_img[resourceCount]);
					// 由于不能直接获取ImageView的图片资源ID，所以通过设置tag来保存图片资源ID
					imageView.setTag(commodity_img[resourceCount]);
					textView1.setText(commodity_desc[resourceCount]);
					textView2.setText("￥" + getRandom(2000, 5000));// 随机生产一个价格

					params_column = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
					params_column.weight = 1f;// 设置Item元素的比值为1
					child_column.setOnClickListener(new View.OnClickListener() {
						// 监听Item元素的Click事件，点击后打开详细页面
						@Override
						public void onClick(View v) {
							ImageView imageView = (ImageView) v.findViewById(R.id.goold_imageView);
							int resId = (Integer) imageView.getTag();// 获取保存到tag中的图片资源ID
							TextView tv1 = (TextView) v.findViewById(R.id.goold_textView1);
							TextView tv2 = (TextView) v.findViewById(R.id.goold_textView2);
							Intent intent = new Intent(mContext, DetailsActivity.class);
							// 将商品信息缓存到Intent中
							intent.putExtra("resid", resId);
							intent.putExtra("desc", tv1.getText().toString());
							intent.putExtra("price", tv2.getText().toString());
							startActivity(intent);
						}
					});
					// 将Item添加到表示一行的线性布局中
					child_row.addView(child_column, params_column);
					if (resourceCount >= 7) {
						resourceCount = 0;
					}
				}
				// 将每一行的线性布局添加到商品主界面中
				linearLayout.addView(child_row);
			}
		}
	}

	public HomeFragment(Context context) {
		mContext = context;// 获取应用程序上下文
		// 获取LayoutInflater实例
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		parent = inflater.inflate(R.layout.fragment_home, container, false);
		findView();
		// 异步加载商品界面
		new MyTask().execute(0);
		// 初始化横幅图片数据
		initImages();
		// 初始化横幅下的小圆点
		initDots();
		// 初始化横幅适配器
		initAdapter();
		return parent;
	}

	// 查找控件
	private void findView() {
		vp = (ViewPager) parent.findViewById(R.id.vp);
		ll = (LinearLayout) parent.findViewById(R.id.ll);
		linearLayout = (LinearLayout) parent.findViewById(R.id.cotent);
	}

	// 初始化横幅下的小圆点
	private void initDots() {
		// 实例化一个集合存放小圆点
		dotsList = new ArrayList<ImageView>();
		for (int i = 0; i < banner_img.length; i++) {
			ImageView view = new ImageView(mContext);
			// 把第一个小圆点设置为高亮状态
			if (i == 0) {
				view.setImageResource(R.drawable.dots_focus);
			} else {
				view.setImageResource(R.drawable.dots_normal);
			}
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			// 设置小圆点的边距
			params.setMargins(5, 3, 5, 3);
			// 把小圆点添加到布局中
			ll.addView(view, params);
			// 把小圆点添加到集合
			dotsList.add(view);
		}
	}

	// 初始化横幅图片数据
	private void initImages() {
		// 实例化一个集合，用于存放图片
		imageList = new ArrayList<ImageView>();
		// 将横幅资源图片放到ImageView数组中
		for (int i = 0; i < banner_img.length; i++) {
			ImageView view = new ImageView(mContext);
			view.setScaleType(ScaleType.FIT_XY);// 设置图片长宽比例为拉伸
			view.setImageResource(banner_img[i]);
			// 添加到集合
			imageList.add(view);
		}
	}

	// 初始化横幅适配器
	private void initAdapter() {
		handler = new InnerHandler(vp);
		// 设置适配器
		VPAdapter adapter = new VPAdapter(imageList, handler);
		vp.setAdapter(adapter);
		// 初始化ViewPager的位置
		vp.setCurrentItem(imageList.size() * 1000);
		// 页面改变监听
		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// 遍历小圆点集合
				for (int i = 0; i < dotsList.size(); i++) {
					if (position % dotsList.size() == i) {
						// 设置当前小圆点高亮
						dotsList.get(i).setImageResource(R.drawable.dots_focus);
					} else {
						// 设置其他小圆点为灰色
						dotsList.get(i).setImageResource(R.drawable.dots_normal);
					}
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
			}

			@Override
			public void onPageScrollStateChanged(int state) {
			}
		});
		// 5秒发送一个消息，即梅5秒切换一次图片
		handler.sendEmptyMessageDelayed(1, 5000);
	}
}
