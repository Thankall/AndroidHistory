package com.example.ttplayer.activity;

import com.example.ttplayer.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Login extends Activity  {
	private TextView title;
	ImageButton back;
	Button login;
	EditText email_e,pwd_e;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		email_e=(EditText) findViewById(R.id.ed_name);
		pwd_e=(EditText) findViewById(R.id.ed_pwd);
		title=(TextView) findViewById(R.id.txt_title);
		title.setText("欢迎登陆");
		back=(ImageButton) findViewById(R.id.but_back);
		back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Login.this.finish();
			}
		});
		login=(Button) findViewById(R.id.but_login);
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String email="邮箱:"+email_e.getText().toString();
				String pwd="密码:"+pwd_e.getText().toString();
				Intent it=new Intent(Login.this, Play.class);
				it.putExtra("email", email);
				it.putExtra("pwd", pwd);
				Login.this.startActivity(it);
			}
		});
	}


}
