package com.anjoyo.gamecenter.adapter;

import java.util.ArrayList;
import java.util.List;

import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.activity.Setting;
import com.anjoyo.gamecenter.bean.AppBean;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class DownloadAdapter extends BaseAdapter {
	private Context context;
	private ViewHolder holder;
	private int pos;
	public static List<AppBean> list = new ArrayList<AppBean>();

	public DownloadAdapter(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;
		
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0) {
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
		pos = position;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.manage_download_item,
					null);
			holder = new ViewHolder();
			holder.iv_item_pic = (ImageView) convertView
					.findViewById(R.id.iv_item_pic);
			holder.tv_item_name = (TextView) convertView
					.findViewById(R.id.tv_item_name);
			holder.tv_down_pro = (TextView) convertView
					.findViewById(R.id.tv_down_pro);
			holder.but_detial = (Button) convertView
					.findViewById(R.id.but_detial);
			holder.but_cancel = (Button) convertView
					.findViewById(R.id.but_cancel);
			holder.but_pause = (Button) convertView
					.findViewById(R.id.but_pause);
			holder.probar_down = (ProgressBar) convertView
					.findViewById(R.id.probar_down);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.iv_item_pic.setImageDrawable(list.get(position).getPic().getDrawable());
		holder.tv_item_name.setText(list.get(position).getName());
		float progress = list.get(position).getThread().t;
		float size = Float.parseFloat(list.get(pos).getSize()
				.substring(0, list.get(pos).getSize().length() -2));
		int pro = (int) ((progress * 0.08 / size)>100?100:(progress * 0.08 / size));
		holder.tv_down_pro.setText(pro + "% (" + ((int)(size*pro))/100.00 + "M/"
				+ list.get(pos).getSize() + ")");
		holder.probar_down.setProgress(pro);
		holder.but_detial.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		holder.but_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				list.get(position).getThread().download=false;
				list.remove(position);
			}
		});
		holder.but_pause.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if ("暂停".equals(holder.but_pause.getText())) {
					holder.but_pause.setText("开始");
					list.get(position).getThread().flag=true;
				} else {
					holder.but_pause.setText("暂停");
					list.get(position).getThread().flag=false;
				}
			}
		});
		return convertView;
	}



	private static class ViewHolder {
		ImageView iv_item_pic;
		TextView tv_item_name;
		TextView tv_down_pro;
		ProgressBar probar_down;
		Button but_detial;
		Button but_cancel;
		Button but_pause;
	}

}
