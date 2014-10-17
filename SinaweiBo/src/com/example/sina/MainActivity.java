package com.example.sina;

import java.text.SimpleDateFormat;

import com.example.sina.R;
import com.example.sina.appstatic.AppFinal;
import com.example.sina.util.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends ActivityGroup {

	
	private TabHost host;
	private int[] draw = { R.drawable.home_sel, R.drawable.message_sel,
			R.drawable.profile_sel, R.drawable.discover_sel,
			R.drawable.more_sel, };
	private String[] title = { "首页", "消息", "我", "发现", "更多" };
	private Class<?>[] cla={HomeAct.class,HomeAct.class,HomeAct.class,HomeAct.class,MoreAct.class,};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tabs);
		init();
		host.setup(getLocalActivityManager());
		for (int i = 0; i < title.length; i++) {
			TabSpec spec = host.newTabSpec(title[i]);
			View v = View.inflate(this, R.layout.tabindicator, null);
			ImageView img = (ImageView) v.findViewById(R.id.ibut_tab);
			TextView tv = (TextView) v.findViewById(R.id.tv_tab);
			img.setBackgroundResource(draw[i]);
			tv.setText(title[i]);
			spec.setIndicator(v);
			Intent it=new Intent(MainActivity.this, cla[i]);
			spec.setContent(it);
			host.addTab(spec);
		}

	

	}

	private void init() {
		host = (TabHost) findViewById(R.id.tab_main);
	
	}


}
