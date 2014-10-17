package com.anjoyo.gamecenter.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.widget.ImageView;

public class AsyncDownloadImageview extends
		AsyncTask<String, Drawable, Drawable> {

	ImageView imageView;

	public AsyncDownloadImageview(ImageView imageView) {
		super();
		this.imageView = imageView;
	}

	@Override
	protected Drawable doInBackground(String... params) {
		// TODO Auto-generated method stub
		InputStream inputStream = null;
		try {
			inputStream = (InputStream) new URL(params[0]).getContent();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Drawable.createFromStream(inputStream, "src");
	}

	@Override
	protected void onPostExecute(Drawable drawable) {
		// TODO Auto-generated method stub
		super.onPostExecute(drawable);
		imageView.setImageDrawable(drawable);
	}

}
