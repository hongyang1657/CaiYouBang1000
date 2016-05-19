package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.bhz.android.caiyoubang.MainActivity;
import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.adapter.GuideAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/7.
 */
public class GuideActivity extends Activity {
    Gallery gallery_guide;
    Button btn_start;
    RadioGroup radioGroup_guide;
    List<Integer> list;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_guidepage);
        init();
    }



    private void init() {
        sp=getSharedPreferences("app", Context.MODE_PRIVATE);
        gallery_guide= (Gallery) findViewById(R.id.guide_gallery);
        btn_start= (Button) findViewById(R.id.guide_startbutton);
        radioGroup_guide= (RadioGroup) findViewById(R.id.guide_radiogroup);
        list=new ArrayList<>();
        setListData();
        GuideAdapter adapter=new GuideAdapter(this,list);
        gallery_guide.setAdapter(adapter);
        gallery_guide.setOnItemSelectedListener(select);
        radioGroup_guide.setOnCheckedChangeListener(checked);
        btn_start.setOnClickListener(click);
    }

    private void setListData() {  //装填引导页信息
        list.add(R.mipmap.guide1);
        list.add(R.mipmap.guide2);
        list.add(R.mipmap.guide3);
    }
//gallery可以带动radiogroup但是反过来不可以，radiogroup设计为不可点击。当进入最后一张图片时显示进入主页的按钮
    AdapterView.OnItemSelectedListener select=new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            RadioButton rb= (RadioButton) radioGroup_guide.getChildAt(position);
            rb.setChecked(true);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    RadioGroup.OnCheckedChangeListener checked=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if(checkedId==R.id.guide_choose3){
                radioGroup_guide.setVisibility(View.INVISIBLE);
                btn_start.setVisibility(View.VISIBLE);
            }else if(checkedId==R.id.guide_choose1||checkedId==R.id.guide_choose2){
                radioGroup_guide.setVisibility(View.VISIBLE);
                btn_start.setVisibility(View.INVISIBLE);
            }
        }
    };

    View.OnClickListener click=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent=new Intent(GuideActivity.this, MainActivity.class);
            SharedPreferences.Editor editor=sp.edit();
            editor.putBoolean("firstlogin",false);
            editor.commit();
            startActivity(intent);
            finish();
        }
    };
}
