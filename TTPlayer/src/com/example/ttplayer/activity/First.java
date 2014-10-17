package com.example.ttplayer.activity;

import com.example.ttplayer.R;
import com.example.ttplayer.activity.Mymusic.Areceiver;
import com.example.ttplayer.data.SqlMusicHelper;
import com.example.ttplayer.service.PlayService;
import com.example.ttplayer.stati.MusicInstance;
import com.example.ttplayer.stati.MymusicList;

import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.widget.RemoteViews;

public class First extends Activity {
	Intent it;
	RemoteViews rm;
	private Cursor c;
	private SqlMusicHelper dbhelper;
	NotificationManager manager;
	Notification notification;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.first);
		Intent service = new Intent(this, PlayService.class);
		startService(service);
		dbhelper = new SqlMusicHelper(this, "songlist");
		c = dbhelper.queryAll();
		MymusicList.songlist.clear();
		MymusicList.favlist.clear();
		while (c.moveToNext()) {
			MusicInstance music = new MusicInstance();
			music.setId(c.getInt(c.getColumnIndex("_id")));
			music.setTitle(c.getString(c.getColumnIndex("title")));
			music.setAlbum(c.getString(c.getColumnIndex("album")));
			music.setArtist(c.getString(c.getColumnIndex("artist")));
			music.setPath(c.getString(c.getColumnIndex("path")));
			music.setIsfav(c.getString(c.getColumnIndex("isfav")));
			music.setDuration(c.getLong(c.getColumnIndex("duration")));
			music.setSize(c.getLong(c.getColumnIndex("size")));
			MymusicList.songlist.add(music);
			if ("T".equals(music.getIsfav())) {
				MymusicList.favlist.add(music);
			}
		}
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.statechanged");
		Nreceiver re = new Nreceiver();
		registerReceiver(re, filter);

		manager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);
		notification = new Notification();
		notification.icon = R.drawable.app_icon;
		notification.tickerText = "天天动听，美好音质。";
		notification.flags = Notification.FLAG_NO_CLEAR;
		 rm=new RemoteViews(this
				.getPackageName(), R.layout.notification);
		
		
		Intent pre=new Intent();
		pre.setAction("com.exmple.ttplayer.ntf_pre");
		//pre.putExtra("ntf_pre", 2);
		PendingIntent prePen=PendingIntent.getBroadcast(this, 0, pre, 0);
		rm.setOnClickPendingIntent(R.id.ntf_pre, prePen);
		
		Intent play=new Intent();
		play.setAction("com.exmple.ttplayer.ntf_play");
		//play.putExtra("ntf_play", 1);
		PendingIntent playPen=PendingIntent.getBroadcast(this, 0, play, 0);
		rm.setOnClickPendingIntent(R.id.ntf_ply, playPen);
		Intent next=new Intent();
		next.setAction("com.exmple.ttplayer.ntf_next");
		//next.putExtra("ntf_next", 3);
		PendingIntent nextPen=PendingIntent.getBroadcast(this, 0, next, 0);
		rm.setOnClickPendingIntent(R.id.ntf_next, nextPen);
		
		
		notification.contentView = rm;
		it = new Intent(First.this, TabHo.class);
		PendingIntent pintent = PendingIntent.getActivity(this, 1, it, 1);
		notification.contentIntent = pintent;
		manager.notify(1, notification);
		new Thread() {
			public void run() {
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					it = new Intent(First.this, TabHo.class);
					startActivity(it);
					First.this.finish();
				}
			}
		}.start();
		
	}
	public class Nreceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			rm.setTextViewText(R.id.ntf_song, MymusicList.songlist.get(MymusicList.pos).getTitle());
			rm.setTextViewText(R.id.ntf_art, MymusicList.songlist.get(MymusicList.pos).getArtist());
			
			if (MymusicList.state == 1) {
				rm.setImageViewResource(R.id.ntf_ply, R.drawable.appwidget_icon_pause_normal);
			} else {
				rm.setImageViewResource(R.id.ntf_ply, R.drawable.appwidget_icon_play_normal);
			}
			manager.notify(1, notification);
		}

	}
}
