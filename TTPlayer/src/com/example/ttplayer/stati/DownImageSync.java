package com.example.ttplayer.stati;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

public class DownImageSync extends AsyncTask<String, Integer, Bitmap> {
	Map<String, Bitmap> maps;
	Bitmap bit;
	InputStream in;
	ImageView img;
	public DownImageSync(Map<String, Bitmap> maps,ImageView img) {
		// TODO Auto-generated constructor stub
		this.maps = maps;
		this.img=img;
	}

	@Override
	protected Bitmap doInBackground(String... params) {
		// TODO Auto-generated method stub

		byte[] data = new byte[1024];
		int i;
		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(
				"http://111.207.25.188:8080/MusicInfo"
						+ params[0]);
	//	Log.i("msg", 1+"");
		try {
			HttpResponse response = client.execute(post);
			HttpEntity entity = response.getEntity();
			in = entity.getContent();
			bit = BitmapFactory.decodeStream(in);
			
			maps.put(params[0], bit);
			//Log.i("msg",2+"");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File file = new File("/mnt/sdcard/ttplayer" + params[0]);
		Log.i("msg", "/mnt/sdcard/ttplayer" + params[0]);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			OutputStream out = new FileOutputStream(file);
			bit.compress(CompressFormat.PNG, 10, out);
			
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bit;
	}
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub

		img.setImageBitmap(result);
		super.onPostExecute(result);
	}
}
