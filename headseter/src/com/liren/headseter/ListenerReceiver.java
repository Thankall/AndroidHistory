package com.liren.headseter;

import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;

public class ListenerReceiver extends BroadcastReceiver {
	TextToSpeech tts;
	static int t = 0;
	static long etime = 0;
	static boolean flag = true;
	static boolean isClose = true;
	AudioManager audioManager;
	ComponentName cn;
	SmsDao smsdao;
	Map<String, String> data;

	public void onReceive(final Context context, Intent intent) {
	
		// TODO Auto-generated method stub
		if (isClose) {
			return;
		}
		Log.i("msg", "zzzzz"+Constants.MoShi);
		audioManager = (AudioManager) context
				.getSystemService(context.AUDIO_SERVICE);
		cn = new ComponentName(context.getPackageName(),
				ListenerReceiver.class.getName());
		KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
		Log.i("msg", event.getKeyCode() + "----" + event.getAction() + "sss");
		if (event != null && event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (event.getKeyCode()) {
			case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
			case KeyEvent.KEYCODE_HEADSETHOOK:
				if (Constants.MoShi!=2) {
					flag = true;
					audioManager.registerMediaButtonEventReceiver(cn);
				}
				if (flag) {
					
					abortBroadcast();
					flag = false;
					Intent it = new Intent(Constants.ACTION_RING);
					it.putExtra("data", Constants.NOWTIME);
					context.sendBroadcast(it);
					audioManager.unregisterMediaButtonEventReceiver(cn);
					
				} else {
					flag = true;
					Intent it = new Intent(Constants.ACTION_RING);
					it.putExtra("data", Constants.STOP);
					context.sendBroadcast(it);

					audioManager.registerMediaButtonEventReceiver(cn);
				}
				break;
			case KeyEvent.KEYCODE_MEDIA_STOP:
				abortBroadcast();
				smsdao = new SmsDao(context);
				data = smsdao.getSmsData();
				Intent itent = new Intent(Constants.ACTION_RING);
				if (data != null) {
					itent.putExtra("data", Constants.SMSINFO);
					itent.putExtra("person", data.get("person"));
					itent.putExtra("body", data.get("body"));
				}

				context.sendBroadcast(itent);
				break;
			case KeyEvent.KEYCODE_MEDIA_NEXT:
				abortBroadcast();
				smsdao = new SmsDao(context);
				data = smsdao.getSmsData();
				Intent it = new Intent(Constants.ACTION_RING);
				if (data != null) {
					it.putExtra("data", Constants.SMSNAME);
					it.putExtra("person", data.get("person"));
				}

				context.sendBroadcast(it);
				break;
			case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
				abortBroadcast();
				smsdao = new SmsDao(context);
				data = smsdao.getSmsData();
				Intent ite = new Intent(Constants.ACTION_RING);
				if (data != null) {
					ite.putExtra("data", Constants.SMSBODY);
					ite.putExtra("body", data.get("body"));
				}

				context.sendBroadcast(ite);
				break;

			default:
				break;
			}
		}

	}
}



