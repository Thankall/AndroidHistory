package com.example.ttplayer.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.example.ttplayer.R;
import com.example.ttplayer.R.drawable;
import com.example.ttplayer.R.id;
import com.example.ttplayer.R.layout;
import com.example.ttplayer.data.SqlMusicHelper;
import com.example.ttplayer.stati.MusicInstance;
import com.example.ttplayer.stati.MymusicList;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

public class MymusicAdapter extends BaseAdapter {

	private List<MusicInstance> songlist;
	private List<MusicInstance> favlist;
	
	private SqlMusicHelper dbhelper;
	
	private Context context;

	public MymusicAdapter(Context context) {
		this.context = context;
		dbhelper = new SqlMusicHelper(context, "songlist");
		songlist = MymusicList.songlist;
		favlist = MymusicList.favlist;
		

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return songlist.size();
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

	ViewHolder holder = null;

	Map<String, String> map;

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		map = new HashMap<String, String>();
		if (convertView == null) {
			View v = View.inflate(context, R.layout.mymusic_item, null);
			holder = new ViewHolder();
			holder.text1 = (TextView) v.findViewById(R.id.txt_songname);
			holder.text2 = (TextView) v.findViewById(R.id.txt_songart);
			holder.but = (ImageButton) v.findViewById(R.id.ibut_img);
			convertView = v;
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		map.put("song", songlist.get(position).getTitle());
		map.put("art", songlist.get(position).getArtist());
		holder.text1.setText((position + 1) + "." + map.get("song"));
		holder.text2.setText((CharSequence) map.get("art"));
		if ("T".equals(songlist.get(position).getIsfav())) {
			holder.but.setBackgroundResource(R.drawable.icon_favourite_checked);

		} else {
			holder.but.setBackgroundResource(R.drawable.icon_favourite_normal);
		}
		holder.but.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ("T".equals(songlist.get(position).getIsfav())) {
					dbhelper.updatetonotfav(songlist.get(position).getId());
					songlist.get(position).setIsfav("F");

					favlist.remove(songlist.get(position));

					v.setBackgroundResource(R.drawable.icon_favourite_normal);
				} else {
					songlist.get(position).setIsfav("T");
					dbhelper.updatetofav(songlist.get(position).getId());
					if (!favlist.contains(songlist.get(position))) {
						favlist.add(songlist.get(position));

					}

					v.setBackgroundResource(R.drawable.icon_favourite_checked);
				}
			}
		});

		return convertView;
	}

	class ViewHolder {
		TextView text1, text2;
		ImageButton but;
	}
}
