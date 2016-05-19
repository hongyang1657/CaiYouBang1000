package com.bhz.android.caiyoubang.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.activity.CreateMenuActivity;
import com.bhz.android.caiyoubang.info.CreateMenuStepInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.support.v4.app.ActivityCompat.startActivity;
import static android.support.v4.app.ActivityCompat.startActivityForResult;


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


    public HyBaseAdapter(CreateMenuActivity activity,Context context,List<CreateMenuStepInfo> list,Uri stepImageUri) {
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
        holder.imageStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "",Toast.LENGTH_SHORT).show();
                File fileImage = new File(Environment.getExternalStorageDirectory(),"stepImage.jpg");
                try {
                    if (fileImage.exists()){
                        fileImage.delete();
                    }
                    fileImage.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stepImageUri = Uri.fromFile(fileImage);
                //跳转相机
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,stepImageUri);
                activity.startActivityForResult(intent,10);

            }
        });


        return convertView;
    }

    public void addInfo(CreateMenuStepInfo stepInfo){
         if(list==null){
             list = new ArrayList<>();
         }
         list.add(stepInfo);
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
