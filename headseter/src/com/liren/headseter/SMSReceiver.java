package com.liren.headseter;

import java.util.Map;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver{

	@Override
	public void onReceive(final Context context, Intent intent) {
		// TODO Auto-generated method stub
		Log.i("msg", "new message");
		if (Constants.MoShi==3) {
			new Thread(){
				public void run() {
					try {
						sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					SmsDao smsdao = new SmsDao(context);
					Map<String, String> data = smsdao.getSmsData();
					
					Intent itent = new Intent(Constants.ACTION_RING);
					if (data != null) {
						itent.putExtra("data", Constants.SMSINFO);
						itent.putExtra("person", data.get("person"));
						itent.putExtra("body", data.get("body"));
					}

					context.sendBroadcast(itent);
				};
				
			}.start();
		}
		
		
	}
	
	
}

