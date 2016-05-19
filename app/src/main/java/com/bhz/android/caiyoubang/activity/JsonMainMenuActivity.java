package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.adapter.MenuDemoAdapter;
import com.bhz.android.caiyoubang.info.MenuInfo;
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
 * Created by Administrator on 2016/5/3.
 */
public class JsonMainMenuActivity extends Activity{
    RunningTime runningTime = new RunningTime();
    int MENU_CID_CHUANCAI = 10;//川菜
    int MENU_CID_YUECAI = 11;//粤菜
    int MENU_CID_XIANGCAI = 12;//湘菜
    int MENU_CID_LUCAI = 13;//鲁菜
    int MENU_CID_JINGCAI = 14;//京菜
    int MENU_CID_ZHECAI = 102;//浙菜
    int MENU_CID_SUCAI = 104;//苏菜
    int MENU_CID_HUICAI = 105;//徽菜

    ImageView iv1;
    ImageView iv2;
    ImageView iv3;
    ImageView iv4;
    ImageView iv5;
    ImageView iv6;
    ImageView iv7;
    ImageView iv8;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_demo_layout);
        init();
    }
    public void init(){
        iv1 = (ImageView) findViewById(R.id.iv1);
        iv2 = (ImageView) findViewById(R.id.iv2);
        iv3 = (ImageView) findViewById(R.id.iv3);
        iv4 = (ImageView) findViewById(R.id.iv4);
        iv5 = (ImageView) findViewById(R.id.iv5);
        iv6 = (ImageView) findViewById(R.id.iv6);
        iv7 = (ImageView) findViewById(R.id.iv7);
        iv8 = (ImageView) findViewById(R.id.iv8);
    }

    //穿入点击的菜谱分类（川菜，粤菜等），获取json数据，解析
    public void getdataFromNet(final int menuId){

        intent = new Intent(this,DemoMenuActivity.class);
        //按标签检索菜谱的url   cid表示标签id     format表示是否返回步骤steps字段      rn表示返回数据条数，默认10条
        String url = "http://apis.juhe.cn/cook/index?cid="+menuId+"&dtype=&pn=&rn=&format=1&key=418bc6e82cd8480c3acae6abeba5f2c5";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(JsonMainMenuActivity.this, "网络获取失败", Toast.LENGTH_SHORT).show();
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
                    for (int i=0;i<data.length();i++){
                        JSONObject obj = data.getJSONObject(i);
                        String name = obj.getString("title");//获取菜谱名字
                        String content = obj.getString("ingredients");//获取菜谱用料
                        String image = obj.getString("albums");//获取菜谱图片
                        image = image.replaceAll("\\]|\"|\\[|\\\\","");//去掉图片uri中的多余字符
                        nameList[i] = name;
                        imageUrlList[i] = image;
                        ingredientsList[i] = content;
                    }
                    intent.putExtra("name",nameList);
                    intent.putExtra("image",imageUrlList);
                    intent.putExtra("content",ingredientsList);
                    intent.putExtra("menuId",menuId);
                    intent.putExtra("CDKEY",1);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                runningTime.progressDialog.dismiss();
                startActivity(intent);
            }
        });
    }


    //获取不同菜系的数据
    public void getMoreMenu(View v){
        runningTime.runningTimeProgressDialog(JsonMainMenuActivity.this);
        switch (v.getId()){
            case R.id.iv1:
                getdataFromNet(MENU_CID_CHUANCAI);
                break;
            case R.id.iv2:
                getdataFromNet(MENU_CID_YUECAI);
                break;
            case R.id.iv3:
                getdataFromNet(MENU_CID_XIANGCAI);
                break;
            case R.id.iv4:
                getdataFromNet(MENU_CID_LUCAI);
                break;
            case R.id.iv5:
                getdataFromNet(MENU_CID_JINGCAI);
                break;
            case R.id.iv6:
                getdataFromNet(MENU_CID_ZHECAI);
                break;
            case R.id.iv7:
                getdataFromNet(MENU_CID_SUCAI);
                break;
            case R.id.iv8:
                getdataFromNet(MENU_CID_HUICAI);
                break;
        }
    }

}
