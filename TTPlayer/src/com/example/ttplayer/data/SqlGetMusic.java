package com.example.ttplayer.data;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;

public class SqlGetMusic extends Activity{
	private Cursor c;
	public SqlGetMusic() {
		
	}
	public  Cursor getMusicCursor(){
		c=getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, null);
		return c;
	}
}
