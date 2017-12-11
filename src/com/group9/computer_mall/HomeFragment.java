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
 * ��ҳ����
 * @author Hasee
 */
public class HomeFragment extends Fragment {

	public static final String TAG = "HomeFragment";
	// ��ͼ
	private View parent;// ����ͼ
	private Context mContext;// Ӧ�ó���������
	private ViewPager vp;// ����ֻ���ʾ�ؼ����̳���Support V4��չ��
	private LinearLayout ll;// ����µ�СԲ��
	private LinearLayout linearLayout;// ��Ʒչʾ�Ŀؼ�
	private LayoutInflater mInflater;// ����ʵ����xml�ļ�����
	// ����
	private List<ImageView> imageList;// ���ͼƬImageView����
	private ArrayList<ImageView> dotsList;// ����µ�ImageViewСԲ������
	// ���ͼƬ��ԴID
	private int[] banner_img = { R.drawable.banner1, R.drawable.banner2, R.drawable.banner3, R.drawable.banner4,
			R.drawable.banner5, R.drawable.banner6 };
	// ��ƷͼƬ��ԴID
	private int[] commodity_img = { R.drawable.hp1, R.drawable.hasee2, R.drawable.shinelon3, R.drawable.dell4,
			R.drawable.lenovo5, R.drawable.asus6, R.drawable.msi7, R.drawable.thunderobot8 };
	// ��Ʒ����
	private String[] commodity_desc = { "��Ӱ����", "����ս��", "������", "��Խ��ϻ", "����С��", "������ʿ", "΢�ǵ���", "����911" };
	// �¼�
	private InnerHandler handler;

