package com.example.sina.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.sina.instance.Statuse;
import com.example.sina.instance.User;

public class JsonParse {
	public static List<Statuse> parseStatuses(String j) {
		List<Statuse> list=new ArrayList<Statuse>();
		JSONObject json;
		try {
			json = new JSONObject(j);
			JSONArray jArr = json.getJSONArray("statuses");  
			for (int i = 0; i < jArr.length(); i++) {
				JSONObject statusesObj = jArr.getJSONObject(i); 
				Statuse stu=parseStause(statusesObj);
				list.add(stu);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}
	
	public static Statuse parseStause(JSONObject stuobj) throws JSONException{
		Statuse sta=new Statuse();
		sta.setText(stuobj.getString("text"));
		//sta.setAttitudes_count(stuobj.getInt("attitudes_count"));
		//sta.setComments_count(stuobj.getInt("comments_count"));
		sta.setCreated_at(stuobj.getString("created_at"));
		sta.setId(stuobj.getLong("id"));
		if (stuobj.has("thumbnail_pic")) {
			sta.setOriginal_pic(stuobj.getString("original_pic"));
			sta.setBmiddle_pic(stuobj.getString("bmiddle_pic"));
			sta.setThumbnail_pic(stuobj.getString("thumbnail_pic"));
		}
		List<String> pics=new ArrayList<String>();
		JSONArray ja=stuobj.getJSONArray("pic_urls");
		for (int i = 0; i < ja.length(); i++) {
			pics.add(ja.getJSONObject(i).getString("thumbnail_pic"));
		}
		sta.setPics(pics);
		sta.setSource(stuobj.getString("source"));
		sta.setReposts_count(stuobj.getInt("reposts_count"));
		if(stuobj.has("user")){
			sta.setUser(parseUser(stuobj.getJSONObject("user")));
		}
		// 如果该微博是转发微博
		if (stuobj.has("retweeted_status")) {
			JSONObject jsonObject = stuobj.getJSONObject("retweeted_status");
			sta.setRetweeted_status(parseStause(jsonObject));
		}
		
		return sta;
		
	}
	public static User parseUser(JSONObject jsonobj) throws JSONException{
		User user= new User();
		user.setId(jsonobj.getLong("id"));
		user.setScreen_name(jsonobj.getString("screen_name"));
		user.setProfile_image_url(jsonobj.getString("profile_image_url"));
		user.setAvatar_large(jsonobj.getString("avatar_large"));
		user.setDescription(jsonobj.getString("description"));
		user.setFavourites_count(jsonobj.getInt("favourites_count"));
		user.setFollowers_count(jsonobj.getInt("followers_count"));
		user.setFriends_count(jsonobj.getInt("friends_count"));
		user.setGender(jsonobj.getString("gender"));// m f
		user.setStatuses_count(jsonobj.getInt("statuses_count"));
		user.setVerified_reason(jsonobj.getString("verified_reason"));
		user.setVerified(jsonobj.getBoolean("verified"));
//		if(jsonobj.has("status")){
//		  JSONObject object=jsonobj.getJSONObject("status");
//		  if(object.has("annotations")){
//			  JSONObject anobj=object.getJSONArray("annotations").getJSONObject(0);
//			  if(anobj.has("place")&&anobj.getJSONObject("place")!=null){
//				  JSONObject placeobj=anobj.getJSONObject("place");
//				  Statuse statuse=pareseStatuse(object);
//				 
//				  Place place=new Place();
//				  place.setLat(placeobj.getString("lat"));
//				  place.setLon(placeobj.getString("lon"));
//				  place.setPoiid(placeobj.getString("poiid"));
//				  place.setTitile(placeobj.getString("title"));
//				  System.out.println(place.toString());
//				  statuses.setPlace(place);
//				  user.setStatuses(statuses);
//			  }
//		  }
//		
//		}
		return user;
	}
}
