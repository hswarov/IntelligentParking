package com.hswarov.intelligentparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.hswarov.intelligentparking.MainActivity;
import com.hswarov.intelligentparking.R;
import com.hswarov.intelligentparking.app.BaseActivity;
import com.hswarov.intelligentparking.util.L;
import com.hswarov.intelligentparking.widget.ClearEditText;

public class SearchActivity extends BaseActivity {
    //UI相关变量
    private ImageView ivBackArrow;  //返回箭头
    private ClearEditText editText;  //搜索输入框
    private ImageView ivVoiceButton;  //语音搜索
    private Button btnSearch;         //搜索按钮
    private RelativeLayout relativeLayout;  //包含语音键和搜索键的RelativeLayout

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        initListener();
    }

    private void initView() {
        editText = (ClearEditText) findViewById(R.id.search_tab_ce);
        ivBackArrow = (ImageView) findViewById(R.id.search_tab_backArrow_iv);
        ivVoiceButton = (ImageView) findViewById(R.id.search_tab_voice_iv);
        btnSearch = (Button) findViewById(R.id.search_tab_search_btn);
        relativeLayout = (RelativeLayout) findViewById(R.id.search_tab_rl);
    }

    private void initListener() {
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        ivVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                L.i("ivVoiceButton点击");
            }
        });

        /**
         * 监听EditText，如果有输入则将voiceButton设置为searchButton，
         */
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().length() > 0) {
                    btnSearch.setVisibility(View.VISIBLE);
                    ivVoiceButton.setVisibility(View.GONE);
                    relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            100,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                }
                if (TextUtils.isEmpty(editText.getText())) {
                    btnSearch.setVisibility(View.GONE);
                    ivVoiceButton.setVisibility(View.VISIBLE);
                    relativeLayout.setLayoutParams(new LinearLayout.LayoutParams(
                            80,
                            ViewGroup.LayoutParams.MATCH_PARENT));
                }
            }
        });
    }

}
