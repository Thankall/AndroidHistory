package com.anjoyo.gamecenter.adapter;

import java.util.ArrayList;





import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.bean.AnjoyoClassificationBean;
import com.anjoyo.gamecenter.utils.AsyncImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ClassificationAdapter extends BaseAdapter{
	ArrayList<AnjoyoClassificationBean> anjoyoClassificationBeans;
	Context context;
	AsyncImageLoader imageLoader;
	public ClassificationAdapter(Context context,ArrayList<AnjoyoClassificationBean> anjoyoClassificationBeans){
		this.anjoyoClassificationBeans=anjoyoClassificationBeans;
		this.context=context;
		
		imageLoader = new AsyncImageLoader();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return anjoyoClassificationBeans.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return anjoyoClassificationBeans.get(position);
	}

	@Override
	public long getItemId(int  position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		viewholder holder=null;
		if (convertView==null) {
			holder=new viewholder();
			convertView=View.inflate(context, R.layout.listview_items, null);
			holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
			holder.tv_num=(TextView) convertView.findViewById(R.id.tv_num);
			holder.ib_class=(ImageView) convertView.findViewById(R.id.ib_class);
		    convertView.setTag(holder);
		    
		}else {
			holder=(viewholder) convertView.getTag();
		}
		imageLoader.dispalyImageview("http://www.gamept.cn"+anjoyoClassificationBeans.get(position).getClassimg(), holder.ib_class, R.drawable.pic_c);
		holder.tv_name.setText(anjoyoClassificationBeans.get(position).getClassname().toString());
		holder.tv_num.setText("共含"+anjoyoClassificationBeans.get(position).getTotal()+"款");
		return convertView;
	}
	public class  viewholder{
		TextView tv_name;
		TextView tv_num;
		ImageView ib_class;
	}

}
