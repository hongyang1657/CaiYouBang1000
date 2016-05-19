package com.bhz.android.caiyoubang.fragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.adapter.ShareGridAdapter;
import com.bhz.android.caiyoubang.data.ShareData;
import com.bhz.android.caiyoubang.utils.MyOKHttpUtils;


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
import com.huewu.pla.lib.MultiColumnListView;


/**
 * Created by Administrator on 2016/4/19.
 */
public class ShareFragment extends Fragment implements MyOKHttpUtils.OKHttpHelper {
    MultiColumnListView listview;
    List<ShareData> list;
    ShareGridAdapter adapter;
    MyOKHttpUtils httpUtils;
    static String url = "http://apis.juhe.cn/cook/queryid";
    String title;
    String imageurl;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.item_fragment_share, container, false);
        init(view);
        return view;
    }

    private void init(View v) {
        httpUtils = MyOKHttpUtils.getinit();
        listview = (MultiColumnListView) v.findViewById(R.id.falllist);
        list = new ArrayList<>();
        setlist();
        adapter = new ShareGridAdapter(getActivity(), list);
        listview.setAdapter(adapter);
    }

    private void setlist() {
        for (int i = 0; i <10; i++) {
            int id = (int) (Math.random() * 30000 + 1);
            httpUtils.dogetID(url, id);
            httpUtils.excute(this);
        }
    }

    @Override
    public void OnFailure(String s) {
        Toast.makeText(getActivity(), s, Toast.LENGTH_LONG).show();
    }

    @Override
    public void OnResponse(String message) {
        try {
            JSONObject object = new JSONObject(message);
            JSONObject result = object.getJSONObject("result");
            JSONArray data = result.getJSONArray("data");
            JSONObject content = data.getJSONObject(0);
            this.title = content.getString("title");
            JSONArray albums = content.getJSONArray("albums");
            this.imageurl = albums.getString(0);
            ShareData SD = new ShareData();
            SD.setTitle(title);
            SD.setImage_url(imageurl);
            list.add(SD);
            adapter.refresh();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeDrawable(String url) {

    }

}
