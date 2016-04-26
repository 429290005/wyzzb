package com.song.zzb.wyzzb.ui;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.adapter.ExpandableMathAdapter;
import com.song.zzb.wyzzb.bean.MathBase;
import com.song.zzb.wyzzb.bean.listContent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
public class CourseIntenseExpandableList extends BaseActivity {

    ExpandableMathAdapter listAdapter;
    ExpandableListView expListView;
    List<MathBase> listDataHeader;
    HashMap<String, List<listContent>> listDataChild;
    private String[] value=new String[2];
    private String[] value3=new String[2];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coursedetaillist);
        listDataHeader = new ArrayList<MathBase>();
        listDataChild = new HashMap<String, List<listContent>>();
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        queryNearbyTeams(null, null);

        setUpLeftMenu(1);
        Bundle bundle= getIntent().getExtras();
//        value = bundle.getStringArray("title1");
        setUpTitle(bundle.getString("title1"));
        new Handler().postDelayed(new Runnable() {
            public void run() {
//				Log.i("videourl",listDataHeader.get(0).getListContent()+"/"+listDataHeader.get(0).getChaptercore()+"/"+"listDataHeader.get(0).getVideofile().getFileUrl()");
                listAdapter = new ExpandableMathAdapter(getApplicationContext(), listDataHeader, listDataChild);

                // setting list adapter

                expListView.setGroupIndicator(null);
                expListView.setAdapter(listAdapter);
                for (int i = 0; i < listDataHeader.size(); i++) {
                    expListView.expandGroup(i);
                }
            }
        }, 500);
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
//                bundle.putString("url", listDataHeader.get(0).getVideofile().getFileUrl(getApplicationContext()));
                bundle.putString("url","");
                intent.setClass(CourseIntenseExpandableList.this, VideoCourseActivity.class);
//                intent.putExtra("team", dataList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                return false;
            }
        });
    }

    private void queryNearbyTeams(String[] keys,String[] values) {
        // TODO Auto-generated method stub
        BmobQuery<MathBase> mathQuery = new BmobQuery<MathBase>();
        //按照时间降序
        mathQuery.order("ordeby");
//        teamQuery.include("captain");
//        if(keys!=null&&values!=null){
//            for(int i=0;i<keys.length;i++){
//                teamQuery.addWhereContains(keys[i], values[i]);
//            }
//        }
//        newsQuery.addWhereEqualTo("age", 25);
//		newsQuery.setLimit(10);
//		newsQuery.order("createdAt");
//判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = mathQuery.hasCachedResult(this.getApplication(),MathBase.class);
        if(isCache){
//            --此为举个例子，并不一定按这种方式来设置缓存策略
            mathQuery.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
        }else{
            mathQuery.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }

        mathQuery.findObjects(CourseIntenseExpandableList.this, new FindListener<MathBase>() {

            @Override
            public void onSuccess(List<MathBase> data) {
                // TODO Auto-generated method stub
                if (data != null) {

                    listDataHeader.addAll(data);
                    for(int i =0;i<data.size(); i++) {
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
