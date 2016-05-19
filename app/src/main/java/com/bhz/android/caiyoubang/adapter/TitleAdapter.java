package com.bhz.android.caiyoubang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.data.EventSummary;

import java.util.List;

/**
 * Created by Administrator on 2016/5/7.
 */
public class TitleAdapter extends MyBaseAdapter<EventSummary> {
    Context context;
    List<EventSummary> list;
    LayoutInflater inflater;
    public TitleAdapter(Context context, List<EventSummary> list) {
        super(context, list);
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.item_home_eventsummary,null);
            holder=new ViewHolder();
            holder.image_title= (ImageView) convertView.findViewById(R.id.item_home_eventsummary);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        holder.image_title.setBackgroundResource(list.get(position).getResID());
        return convertView;
    }

    private class ViewHolder{
        ImageView image_title;
    }
}
