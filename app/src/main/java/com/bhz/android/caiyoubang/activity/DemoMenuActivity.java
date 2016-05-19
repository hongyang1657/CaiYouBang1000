package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.adapter.MenuDemoAdapter;
import com.bhz.android.caiyoubang.utils.RunningTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 各种菜谱列表activity
 * Created by Administrator on 2016/5/3
 */
public class DemoMenuActivity extends Activity{
    RunningTime runningTime = new RunningTime();
    String[] nameList;//获取的菜谱名字
    String[] imageUrlList;//获取的菜谱主图片
    String[] ingredientsList;//获取的菜谱配料
    int menuId;
    String searchText;
    ListView listView;
    TextView tvTitle;
    ImageView imgBack;
    MenuDemoAdapter adapter;

    String MenuName;
    String MenuAbstract;
    String MenuStuff;
    String MenuTips;
    String MenuimageHead;
    int CDKEY;//识别码，识别是从哪个activity跳转过来的
    Intent intent;
    String url;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_layout);
        getIntentInfo();
        init();
    }
    public void init(){
        tvTitle = (TextView) findViewById(R.id.tv_title);
        tvTitle.setText(title);
        imgBack = (ImageView) findViewById(R.id.iv_back1);
        imgBack.setOnClickListener(backListener);
        listView = (ListView) findViewById(R.id.list_demo);
        adapter = new MenuDemoAdapter(this,nameList,imageUrlList,ingredientsList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(listener);
    }
    private void getIntentInfo(){
        Intent intent = getIntent();
        nameList = intent.getStringArrayExtra("name");
        imageUrlList = intent.getStringArrayExtra("image");
        ingredientsList = intent.getStringArrayExtra("content");
        menuId = intent.getIntExtra("menuId",1);//得到菜谱url的id值
        searchText = intent.getStringExtra("searchText");
        CDKEY = intent.getIntExtra("CDKEY",100);//识别
        title = intent.getStringExtra("title");
        Log.i("result", "getIntentInfo: ---------"+title);
    }

    //设置item点击，展示菜单详情
    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            runningTime.runningTimeProgressDialog(DemoMenuActivity.this);
            getOkhttpData(position);
        }
    };
    public void getOkhttpData(final int position){
        intent = new Intent(DemoMenuActivity.this,MenuActivity.class);
        if (CDKEY==1){
            url = "http://apis.juhe.cn/cook/index?cid="+menuId+"&dtype=&pn=&rn=&format=1&key=418bc6e82cd8480c3acae6abeba5f2c5";
        }else if (CDKEY==2){
            url = "http://apis.juhe.cn/cook/query.php?menu="+searchText+"&dtype=&pn=&rn=&albums=&=&key=418bc6e82cd8480c3acae6abeba5f2c5";
        }
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(DemoMenuActivity.this, "网络获取失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONObject result = jsonObject.getJSONObject("result");
                    JSONArray data = result.getJSONArray("data");
                    JSONObject obj = data.getJSONObject(position);
                    MenuName = obj.getString("title");//解析得到菜谱名
                    MenuAbstract = obj.getString("imtro");//解析得到菜谱简介
                    MenuStuff = obj.getString("ingredients")+obj.getString("burden");//解析得到菜谱用料
                    MenuTips = obj.getString("tags");//解析得到小贴士
                    MenuimageHead = obj.getString("albums");//解析得到图片uri
                    MenuimageHead = MenuimageHead.replaceAll("\\]|\"|\\[|\\\\","");

                    JSONArray stepsArray = obj.getJSONArray("steps");
                    String[] imgList = new String[stepsArray.length()];//步骤图片url数组
                    String[] stepList = new String[stepsArray.length()];//步骤说明数组
                    for (int i=0;i<stepsArray.length();i++){
                        JSONObject step = stepsArray.getJSONObject(i);
                        String img = step.getString("img");//步骤图片
                        String text = step.getString("step");//步骤内容
                        img = img.replaceAll("\\]|\"|\\[|\\\\","");
                        imgList[i] = img;
                        stepList[i] = text;
                    }
                    intent.putExtra("MenuName",MenuName);
                    intent.putExtra("MenuAbstract",MenuAbstract);
                    intent.putExtra("MenuStuff",MenuStuff);
                    intent.putExtra("MenuTips",MenuTips);
                    intent.putExtra("MenuimageHead",MenuimageHead);
                    intent.putExtra("imgList",imgList);
                    intent.putExtra("stepList",stepList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runningTime.progressDialog.dismiss();
                startActivity(intent);
            }
        });
    }
    View.OnClickListener backListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };
}
