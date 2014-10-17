package com.anjoyo.gamecenter.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;
/**
 * 图片异步处理
 * @author lixuebin
 *
 */
public class AsyncImageLoader {

	private Map<String, SoftReference<Drawable>> cacheMaps;
	FileUtil fileUtil;

	public AsyncImageLoader() {
		// TODO Auto-generated constructor stub
		cacheMaps = new HashMap<String, SoftReference<Drawable>>();
		fileUtil = new FileUtil();
	}

	/**
	 * 显示图片
	 * @param imageUri图片地址
	 * @param imageView要填充的imageview
	 * @param defaultImage默认图片
	 */
	public void dispalyImageview(String imageUri, ImageView imageView,int defaultImage) {
		if (cacheMaps.containsKey(imageUri)) { // 当缓存有该图片时直接取
			SoftReference<Drawable> rf = cacheMaps.get(imageUri);
			Drawable drawable = rf.get();
			if (drawable != null) { 
				imageView.setImageDrawable(cacheMaps.get(imageUri).get());
				
			} else { // 如果取到的为空，则说明缓存已释放该图片，删除该图片路径
				cacheMaps.remove(imageUri);
			}

		} else { // 如果缓存不存在该图片，则先看sd卡中是否存在，如果不存在则网络下载
			Bitmap bitmap = getDrawableFromSDcard(imageUri);
			if (bitmap != null) {
				imageView.setImageBitmap(bitmap);
			}else {
				imageView.setImageResource(defaultImage);
				new DownLoadImageview(imageView).execute(imageUri);
			}
		}

	}

	/**
	 * 从sd卡取图片
	 * @param imageUri
	 * @return
	 */
	public Bitmap getDrawableFromSDcard(String imageUri){
		File file = fileUtil.getCacheFile(imageUri);
		FileInputStream inputStream = null;
		Bitmap bitmap = null;
		try {
			inputStream = new FileInputStream(file);
			bitmap = BitmapFactory.decodeStream(inputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;	
	}

	/**
	 * 异步加载图片，并把下载的图片存放在sd卡中
	 * @author lixuebin
	 *
	 */
	class DownLoadImageview extends AsyncTask<String, Drawable, Drawable> {

		ImageView imageView;

		public DownLoadImageview(ImageView imageView) {
			super();
			this.imageView = imageView;
		}

		@Override
		protected Drawable doInBackground(String... params) {
			// TODO Auto-generated method stub
			Drawable drawable = null;
			InputStream inputStream = null;
			try {
				inputStream = (InputStream) new URL(params[0]).getContent();
				FileOutputStream fileOutputStream = new FileOutputStream(fileUtil.getCacheFile(params[0]));
				int i = 0;
				byte[] b = new byte[1024];
				while ((i = inputStream.read(b)) != -1) {
					fileOutputStream.write(b, 0, i);
				}
				fileOutputStream.close();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			drawable = Drawable.createFromStream(inputStream, "src");
			cacheMaps.put(params[0], new SoftReference<Drawable>(drawable));
			return drawable;
		}

		@Override
		protected void onPostExecute(Drawable drawable) {
			// TODO Auto-generated method stub
			super.onPostExecute(drawable);
			imageView.setImageDrawable(drawable);
		}

	}

}

