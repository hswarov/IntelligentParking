package com.hswarov.intelligentparking;

import android.test.AndroidTestCase;

import com.hswarov.intelligentparking.util.HttpUtils;
import com.hswarov.intelligentparking.util.L;

/**
 * Created by hswarov on 2015/9/1.
 * In IntelligentParking
 */
public class TestHttpUtils extends AndroidTestCase {
    public void testSendInfo() {
        String res = HttpUtils.doGet("你好");
        L.e("TAG", res);
        res = HttpUtils.doGet("这里是IP智能停车");
        L.e("TAG", res);
        res = HttpUtils.doGet("很高兴为你服务");
        L.e("TAG", res);
        res = HttpUtils.doGet("有什么可以帮助你的吗？");
        L.e("TAG", res);
    }

}
