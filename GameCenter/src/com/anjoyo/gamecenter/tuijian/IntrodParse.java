package com.anjoyo.gamecenter.tuijian;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.anjoyo.gamecenter.utils.Constants;

public class IntrodParse {

	public TuijianBean getTuijianbeans(String url) throws Exception{
		//List<TuijianBean> choiceBeans = new ArrayList<TuijianBean>();
		String jsonString = Constants.getJsonString(url);
		System.out.println(jsonString+"-------string-===intr-----");
		List<String> list = null;
		MorePic morePic = null;
		TuijianBean bean = null;
		try {
			JSONObject tuijianinfo = new JSONObject(jsonString);
			bean = new TuijianBean();
			
			
				bean.setId(tuijianinfo.getInt("id"));
				bean.setStar(tuijianinfo.getInt("star"));
				bean.setIcon(tuijianinfo.getString("icon"));
				bean.setTitle(tuijianinfo.getString("title"));
				bean.setFilesize(tuijianinfo.getString("filesize"));
				bean.setOnclick(tuijianinfo.getInt("onclick"));
				bean.setVersion(tuijianinfo.getString("version"));
				bean.setVersionname(tuijianinfo.getString("versionname"));
				bean.setFlashurl(tuijianinfo.getString("flashurl"));
				bean.setNewsTime(tuijianinfo.getString("newstime"));
				bean.setClassName(tuijianinfo.getString("classname"));
				bean.setFlashsay(tuijianinfo.getString("flashsay"));
				bean.setClassId(tuijianinfo.getInt("classid"));
				JSONArray arrayPic = tuijianinfo.getJSONArray("morepic");
				morePic = new MorePic();
				int array = arrayPic.length();
				list = new ArrayList<String>();
				for (int j = 0; j < array; j++) {
					
					JSONObject objectPic = (JSONObject) arrayPic.get(j);
					String pic = objectPic.getString("pic");
					list.add(pic);
					
				}
				morePic.setPic(list);
				bean.setMorePic(morePic);
				
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bean;
	} 
	
}
