package com.example.ttplayer.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.ttplayer.R;
import com.example.ttplayer.adapter.PagetAdapter44;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

public class Pager44 extends Activity {
	private ListView list = null;
	private ViewPager pager = null;
	private TextView text,t1,t2,t3 ;
	private View v1,v2,v3;
	private List<View> views=new ArrayList<View>();
	private int[] res = { R.layout.page_tuijian, R.layout.page_paihang, R.layout.page_fenlei};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pager44);
		
		v1=findViewById(R.id.view_tuijian);
		v2=findViewById(R.id.view_paihang);
		v3=findViewById(R.id.view_fenlei);
		t1=(TextView) findViewById(R.id.but_tuijian);
		t2=(TextView) findViewById(R.id.but_paihang);
		t3=(TextView) findViewById(R.id.but_fenlei);
		
		text = (TextView) findViewById(R.id.txt_title);
		text.setText("关注好友");
		PagerList44 adapter = new PagerList44(this);

		pager = (ViewPager) findViewById(R.id.viewpager);
		
		for (int i = 0; i < res.length; i++) {
			
			View v=View.inflate(this, res[i], null);
			
			views.add(v);
		}

		list = (ListView) ((View) (views.get(0))).findViewById(R.id.list_tuijian);
		list.setAdapter(adapter);
		pager.setAdapter(new PagetAdapter44(views));
		pager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					v1.setVisibility(View.VISIBLE);
					v2.setVisibility(View.INVISIBLE);
					v3.setVisibility(View.INVISIBLE);
					break;
				case 1:
					v2.setVisibility(View.VISIBLE);
					v1.setVisibility(View.INVISIBLE);
					v3.setVisibility(View.INVISIBLE);
					break;
				case 2:
					v3.setVisibility(View.VISIBLE);
					v2.setVisibility(View.INVISIBLE);
					v1.setVisibility(View.INVISIBLE);
					break;

				default:
					break;
				}
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		t1.setOnClickListener(new MyClick(0));
		t2.setOnClickListener(new MyClick(1));
		t3.setOnClickListener(new MyClick(2));
	}
	private class MyClick implements OnClickListener{
		int i;
		public MyClick(int i){
			this.i=i;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			pager.setCurrentItem(i);
		}
		
	}
}
