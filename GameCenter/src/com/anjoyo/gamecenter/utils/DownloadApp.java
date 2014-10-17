package com.anjoyo.gamecenter.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

public class DownloadApp extends Thread {
	private String url;
	private String name;
	public int t = 0;
	Context context;
	public boolean flag=false;
	public boolean download=true;

	public DownloadApp(Context context, String url, String name) {
		this.url = "http://www.gamept.cn" + url;
		this.name = name;
		this.context = context;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] data = new byte[1024];
		int i;

		try {
			Log.i("mag", url);
			URL ur = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) ur.openConnection();
			conn.connect();
			InputStream in = conn.getInputStream();
			final String path = Environment.getExternalStorageDirectory()+"/GameCenter/" + name + ".apk";
			File file = new File(path);

			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			
			OutputStream out = new FileOutputStream(file);

			while (download) {
				if (flag) {
					continue;
				}
				if ((i = in.read(data)) !=-1) {
					out.write(data, 0, i);
					t++;
					if (t % 100 == 0) {
						Intent it = new Intent("downloadapp");
						context.sendBroadcast(it);
					}
				} else {
					break;
				}

			}
			Intent it = new Intent("downloadfinished");
			it.putExtra("name", name);
			context.sendBroadcast(it);
			out.close();
			in.close();
		} catch (Exception e) {
			// TODO: handle exception
		}

		super.run();
	}
}
