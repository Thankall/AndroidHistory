package com.example.ttplayer.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ttplayer.R;
import com.example.ttplayer.adapter.DingZhiAdapter;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class DingZhi extends Activity {
	private DingZhiAdapter ad;
	private TextView title;
	private ListView view;
	private Button selall,seldel;
	private ImageButton share,back;
	
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dingzhi);
		back=(ImageButton) findViewById(R.id.but_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DingZhi.this.finish();
			}
		});
		selall=(Button) findViewById(R.id.but_selall);
		seldel=(Button) findViewById(R.id.but_seldel);
		selall.setOnClickListener(new SelectAll());
		view=(ListView) findViewById(R.id.list);
		title=(TextView) findViewById(R.id.txt_title);
		title.setText("定制首页");
		
		ad=new DingZhiAdapter(this);
		view.setAdapter(ad);
		
	}
	private class SelectAll implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			for (int i = 0; i < ad.check.length; i++) {
				ad.check[i]=true;
			}
			ad.notifyDataSetChanged();
		}
		
	}
}
