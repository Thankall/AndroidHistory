package com.example.ttplayer.adapter;

import java.util.ArrayList;
import java.util.List;

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

public class MyfavAdapter extends BaseAdapter {

	private List<MusicInstance> favlist, songlist;
	private Context context;

	public MyfavAdapter(Context context) {
		this.context = context;
		this.favlist = MymusicList.favlist;
		this.songlist = MymusicList.songlist;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return favlist.size();
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

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

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
		holder.text1.setText((position + 1) + "."
				+ favlist.get(position).getTitle());
		holder.text2.setText(favlist.get(position).getArtist());
		holder.but.setBackgroundResource(R.drawable.icon_favourite_checked);
		holder.but.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SqlMusicHelper dbhelper = new SqlMusicHelper(context,
						"songlist");
				dbhelper.updatetonotfav(songlist.get(songlist.indexOf(favlist.get(position))).getId());
				songlist.get(songlist.indexOf(favlist.get(position))).setIsfav("F");
				favlist.remove(position);
				MyfavAdapter.this.notifyDataSetChanged();

			}
		});

		return convertView;
	}

	class ViewHolder {
		TextView text1, text2;
		ImageButton but;
	}
}
