package com.anjoyo.gamecenter.tuijian;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.anjoyo.gamecenter.utils.Constants;


public class JsonParse {

	public List<TuijianBean> getTuijianbeans(String url) throws Exception{
		List<TuijianBean> choiceBeans = new ArrayList<TuijianBean>();
		String jsonString = Constants.getJsonString(url);
		System.out.println("---jsonstr---"+jsonString);
		System.out.println("22222222");
		try {
			JSONObject info = new JSONObject(jsonString);
			
			JSONArray tuijianArray = info.getJSONArray("items");
			int num = tuijianArray.length();
			for (int i = 0; i < num; i++) {
				JSONObject tuijianinfo = tuijianArray.getJSONObject(i);
				TuijianBean bean = new TuijianBean();
				bean.setId(tuijianinfo.getInt("id"));
				bean.setStar(tuijianinfo.getInt("star"));
				bean.setIcon(tuijianinfo.getString("icon"));
				bean.setTitle(tuijianinfo.getString("title"));
				bean.setFilesize(tuijianinfo.getString("filesize"));
				bean.setOnclick(tuijianinfo.getInt("onclick"));
				bean.setVersion(tuijianinfo.getString("version"));
				bean.setFlashurl(tuijianinfo.getString("flashurl"));
				choiceBeans.add(bean);
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return choiceBeans;
	} 
}
