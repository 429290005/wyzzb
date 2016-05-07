package com.song.zzb.wyzzb.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.webkit.WebView;

import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.util.Constant;

import cn.bmob.v3.Bmob;

public class WebViewActivity extends FragmentActivity {
	private WebView myWebView;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);
		Bmob.initialize(this, Constant.BMOB_APP_ID);
//		BmobUpdateAgent.update(this);

		myWebView = (WebView) findViewById(R.id.myWebView);
		myWebView.getSettings().setJavaScriptEnabled(true);
		// 与js交互，JavaScriptinterface 是个接口，与js交互时用到的，这个接口实现了从网页跳到app中的activity 的方法，特别重要
		myWebView.addJavascriptInterface(new JavaScriptinterface(this), "android");
		myWebView.loadUrl("file:///android_asset/index.html");
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent();
				intent.setClass(WebViewActivity.this, MainActivity.class);
				startActivity(intent);
				finish();
			}
		}, 3000);

	}
}