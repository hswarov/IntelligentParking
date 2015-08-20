package com.hswarov.intelligentparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ScrollView;

import com.hswarov.intelligentparking.MainActivity;
import com.hswarov.intelligentparking.R;
import com.hswarov.intelligentparking.app.BaseActivity;

public class UserCenterActivity extends BaseActivity {

    private ImageButton ibBackb;
    private ImageButton ibBackw;
    private ImageButton ibMsgb;
    private ImageButton ibMsgw;
    private ScrollView mScrollview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        initView();
        initListener();
    }

    private void initListener() {
        mScrollview.setOnTouchListener(new MyTouchListener());
        ibBackb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainActivity();
            }
        });
        ibBackw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToMainActivity();
            }
        });
    }

    private void backToMainActivity() {
        Intent intent = new Intent(UserCenterActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void initView() {
        ibBackb = (ImageButton) findViewById(R.id.usercenter_back_black_ib);
        ibBackw = (ImageButton) findViewById(R.id.usercenter_back_white_ib);
        ibMsgb = (ImageButton) findViewById(R.id.usercenter_message_black_ib);
        ibMsgw = (ImageButton) findViewById(R.id.usercenter_message_white_ib);
        mScrollview= (ScrollView) findViewById(R.id.usercenter_sv);
    }

    private class MyTouchListener implements View.OnTouchListener{

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int scrollY = v.getScrollY();
            if(scrollY != 0){
                ibBackb.setVisibility(View.VISIBLE);
                ibBackw.setVisibility(View.GONE);
                ibMsgw.setVisibility(View.GONE);
                ibMsgb.setVisibility(View.VISIBLE);
            }
            if(scrollY == 0){
                ibBackb.setVisibility(View.GONE);
                ibBackw.setVisibility(View.VISIBLE);
                ibMsgw.setVisibility(View.VISIBLE);
                ibMsgb.setVisibility(View.GONE);
            }
            return false;
        }
    }


}
