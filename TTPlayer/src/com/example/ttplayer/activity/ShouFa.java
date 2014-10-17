package com.example.ttplayer.activity;

import java.util.ArrayList;
import java.util.List;

import com.example.ttplayer.R;
import com.example.ttplayer.adapter.ShouFaAdapter;
import com.example.ttplayer.view.RefreshableView;
import com.example.ttplayer.view.RefreshableView.PullToRefreshListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class ShouFa extends Activity {
	ListView lview;
	List<String> img_list;
	TextView title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mymusic);
		
		title=(TextView) findViewById(R.id.txt_title);
		title.setText("首发专区");
		img_list=new ArrayList<String>();
		for (int i = 0; i < Netmusic.musics.size(); i++) {
			img_list.add(Netmusic.musics.get(i).getMp3AlbumImage());
		}
		
		Log.i("msg", Netmusic.musics.size()+"");
		lview=(ListView) findViewById(R.id.list_mymusic_my);
		lview.setAdapter(new ShouFaAdapter(this,img_list));
		
	}
	
}
