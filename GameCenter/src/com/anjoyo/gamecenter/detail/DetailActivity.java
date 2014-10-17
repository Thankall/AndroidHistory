package com.anjoyo.gamecenter.detail;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.adapter.DownloadAdapter;
import com.anjoyo.gamecenter.bean.AppBean;
import com.anjoyo.gamecenter.tuijian.IntrodParse;
import com.anjoyo.gamecenter.tuijian.JsonParse;
import com.anjoyo.gamecenter.tuijian.TuijianAdapter;
import com.anjoyo.gamecenter.tuijian.TuijianBean;
import com.anjoyo.gamecenter.utils.AsyncImageLoader;
import com.anjoyo.gamecenter.utils.CheckNetWork;
import com.anjoyo.gamecenter.utils.Constants;
import com.anjoyo.gamecenter.utils.DownloadApp;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailActivity extends Activity implements OnClickListener {
	Button detailIntr;
	Button download;
	TextView gameCount;
	int gameCountDet;
	TextView gameDaxiao;
	TextView gameFenlei;
	String gameIcon;String flashUrl;
	ArrayList<TuijianBean> gameList;
	ArrayList<TuijianBean> gameListUp;
	TuijianBean introdBean;
	TextView gameName;TextView textJianjie;
	String gameNameDet,gameClass,gameUpDate;
	TextView gameNameLl;
	List<String> listPic;
	String gameSize,flashSay;
	String versionName;
	int gameStar,gameId;
	TextView gameUpdate;
	TextView gameVersion;
	String gameVersionDet;
	GridView gridDetail;
	int page = 1;int classId;
	private boolean isdownload;
	DetailAdapter localTuijianAdapter;
	ImageView imageback;
	ImageView imgIcon;
	ImageView imgShare;
	ImageView moreInfo;
	RelativeLayout relDetailIntListview;
	//PullToRefreshListView relGameListView;
	ListView relGameListView;
	Button relatGame;
	RatingBar star;
	AsyncImageLoader asy;
	
	Handler handler = new Handler() {
		public void handleMessage(Message paramMessage) {
			switch (paramMessage.what) {
			case 20:
			
				Log.i("tag", gameList.size()+"------detail--size222-------");
				localTuijianAdapter = new DetailAdapter(
						DetailActivity.this, gameList);
				
				relGameListView
						.setAdapter(localTuijianAdapter);
				
				DetailActivity.this.relatGame.setBackgroundResource(R.drawable.little_tit_click);
				DetailActivity.this.detailIntr
						.setBackgroundResource(R.drawable.little_tit_normal);
				DetailActivity.this.relatGame.setTextColor(Color
						.parseColor("#ffffff"));
				DetailActivity.this.detailIntr.setTextColor(Color
						.parseColor("#000000"));
				break;
			case 21:
			
			DetailActivity.this.detailIntr.setBackgroundResource(R.drawable.little_tit_click);
			DetailActivity.this.relatGame.setBackgroundResource(R.drawable.little_tit_normal);
			DetailActivity.this.detailIntr.setTextColor(Color
					.parseColor("#ffffff"));
			DetailActivity.this.relatGame.setTextColor(Color
					.parseColor("#000000"));
			break;
			case 22:
				
				localTuijianAdapter = new DetailAdapter(
						DetailActivity.this, gameList);
				relGameListView
						.setAdapter(localTuijianAdapter);
				//relGameListView.onRefreshComplete();
				break;
			case 23:
				localTuijianAdapter.getDataList().addAll(gameListUp);
				localTuijianAdapter.notifyDataSetChanged();
				//relGameListView.onRefreshComplete();
				break;
			case 55:
				
				introdBean = (TuijianBean) paramMessage.obj;
				gameClass = introdBean.getClassName();
				gameUpDate = introdBean.getNewsTime();
				
				gameFenlei.setText("分类 : "+gameClass);
				gameUpdate.setText("更新日期 : "+gameUpDate);
				
				gameName.setText(gameNameDet);
				gameNameLl.setText(gameNameDet);
				
				new AsyncImageLoader().dispalyImageview(gameIcon, imgIcon, R.drawable.pic_c);
				
				star.setRating(gameStar);
				gameCount.setText("下载次数 : " + gameCountDet + "次");
				gameVersion.setText("版本 : " + versionName);
				gameDaxiao.setText("大小 : " + gameSize);
				flashSay = introdBean.getFlashsay();
				textJianjie.setText(flashSay);
				//图片集合
				//listPic = (List<String>) introdBean.getMorePic();
				
				break;
		}
	};
	};
	
	
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.detailsactivity);
		
		initView();
		getIntrodData();
		//getData();
		
		this.imageback.setOnClickListener(this);
		this.imgShare.setOnClickListener(this);
		this.detailIntr.setOnClickListener(this);
		this.relatGame.setOnClickListener(this);
		this.download.setOnClickListener(this);
		this.moreInfo.setOnClickListener(this);
		//relGameListView.setOnRefreshListener(this);
		ArrayList localArrayList = getGridData();
		this.gridDetail.setAdapter(new SimpleAdapter(this, localArrayList,
				R.layout.gridview_item, new String[] { "ItemImage" },
				new int[] { R.id.imgGridView_detail }));
	}
	
	
	//获得listview的数据
	private void getData() {
		new Thread() {
			public void run() {
				try {
					JsonParse localJsonParse = new JsonParse();
					gameList = (ArrayList<TuijianBean>) localJsonParse
							.getTuijianbeans(Constants.DETAILURL+classId+"&currentPage="+page);
					System.out.println(classId+"---------cksdfldf------id-------");
					System.out.println("88888888888888888888888888888");
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
				Message localMessage = new Message();
				localMessage.what = 20;
				handler.sendMessage(localMessage);
			};
		}.start();
	}

	private void getIntrodData() {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				try {
					IntrodParse localJsonParse = new IntrodParse();
					introdBean = localJsonParse
							.getTuijianbeans(Constants.INTRODUCEURL+gameId);
					gameClass = introdBean.getClassName();
					gameUpDate = introdBean.getNewsTime();
					classId = introdBean.getClassId();
					versionName = introdBean.getVersionname();
					flashSay = introdBean.getFlashsay();
					gameNameDet = introdBean.getTitle();
					gameIcon = "http://www.gamept.cn"+introdBean.getIcon();
					gameStar = introdBean.getStar();
					gameCountDet = introdBean.getOnclick();
					gameVersionDet = introdBean.getVersion();
					gameSize = introdBean.getFilesize();
					//图片集合
					listPic = (List<String>) introdBean.getMorePic();
					System.out.println(gameClass + flashSay + gameSize + "----------");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				Message message = new Message();
				message.what = 55;
				message.obj = introdBean;
				handler.sendMessage(message);
				
			};
		}.start();
	}
	
	
	
	private ArrayList<HashMap<String, Bitmap>> getGridData() {
		ArrayList localArrayList = new ArrayList();
		HashMap localHashMap = new HashMap();
		for (int i = 0;i<3; i++) {
			
				localHashMap = new HashMap();
			localHashMap.put("ItemImage",R.drawable.pic_d);
			localArrayList.add(localHashMap);
			
		}
		return localArrayList;
	}
	
	//初始化控件
	private void initView() {
		this.imageback = ((ImageView) findViewById(R.id.imgBack_detail));
		this.gameName = ((TextView) findViewById(R.id.texGameName_detail));
		this.imgShare = ((ImageView) findViewById(R.id.imgShare_detail));
		this.imgIcon = ((ImageView) findViewById(R.id.imgGameIcon_detail));
		this.gameNameLl = ((TextView) findViewById(R.id.gamename_detail));
		this.star = ((RatingBar) findViewById(R.id.ratingbar_detail));
		this.gameDaxiao = ((TextView) findViewById(R.id.gamedaxiao_detail));
		this.gameVersion = ((TextView) findViewById(R.id.gameversion_detail));
		this.gameFenlei = ((TextView) findViewById(R.id.gamefenlei_detail));
		this.gameCount = ((TextView) findViewById(R.id.gamecount_detail));
		this.gameUpdate = ((TextView) findViewById(R.id.gameupdate_detail));
		this.detailIntr = ((Button) findViewById(R.id.detailIntr_details));
		this.relatGame = ((Button) findViewById(R.id.relatGame_details));
		this.download = ((Button) findViewById(R.id.download_detail));
		this.moreInfo = ((ImageView) findViewById(R.id.moreinfo_details));
		this.relGameListView = ((ListView) findViewById(R.id.relatGame_listview));
		this.relDetailIntListview = ((RelativeLayout) findViewById(R.id.rel_detailIntr));
		this.gridDetail = ((GridView) findViewById(R.id.grid_detail));
		textJianjie = (TextView) this.findViewById(R.id.webview_details);
		//去掉点击背景色
		gridDetail.setSelector(new ColorDrawable(Color.TRANSPARENT));
		//relGameListView.setSelector(new ColorDrawable(Color.TRANSPARENT));
		
		Intent localIntent = getIntent();
		this.gameId = localIntent.getIntExtra("gameId",0);
//		this.gameNameDet = localIntent.getStringExtra("gamename");
		this.gameIcon = "http://www.gamept.cn"+localIntent.getStringExtra("gameicon");
//		this.gameStar = localIntent.getIntExtra("gamestar", 0);
//		this.gameSize = localIntent.getStringExtra("gamedaxiao");
//		this.gameVersionDet = localIntent.getStringExtra("gameversion");
//		this.gameCountDet = localIntent.getIntExtra("gamecount", 0);
//		flashUrl = "http://www.gamept.cn"+localIntent.getStringExtra("gameflashUrl");
//		listPic = (List<String>) localIntent.getSerializableExtra("morePic");
//		flashSay = localIntent.getStringExtra("flashsay");
//		gameFenlei.setText("分类 : "+gameClass);
//		gameUpdate.setText("更新日期 : "+gameUpDate);
		
		this.gameName.setText(this.gameNameDet);
		this.gameNameLl.setText(this.gameNameDet);
		
		new AsyncImageLoader().dispalyImageview(gameIcon, imgIcon, R.drawable.pic_c);
		
		this.star.setRating(this.gameStar);
		this.gameCount.setText("下载次数 : " + this.gameCountDet + "次");
		this.gameVersion.setText("版本 : " + this.gameVersionDet);
		this.gameDaxiao.setText("大小 : " + this.gameSize);
		
		
		
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.imgShare_detail:
			
			Intent intent=new Intent(Intent.ACTION_SEND);  
			  
			intent.setType("text/plain");  
			intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
			 
			intent.putExtra(Intent.EXTRA_TEXT, flashUrl); 
			
			startActivity(Intent.createChooser(intent, getTitle()));  

			
			break;
		case R.id.imgBack_detail:
			finish();
			break;
		case R.id.detailIntr_details:
			this.relGameListView.setVisibility(View.INVISIBLE);
			this.relDetailIntListview.setVisibility(View.VISIBLE);
			
			this.handler.sendEmptyMessage(21);
			break;
		case R.id.relatGame_details:
			
		this.relGameListView.setVisibility(View.VISIBLE);
		this.relDetailIntListview.setVisibility(View.INVISIBLE);
		getData();
		
		break;
		
		case R.id.moreinfo_details:
			textJianjie.setMaxLines(15);
			textJianjie.setText(flashSay);
			
			break;
		case R.id.download_detail:
			final SharedPreferences share = this.getSharedPreferences("setting",
					Activity.MODE_PRIVATE);
			boolean ckwifi = share.getBoolean("cb_wifi", false);
			final Dialog dialog;
			if (ckwifi&&!new CheckNetWork().isWifiConnected(this)) {
				dialog=new AlertDialog.Builder(this).create();
				View dia_view=View.inflate(this, R.layout.dialog_iphone, null);
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
				app.setName(gameNameDet);
				app.setSize(gameSize);
				app.setUrl(flashUrl);
				if(DownloadAdapter.list.contains(app)){
					Toast.makeText(this, "任务已存在",Toast.LENGTH_SHORT ).show();
					return;
				}
				app.setPic(new ImageView(this));
				asy.dispalyImageview("http://www.gamept.cn"+gameIcon, app.getPic(), R.drawable.pic_c);
				DownloadApp d_app=new DownloadApp(this, app.getUrl(), app.getName());
				d_app.start();
				Toast.makeText(this, "开始下载。。。",Toast.LENGTH_SHORT ).show();
				app.setThread(d_app);
				DownloadAdapter.list.add(app);
			}
			
			break;
		}
	}

	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
