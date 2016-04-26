package com.song.zzb.wyzzb.ui;



import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.view.View.OnClickListener;
import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.fragment.FindFragment;
import com.song.zzb.wyzzb.fragment.ForumFragment;

import com.song.zzb.wyzzb.fragment.MyPersonFragment;
import com.song.zzb.wyzzb.fragment.StudyMainFragment;
import com.song.zzb.wyzzb.fragment.TiKuInforFragment;
import com.umeng.comm.core.CommunitySDK;
import com.umeng.comm.core.beans.CommConfig;
import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.beans.MessageCount;
import com.umeng.comm.core.beans.Topic;
import com.umeng.comm.core.constants.Constants;
import com.umeng.comm.core.impl.CommunityFactory;
import com.umeng.comm.core.listeners.Listeners;
import com.umeng.comm.core.nets.responses.FeedItemResponse;
import com.umeng.comm.core.nets.responses.TopicResponse;
import com.umeng.comm.core.sdkmanager.LocationSDKManager;
import com.umeng.comm.core.sdkmanager.LoginSDKManager;
import com.umeng.comm.ui.fragments.CommunityMainFragment;
import com.umeng.community.location.DefaultLocationImpl;
import com.umeng.community.login.UMAuthService;
import com.umeng.community.login.UMLoginServiceFactory;
import com.umeng.community.share.UMShareServiceFactory;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengRegistrar;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;


import java.io.File;


import cn.bmob.v3.update.BmobUpdateAgent;

public class MainActivity extends BaseActivity implements OnClickListener {
    private TextView[] mTabs;
    private Fragment[] fragments;
    private int index;
    private int currentTabIndex;
//    private TextView information_tv;
    private TextView questionBank_tv;
    private TextView study_tv;
    private TextView forum_tv,me_tv;
//    private InformationFragment informationFragment;    //资讯
    private TiKuInforFragment tiKuInforFragment;
//    private QuestionBankFragment questionBankFragment;          // 题库
    private StudyMainFragment studyFragment;     // 学堂
    private ForumFragment forumFragment;            // 论坛
//    private MeFragment meFragment;          // 我
    CommunitySDK mCommSDK = null;
    /**
     * 未读消息的数量
     */
    private MessageCount mUnreadMsg ;
    private String mContainerClass;
    /**
     * 含有未读消息时的红点视图
     */
    private View mBadgeView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 1、初始化友盟微社区
        mCommSDK = CommunityFactory.getCommSDK(this);
        super.onCreate(savedInstanceState);
        mContainerClass = getClass().getName();
        setContentView(R.layout.activity_main);

         checkUpdate();
//        PushAgent mPushAgent = PushAgent.getInstance(getApplicationContext());
//        mPushAgent.enable();
        //开启推送并设置注册的回调处理
//        mPushAgent.enable(new IUmengRegisterCallback() {
//
//            @Override
//            public void onRegistered(final String registrationId) {
//                new Handler().post(new Runnable() {
//                    @Override
//                    public void run() {
//                        //onRegistered方法的参数registrationId即是device_token
//                        Log.d("device_token", registrationId);
//                    }
//                });
//            }
//        });
//        PushAgent.getInstance(getApplicationContext()).onAppStart();
//        String device_token = UmengRegistrar.getRegistrationId(getApplicationContext());
//        Log.i("device_token",""+device_token);
        mUnreadMsg = CommConfig.getConfig().mMessageCount;

        LocationSDKManager.getInstance().addAndUse(new DefaultLocationImpl());
        initView();
        // =================== 自定义设置部分 =================
        // 在初始化CommunitySDK之前配置推送和登录等组件
        useSocialLogin();

        // 使用自定义的ImageLoader
        // useMyImageLoader();

        // 使用自定义的登录系统
        //useCustomLogin();

