package com.song.zzb.wyzzb.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;


import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.adapter.ExpandableComputerAdapter;
import com.song.zzb.wyzzb.adapter.ExpandableEnglishAdapter;
import com.song.zzb.wyzzb.adapter.ExpandableLanguageAdapter;
import com.song.zzb.wyzzb.adapter.ExpandableMathAdapter;

import com.song.zzb.wyzzb.bean.ComputerBase;
import com.song.zzb.wyzzb.bean.EnglishBase;
import com.song.zzb.wyzzb.bean.LanguageBase;
import com.song.zzb.wyzzb.bean.MathBase;
import com.song.zzb.wyzzb.bean.listContent;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

public class CourseBaseExpandableList extends BaseActivity {
	private String[] value3=new String[2];// 上个页面传过来的值
	private int[] value4=new int[3];//得到具体课程
	ExpandableMathAdapter listMathAdapter;
	ExpandableEnglishAdapter listEnglishAdapter;
	ExpandableComputerAdapter listComputerAdapter;
	ExpandableLanguageAdapter listLanguageAdapter;
	ExpandableListView expListView;
	List<MathBase> listDataHeaderMathBase = new ArrayList<MathBase>();
	List<EnglishBase> listDataHeaderEnglishBase = new ArrayList<EnglishBase>();
	List<ComputerBase> listDataHeaderComputerBase = new ArrayList<ComputerBase>();
	List<LanguageBase> listDataHeaderLanguageBase = new ArrayList<LanguageBase>();
	HashMap<String, List<listContent>> listDataChild;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coursedetaillist);
		listDataChild = new HashMap<String, List<listContent>>();
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		setUpLeftMenu(1);
 		Bundle bundle= getIntent().getExtras();
		value3 =bundle.getStringArray("title1");
		value4[0]=Integer.parseInt(value3[0]);//课程类型编码
        setUpTitle(value3[1]);
		initData(Integer.parseInt(value3[0]));
//		queryData(Integer.parseInt(value3[0]));
//		new Handler().postDelayed(new Runnable() {
//			public void run() {
////				Log.i("videourl",listDataHeader.get(0).getListContent()+"/"+listDataHeader.get(0).getChaptercore()+"/"+"listDataHeader.get(0).getVideofile().getFileUrl()");
//				listAdapter = new ExpandableMathAdapter(getApplicationContext(), listDataHeader, listDataChild);
//
//				// setting list adapter
//
//				expListView.setGroupIndicator(null);
//				expListView.setAdapter(listAdapter);
//				for (int i = 0; i < listDataHeader.size(); i++) {
//					expListView.expandGroup(i);
//				}
//			}
//		}, 500);
		expListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
										int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
//				Toast.makeText(
//						getApplicationContext(),
//						listDataHeader.get(groupPosition)
//								+ " : "
//								+ listDataChild.get(
//								listDataHeader.get(groupPosition)).get(
//								childPosition), Toast.LENGTH_SHORT)
//						.show();
//				Log.i("videourl",listDataHeader.get(0).getListContent()+"/"+listDataHeader.get(0).getVideofile().getFileUrl(getApplicationContext()));
 				Intent intent = new Intent();
      			Bundle bundle = new Bundle();
				value4[1]=groupPosition+1;//哪个章节
				value4[2]=childPosition+1;//哪个小节
				Log.i("value4",""+value4);
				Log.i("flag1",""+value4[0]+"/"+value4[1]+"/"+value4[2]);
//    			bundle.putString("url", listDataHeader.get(0).getVideofile().getFileUrl(getApplicationContext()));
				bundle.putIntArray("type",value4);
				intent.setClass(CourseBaseExpandableList.this, VideoCourseActivity.class);
