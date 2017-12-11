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
	public static final byte ALL_CHECKED = 1;// ��������Ԫ�ر�ѡ��
	public static final byte SECTION_CHECKED = 0;// ������Ԫ�ر�ѡ��
	public static final byte NO_CHECKED = -1;// ����û��Ԫ�ر�ѡ��

	// ���ⲿ���õĽӿ�
	interface PayListener {
		void onCheckedChange(int position, boolean isChecked);
	}

	// ListView��ÿһ��Item��ʵ������
	class ViewHolder {
		ImageView imageView;
		TextView textView1;
		TextView textView2;
		CheckBox checkBox;
	}

	private Context mContext;
	private LayoutInflater mInflater;
	private PayListener mListener;
	private ArrayList<Commodity> mArrayList;// ����ÿһ��ItemԪ�ص�����
	private int total;// ��Ʒ�ܼ�
	private byte isAllCheck;// �Ƿ�ȫ��ѡ��

	public ShoppingCarAdapter(Context context, PayListener listener, List<Commodity> arrayList) {
		mContext = context;
		mListener = listener;
		mArrayList = new ArrayList<Commodity>();
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		total = 0;
		// ����Ʒ׷�ӵ��ڲ�������
		for (Commodity commodity : arrayList) {
			mArrayList.add(commodity);
		}
	}

	@Override
	public int getCount() {
		return mArrayList.size();// ��ȡԪ������
	}

	@Override
	public Commodity getItem(int position) {
		return mArrayList.get(position);// ��ȡָ��λ�õ�Ԫ��
	}

	@Override
	public long getItemId(int position) {
		return position;// ��ȡID��Ԫ�ص�λ��
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Log.w(TAG, "getView");
		ViewHolder holder;
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.list_item, null);// ����һ����Ԫ�ص�xml����
			// ʹ��ViewHolder�����棬�����Ͳ���Ҫÿ�ζ��ظ�ִ�м���xml���ֺͲ���ID
			holder = new ViewHolder();
			// ����ID
			holder.checkBox = (CheckBox) convertView.findViewById(R.id.list_item_cb);
			holder.imageView = (ImageView) convertView.findViewById(R.id.list_item_iv);
			holder.textView1 = (TextView) convertView.findViewById(R.id.list_item_tv1);
			holder.textView2 = (TextView) convertView.findViewById(R.id.list_item_tv2);
			convertView.setTag(holder);// �ŵ�convertView��tag�б���
		} else {
			holder = (ViewHolder) convertView.getTag();// ��tag��ȡ��
		}
		Commodity commodity = getItem(position);// ��ȡԪ��
		// ����ͼƬ���ı�����
		holder.imageView.setImageResource(commodity.imageId);
		holder.textView1.setText(commodity.description);
		holder.textView2.setText(commodity.price);
		holder.checkBox.setChecked(commodity.pay);// ����ѡ��
		holder.checkBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			// ÿһ��Ԫ�ص�ѡ�м�����
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Log.d(TAG, "onCheckedChanged---" + isChecked);
				changePay(position, isChecked);// �ı���Ʒ�Ĺ���״̬
				mListener.onCheckedChange(position, isChecked);// �ص����ⲿ�Ľӿ�
			}
		});
		return convertView;// �������View���󷵻أ������ظ�ʹ����Դ
	}

	public void addData(List<Commodity> list) {
		// ����Ʒ׷�ӵ��ڲ�������
		for (Commodity commodity : list) {
			mArrayList.add(commodity);
		}
		notifyDataSetChanged();// ֪ͨListViewˢ�½�����ʾ
	}

	// �ı���Ʒ�Ĺ���״̬
	private void changePay(int position, boolean isChecked) {
		Commodity c = getItem(position);
		c.pay = isChecked;// �ı乺��״̬(true/false)
		mArrayList.set(position, c);
		changeTotal(c);// �ı��ܼ�
	}

	// �ı��ܼ�
	private void changeTotal(Commodity c) {
		Log.d(TAG, "changeTotal---" + total);
		if (c.pay) {// ����������
			total += c.getPrice();// getPrice�������Ի�ȡ��Ʒ�۸�
		} else {
			if (NO_CHECKED == isAllCheck) {// ���ȫ��δѡ�У����ܼ�ֱ������0
				setTotal(0);
				return;
			}
			// �����������
			total -= c.getPrice();
			if (getTotal() < 0) {// �������ܼ۳��ָ���
				setTotal(0);
			}
		}
	}

	// ���¼����ܼ�
	public synchronized void preparedTotal() {
		setTotal(0);
		for (Commodity c : mArrayList) {
			changeTotal(c);
		}
	}

	// �ж��Ƿ�ȫ��Ԫ��ѡ��
	public int getAllChecked() {
		if (mArrayList.isEmpty()) {// ��������Ԫ�أ�������Ԫ�ر�ѡ��
			return NO_CHECKED;
		}
		int checkCount = 0;
		for (Commodity commodity : mArrayList) {
			if (commodity.pay) {
				checkCount++;// ͳ�ƹ��������
			}
		}
		if (checkCount == getCount()) {
			isAllCheck = ALL_CHECKED;// ͳ��������Ԫ������һ����Ϊȫ��ѡ��
			return ALL_CHECKED;
		} else if (checkCount <= 0) {
			isAllCheck = NO_CHECKED;// ͳ������Ϊ0��Ϊû���κ�Ԫ�ر�ѡ��
			return NO_CHECKED;
		} else {
			isAllCheck = SECTION_CHECKED;// ͳ��������Ԫ����������Ϊ����ѡ��
			return SECTION_CHECKED;
		}
	}

	// ����ȫ��Ԫ�ص�ѡ��״̬
	public void setAllChecked(boolean allChecked) {
		if (allChecked) {// ȫ��ѡ��
			for (Commodity commodity : mArrayList) {
				commodity.pay = true;
			}
			isAllCheck = ALL_CHECKED;
		} else {// ȫ����ѡ��
			for (Commodity commodity : mArrayList) {
				commodity.pay = false;
			}
			isAllCheck = NO_CHECKED;
		}
		notifyDataSetChanged();// ֪ͨListViewˢ�½�����ʾ
	}

	// ����ѡ�е���Ʒ
	public void clear() {
		if (mArrayList.isEmpty()) {// ����Ϊ���򲻲���
			return;
		}
		Commodity commodity;
		for (int i = 0; i < getCount();) {
			commodity = getItem(i);
			if (commodity.pay) {// ���Ϊѡ�����Ƴ�
				mArrayList.remove(i);
			} else {// ������������������
				i++;
			}
		}
		if (getCount() > 128) {// ������鳤�ȴ���128
			mArrayList.trimToSize();// ���µ��������С
		}
		notifyDataSetChanged();// ֪ͨListViewˢ�½�����ʾ
	}

	// �����ܼ�
	public int getTotal() {
		return total;
	}

	// �����ܼ�
	public void setTotal(int total) {
		this.total = total;
	}

}