        initPlatforms(this);
        // 设置地理位置SDK

    }
    /**
     * 初始化分享相关的平台
     *
     * @param activity
     */
    private void initPlatforms(Activity activity) {
        // 添加QQ
        UMQQSsoHandler qqHandler = new UMQQSsoHandler(activity, "1105314760",
                "nMOw4cv1SROqmJHm");
        qqHandler.addToSocialSDK();
        // 添加QZone平台
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, "1105314760",
                "nMOw4cv1SROqmJHm");
        qZoneSsoHandler.addToSocialSDK();
        // 添加微信平台
        UMWXHandler wechatHandler = new UMWXHandler(activity, "wx96110a1e3af63a39",
                "c60e3d3ff109a5d17013df272df99199");
        wechatHandler.addToSocialSDK();
        // 添加微信朋友圈平台
        UMWXHandler circleHandler = new UMWXHandler(activity, "wx96110a1e3af63a39",
                "c60e3d3ff109a5d17013df272df99199");
        circleHandler.setToCircle(true);
        circleHandler.addToSocialSDK();

        UMShareServiceFactory.getSocialService().getConfig()
                .setPlatforms(SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN,
                        SHARE_MEDIA.QZONE, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA);
        UMShareServiceFactory.getSocialService().getConfig()
                .setPlatformOrder(SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN,
                        SHARE_MEDIA.QZONE, SHARE_MEDIA.QQ, SHARE_MEDIA.SINA);
    }

    /**
     * 自定义自己的登录系统
     */
    protected void useSocialLogin() {

        // 用户自定义的登录
        UMAuthService mLogin = UMLoginServiceFactory.getLoginService("umeng_login_impl");
        String appId = "1105314760";
        String appKey = "nMOw4cv1SROqmJHm";
        // SSO 设置
        // mLogin.getConfig().setSsoHandler(new SinaSsoHandler());
        new UMQQSsoHandler(this, appId, appKey).addToSocialSDK();

        String wxappId = "wx96110a1e3af63a39";
        String wxappSecret = "c60e3d3ff109a5d17013df272df99199";
        new UMWXHandler(getApplicationContext(), wxappId,
                wxappSecret).addToSocialSDK();

        // 将登录实现注入到sdk中,key为umeng_login
        LoginSDKManager.getInstance().addAndUse(mLogin);

    }

    protected void initView(){
        questionBank_tv = (TextView)findViewById(R.id.main_bank);
        study_tv = (TextView)findViewById(R.id.main_learn);
        forum_tv = (TextView)findViewById(R.id.main_forum);
        me_tv= (TextView)findViewById(R.id.main_me);
        mBadgeView = findViewById(R.id.umeng_comm_badge_view);
        mBadgeView.setVisibility(View.GONE);
        questionBank_tv.setOnClickListener(this);
        study_tv.setOnClickListener(this);
        forum_tv.setOnClickListener(this);
        me_tv.setOnClickListener(this);
        mTabs = new TextView[]{questionBank_tv,forum_tv,study_tv,me_tv};
        questionBank_tv.setSelected(true);
        tiKuInforFragment=new TiKuInforFragment();
        // 2、单纯Fragment使用方式
        CommunityMainFragment forumfragment = new CommunityMainFragment();
        forumfragment.setBackButtonVisibility(View.GONE);
        studyFragment  = new StudyMainFragment();
        FindFragment myPersonFragment = new FindFragment();
        Bundle bundle2 = new Bundle();
        if (CommConfig.getConfig().loginedUser == null) {// 来自开发者外部调用的情况
            bundle2.putParcelable(Constants.TAG_USER, CommConfig.getConfig().loginedUser);
        } else {
            bundle2.putParcelable(Constants.TAG_USER, CommConfig.getConfig().loginedUser);
        }
        myPersonFragment.setArguments(bundle2);
        fragments = new Fragment[]{tiKuInforFragment,forumfragment,studyFragment,myPersonFragment};
        // 添加显示第一个fragment
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,tiKuInforFragment ).
                add(R.id.fragment_container,forumfragment).hide(forumfragment).show(tiKuInforFragment).commit();
        resetTextView(0);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_bank:
                index = 0;
                resetTextView(0);
                break;
            case R.id.main_forum:
                index = 1;
                resetTextView(1);
                break;
            case R.id.main_learn:
                index = 2;
                resetTextView(2);
                break;
            case R.id.main_me:
                index = 3;
                resetTextView(3);
                break;

            default:
                break;

        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        mTabs[currentTabIndex].setSelected(false);
        // 把当前tab设为选中状态
        mTabs[index].setSelected(true);
        currentTabIndex = index;
    }
    /**
     * 检查版本更新，每天一次
     */
    private void checkUpdate() {
        /* Get Last Update Time from Preferences */
//        lastUpdateTime = new SharePreferenceUtil(this).getLastUpdateTime();
        // 每天检测一次是否有更新
		/* Should Activity Check for Updates Now? */
//        if ((lastUpdateTime + (24 * 60 * 60 * 1000)) < System.currentTimeMillis()) {
			/* Save current timestamp for next Check */
//            lastUpdateTime = System.currentTimeMillis();
//            new SharePreferenceUtil(this).setLastUpdateTime(lastUpdateTime);
			/* Start Update */
//        BmobUpdateAgent.update(this);
//        }
    }

//    /**
//     * 连续按两次返回键就退出
//     */
//    @Override
//    public void onBackPressed() {
//        // TODO Auto-generated method stub
//        if (informationFragment != null
//                && informationFragment.getSettingsMenuVisibility() == View.VISIBLE) {
//            personalFragment.onBackPressed();
//            return;
//        }
//        if (teaminfoFragment != null
//                && teaminfoFragment.getSettingsMenuVisibility() == View.VISIBLE) {
//            teaminfoFragment.onBackPressed();
//            return;
//        }
//        if (firstTime + 2000 > System.currentTimeMillis()) {
//            new SharePreferenceUtil(this).setFirstPersonalCenter(false);
//            super.onBackPressed();
//        } else {
//            showToast("再按一次退出程序");
//        }
//        firstTime = System.currentTimeMillis();
//    }
    /**
     * 重置颜色
     */
    private void resetTextView(int i) {
        switch(i){
            case 0:
                questionBank_tv.setTextColor(getResources().getColor(R.color.color_theme));
                study_tv.setTextColor(Color.BLACK);
                forum_tv.setTextColor(Color.BLACK);
                me_tv.setTextColor(Color.BLACK);
                break;
            case 1:

                questionBank_tv.setTextColor(Color.BLACK);
                 forum_tv.setTextColor(getResources().getColor(R.color.color_theme));
                study_tv.setTextColor(Color.BLACK);
                me_tv.setTextColor(Color.BLACK);
                break;
            case 2:

                questionBank_tv.setTextColor(Color.BLACK);
                forum_tv.setTextColor(Color.BLACK);
                study_tv.setTextColor(getResources().getColor(R.color.color_theme));
                me_tv.setTextColor(Color.BLACK);
                break;
            case 3:

                questionBank_tv.setTextColor(Color.BLACK);
                study_tv.setTextColor(Color.BLACK);
                forum_tv.setTextColor(Color.BLACK);
                me_tv.setTextColor(getResources().getColor(R.color.color_theme));
                break;
            default:
                break;
        }

    }
    @Override
    public void onResume() {
        super.onResume();
        if (mUnreadMsg.unReadTotal > 0) {
            mBadgeView.setVisibility(View.VISIBLE);
        } else {
            mBadgeView.setVisibility(View.INVISIBLE);
        }
    }
}
