package com.song.zzb.wyzzb.util;

import android.app.Activity;
import android.app.Application;
import android.graphics.Bitmap;


import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.bean.User;

import java.io.File;

import cn.bmob.v3.BmobUser;

public class MyApplication extends Application {

	public static String TAG;
	
	private static MyApplication myApplication = null;
	

	
	public static MyApplication getInstance(){
		return myApplication;
	}
	public User getCurrentUser() {
		User user = BmobUser.getCurrentUser(myApplication, User.class);
		if(user!=null){
			return user;
		}
		return null;
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		TAG = this.getClass().getSimpleName();
		//由于Application类本身已经单例，所以直接按以下处理即可。
		myApplication = this;
		initImageLoader();
	}
	
	/**
	 * 初始化imageLoader
	 */
	public void initImageLoader(){
		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
										.memoryCache(new LruMemoryCache(5*1024*1024))
										.memoryCacheSize(10*1024*1024)
				                        .diskCache(new UnlimitedDiskCache(cacheDir))
										.diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
										.build();
		ImageLoader.getInstance().init(config);
	}
	
	public DisplayImageOptions getOptions(int drawableId){
		return new DisplayImageOptions.Builder()
				.showImageOnLoading(drawableId)
				.showImageForEmptyUri(drawableId)
				.showImageOnFail(drawableId)
				.resetViewBeforeLoading(true)
				.cacheInMemory(true)
				.cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();

	}
}
