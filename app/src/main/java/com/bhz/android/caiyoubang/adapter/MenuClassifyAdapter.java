package com.bhz.android.caiyoubang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bhz.android.caiyoubang.R;

/**
 * 一级分类查询列表adapter
 * Created by Administrator on 2016/5/7.
 */
public class MenuClassifyAdapter extends BaseAdapter{
    LayoutInflater inflater;
    Context context;
    String[] menuClassify = {"菜式菜品","菜系","时令食材","功效","场景","工艺口味","菜肴","主食","西点"
            ,"汤羹饮品","其他菜品","人群","疾病","畜肉类","禽蛋类","水产类","蔬菜类","水果类","米面豆乳类"
            ,"日常","节日","节气","基本工艺","其他工艺","基本口味","多元口味","水果味","调味料"};//一级分类
    int mSelect;

    public MenuClassifyAdapter(Context context) {
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return menuClassify.length;
    }

    @Override
    public Object getItem(int position) {
        return menuClassify[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.classify_adapter_layout,null);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.tv_menu_name);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
            holder.textView.setText(menuClassify[position]);
        if (mSelect==position){
            convertView.setBackgroundResource(R.color.colorLightGray);//选中项背景颜色
        }else {
            convertView.setBackgroundResource(R.color.colorWhite);//未选中的背景颜色（白色）
        }
        return convertView;
    }

    public void changeSelected(int position){
        if (position!=mSelect){
            mSelect = position;
            notifyDataSetChanged();
        }
    }

    class ViewHolder{
        TextView textView;
    }
}