//                intent.putExtra("team", dataList.get(position));
 				intent.putExtras(bundle);
				startActivity(intent);
				return false;
			}
		});
	}
	private void initData(int values){

		switch (values){
			case 0:
				queryLanguageData(values);

				break;
			case 1:
				queryMathData(values);
				break;
			case 2:
				queryComputerData(values);
				break;
			case 3:
				queryEnglishData(values);
				break;
			default:
				break;
		}



	}
	private void queryMathData(int values) {
		// TODO Auto-generated method stub
		BmobQuery<MathBase> mathQuery = new BmobQuery<MathBase>();
		//按照时间降序
// 		mathQuery.addWhereEqualTo("coursetype",values);
 		mathQuery.order("ordeby");
		//判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
		boolean isCache = mathQuery.hasCachedResult(this.getApplication(),MathBase.class);
		if(isCache){
//            --此为举个例子，并不一定按这种方式来设置缓存策略
			mathQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
		}else{
			mathQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
		}
		mathQuery.findObjects(CourseBaseExpandableList.this, new FindListener<MathBase>() {

			@Override
			public void onSuccess(List<MathBase> data) {
				// TODO Auto-generated method stub
				if (data != null) {

					listDataHeaderMathBase.addAll(data);
					new Handler().postDelayed(new Runnable() {
						public void run() {
							listMathAdapter = new ExpandableMathAdapter(getApplicationContext(), listDataHeaderMathBase, listDataChild);
							expListView.setGroupIndicator(null);
							expListView.setAdapter(listMathAdapter);
							for (int i = 0; i < listDataHeaderMathBase.size(); i++) {
								expListView.expandGroup(i);
							}
						}
					}, 500);
					for (int i = 0; i < data.size(); i++) {
						listDataChild.put(data.get(i).getChapter(), data.get(i).getListContent());
					}
				}
			}

			@Override
			public void onError(int errorCode, String errorString) {
				// TODO Auto-generated method stub

			}
		});
	}
	private void queryLanguageData(int values) {
		// TODO Auto-generated method stub
		BmobQuery<LanguageBase> mathQuery = new BmobQuery<LanguageBase>();
		//按照时间降序
//		mathQuery.addWhereEqualTo("coursetype",values);
		mathQuery.order("ordeby");
		//判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
		boolean isCache = mathQuery.hasCachedResult(this.getApplication(),LanguageBase.class);
		if(isCache){
//            --此为举个例子，并不一定按这种方式来设置缓存策略
			mathQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
		}else{
			mathQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
		}

		mathQuery.findObjects(CourseBaseExpandableList.this, new FindListener<LanguageBase>() {

			@Override
			public void onSuccess(List<LanguageBase> data) {
				// TODO Auto-generated method stub
				if (data != null) {

					listDataHeaderLanguageBase.addAll(data);
					new Handler().postDelayed(new Runnable() {
						public void run() {
							listLanguageAdapter = new ExpandableLanguageAdapter(getApplicationContext(), listDataHeaderLanguageBase, listDataChild);
							expListView.setGroupIndicator(null);
							expListView.setAdapter(listLanguageAdapter);
							for (int i = 0; i < listDataHeaderLanguageBase.size(); i++) {
								expListView.expandGroup(i);
							}
						}
					}, 500);
					for (int i = 0; i < data.size(); i++) {
						listDataChild.put(data.get(i).getChapter(), data.get(i).getListContent());
					}
				}
			}
			@Override
			public void onError(int errorCode, String errorString) {
				// TODO Auto-generated method stub

			}
		});
	}
	private void queryEnglishData(int values) {
		// TODO Auto-generated method stub
		BmobQuery<EnglishBase> mathQuery = new BmobQuery<EnglishBase>();
		//按照时间降序
		mathQuery.addWhereEqualTo("coursetype",values);
		mathQuery.order("ordeby");
		//判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
		boolean isCache = mathQuery.hasCachedResult(this.getApplication(),EnglishBase.class);
		if(isCache){
//            --此为举个例子，并不一定按这种方式来设置缓存策略
			mathQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
		}else{
			mathQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
		}

		mathQuery.findObjects(CourseBaseExpandableList.this, new FindListener<EnglishBase>() {

			@Override
			public void onSuccess(List<EnglishBase> data) {
				// TODO Auto-generated method stub
				if (data != null) {

					listDataHeaderEnglishBase.addAll(data);
					new Handler().postDelayed(new Runnable() {
						public void run() {
							listEnglishAdapter  = new ExpandableEnglishAdapter(getApplicationContext(), listDataHeaderEnglishBase, listDataChild);
							expListView.setGroupIndicator(null);
							expListView.setAdapter(listEnglishAdapter);
							for (int i = 0; i < listDataHeaderEnglishBase.size(); i++) {
								expListView.expandGroup(i);
							}
						}
					}, 500);
					for (int i = 0; i < data.size(); i++) {
						listDataChild.put(data.get(i).getChapter(), data.get(i).getListContent());
					}
				}
			}
			@Override
			public void onError(int errorCode, String errorString) {
				// TODO Auto-generated method stub

			}
		});
	}
	private void queryComputerData(int values) {
		// TODO Auto-generated method stub
		BmobQuery<ComputerBase> mathQuery = new BmobQuery<ComputerBase>();
		//按照时间降序
		mathQuery.addWhereEqualTo("coursetype",values);
		mathQuery.order("ordeby");
		//判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
		boolean isCache = mathQuery.hasCachedResult(this.getApplication(),ComputerBase.class);
		if(isCache){
//            --此为举个例子，并不一定按这种方式来设置缓存策略
			mathQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
		}else{
			mathQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
		}

		mathQuery.findObjects(CourseBaseExpandableList.this, new FindListener<ComputerBase>() {

			@Override
			public void onSuccess(List<ComputerBase> data) {
				// TODO Auto-generated method stub
				if (data != null) {

					listDataHeaderComputerBase.addAll(data);
					new Handler().postDelayed(new Runnable() {
						public void run() {
							listComputerAdapter = new ExpandableComputerAdapter(getApplicationContext(), listDataHeaderComputerBase, listDataChild);
							expListView.setGroupIndicator(null);
							expListView.setAdapter(listComputerAdapter);
							for (int i = 0; i < listDataHeaderComputerBase.size(); i++) {
								expListView.expandGroup(i);
							}
						}
					}, 500);
					for (int i = 0; i < data.size(); i++) {
						listDataChild.put(data.get(i).getChapter(), data.get(i).getListContent());
					}
				}
			}
			@Override
			public void onError(int errorCode, String errorString) {
				// TODO Auto-generated method stub

			}
		});
	}
}
