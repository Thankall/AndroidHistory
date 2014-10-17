package com.example.ttplayer.activity;

import com.example.ttplayer.R;
import com.example.ttplayer.adapter.TabAdapter;
import com.example.ttplayer.data.SqlMusicHelper;
import com.example.ttplayer.service.PlayService;
import com.example.ttplayer.stati.MymusicList;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

public class TabHo extends Activity {
	private GridView grid = null;
	private TabHost host = null;
	private TabAdapter adapter;
	private int[] res = { R.id.tab_my, R.id.tab_tao, R.id.tab_sou, R.id.tab_tui };
	private String[] title = { "我的", "淘歌", "搜索", "推荐", };
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost);
		
		adapter = new TabAdapter(this);

		host = (TabHost) findViewById(R.id.tabhost);
		host.setup();
		for (int i = 0; i < res.length; i++) {
			TabSpec spec = host.newTabSpec(title[i]);
			View v = View.inflate(this, R.layout.view_title, null);
			TextView t = (TextView) v.findViewById(R.id.txt_v_t);
			t.setText(title[i]);
			t.setTextColor(0xffffffff);

			spec.setIndicator(v);
			spec.setContent(res[i]);
			host.addTab(spec);
		}

		ImageButton img = (ImageButton) this.findViewById(R.id.img_fang);
		img.getBackground().setAlpha(200);
		img.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Intent it = new Intent(TabHo.this, Play.class);
				TabHo.this.startActivity(it);
			}
		});
		grid = (GridView) findViewById(R.id.grid_my);
		grid.setAdapter(adapter);
		grid.setSelector(new ColorDrawable(Color.TRANSPARENT));
		grid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent it;
				switch (position) {
				case 0:
					it = new Intent(TabHo.this, Mymusic.class);
					TabHo.this.startActivity(it);
					break;
				case 1:
					it = new Intent(TabHo.this, Myfav.class);
					TabHo.this.startActivity(it);
					break;
				case 2:
					it = new Intent(TabHo.this, Login.class);
					TabHo.this.startActivity(it);
					break;
				case 3:
					it = new Intent(TabHo.this, Pager44.class);
					TabHo.this.startActivity(it);
					break;
				case 4:
					it = new Intent(TabHo.this, Login.class);
					TabHo.this.startActivity(it);
					break;
				case 5:
					it = new Intent(TabHo.this, Login.class);
					TabHo.this.startActivity(it);
					break;
				case 6:
					it = new Intent(TabHo.this, Netmusic.class);
					TabHo.this.startActivity(it);
					break;
				case 7:
					it = new Intent(TabHo.this, ShouFa.class);
					TabHo.this.startActivity(it); 
					break;
				case 8:
					it = new Intent(TabHo.this, DingZhi.class);
					TabHo.this.startActivity(it);
					break;

				default:
					break;
				}
			}
		});
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(menu.NONE, 1, 1, "退出");
		menu.add(menu.NONE, 2, 2, "账号管理");
		menu.add(menu.NONE, 3, 3, "设置");
		menu.add(menu.NONE, 4, 4, "官方微博");
		menu.add(menu.NONE, 5, 5, "夜间模式");
		menu.add(menu.NONE, 6, 6, "更多");
		return true;

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			Intent it=new Intent(this, PlayService.class);
			stopService(it);
			TabHo.this.finish();
			break;

		default:
			break;
		}

		return true;
	}

	boolean flag = false;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if (flag == true) {
				//Log.i("msg", "assssssssssd");
				TabHo.this.finish();
			} else {
				Toast.makeText(TabHo.this, "再按一下返回键退出程序", Toast.LENGTH_SHORT)
						.show();
				flag = true;
				new Thread() {
					public void run() {
						try {
							Thread.sleep(1500);
							flag = false;
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					};
				}.start();
			}

			break;

		default:
			return super.onKeyDown(keyCode, event);
		}

		return true;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		adapter = new TabAdapter(this);
		grid.setAdapter(adapter);
		super.onStart();
	}
}
