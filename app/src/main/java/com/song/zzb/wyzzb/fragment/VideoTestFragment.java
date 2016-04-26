package com.song.zzb.wyzzb.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.adapter.StudyMainAdapter;
import com.song.zzb.wyzzb.adapter.VideoTestAdapter;
import com.song.zzb.wyzzb.bean.CourseList;
import com.song.zzb.wyzzb.bean.MathBaseContent;
import com.song.zzb.wyzzb.bean.videocore;
import com.song.zzb.wyzzb.ui.StudyCourseDetail;
import com.song.zzb.wyzzb.ui.VideoCourseActivity;
import com.song.zzb.wyzzb.util.ActivityUtil;
import com.song.zzb.wyzzb.util.xlistview.XListView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by song on 2016/2/12.
 */
public class VideoTestFragment extends BaseFragment{

    private VideoTestAdapter videoTestAdapter;
    private XListView listView;
    private  List<videocore> videocores = new ArrayList<>();
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        Bundle bundle=getArguments();
        if(bundle!=null){
            videocores.addAll((List)bundle.getSerializable("core"));
            Log.i("name", "" + bundle.getSerializable("core"));
            Log.i("name", "" + videocores);
        }

        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.fragment_video_test, container, false);
//        Bundle bundle=getArguments();
//        Log.i("name", "" + bundle.getSerializable("core"));
        return v;
    }
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        // TODO Auto-generated method stub
        super.onHiddenChanged(hidden);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onViewCreated(view, savedInstanceState);

        initViews();
        initClick();
//        queryData();

    }
    protected  void initClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                CourseList courseList=(CourseList) parent.getItemAtPosition(position);
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                String[] value = new String[2];
//                value[0]=courseList.getTitle();
//                value[1]=Integer.toString(courseList.getFlagID());
//                bundle.putStringArray("title", value);
//                intent.setClass(getActivity(), StudyCourseDetail.class);
//                intent.putExtras(bundle);
//                startActivity(intent);
            }
        });
    }
    protected void initViews(){
        listView = (XListView)findViewById(R.id.list_test);
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(false);
        listView.setItemsCanFocus(true);
        videoTestAdapter = new VideoTestAdapter(getActivity(),videocores);
        videoTestAdapter.notifyDataSetChanged();
        listView.setAdapter(videoTestAdapter);
        ActivityUtil.setListViewHeightBasedOnChildren(listView);

    }
    private void queryData() {
        // TODO Auto-generated method stub
        BmobQuery<videocore> newsQuery = new BmobQuery<videocore>();
        //按照时间降序
//        newsQuery.order("createdAt");
//判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = newsQuery.hasCachedResult(this.getContext(),videocore.class);
        if(isCache){
            if(ActivityUtil.hasNetwork(this.getContext())){
                newsQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则
            }else{
                //--此为举个例子，并不一定按这种方式来设置缓存策略
                newsQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
            }

        }else{
            newsQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }
        newsQuery.findObjects(getActivity(), new FindListener<videocore>() {

            @Override
            public void onSuccess(List<videocore> data) {
                // TODO Auto-generated method stub
                if (data != null) {
//                    VideoCourseActivity videoCourseActivity =new VideoCourseActivity();
////                    courseList.addAll(data);
//                    ((VideoCourseActivity)getActivity()).getVideocores().toString();
//                  Log.i("videocore", videoCourseActivity.getVideocores().toString());
//                    videoTestAdapter = new VideoTestAdapter(getActivity(),videoCourseActivity.getVideocores());
//                    videoTestAdapter.notifyDataSetChanged();
//                    listView.setAdapter(videoTestAdapter);
//                    ActivityUtil.setListViewHeightBasedOnChildren(listView);
                }
            }

            @Override
            public void onError(int errorCode, String errorString) {
                // TODO Auto-generated method stub

            }
        });
    }
}
