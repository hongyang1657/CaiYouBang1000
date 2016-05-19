package com.bhz.android.caiyoubang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.data.MyRecipe;

import java.util.List;

/**
 * Created by Administrator on 2016/5/10.
 */
public class MyRecipeAdapter extends MyBaseAdapter {
    Context context;
    List<MyRecipe> list;
    LayoutInflater inflater;

    public MyRecipeAdapter(Context context, List list) {
        super(context, list);
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_myrecipe, null);
            holder = new ViewHolder();
            holder.image = (ImageView) convertView.findViewById(R.id.my_myrecipe);
            holder.tv_name = (TextView) convertView.findViewById(R.id.my_myrecipename);
            holder.tv_time = (TextView) convertView.findViewById(R.id.my_myrecipetime);
            holder.tv_content = (TextView) convertView.findViewById(R.id.my_myrecipecontent);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.image.setBackgroundResource(R.mipmap.ic_launcher);
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_time.setText(showtime(list.get(position).getTime()));
        holder.tv_content.setText(list.get(position).getContent());
        return convertView;
    }

    private class ViewHolder {
        ImageView image;
        TextView tv_name;
        TextView tv_time;
        TextView tv_content;
    }
    private String showtime(String s){
        return s;
    }
}
