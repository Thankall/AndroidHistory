package com.example.sina.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.sina.Comment;
import com.example.sina.R;
import com.example.sina.appstatic.AppStatic;
import com.example.sina.instance.Statuse;
import com.example.sina.util.DownImageSync;
import com.example.sina.util.TimeUtil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WeiBoAdapter extends BaseAdapter {
	private Context context;
	private List<Statuse> statuses;
	private Bitmap bit;
	private Map<String, Bitmap> maps = new HashMap<String, Bitmap>();

	public WeiBoAdapter(Context context) {
		this.context = context;
		statuses = AppStatic.statuses;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return statuses.size();
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
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.weibo_item, null);
			holder.ivicon = (ImageView) convertView.findViewById(R.id.ivicon);
			// holder.ivimg = (ImageView)
			// convertView.findViewById(R.id.statusesimg);
			holder.tvname = (TextView) convertView.findViewById(R.id.username);
			holder.tvtext = (TextView) convertView.findViewById(R.id.tvtext);
			holder.tvcommentcout = (TextView) convertView
					.findViewById(R.id.commentcount);
			holder.tvlikecount = (TextView) convertView
					.findViewById(R.id.likecount);
			holder.tvtime = (TextView) convertView.findViewById(R.id.tvtime);
			holder.tvsource = (TextView) convertView
					.findViewById(R.id.tvsource);
			holder.tvredcount = (TextView) convertView
					.findViewById(R.id.redcount);
			holder.retext = (TextView) convertView.findViewById(R.id.re_text);
			holder.ivimg = (ImageView) convertView.findViewById(R.id.ivimg);
			holder.ivreimg = (ImageView) convertView.findViewById(R.id.re_img);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.tvname
				.setText(statuses.get(position).getUser().getScreen_name());
		holder.tvtime.setText(TimeUtil.getShortTime(statuses.get(position)
				.getCreated_at()));
		String s = statuses.get(position).getSource();
		holder.tvsource.setText("来自："
				+ s.substring(s.indexOf(">") + 1, s.lastIndexOf("<")));
		holder.tvtext.setText(statuses.get(position).getText());
		holder.tvlikecount.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("msg", "like");
			}
		});
		holder.tvcommentcout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent it=new Intent(context, Comment.class);
				it.putExtra("from", "comment");
				it.putExtra("id", statuses.get(position).getId()+"");
				context.startActivity(it);
				//Log.i("msg", "tvcommentcout");
			}
		});
		holder.tvredcount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.i("msg", "tvcommentcout");
				Intent it=new Intent(context, Comment.class);
				it.putExtra("id", statuses.get(position).getId()+"");
				it.putExtra("from", "recount");
				context.startActivity(it);
			}
		});
		String u = statuses.get(position).getUser().getProfile_image_url();
		holder.ivicon.setTag(u);
		bit = getBitmap(u, holder.ivicon);
		if (bit == null) {
			holder.ivicon.setImageResource(R.drawable.portrait);
		} else {
			holder.ivicon.setImageBitmap(bit);
		}

		u = statuses.get(position).getThumbnail_pic();
		holder.ivimg.setTag(u);
		bit = getBitmap(u, holder.ivimg);
		if (bit == null) {
			holder.ivimg.setVisibility(View.GONE);
			//holder.ivimg.setImageResource(R.drawable.bg_face_sel);
		} else {
			holder.ivimg.setImageBitmap(bit);
		}
		if (statuses.get(position).getRetweeted_status() != null) {
			View v = convertView.findViewById(R.id.re_rl);
			v.setVisibility(View.VISIBLE);
			String name = statuses.get(position).getRetweeted_status()
					.getUser().getScreen_name();
			holder.retext.setText("@" + name + ":"
					+ statuses.get(position).getRetweeted_status().getText());
			if (statuses.get(position).getRetweeted_status().getThumbnail_pic() !=null) {
				String p = statuses.get(position).getRetweeted_status()
						.getThumbnail_pic();
				holder.ivreimg.setTag(p);
				bit = getBitmap(p, holder.ivreimg);
				if (bit == null) {
					holder.ivimg.setVisibility(View.GONE);
					//holder.ivimg.setImageResource(R.drawable.bg_face_sel);
				} else {
					holder.ivreimg.setImageBitmap(bit);
				}
			}
		}
		

		return convertView;
	}

	private Bitmap getBitmap(String u, ImageView imgv) {
		// TODO Auto-generated method stub
		if (maps.get(u) != null) {
			return maps.get(u);
		}
		if (u == null || "".equals(u)) {
			return null;
		}

		new DownImageSync(maps, imgv).execute(u);
		return null;
	}

	private class ViewHolder {
		TextView tvname, tvtext, retext, tvcommentcout, tvredcount,
				tvretcontent, tvtime, tvsource, tvlikecount;
		ImageView ivicon, ivimg, ivreimg;
	}

}
