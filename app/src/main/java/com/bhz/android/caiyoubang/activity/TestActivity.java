package com.bhz.android.caiyoubang.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bhz.android.caiyoubang.R;


/**
 * Created by Administrator on 2016/5/7.
 */
public class TestActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testlayout);
        Intent intent=getIntent();
        String s=intent.getStringExtra("caixi");
        String s1=intent.getStringExtra("login");
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,s1,Toast.LENGTH_LONG).show();
    }
}
