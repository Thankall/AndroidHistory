package com.anjoyo.gamecenter.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Intent;

public class Httpget {
	public String sb;
	public int a;
	String url;
	
	public Httpget() {
		this.sb = sb;
	}

	public String getData(String url) throws IllegalStateException, IOException {
		
			
			
			
		
	

		
		StringBuilder sb = new StringBuilder();
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		HttpResponse httpResponse = httpClient.execute(httpGet);
		HttpEntity httpEntity = httpResponse.getEntity();
		if (httpEntity != null) {
			InputStream inputStream = httpEntity.getContent();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream));
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}

			return sb.toString();

		}
		return sb.toString();
		
		

	}
}
