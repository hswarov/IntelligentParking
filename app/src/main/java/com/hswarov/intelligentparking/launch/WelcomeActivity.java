package com.hswarov.intelligentparking.launch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;

import com.hswarov.intelligentparking.MainActivity;
import com.hswarov.intelligentparking.R;
import com.hswarov.intelligentparking.app.BaseActivity;

public class WelcomeActivity extends BaseActivity {
    protected static final String TAG = "WelcomeActivity";
    private Context mContext;
    private ImageView mImageView;
    private SharedPreferences mSP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        mContext = this;
        findView();
        init();
    }

    /*
     * 跳到主界面
     */
    private void init() {
        mImageView.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(mContext, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 50);
    }

    private void findView() {
        mImageView = (ImageView) findViewById(R.id.iv_welcome);
    }


}
