package com.group9.computer_mall;

import java.util.List;

import com.group9.computer_mall.HomeFragment.InnerHandler;

import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class VPAdapter extends PagerAdapter {

	private List<ImageView> imageList;
	private InnerHandler handler;

	public VPAdapter(List<ImageView> imageList, InnerHandler handler) {
		this.imageList = imageList;
		this.handler = handler;
	}

	@Override
	public int getCount() {
		return Integer.MAX_VALUE;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		ImageView view = imageList.get(position % imageList.size());

		// ���ú���Ĵ��ؼ��������ڵ��ͼƬ��ʱ��ֹͣ�ֲ�
		view.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				v.performClick();// �Ȼص�Ԫ�ص�Click�¼�
				switch (event.getAction()) {
				case MotionEvent.ACTION_CANCEL:// ��껮��
					// ������Ϣ
					handler.sendEmptyMessageDelayed(1, 5000);
					break;
				case MotionEvent.ACTION_DOWN:// ��갴��
					// �������handler��Ϣ�ص���Ϣ�����лٵ�����
					handler.removeCallbacksAndMessages(null);
					break;
				case MotionEvent.ACTION_UP:// ���̧��
					// ������Ϣ
					handler.sendEmptyMessageDelayed(1, 5000);
					break;
				}
				return true;
			}
		});
		container.addView(view);
		return view;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// super.destroyItem(container, position, object);
		container.removeView((View) object);
	}

}
