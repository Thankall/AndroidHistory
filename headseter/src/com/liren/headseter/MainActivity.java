package com.liren.headseter;

import android.os.Bundle;
import android.app.Activity;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends Activity {
	RadioGroup rdoGroup;
	RadioButton rb1, rb2, rb3;
	ToggleButton togButton;
	TextView textView;
	int moshi;// 模式：1普通，2播放，3驾驶
	int OnOff;// 是否开机自启动
	Intent it;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);
		init();

		rdoGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.rb1:
					Log.i("xxx", "普通模式");
					Constants.MoShi = 1;
					break;
				case R.id.rb2:
					Log.i("xxx", "播放模式");
					Constants.MoShi = 2;
					break;
				case R.id.rb3:
					Log.i("xxx", "驾驶模式");

					Constants.MoShi = 3;
					break;
				}
			}
		});
		togButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (togButton.isChecked()) {
					Constants.OnOff = 1;
					textView.setText("开机自启动：是");
				} else {
					Constants.OnOff = 2;
					textView.setText("开机自启动：否");
				}
			}
		});
		SharedPreferences preference = getSharedPreferences("Mo", 0);
		OnOff = preference.getInt("OnOff", 2);
		moshi = preference.getInt("moshi", 1);
		Constants.MoShi = moshi;
		switch (moshi) {
		case 1:
			rb1.setChecked(true);
			break;
		case 2:
			rb2.setChecked(true);
			break;
		case 3:
			rb3.setChecked(true);
			break;
		}
		switch (OnOff) {
		case 1:
			togButton.setChecked(true);
			textView.setText("开机自启动：是");
			break;

		case 2:
			togButton.setChecked(false);
			textView.setText("开机自启动：否");
			break;

		}
		it = new Intent(this, RingService.class);
		startService(it);

	}

	public void init() {
		rdoGroup = (RadioGroup) findViewById(R.id.radio_group);
		textView = (TextView) findViewById(R.id.tv_isonoff);
		togButton = (ToggleButton) findViewById(R.id.toggle_button);
		rb1 = (RadioButton) findViewById(R.id.rb1);
		rb2 = (RadioButton) findViewById(R.id.rb2);
		rb3 = (RadioButton) findViewById(R.id.rb3);
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, 1, 0, "完全退出");
		menu.add(0, 2, 0, "软件说明");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case 1:
			stopService(it);
			finish();
			break;
		case 2:
			Intent it=new Intent(this, Activity2.class);
			startActivity(it);
			Log.i("xxx", "软件说明");
			break;
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		SharedPreferences preference = getSharedPreferences("Mo", 0);
		Editor tEditor = preference.edit();
		tEditor.putInt("OnOff", Constants.OnOff);
		tEditor.putInt("moshi", Constants.MoShi);
		tEditor.commit();
	}

}
