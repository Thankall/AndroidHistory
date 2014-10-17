package com.example.ttplayer.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.ttplayer.R;
import com.example.ttplayer.R.drawable;
import com.example.ttplayer.R.id;
import com.example.ttplayer.R.layout;
import com.example.ttplayer.data.SqlMusicHelper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TabAdapter extends BaseAdapter {
	private int[] img = { R.drawable.icon_local_music,
			R.drawable.icon_favorites, R.drawable.icon_folder_plus,
			R.drawable.icon_artist_plus, R.drawable.icon_genre_plus,
			R.drawable.icon_album_plus, R.drawable.icon_download,
			R.drawable.icon_library_music_circle, R.drawable.icon_home_custom };
	private String title[] = { "我的音乐", "我的最爱", "文件夹", "关注好友", 
			"风格", "专辑","下载管理", "音乐圈","定制首页" };
	private List<Integer> num = new ArrayList<Integer>();
	private SqlMusicHelper dbhelper=null;
	private Context context=null;
	
	public TabAdapter(Context context){
		this.context=context;
		dbhelper=new SqlMusicHelper(context, "songlist");
		num.add(dbhelper.queryAll().getCount());
		num.add(dbhelper.queryFav().getCount());
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return img.length;
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
		ViewHolder holder;
		if(convertView==null){
			convertView=View.inflate(context, R.layout.grid, null);
			holder=new ViewHolder();
			holder.imgv=(ImageView) convertView.findViewById(R.id.img_grid);
			holder.txt=(TextView) convertView.findViewById(R.id.text_grid);
			holder.txtnum=(TextView) convertView.findViewById(R.id.text_gridnum);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.imgv.setImageResource(img[position]);
		holder.txt.setText(title[position]);
		if(position<num.size()){
			holder.txtnum.setText(num.get(position)+"");
			
		}

		
		
		return convertView;
	}
	static class ViewHolder{
		ImageView imgv;
		TextView txt;
		TextView txtnum;
	}
}
