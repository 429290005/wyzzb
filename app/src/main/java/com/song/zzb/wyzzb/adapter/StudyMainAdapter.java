package com.song.zzb.wyzzb.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.bean.CourseList;
import com.song.zzb.wyzzb.util.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 2016/2/11.
 */
public class StudyMainAdapter extends BaseAdapter {
    private Context mContext;
    private List<CourseList> news = new ArrayList<CourseList>();

    public StudyMainAdapter(Context mContext, List<CourseList> news) {
        super();
        this.mContext = mContext;
        this.news = news;
    }



    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return news.size();
    }

    @Override
    public CourseList getItem(int position) {
        if (news != null && news.size() != 0) {
            return news.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub
        ViewHolder viewHolder = null;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.studymain_item, null);
            viewHolder.tv_seniorhelp = (TextView)convertView.findViewById(R.id.tv_seniorhelp);
            viewHolder.tv_seniorhelpdsc = (TextView)convertView.findViewById(R.id.tv_seniorhelpdsc);
            viewHolder.image = (ImageView)convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String picUrl = null;
        CourseList news = this.news.get(position);
        Log.i("user", "USER avatar IS NULL"+news);
        if(news.getTitle()!=null){
            viewHolder.tv_seniorhelp.setText(news.getTitle());}
        if(news.getPicFile()==null){
            Log.i("user", "USER avatar IS NULL");
        }
        String avatarUrl = null;
        if(news.getPicFile()!=null){
            avatarUrl = news.getPicFile().getFileUrl(mContext);
            ImageLoader.getInstance().displayImage(avatarUrl,viewHolder.image);
        }
        viewHolder.tv_seniorhelpdsc.setText(news.getTitledsc());
//        viewHolder.image.setText(news.getCreatedAt());
        return convertView;
    }

    private class ViewHolder{
        TextView tv_seniorhelp;
        TextView tv_seniorhelpdsc;
        ImageView image;
    }
}

