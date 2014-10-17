package com.example.ttplayer.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ttplayer.R;
import com.example.ttplayer.activity.Play.Areceiver;
import com.example.ttplayer.adapter.MymusicAdapter;
import com.example.ttplayer.data.SqlMusicHelper;
import com.example.ttplayer.stati.MymusicList;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;

public class Mymusic extends Activity implements OnClickListener {
	ListView lview, poplist;
	TextView title, song, art, dur;
	ImageButton but_pop, back, play, next;
	ImageView fang;
	PopupWindow popWin;
	AlertDialog dialog;
	private int del_pos;
	MymusicAdapter mAdapter;
	
	int[] location = new int[2];
	String[] con = { "删除", "添加到", "发送", "设为铃声", "详细信息", };
	String[] poptext = { "歌曲排序", "多选", "清空", "扫描音乐" };
	int[] img = { R.drawable.icon_list_context_menu_remove,
			R.drawable.icon_list_context_menu_add_to,
			R.drawable.icon_list_context_menu_share,
			R.drawable.icon_list_context_menu_set_as_ringtone,
			R.drawable.icon_list_context_menu_media_info };
	List<Map<String, String>> list = new ArrayList<Map<String, String>>();
	List<Map<String, String>> list_pop = new ArrayList<Map<String, String>>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mymusic);
		for (int i = 0; i < poptext.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("_txt", poptext[i]);
			list_pop.add(map);
		}
		title = (TextView) findViewById(R.id.txt_title);
		title.setText("我的音乐");
		but_pop = (ImageButton) findViewById(R.id.ibut_meun);
		but_pop.getLocationInWindow(location);
		back = (ImageButton) findViewById(R.id.but_back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Mymusic.this.finish();
			}
		});
		but_pop.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				

					View popview = View.inflate(Mymusic.this,
							R.layout.popwindow, null);
					poplist = (ListView) popview.findViewById(R.id.list_pop);
					poplist.setAdapter(new SimpleAdapter(Mymusic.this,
							list_pop, R.layout.pop_text,
							new String[] { "_txt" }, new int[] { R.id.txt_pop }));
					popWin = new PopupWindow(popview, 200,
							LayoutParams.WRAP_CONTENT, true);
					popWin.setBackgroundDrawable(new ColorDrawable(0));
					popWin.showAsDropDown(but_pop);
					// popWin.setOutsideTouchable(true);
					
				
			}
		});
		lview = (ListView) findViewById(R.id.list_mymusic_my);
		mAdapter = new MymusicAdapter(this);
		lview.setAdapter(mAdapter);

		for (int i = 0; i < con.length; i++) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("_img", img[i] + "");
			map.put("_con", con[i]);
			list.add(map);
		}
		lview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Log.i("msg", "assssssssssd");
				// TODO Auto-generated method stub
				del_pos = position;
				dialog = new AlertDialog.Builder(Mymusic.this).create();

				View v = View.inflate(Mymusic.this, R.layout.dialog_mymusic,
						null);
				TextView tit = (TextView) v.findViewById(R.id.txt_dia_tit);
				tit.setText(((TextView) (view.findViewById(R.id.txt_songname)))
						.getText());
				ListView list_d = (ListView) v.findViewById(R.id.list_dialog);
				list_d.setAdapter(new SimpleAdapter(Mymusic.this, list,
						R.layout.dialog_item, new String[] { "_img", "_con" },
						new int[] { R.id.ico_dia, R.id.txt_dia }));
				list_d.setOnItemClickListener(new DialogItemClick());
				dialog.show();
				dialog.getWindow().setContentView(v);
				return true;
			}
		});
		lview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// MymusicList.state=1;
				Intent it = new Intent("com.exmple.ttplayer.playser");
				MymusicList.pos = position;

				it.putExtra("data", 1);
				it.putExtra("diffrent", 1);
				sendBroadcast(it);
			}
		});
		play = (ImageButton) findViewById(R.id.img_play);
		play.setOnClickListener(this);
		next = (ImageButton) findViewById(R.id.img_next);
		next.setOnClickListener(this);
		fang = (ImageView) findViewById(R.id.img_fang);
		fang.setOnClickListener(this);
		song = (TextView) findViewById(R.id.txt_name);
		art = (TextView) findViewById(R.id.txt_art);
		if (MymusicList.songlist .size()!=0) {
			song.setText(MymusicList.songlist.get(MymusicList.pos).getTitle());
			art.setText(MymusicList.songlist.get(MymusicList.pos).getArtist());
		}

		if (MymusicList.state == 1) {
			play.setBackgroundResource(R.drawable.appwidget_icon_pause_normal);
		} else {
			play.setBackgroundResource(R.drawable.appwidget_icon_play_normal);
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.statechanged");
		Areceiver re = new Areceiver();
		registerReceiver(re, filter);

	}

	public class Areceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			song.setText(MymusicList.songlist.get(MymusicList.pos).getTitle());
			art.setText(MymusicList.songlist.get(MymusicList.pos).getArtist());
			if (MymusicList.state == 1) {
				play.setBackgroundResource(R.drawable.appwidget_icon_pause_normal);
			} else {
				play.setBackgroundResource(R.drawable.appwidget_icon_play_normal);
			}
		}

	}

	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// // TODO Auto-generated method stub
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// Dialog exit = new AlertDialog.Builder(this)
	// .setTitle("确认退出？")
	// .setIcon(android.R.drawable.ic_dialog_info)
	// .setPositiveButton("确定",
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// // TODO Auto-generated method stub
	// Mymusic.this.finish();
	// }
	// })
	// .setNegativeButton("取消",
	// new DialogInterface.OnClickListener() {
	//
	// @Override
	// public void onClick(DialogInterface dialog,
	// int which) {
	// // TODO Auto-generated method stub
	//
	// }
	// }).create();
	// exit.show();
	// }
	//
	// return super.onKeyDown(keyCode, event);
	// }
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent ser = new Intent("com.exmple.ttplayer.playser");
		switch (v.getId()) {
		case R.id.img_play:

			ser.putExtra("data", 1);
			sendBroadcast(ser);
			break;
		case R.id.img_next:
			ser.putExtra("data", 3);
			sendBroadcast(ser);
			break;
		case R.id.img_fang:
			Intent it = new Intent(Mymusic.this, Play.class);
			Mymusic.this.startActivity(it);
			break;

		default:
			break;
		}

	}

	public class DialogItemClick implements OnItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			switch (position) {
			case 0:
				SqlMusicHelper dbhelper = new SqlMusicHelper(Mymusic.this,
						"songlist");
				dbhelper.delete(MymusicList.songlist.get(del_pos).getId());
				MymusicList.favlist.remove(MymusicList.songlist.get(del_pos));
				MymusicList.songlist.remove(del_pos);
				break;

			default:
				break;
			}
			dialog.dismiss();
			mAdapter.notifyDataSetChanged();
		}
	}

}
