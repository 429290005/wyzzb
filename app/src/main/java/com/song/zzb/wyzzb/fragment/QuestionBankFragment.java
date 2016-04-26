package com.song.zzb.wyzzb.fragment;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.adapter.StudyMainAdapter;

/**
 * Created by song on 2016/1/28.
 */
public class QuestionBankFragment extends BaseFragment implements View.OnClickListener{
    private Fragment[] fragments;
    private LinearLayout[] mTabs;
    private TextView id_test_tv,id_answer_tv,id_exchange_tv ;
    private int index;

    private StudyMainAdapter studyMainAdapter;


    private int currentTabIndex;
    private LinearLayout id_tab_test,id_tab_answer,id_tab_exchange ;
    private TiKuInforFragment tikuExciseFragment ;    //题库练习
    private TiKuInforFragment exchangeVideoFragment;          // 交流
    private AnswerVideoFragment answerVideoFragment;     // 问答

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.fragment_tiku, container, false);
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
        setUpTitle("题库");
        initViews();
        initClick();

    }

    protected void initViews(){
        id_tab_test= (LinearLayout)findViewById(R.id.id_tab_test);
        id_tab_answer= (LinearLayout)findViewById(R.id.id_tab_answer);
        id_tab_exchange= (LinearLayout)findViewById(R.id.id_tab_exchange);
        id_test_tv = (TextView)this.findViewById(R.id.id_test_tv);
        id_answer_tv = (TextView)this.findViewById(R.id.id_answer_tv);
        id_exchange_tv = (TextView)this.findViewById(R.id.id_exchange_tv);


        mTabs = new LinearLayout[]{id_tab_test, id_tab_answer, id_tab_exchange};
         id_tab_test.setSelected(true);
        tikuExciseFragment = new TiKuInforFragment();
        exchangeVideoFragment = new TiKuInforFragment();
        answerVideoFragment = new AnswerVideoFragment();
         resetTextView(0);
         fragments = new Fragment[]{tikuExciseFragment, answerVideoFragment, exchangeVideoFragment};
                                // 添加显示第一个fragment
        getChildFragmentManager().beginTransaction().add(R.id.fragment1_container, tikuExciseFragment).add(R.id.fragment1_container, answerVideoFragment).hide(answerVideoFragment).show(tikuExciseFragment).commit();


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


                break;
            case 1:
                id_test_tv.setTextColor(Color.BLACK);
                id_answer_tv.setTextColor(getResources().getColor(R.color.color_bottom_text_press));
                id_exchange_tv.setTextColor(Color.BLACK);

                break;
            case 2:
                id_test_tv.setTextColor(Color.BLACK);
                id_answer_tv.setTextColor(Color.BLACK);
                id_exchange_tv.setTextColor(getResources().getColor(R.color.color_bottom_text_press));

                break;

            default:
                break;
        }



    }
    private void initClick(){
        id_tab_test.setOnClickListener(this);
        id_tab_answer.setOnClickListener(this);
        id_tab_exchange.setOnClickListener(this);


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

            case R.id.actionbar_title:
                showToast("你点击了标题");
                break;
            case R.id.left_menu:

                showToast("你点击了");
                break;
            default:
                break;

        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getChildFragmentManager()
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

}
