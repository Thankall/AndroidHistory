package com.example.sina;

import java.util.ArrayList;
import java.util.List;

import com.example.sina.adapter.PopAdapter;
import com.example.sina.adapter.WeiBoAdapter;
import com.example.sina.appstatic.AppStatic;
import com.example.sina.thread.UserThread;
import com.example.sina.thread.WeiBoThread;
import com.example.sina.util.AccessTokenKeeper;
import com.example.sina.util.RefreshableView;
import com.example.sina.util.RefreshableView.PullToRefreshListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;

public class HomeAct extends Activity implements OnClickListener {
	private TextView name;
	private ListView lv;
	private ImageButton new_weibo;
	private ImageButton pop_weibo;
	private WeiBoAdapter adapter;
	private int lastindex;
	private int page=1;
	private RefreshableView refresh;
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				//Log.i("msg", "sss"+AppStatic.statuses.size());
				
				adapter = new WeiBoAdapter(HomeAct.this);
				lv.setAdapter(adapter);
				refresh.finishRefreshing();
				break;
			case 2:
				
				name.setText(AppStatic.user.getScreen_name());
				break;
			case 3:
				adapter.notifyDataSetChanged();
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
		setContentView(R.layout.home);
		init();
		
		String token = AccessTokenKeeper.readAccessToken(this).getToken();
		final String url = "https://api.weibo.com/2/statuses/home_timeline.json?access_token="
				+ token+"&page=";
		new WeiBoThread(url+(page++), handler,false).start();
		new UserThread("https://api.weibo.com/2/users/show.json?access_token="
				+ token + "&uid="
				+ AccessTokenKeeper.readAccessToken(this).getUid(), handler)
				.start();
		;
		refresh.setOnRefreshListener(new PullToRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				page=1;
				new WeiBoThread(url+(page++), handler,false).start();
			}
		}, 1);
		lv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				//Log.i("msg", adapter.getCount()+"ad");
				if ((lastindex == adapter.getCount())
						&& (scrollState == OnScrollListener.SCROLL_STATE_IDLE)) {
					//Log.i("msg", "onscroll");
					new WeiBoThread(url+(page++), handler,true).start();
					
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				lastindex = firstVisibleItem + visibleItemCount ;
				//Log.i("msg", lastindex+"last");
			}
		});
	}

	private void init() {
		name = (TextView) findViewById(R.id.name);
		name.setOnClickListener(this);
		new_weibo = (ImageButton) findViewById(R.id.new_weibo);
		new_weibo.setOnClickListener(this);
		pop_weibo = (ImageButton) findViewById(R.id.pop_weibo);
		pop_weibo.setOnClickListener(this);
		lv = (ListView) findViewById(R.id.home_list);
		lv.setSelector(R.drawable.hide_listview_yellow_selector);
		refresh = (RefreshableView) findViewById(R.id.refresh);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.name:
			String[] data = {  "首页", "好友圈", "我的微博", "周边微博", };
			String[] group = { "特别关注", "明星", "其他", "朋友", "媒体", };
			String[] div = { "TAG分组", "TAG其他", };
			String[] other = { "智能排序" };
			
			List<String> list=new ArrayList<String>();
			List<String> listTag=new ArrayList<String>();
			for (int i = 0; i < data.length; i++) {
				list.add(data[i]);
			}
			list.add(div[0]);
			listTag.add(div[0]);
			for (int i = 0; i < group.length; i++) {
				list.add(group[i]);
			}
			list.add(div[1]);
			listTag.add(div[1]);
			for (int i = 0; i < other.length; i++) {
				list.add(other[i]);
			}
			View popname_view = View.inflate(this, R.layout.popname, null);
			PopupWindow popWin_name = new PopupWindow(popname_view, 400, 800,
					false);
			ListView name_list = (ListView) popname_view
					.findViewById(R.id.pop_name_list);
			name_list.setAdapter(new PopAdapter(this,list,listTag));

			popWin_name.setFocusable(true);
			popWin_name.setBackgroundDrawable(new ColorDrawable(0));
			popWin_name.showAsDropDown(name, -100, 0);
			//popWin_name.showAtLocation(name, Gravity.CENTER_HORIZONTAL, 0, -80);
			break;
		case R.id.new_weibo:
			Intent it=new Intent(this,SendWeiBo.class);
			
			startActivity(it);
			break;
		case R.id.pop_weibo:
			View popview = View.inflate(this, R.layout.popweibo, null);
			PopupWindow popWin = new PopupWindow(popview, 300,
					LayoutParams.WRAP_CONTENT, false);
			TextView ref = (TextView) popview.findViewById(R.id.pop_refresh);
			TextView qrcode = (TextView) popview.findViewById(R.id.pop_qrcode);
			ref.setOnClickListener(this);
			qrcode.setOnClickListener(this);
			popWin.setFocusable(true);
			popWin.setBackgroundDrawable(new ColorDrawable(0));
			popWin.showAsDropDown(pop_weibo);
			break;
		case R.id.pop_refresh:
			Log.i("msg", "pop_refresh");
		case R.id.pop_qrcode:
			Log.i("msg", "qrcode");
		default:
			break;
		}
	}
}