	/**
	 * �������ֻ���Handler��ͨ�����Ϸ���Message��Handler����ͼƬ�л�
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
			// ��ȡ�����ǰ��ʾ��ҳ������
			int currentItem = vp.getCurrentItem();
			// �л�����һ��ҳ��
			vp.setCurrentItem(++currentItem);
			// �ٴε��ã�5������ֻ�ͼƬ
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
			// ��ʼ��һ��10��4�е���Ʒ����
			initGrid(10, 4);
			return null;
		}

		protected int getRandom(int min, int max) {
			return r.nextInt(max) % (max - min + 1) + min;
		}

		@SuppressLint("InflateParams")
		private void initGrid(int rows, int columns) {
			LinearLayout child_row;// ����һ����Ʒ
			LinearLayout child_column;// ����ÿ���е�ÿһ����Ʒ
			ImageView imageView;// ��ƷͼƬ
			TextView textView1;// ��Ʒ����
			TextView textView2;// ��Ʒ�۸�
			LinearLayout.LayoutParams params_row;// ���Բ��ֵ����ԣ����糤������߾��
			LinearLayout.LayoutParams params_column;// ���Բ��ֵ����ԣ����糤������߾��
			int resourceCount = 0;
			for (int i = 0; i < rows; i++) {// ��һ��ѭ������ʾÿһ��
				child_row = new LinearLayout(mContext);
				// ���ÿؼ��ĳ���
				params_row = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
						LinearLayout.LayoutParams.WRAP_CONTENT);
				child_row.setLayoutParams(params_row);
				child_row.setWeightSum(columns);// �������Բ��ֵı�ֵ
				for (int j = 0; j < columns; j++, resourceCount++) {// �ڶ���ѭ������ʾÿһ���е�ÿһ��
					// ʹ���ⲿ�Ѿ�д�õ�xml����ʵ����һ����ƷItemԪ��
					child_column = (LinearLayout) mInflater.inflate(R.layout.goold_layout, null);
					// ����ID
					imageView = (ImageView) child_column.findViewById(R.id.goold_imageView);
					textView1 = (TextView) child_column.findViewById(R.id.goold_textView1);
					textView2 = (TextView) child_column.findViewById(R.id.goold_textView2);
					// ��������
					imageView.setImageResource(commodity_img[resourceCount]);
					// ���ڲ���ֱ�ӻ�ȡImageView��ͼƬ��ԴID������ͨ������tag������ͼƬ��ԴID
					imageView.setTag(commodity_img[resourceCount]);
					textView1.setText(commodity_desc[resourceCount]);
					textView2.setText("��" + getRandom(2000, 5000));// �������һ���۸�

					params_column = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
					params_column.weight = 1f;// ����ItemԪ�صı�ֵΪ1
					child_column.setOnClickListener(new View.OnClickListener() {
						// ����ItemԪ�ص�Click�¼�����������ϸҳ��
						@Override
						public void onClick(View v) {
							ImageView imageView = (ImageView) v.findViewById(R.id.goold_imageView);
							int resId = (Integer) imageView.getTag();// ��ȡ���浽tag�е�ͼƬ��ԴID
							TextView tv1 = (TextView) v.findViewById(R.id.goold_textView1);
							TextView tv2 = (TextView) v.findViewById(R.id.goold_textView2);
							Intent intent = new Intent(mContext, DetailsActivity.class);
							// ����Ʒ��Ϣ���浽Intent��
							intent.putExtra("resid", resId);
							intent.putExtra("desc", tv1.getText().toString());
							intent.putExtra("price", tv2.getText().toString());
							startActivity(intent);
						}
					});
					// ��Item��ӵ���ʾһ�е����Բ�����
					child_row.addView(child_column, params_column);
					if (resourceCount >= 7) {
						resourceCount = 0;
					}
				}
				// ��ÿһ�е����Բ�����ӵ���Ʒ��������
				linearLayout.addView(child_row);
			}
		}
	}

	public HomeFragment(Context context) {
		mContext = context;// ��ȡӦ�ó���������
		// ��ȡLayoutInflaterʵ��
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		parent = inflater.inflate(R.layout.fragment_home, container, false);
		findView();
		// �첽������Ʒ����
		new MyTask().execute(0);
		// ��ʼ�����ͼƬ����
		initImages();
		// ��ʼ������µ�СԲ��
		initDots();
		// ��ʼ�����������
		initAdapter();
		return parent;
	}

	// ���ҿؼ�
	private void findView() {
		vp = (ViewPager) parent.findViewById(R.id.vp);
		ll = (LinearLayout) parent.findViewById(R.id.ll);
		linearLayout = (LinearLayout) parent.findViewById(R.id.cotent);
	}

	// ��ʼ������µ�СԲ��
	private void initDots() {
		// ʵ����һ�����ϴ��СԲ��
		dotsList = new ArrayList<ImageView>();
		for (int i = 0; i < banner_img.length; i++) {
			ImageView view = new ImageView(mContext);
			// �ѵ�һ��СԲ������Ϊ����״̬
			if (i == 0) {
				view.setImageResource(R.drawable.dots_focus);
			} else {
				view.setImageResource(R.drawable.dots_normal);
			}
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			// ����СԲ��ı߾�
			params.setMargins(5, 3, 5, 3);
			// ��СԲ����ӵ�������
			ll.addView(view, params);
			// ��СԲ����ӵ�����
			dotsList.add(view);
		}
	}

	// ��ʼ�����ͼƬ����
	private void initImages() {
		// ʵ����һ�����ϣ����ڴ��ͼƬ
		imageList = new ArrayList<ImageView>();
		// �������ԴͼƬ�ŵ�ImageView������
		for (int i = 0; i < banner_img.length; i++) {
			ImageView view = new ImageView(mContext);
			view.setScaleType(ScaleType.FIT_XY);// ����ͼƬ�������Ϊ����
			view.setImageResource(banner_img[i]);
			// ��ӵ�����
			imageList.add(view);
		}
	}

	// ��ʼ�����������
	private void initAdapter() {
		handler = new InnerHandler(vp);
		// ����������
		VPAdapter adapter = new VPAdapter(imageList, handler);
		vp.setAdapter(adapter);
		// ��ʼ��ViewPager��λ��
		vp.setCurrentItem(imageList.size() * 1000);
		// ҳ��ı����
		vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// ����СԲ�㼯��
				for (int i = 0; i < dotsList.size(); i++) {
					if (position % dotsList.size() == i) {
						// ���õ�ǰСԲ�����
						dotsList.get(i).setImageResource(R.drawable.dots_focus);
					} else {
						// ��������СԲ��Ϊ��ɫ
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
		// 5�뷢��һ����Ϣ����÷5���л�һ��ͼƬ
		handler.sendEmptyMessageDelayed(1, 5000);
	}
}
