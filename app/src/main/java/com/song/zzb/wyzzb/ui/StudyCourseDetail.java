package com.song.zzb.wyzzb.ui;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.song.zzb.wyzzb.R;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.Topic;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.listeners.Listeners;
import com.umeng.comm.core.nets.responses.TopicItemResponse;
import com.umeng.comm.ui.activities.TopicDetailActivity;

/**
 * Created by song on 2016/2/1.
 */
public class StudyCourseDetail extends BaseActivity{
    private LinearLayout ly_base,ly_intensify,ly_sprint;
    private RelativeLayout nodesFavorite,topicsFavorite;
    private String[] value=new String[2];
    private String[] value2=new String[2];
    private CommunitySDK mCommSDK;
     protected Topic mTopic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_study_coursedetail);
         mCommSDK = CommunityFactory.getCommSDK(getApplicationContext());
        initView();

        initClickEvent();

    }
    protected void initView(){
        ly_base = (LinearLayout)findViewById(R.id.ly_base);
        ly_intensify=(LinearLayout)findViewById(R.id.ly_intensify);
        ly_sprint=(LinearLayout)findViewById(R.id.ly_sprint);
        nodesFavorite=(RelativeLayout)findViewById(R.id.nodesFavorite);
        topicsFavorite=(RelativeLayout)findViewById(R.id.topicsFavorite);

        Bundle bundle= getIntent().getExtras();
         value = bundle.getStringArray("title");
         setUpTitle(value[0]);
         setUpLeftMenu(1);
        //        void fetchTopicWithId(String topicid, final FetchListener listener)
//
//        参数:
//        topicid-话题id
//        listener-监听器
//
//        onComplete回调:
//        response.result-查询结果，Topic数据结构。
//        Intent intent = new Intent();
//        ComponentName componentName = new ComponentName(getActivity(), TopicDetailActivity.class);
//        intent.setComponent(componentName);
//        intent.putExtra(Constants.TAG_TOPIC, topic);
//        getActivity().startActivity(intent);
        mCommSDK.fetchTopicWithId("56f63e6455c4000ae2288240", new Listeners.FetchListener<TopicItemResponse>() {
            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(TopicItemResponse topicItemResponse) {
                mTopic=topicItemResponse.result;
            }
        });

    }
    protected void initClickEvent(){

        ly_base.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent();
                Bundle bundle1 =new Bundle();
                value2[0] =value[1];//课程类型编码
                value2[1] =actionbarTitle.getText().toString() + "基础课程";//课程名称
                Log.i("flag", "" + value);
                Log.i("flag",""+value2[0]+"/"+value2[1]);
                bundle1.putStringArray("title1",value2);
                intent1.setClass(StudyCourseDetail.this, CourseBaseExpandableList.class);
                intent1.putExtras(bundle1);
                startActivity(intent1);
            }
        });
        ly_intensify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent();
                Bundle bundle2 =new Bundle();
                value2[0] =value[1];
                value2[1] =actionbarTitle.getText().toString() + "强化课程";
                bundle2.putStringArray("title1", value2);
                Log.i("flag", "" + value);
                intent2.setClass(StudyCourseDetail.this,CourseIntenseExpandableList.class);
                intent2.putExtras(bundle2);
                startActivity(intent2);

            }
        });
        ly_sprint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent();
                Bundle bundle3 =new Bundle();
                bundle3.putString("title1", actionbarTitle.getText().toString() + "冲刺课程");
                intent3.setClass(StudyCourseDetail.this, CourseBaseExpandableList.class);
                intent3.putExtras(bundle3);
                startActivity(intent3);
            }
        });

        nodesFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName(getApplication(), TopicDetailActivity.class);
                intent.setComponent(componentName);
                intent.putExtra(Constants.TAG_TOPIC, mTopic);
                startActivity(intent);
            }
        });
        topicsFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                ComponentName componentName = new ComponentName(getApplication(), TopicDetailActivity.class);
                intent.setComponent(componentName);
                intent.putExtra(Constants.TAG_TOPIC, mTopic);
                startActivity(intent);
            }
        });
    }

}
