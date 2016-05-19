package com.bhz.android.caiyoubang.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * 耗时操作时弹出progressDialog工具类
 * Created by Administrator on 2016/5/12.
 */
public class RunningTime {
    public ProgressDialog progressDialog;

    public void runningTimeProgressDialog(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Please wait...");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
    }
}
