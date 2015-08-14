package com.hswarov.intelligentparking;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatus;
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
    float maxZoomLevel;
    float minZoomLevel;

    MapView mMapView;
    BaiduMap mBaiduMap;
    private float currentZoomLevel;//当前缩放比例

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
    private Button ivInButton;
    private Button ivOutButton;
    private ImageView ivLoctionButton;
    private RelativeLayout rlLoc;
    private RelativeLayout rlUsercar;
    private ImageView ivRoadCondition;
    private ImageView ivMaplayers;
    private ImageView ivStreetScape;

    int numRoad = 0;
    int numStreet = 0;
    final int NORMAL_MODE = 0;
    final int COMPASS_MODE = 1;
    int mapMode = NORMAL_MODE;

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
        //UI初始化
        llMainTab = (LinearLayout) findViewById(R.id.main_tab_ll);
        llNearby = (LinearLayout) findViewById(R.id.main_tab_near_ll);
        llRoute = (LinearLayout) findViewById(R.id.main_tab_route_ll);
        llNavi = (LinearLayout) findViewById(R.id.main_tab_nav_ll);
        llMine = (LinearLayout) findViewById(R.id.main_tab_mine_ll);
        ivVoiceButton = (ImageView) findViewById(R.id.layout_search_tab_voice);
        rlLoc = (RelativeLayout) findViewById(R.id.main_loc_rl);
        rlUsercar = (RelativeLayout) findViewById(R.id.main_user_car);
        ivInButton = (Button) findViewById(R.id.main_zoom_in_btn);
        ivOutButton = (Button) findViewById(R.id.main_zoom_out_btn);
        ivLoctionButton = (ImageView) findViewById(R.id.main_loc_iv);
        ivRoadCondition = (ImageView) findViewById(R.id.main_road_condition_iv);
        ivMaplayers = (ImageView) findViewById(R.id.main_maplayers_iv);
        ivStreetScape = (ImageView) findViewById(R.id.main_street_scape_iv);
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
                locationModeChange();
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
                currentZoomLevel = mBaiduMap.getMapStatus().zoom;
                if (currentZoomLevel > minZoomLevel) {
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomOut());
                    ivInButton.setEnabled(true);
                } else {
                    Toast.makeText(getApplicationContext(), "已缩小至最低级别", Toast.LENGTH_SHORT).show();
                    ivOutButton.setEnabled(false);
                }
                L.i("ivOutButton点击");
            }
        });
        ivInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentZoomLevel = mBaiduMap.getMapStatus().zoom;
                if (currentZoomLevel < maxZoomLevel) {
                    MapStatusUpdateFactory.zoomIn();
                    mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomIn());
                    ivOutButton.setEnabled(true);
                } else {
                    Toast.makeText(getApplicationContext(), "已放大至最高级别", Toast.LENGTH_SHORT).show();
                    ivInButton.setEnabled(false);
                }
                L.i("ivInButton点击");
            }
        });
        ivRoadCondition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] roadConArray = {R.drawable.main_icon_roadcondition_off,
                        R.drawable.main_icon_roadcondition_on};
                ivRoadCondition.setImageResource(roadConArray[++numRoad % 2]);
                mBaiduMap.setTrafficEnabled(true);
                if (numRoad % 2 == 0) mBaiduMap.setTrafficEnabled(false);

                L.i("ivRoadCondition点击");
            }
        });
        ivMaplayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                L.i("ivMaplayers点击");
            }
        });
        ivStreetScape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int[] streetScaArray = {R.drawable.main_map_icon_streetscape,
                        R.drawable.main_map_icon_streetscape_selected};
                ivStreetScape.setImageResource(streetScaArray[++numStreet % 2]);

                L.i("ivStreetScape点击");
            }
        });
    }

    /**
     *改变定位模式：普通、罗盘
     */
    private void locationModeChange() {
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                if (mapMode == NORMAL_MODE) {
                    ivLoctionButton.setImageResource(R.drawable.main_icon_follow);
                    /*
                     重新定位
                    */
                    if (mLocationClient != null && mLocationClient.isStarted()) {
                        mLocationClient.requestLocation();
                    } else {
                        L.d("MainActivity", "locationClient is null or not started");
                    }
                    mapMode = COMPASS_MODE;
                } else {
                    switch (mCurrentMode) {
                        case NORMAL:
                            ivLoctionButton.setImageResource(R.drawable.main_icon_compass);
                            mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                            mBaiduMap
                                    .setMyLocationConfigeration(new MyLocationConfiguration(
                                            mCurrentMode, true, mCurrentMarker));
                            performZoom(18.0f);
                            performRotate(45);
                            performOverlook(-30);
                            break;
                        case COMPASS:
                            ivLoctionButton.setImageResource(R.drawable.main_icon_follow);
                            // requestLocButton.setText("普通");
                            mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                            mBaiduMap
                                    .setMyLocationConfigeration(new MyLocationConfiguration(
                                            mCurrentMode, true, mCurrentMarker));
                            performOverlook(30);
                            performRotate(0);
                            break;
                    }
                }
            }
        };
        rlLoc.setOnClickListener(btnClickListener);

    }

    /**
     * 处理缩放 sdk 缩放级别范围： [3.0,19.0]
     */
    private void performZoom(float zoomLevel) {
        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(zoomLevel);
        mBaiduMap.animateMapStatus(u);
    }

    /**
     * 处理俯视 俯角范围： -45 ~ 0 , 单位： 度
     */
    private void performOverlook(int overlookAngle) {
        MapStatus ms = new MapStatus.Builder(mBaiduMap.getMapStatus()).overlook(overlookAngle).build();
        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
        mBaiduMap.animateMapStatus(u);
    }
    /**
     * 处理旋转 旋转角范围： -180 ~ 180 , 单位：度 逆时针旋转
     */
    private void performRotate(int rotate) {
        MapStatus ms = new MapStatus.Builder(mBaiduMap.getMapStatus()).rotate(rotate).build();
        MapStatusUpdate u = MapStatusUpdateFactory.newMapStatus(ms);
        mBaiduMap.animateMapStatus(u);
    }

    private void userCenter() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void initView() {
        // 地图初始化
        mMapView = (MapView) findViewById(R.id.bmapView);
        MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(17.0f);//设置缩放级别
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(msu);
        maxZoomLevel = mBaiduMap.getMaxZoomLevel();
        minZoomLevel = mBaiduMap.getMinZoomLevel();
        /**
        设置比例尺位置
         */
        mBaiduMap.setOnMapLoadedCallback(new BaiduMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                mMapView.setScaleControlPosition(new Point(80, 791));
            }
        });

            /**
            让用户手势缩放和按钮缩放可以同步
            并解决有时候地图放的过大而无法手势缩小的问题。
            上上述用数字表示的最大最小缩放等级，改用BaiduMap的对象来获取最大最小值，
            如果超过了就将最大最小值赋给当前值
             */
        BaiduMap.OnMapStatusChangeListener mapStatusChangeListener = new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {
                currentZoomLevel = mBaiduMap.getMapStatus().zoom; //当前缩放比例
                if (currentZoomLevel >= maxZoomLevel) {
                    currentZoomLevel = maxZoomLevel;
                } else if (currentZoomLevel <= minZoomLevel) {
                    currentZoomLevel = minZoomLevel;
                }

                if (currentZoomLevel == maxZoomLevel) {
                    MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                            .zoomTo(currentZoomLevel);
                    mBaiduMap.animateMapStatus(mapStatusUpdate);//以动画方式更新地图状态，动画耗时 300 ms
                    ivInButton.setEnabled(false);
                } else if (currentZoomLevel == minZoomLevel) {
                    MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory
                            .zoomTo(currentZoomLevel);
                    mBaiduMap.animateMapStatus(mapStatusUpdate);
                    ivOutButton.setEnabled(false);
                } else {
                    if (!ivOutButton.isEnabled() || !ivInButton.isEnabled()) {
                        ivInButton.setEnabled(true);
                        ivOutButton.setEnabled(true);
                    }
                }
            }

            /**
             *当地图中心点改变时，显示定位按钮
             */
            @Override
            public void onMapStatusChange(MapStatus mapStatus) {
                ivLoctionButton.setImageResource(R.drawable.main_icon_location);
                mapMode = NORMAL_MODE;
            }

            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {

            }
        };
        mBaiduMap.setOnMapStatusChangeListener(mapStatusChangeListener);
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

    /**
     *设置定位SDK的定位方式
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
