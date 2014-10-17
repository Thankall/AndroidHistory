package com.example.ttplayer.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.ttplayer.R;
import com.example.ttplayer.adapter.MyfavAdapter;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.Activity;


public class Myfav extends Activity  {
	ListView lview;
	TextView title;
	
	MyfavAdapter ada=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.myfav);
		title=(TextView) findViewById(R.id.txt_title);
		title.setText("我的最爱");
		ada=new MyfavAdapter(this);
		lview=(ListView) findViewById(R.id.list_mymusic);
		lview.setAdapter(ada);
	}
	

}
