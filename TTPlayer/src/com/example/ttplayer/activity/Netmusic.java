package com.example.ttplayer.activity;

import java.io.File;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpConnection;
import org.json.JSONArray;
import org.json.JSONObject;

import com.example.ttplayer.R;
import com.example.ttplayer.adapter.NetAdapter;
import com.example.ttplayer.stati.DownloadUtil;
import com.example.ttplayer.stati.MusicInstance;
import com.example.ttplayer.stati.MymusicList;
import com.example.ttplayer.view.RefreshableView;
import com.example.ttplayer.view.RefreshableView.PullToRefreshListener;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class Netmusic extends Activity implements OnItemClickListener {
	ListView lview;
	ProgressBar pro;
	TextView title;
	int page=1;
	RefreshableView refresh;
	public static List<MusicInstance> musics = new ArrayList<MusicInstance>();
	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
//			pro.setVisibility(View.GONE);
//			lview.setVisibility(View.VISIBLE);
			lview.setAdapter(new NetAdapter(Netmusic.this, musics));
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.mymusic);
		refresh = (RefreshableView) findViewById(R.id.refresh);
		
		IntentFilter filter = new IntentFilter();
		filter.addAction("com.download");
		DownReceiver receiver = new DownReceiver();
		registerReceiver(receiver, filter);
		title = (TextView) findViewById(R.id.txt_title);
		title.setText("音乐圈");
		lview = (ListView) findViewById(R.id.list_mymusic_my);
		pro = (ProgressBar) findViewById(R.id.pro);
		// lview.setVisibility(View.GONE);
		pro.setVisibility(View.VISIBLE);
		musics = new ArrayList<MusicInstance>();
		new NetThread(page++).start();
		lview.setOnItemClickListener(this);
		refresh.setOnRefreshListener(new PullToRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				
				

				//musics.clear();
				NetThread t=new NetThread(page++);
				t.start();
				try {
					t.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				refresh.finishRefreshing();
			}
		}, 0);
	}

	private class NetThread extends Thread {
		int page;
		public NetThread(int p) {
			// TODO Auto-generated constructor stub
			page=p;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				URL url = new URL(
						"http://111.207.25.188:8080/MusicInfo/getMusicInfo?page="+page);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.connect();
				InputStream in = conn.getInputStream();
				byte[] data = new byte[1024];
				int i = 0;
				StringBuffer s = new StringBuffer();
				while ((i = in.read(data)) != -1) {
					s.append(new String(data, 0, i));
				}
				List<MusicInstance> mus = new ArrayList<MusicInstance>();
				JSONObject json = new JSONObject(s.toString());
				JSONArray jarray = (JSONArray) json.get("MusicInfo");
				for (int j = 0; j < jarray.length(); j++) {
					JSONObject musicInfo = jarray.getJSONObject(j);
					String mp3name = musicInfo.getString("mp3Name");
					String mp3Artist = musicInfo.getString("mp3Artist");
					String mp3Album = musicInfo.getString("mp3Album");
					String mp3Image = musicInfo.getString("mp3Image");
					String mp3FileName = musicInfo.getString("mp3FileName");
					String mp3Size = musicInfo.getString("mp3Size");
					String lrcName = musicInfo.getString("lrcName");
					String lrcSize = musicInfo.getString("lrcSize");
					String mp3AlbumImage = musicInfo.getString("mp3AlbumImage");
					MusicInstance music = new MusicInstance();
					music.setAlbum(mp3Album);
					music.setMp3AlbumImage(mp3AlbumImage);
					music.setArtist(mp3Artist);
					music.setTitle(mp3name);
					music.setPath(mp3FileName);
					mus.add(music);
				}
				
				musics=mus;
				handler.sendEmptyMessage(1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			super.run();
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String url = musics.get(position).getPath();
		File file = new File("/mnt/sdcard/ttplayer" + url);

		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		if (file.exists()) {

		} else {// 下载
			DownloadUtil.downLoad(Netmusic.this, url);
			Toast.makeText(Netmusic.this, "开始下载。。。", 0).show();
		}
	}

	private class DownReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Toast.makeText(Netmusic.this, "下载完成。", 0).show();
		}

	}
}
