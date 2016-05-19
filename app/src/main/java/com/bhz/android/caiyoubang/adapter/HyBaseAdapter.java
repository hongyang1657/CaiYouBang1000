package com.bhz.android.caiyoubang.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.activity.CreateMenuActivity;
import com.bhz.android.caiyoubang.info.CreateMenuStepInfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * Created by Administrator on 2016/4/13.
 */
public class HyBaseAdapter extends BaseAdapter{
    int i;//上传菜谱步骤数
    Context context;
    List<CreateMenuStepInfo> list;
    LayoutInflater inflater;
    CreateMenuActivity activity;
    String[] contentList;
    Uri stepImageUri;
    Bitmap[] bitmaps = new Bitmap[10];


    public HyBaseAdapter(CreateMenuActivity activity, Context context, List<CreateMenuStepInfo> list, Uri stepImageUri) {
        this.stepImageUri = stepImageUri;
        this.activity = activity;
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list==null?0:list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.step_layout,null);
            holder.tvStepNumber = (TextView) convertView.findViewById(R.id.tv_step_number);
            holder.imageStep = (ImageView) convertView.findViewById(R.id.image_step_image);
            holder.etStepContent = (EditText) convertView.findViewById(R.id.et_step_content);
            convertView.setTag(holder);
        }

        holder = (ViewHolder) convertView.getTag();
        i = position+1;
        holder.tvStepNumber.setText(""+i);//设置步骤数目
        contentList = new String[i];
        contentList[position] = holder.etStepContent.getText().toString();//----------------需要入库
        //给步骤图片设点击事件
        holder.imageStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转相册
                File outputImage = new File(Environment.getExternalStorageDirectory(), "outputImage"+position+".jpg");
                try {
                    if (outputImage.exists()) {
                        outputImage.delete();
                    }
                    outputImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stepImageUri = Uri.fromFile(outputImage);

                Intent intent1 = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent1.setType("image/*");
                intent1.putExtra("crop", true);
                intent1.putExtra("scale", true);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, stepImageUri);
                activity.startActivityForResult(intent1,23);
            }
        });

        if (bitmaps[position]!=null){
            holder.imageStep.setImageBitmap(bitmaps[position]);
        }

        return convertView;
    }

    public void addInfo(CreateMenuStepInfo stepInfo){
         if(list==null){
             list = new ArrayList<>();
         }
         list.add(stepInfo);
        notifyDataSetChanged();
    }

    public void addImage(Bitmap[] bitmaps){
        this.bitmaps = bitmaps;
        notifyDataSetChanged();
    }

    public final class ViewHolder{
        public TextView tvStepNumber;
        public ImageView imageStep;
        public EditText etStepContent;
    }

    public String[] getContentList() {
        return contentList;
    }
}
