package com.bhz.android.caiyoubang.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhz.android.caiyoubang.R;
import com.bhz.android.caiyoubang.activity.TestActivity;
import com.bhz.android.caiyoubang.activity.UserLoginPageActivity;
import com.bhz.android.caiyoubang.activity.UserPhoneRegisterActivity;


/**
 * Created by Administrator on 2016/5/7.
 */
public class MyLogoutFragment extends Fragment {
    int accountjudge=0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.item_fragment_mywithoutlogin,null);
        init();
        return view;
    }
    private void init() {
        loginjudge(accountjudge);
    }
    private void loginjudge(int accountjudge){
        if(accountjudge==0){
            Intent intent=new Intent(getActivity(), UserLoginPageActivity.class);
            intent.putExtra("login","请输入账号密码");
            startActivity(intent);
        }
    }
}
