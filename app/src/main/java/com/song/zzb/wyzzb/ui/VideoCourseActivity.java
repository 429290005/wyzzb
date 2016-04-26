package com.song.zzb.wyzzb.ui;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.bean.MathBaseContent;
import com.song.zzb.wyzzb.bean.videocore;
import com.song.zzb.wyzzb.fragment.AnswerVideoFragment;
import com.song.zzb.wyzzb.fragment.DownloadVideoFragment;

import com.song.zzb.wyzzb.fragment.TiKuInforFragment;
import com.song.zzb.wyzzb.fragment.VideoTestFragment;
import com.song.zzb.wyzzb.ui.view.MediaController;
import com.song.zzb.wyzzb.ui.view.SuperVideoPlayer;
import com.song.zzb.wyzzb.util.DensityUtil;
import java.util.ArrayList;
import java.util.List;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;


public class VideoCourseActivity extends BaseActivity implements View.OnClickListener {
    private SuperVideoPlayer mSuperVideoPlayer;
    private Button mPlayBtnView;
    private Fragment[] fragments;
    private LinearLayout[] mTabs;
    private TextView id_test_tv,id_answer_tv,id_exchange_tv,id_download_tv,actionbar_right_menu,actionbar_title;
    private int index;
    private String url="";
    private String author="";
    private String title="";
    private int[] value=new int[3];

