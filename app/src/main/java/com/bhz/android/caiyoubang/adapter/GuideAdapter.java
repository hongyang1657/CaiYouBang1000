package com.bhz.android.caiyoubang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.bhz.android.caiyoubang.R;

import java.util.List;

/**
 * Created by Administrator on 2016/5/7.
 */
public class GuideAdapter extends MyBaseAdapter {
    Context context;
    List<Integer> list;
    LayoutInflater inflater;
    public GuideAdapter(Context context, List<Integer> list) {
        super(context, list);
        this.context=context;
        this.list=list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.item_guide,null);
            holder=new ViewHolder();
            holder.image= (ImageView) convertView.findViewById(R.id.guide_image);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        holder.image.setBackgroundResource(list.get(position));
        return convertView;
    }

    private class ViewHolder{
        ImageView image;
    }
}
