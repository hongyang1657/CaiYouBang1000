package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.utils.RunningTime;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 搜索菜谱界面
 * Created by Administrator on 2016/5/7.
 */
public class SearchActivity extends Activity{
    RunningTime runningTime = new RunningTime();
    AutoCompleteTextView autoCompleteTextView;
    ImageView ivBack;
    Button btSearch;

    String searchText;//搜索框中输入的内容转化的URL编码
    Intent intent;
    String text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        init();

    }
    private void init(){
        autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.et_search);
        ivBack = (ImageView) findViewById(R.id.img_back);
        btSearch = (Button) findViewById(R.id.bt_search);
        btSearch.setOnClickListener(searchListener);//搜索按钮监听
        ivBack.setOnClickListener(backToMain);
    }
    private void getJson(){
        intent = new Intent(SearchActivity.this,DemoMenuActivity.class);
        String url = "http://apis.juhe.cn/cook/query.php?menu="+searchText+"&dtype=&pn=&rn=&albums=&=&key=418bc6e82cd8480c3acae6abeba5f2c5";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(SearchActivity.this, "网络获取失败", Toast.LENGTH_SHORT).show();
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
                    intent.putExtra("CDKEY",2);
                    intent.putExtra("searchText",searchText);
                    intent.putExtra("title",text);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runningTime.progressDialog.dismiss();
                startActivity(intent);
            }
        });
    }

    View.OnClickListener searchListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //获取输入框内容，转化为UTF-8编码
            try {
                text = autoCompleteTextView.getText().toString();
                searchText = URLEncoder.encode(text,"UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            runningTime.runningTimeProgressDialog(SearchActivity.this);
            getJson();
        }
    };

    View.OnClickListener backToMain = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    public void topick(View view){
        switch (view.getId()){
            case R.id.bt1:
                pickToIntent("减肥");
                break;
            case R.id.bt2:
                pickToIntent("新疆菜");
                break;
            case R.id.bt3:
                pickToIntent("宵夜");
                break;
            case R.id.bt4:
                pickToIntent("西餐");
                break;
            case R.id.bt5:
                pickToIntent("水果");
                break;
            case R.id.bt6:
                pickToIntent("美容");
                break;
            case R.id.bt7:
                pickToIntent("早餐");
                break;
            case R.id.bt8:
                pickToIntent("烤箱");
                break;
            case R.id.bt9:
                pickToIntent("海鲜");
                break;
        }
    }
    private void pickToIntent(String text){
        try {
            searchText = URLEncoder.encode(text,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        runningTime.runningTimeProgressDialog(SearchActivity.this);
        getJson();
    }
}
