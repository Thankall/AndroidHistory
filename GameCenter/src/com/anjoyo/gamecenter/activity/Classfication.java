package com.anjoyo.gamecenter.activity;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.adapter.ClassificationAdapter;
import com.anjoyo.gamecenter.bean.AnjoyoClassificationBean;
import com.anjoyo.gamecenter.utils.ClassificationHlr;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class Classfication extends Activity implements OnItemClickListener {

	ArrayList<AnjoyoClassificationBean> anjoyoClassificationBeans;
	String a;
	ListView listView;
	String imgurl;
	ImageView imgIcon;
	ClassificationAdapter adapter;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.classfication);
		listView = (ListView) findViewById(R.id.lv_class);
		anjoyoClassificationBeans = new ArrayList<AnjoyoClassificationBean>();

		init();
		
	  
		
				
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
				
				int a = anjoyoClassificationBeans.get(arg2).getClassid();
				
			    
				
				Intent intent = new Intent();
				intent.putExtra("id", a);
				
				intent.setClass(Classfication.this, Hot_NewAvtivity.class);
				startActivity(intent);

			}
		});

	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				adapter = new ClassificationAdapter(Classfication.this,//������
						anjoyoClassificationBeans);
				listView.setAdapter(adapter);

			}
		}
	};

	public void init() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				anjoyoClassificationBeans = new ArrayList<AnjoyoClassificationBean>();
				ClassificationHlr classificationHlr = null;

				try {
					anjoyoClassificationBeans = classificationHlr.getinfo();//���
					for (int i = 0; i < anjoyoClassificationBeans.size(); i++) {
						
System.out.println(anjoyoClassificationBeans.get(i).getClassimg()+"222222222222");//������ͼƬ
					}
					
					
					handler.sendEmptyMessage(1);

				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}).start();
		
		
		  for (int i = 0; i < anjoyoClassificationBeans.size(); i++) {
				String imgurl="http://www.gamept.cn"+anjoyoClassificationBeans.get(i).getClassimg();//������ͼƬ
				System.out.println(imgurl+"11111111111");
			}
	//	new AsyncImageLoader().dispalyImageview(imgurl, imgIcon, R.drawable.pic_c);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}

}
