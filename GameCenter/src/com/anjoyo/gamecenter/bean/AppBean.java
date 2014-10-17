package com.anjoyo.gamecenter.bean;

import com.anjoyo.gamecenter.utils.DownloadApp;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class AppBean {
	private String name;
	private ImageView pic;
	private String url;
	private String size;
	private DownloadApp thread;
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AppBean other = (AppBean) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (size == null) {
			if (other.size != null)
				return false;
		} else if (!size.equals(other.size))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	public DownloadApp getThread() {
		return thread;
	}
	public void setThread(DownloadApp thread) {
		this.thread = thread;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ImageView getPic() {
		return pic;
	}
	public void setPic(ImageView pic) {
		this.pic = pic;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
}
