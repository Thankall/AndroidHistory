package com.liren.headseter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;
import android.util.Log;

public class SmsDao {
	private Context context;
	public SmsDao(Context context){
		this.context=context;
	}
	private Map<String, String> data;
	public Map<String, String> getSmsData(){
		
		Uri uri=Uri.parse(Constants.SMS_URI);
		ContentResolver cr=context.getContentResolver();
		String[] projection={"person","address","body","date"};
		String selection="read=0";
		//String[] selectionArgs={};
		Cursor c=cr.query(uri, projection, selection, null, "date desc");
		if(c==null||c.getCount()==0){
			return data;
		}else{
			c.moveToFirst();
			String person=c.getString(c.getColumnIndex("person"));
			String address=c.getString(c.getColumnIndex("address"));
			String body=c.getString(c.getColumnIndex("body"));
			String value=(person==null||"".equals(person))?address:person;
			Uri u=Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, address);
			Cursor cu=cr.query(u, new String[]{PhoneLookup.DISPLAY_NAME}, null, null, null);
			Log.i("msg", cu.getCount()+"");
			if(cu.moveToFirst()){
				value=cu.getString(cu.getColumnIndex(PhoneLookup.DISPLAY_NAME));
			}
			data =new HashMap<String, String>();
			
			data.put("person", value);
			data.put("body", body);
			
		}
		return data;
	}
}
