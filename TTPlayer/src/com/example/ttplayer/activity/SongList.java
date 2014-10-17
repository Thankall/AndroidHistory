package com.example.ttplayer.activity;

import com.example.ttplayer.R;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class SongList extends Activity implements OnClickListener {
	private Button but2=null;
	private Button play=null;
	private Button dingzhi=null;
	private LinearLayout layout=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.songlist);
		but2=(Button) findViewById(R.id.but_log);
		but2.setOnClickListener(this);
		layout=(LinearLayout) findViewById(R.id.layout);
		play=(Button) findViewById(R.id.but_play);
		play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(SongList.this, Play.class);
				SongList.this.startActivity(it);
			}
		});
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent it=new Intent(this, Login.class);
		this.startActivity(it);
		
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		// TODO Auto-generated method stub
		if(newConfig.orientation==Configuration.ORIENTATION_LANDSCAPE){
			Log.i("msg", "xxxxxxxx");
			layout.setBackgroundResource(R.drawable.social_publish_poster_gallery_bkg);
		}else if(newConfig.orientation==Configuration.ORIENTATION_PORTRAIT){
			Log.i("msg", "ssssssss");
			layout.setBackgroundResource(R.drawable.splash_default);
		}
		super.onConfigurationChanged(newConfig);
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(menu.NONE, 1, 1, "退出");
		return true;
		
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId()==1){
			SongList.this.finish();
		}
		return true;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			Dialog exit=new AlertDialog.Builder(this)
			.setTitle("确认退出？")
			.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					SongList.this.finish();
				}
			}
			)
			.setNegativeButton("取消", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
			}).create();
			exit.show();
		}
		
		return super.onKeyDown(keyCode, event);
	}

}
