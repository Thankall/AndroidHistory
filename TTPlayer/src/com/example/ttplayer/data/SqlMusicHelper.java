package com.example.ttplayer.data;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.MediaStore;

public class SqlMusicHelper extends SQLiteOpenHelper {
	private String tablename="musiclist";
	private Cursor c,all;
	private Context context;
	public SqlMusicHelper(Context context, String name) {
		super(context, name, null, 1);
		this.context=context;
		c=new SqlGetMusic().getMusicCursor();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql="create table " + tablename + "("
				+ "_id integer primary key ,"
				+ "title varchar(10) not null," 
				+ "size long,"
				+ "duration long,"
				+ "artist varchar(10),"
				+ "album varchar(10),"
				+ "path varchar(100),"
				+"isfav varchar(1))";
		db.execSQL(sql);
		for(c.moveToFirst();!c.isAfterLast();c.moveToNext()){
			String init="insert into " + tablename + " (title,size,duration,artist,album,path) values (?,?,?,?,?,?)";
			String []args={c.getString(c.getColumnIndex(MediaStore.Audio.Media.TITLE)),
					c.getLong(c.getColumnIndex(MediaStore.Audio.Media.SIZE))+"",
			c.getLong(c.getColumnIndex(MediaStore.Audio.Media.DURATION))+"",
			c.getString(c.getColumnIndex(MediaStore.Audio.Media.ARTIST)),
			c.getString(c.getColumnIndex(MediaStore.Audio.Media.ALBUM)),
			c.getString(c.getColumnIndex(MediaStore.Audio.Media.DATA))};
			db.execSQL(init, args);
		}
		
	}
	public Cursor queryAll(){
		SQLiteDatabase db=getWritableDatabase();
		String sql="select * from "+ tablename;
		all=db.rawQuery(sql, null);
		return all;
		
	}
	public Cursor queryFav(){
		SQLiteDatabase db=getWritableDatabase();
		String sql="select * from "+ tablename+" where isfav='T'";
		all=db.rawQuery(sql, null);
		return all;
	}
	public void updatetofav(int id){
		SQLiteDatabase db=getWritableDatabase();
		String sql="update "+tablename+" set isfav='T' where _id=?";
		db.execSQL(sql, new String[]{id+""});
	}
	public void updatetonotfav(int id){
		SQLiteDatabase db=getWritableDatabase();
		String sql="update "+tablename+" set isfav='F' where _id=?";
		db.execSQL(sql, new String[]{id+""});
	}
	public void delete(int id){
		SQLiteDatabase db=getWritableDatabase();
		String sql="delete from "+tablename+" where _id=?";
		db.execSQL(sql, new String[]{id+""});
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("drop table if exists "+ tablename);
		this.onCreate(db);
	}
	private class SqlGetMusic {
		private Cursor c;
		public SqlGetMusic() {
			
		}
		public  Cursor getMusicCursor(){
			c=context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
			return c;
		}
	}
}
