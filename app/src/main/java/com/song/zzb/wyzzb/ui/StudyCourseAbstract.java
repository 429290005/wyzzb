//package com.song.zzb.wyzzb.ui;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ListView;
//
//import com.song.zzb.wyzzb.R;
//import com.song.zzb.wyzzb.adapter.FragmentInformaitonNewsAdapter;
//import com.song.zzb.wyzzb.adapter.StudyCourseAbstractAdapter;
//import com.song.zzb.wyzzb.bean.*;
//import com.song.zzb.wyzzb.bean.Math;
//import com.song.zzb.wyzzb.util.LogUtil;
//import com.song.zzb.wyzzb.util.ToastUtil;
//
//import java.util.List;
//
//import cn.bmob.v3.BmobQuery;
//import cn.bmob.v3.listener.FindListener;
//
///**
// * Created by song on 2016/2/1.
// */
//public class StudyCourseAbstract extends BaseActivity{
//    private ListView listView;
//    private StudyCourseAbstractAdapter courseAbstractAdapter;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.courseabstract);
//        initView();
//        queryContent(null,null);
//
//    }
//    protected void initView(){
//        setUpTitle("书本列表");
//        listView = (ListView)findViewById(R.id.list_course);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent1 = new Intent();
//                intent1.setClass(StudyCourseAbstract.this, StudyCourseDetail.class);
//                startActivity(intent1);
//            }
//        });
//
//    }
//    private void queryContent(String[] keys,String[] values) {
//        // TODO Auto-generated method stub
//        BmobQuery<Math> newsQuery = new BmobQuery<Math>();
//        //按照时间降序
//        newsQuery.order("-createdAt");
////        teamQuery.include("captain");
////        if(keys!=null&&values!=null){
////            for(int i=0;i<keys.length;i++){
////                teamQuery.addWhereContains(keys[i], values[i]);
////            }
////        }
////		showToast("正在搜索球队...");
//        newsQuery.findObjects(this, new FindListener<Math>() {
//
//            @Override
//            public void onSuccess(List<Math> data) {
//                // TODO Auto-generated method stub
//                if (data != null) {
//                     courseAbstractAdapter = new StudyCourseAbstractAdapter(StudyCourseAbstract.this,data);
//                    courseAbstractAdapter.notifyDataSetChanged();
//                    listView.setAdapter(courseAbstractAdapter);
//
//                    Log.i("News", data.get(0).getTitle());
//                    ToastUtil.showToast(StudyCourseAbstract.this, "找到" + data.get(0).getTitle() + "支球队");
//                    LogUtil.i("News", data.get(0).getTitle());
//
//                }
////                Log.i("News", data.get(0).getTitle());
////                Log.i("News", data.get(0).getPaperauthor());
////                Log.i("News", data.get(0).getPapercontent());
////                Log.i("News", data.get(0).getPapertype());
////                Log.i("News", data.get(0).getCreatedAt());
////                ToastUtil.showToast(getActivity(), "找到" + data.size() + "支球队");
//
//            }
//
//            @Override
//            public void onError(int errorCode, String errorString) {
//                // TODO Auto-generated method stub
//
//            }
//        });
//    }
//}
