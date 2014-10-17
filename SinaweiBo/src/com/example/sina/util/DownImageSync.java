package com.example.sina.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;



import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;

public class DownImageSync extends AsyncTask<String, String, Bitmap>{
	private String u;
	private ImageView imgv;
	private Map<String, Bitmap> maps;
	public DownImageSync(Map<String, Bitmap> maps, ImageView imgv) {
		// TODO Auto-generated constructor stub
		this.maps = maps;
		this.imgv = imgv;
	}
	@Override
	protected Bitmap doInBackground(String... params) {
		Bitmap bit = null;
		u = params[0];
		try {
			URL ur = new URL(params[0]);
			// Log.i("msg", blogers.get(position).getAvatar());
			HttpURLConnection conn = (HttpURLConnection) ur.openConnection();
			conn.setDoInput(true);
			conn.connect();
			InputStream in = conn.getInputStream();
			bit = BitmapFactory.decodeStream(in);
			in.close();
			maps.put(u, bit);
		} catch (Exception e) {
			// TODO: handle exception
		}

		return bit;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		if (result != null && u.equals(imgv.getTag())) {
			imgv.setVisibility(View.VISIBLE);
			imgv.setImageBitmap(result);

		}
		// if(result==null){
		// avatar.setImageResource(R.drawable.sample_face);
		// }
		super.onPostExecute(result);
	}

}
