package com.example.sina;

import java.text.SimpleDateFormat;

import com.example.sina.appstatic.AppFinal;
import com.example.sina.util.AccessTokenKeeper;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MoreAct extends Activity {
	private TextView txtv;
	private Button btn;
	private WeiboAuth mWeiboAuth;// 创建微博实例
	private Oauth2AccessToken mAccessToken;
	private SsoHandler mSsoHandler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more);
		init();
		mWeiboAuth = new WeiboAuth(MoreAct.this, AppFinal.APP_KEY,
				AppFinal.REDIRECT_URL, AppFinal.SCOPE);
		btn.setOnClickListener(new Button.OnClickListener() {

			@Override
			public void onClick(View v) {
				mSsoHandler = new SsoHandler(MoreAct.this, mWeiboAuth);
                mSsoHandler.authorize(new AuthListenner());
			}
		});
	}
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResult
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
    }
	private void init() {
		txtv = (TextView) findViewById(R.id.txtv_token);
		btn = (Button) findViewById(R.id.but);
	}

	class AuthListenner implements WeiboAuthListener {

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onComplete(Bundle arg0) {
			// 从Bundle中解析出token
			mAccessToken = Oauth2AccessToken.parseAccessToken(arg0);
			if (mAccessToken.isSessionValid()) {
				AccessTokenKeeper.writeAccessToken(MoreAct.this,
						mAccessToken);
				updateView(false);
			} else {
				Toast.makeText(MoreAct.this, "授权失败", 1000).show();
			}
		}

		@Override
		public void onWeiboException(WeiboException arg0) {
			// TODO Auto-generated method stub

		}

		private void updateView(boolean hasExisted) {
			String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
					.format(new java.util.Date(mAccessToken.getExpiresTime()));
			String format = getString(R.string.weibosdk_demo_token_to_string_format_1);

			String message = String.format(format, mAccessToken.getToken(),
					date);
			if (hasExisted) {
				message = getString(R.string.weibosdk_demo_token_has_existed)
						+ "\n" + message;
			}
			 txtv.setText(message);
		}

	}
}
