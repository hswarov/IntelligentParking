package com.hswarov.intelligentparking.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.hswarov.intelligentparking.MainActivity;
import com.hswarov.intelligentparking.R;
import com.hswarov.intelligentparking.app.AppManager;
import com.hswarov.intelligentparking.app.BaseActivity;
import com.hswarov.intelligentparking.widget.MyDialog;

public class UserCenterActivity extends BaseActivity {

    private ImageButton ibBackb;
    private ImageButton ibBackw;
    private ImageButton ibMsgb;
    private ImageButton ibMsgw;
    private ScrollView mScrollview;
    private RelativeLayout rlExit;

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
        ibMsgw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCustomerService();
            }
        });
        ibMsgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toCustomerService();
            }
        });
        rlExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertExitDialog();
            }
        });
    }

    private void toCustomerService() {
        Intent intent = new Intent(UserCenterActivity.this, CustomerServiceActivity.class);
        startActivity(intent);
    }

    private void alertExitDialog() {
        MyDialog.Builder builder = new MyDialog.Builder(this);
        builder.setMessage(R.string.dialog_text);
        builder.setTitle(R.string.dialog_title);
        builder.setPositiveButton(R.string.dialog_determine, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                AppManager.getAppManager().AppExit();
            }
        });
        builder.setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.create().show();
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
        rlExit= (RelativeLayout) findViewById(R.id.usercenter_exit_rl);
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
