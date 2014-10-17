package com.anjoyo.gamecenter.detail;

import java.util.ArrayList;

import com.anjoyo.gamecenter.tuijian.TuijianBean;
import com.anjoyo.gamecenter.utils.AsyncImageLoader;
import com.anjoyo.gamecenter.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class DetailAdapter extends BaseAdapter{
	Context context;
	ArrayList<TuijianBean> listTuijian;
	AsyncImageLoader imageLoader;
	
	public DetailAdapter(Context context,ArrayList<TuijianBean> listTuijian){
		this.context = context;
		this.listTuijian = listTuijian;
		
		imageLoader = new AsyncImageLoader();
	}
	
	public ArrayList<TuijianBean> getDataList(){
		return listTuijian;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listTuijian.size();
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
		ViewHolder viewHolder = null;
		if(convertView == null){
			
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.tuijianlistview_item, null);
			viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon_tuijian);
			viewHolder.textTitle = (TextView) convertView.findViewById(R.id.gameName_tuijian);
			viewHolder.textFileSize = (TextView) convertView.findViewById(R.id.filesize_tuijian);
			viewHolder.star = (RatingBar) convertView.findViewById(R.id.ratingbar_tuijian);
			convertView.setTag(viewHolder);
			
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		
		imageLoader.dispalyImageview("http://www.gamept.cn"+listTuijian.get(position).getIcon(), viewHolder.imgIcon, R.drawable.pic_c);
		viewHolder.textTitle.setText(listTuijian.get(position).getTitle());
		viewHolder.textFileSize.setText(listTuijian.get(position).getFilesize());
		viewHolder.star.setRating(listTuijian.get(position).getStar());
		
		return convertView;
	}

	static class ViewHolder{
		ImageView imgIcon;
		TextView textTitle;
		TextView textFileSize;
		RatingBar star;
	} 
}
