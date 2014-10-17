package com.anjoyo.gamecenter.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Constants {

	public static final String ADURL = "http://www.gamept.cn/yx_ad.php";
	  public static final String DETAILURL = "http://www.gamept.cn/yx_lanmu_recommend.php?uid=163&id=";
	  public static final String INTRODUCEURL = "http://www.gamept.cn/detail.php?uid=163&id=";
	  public static final int DETAILWHAT = 20;
	  public static final int HOTESTWHAT = 2;
	  public static final String IMAGEDIR_NAME = "court";
	  public static final int INTRODUCEWHAT = 21;
	  public static final int NEWESTWHAT = 1;
	  public static final String RANKHOTEST = "http://www.gamept.cn/yx_hot.php?pageSize=21&currentPage=1";
	  public static final String RANKNEWEST = "http://www.gamept.cn/yx_new.php?pageSize=21&currentPage=";
	  public static final int TUIJIANUPWHAT = 11;
	  public static final int TUIJIANDOWNWHAT = 13;
	  public static final String TUIJIANURL = "http://www.gamept.cn/yx_recommend.php?currentPage=";
	  public static final int TUIJIANVIEWPAGERWHAT = 12;
	  public static final int TUIJIANWHAT = 10;
	  public static final int WHAT = 3;
	  public static final int RANKUPWHAT = 60;
	  public static final int RANKDOWNWHAT = 63;
	  public static final String CLASSIFICATION_URL="http://www.gamept.cn/yx_category_count.php";
	   public static final String CLASSIFICATION_HOT_URL="http://www.gamept.cn/yx_lanmu_hot.php?id=";
	   public static final String CLASSIFICATION_NEW_URL="http://www.gamept.cn/yx_lanmu_new.php?id=";
	  
	//�������磬�õ���������ַ�
	public static String getJsonString(String url) throws Exception{
		URL getUrl = new URL(url);

		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		connection.setConnectTimeout(3000);
		connection.setReadTimeout(3000);
		connection.connect();
		int code = connection.getResponseCode();
		StringBuffer result = new StringBuffer();
		if (code == 200) {
			BufferedReader bufferedReader = new BufferedReader(
					new InputStreamReader(connection.getInputStream(),
							"utf-8"));
			String lines;
			while ((lines = bufferedReader.readLine()) != null) {
				result.append(lines);
			}
			bufferedReader.close();
			connection.disconnect();
		} else {
			connection.disconnect();
		}
	
		
	return result.toString();
	}
}
