package com.hswarov.intelligentparking.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hswarov.intelligentparking.R;
import com.hswarov.intelligentparking.bean.ChatMessage;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by hswarov on 2015/9/1.
 * In IntelligentParking
 */
public class ChatMessageAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<ChatMessage> mDatas;

    public ChatMessageAdapter(Context context, List<ChatMessage> mdatas) {
        mInflater = LayoutInflater.from(context);
        this.mDatas = mdatas;
    }

    @Override
    public int getCount() {

        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {

        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {

        return position;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage chatMessage = mDatas.get(position);
        if (chatMessage.getType() == ChatMessage.Type.INCOMING) {
            return 0;
        }
        return 1;
    }

    @Override
    public int getViewTypeCount() {

        return 2;
    }

    @SuppressLint("SimpleDateFormat")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ChatMessage chatMessage = mDatas.get(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            if (getItemViewType(position) == 0) {
                convertView = mInflater.inflate(R.layout.layout_item_from_msg, parent,
                        false);
                viewHolder = new ViewHolder();
                viewHolder.mDates = (TextView) convertView
                        .findViewById(R.id.id_from_msg_date);
                viewHolder.mMsg = (TextView) convertView
                        .findViewById(R.id.id_from_msg_info);

            } else {
                convertView = mInflater.inflate(R.layout.layout_item_to_msg, parent,
                        false);
                viewHolder = new ViewHolder();
                viewHolder.mDates = (TextView) convertView
                        .findViewById(R.id.id_to_msg_date);
                viewHolder.mMsg = (TextView) convertView
                        .findViewById(R.id.id_to_msg_info);
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 设置数据
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        viewHolder.mDates.setText(df.format(chatMessage.getDate()));
        viewHolder.mMsg.setText(chatMessage.getMsg());

        return convertView;
    }

    private final class ViewHolder {
        TextView mDates;
        TextView mMsg;

    }
}
