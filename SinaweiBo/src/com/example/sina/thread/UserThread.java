package com.example.sina.thread;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import android.os.Handler;
import android.util.Log;

import com.example.sina.appstatic.AppStatic;
import com.example.sina.util.JsonParse;

public class UserThread extends Thread {
	private String urlstr;
	private Handler handler;
	public UserThread(String s, Handler handler){
		urlstr=s;
		this.handler=handler;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			URL url=new URL(urlstr);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.connect();
			InputStream in=conn.getInputStream();
			byte[] data = new byte[1024];
			int i = 0;
			StringBuffer s = new StringBuffer();
			while ((i = in.read(data)) != -1) {
				s.append(new String(data, 0, i));
			}
			//Log.i("msg", s.toString());
			JSONObject jsonobj=new JSONObject(s.toString());
			AppStatic.user=JsonParse.parseUser(jsonobj);
			handler.sendEmptyMessage(2);
		} catch (Exception e) {
			// TODO: handle exception
		}
		super.run();
	}
}
