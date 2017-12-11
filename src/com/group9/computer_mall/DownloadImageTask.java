package com.group9.computer_mall;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

// 异步处理图片下载
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	ImageView bmImage;

	public DownloadImageTask(ImageView bmImage) {
		this.bmImage = bmImage;// 接收外部传递进来的ImageView
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];// 图片链接
		Bitmap mIcon11 = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();// 打开链接
			mIcon11 = BitmapFactory.decodeStream(in);// 下载图片
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return mIcon11;
	}

	protected void onPostExecute(Bitmap result) {
		bmImage.setImageBitmap(result);// 为ImageView设置图片
	}
}
