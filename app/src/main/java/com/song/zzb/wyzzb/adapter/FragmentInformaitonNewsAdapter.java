//package com.song.zzb.wyzzb.adapter;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import com.song.zzb.wyzzb.R;
//import com.song.zzb.wyzzb.bean.News;
//
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class FragmentInformaitonNewsAdapter extends BaseAdapter {
//
//	private Context mContext;
//	private List<News> news = new ArrayList<News>();
//
//	public FragmentInformaitonNewsAdapter(Context mContext, List<News> news) {
//		super();
//		this.mContext = mContext;
//		this.news = news;
//	}
//
//	public void setNews(List<News> news) {
//		this.news = news;
//	}
//
//	@Override
//	public int getCount() {
//		// TODO Auto-generated method stub
//		return news.size();
//	}
//
//	@Override
//	public News getItem(int position) {
//		if (news != null && news.size() != 0) {
//			return news.get(position);
//		}
//		return null;
//	}
//
//	@Override
//	public long getItemId(int position) {
//		// TODO Auto-generated method stub
//		return position;
//	}
//
//	@Override
//	public View getView(int position, View convertView, ViewGroup parent) {
//
//		// TODO Auto-generated method stub
//		ViewHolder viewHolder = null;
//		if(convertView == null){
//			viewHolder = new ViewHolder();
//			convertView = LayoutInflater.from(mContext).inflate(R.layout.fragmentinformation_xlistview_item, null);
//			viewHolder.news_title = (TextView)convertView.findViewById(R.id.tv_title);
//			viewHolder.news_abstract = (TextView)convertView.findViewById(R.id.tv_paperabstract);
//			viewHolder.news_date = (TextView)convertView.findViewById(R.id.tv_date);
// 		convertView.setTag(viewHolder);
//		}else{
//			viewHolder = (ViewHolder) convertView.getTag();
//		}
//
//		News news = this.news.get(position);
//		if(news.getTitle()!=null){
//		viewHolder.news_title.setText(news.getTitle());}
//		viewHolder.news_abstract.setText(news.getPaperauthor());
//		viewHolder.news_date.setText(news.getCreatedAt());
//		return convertView;
//	}
//
//	private class ViewHolder{
//		TextView news_title;
//		TextView news_abstract;
//		TextView news_date;
//	}
//}
