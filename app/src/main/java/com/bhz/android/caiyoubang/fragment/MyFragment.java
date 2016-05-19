package com.bhz.android.caiyoubang.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.activity.UserEditDataActivity;
import com.bhz.android.caiyoubang.activity.UserSetActivity;
import com.bhz.android.caiyoubang.adapter.MyRecipeAdapter;
import com.bhz.android.caiyoubang.data.MyRecipe;
import com.bhz.android.caiyoubang.db.DBOperator;
import com.bhz.android.caiyoubang.db.MyDbHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/4/19.
 */
public class MyFragment extends Fragment {
    EditText et_epsode;
    RadioGroup group_mychoice;
    ListView list_my;
    TextView tv_tip;
    TextView tv_set;
    List<MyRecipe> list;
    ImageView imgUserPhoto;

    SharedPreferences sharedPreferences;

    int loginType;
    String userId;
    String userName;
    MyDbHelper myDbHelper;
    SQLiteDatabase database;
    DBOperator operator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.item_fragment_my, container, false);
        init(view);
        initUserData();
        tv_set.setOnClickListener(click);
        imgUserPhoto.setOnClickListener(click);
        return view;
    }

    private void initUserData(){
        myDbHelper = new MyDbHelper(getActivity());
        operator = new DBOperator(getActivity());
        sharedPreferences = getActivity().getSharedPreferences("app", Context.MODE_PRIVATE);
        String imgQQPhotUrl = sharedPreferences.getString("QQIcon",null);
        userName = sharedPreferences.getString("QQNAME", null);
        userId=sharedPreferences.getString("USERID",null);
        loginType=sharedPreferences.getInt("LoginType",0);
        if(imgQQPhotUrl!=null) {
            Picasso.with(getActivity()).load(imgQQPhotUrl).into(imgUserPhoto);
        }
    }

    private void init(View view) {
        et_epsode = (EditText) view.findViewById(R.id.mainpage_my_epsode);
        group_mychoice = (RadioGroup) view.findViewById(R.id.mainpage_my_option);
        list_my = (ListView) view.findViewById(R.id.mainpage_my_optionlist);
        tv_tip= (TextView) view.findViewById(R.id.mainpage_my_tip);
        tv_set = (TextView) view.findViewById(R.id.mainpage_my_systemset);                     // 设置
        imgUserPhoto= (ImageView) view.findViewById(R.id.mainpage_my_icon);                    //头像
        group_mychoice.setOnCheckedChangeListener(mychoice);
        list=new ArrayList<>();
        setlist();
        MyRecipeAdapter adapter=new MyRecipeAdapter(getActivity(),list);
        list_my.setAdapter(adapter);
    }

    View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mainpage_my_systemset:
                    Intent intentSet = new Intent(getActivity(), UserSetActivity.class);
                    startActivity(intentSet);
                    break;
                case R.id.mainpage_my_icon:
                    Intent intentPhoto = new Intent(getActivity(), UserEditDataActivity.class);
                    startActivity(intentPhoto);
                    break;
            }
        }
    };

    private void setlist() {
        for(int i=0;i<10;i++){
            MyRecipe recipe=new MyRecipe();
            recipe.setName(i+"");
            recipe.setTime(System.currentTimeMillis()+"");
            recipe.setContent("fuck you"+i);
            list.add(recipe);
        }
    }

    RadioGroup.OnCheckedChangeListener mychoice = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch(checkedId){
            case R.id.mainpage_my_recipe:
                if (list==null){
                    tv_tip.setVisibility(View.VISIBLE);
                }
                break;
/*                        *//*case R.id.mainpage_my_memo:*//*
                break;*/
            case R.id.mainpage_my_favourite:
                break;
        }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        database = myDbHelper.getWritableDatabase();
        database = myDbHelper.getReadableDatabase();
        switch (loginType){
            case 1:
                operator.queryepsodeFromQQ(userId,et_epsode);
                break;
            case 2:
                operator.queryepsodeFromQQ(userName,et_epsode);
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        database.close();
    }
}
