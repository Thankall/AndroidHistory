package com.example.ttplayer.activity;

import com.example.ttplayer.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	private Button but1=null; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		new Thread(){
			public void run(){
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally{
					Intent it=new Intent(MainActivity.this, MainActivity2.class);
					startActivity(it);
					MainActivity.this.finish();
				}
			}
		}.start();
		//but1=(Button) findViewById(R.id.but1);
		//but1.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent it=new Intent(this, MainActivity2.class);
		startActivity(it);
	}


}
