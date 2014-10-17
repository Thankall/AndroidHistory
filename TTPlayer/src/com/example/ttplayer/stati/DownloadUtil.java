package com.example.ttplayer.stati;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpConnection;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.MediaScannerConnectionClient;
import android.net.Uri;
import android.util.Log;

public class DownloadUtil {
	
	static MediaScannerConnection mescan;
	
	public static void downLoad(final Context context,final String url) {
		 
		new Thread() {
			public void run() {
				byte[] data = new byte[1024];
				int i;
				URL u;
				InputStream in = null;
				try {
					u = new URL("http://111.207.25.188:8080/MusicInfo"+url);
					HttpURLConnection conn = (HttpURLConnection) u
							.openConnection();
					conn.connect();
					in = conn.getInputStream();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				final String path="/mnt/sdcard/ttplayer" + url;
				File file = new File(path);
			
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				try {
					OutputStream out = new FileOutputStream(file);

					while ((i = in.read(data)) != -1) {
						out.write(data, 0, i);
					}
					
					out.close();
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mescan=new MediaScannerConnection(context, new MediaScannerConnectionClient() {
					
					@Override
					public void onScanCompleted(String path, Uri uri) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onMediaScannerConnected() {
						// TODO Auto-generated method stub
						mescan.scanFile(path, "audio/mp3");
					}
				});
				mescan.connect();
				Intent intent=new Intent("com.download");
				context.sendBroadcast(intent);
			};
		}.start();
	}
	 
}
