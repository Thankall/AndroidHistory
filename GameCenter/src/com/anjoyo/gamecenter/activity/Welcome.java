package com.anjoyo.gamecenter.activity;

import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.utils.CheckNetWork;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class Welcome extends Activity {
	private Intent it;
	private Dialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);
		it=new Intent(this, MainActivity.class);
		final SharedPreferences share = this.getSharedPreferences("setting",
				Activity.MODE_PRIVATE);
		boolean ckwifi = share.getBoolean("cb_wifi", false);
		
		if (ckwifi&&!new CheckNetWork().isWifiConnected(this)) {
			dialog=new AlertDialog.Builder(this).create();
			View dia_view=View.inflate(this, R.layout.dialog_iphone, null);
			CheckBox cb=(CheckBox) dia_view.findViewById(R.id.cb_notip);
			cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
				
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					// TODO Auto-generated method stub
					SharedPreferences.Editor edit = share.edit();
					edit.putBoolean("cb_wifi", isChecked);
				}
			});
			Button next =(Button) dia_view.findViewById(R.id.but_ok);
			Button exit =(Button) dia_view.findViewById(R.id.but_no);
			next.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					dialog.dismiss();
					new Thread(){
						
						public void run() {
							try {
								sleep(2000);
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							Welcome.this.startActivity(it);
							Welcome.this.finish();
						};
					}.start();
				}
			});
			exit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Welcome.this.finish();
				}
			});
			dialog.show();
			dialog.getWindow().setContentView(dia_view);
		}else{
			new Thread(){
				
				public void run() {
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Welcome.this.startActivity(it);
					Welcome.this.finish();
				};
			}.start();
		}
		
		
	}
}
