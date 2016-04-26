package com.song.zzb.wyzzb.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.song.zzb.wyzzb.R;
import com.song.zzb.wyzzb.bean.MathBase;
import com.song.zzb.wyzzb.bean.News;
import com.song.zzb.wyzzb.bean.listContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableMathAdapter extends BaseExpandableListAdapter {

	private Context _context;
	private List<MathBase> _listDataHeader= new ArrayList(); // header titles

	// child data in format of header title, child title
	private HashMap<String, List<listContent>> _listDataChild= new HashMap<>();

	public ExpandableMathAdapter(Context context, List<MathBase> listDataHeader,
								 HashMap<String, List<listContent>> listChildData) {
		this._context = context;
		this._listDataHeader = listDataHeader;
		this._listDataChild = listChildData;
	}
	@Override
	public int getGroupCount() {
		return this._listDataHeader.size();
	}
	@Override
	public int getChildrenCount(int groupPosition) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition).getChapter())
				.size();
	}
	@Override
	public Object getGroup(int groupPosition) {
		return this._listDataHeader.get(groupPosition);
	}
	@Override
	public Object getChild(int groupPosition, int childPosititon) {
		return this._listDataChild.get(this._listDataHeader.get(groupPosition).getChapter())
				.get(childPosititon);
	}
	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}
	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}
	public View getChildView(int groupPosition, final int childPosition,
							 boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolderlist viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolderlist();
			convertView = LayoutInflater.from(_context).inflate(R.layout.coursedetaillist_item, null);
			viewHolder.tv_title = (TextView)convertView.findViewById(R.id.tv_title);

			viewHolder.tv_detail = (TextView)convertView.findViewById(R.id.tv_detail);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolderlist) convertView.getTag();
		}
		viewHolder.tv_title.setText(_listDataChild.get(_listDataHeader.get(groupPosition).getChapter()).get(childPosition).getListchapter());
 		viewHolder.tv_detail.setText(_listDataChild.get(_listDataHeader.get(groupPosition).getChapter()).get(childPosition).getListchapterdsc());
		return convertView;
	}
	public View getGroupView(int groupPosition, boolean isExpanded,
 			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolderHead viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolderHead();
			convertView = LayoutInflater.from(_context).inflate(R.layout.coursedetailgroup_list_item, null);
			viewHolder.tv_chapter = (TextView)convertView.findViewById(R.id.tv_chapter);
			viewHolder.tv_content = (TextView)convertView.findViewById(R.id.tv_content);
			viewHolder.tv_detail = (TextView)convertView.findViewById(R.id.tv_detail);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolderHead) convertView.getTag();
		}
			viewHolder.tv_chapter.setText(_listDataHeader.get(groupPosition).getChapter());
	        viewHolder.tv_content.setText(_listDataHeader.get(groupPosition).getChapterdsc());
		    viewHolder.tv_detail.setText(_listDataHeader.get(groupPosition).getChaptercontent());
		return convertView;
//		[{"listchapter":"第1节","listchapterdsc":"微分中值定理（上）","url":"地址","flag":""},
//		{"listchapter":"第2节","listchapterdsc":"微分中值定理（中）","url":"地址","flag":""},
//		{"listchapter":"第3节","listchapterdsc":"微分中值定理（下）","url":"地址","flag":""},
//		{"listchapter":"第4节","listchapterdsc":"函数的单调性","url":"地址","flag":""},
//		{"listchapter":"第5节","listchapterdsc":"函数的及值","url":"地址","flag":""},
//		{"listchapter":"第6节","listchapterdsc":"函数的凹凸性","url":"地址","flag":""},
//		"listchapter":"第7节","listchapterdsc":"函数的拐点","url":"地址","flag":""}
//		,{"listchapter":"第8节","listchapterdsc":"函数的渐近线","url":"地址","flag":""},
//	{"listchapter":"第8节","listchapterdsc":"曲率与曲率半径","url":"地址","flag":""}]
	}
	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	private class ViewHolderHead{
		TextView tv_detail;
		TextView tv_content;
		TextView tv_chapter;
	}
	private class ViewHolderlist{
		TextView tv_title;
		TextView tv_detail;

	}
}
