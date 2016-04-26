package com.song.zzb.wyzzb.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.ui.StudyCourseDetail;

/**
 * Created by song on 2016/2/10.
 */
public class DownloadVideoFragment extends BaseFragment{
    private LinearLayout linear_seniorhelp,linear_computer,linear_english,linear_math,linear_language;
    private TextView tv_computer,tv_english,tv_math,tv_language,tv_seniorhelp;
    // TODO Auto-genetv_computerrated method stub
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View v = inflater.inflate(R.layout.fragment_video_download, container, false);
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

        initViews();
        initClick();


    }
    protected void initViews(){
//        linear_seniorhelp=(LinearLayout)findViewById(R.id.linlay_seniorhelp);
//        linear_computer=(LinearLayout)findViewById(R.id.linlay_computer);
//        linear_english=(LinearLayout)findViewById(R.id.linlay_english);
//        linear_math=(LinearLayout)findViewById(R.id.linlay_math);
//        linear_language=(LinearLayout)findViewById(R.id.linlay_language);
//        tv_computer=(TextView)findViewById(R.id.tv_computer);
//        tv_english=(TextView)findViewById(R.id.tv_english);
//        tv_math=(TextView)findViewById(R.id.tv_math);
//        tv_language=(TextView)findViewById(R.id.tv_language);
//        tv_seniorhelp=(TextView)findViewById(R.id.tv_seniorhelp);
    }
    protected void initClick(){
//        linear_seniorhelp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putString("title", tv_seniorhelp.getText().toString());
//
//                intent.setClass(getActivity(), StudyCourseDetail.class);
////                intent.putExtra("team", dataList.get(position));
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//        linear_computer.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putString("title", tv_computer.getText().toString());
//
//                intent.setClass(getActivity(), StudyCourseDetail.class);
////                intent.putExtra("team", dataList.get(position));
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//        linear_english.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putString("title", tv_english.getText().toString());
//
//                intent.setClass(getActivity(), StudyCourseDetail.class);
////                intent.putExtra("team", dataList.get(position));
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
//        linear_math.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putString("title", tv_math.getText().toString());
//
//                intent.setClass(getActivity(), StudyCourseDetail.class);
////                intent.putExtra("team", dataList.get(position));
//                intent.putExtras(bundle);
//                startActivity(intent);
//            }
//        });
    }
}
