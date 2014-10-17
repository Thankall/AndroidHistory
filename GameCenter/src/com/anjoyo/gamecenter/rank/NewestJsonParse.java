package com.anjoyo.gamecenter.rank;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.anjoyo.gamecenter.utils.Constants;

public class NewestJsonParse {

	public List<Rankbean> getNewestbeans(String url) throws Exception{
		List<Rankbean> newestBeans = new ArrayList<Rankbean>();
		//String jsonString = Constants.getJsonString(url);
		try {
			JSONObject info = new JSONObject(url);
			
			JSONArray newestArray = info.getJSONArray("items");
			int num = newestArray.length();
			for (int i = 0; i < num; i++) {
				JSONObject newestinfo = newestArray.getJSONObject(i);
				Rankbean bean = new Rankbean();
				bean.setId(newestinfo.getInt("id"));
				bean.setIcon(newestinfo.getString("icon"));
				bean.setTitle(newestinfo.getString("title"));
				bean.setFilesize(newestinfo.getString("filesize"));
				bean.setInfopfen(newestinfo.getInt("infopfen"));
				bean.setInfopfennum(newestinfo.getInt("infopfennum"));
				bean.setOnclick(newestinfo.getInt("onclick"));
				bean.setVersion(newestinfo.getString("version"));
				bean.setStar(newestinfo.getInt("star"));
				bean.setFlashurl(newestinfo.getString("flashurl"));
				newestBeans.add(bean);
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newestBeans;
	} 
	
}
