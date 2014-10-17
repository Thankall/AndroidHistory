package com.anjoyo.gamecenter.rank;

import java.util.ArrayList;
import java.util.List;

import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.adapter.DownloadAdapter;
import com.anjoyo.gamecenter.bean.AppBean;
import com.anjoyo.gamecenter.utils.AsyncImageLoader;
import com.anjoyo.gamecenter.utils.CheckNetWork;
import com.anjoyo.gamecenter.utils.DownloadApp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class RankAdapter extends BaseAdapter{
	
	Context context;
	ArrayList<Rankbean> gameList;
	AsyncImageLoader imageLoader;
	
	public RankAdapter(Context context,ArrayList<Rankbean> gameList){
		this.context = context;
		this.gameList = gameList;
		imageLoader = new AsyncImageLoader();
	}
	
	public ArrayList<Rankbean> getDataList() {
		return gameList;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return gameList.size();
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
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.ranklistview_item, null);
			holder.imgIcon = (ImageView) convertView.findViewById(R.id.imgIcon_rank);
			holder.textViewTitle = (TextView) convertView.findViewById(R.id.gameName_rank);
			holder.textViewFilesize = (TextView) convertView.findViewById(R.id.filesize_rank);
			holder.star = (RatingBar) convertView.findViewById(R.id.ratingbar_rank);
			holder.download_tuijian=(ImageView) convertView.findViewById(R.id.imgdownload_rank);
			convertView.setTag(holder);
			
		}else{
			
			holder = (ViewHolder) convertView.getTag();
		}
		
		imageLoader.dispalyImageview("http://www.gamept.cn"+gameList.get(position).getIcon(), holder.imgIcon, R.drawable.ic_launcher);
		
		holder.textViewTitle.setText(gameList.get(position).getTitle());
		holder.textViewFilesize.setText(gameList.get(position).getFilesize());
		
		
			holder.star.setRating(gameList.get(position).getStar());
		
			holder.download_tuijian.setOnClickListener(new OnClickListener() {
				
				private boolean isdownload;

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final SharedPreferences share = context.getSharedPreferences("setting",
							Activity.MODE_PRIVATE);
					boolean ckwifi = share.getBoolean("cb_wifi", false);
					final Dialog dialog;
					if (ckwifi&&!new CheckNetWork().isWifiConnected(context)) {
						dialog=new AlertDialog.Builder(context).create();
						View dia_view=View.inflate(context, R.layout.dialog_iphone, null);
						CheckBox cb=(CheckBox) dia_view.findViewById(R.id.cb_notip);
						cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							
							@Override
							public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
								// TODO Auto-generated method stub
								SharedPreferences.Editor edit = share.edit();
								edit.putBoolean("cb_wifi", isChecked);
							}
						});
						Button next =(Button) dia_view.findViewById(R.id.but_ok);
						Button exit =(Button) dia_view.findViewById(R.id.but_no);
						next.setOnClickListener(new OnClickListener() {
							
							

							@Override
							public void onClick(View v) {
								
								// TODO Auto-generated method stub
								isdownload=true;
								
							}
						});
						exit.setOnClickListener(new OnClickListener() {
							
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								isdownload=false;
								dialog.dismiss();
							}
						});
						dialog.show();
						dialog.getWindow().setContentView(dia_view);
					}else{
						isdownload=true;
					}
					if (isdownload) {
						AppBean app=new AppBean();
						app.setName(gameList.get(position).getTitle());
						app.setSize(gameList.get(position).getFilesize());
						app.setUrl(gameList.get(position).getFlashurl());
						if(DownloadAdapter.list.contains(app)){
							Toast.makeText(context, "任务已存在",Toast.LENGTH_SHORT ).show();
							return;
						}
						app.setPic(new ImageView(context));
						imageLoader.dispalyImageview("http://www.gamept.cn"+gameList.get(position).getIcon(), app.getPic(), R.drawable.pic_c);
						DownloadApp d_app=new DownloadApp(context, app.getUrl(), app.getName());
						d_app.start();
						Toast.makeText(context, "开始下载。。。",Toast.LENGTH_SHORT ).show();
						app.setThread(d_app);
						DownloadAdapter.list.add(app);
					}
					
				}
			});
		return convertView;
	}

	static class ViewHolder{
		
		public ImageView download_tuijian;
		ImageView imgIcon;
		TextView textViewTitle;
		TextView textViewFilesize;
		RatingBar star;
	}
}
