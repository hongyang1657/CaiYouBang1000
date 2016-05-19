package com.bhz.android.caiyoubang.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.activity.DemoMenuActivity;
import com.bhz.android.caiyoubang.activity.EventForMoreActivity;
import com.bhz.android.caiyoubang.activity.SearchActivity;
import com.bhz.android.caiyoubang.adapter.EventSummaryAdapter;
import com.bhz.android.caiyoubang.adapter.HotAdapter;
import com.bhz.android.caiyoubang.adapter.TitleAdapter;
import com.bhz.android.caiyoubang.data.EventSummary;
import com.bhz.android.caiyoubang.data.ShareData;
import com.bhz.android.caiyoubang.utils.MyOKHttpUtils;
import com.bhz.android.caiyoubang.utils.RunningTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/4/19.
 */
public class HomeFragment extends Fragment implements MyOKHttpUtils.OKHttpHelper {
    RunningTime runningTime = new RunningTime();
    //系统设定按钮
    TextView tv_systemseting;
    //搜索栏
    Button btn_forsearch;
    //标题banner
    Gallery gallery_title;
    //活动banner
    Gallery gallery_event;
    //热门菜banner
    Gallery gallery_hot;
    //更多活动
    Button btn_eventformore;
    //更多热门菜
    Button btn_hotformore;
    //bannner相关
    RadioGroup group_title;
    //菜系选择按钮
    Button btn_chuan;
    Button btn_lu;
    Button btn_yue;
    Button btn_su;
    Button btn_zhe;
    Button btn_min;
    Button btn_xiang;
    Button btn_hui;
    //设置用fragment
    FragmentManager fragmentmanager;
    FragmentTransaction transaction;
    //判定是否isshow
    int systemsetjudge = 0;
    //当前Activity
    Activity homepage;
    List<EventSummary> list;
    List<EventSummary> randomlist;
    List<EventSummary> fulleventlist;
    List<ShareData> hotlist;
    Intent intent;
    Context context;
    OkHttpClient client;
    Handler handler = new Handler();
    HotAdapter hotadapter;
    MyOKHttpUtils helper;

