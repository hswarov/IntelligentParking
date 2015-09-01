package com.hswarov.intelligentparking.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hswarov.intelligentparking.R;
import com.hswarov.intelligentparking.adapter.ChatMessageAdapter;
import com.hswarov.intelligentparking.app.BaseActivity;
import com.hswarov.intelligentparking.bean.ChatMessage;
import com.hswarov.intelligentparking.util.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerServiceActivity extends BaseActivity {
    private ListView mMsgs;
    private ChatMessageAdapter mAdapter;
    private List<ChatMessage> mDates;

    private EditText mInputMsg;
    private TextView mSendMsg;


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            ChatMessage fromChatMessage = (ChatMessage) msg.obj;
            mDates.add(fromChatMessage);
            mAdapter.notifyDataSetChanged();
            mMsgs.setSelection(mDates.size() - 1);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_service);

        initView();
        initDatas();
        initListener();
    }

    private void initView() {
        mMsgs = (ListView) findViewById(R.id.customer_service_listview_msgs);
        mInputMsg = (EditText) findViewById(R.id.customer_service_et);
        mSendMsg = (TextView) findViewById(R.id.customer_service_tv);
    }

    private void initDatas() {
        mDates = new ArrayList<ChatMessage>();
        mDates.add(new ChatMessage("你好", ChatMessage.Type.INCOMING, new Date()));
        mAdapter = new ChatMessageAdapter(getApplicationContext(), mDates);
        mMsgs.setAdapter(mAdapter);
    }

    private void initListener() {
        mSendMsg.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final String toMsg = mInputMsg.getText().toString();
                if (TextUtils.isEmpty(toMsg)) {
                    Toast.makeText(CustomerServiceActivity.this, "消息不能为空！",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                ChatMessage toChatMessage = new ChatMessage();
                toChatMessage.setDate(new Date());
                toChatMessage.setMsg(toMsg);
                toChatMessage.setType(ChatMessage.Type.OUTCOMING);
                mDates.add(toChatMessage);
                mAdapter.notifyDataSetChanged();

                mInputMsg.setText("");
                mMsgs.setSelection(mDates.size() - 1);
                new Thread() {
                    public void run() {
                        if (isNetworkConnected(getApplicationContext())) {
                            ChatMessage fromMessage = HttpUtils
                                    .sendMessage(toMsg);
                            Message m = Message.obtain();
                            m.obj = fromMessage;
                            mHandler.sendMessage(m);
                        }else{
                            return;
                        }

                    };
                }.start();

            }
        });

    }

    /**
     * 判断当前网络连接状况
     *
     * @param context
     * @return
     */
    public boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager
                    .getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }



}
