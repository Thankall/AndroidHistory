package com.example.ttplayer.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.ttplayer.R;
import com.example.ttplayer.adapter.TabAdapter44;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.ViewPager;
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

public class TabHo44 extends Activity {
	private ListView list = null;
	private ViewPager pager = null;
	private TextView text = null;
	private List views=new ArrayList<View>();
	private int[] res = { R.id.tab_tuijian, R.id.tab_paihang, R.id.tab_fenlei };
	private String[] title = { "推荐", "排行", "分类" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewpager44);
		
		text = (TextView) findViewById(R.id.txt_title);
		text.setText("关注好友");
		TabAdapter44 adapter = new TabAdapter44(this);

		pager = (ViewPager) findViewById(R.id.tabhost44);
		
		for (int i = 0; i < res.length; i++) {
			
			View v=View.inflate(this, R.layout.page44, null);
			View layout= v.findViewById(res[i]);
			views.add(layout);
		}

		list = (ListView) findViewById(R.id.list_tuijian);
		list.setAdapter(adapter);

	}

}
