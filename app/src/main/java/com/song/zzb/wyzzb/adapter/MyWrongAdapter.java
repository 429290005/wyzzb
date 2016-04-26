package com.song.zzb.wyzzb.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.song.zzb.wyzzb.R;

import java.util.List;

/**
 * Created by song on 2016/2/28.
 */
public class MyWrongAdapter extends BaseAdapter {
    private ListView listView;
    private List<String> list;
    private LayoutInflater mInflater;
    private Context context;

    public MyWrongAdapter(Context context, List<String> list,
                                      ListView listView) {
        // this.context = context;
        this.listView = listView;
        this.context=context;
        this.list = list;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        final String map = list.get(position);
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.mywrong_item, null);

            viewHolder.title = (TextView) convertView.findViewById(R.id.my_error_item_name);
        }
        if (map != null ) {

            viewHolder.title.setText(map);

        }
        convertView.setTag(viewHolder);
        return convertView;
    }

    /**
     * 得到数据
     *
     * @return
     */
    public List<String> GetData() {
        return list;
    }

    public int getCount() {
        return list.size();
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder {
        TextView title;
    }
}
