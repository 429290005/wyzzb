package com.song.zzb.wyzzb.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.bean.CourseList;
import com.song.zzb.wyzzb.bean.TiKuList;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 2016/2/22.
 */
public class TiKuExciseAdapter extends BaseAdapter {
    private Context mContext;
    private List<TiKuList> news = new ArrayList<TiKuList>();

    public TiKuExciseAdapter(Context mContext, List<TiKuList> news) {
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
    public TiKuList getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.tikuexcise_item, null);
            viewHolder.title = (TextView)convertView.findViewById(R.id.title);
            viewHolder.dsc_nan = (TextView)convertView.findViewById(R.id.dsc_nan);
            viewHolder.dsc_tv = (TextView)convertView.findViewById(R.id.dsc_tv);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        TiKuList news = this.news.get(position);

        Log.i("user", "USER avatar IS NULL" + news.getPerNumber()+"/"+news.getKuTitle());
        if(news.getKuTitle()!=null){
            viewHolder.title.setText(news.getKuTitle());}
        if(news.getDifficulty()!=null){
            viewHolder.dsc_nan.setText(news.getDifficulty());}
        if(news.getPerNumber()!=null){
            viewHolder.dsc_tv.setText(news.getPerNumber().toString());}

        return convertView;
    }

    private class ViewHolder{
        TextView title;
        TextView dsc_nan;
        TextView dsc_tv;
    }
}

