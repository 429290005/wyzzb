package com.song.zzb.wyzzb.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.util.Constant;

import java.io.File;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;


/**
 * 引导页
 * 
 * @ClassName: SplashActivity
 * @Description: TODO
 * @author smile
 * @date 2014-6-4 上午9:45:43
 */
public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);
 		long target_size = new File(Environment.getExternalStorageDirectory(), "转本帮.apk").length();
//
////        long target_size = new File("sdcard/转本帮.apk").length();
	Log.i("target_size", "" + target_size);
     Log.i("target_size", "" + Environment.getExternalStorageDirectory());
		Bmob.initialize(this, Constant.BMOB_APP_ID);
//		BmobUpdateAgent.update(this);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
					Intent intent = new Intent();
					intent.setClass(SplashActivity.this, MainActivity.class);
					startActivity(intent);
				    finish();
			}
		}, 1000);
	}

}
