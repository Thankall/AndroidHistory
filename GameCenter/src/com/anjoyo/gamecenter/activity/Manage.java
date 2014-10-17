package com.anjoyo.gamecenter.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.adapter.AllappAdapter;
import com.anjoyo.gamecenter.adapter.DownloadAdapter;
import com.anjoyo.gamecenter.adapter.FinishAdapter;
import com.anjoyo.gamecenter.bean.AppBean;
import com.anjoyo.gamecenter.utils.AppsUtil;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Manage extends Activity implements OnClickListener {
	private TextView txtDownlaodManger;
	private TextView txtSoftManger;
	private Button but_pause;
	private RelativeLayout relDownload;
	private RelativeLayout relManagerSoft;
	private ListView listviewlocal;
	private ListView lvdownload;
	private ListView lvfinish;
	private List<AppBean> list_finish;
	private LinearLayout pre_view;
	private DownloadAdapter down_adapter;
	private FinishAdapter finish_adapter;
	private Handler hander=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				finish_adapter=new FinishAdapter(Manage.this, list_finish);
				lvfinish.setAdapter(finish_adapter);
				break;

			default:
				break;
			}
		};
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.manage_layout);
		init();
		down_adapter = new DownloadAdapter(this);
		lvdownload.setAdapter(down_adapter);

		finish_adapter = new FinishAdapter(Manage.this, list_finish);
		lvfinish.setAdapter(finish_adapter);
		IntentFilter if_fini = new IntentFilter();
		if_fini.addAction("downloadfinished");
		DownFinisher df = new DownFinisher();
		registerReceiver(df, if_fini);
		IntentFilter inf = new IntentFilter();
		inf.addAction("downloadapp");
		DownReceiver re = new DownReceiver();
		registerReceiver(re, inf);
	}

	class DownFinisher extends BroadcastReceiver {

		private List<AppBean> list;

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String name = intent.getStringExtra("name");
			Log.i("msg", name);
			down_adapter.notifyDataSetChanged();
			list = DownloadAdapter.list;
			for (int i = 0; i < list.size(); i++) {
				if (name.equals(list.get(i).getName())) {
					AppBean app = list.get(i);
					list.remove(i);
					list_finish.add(app);
					Log.i("msg", list_finish.size()+"===========");
				
					break;
				}
				
			}
			hander.sendEmptyMessage(1);
			String str = "/GameCenter/"+name+".apk";
			String fileName = Environment.getExternalStorageDirectory() + str; 
			Intent it = new Intent(Intent.ACTION_VIEW); 
			 it.setDataAndType(Uri.fromFile(new File(fileName)), "application/vnd.android.package-archive"); 
			startActivity(it);
			
		}

	}

	class DownReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			down_adapter.notifyDataSetChanged();

		}

	}

	private void init() {
		// TODO Auto-generated method stub
		txtDownlaodManger = (TextView) findViewById(R.id.txtDownlaodManger);
		txtSoftManger = (TextView) findViewById(R.id.txtSoftManger);
		relDownload = (RelativeLayout) findViewById(R.id.relDownload);
		relManagerSoft = (RelativeLayout) findViewById(R.id.relManagerSoft);
		txtDownlaodManger.setOnClickListener(this);
		txtSoftManger.setOnClickListener(this);
		listviewlocal = (ListView) findViewById(R.id.listviewlocal);
		lvdownload = (ListView) findViewById(R.id.listviewDownload);
		lvfinish = (ListView) findViewById(R.id.listviewDownloaded);
		list_finish = new ArrayList<AppBean>();
		lvdownload.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				if (pre_view != null) {
					pre_view.setVisibility(View.GONE);
				}
				Log.i("msg", "aazxcz");
				LinearLayout item_bar = (LinearLayout) view
						.findViewById(R.id.ll_item_down_bar);
				pre_view = item_bar;
				item_bar.setVisibility(View.VISIBLE);
				down_adapter.notifyDataSetInvalidated();
			}

		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.txtDownlaodManger:
			txtDownlaodManger.setBackgroundResource(R.drawable.button2_click);
			txtSoftManger.setBackgroundResource(R.drawable.button2_normal);
			relManagerSoft.setVisibility(View.GONE);
			relDownload.setVisibility(View.VISIBLE);
			lvdownload.setAdapter(down_adapter);

			break;
		case R.id.txtSoftManger:
			txtSoftManger.setBackgroundResource(R.drawable.button2_click);
			txtDownlaodManger.setBackgroundResource(R.drawable.button2_normal);
			relDownload.setVisibility(View.GONE);
			relManagerSoft.setVisibility(View.VISIBLE);
			List<PackageInfo> apps = AppsUtil.getAllApps(this);
			AllappAdapter appsadapter = new AllappAdapter(this, apps);
			listviewlocal.setAdapter(appsadapter);
			listviewlocal.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					if (pre_view != null) {
						pre_view.setVisibility(View.GONE);
					}
					LinearLayout item_bar = (LinearLayout) view
							.findViewById(R.id.ll_item_bar);
					pre_view = item_bar;
					item_bar.setVisibility(View.VISIBLE);
				}

			});

			break;

		default:
			break;
		}
	}
}
