package com.anjoyo.gamecenter.activity;

import java.util.List;

import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.bean.AppBean;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class FinishAdapter extends BaseAdapter {

	private Context context;
	private List<AppBean> list;

	public FinishAdapter(Context context, List<AppBean> list_finish) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list=list_finish;
	}

	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
		ViewHolder holder;
		Log.i("msg", "pos"+position);
		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.manage_finished_item,
					null);
			holder = new ViewHolder();
			holder.iv_item_pic = (ImageView) convertView
					.findViewById(R.id.iv_item_pic);
			holder.tv_item_name = (TextView) convertView
					.findViewById(R.id.tv_item_name);
			holder.tv_down_pro = (TextView) convertView
					.findViewById(R.id.tv_size);
			holder.but_detial = (Button) convertView
					.findViewById(R.id.but_detial);
			holder.but_cancel = (Button) convertView
					.findViewById(R.id.but_cancel);
			holder.but_pause = (Button) convertView
					.findViewById(R.id.but_pause);
			holder.iv_item_pic.setImageDrawable(list.get(position).getPic().getDrawable());
			holder.tv_item_name.setText(list.get(position).getName());
			holder.tv_down_pro.setText(list.get(position).getSize());
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
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
