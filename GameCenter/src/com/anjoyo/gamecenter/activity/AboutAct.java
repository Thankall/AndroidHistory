package com.anjoyo.gamecenter.activity;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.anjoyo.gamecenter.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class AboutAct extends Activity {

	ImageButton button;
	ImageView sendup;
	EditText editText,editText2;
	String a,b;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_act);
		button=(ImageButton) findViewById(R.id.back);
		sendup=(ImageView) findViewById(R.id.sendup);
		editText=(EditText) findViewById(R.id.iv);
		editText2=(EditText) findViewById(R.id.et1);
		sendup.setOnClickListener(new OnClickListener() {
			
		String a=editText.getText().toString().trim();
		String b=editText2.getText().toString().trim();
		
			@Override
			public void onClick(View arg0) {
				
			Toast.makeText(AboutAct.this, "已提交", 0).show();
				editText.setText("");
				editText2.setText("");
			}
		});
		button.setOnClickListener(new OnClickListener() {
	    
			@Override
			public void onClick(View arg0) {
				finish();
				
			}
		});
	}


}
