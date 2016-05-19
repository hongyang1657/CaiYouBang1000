package com.bhz.android.caiyoubang.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhz.android.caiyoubang.R;


/**
 * Created by Administrator on 2016/4/20.
 */
public class SystemSetFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view=inflater.inflate(R.layout.item_fragment_systemset,null);
        return view;
    }
}
