package com.anjoyo.gamecenter.activity;

import java.util.ArrayList;
import java.util.List;

import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.adapter.ViewpagerAdapter;
import com.anjoyo.gamecenter.rank.RankActivity;
import com.anjoyo.gamecenter.tuijian.tuijianActivity;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ActivityGroup;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActivityGroup {
	private ImageView but_menu, but_search;
	private RelativeLayout rl_rank, rl_recommend, rl_classify, rl_manager;
	private TextView tv_recommend, tv_rank, tv_classify, tv_manager;
	private ViewPager pager;
	private ViewpagerAdapter pager_adapter;
	private List<View> views;
	private long exittime;
	ProgressDialog dialog;
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				dialog.dismiss();
				Toast.makeText(MainActivity.this, "暂无更新版本", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		initViewPager();
	}
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(menu.NONE, 1, 1, "版本更新").setIcon(R.drawable.update);
		menu.add(menu.NONE, 2, 2, "关于").setIcon(R.drawable.about);
		menu.add(menu.NONE, 3, 3, "设置").setIcon(R.drawable.setting);
		menu.add(menu.NONE, 4, 4, "退出").setIcon(R.drawable.exit);
		return true;

	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent it;
		switch (item.getItemId()) {
		case 1:
			  dialog=new ProgressDialog(this);
			dialog.setTitle("正在检查更新。。");
			dialog.setMessage("请稍等。。");
			dialog.show();
			new Thread(){
				public void run() {
					try {
						sleep(2000);
						handler.sendEmptyMessage(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				};
			}.start();
			break;
		case 2:
			it=new Intent(MainActivity.this,AboutAct.class);
			startActivity(it);
			break;
		case 3://设置
			it=new Intent(MainActivity.this,Setting.class);
			startActivity(it);
			break;
		case 4://退出
			MainActivity.this.finish();
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	@SuppressWarnings("deprecation")
	private void initViewPager() {
		views = new ArrayList<View>();
		views.add(getLocalActivityManager().startActivity(
				"a1",
				new Intent(MainActivity.this, tuijianActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
		views.add(getLocalActivityManager().startActivity(
				"a1",
				new Intent(MainActivity.this,RankActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
		views.add(getLocalActivityManager().startActivity(
				"a1",
				new Intent(MainActivity.this, Classfication.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
		views.add(getLocalActivityManager().startActivity(
				"a1",
				new Intent(MainActivity.this, Manage.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
				.getDecorView());
		pager_adapter = new ViewpagerAdapter(views);
		pager.setAdapter(pager_adapter);
		pager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				switch (arg0) {
				case 0:
					tv_recommend.setBackgroundResource(R.drawable.bar_click_bg);
					tv_rank.setBackgroundColor(0x00ffffff);
					tv_classify.setBackgroundColor(0x00ffffff);
					tv_manager.setBackgroundColor(0x00ffffff);
					break;
				case 1:
					tv_rank.setBackgroundResource(R.drawable.bar_click_bg);
					tv_recommend.setBackgroundColor(0x00ffffff);
					tv_classify.setBackgroundColor(0x00ffffff);
					tv_manager.setBackgroundColor(0x00ffffff);
					break;
				case 2:
					tv_classify.setBackgroundResource(R.drawable.bar_click_bg);
					tv_rank.setBackgroundColor(0x00ffffff);
					tv_recommend.setBackgroundColor(0x00ffffff);
					tv_manager.setBackgroundColor(0x00ffffff);
					break;
				case 3:
					tv_manager.setBackgroundResource(R.drawable.bar_click_bg);
					tv_rank.setBackgroundColor(0x00ffffff);
					tv_classify.setBackgroundColor(0x00ffffff);
					tv_recommend.setBackgroundColor(0x00ffffff);
					break;

				default:
					break;
				}
			}

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	private void init() {
		but_menu = (ImageView) findViewById(R.id.but_menu);
		but_search = (ImageView) findViewById(R.id.but_search);
		but_menu.setOnClickListener(new TitleClick());
		but_search.setOnClickListener(new TitleClick());
		tv_recommend = (TextView) findViewById(R.id.tv_recommend);
		tv_rank = (TextView) findViewById(R.id.tv_rank);
		tv_classify = (TextView) findViewById(R.id.tv_classify);
		tv_manager = (TextView) findViewById(R.id.tv_manager);
		rl_classify = (RelativeLayout) findViewById(R.id.rl_classify);
		rl_rank = (RelativeLayout) findViewById(R.id.rl_rank);
		rl_recommend = (RelativeLayout) findViewById(R.id.rl_recommend);
		rl_manager = (RelativeLayout) findViewById(R.id.rl_manager);
		pager = (ViewPager) findViewById(R.id.viewpager);
		rl_recommend.setOnClickListener(new ClickPager(0));
		rl_rank.setOnClickListener(new ClickPager(1));
		rl_classify.setOnClickListener(new ClickPager(2));
		rl_manager.setOnClickListener(new ClickPager(3));
	}
	private class ClickPager implements OnClickListener{
		int i;
		public ClickPager(int i) {
			this.i=i;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			pager.setCurrentItem(i,true);
		}
		
	}
	private class TitleClick implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		
	}
	public boolean dispatchKeyEvent(KeyEvent event) {

		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_UP) {

			
				if ((System.currentTimeMillis() - exittime) > 2000) {
					Toast.makeText(MainActivity.this, "再按一次退出",
							Toast.LENGTH_SHORT).show();
					exittime = System.currentTimeMillis();
				} else {
					finish();

				}
			
			return true;
		}
		return false;
		
	}

}
