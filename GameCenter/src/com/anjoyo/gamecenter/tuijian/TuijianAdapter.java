package com.anjoyo.gamecenter.tuijian;

import java.util.ArrayList;

import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.adapter.DownloadAdapter;
import com.anjoyo.gamecenter.bean.AppBean;
import com.anjoyo.gamecenter.utils.AsyncImageLoader;
import com.anjoyo.gamecenter.utils.DownloadApp;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class TuijianAdapter extends BaseAdapter{
	Context context;
	ArrayList<TuijianBean> listTuijian;
	AsyncImageLoader imageLoader;
	
	public TuijianAdapter(Context context,ArrayList<TuijianBean> listTuijian){
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if(convertView == null){
			
			viewHolder = new ViewHolder();
			convertView = View.inflate(context, R.layout.tuijianlistview_item, null);
			viewHolder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon_tuijian);
			viewHolder.textTitle = (TextView) convertView.findViewById(R.id.gameName_tuijian);
			viewHolder.textFileSize = (TextView) convertView.findViewById(R.id.filesize_tuijian);
			viewHolder.download_tuijian=(Button) convertView.findViewById(R.id.download_tuijian);
			viewHolder.star = (RatingBar) convertView.findViewById(R.id.ratingbar_tuijian);
			convertView.setTag(viewHolder);
			
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		imageLoader.dispalyImageview("http://www.gamept.cn"+listTuijian.get(position).getIcon(), viewHolder.imgIcon, R.drawable.pic_c);
		viewHolder.textTitle.setText(listTuijian.get(position).getTitle());
		viewHolder.textFileSize.setText(listTuijian.get(position).getFilesize());
		viewHolder.star.setRating(listTuijian.get(position).getStar());
		viewHolder.download_tuijian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				AppBean app=new AppBean();
				app.setName(listTuijian.get(position).getTitle());
				app.setSize(listTuijian.get(position).getFilesize());
				app.setUrl(listTuijian.get(position).getFlashurl());
				if(DownloadAdapter.list.contains(app)){
					Toast.makeText(context, "任务已存在",Toast.LENGTH_SHORT ).show();
					return;
				}
				app.setPic(new ImageView(context));
				imageLoader.dispalyImageview("http://www.gamept.cn"+listTuijian.get(position).getIcon(), app.getPic(), R.drawable.pic_c);
				DownloadApp d_app=new DownloadApp(context, app.getUrl(), app.getName());
				d_app.start();
				Toast.makeText(context, "开始下载。。",Toast.LENGTH_SHORT ).show();
				app.setThread(d_app);
				DownloadAdapter.list.add(app);
			}
		});
		return convertView;
	}

	static class ViewHolder{
		ImageView imgIcon;
		TextView textTitle;
		TextView textFileSize;
		RatingBar star;
		Button download_tuijian;
	} 
}
