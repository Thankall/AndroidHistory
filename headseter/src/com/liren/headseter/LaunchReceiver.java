package com.liren.headseter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class LaunchReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(Constants.OnOff==2){
			return;
		}
		SharedPreferences preference = context.getSharedPreferences("Mo", 0);
		
		Constants.MoShi = preference.getInt("moshi", 1);
		Intent it = new Intent(context, RingService.class);
		context.startService(it);
	}

}
