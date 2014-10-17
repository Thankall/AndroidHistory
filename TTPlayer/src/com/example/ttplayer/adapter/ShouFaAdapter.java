package com.example.ttplayer.adapter;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ttplayer.R;
import com.example.ttplayer.stati.DownImageSync;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ShouFaAdapter extends BaseAdapter {
	Context context;
	List<String> img_list;
	Map<String, Bitmap> maps=new HashMap<String, Bitmap>();
 	public ShouFaAdapter(Context context, List<String> img_list) {
		this.context = context;
		this.img_list = img_list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return img_list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView img = new ImageView(context);
		
		Bitmap bit=getImage(img_list.get(position),img);
		if(bit==null){
			//Log.i("msg", "d2");
			img.setImageResource(R.drawable.online_default_big_logo);
		}else{
			//Log.i("msg", "d333");
			img.setImageBitmap(bit);
		}
		
		return img;
	}

	public Bitmap getImage(String url,ImageView img) {
		if (maps.get(url) != null) {
			// 如果集合有值，也就是内存中有数据，就直接用内存中的数据，为了快和省资源
			
			return maps.get(url);
		} else {
			// 到本地加载
			File file = new File("/mnt/sdcard/ttplayer" + url);
			
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (file.exists()) {
				Bitmap bitmap = BitmapFactory.decodeFile("/mnt/sdcard/ttplayer" + url);
				
				return bitmap;
			}

		}
		// 访问网络下载图片 asynctask

		new DownImageSync(maps,img).execute(url);
		//Log.i("msg", "down");
		return null;
	
		
	}
}
