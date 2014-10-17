package com.example.ttplayer.activity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.ttplayer.R;
import com.example.ttplayer.service.PlayService;
import com.example.ttplayer.stati.MymusicList;

import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.format.Time;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class Play extends Activity {
	private TextView title, email, pwd;
	private ImageButton back, menu, pause, stop, pre, next;
	private Intent ser;
	private SeekBar seek;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.play);
		Intent it = getIntent();
		String email_s = it.getStringExtra("email");
		String pwd_s = it.getStringExtra("pwd");
		email = (TextView) findViewById(R.id.txt_user);
		pwd = (TextView) findViewById(R.id.txt_pwd);
		email.setText(email_s);
		pwd.setText(pwd_s);
		title = (TextView) findViewById(R.id.txt_title);
		title.setText("我的音乐");

		menu = (ImageButton) findViewById(R.id.but_menu);
		menu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it = new Intent(Play.this, DingZhi.class);
				Play.this.startActivity(it);
			}
		});
		back = (ImageButton) findViewById(R.id.but_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Play.this.finish();
			}
		});
		pause = (ImageButton) findViewById(R.id.but_pause);
		// stop=(ImageButton) findViewById(R.id.but_stop);
		pre = (ImageButton) findViewById(R.id.but_prev);
		next = (ImageButton) findViewById(R.id.but_next);
		pause.setOnClickListener(new MyOnClick());
		next.setOnClickListener(new MyOnClick());
		pre.setOnClickListener(new MyOnClick());
		seek=(SeekBar) findViewById(R.id.prb_song);
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
				ser = new Intent("com.exmple.ttplayer.playseek");
				ser.putExtra("prgs", seekBar.getProgress());
				sendBroadcast(ser);
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				// TODO Auto-generated method stub
				
			}
		});
		IntentFilter profilter=new IntentFilter();
		profilter.addAction("com.example.ttplayer.seek");
		Progressreceiver prore=new Progressreceiver();
		registerReceiver(prore, profilter);
		IntentFilter filter=new IntentFilter();
		filter.addAction("com.statechanged");
		Areceiver re=new Areceiver();
		registerReceiver(re, filter);
		
		seek.setMax((int)(MymusicList.songlist.get(MymusicList.pos).getDuration()));
		title.setText(MymusicList.songlist.get(MymusicList.pos).getTitle());
		if(MymusicList.state==1){
			pause.setBackgroundResource(R.drawable.appwidget_icon_pause_normal);
		}else{
			pause.setBackgroundResource(R.drawable.appwidget_icon_play_normal);
		}
		
	}

	private class MyOnClick implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			ser = new Intent("com.exmple.ttplayer.playser");
			switch (v.getId()) {
			case R.id.but_pause:
				ser.putExtra("data", 1);
				
				break;
			case R.id.but_prev:
				ser.putExtra("data", 2);
				break;
			case R.id.but_next: 
				ser.putExtra("data", 3);
				break;

			}
			sendBroadcast(ser);
		}
	}

	public class Areceiver extends BroadcastReceiver{
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			seek.setMax((int)(MymusicList.songlist.get(MymusicList.pos).getDuration()));
			title.setText(MymusicList.songlist.get(MymusicList.pos).getTitle());
			if(MymusicList.state==1){
				pause.setBackgroundResource(R.drawable.appwidget_icon_pause_normal);
			}else{
				pause.setBackgroundResource(R.drawable.appwidget_icon_play_normal);
			}
		}
		
	}
	
	public class Progressreceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int pro=intent.getIntExtra("pro", 0);
			seek.setProgress(pro);
		}
		
	}
}
