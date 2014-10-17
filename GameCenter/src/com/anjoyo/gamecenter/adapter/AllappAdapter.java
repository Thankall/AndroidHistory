package com.anjoyo.gamecenter.adapter;

import java.util.List;











import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.activity.Setting;

import android.content.Context;
import android.content.Intent;
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
import android.widget.TextView;

public class AllappAdapter extends BaseAdapter  {
	private Context context;
	private List<PackageInfo> list;
	public AllappAdapter(Context context,List list) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.list=list;
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
		ViewHolder holder;
		if(convertView==null){
			convertView=View.inflate(context, R.layout.manage_item, null);
			holder=new ViewHolder();
			holder.iv_item_pic=(ImageView) convertView.findViewById(R.id.iv_item_pic);
			holder.tv_item_name=(TextView) convertView.findViewById(R.id.tv_item_name);
			holder.but_remo=(Button) convertView.findViewById(R.id.but_remo);
			holder.but_move=(Button) convertView.findViewById(R.id.but_move);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
 
		PackageManager pManager = context.getPackageManager();
		holder.iv_item_pic.setImageDrawable(pManager.getApplicationIcon(list.get(position).applicationInfo));
		holder.tv_item_name.setText(pManager.getApplicationLabel(list.get(position).applicationInfo));		
		holder.but_remo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri=Uri.parse("package:"+list.get(position).applicationInfo.packageName);
				Intent it=new Intent(Intent.ACTION_DELETE, uri);
				context.startActivity(it);
			}
		});
		holder.but_move.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Uri uri=Uri.parse("package:"+list.get(position).applicationInfo.packageName);
				Intent it=new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, uri);
				context.startActivity(it);
			}
		});
		return convertView;
	}
	private static class ViewHolder{
		ImageView iv_item_pic;
		TextView tv_item_name;
		Button but_remo;
		Button but_move;
	}


}
