package com.song.zzb.wyzzb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.song.zzb.wyzzb.R;

import com.song.zzb.wyzzb.adapter.StudyMainAdapter;
import com.song.zzb.wyzzb.bean.CourseList;
import com.song.zzb.wyzzb.bean.News;
import com.song.zzb.wyzzb.ui.StudyCourseDetail;
import com.song.zzb.wyzzb.util.ActivityUtil;
import com.song.zzb.wyzzb.util.LogUtil;
import com.song.zzb.wyzzb.util.ToastUtil;
import com.song.zzb.wyzzb.util.xlistview.XListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by song on 2016/2/11.
 */
public class StudyMainFragment extends BaseFragment{
  private StudyMainAdapter studyMainAdapter;
    private List<CourseList> courseList=new ArrayList<CourseList>();
    private XListView listView;
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.studymainfragment, container, false);
        return v;
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
        setUpTitle("2015专转本学堂");
        initViews();
        initClick();
        queryData();

    }
    protected  void initClick(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               CourseList courseList=(CourseList) parent.getItemAtPosition(position);
                if(!Integer.toString(courseList.getFlagID()).equals("4") && !Integer.toString(courseList.getFlagID()).equals("5")){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    String[] value = new String[2];
                    value[0]=courseList.getTitle();//标题
                    value[1]=Integer.toString(courseList.getFlagID());//课程类别编码，通过课程类别编码，控制StudyCourseDetail.class显示的内容
                    Log.i("flag",""+value[0]+"/"+value[1]);
                    bundle.putStringArray("title", value);
                    intent.setClass(getActivity(), StudyCourseDetail.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }else{
                    ToastUtil.showToast(getContext(),"此功能还未开通");
                }

            }
        });
    }
    protected void initViews(){
        listView = (XListView)findViewById(R.id.list_study);
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(false);
        listView.setItemsCanFocus(true);

    }
    private void queryData() {
        // TODO Auto-generated method stub
        BmobQuery<CourseList> newsQuery = new BmobQuery<CourseList>();
        //按照时间降序
        newsQuery.order("createdAt");
         //判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = newsQuery.hasCachedResult(this.getContext(),CourseList.class);
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
        newsQuery.findObjects(getActivity(), new FindListener<CourseList>() {

            @Override
            public void onSuccess(List<CourseList> data) {
                // TODO Auto-generated method stub
                if (data != null) {

                    courseList.addAll(data);
                    Log.i("News", courseList.toString());
                    studyMainAdapter = new StudyMainAdapter(getActivity(), courseList);
                    studyMainAdapter.notifyDataSetChanged();
                    listView.setAdapter(studyMainAdapter);
                }
            }

            @Override
            public void onError(int errorCode, String errorString) {
                // TODO Auto-generated method stub

            }
        });
    }
}
