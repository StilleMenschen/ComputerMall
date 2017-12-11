package com.group9.computer_mall;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

public class ShoppingCarAdapter extends BaseAdapter {

	public static final String TAG = "ShoppingCarAdapter";
	public static final byte ALL_CHECKED = 1;// 代表所有元素被选中
	public static final byte SECTION_CHECKED = 0;// 代表部分元素被选中
	public static final byte NO_CHECKED = -1;// 代表没有元素被选中

	// 给外部调用的接口
	interface PayListener {
		void onCheckedChange(int position, boolean isChecked);
	}

	// ListView中每一个Item的实例对象
	class ViewHolder {
		ImageView imageView;
		TextView textView1;
		TextView textView2;
		CheckBox checkBox;
	}

	private Context mContext;
	private LayoutInflater mInflater;
	private PayListener mListener;
	private ArrayList<Commodity> mArrayList;// 保存每一个Item元素的数组
	private int total;// 商品总价
	private byte isAllCheck;// 是否全部选中

	public ShoppingCarAdapter(Context context, PayListener listener, List<Commodity> arrayList) {
		mContext = context;
		mListener = listener;
		mArrayList = new ArrayList<Commodity>();
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		total = 0;
		// 将商品追加到内部数组中
		for (Commodity commodity : arrayList) {
			mArrayList.add(commodity);
		}
	}

	@Override
	public int getCount() {
		return mArrayList.size();// 获取元素数量
	}

	@Override
	public Commodity getItem(int position) {
		return mArrayList.get(position);// 获取指定位置的元素
	}

	@Override
	public long getItemId(int position) {
		return position;// 获取ID即元素的位置
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.w(TAG, "getView");
		ViewHolder holder;
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.list_item, null);// 加载一个子元素的xml布局
			// 使用ViewHolder来缓存，这样就不需要每次都重复执行加载xml布局和查找ID
			holder = new ViewHolder();
			// 查找ID
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.list_item_cb);
			holder.imageView = (ImageView) convertView.findViewById(R.id.list_item_iv);
			holder.textView1 = (TextView) convertView.findViewById(R.id.list_item_tv1);
			holder.textView2 = (TextView) convertView.findViewById(R.id.list_item_tv2);
			convertView.setTag(holder);// 放到convertView的tag中保存
		} else {
			holder = (ViewHolder) convertView.getTag();// 从tag中取出
		}
		Commodity commodity = getItem(position);// 获取元素
		// 设置图片或文本数据
		holder.imageView.setImageResource(commodity.imageId);
		holder.textView1.setText(commodity.description);
		holder.textView2.setText(commodity.price);
		holder.checkBox.setChecked(commodity.pay);// 设置选中
		holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			// 每一个元素的选中监听器
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.d(TAG, "onCheckedChanged---" + isChecked);
				changePay(position, isChecked);// 改变商品的购买状态
				mListener.onCheckedChange(position, isChecked);// 回调至外部的接口
			}
		});
		return convertView;// 将缓存的View对象返回，可以重复使用资源
	}

	public void addData(List<Commodity> list) {
		// 将商品追加到内部数组中
		for (Commodity commodity : list) {
			mArrayList.add(commodity);
		}
		notifyDataSetChanged();// 通知ListView刷新界面显示
	}

	// 改变商品的购买状态
	private void changePay(int position, boolean isChecked) {
		Commodity c = getItem(position);
		c.pay = isChecked;// 改变购买状态(true/false)
		mArrayList.set(position, c);
		changeTotal(c);// 改变总价
	}

	// 改变总价
	private void changeTotal(Commodity c) {
		Log.d(TAG, "changeTotal---" + total);
		if (c.pay) {// 购买则增加
			total += c.getPrice();// getPrice方法可以获取商品价格
		} else {
			if (NO_CHECKED == isAllCheck) {// 如果全部未选中，则总价直接设置0
				setTotal(0);
				return;
			}
			// 不购买则减少
			total -= c.getPrice();
			if (getTotal() < 0) {// 不允许总价出现负数
				setTotal(0);
			}
		}
	}

	// 重新计算总价
	public synchronized void preparedTotal() {
		setTotal(0);
		for (Commodity c : mArrayList) {
			changeTotal(c);
		}
	}

	// 判断是否全部元素选中
	public int getAllChecked() {
		if (mArrayList.isEmpty()) {// 数组中无元素，不会有元素被选中
			return NO_CHECKED;
		}
		int checkCount = 0;
		for (Commodity commodity : mArrayList) {
			if (commodity.pay) {
				checkCount++;// 统计购买的数量
			}
		}
		if (checkCount == getCount()) {
			isAllCheck = ALL_CHECKED;// 统计数量与元素数量一致则为全部选中
			return ALL_CHECKED;
		} else if (checkCount <= 0) {
			isAllCheck = NO_CHECKED;// 统计数量为0则为没有任何元素被选中
			return NO_CHECKED;
		} else {
			isAllCheck = SECTION_CHECKED;// 统计数量比元素数量少则为部分选中
			return SECTION_CHECKED;
		}
	}

	// 设置全部元素的选中状态
	public void setAllChecked(boolean allChecked) {
		if (allChecked) {// 全部选中
			for (Commodity commodity : mArrayList) {
				commodity.pay = true;
			}
			isAllCheck = ALL_CHECKED;
		} else {// 全部不选中
			for (Commodity commodity : mArrayList) {
				commodity.pay = false;
			}
			isAllCheck = NO_CHECKED;
		}
		notifyDataSetChanged();// 通知ListView刷新界面显示
	}

	// 购买选中的商品
	public void clear() {
		if (mArrayList.isEmpty()) {// 数组为空则不操作
			return;
		}
		Commodity commodity;
		for (int i = 0; i < getCount();) {
			commodity = getItem(i);
			if (commodity.pay) {// 如果为选中则移除
				mArrayList.remove(i);
			} else {// 否则索引继续往下走
				i++;
			}
		}
		if (getCount() > 128) {// 如果数组长度大于128
			mArrayList.trimToSize();// 重新调整数组大小
		}
		notifyDataSetChanged();// 通知ListView刷新界面显示
	}

	// 返回总价
	public int getTotal() {
		return total;
	}

	// 设置总价
	public void setTotal(int total) {
		this.total = total;
	}

}
