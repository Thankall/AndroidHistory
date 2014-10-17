package com.anjoyo.gamecenter.tuijian;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.detail.DetailActivity;
import com.anjoyo.gamecenter.utils.Constants;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

public class tuijianActivity extends Activity implements OnItemClickListener,OnRefreshListener2<ListView> {

	private ViewPager view_pager;
	List<View> list;
	private LayoutInflater inflater;
	ArrayList<TuijianBean> listTuijian;
	//�������ؼ���
	ArrayList<TuijianBean> listTuijianUp;
	 // ��漯��
	ArrayList<AdBean> listAd;
	
	private ImageView image;
	private View item;
	private ViewpagerAdapter adapterView;
	private ImageView[] indicator_imgs = new ImageView[4];// �����ͼƬ����
	int page = 1;
	int gameId;
	ImageView imageView;
	PullToRefreshListView listView;
	TuijianAdapter adapter;
	
	
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case Constants.TUIJIANWHAT:
				
				adapter = new TuijianAdapter(
						tuijianActivity.this, listTuijian);
				listView.setAdapter(adapter);
				
				break;
			case 5:
				listAd = (ArrayList<AdBean>) msg.obj;
				initIndicator();
				/**
				 * �������item ��ÿһ��viewPager����һ��item�� �ӷ�������ȡ����ݣ������±��⡢url��ַ�� ��������������
				 */
				for (int i = 0; i < listAd.size(); i++) {
					item = inflater.inflate(R.layout.viewpager_item, null);

					list.add(item);
				}
				
				// ������������ ����װ���������ݽ�ȥ
				adapterView = new ViewpagerAdapter(list, tuijianActivity.this, listAd);
				view_pager.setAdapter(adapterView);
				break;
			case 53:
				int totalcount = list.size();// viewPager.getChildCount();
				int currentItem = view_pager.getCurrentItem();

				int toItem = currentItem + 1 == totalcount ? 0
						: currentItem + 1;

				view_pager.setCurrentItem(toItem, true);
				// ÿ�����ӷ���һ��message�������л�viewPager�е�ͼƬ
				this.sendEmptyMessageDelayed(53, 3000);
				break;
				//����ˢ��
			case Constants.TUIJIANDOWNWHAT:
				adapter = new TuijianAdapter(
						tuijianActivity.this, listTuijian);
				listView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
				listView.onRefreshComplete();
				break;
				//��������
			case Constants.TUIJIANUPWHAT:
				adapter.getDataList().addAll(listTuijianUp);
				adapter.notifyDataSetChanged();
				listView.onRefreshComplete();
				
