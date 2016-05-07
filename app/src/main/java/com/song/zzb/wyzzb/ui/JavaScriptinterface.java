package com.song.zzb.wyzzb.ui;

import android.app.Activity;
import android.content.Intent;

public class JavaScriptinterface {

	Activity mActivity;

	public JavaScriptinterface(Activity mActivity) {
		this.mActivity = mActivity;
	}

	/** 与js交互时用到的方法，在js里直接调用的 */
	public void startActivity() {
		Intent intent = new Intent();
		intent.setClass(mActivity, MainActivity.class);

		mActivity.startActivity(intent);
		mActivity.finish();
	}
}
