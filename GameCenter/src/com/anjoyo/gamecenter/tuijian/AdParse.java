package com.anjoyo.gamecenter.tuijian;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.anjoyo.gamecenter.utils.Constants;

/**
 * 广告接口的解析
 * @author lixuebin
 *
 */
public class AdParse {

	public List<AdBean> getAdbeans(String url) throws Exception{
		List<AdBean> adBeans = new ArrayList<AdBean>();
		String jsonString = Constants.getJsonString(url);
		System.out.println("@@@@@@@@@@@@"+jsonString);
		try {
			JSONObject info = new JSONObject(jsonString);
			
			JSONArray adArray = info.getJSONArray("items");
			int num = adArray.length();
			for (int i = 0; i < num; i++) {
				JSONObject adinfo = adArray.getJSONObject(i);
				AdBean bean = new AdBean();
				bean.setId(adinfo.getInt("id"));
				bean.setTitlePic(adinfo.getString("titlepic"));
				bean.setGameId(adinfo.getInt("gameid"));
				adBeans.add(bean);
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adBeans;
	} 
}