				break;
//			case 55:
//				
//				break;
				
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tuijian);
		getJson();
		
		getAdJson();
		initView();

		// �󶨶������������緭ҳ�Ķ���
		view_pager.setOnPageChangeListener(new MyListener());

	

		listView.setOnItemClickListener(this);
		
		listView.setOnRefreshListener(this);
	}

	private void initView() {

		view_pager = (ViewPager) this.findViewById(R.id.view_pager);
		listView = (PullToRefreshListView) this.findViewById(R.id.listviewTuijian);
		list = new ArrayList<View>();
		inflater = LayoutInflater.from(this);
		

		listTuijian = new ArrayList<TuijianBean>();
	}

	private void initIndicator() {

		
		
		ImageView imgView;
		View v = findViewById(R.id.indicator);// ����ˮƽ���֣�����̬����ͼ��
		for (int i = 0; i < listAd.size(); i++) {
			imgView = new ImageView(this);
			LinearLayout.LayoutParams params_linear = new LinearLayout.LayoutParams(
					10, 10);
			params_linear.setMargins(7, 10, 7, 10);
			imgView.setLayoutParams(params_linear);
			indicator_imgs[i] = imgView;

			if (i == 0) { // ��ʼ����һ��Ϊѡ��״̬

				indicator_imgs[i].setBackgroundResource(R.drawable.chosedpoint);
			} else {
				indicator_imgs[i].setBackgroundResource(R.drawable.point);
			}
			((ViewGroup) v).addView(indicator_imgs[i]);
		}

	}

	private void getJson() {

		new Thread() {
			public void run() {
				try {
					JsonParse parse = new JsonParse();
					
					listTuijian = (ArrayList<TuijianBean>) parse
							.getTuijianbeans(Constants.TUIJIANURL+1);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message message = handler.obtainMessage();
				message.what = Constants.TUIJIANWHAT;
				message.obj = listTuijian;
				
				handler.sendMessage(message);
			};
		}.start();

	}
	

	
	private void getAdJson() {

		new Thread() {
			public void run() {
				try {
					listAd=new ArrayList<AdBean>();
					AdParse adParse = new AdParse();
					listAd = (ArrayList<AdBean>) adParse
							.getAdbeans(Constants.ADURL);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message message = handler.obtainMessage();
				message.what = 5;
				
				message.obj = listAd;
				handler.sendMessage(message);
			};
		}.start();

	}

	class MyListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			if (state == 0) {
				// new MyAdapter(null).notifyDataSetChanged();
			}
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPageSelected(int position) {

			// �ı����е����ı���ͼƬΪ��δѡ��
			for (int i = 0; i < indicator_imgs.length; i++) {

				indicator_imgs[i].setBackgroundResource(R.drawable.point);

			}

			// �ı䵱ǰ����ͼƬΪ��ѡ��
			indicator_imgs[position]
					.setBackgroundResource(R.drawable.chosedpoint);
		}

	}

	/**
	 * �첽����ͼƬ
	 */
	static class AsyncImageLoader {

		// �����ã�ʹ���ڴ�����ʱ���� �������˳������ڴ治������������ã�
		private HashMap<String, SoftReference<Drawable>> imageCache;

		public AsyncImageLoader() {
			imageCache = new HashMap<String, SoftReference<Drawable>>();
		}

		/**
		 * ����ص��ӿ�
		 */
		public interface ImageCallback {
			public void imageLoaded(Drawable imageDrawable, String imageUrl);
		}

		/**
		 * �������̼߳���ͼƬ ���̼߳�����ͼƬ����handler���?���̲߳��ܸ���ui����handler�������̣߳����Ը���ui��
		 * handler�ֽ���imageCallback��imageCallback��Ҫ�Լ���ʵ�֣���������ԶԻص�������д���
		 * 
		 * @param imageUrl
		 *            ����Ҫ���ص�ͼƬurl
		 * @param imageCallback
		 *            ��
		 * @return
		 */
		public Drawable loadDrawable(final String imageUrl,
				final ImageCallback imageCallback) {

			// �����д���ͼƬ ��������ʹ�û���
			if (imageCache.containsKey(imageUrl)) {
				SoftReference<Drawable> softReference = imageCache
						.get(imageUrl);
				Drawable drawable = softReference.get();
				if (drawable != null) {
					imageCallback.imageLoaded(drawable, imageUrl);// ִ�лص�
					return drawable;
				}
			}

			/**
			 * �����߳���ִ�лص���������ͼ
			 */
			final Handler handler = new Handler() {
				public void handleMessage(Message message) {
					imageCallback.imageLoaded((Drawable) message.obj, imageUrl);
				}
			};

			/**
			 * �������̷߳������粢����ͼƬ ���ѽ���handler����
			 */
			new Thread() {
				@Override
				public void run() {
					Drawable drawable = loadImageFromUrl(imageUrl);
					// �������ͼƬ�ŵ�������
					imageCache.put(imageUrl, new SoftReference<Drawable>(
							drawable));
					Message message = handler.obtainMessage(0, drawable);
					handler.sendMessage(message);
				}
			}.start();

			return null;
		}

		/**
		 * ����ͼƬ ��ע��HttpClient ��httpUrlConnection�����
		 */
		public Drawable loadImageFromUrl(String url) {

			try {
				HttpClient client = new DefaultHttpClient();
				client.getParams().setParameter(
						CoreConnectionPNames.CONNECTION_TIMEOUT, 1000 * 15);
				HttpGet get = new HttpGet(url);
				HttpResponse response;

				response = client.execute(get);
				if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
					HttpEntity entity = response.getEntity();

					Drawable d = Drawable.createFromStream(entity.getContent(),
							"src");

					return d;
				} else {
					return null;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			return null;
		}

		// ����
		public void clearCache() {

			if (this.imageCache.size() > 0) {

				this.imageCache.clear();
			}

		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// activity����4���Ӻ󣬷���һ��message��������viewPager�е�ͼƬ�л�����һ��
		handler.sendEmptyMessageDelayed(53, 3000);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		// ֹͣviewPager��ͼƬ���Զ��л�
		handler.removeMessages(53);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent localIntent = new Intent(this, DetailActivity.class);
		gameId = this.listTuijian.get(position-1).getId();
		localIntent.putExtra("gameId",gameId);
		localIntent.putExtra("gamename",
				((TuijianBean) this.listTuijian.get(position-1)).getTitle());
		localIntent.putExtra("gameicon",
				((TuijianBean) this.listTuijian.get(position-1)).getIcon());
		localIntent.putExtra("gamestar",
				((TuijianBean) this.listTuijian.get(position-1)).getStar());
		localIntent.putExtra("gamedaxiao",
				((TuijianBean) this.listTuijian.get(position-1)).getFilesize());
		localIntent.putExtra("gameversion",
				((TuijianBean) this.listTuijian.get(position-1)).getVersion());
		localIntent.putExtra("gamecount",
				((TuijianBean) this.listTuijian.get(position-1)).getOnclick());
		localIntent.putExtra("gamecount",
				((TuijianBean) this.listTuijian.get(position-1)).getOnclick());
		localIntent.putExtra("gameflashUrl",
				((TuijianBean) this.listTuijian.get(position-1)).getFlashurl());
		
		startActivity(localIntent);
	}
//����ˢ��
	@Override
	public void onPullDownToRefresh(PullToRefreshBase<ListView> arg0) {
		// TODO Auto-generated method stub
		new Thread() {
			public void run() {
				try {
					JsonParse parse = new JsonParse();
					
					listTuijian = (ArrayList<TuijianBean>) parse
							.getTuijianbeans(Constants.TUIJIANURL+1);
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message message = handler.obtainMessage();
				message.what = Constants.TUIJIANDOWNWHAT;
				message.obj = listTuijian;
				
				handler.sendMessage(message);
			};
		}.start();
	}
//��������
	@Override
	public void onPullUpToRefresh(PullToRefreshBase<ListView> arg0) {
		// TODO Auto-generated method stub
		page++;
		new Thread() {
			public void run() {
				try {
					JsonParse parse = new JsonParse();
					
					listTuijianUp = (ArrayList<TuijianBean>) parse
							.getTuijianbeans(Constants.TUIJIANURL+page);
					System.out.println(listTuijianUp.size()+"----------upsize------");
					Log.i("tag", listTuijianUp.size()+"----------upsize------");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Message message = handler.obtainMessage();
				message.what = Constants.TUIJIANUPWHAT;
				
				handler.sendMessage(message);
			};
		}.start();
	}
}
