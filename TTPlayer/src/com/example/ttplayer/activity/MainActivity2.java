package com.example.ttplayer.activity;

import com.example.ttplayer.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity2 extends Activity implements OnClickListener {
	private Button but2=null;
	private LinearLayout layout=null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity2);
		but2=(Button) findViewById(R.id.exit);
		but2.setOnClickListener(this);
		layout=(LinearLayout) findViewById(R.id.layout);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		this.finish();
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


}
