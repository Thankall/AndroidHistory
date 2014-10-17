package com.anjoyo.gamecenter.tuijian;

import java.util.ArrayList;
import java.util.List;



import com.anjoyo.gamecenter.R;
import com.anjoyo.gamecenter.detail.DetailActivity;
import com.anjoyo.gamecenter.tuijian.tuijianActivity.AsyncImageLoader;
import com.anjoyo.gamecenter.tuijian.tuijianActivity.AsyncImageLoader.ImageCallback;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ViewpagerAdapter extends PagerAdapter {

	private List<View> mList;
	private ImageView image;
	ArrayList<AdBean> listAd;
	Context context;

	private AsyncImageLoader asyncImageLoader;

	public ViewpagerAdapter(List<View> list,Context context,ArrayList<AdBean> listAd) {
		mList = list;
		this.context = context;
		this.listAd = listAd;
		asyncImageLoader = new AsyncImageLoader();
	}

	/**
	 * Return the number of views available.
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList.size();
	}

	/**
	 * Remove a page for the given position. 滑动过后就销毁 ，销毁当前页的前一个的前一个的页！
	 * instantiateItem(View container, int position) This method was deprecated
	 * in API level . Use instantiateItem(ViewGroup, int)
	 */
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		container.removeView(mList.get(position));

	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == arg1;
	}

	/**
	 * Create the page for the given position.
	 */
	@SuppressLint("NewApi")
	@Override
	public Object instantiateItem(final ViewGroup container, final int position) {

		Drawable cachedImage = asyncImageLoader.loadDrawable("http://www.gamept.cn"+listAd.get(position).getTitlePic(),
				new ImageCallback() {

					@SuppressLint("NewApi")
					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {

						View view = mList.get(position);
						image = ((ImageView) view.findViewById(R.id.image));
						image.setBackground(imageDrawable);
						container.removeView(mList.get(position));
						container.addView(mList.get(position));
						// adapter.notifyDataSetChanged();

					}
				});

		View view = mList.get(position);
		image = ((ImageView) view.findViewById(R.id.image));
		image.setBackground(cachedImage);

		container.removeView(mList.get(position));
		container.addView(mList.get(position));
		// adapter.notifyDataSetChanged();

		mList.get(position).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,DetailActivity.class);
				intent.putExtra("gameId", listAd.get(position).getGameId());
				context.startActivity(intent);
			}
		});
		
		return mList.get(position);

	}

}

