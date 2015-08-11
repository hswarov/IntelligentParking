package com.hswarov.intelligentparking.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.hswarov.intelligentparking.R;
import com.hswarov.intelligentparking.app.BaseActivity;
import com.hswarov.intelligentparking.widget.TextURLView;

public class LoginActivity extends BaseActivity {

    TextURLView mTUVForget = null;
    TextURLView mTUVRegister = null;
    EditText mETName = null;
    EditText mETPw = null;
    Button btnLogin=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initWidget();
        initTvURL();
        initEvent();
    }

    private void initWidget() {
        btnLogin = (Button) this.findViewById(R.id.Login_btn);
        mTUVForget = (TextURLView) findViewById(R.id.login_forget_password);
        mTUVRegister = (TextURLView) findViewById(R.id.login_register);
        mETName=(EditText)this.findViewById(R.id.login_username);
        mETPw=(EditText)this.findViewById(R.id.login_pw);;
    }

    private void initTvURL() {
        mTUVForget.setText(R.string.login_forget_password);
        mTUVRegister.setText(R.string.login_register);
    }

    private void initEvent() {

    }


}
