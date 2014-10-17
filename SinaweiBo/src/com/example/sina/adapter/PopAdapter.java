package com.example.sina.adapter;

import java.util.List;

import com.example.sina.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

public class PopAdapter extends BaseAdapter {
	private Context context;
	private List<String> list;
	private List<String> div;
	public PopAdapter(Context context,List<String> list,List<String> div){
		this.context=context;
		this.list=list;
		this.div=div;
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
		// TODO Auto-generated method stub
		View v=View.inflate(context, R.layout.popname_text, null);
		View d=View.inflate(context, R.layout.popname_divide, null);
		TextView t=(TextView) v.findViewById(R.id.pop_name_text);
		TextView td=(TextView) d.findViewById(R.id.pop_div);
		if(!div.contains(list.get(position))){
			t.setText(list.get(position));
			return v;
		}else{
			td.setText(list.get(position).substring(3));
			return d;
		}
		
		
	}

	
}
