package com.example.ttplayer.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.ttplayer.R;
import com.example.ttplayer.R.id;
import com.example.ttplayer.R.layout;
import com.example.ttplayer.adapter.MymusicAdapter.ViewHolder;
import com.example.ttplayer.stati.MusicInstance;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

public class NetAdapter extends BaseAdapter {
	Context context;
	List<MusicInstance> musics;

	public NetAdapter(Context context, List<MusicInstance> musics) {
		this.context = context;
		this.musics = musics;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return musics.size();
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
	ViewHolder holder=new ViewHolder();
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

	
		if (convertView == null) {
			View v = View.inflate(context, R.layout.mymusic_item, null);
			holder = new ViewHolder();
			holder.text1 = (TextView) v.findViewById(R.id.txt_songname);
			holder.text2 = (TextView) v.findViewById(R.id.txt_songart);
			
			convertView = v;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
	
		holder.text1.setText((position + 1) + "." +musics.get(position).getTitle());
		holder.text2.setText((CharSequence)  musics.get(position).getArtist());
		
		
		

		return convertView;
	}

	class ViewHolder {
		TextView text1, text2;
		
	}

}