//		gameFenlei.setText("分类 : "+gameClass);
//		gameUpdate.setText("更新日期 : "+gameUpDate);

	}
//	@Override
//	public void onPullDownToRefresh(PullToRefreshBase<ListView> arg0) {
//		
//		new Thread() {
//			public void run() {
//				try {
//					JsonParse localJsonParse = new JsonParse();
//					gameList = (ArrayList<TuijianBean>) localJsonParse
//							.getTuijianbeans(Constants.DETAILURL+classId+"&currentPage="+page);
//					System.out.println(classId+"------xiala-----calss------");
//					
//				} catch (Exception e) {
//					
//					e.printStackTrace();
//				}
//				Message localMessage = new Message();
//				localMessage.what = 22;
//				handler.sendMessage(localMessage);
//			};
//		}.start();
//		
//	}
//
//	@Override
//	public void onPullUpToRefresh(PullToRefreshBase<ListView> arg0) {
//		 
//		page++;
//		new Thread() {
//			public void run() {
//				try {
//					JsonParse localJsonParse = new JsonParse();
//					gameList = (ArrayList<TuijianBean>) localJsonParse
//							.getTuijianbeans(Constants.DETAILURL+classId+"&currentPage="+page);
//					
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//				Message localMessage = new Message();
//				localMessage.what = 23;
//				
//				handler.sendMessage(localMessage);
//			}
//		}.start();
//	}


	
}