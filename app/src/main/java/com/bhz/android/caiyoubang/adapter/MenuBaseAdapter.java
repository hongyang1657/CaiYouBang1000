package com.bhz.android.caiyoubang.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.info.MenuStepInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class MenuBaseAdapter extends BaseAdapter{
    Context context;
    LayoutInflater inflater;
    String[] imgList;
    String[] stepList;

    public MenuBaseAdapter(Context context,String[] imgList,String[] stepList) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.imgList = imgList;
        this.stepList = stepList;
    }

    @Override
    public int getCount() {
        return stepList.length;
    }

    @Override
    public Object getItem(int position) {
        return stepList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.method_layout,null);
            holder = new ViewHolder();
            holder.stepNumber = (TextView) convertView.findViewById(R.id.tv_step_number1);
            holder.stepImage = (ImageView) convertView.findViewById(R.id.image_step_image1);
            holder.stepText = (TextView) convertView.findViewById(R.id.et_step_content1);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        holder.stepNumber.setText(""+(position+1));
        holder.stepText.setText(stepList[position]);

        Picasso.with(context).load(imgList[position].replaceAll("\\]|\"|\\[|\\\\","")).into(holder.stepImage);
        return convertView;
    }

    class ViewHolder{
        TextView stepNumber;
        ImageView stepImage;
        TextView stepText;
    }

}
