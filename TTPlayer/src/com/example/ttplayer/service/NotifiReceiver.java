package com.example.ttplayer.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotifiReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		Intent ser = new Intent("com.exmple.ttplayer.playser");
		if("com.exmple.ttplayer.ntf_pre".equals(intent.getAction())){
			ser.putExtra("data", 2);
		}else if("com.exmple.ttplayer.ntf_play".equals(intent.getAction())){
			ser.putExtra("data", 1);
		}else if("com.exmple.ttplayer.ntf_next".equals(intent.getAction())){
			ser.putExtra("data", 3);
		}
		context.sendBroadcast(ser);
	}

}
