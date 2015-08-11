package com.hswarov.intelligentparking.app;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.baidu.mapapi.SDKInitializer;
import com.hswarov.intelligentparking.util.L;

/**
 * Created by Administrator on 2015/8/4.
 */
public class IntelligentParkingApplication extends Application {

    public static String strVersion = "1.0.00";

    @Override
    public void onCreate() {
        super.onCreate();
        strVersion = getVersionNumber();
        //设置Debug模式
        L.isDebug = true;
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
    }


    private String getVersionNumber(){
        PackageManager packageManager = getPackageManager();
        PackageInfo packInfo = null;
        try {
            packInfo = packageManager.getPackageInfo(getPackageName(),0);
            String version = packInfo.versionName;
            return version;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
