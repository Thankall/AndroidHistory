package com.example.sina.thread;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

import com.example.sina.appstatic.AppStatic;
import com.example.sina.instance.Statuse;
import com.example.sina.util.JsonParse;

import android.os.Handler;
import android.util.Log;

public class WeiBoThread extends Thread {
	private String urlstr;
	private Handler handler;
	private boolean isadd;
	public WeiBoThread(String url, Handler handler,boolean isadd) {
		// TODO Auto-generated constructor stub
		this.urlstr=url;
		this.handler=handler;
		this.isadd=isadd;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			//Log.i("msg", urlstr);
			URL url=new URL(urlstr);
			HttpURLConnection conn=(HttpURLConnection) url.openConnection();
			conn.connect();
			InputStream in=conn.getInputStream();
			StringBuffer s = new StringBuffer();
//			Scanner scan=new Scanner(in);
//			while(scan.hasNext()){
//				s.append(scan.next());
//			}
			String temp;
			InputStreamReader isr = new InputStreamReader(in);
			BufferedReader br = new BufferedReader(isr);
			while ((temp=br.readLine())!=null) {
				s.append(temp);
				
			}

			//Log.i("msg", s.toString());
			if(isadd){
				AppStatic.statuses.addAll(JsonParse.parseStatuses(s.toString()));
				handler.sendEmptyMessage(3);
			}else{
				AppStatic.statuses=JsonParse.parseStatuses(s.toString());
				handler.sendEmptyMessage(1);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		super.run();
	}
	
}
