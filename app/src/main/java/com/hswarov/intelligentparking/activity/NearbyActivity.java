package com.hswarov.intelligentparking.activity;

import android.os.Bundle;

import com.hswarov.intelligentparking.R;
import com.hswarov.intelligentparking.app.BaseActivity;

import java.util.List;

import cn.trinea.android.view.autoscrollviewpager.AutoScrollViewPager;

public class NearbyActivity extends BaseActivity {

    private AutoScrollViewPager mViewPager;
    private List<Integer> imageIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby);
        initView();
    }

    private void initView() {
        mViewPager = (AutoScrollViewPager) findViewById(R.id.nearby_view_pager);
    }


}
