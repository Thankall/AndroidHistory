package com.example.ttplayer.service;


import com.example.ttplayer.activity.Play;
import com.example.ttplayer.stati.MymusicList;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.IBinder;
import android.util.Log;

public class PlayService extends Service{
	
	
	MediaPlayer mp; 
	Thread t;
	boolean flag=false;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		IntentFilter filter=new IntentFilter();
		filter.addAction("com.exmple.ttplayer.playser");
		ServiceReciver reciver=new ServiceReciver();
		registerReceiver(reciver, filter);
		IntentFilter filterseek=new IntentFilter();
		filterseek.addAction("com.exmple.ttplayer.playseek");
		SeekReceiver receiverseek=new SeekReceiver();
		registerReceiver(receiverseek, filterseek);
		mp=new MediaPlayer();
		mp.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				if(MymusicList.pos<MymusicList.songlist.size()-1){
					MymusicList.pos++;
					//Log.i("msg", "wancheng");
					mp.reset();
					try {
						mp.setDataSource(MymusicList.songlist.get(MymusicList.pos).getPath());
						mp.prepare();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mp.start();
					MymusicList.state=1;
					Intent it=new Intent("com.statechanged");
					sendBroadcast(it);
				}
			}
		});
		
		t=new Thread(){
			public void run() {
				while(true){
					if(flag){
						Intent it = new Intent("com.example.ttplayer.seek");
						it.putExtra("pro", mp.getCurrentPosition());
						sendBroadcast(it);
						//Log.i("msg", "xian");
						try {
							sleep(1000);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					
				}
			};
		};
		t.start();
		
	}


	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	public class ServiceReciver extends BroadcastReceiver{

		

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int i=intent.getIntExtra("data", -1);

			Log.i("msg", i+"");
//			if(flag){
//				t.start();
//				flag=false;
//			}
			flag=true;
			if(intent.getIntExtra("diffrent", -1)==1){
				MymusicList.state=2;
			}
			Log.i("msg", MymusicList.state+"");
			switch (i) {
			case 1:
				Log.i("msg", "1111");
				if(MymusicList.state==1){
					mp.pause();
					MymusicList.state=0;
				}else if(MymusicList.state==0){
					
					mp.start(); 
					MymusicList.state=1;
				}else if(MymusicList.state==2){
					try {
						mp.reset();
						mp.setDataSource(MymusicList.songlist.get(MymusicList.pos).getPath());
						mp.prepare();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mp.start(); 
					MymusicList.state=1;
				}
				
				break;
			case 2:
				Log.i("msg", "22222");
				if((MymusicList.pos)!=0){
					MymusicList.pos--;
					mp.stop();
					mp.reset();
					try {
						mp.setDataSource(MymusicList.songlist.get(MymusicList.pos).getPath());
						mp.prepare();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mp.start();
					MymusicList.state=1;
				}
				break;
			case 3:
				Log.i("msg", "33333");
				if(MymusicList.pos<MymusicList.songlist.size()-1){
					MymusicList.pos++;
					mp.stop();
					mp.reset();
					try {
						mp.setDataSource(MymusicList.songlist.get(MymusicList.pos).getPath());
						mp.prepare();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					mp.start();
					MymusicList.state=1;
				}
				break;

			
			}
			
			Intent it=new Intent("com.statechanged");
			sendBroadcast(it);			
			
			
			
		}
		
	}
	public class SeekReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			int prgs=intent.getIntExtra("prgs", 0);
			mp.seekTo(prgs);
		}
		
	}
}