    int MENU_CID_CHUANCAI = 10;//川菜
    int MENU_CID_YUECAI = 11;//粤菜
    int MENU_CID_XIANGCAI = 12;//湘菜
    int MENU_CID_LUCAI = 13;//鲁菜
    int MENU_CID_MINCAI = 101;//闽菜
    int MENU_CID_ZHECAI = 102;//浙菜
    int MENU_CID_SUCAI = 104;//苏菜
    int MENU_CID_HUICAI = 105;//徽菜


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.item_fragment_mainpage, null);
        init(view);
        return view;
    }

    //初始化控件
    private void init(View view) {
        helper = MyOKHttpUtils.getinit();
        View btnview = view;
        tv_systemseting = (TextView) view.findViewById(R.id.mainpage_home_systemset);
        group_title = (RadioGroup) view.findViewById(R.id.mainpage_home_gallerychoose);
        gallery_title = (Gallery) view.findViewById(R.id.mainpage_home_gallery);
        gallery_event = (Gallery) view.findViewById(R.id.mainpage_home_eventgallery);
        btn_forsearch = (Button) view.findViewById(R.id.mainpage_home_search);
        gallery_hot = (Gallery) view.findViewById(R.id.mainpage_home_recipegallery);
        btn_eventformore = (Button) view.findViewById(R.id.mainpage_home_moreevent);
        btn_hotformore = (Button) view.findViewById(R.id.mainpage_home_recipe);
        fragmentmanager = getChildFragmentManager();
        homepage = getActivity();
        initbutton(btnview);
        list = new ArrayList<>();
        randomlist = new ArrayList<>();
        hotlist = new ArrayList<>();
        fulleventlist = new ArrayList<>();//定义一个包括所有活动的list
        setList();
        fulleventlist = list;  //这还是一个包括所有活动的list
        setRandomList();    //这一步之后所有活动被随机分成两部分并填充
        sethotlist();        //设置热门菜list
        EventSummaryAdapter eventadapter = new EventSummaryAdapter(homepage, randomlist);
        TitleAdapter titleadapter = new TitleAdapter(homepage, list);
        hotadapter = new HotAdapter(homepage, hotlist);
        gallery_hot.setAdapter(hotadapter);
        gallery_event.setAdapter(eventadapter);
        gallery_title.setAdapter(titleadapter);
        gallery_title.setOnItemSelectedListener(titleselect);
        group_title.setOnCheckedChangeListener(titlegroupchoose);
        btn_eventformore.setOnClickListener(eventformore);
        btn_forsearch.setOnClickListener(search);
    }

    //通过网络获取热门菜并将图片解析为Drawable文件
    private void sethotlist() {
/*        for (int i = 0; i < 4; i++) {*/
            int id = (int) (Math.random() * 30000 + 1);
            String url = "http://apis.juhe.cn/cook/queryid";
            helper.dogetID(url, id);
            helper.excute(this);
    /*    }*/
    }


    View.OnClickListener search = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(homepage, SearchActivity.class);
            startActivity(intent);
        }
    };
    View.OnClickListener eventformore = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(homepage, EventForMoreActivity.class);
            Bundle bundle = new Bundle();

            startActivity(intent);
        }
    };

    private void setRandomList() {  //随机抽取四张图片作为活动图片装填
    /*    for (int i = 0; i < 4; i++) {*/
        int random = ((int) (Math.random() * list.size()));
        randomlist.add(list.get(random));
        list.remove(random);
    /*    }*/

    }

    //初始化按钮
    private void initbutton(View view) {
        btn_chuan = (Button) view.findViewById(R.id.button_chuan);
        btn_lu = (Button) view.findViewById(R.id.button_lu);
        btn_yue = (Button) view.findViewById(R.id.button_yue);
        btn_su = (Button) view.findViewById(R.id.button_su);
        btn_zhe = (Button) view.findViewById(R.id.button_zhe);
        btn_min = (Button) view.findViewById(R.id.button_min);
        btn_xiang = (Button) view.findViewById(R.id.button_xiang);
        btn_hui = (Button) view.findViewById(R.id.button_xiang);
        btn_chuan.setOnClickListener(caixi);
        btn_lu.setOnClickListener(caixi);
        btn_yue.setOnClickListener(caixi);
        btn_su.setOnClickListener(caixi);
        btn_zhe.setOnClickListener(caixi);
        btn_min.setOnClickListener(caixi);
        btn_xiang.setOnClickListener(caixi);
        btn_hui.setOnClickListener(caixi);
        btn_eventformore.setOnClickListener(jump);
        btn_hotformore.setOnClickListener(jump);
    }

    private void setList() {
        for (int i = 1; i <= 8; i++) {
            list.add(setEventSummary(i));
        }
    }

    private EventSummary setEventSummary(int i) {  //将图片RID填入对应list
        EventSummary eventsummary = new EventSummary();
        eventsummary.setResID(datachange(i));
        return eventsummary;
    }

    private Integer datachange(int i) {
        Integer resID = 0;
        switch (i) {
            case 1:
                resID = R.mipmap.event1;
                break;
            case 2:
                resID = R.mipmap.event2;
                break;
            case 3:
                resID = R.mipmap.event3;
                break;
            case 4:
                resID = R.mipmap.event4;
                break;
            case 5:
                resID = R.mipmap.event5;
                break;
            case 6:
                resID = R.mipmap.event6;
                break;
            case 7:
                resID = R.mipmap.event7;
                break;
            case 8:
                resID = R.mipmap.event8;
                break;
        }
        return resID;
    }

    //穿入点击的菜谱分类（川菜，粤菜等），获取json数据，解析
    public void getdataFromNet(final int menuId) {

        intent = new Intent(homepage, DemoMenuActivity.class);
        //按标签检索菜谱的url   cid表示标签id     format表示是否返回步骤steps字段      rn表示返回数据条数，默认10条
        String url = "http://apis.juhe.cn/cook/index?cid=" + menuId + "&dtype=&pn=&rn=&format=1&key=418bc6e82cd8480c3acae6abeba5f2c5";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(context, "网络获取失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String[] nameList;//菜谱名数组
                String[] imageUrlList;//菜谱图片url数组
                String[] ingredientsList;//菜谱用料数组
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject result = jsonObject.getJSONObject("result");
                    JSONArray data = result.getJSONArray("data");
                    nameList = new String[data.length()];
                    imageUrlList = new String[data.length()];
                    ingredientsList = new String[data.length()];
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);
                        String name = obj.getString("title");//获取菜谱名字
                        String content = obj.getString("ingredients");//获取菜谱用料
                        String image = obj.getString("albums");//获取菜谱图片
                        image = image.replaceAll("\\]|\"|\\[|\\\\", "");//去掉图片uri中的多余字符
                        nameList[i] = name;
                        imageUrlList[i] = image;
                        ingredientsList[i] = content;
                    }
                    intent.putExtra("name", nameList);
                    intent.putExtra("image", imageUrlList);
                    intent.putExtra("content", ingredientsList);
                    intent.putExtra("menuId", menuId);
                    intent.putExtra("CDKEY", 1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runningTime.progressDialog.dismiss();
                startActivity(intent);
            }
        });
    }

    //菜系按钮点击事件
    View.OnClickListener caixi = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            runningTime.runningTimeProgressDialog(homepage);
            switch (v.getId()) {
                case R.id.button_chuan:
                    getdataFromNet(MENU_CID_CHUANCAI);
                    break;
                case R.id.button_lu:
                    getdataFromNet(MENU_CID_LUCAI);
                    break;
                case R.id.button_yue:
                    getdataFromNet(MENU_CID_YUECAI);
                    break;
                case R.id.button_su:
                    getdataFromNet(MENU_CID_SUCAI);
                    break;
                case R.id.button_zhe:
                    getdataFromNet(MENU_CID_ZHECAI);
                    break;
                case R.id.button_min:
                    getdataFromNet(MENU_CID_MINCAI);
                    break;
                case R.id.button_xiang:
                    getdataFromNet(MENU_CID_XIANGCAI);
                    break;
                case R.id.button_hui:
                    getdataFromNet(MENU_CID_HUICAI);
                    break;
            }
        }
    };
    //更多信息跳转事件
    View.OnClickListener jump = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.mainpage_home_moreevent:
                    Intent intent = new Intent(homepage, EventForMoreActivity.class);
                    startActivity(intent);
                    break;
                case R.id.mainpage_home_morerecipe:
                    break;
            }
        }
    };


    //标题的gallery控制radiobutton的选择
    AdapterView.OnItemSelectedListener titleselect = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            RadioButton button = (RadioButton) group_title.getChildAt(i);
            button.setChecked(true);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };
    //标题的radiogroup控制gallery滑动
    RadioGroup.OnCheckedChangeListener titlegroupchoose = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            switch (i) {
                case R.id.mainpage_home_choose1:
                    gallery_title.setSelection(0);
                    break;
                case R.id.mainpage_home_choose2:
                    gallery_title.setSelection(1);
                    break;
                case R.id.mainpage_home_choose3:
                    gallery_title.setSelection(2);
                    break;
                case R.id.mainpage_home_choose4:
                    gallery_title.setSelection(3);
                    break;
            }
        }
    };

    @Override
    public void OnFailure(String s) {
        Toast.makeText(homepage, s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnResponse(String message) {
        try {
            Log.i("test", "OnResponse: " + message);
            JSONObject object = new JSONObject(message);
            JSONObject result = object.getJSONObject("result");
            Log.i("test", "OnResponse: " + result);
            JSONArray data = result.getJSONArray("data");
            Log.i("test", "OnResponse: " + data);
            JSONObject content = data.getJSONObject(0);
            Log.i("test", "OnResponse: " + content);
            String title = content.getString("title");
            Log.i("test", "OnResponse: " + title);
            JSONArray albums = content.getJSONArray("albums");
            String imageurl = albums.getString(0);
            Log.i("test", "OnResponse: " + imageurl);
            ShareData SD = new ShareData();
            SD.setTitle(title);
            SD.setImage_url(imageurl);
            hotlist.add(SD);
            Log.i("test", "OnResponse: " + hotlist.get(0).getTitle());
            hotadapter.refresh();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeDrawable(String url) {

    }
}
