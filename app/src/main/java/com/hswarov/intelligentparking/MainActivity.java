package com.hswarov.intelligentparking;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.hswarov.intelligentparking.activity.LoginActivity;
import com.hswarov.intelligentparking.app.BaseActivity;
import com.hswarov.intelligentparking.util.L;

public class MainActivity extends BaseActivity {
    // 定位相关
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    //public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;

    MapView mMapView;
    BaiduMap mBaiduMap;

    // UI相关
    RadioGroup.OnCheckedChangeListener radioButtonListener;
    boolean isFirstLoc = true;// 是否首次定位

    //UI控件
    private LinearLayout llMainTab;
    private LinearLayout llNearby; //附近
    private LinearLayout llRoute;  //路线
    private LinearLayout llNavi;   //导航
    private LinearLayout llMine;   //我的
    private ImageView ivVoiceButton;
    private ImageView ivInButton;
    private ImageView ivOutButton;
    private ImageView ivLoctionButton;
    private RelativeLayout rlLoc;
    private RelativeLayout rlUsercar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
        initView();
        initWidget();
        initListener();
    }

    private void initWidget() {
        //Tab初始化
        llMainTab = (LinearLayout) findViewById(R.id.main_tab_ll);
        llNearby = (LinearLayout) findViewById(R.id.main_tab_near_ll);
        llRoute = (LinearLayout) findViewById(R.id.main_tab_route_ll);
        llNavi = (LinearLayout) findViewById(R.id.main_tab_nav_ll);
        llMine = (LinearLayout) findViewById(R.id.main_tab_mine_ll);
        ivVoiceButton = (ImageView) findViewById(R.id.layout_search_tab_voice);
        rlLoc = (RelativeLayout) findViewById(R.id.main_loc_rl);
        rlUsercar = (RelativeLayout) findViewById(R.id.main_usercar);
        ivInButton = (ImageView) findViewById(R.id.main_zoom_in);
        ivOutButton = (ImageView) findViewById(R.id.main_zoom_out);
        ivLoctionButton= (ImageView) findViewById(R.id.main_loc_iv);
    }

    private void initListener() {
        llNearby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                L.i("llNearby点击");
            }
        });

        llRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                L.i("llRoute点击");
            }
        });
        llNavi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                L.i("llNavi点击");
            }
        });
        llMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userCenter();
                L.i("llRoute点击");
            }
        });
        ivVoiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                L.i("ivVoiceButton点击");
            }
        });
        rlLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loctionModeChange();
                L.i("rlLoc点击");
            }
        });
        rlUsercar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                L.i("rlLoc点击");
            }
        });
        ivOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                L.i("ivOutButton点击");
            }
        });
        ivInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                L.i("ivInButton点击");
            }
        });
    }

    private void loctionModeChange() {
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                        ivLoctionButton.setImageResource(R.drawable.main_icon_follow);
                        //requestLocButton.setText("跟随");
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    case COMPASS:
                        ivLoctionButton.setImageResource(R.drawable.main_icon_location);
                       // requestLocButton.setText("普通");
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                    case FOLLOWING:
                        ivLoctionButton.setImageResource(R.drawable.main_icon_compass);
                       // requestLocButton.setText("罗盘");
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        mBaiduMap
                                .setMyLocationConfigeration(new MyLocationConfiguration(
                                        mCurrentMode, true, mCurrentMarker));
                        break;
                }
            }
        };
        rlLoc.setOnClickListener(btnClickListener);


    }

    private void userCenter() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void initView() {
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);//设置缩放级别
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(msu);
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化

        mLocationClient = new LocationClient(this);
        mLocationClient.registerLocationListener(myListener);
        // 隐藏logo
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls)) {
            child.setVisibility(View.INVISIBLE);
        }
        mMapView.showZoomControls(false);//隐藏缩放按钮
        initLocation();
        mLocationClient.start();
    }

    /*
    设设置定位SDK的定位方式
     */
    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }

    @Override
    protected void onDestroy() {
        // 退出时销毁定位
        mLocationClient.stop();
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mMapView = null;
        super.onDestroy();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }


    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null)
                return;
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }

        public void onReceivePoi(BDLocation poiLocation) {
        }

    }
}
