package com.example.ttplayer.adapter;

import com.example.ttplayer.R;
import com.example.ttplayer.R.id;
import com.example.ttplayer.R.layout;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TabAdapter44 extends BaseAdapter {
	
	private String name[] = { "张杰", "大张伟", "王麟", "徐誉滕", 
			 };
	private int fans[] = { 63464,4456,46456,56 };
	private String jieshao[] = { "我的音乐", "我的最爱", "是个人", "歌手"};
	
	private Context context=null;
	
	public TabAdapter44(Context context){
		this.context=context;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return name.length;
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
			convertView=View.inflate(context, R.layout.tab_44_item, null);
			holder=new ViewHolder();
			holder.txtn=(TextView) convertView.findViewById(R.id.txt_name44);
			holder.txtf=(TextView) convertView.findViewById(R.id.txt_fensi);
			holder.txti=(TextView) convertView.findViewById(R.id.txt_jieshao44);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		
		holder.txtn.setText(name[position]);
		holder.txtf.setText("粉丝数："+fans[position]);
		holder.txti.setText(jieshao[position]);
		

		
		
		return convertView;
	}
	static class ViewHolder{
		
		TextView txtn;
		TextView txtf,txti;
	}
}
