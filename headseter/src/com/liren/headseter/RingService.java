package com.liren.headseter;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;



import java.util.Map;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint.Join;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class RingService extends Service {
	TextToSpeech tts;
	int t = 0;
	long etime = 0;
	AudioManager audioManager;
	ComponentName cn;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
//		IntentFilter inf = new IntentFilter();
//		inf.setPriority(1000);
//		inf.addAction(Intent.ACTION_MEDIA_BUTTON);
//		ListenerReceiver lre = new ListenerReceiver();
//		registerReceiver(lre, inf);
		tts = new TextToSpeech(this, new OnInitListener() {

			@Override
			public void onInit(int status) {
				// TODO Auto-generated method stub
				if (status == TextToSpeech.SUCCESS) {
					int result = tts.setLanguage(Locale.CHINA);
					if (result == TextToSpeech.LANG_MISSING_DATA
							|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
						Toast.makeText(RingService.this, "语言暂不支持,请下载中文语音工具",
								Toast.LENGTH_SHORT).show();
						ListenerReceiver.isClose=true;
					}else{
						ListenerReceiver.isClose=false;
					}
				}
			}
		});

		
		IntentFilter rf = new IntentFilter();
		rf.addAction(Constants.ACTION_RING);
		RingReceiver lr = new RingReceiver();
		registerReceiver(lr, rf);
		
//		IntentFilter rf2 = new IntentFilter();
//		rf2.addAction("android.provider.Telephony.SMS_RECEIVED");
//		SMSReceiver sr= new SMSReceiver();
//		registerReceiver(sr, rf2);
//		Log.i("msg", "rege");
		
//		audioManager = (AudioManager) this.getSystemService(AUDIO_SERVICE);
//		cn = new ComponentName(getPackageName(),
//				ListenerReceiver.class.getName());
//		audioManager.registerMediaButtonEventReceiver(cn);
		super.onCreate();
	}

	public class RingReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(final Context context, Intent intent) {
			// TODO Auto-generated method stub

			// Toast.makeText(context, "按下按键", Toast.LENGTH_SHORT).show();
			int data=intent.getIntExtra("data", -1);
			switch (data) {
			case Constants.NOWTIME:
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("H点mm分");
				String time = sdf.format(date);
				Toast.makeText(context, time, Toast.LENGTH_SHORT).show();
				tts.speak("现在时刻：" + time, TextToSpeech.QUEUE_FLUSH, null);
				break;
			case Constants.SMSNAME:
				String person=intent.getStringExtra("person");
				tts.speak("短信来自：" + person, TextToSpeech.QUEUE_FLUSH, null);
				break;
			case Constants.SMSBODY:
				String body=intent.getStringExtra("body");
				tts.speak("短信内容：" + body, TextToSpeech.QUEUE_FLUSH, null);
				break;
			case Constants.STOP:
				tts.speak("" , TextToSpeech.QUEUE_FLUSH, null);
				break;
			case Constants.SMSINFO:
				String p=intent.getStringExtra("person");
				String b=intent.getStringExtra("body");
				tts.speak(p+"发来消息："+b , TextToSpeech.QUEUE_FLUSH, null);
				break;

			default:
				tts.speak("没有新信息" , TextToSpeech.QUEUE_FLUSH, null);
				break;
			}
			

		}

	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		ListenerReceiver.isClose=true;
		super.onDestroy();
	}
	

}
