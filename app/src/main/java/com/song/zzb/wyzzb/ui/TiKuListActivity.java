package com.song.zzb.wyzzb.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.adapter.MyWrongAdapter;
import com.song.zzb.wyzzb.adapter.TiKuExciseAdapter;
import com.song.zzb.wyzzb.bean.TiKuList;
import com.song.zzb.wyzzb.util.ActivityUtil;
import com.song.zzb.wyzzb.util.xlistview.XListView;
import com.umeng.comm.core.beans.CommConfig;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.impl.CommunitySDKImpl;
import com.umeng.comm.core.login.LoginListener;
import com.umeng.comm.core.utils.CommonUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by song on 2016/5/6.
 */
public class TiKuListActivity extends BaseActivity{

    private int[] value=new int[2];
    Bundle bundle =new Bundle();
    private TiKuExciseAdapter tiKuBaseAdapter;
    private XListView listView;
    Bundle bundletitle =new Bundle();
    protected CommUser mUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tikulist);
        mUser = CommonUtils.getLoginUser(this);
        initViews();
        if(getIntent().getExtras().getString("title").equals("基础试题")){
            setUpTitle("基础试题");
            queryData();
        };
        if(getIntent().getExtras().getString("title").equals("强化试题")){
            setUpTitle("强化试题");
            queryDataIntense();
        };
        if(getIntent().getExtras().getString("title").equals("冲刺试题")){
            setUpTitle("冲刺试题");
            queryDatasprint();
        };
        if(getIntent().getExtras().getString("title").equals("历年真题")){
            setUpTitle("历年真题");
            queryLastRealPaper();
        };
		/*
		 * 清空题库
		 */
        rightMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                final TiKuList tKuLtist1 = (TiKuList) parent.getItemAtPosition(position);
                if (CommonUtils.isLogin(getApplicationContext())) {
                    CommUser user = CommonUtils.getLoginUser(getApplicationContext());
//                    Log.i("userinfor", "" + user.name + "////" + user.point + "////" + user.toString());
//                    Log.i("userinfor7", "" + user.name + "////" + user.point + "////" + tKuLtist1.getPoint());
                    if (user.point >= tKuLtist1.getPoint()) {
                        Intent intent = new Intent();
                        value[0] = position;
                        value[1] = tKuLtist1.questiontype;
                        bundle.putIntArray("value", value);
                        bundletitle.putString("title", tKuLtist1.getKuTitle());
                        Log.i("flag", "" + value);
                        intent.setClass(getApplicationContext(), ExameActivity.class);
                        intent.putExtras(bundle);
                        intent.putExtras(bundletitle);
                        startActivity(intent);
                    } else {
                        Log.i("userinfor6", "" + user.id + "////" + user.point + "////" + tKuLtist1.getPoint());
                        showToast("积分不足，请联系管理员");
                    }
                } else {
                    mUser = CommConfig.getConfig().loginedUser;
                    CommunitySDKImpl.getInstance().login(getApplicationContext(), new LoginListener() {
                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onComplete(int stCode, CommUser userInfo) {
                            Log.i("userinfor3", "" + stCode);
                            if (stCode == 0) {
                                if (userInfo.point >= tKuLtist1.getPoint()) {
                                    Log.i("userinfor5", "" + userInfo.id + "////" + userInfo.point + "////" + tKuLtist1.getPoint());
                                    Intent intent = new Intent();
                                    value[0] = position;
                                    value[1] = tKuLtist1.questiontype;
                                    bundle.putIntArray("value", value);
                                    Log.i("flag1", "" + value.toString());
                                    intent.setClass(getApplicationContext(), ExameActivity.class);
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                } else {
                                    Log.i("userinfor4", "" + userInfo.name + "////" + userInfo.point + "////" + tKuLtist1.getPoint());
                                    showToast("积分不足，请联系管理员");
                                }
                            }
                        }
                    });
                }
                TiKuList tKuLtist = new TiKuList();
                tKuLtist.increment("perNumber"); // 人数递增1
                tKuLtist.update(getApplication(), tKuLtist1.getObjectId(), new UpdateListener() {
                    @Override
                    public void onSuccess() {

                        Log.i("perNumber", "更新成功：");
                    }

                    @Override
                    public void onFailure(int i, String s) {
                        Log.i("perNumber", "更新失败：" + s);
                    }
                });
            }
        });

    }

    protected void initViews(){
        setUpLeftMenu(1);
        setUpRightMenu("纸质版");
        listView = (XListView)findViewById(R.id.list_study);
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(false);
        listView.setItemsCanFocus(true);

    }
    /*
      冲刺试题
       */
    private void queryDatasprint() {
        // TODO Auto-generated method stub
        BmobQuery<TiKuList> newsQuery = new BmobQuery<TiKuList>();
        //按照时间降序
        newsQuery.addWhereEqualTo("questiontype", 2);
        newsQuery.order("orderby");
        //判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = newsQuery.hasCachedResult(getApplicationContext(),TiKuList.class);
        if(isCache){
            if(ActivityUtil.hasNetwork(getApplicationContext())){
                newsQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则
            }else{
                //--此为举个例子，并不一定按这种方式来设置缓存策略
                newsQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
            }

        }else{
            newsQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }
        newsQuery.findObjects(getApplicationContext(), new FindListener<TiKuList>() {

            @Override
            public void onSuccess(List<TiKuList> data) {
                // TODO Auto-generated method stub
                if (data != null) {
//                    courseList.addAll(data);
                    tiKuBaseAdapter = new TiKuExciseAdapter(getApplicationContext(), data);
                    tiKuBaseAdapter.notifyDataSetChanged();
                    listView.setAdapter(tiKuBaseAdapter);

                }
            }

            @Override
            public void onError(int errorCode, String errorString) {
                // TODO Auto-generated method stub
                showToast(errorString);
            }
        });
    }
    /*
    强化试题
     */
    private void queryDataIntense() {
        // TODO Auto-generated method stub
        BmobQuery<TiKuList> newsQuery = new BmobQuery<TiKuList>();
        //按照时间降序
        newsQuery.addWhereEqualTo("questiontype", 1);
        newsQuery.order("orderby");
        //判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = newsQuery.hasCachedResult(getApplicationContext(),TiKuList.class);
        if(isCache){
            if(ActivityUtil.hasNetwork(getApplicationContext())){
                newsQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则
            }else{
                //--此为举个例子，并不一定按这种方式来设置缓存策略
                newsQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
            }

        }else{
            newsQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }
        newsQuery.findObjects(getApplicationContext(), new FindListener<TiKuList>() {

            @Override
            public void onSuccess(List<TiKuList> data) {
                // TODO Auto-generated method stub
                if (data != null) {
//                    courseList.addAll(data);
                    tiKuBaseAdapter = new TiKuExciseAdapter(getApplicationContext(), data);
                    tiKuBaseAdapter.notifyDataSetChanged();
                    listView.setAdapter(tiKuBaseAdapter);

                }
            }

            @Override
            public void onError(int errorCode, String errorString) {
                // TODO Auto-generated method stub
                showToast(errorString);
            }
        });
    }
    private void queryData() {
        // TODO Auto-generated method stub
        BmobQuery<TiKuList> newsQuery = new BmobQuery<TiKuList>();
        //按照时间降序
        newsQuery.addWhereEqualTo("questiontype", 0);
        newsQuery.order("orderby");
        //判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = newsQuery.hasCachedResult(getApplicationContext(),TiKuList.class);
        if(isCache){
            if(ActivityUtil.hasNetwork(getApplicationContext())){
                newsQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则
            }else{
                //--此为举个例子，并不一定按这种方式来设置缓存策略
                newsQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
            }

        }else{
            newsQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }
        newsQuery.findObjects(getApplicationContext(), new FindListener<TiKuList>() {

            @Override
            public void onSuccess(List<TiKuList> data) {
                // TODO Auto-generated method stub
                if (data != null) {
//                    courseList.addAll(data);
                    tiKuBaseAdapter = new TiKuExciseAdapter(getApplicationContext(), data);
                    tiKuBaseAdapter.notifyDataSetChanged();
                    listView.setAdapter(tiKuBaseAdapter);

                }
            }

            @Override
            public void onError(int errorCode, String errorString) {
                // TODO Auto-generated method stub
                showToast(errorString);
            }
        });
    }


    /*
    历年真题
     */
    private void queryLastRealPaper() {
        // TODO Auto-generated method stub
        BmobQuery<TiKuList> newsQuery = new BmobQuery<TiKuList>();
        //按照时间降序
        newsQuery.addWhereEqualTo("questiontype", 4);
        newsQuery.order("orderby");
        //判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = newsQuery.hasCachedResult(getApplicationContext(),TiKuList.class);
        if(isCache){
            if(ActivityUtil.hasNetwork(getApplicationContext())){
                newsQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则
            }else{
                //--此为举个例子，并不一定按这种方式来设置缓存策略
                newsQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
            }

        }else{
            newsQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }
        newsQuery.findObjects(getApplicationContext(), new FindListener<TiKuList>() {

            @Override
            public void onSuccess(List<TiKuList> data) {
                // TODO Auto-generated method stub
                if (data != null) {
//                    courseList.addAll(data);
                    tiKuBaseAdapter = new TiKuExciseAdapter(getApplicationContext(), data);
                    tiKuBaseAdapter.notifyDataSetChanged();
                    listView.setAdapter(tiKuBaseAdapter);

                }
            }

            @Override
            public void onError(int errorCode, String errorString) {
                // TODO Auto-generated method stub
                showToast(errorString);
            }
        });
    }
}

