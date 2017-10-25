package com.suleman.eventnotifier;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Sulle on 25-Oct-17.
 */

public class ListAdapter extends BaseAdapter{
    Context context;
    List<Event> event_list;
    public ListAdapter(List<Event> listValue,Context context)
    {
            this.context=context;
        this.event_list=listValue;
    }
    @Override
    public int getCount() {
        return this.event_list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.event_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewItem viewItem = null;
        if(convertView==null)
        {
            viewItem = new ViewItem();
            LayoutInflater layoutInfiater = (LayoutInflater)this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInfiater.inflate(R.layout.listview_items, null);
            viewItem.tvEventName=(TextView)convertView.findViewById(R.id.tvEventName);
            convertView.setTag(viewItem);
        }
        else
        {
            viewItem = (ViewItem) convertView.getTag();
        }
        viewItem.tvEventName.setText(event_list.get(position).eventName);
        return  convertView;
    }

    class ViewItem {
        TextView tvEventName;
    }
}
