package com.bhz.android.caiyoubang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhz.android.caiyoubang.R;
import com.squareup.picasso.Picasso;


/**
 * 各种菜谱列表
 * Created by Administrator on 2016/5/3.
 */
public class MenuDemoAdapter extends BaseAdapter{
    Context context;
    LayoutInflater inflater;
    String[] nameList;//菜谱名字
    String[] imageUrlList;//菜谱图片url
    String[] ingredientsList;//菜谱配料

    public MenuDemoAdapter(Context context, String[] nameList,String[] imageUrlList,String[] ingredientsList) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.nameList = nameList;
        this.imageUrlList = imageUrlList;
        this.ingredientsList = ingredientsList;
    }

    @Override
    public int getCount() {
        return nameList==null?0:nameList.length;
    }

    @Override
    public Object getItem(int position) {
        return nameList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.menu_list_demo_layout,null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.iv_demoimage);
            holder.textView = (TextView) convertView.findViewById(R.id.tv_name);
            holder.textViewIngredients = (TextView) convertView.findViewById(R.id.tv_content);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.textView.setText(nameList[position]);
        holder.textViewIngredients.setText(ingredientsList[position]);
        Picasso.with(context).load(imageUrlList[position]).into(holder.imageView);
        return convertView;
    }
    private class ViewHolder{
        ImageView imageView;
        TextView textView;
        TextView textViewIngredients;
    }
}
