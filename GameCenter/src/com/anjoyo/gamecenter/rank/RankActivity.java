package com.anjoyo.gamecenter.rank;


import java.util.ArrayList;

import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.detail.DetailActivity;
import com.anjoyo.gamecenter.tuijian.IntrodParse;
import com.anjoyo.gamecenter.tuijian.TuijianBean;
import com.anjoyo.gamecenter.utils.Constants;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;


public class RankActivity extends Activity implements OnClickListener,OnItemClickListener,OnRefreshListener2<ListView>{

	Button newest,hottest;
	PullToRefreshListView gameListView;
	ArrayList<Rankbean> gameList;
	//下拉加载集合
	RankAdapter adapter;
	ArrayList<Rankbean> gameLists;
	
	int gameId;
	int page = 1;
	Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 3:
				//gameList = (ArrayList<Rankbean>) msg.obj;
				adapter = new RankAdapter(RankActivity.this, gameList);
				gameListView.setAdapter(adapter);
				break;
			case Constants.NEWESTWHAT:
				//gameList = (ArrayList<Rankbean>) msg.obj;
				adapter = new RankAdapter(RankActivity.this, gameList);
				gameListView.setAdapter(adapter);
				
				 RankActivity.this.newest.setBackgroundResource(R.drawable.button_click);
			        RankActivity.this.hottest.setBackgroundResource(R.drawable.button_normal);
			        RankActivity.this.newest.setTextColor(Color.parseColor("#ffffff"));
			        RankActivity.this.hottest.setTextColor(Color.parseColor("#000000"));
				break;

			case Constants.HOTESTWHAT:
				//gameList = (ArrayList<Rankbean>) msg.obj;
				adapter = new RankAdapter(RankActivity.this, gameList);
				gameListView.setAdapter(adapter);
				
				 RankActivity.this.newest.setBackgroundResource(R.drawable.button_normal);
			      RankActivity.this.hottest.setBackgroundResource(R.drawable.button_click);
			      RankActivity.this.hottest.setTextColor(Color.parseColor("#ffffff"));
			      RankActivity.this.newest.setTextColor(Color.parseColor("#000000"));
				break;
				//下拉刷新
			case Constants.RANKDOWNWHAT:
				//gameList = (ArrayList<Rankbean>) msg.obj;
				adapter = new RankAdapter(RankActivity.this, gameList);
				gameListView.setAdapter(adapter);
				
				gameListView.onRefreshComplete();
				break;
				//下拉加载
			case Constants.RANKUPWHAT:
				
				//gameLists = (ArrayList<Rankbean>) msg.obj;
			//	RankAdapter adapterUp = new RankAdapter(RankActivity.this, gameList);
				adapter.getDataList().addAll(gameLists);
				adapter.notifyDataSetChanged();
				gameListView.onRefreshComplete();
				
				break;
			}
		};
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rankactivity);
		initView();
		newest.setOnClickListener(this);
		hottest.setOnClickListener(this);
		gameListView.setOnItemClickListener(this);
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		newest = (Button) this.findViewById(R.id.newest_rank);
		hottest = (Button) this.findViewById(R.id.hotest_rank);
		gameListView = (PullToRefreshListView) this.findViewById(R.id.listview_rank);
		gameList = new ArrayList<Rankbean>();
		getData();
		
	}

	private void getData() {
		// TODO Auto-generated method stub
		new Thread(){
			public void run() {
				try {
					String newGameStr = Constants.getJsonString(Constants.RANKNEWEST+1);
				
				NewestJsonParse jsonParse = new NewestJsonParse();
				gameList = (ArrayList<Rankbean>) jsonParse.getNewestbeans(newGameStr);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Message message = new Message(); 
				message.what = 3;
				message.obj = gameList;
				handler.sendMessage(message);
			};
		}.start();
		
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.newest_rank:
			
			new Thread(){
				public void run() {
					try {
						String newGameStr = Constants.getJsonString(Constants.RANKNEWEST+1);
					
					NewestJsonParse jsonParse = new NewestJsonParse();
					gameList = (ArrayList<Rankbean>) jsonParse.getNewestbeans(newGameStr);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					Message message = new Message(); 
					message.what = Constants.NEWESTWHAT;
					message.obj = gameList;
					handler.sendMessage(message);
				};
			}.start();
			
			
			
			break;

		case R.id.hotest_rank:
			
			 
			new Thread(){
				public void run() {
					try {
						String hotGameStr = Constants.getJsonString(Constants.RANKHOTEST);
				
					NewestJsonParse jsonParseHot = new NewestJsonParse();
					gameList = (ArrayList<Rankbean>) jsonParseHot.getNewestbeans(hotGameStr);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Message messageHot = new Message(); 
					messageHot.what = Constants.HOTESTWHAT;
					messageHot.obj = gameList;
					handler.sendMessage(messageHot);
				};
			}.start();
			
			
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent localIntent = new Intent(this, DetailActivity.class);
		gameId = gameList.get(position-1).getId();
		
		 localIntent.putExtra("gameId", gameId);
	    localIntent.putExtra("gamename", (gameList.get(position-1)).getTitle());
	    localIntent.putExtra("gameicon", (gameList.get(position-1)).getIcon());
	    localIntent.putExtra("gamestar", (gameList.get(position-1)).getStar());
	    localIntent.putExtra("gamedaxiao", (gameList.get(position-1)).getFilesize());
	    localIntent.putExtra("gameversion", (gameList.get(position-1)).getVersion());
	    localIntent.putExtra("gamecount", (gameList.get(position-1)).getOnclick());
	    localIntent.putExtra("gameflashUrl", (gameList.get(position-1)).getFlashurl());
	   
	    startActivity(localIntent);
		
	}

	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> arg0) {
		// TODO Auto-generated method stub
		new Thread(){
			public void run() {
				try {
					String newGameStr = Constants.getJsonString(Constants.RANKNEWEST+1);
				
				NewestJsonParse jsonParse = new NewestJsonParse();
				gameList = (ArrayList<Rankbean>) jsonParse.getNewestbeans(newGameStr);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Message message = new Message(); 
				message.what = Constants.RANKDOWNWHAT;
				message.obj = gameList;
				handler.sendMessage(message);
			};
		}.start();
	}

	//上拉加载
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> arg0) {
		// TODO Auto-generated method stub
		page++;
		new Thread(){
			public void run() {
				try {
					String newGameStr = Constants.getJsonString(Constants.RANKNEWEST+page);
				
				NewestJsonParse jsonParse = new NewestJsonParse();
				gameLists = (ArrayList<Rankbean>) jsonParse.getNewestbeans(newGameStr);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Message message = new Message(); 
				message.what = Constants.RANKUPWHAT;
				message.obj = gameLists;
				handler.sendMessage(message);
			};
		}.start();
	}
	
	
	
}

