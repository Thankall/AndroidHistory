package com.example.ttplayer.adapter;

import com.example.ttplayer.R;
import com.example.ttplayer.R.drawable;
import com.example.ttplayer.R.id;
import com.example.ttplayer.R.layout;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class DingZhiAdapter extends BaseAdapter {

	private Context context = null;
	private String[] data = { "文件夹", "歌手", "专辑", "风格", "最近添加", "最近播放", "新建列表" };
	public boolean[] check = { false, false, false, false, false, false, false, };
	private int[] src = { R.drawable.icon_subscription_folder,
			R.drawable.icon_subscription_artist,
			R.drawable.icon_subscription_album,
			R.drawable.icon_subscription_genre,
			R.drawable.icon_subscription_recent_add,
			R.drawable.icon_subscription_play,
			R.drawable.icon_subscription_new_playlist, };

	public DingZhiAdapter(Context context) {
		this.context = context;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return src.length;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater inflater = LayoutInflater.from(context);
			convertView = inflater.inflate(R.layout.list_data, null);
			holder = new ViewHolder();
			holder.image = (ImageView) convertView.findViewById(R.id._img);
			holder.text = (TextView) convertView.findViewById(R.id._txt);
			holder.box = (CheckBox) convertView.findViewById(R.id._check);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.box
				.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						check[position] = isChecked;
					}
				});
		holder.box.setChecked(check[position]);
		holder.image.setImageResource(src[position]);
		holder.text.setText(data[position]);

		// layout.setLayoutParams(new
		// RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT,
		// RelativeLayout.LayoutParams.WRAP_CONTENT));
		return convertView;
	}

	private static class ViewHolder {
		public ImageView image;
		public TextView text;
		public CheckBox box;
	}

}
