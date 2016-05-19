package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.adapter.MenuClassifyAdapter;
import com.bhz.android.caiyoubang.adapter.MenuClassifyContentAdapter;
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
 * Created by Administrator on 2016/5/7.
 */
public class ClassifyActivity extends Activity {
    RunningTime runningTime = new RunningTime();
    String[] menuNameList;//分类菜谱名
    String[] menuIdList;//分类菜谱id

    ListView lvClassify;//菜谱分类标签
    ListView lvContent;//标签具体内容
    String url = "http://apis.juhe.cn/cook/category?parentid=&dtype=&key=418bc6e82cd8480c3acae6abeba5f2c5";
    MenuClassifyAdapter adapterFirst;
    MenuClassifyContentAdapter adapterSecond;
    JSONArray list;
    Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_classify_layout);
        init();
    }

    private void init() {
        //一级adapter
        lvClassify = (ListView) findViewById(R.id.list_classify);
        adapterFirst = new MenuClassifyAdapter(this);
        lvClassify.setAdapter(adapterFirst);
        lvClassify.setOnItemClickListener(listenerOne);//一级分类item点击事件

    }

    private void doJson(final int position) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(ClassifyActivity.this, "网络获取失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("result");
                    JSONObject obj = jsonArray.getJSONObject(position);//获取点击项的object
                    list = obj.getJSONArray("list");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject classify = list.getJSONObject(i);
                        menuNameList = new String[list.length()];
                        menuIdList = new String[list.length()];
                        menuNameList[i] = classify.getString("name");
                        menuIdList[i] = classify.getString("id");
                        Log.i("result", "onResponse:------ " + menuNameList[i]);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lvContent = (ListView) findViewById(R.id.list_content);
                        adapterSecond = new MenuClassifyContentAdapter(ClassifyActivity.this, menuNameList, menuIdList);
                        lvContent.setAdapter(adapterSecond);
                    }
                });

                runningTime.progressDialog.dismiss();
            }
        });
    }

    //一级分类item点击事件
    AdapterView.OnItemClickListener listenerOne = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            runningTime.runningTimeProgressDialog(ClassifyActivity.this);
            doJson(position);
            adapterFirst.changeSelected(position);//改变背景色
        }
    };


}