    private ArrayList<videocore> videocores = new ArrayList<>();
    private int currentTabIndex;
    private LinearLayout id_tab_test,id_tab_answer,id_tab_exchange,id_tab_download;
    private VideoTestFragment testVideoFragment ;    //考点
    private TiKuInforFragment exchangeVideoFragment;          // 交流
    private AnswerVideoFragment answerVideoFragment;     // 问答
    private DownloadVideoFragment downloadVideoFragment;            // 下载
   private videocore videocore= new videocore();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.videocourse);
        initView();
        initClick();
    }
    private void initView(){
        mSuperVideoPlayer = (SuperVideoPlayer) findViewById(R.id.video_player_item_1);
        mPlayBtnView = (Button)findViewById(R.id.play_btn);
        mPlayBtnView.setOnClickListener(this);
        mSuperVideoPlayer.setVideoPlayCallback(mVideoPlayCallback);
        id_tab_test= (LinearLayout)findViewById(R.id.id_tab_test);
        id_tab_answer= (LinearLayout)findViewById(R.id.id_tab_answer);
        id_tab_exchange= (LinearLayout)findViewById(R.id.id_tab_exchange);
        id_tab_download= (LinearLayout)findViewById(R.id.id_tab_download);
        id_test_tv = (TextView)this.findViewById(R.id.id_test_tv);
        id_answer_tv = (TextView)this.findViewById(R.id.id_answer_tv);
        id_exchange_tv = (TextView)this.findViewById(R.id.id_exchange_tv);
        id_download_tv = (TextView)this.findViewById(R.id.id_download_tv);
        actionbar_right_menu=(TextView)this.findViewById(R.id.actionbar_right_menu);
        actionbar_title=(TextView)this.findViewById(R.id.actionbar_title);
        Bundle bundle= getIntent().getExtras();
        value = bundle.getIntArray("type");
        Log.i("flag", "" + value[0] + "/" + value[1]);
        queryData(value[1], value[2]);
        Log.i("videocore2", "" + videocores);
        Log.i("flagauthor3", "" + author + "/" + title);
//                            actionbar_right_menu.setText(author);
//                            actionbar_title.setText(title);

//        new Thread() {//创建子线程进行网络访问的操作
//            public void run() {
//                try {
//                    Log.i("flag1", "" + value[0] + "/" + value[1]);
//                    if(videocores!=null || videocores.equals("")){
//                        Log.i("videocore6", "" + videocores);
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                mTabs = new LinearLayout[]{id_tab_test, id_tab_answer, id_tab_exchange, id_tab_download};
//                                id_tab_test.setSelected(true);
//                                testVideoFragment = new VideoTestFragment();
//                                Bundle bundle2 = new Bundle();
//                                bundle2.putSerializable("core", videocores);
//                                Log.i("videocore1", "" + videocores);
//                                testVideoFragment.setArguments(bundle2);
//                                exchangeVideoFragment = new InformationFragment();
//                                answerVideoFragment = new AnswerVideoFragment();
//                                downloadVideoFragment = new DownloadVideoFragment();
//                                resetTextView(0);
//                                fragments = new Fragment[]{testVideoFragment, answerVideoFragment, exchangeVideoFragment, downloadVideoFragment};
//                                // 添加显示第一个fragment
//                                getSupportFragmentManager().beginTransaction().add(R.id.fragment1_container, testVideoFragment).
//                                        add(R.id.fragment1_container, answerVideoFragment).hide(answerVideoFragment).show(testVideoFragment).commit();
//                            }
//                        });
//                    }
////                    handler.sendEmptyMessage(0);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();


}
    private void initClick(){
        id_tab_test.setOnClickListener(this);
        id_tab_answer.setOnClickListener(this);
        id_tab_exchange.setOnClickListener(this);
        id_tab_download.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
//        resetTextView();
        switch (v.getId()) {
            case R.id.id_tab_test:
                index = 0;
                resetTextView(0);
                break;
            case R.id.id_tab_answer:
                index = 1;
                resetTextView(1);
                break;
            case R.id.id_tab_exchange:
                index = 2;
                resetTextView(2);
                break;
            case R.id.id_tab_download:
                index = 3;
                resetTextView(3);
                break;
            case R.id.play_btn:
                mPlayBtnView.setVisibility(View.GONE);
                mSuperVideoPlayer.setVisibility(View.VISIBLE);
                mSuperVideoPlayer.setAutoHideController(false);
                Uri uri = Uri.parse(url);
                mSuperVideoPlayer.loadAndPlay(uri,0);
                break;
            case R.id.actionbar_title:
                showToast("你点击了标题");
                break;
            case R.id.left_menu:
                finish();
                showToast("你点击了");
                break;
            default:
                break;

        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment1_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }
    /**
     * 播放器的回调函数
     */
    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new SuperVideoPlayer.VideoPlayCallbackImpl() {
        /**
         * 播放器关闭按钮回调
         */
        @Override
        public void onCloseVideo() {
            mSuperVideoPlayer.close();//关闭VideoView
            mPlayBtnView.setVisibility(View.VISIBLE);
            mSuperVideoPlayer.setVisibility(View.GONE);
            resetPageToPortrait();
        }

        /**
         * 播放器横竖屏切换回调
         */
        @Override
        public void onSwitchPageType() {
            if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                mSuperVideoPlayer.setPageType(MediaController.PageType.EXPAND);
            }
        }

        /**
         * 播放完成回调
         */
        @Override
        public void onPlayFinish() {

        }
    };
    /***
     * 旋转屏幕之后回调
     *
     * @param newConfig newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (null == mSuperVideoPlayer) return;
        /***
         * 根据屏幕方向重新设置播放器的大小
         */
        if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().getDecorView().invalidate();
            float height = DensityUtil.getWidthInPx(this);
            float width = DensityUtil.getHeightInPx(this);
            mSuperVideoPlayer.getLayoutParams().height = (int) width;
            mSuperVideoPlayer.getLayoutParams().width = (int) height;
        } else if (this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            final WindowManager.LayoutParams attrs = getWindow().getAttributes();
            attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(attrs);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
            float width = DensityUtil.getWidthInPx(this);
            float height = DensityUtil.dip2px(this, 200.f);
            mSuperVideoPlayer.getLayoutParams().height = (int) height;
            mSuperVideoPlayer.getLayoutParams().width = (int) width;
        }
    }

    /***
     * 恢复屏幕至竖屏
     */
    private void resetPageToPortrait() {
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mSuperVideoPlayer.setPageType(MediaController.PageType.SHRINK);
        }
    }
    /**
     * 重置颜色
     */
    private void resetTextView(int i) {
        switch(i){

            case 0:
                id_test_tv.setTextColor(getResources().getColor(R.color.color_bottom_text_press));
                id_answer_tv.setTextColor(Color.BLACK);
                id_exchange_tv.setTextColor(Color.BLACK);
                id_download_tv.setTextColor(Color.BLACK);

                break;
            case 1:
                id_test_tv.setTextColor(Color.BLACK);
                id_answer_tv.setTextColor(getResources().getColor(R.color.color_bottom_text_press));
                id_exchange_tv.setTextColor(Color.BLACK);
                id_download_tv.setTextColor(Color.BLACK);
                break;
            case 2:
                id_test_tv.setTextColor(Color.BLACK);
                id_answer_tv.setTextColor(Color.BLACK);
                id_exchange_tv.setTextColor(getResources().getColor(R.color.color_bottom_text_press));
                id_download_tv.setTextColor(Color.BLACK);
                break;
            case 3:
                id_test_tv.setTextColor(Color.BLACK);
                id_answer_tv.setTextColor(Color.BLACK);
                id_exchange_tv.setTextColor(Color.BLACK);
                id_download_tv.setTextColor(getResources().getColor(R.color.color_bottom_text_press));
                break;
            default:
                break;
        }



    }
    private void queryData(int value0,int value1) {
//        Log.i("flag", "" + value0+"/"+value1);
        // TODO Auto-generated method stub
        BmobQuery<MathBaseContent> mathQuery1 = new BmobQuery<MathBaseContent>();
        //按照时间降序
        mathQuery1.addWhereEqualTo("chapter",value0);
        BmobQuery<MathBaseContent> mathQuery2 = new BmobQuery<MathBaseContent>();
        //按照时间降序
        mathQuery2.addWhereEqualTo("chapterAct", value1);
        //最后组装完整的and条件
        List<BmobQuery<MathBaseContent>> andQuerys = new ArrayList<BmobQuery<MathBaseContent>>();
        andQuerys.add(mathQuery1);
        andQuerys.add(mathQuery2);
        //查询符合整个and条件的人
        BmobQuery<MathBaseContent> query = new BmobQuery<MathBaseContent>();
        query.and(andQuerys);
//判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
        boolean isCache = query.hasCachedResult(this.getApplication(),MathBaseContent.class);
        if(isCache){
//            --此为举个例子，并不一定按这种方式来设置缓存策略
            query.setCachePolicy(BmobQuery.CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
        }else{
            query.setCachePolicy(BmobQuery.CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
        }

        query.findObjects(VideoCourseActivity.this, new FindListener<MathBaseContent>() {

            @Override
            public void onSuccess(List<MathBaseContent> data) {
                // TODO Auto-generated method stub
                if (data != null) {
                    url = data.get(0).getVideofile().getFileUrl(getApplicationContext());
                    author = data.get(0).getVideoauthor();
                    title = data.get(0).getVideotitle();
                    Log.i("author",""+author+"/"+title);
                    Log.i("author", "" + data.get(0).getVideocore());
                    Log.i("flagauthor2", "" + author + "/" + title);
                    actionbar_right_menu.setText(author);
                    actionbar_title.setText(title);
                    mTabs = new LinearLayout[]{id_tab_test, id_tab_answer, id_tab_exchange, id_tab_download};
                    id_tab_test.setSelected(true);
                    testVideoFragment = new VideoTestFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putSerializable("core", videocores);
                    Log.i("videocore1", "" + videocores);
                    testVideoFragment.setArguments(bundle2);
                    exchangeVideoFragment = new TiKuInforFragment();
                    answerVideoFragment = new AnswerVideoFragment();
                    downloadVideoFragment = new DownloadVideoFragment();
                    resetTextView(0);
                    fragments = new Fragment[]{testVideoFragment, answerVideoFragment, exchangeVideoFragment, downloadVideoFragment};
                    // 添加显示第一个fragment
                    getSupportFragmentManager().beginTransaction().add(R.id.fragment1_container, testVideoFragment).
                            add(R.id.fragment1_container, answerVideoFragment).hide(answerVideoFragment).show(testVideoFragment).commit();
//                    videocores.set(data.get(0).getVideocore());
//                    ToastUtil.showToast(getApplication(), data.get(0).getVideocoreList().get(0).getCore());
                    videocores.addAll(data.get(0).getVideocore());

                    Log.i("videocore5", "" + videocores);
                    Log.i("videocore", "" + videocores);
//                    videoTestAdapter = new VideoTestAdapter(getApplicationContext(), data.get(0).getVideocoreList());
//                    videoTestAdapter.notifyDataSetChanged();
//                    xListView.setAdapter(videoTestAdapter);
                }
            }

            @Override
            public void onError(int errorCode, String errorString) {
                // TODO Auto-generated method stub

            }
        });
    }
}
