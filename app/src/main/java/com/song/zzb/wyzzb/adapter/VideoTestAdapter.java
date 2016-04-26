package com.song.zzb.wyzzb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.bean.News;
import com.song.zzb.wyzzb.bean.Video;
import com.song.zzb.wyzzb.bean.videocore;
import com.song.zzb.wyzzb.htmltextview.HtmlTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 2016/2/12.
 */
public class VideoTestAdapter extends BaseAdapter {

    private Context mContext;
    private List<videocore> news = new ArrayList<videocore>();

    public VideoTestAdapter(Context mContext, List<videocore> news) {
        super();
        this.mContext = mContext;
        this.news = news;
    }

    public void setNews(List<videocore> news) {
        this.news = news;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return news.size();
    }

    @Override
    public videocore getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.video_course_content_item, null);
            viewHolder.title1 = (TextView)convertView.findViewById(R.id.title1);
            viewHolder.title2 = (TextView)convertView.findViewById(R.id.title2);
            viewHolder.text = (HtmlTextView) convertView.findViewById(R.id.html_text);
//            viewHolder.news_date = (TextView)convertView.findViewById(R.id.tv_date);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        videocore news = this.news.get(position);
        if(news.getCoretitle()!=null){
            viewHolder.title2.setText(news.getCoretitle());}
//        viewHolder.news_abstract.setText(news.getPaperauthor());
        viewHolder.title1.setText(news.getCore());
        viewHolder.text.setHtmlFromString("<h2>Hello world</h2><ul><li>cats</li><li>dogs Lorem ipsum dolor sit amet." +
                " Lorem ipsum dolor sit amet Lorem ipsum dolor sit amet</li></ul><br/><ol><li>first</li><li>second<ol>" +
                "<li>second - first<br/>newline</li></ol></li></ol><br/><img src=\"cat\"/><br/>" +
                "<br/>A very long text follows below and it contains bold parts. This can cause a crash" +
                " <a href=\"http://code.google.com/p/android/issues/detail?id=35466\">on some Android versions</a> when using a normal" +
                " TextView, but our implementation should workaround that bug.<br/><br/>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>Lo<b>remips</b>umdol<b>orsita</b>metLorem<b>ipsu</b>mdo<b>lorsit</b>ametLoremip<b>sumdolorsitamet.</b>", new HtmlTextView.LocalImageGetter());


    return convertView;
    }

    private class ViewHolder{
        TextView title1;
        TextView title2;
        TextView news_date;
        HtmlTextView text;
    }
}
