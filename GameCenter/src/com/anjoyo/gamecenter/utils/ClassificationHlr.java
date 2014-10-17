package com.anjoyo.gamecenter.utils;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.anjoyo.gamecenter.bean.AnjoyoClassificationBean;

import android.R.integer;
import android.content.Intent;
import android.util.Log;


public class ClassificationHlr {//�������
	ArrayList<AnjoyoClassificationBean> anjoyoClassificationBeans;
	Httpget httpget;
	String sb;
	
	
	
	public  static ArrayList<AnjoyoClassificationBean> getinfo() throws IllegalStateException, IOException, JSONException{
		Intent intent=new Intent();
		Httpget httpget = new Httpget();
		String sb=httpget.getData(Constants.CLASSIFICATION_URL);
		
		
		JSONObject jsonObject=null;
		ArrayList<AnjoyoClassificationBean> anjoyoClassificationBeans=new ArrayList<AnjoyoClassificationBean>();
		jsonObject =new JSONObject(sb);
		JSONArray jsonArray=new JSONArray();
		
		jsonArray=jsonObject.getJSONArray("items");
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject2=jsonArray.getJSONObject(i);
			anjoyoClassificationBeans.add(anjoyoClassificationBean(jsonObject2));
			
		}
				
				
		return anjoyoClassificationBeans;
		
	}
	public static AnjoyoClassificationBean anjoyoClassificationBean(JSONObject anjoyoClassificationBeans) throws NumberFormatException, JSONException{
		AnjoyoClassificationBean anjoyoClassification=new AnjoyoClassificationBean();
		anjoyoClassification.setTotal(Integer.parseInt(anjoyoClassificationBeans.getString("total")));
		anjoyoClassification.setClassid(Integer.parseInt(anjoyoClassificationBeans.getString("classid")));
		anjoyoClassification.setClassname(anjoyoClassificationBeans.getString("classname"));
		anjoyoClassification.setClassimg(anjoyoClassificationBeans.getString("classimg"));
		anjoyoClassification.setPicMd5(anjoyoClassificationBeans.getString("picMd5"));
		
		return anjoyoClassification;
		
	}
	
	
}
