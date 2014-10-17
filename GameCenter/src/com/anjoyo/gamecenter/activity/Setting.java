package com.anjoyo.gamecenter.activity;

import com.anjoyo.gamecenter.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;

public class Setting extends Activity implements OnClickListener,
		OnCheckedChangeListener {
	private ImageView but_back;
	private ImageView iv_fankui;
	private ImageView iv_newman;
	private CheckBox cb_3g;
	private CheckBox cb_update;
	private CheckBox cb_wifi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting_layout);
		init();
		SharedPreferences share = Setting.this.getSharedPreferences("setting",
				Activity.MODE_PRIVATE);
		boolean is3g = share.getBoolean("cb_3g", false);
		boolean iswifi = share.getBoolean("cb_wifi", false);
		boolean isupdate = share.getBoolean("cb_update", false);
		cb_3g.setChecked(is3g);
		cb_wifi.setChecked(iswifi);
		cb_update.setChecked(isupdate);
	}

	private void init() {
		// TODO Auto-generated method stub
		but_back = (ImageView) findViewById(R.id.but_back);
		iv_fankui = (ImageView) findViewById(R.id.iv_fankui);
		iv_newman = (ImageView) findViewById(R.id.iv_newman);
		cb_3g = (CheckBox) findViewById(R.id.cb_3g);
		cb_update = (CheckBox) findViewById(R.id.cb_update);
		cb_wifi = (CheckBox) findViewById(R.id.cb_wifi);
		but_back.setOnClickListener(this);
		iv_fankui.setOnClickListener(this);
		iv_newman.setOnClickListener(this);
		cb_3g.setOnCheckedChangeListener(this);
		cb_update.setOnCheckedChangeListener(this);
		cb_wifi.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.but_back:
			this.finish();
			break;
		case R.id.iv_fankui:

			break;
		case R.id.iv_newman:

			break;

		default:
			break;
		}

	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		SharedPreferences share = Setting.this.getSharedPreferences(
				"setting", Activity.MODE_PRIVATE);
		SharedPreferences.Editor edit = share.edit();
		switch (buttonView.getId()) {
		case R.id.cb_3g:
			edit.putBoolean("cb_3g", isChecked);
			edit.commit();
			break;
		case R.id.cb_update:
			edit.putBoolean("cb_update", isChecked);
			edit.commit();
			break;
		case R.id.cb_wifi:
			edit.putBoolean("cb_wifi", isChecked);
			edit.commit();
			break;

		default:
			break;
		}

	}
}
