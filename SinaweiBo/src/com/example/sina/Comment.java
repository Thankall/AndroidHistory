package com.example.sina;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.example.sina.util.AccessTokenKeeper;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Comment extends Activity implements OnClickListener {
	private String from;
	private TextView title;
	private Button send;
	private Button quxiao;
	private EditText comment;
	NotificationManager manager;
	Notification notification;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comment_layout);
		init();
		from = getIntent().getStringExtra("from");
		if ("comment".equals(from)) {
			title.setText("发评论");

		}
		if ("recount".equals(from)) {
			title.setText("转发微博");
		}
		send.setOnClickListener(this);
		quxiao.setOnClickListener(this);

	}

	private void init() {
		title = (TextView) findViewById(R.id.com_title);
		send = (Button) findViewById(R.id.but_send);
		quxiao = (Button) findViewById(R.id.but_quxiao);
		comment = (EditText) findViewById(R.id.etweibocontent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.but_send:
			final String text = comment.getText().toString();
			manager = (NotificationManager) Comment.this
					.getSystemService(Context.NOTIFICATION_SERVICE);
			notification = new Notification();
			//notification.icon = R.drawable.app_icon;
			notification.tickerText = "评论发送中...";
			//notification.flags = Notification.FLAG_NO_CLEAR;
			manager.notify(1, notification);
			new Thread() {
				public void run() {
					try {
						String t = URLEncoder.encode(text, "UTF-8");// 中文数据需要经过URL编码
						String id = getIntent().getStringExtra("id");
						String token = AccessTokenKeeper.readAccessToken(
								Comment.this).getToken();
						// String params = "comment=" + t +"&id="+id+
						// "&access_token=" + token;
						// byte[] data = params.getBytes();
						//
						// URL url = new
						// URL("https://api.weibo.com/2/comments/create.json");
						// HttpURLConnection conn = (HttpURLConnection)
						// url.openConnection();
						// conn.setConnectTimeout(3000);
						// //这是请求方式为POST
						// conn.setRequestMethod("POST");
						// //设置post请求必要的请求头
						// conn.setRequestProperty("Content-Type",
						// "application/x-www-form-urlencoded");// 请求头, 必须设置
						// conn.setRequestProperty("Content-Length", data.length
						// + "");// 注意是字节长度, 不是字符长度
						//
						// conn.setDoOutput(true);// 准备写出
						// conn.getOutputStream().write(data);// 写出数据

						// 第一步，创建HttpPost对象
						HttpPost httpPost = new HttpPost(
								"https://api.weibo.com/2/comments/create.json");

						// 设置HTTP POST请求参数必须用NameValuePair对象
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("comment", text));
						params.add(new BasicNameValuePair("id", id));
						params.add(new BasicNameValuePair("access_token", token));

						HttpResponse httpResponse = null;

						// 设置httpPost请求参数
						httpPost.setEntity(new UrlEncodedFormEntity(params,
								HTTP.UTF_8));
						httpResponse = new DefaultHttpClient()
								.execute(httpPost);
						// System.out.println(httpResponse.getStatusLine().getStatusCode());
						if (httpResponse.getStatusLine().getStatusCode() == 200) {
							
							// 第三步，使用getEntity方法活得返回结果
							// String result =
							// EntityUtils.toString(httpResponse.getEntity());
							// System.out.println("result:" + result);
							// Log.i("msg", result+"sss");

						}

						// Log.i("msg", conn.getResponseCode()+"sss");
					} catch (Exception e) {
						// TODO: handle exception
					}

				};
			}.start();
			break;
		case R.id.but_quxiao:
			this.finish();
			break;

		default:
			break;
		}
	}
}
