package com.wisesignsoft.OperationManagement.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.hyphenate.easeui.domain.EaseUser;
import com.wisesignsoft.OperationManagement.BaseFragment;
import com.wisesignsoft.OperationManagement.R;
import com.wisesignsoft.OperationManagement.db.MySharedpreferences;
import com.wisesignsoft.OperationManagement.db.UserDataManager;
import com.wisesignsoft.OperationManagement.net.RequestTask;
import com.wisesignsoft.OperationManagement.net.request.RequestYxyw;
import com.wisesignsoft.OperationManagement.net.response.QueryAllValidUsersResponse;
import com.wisesignsoft.OperationManagement.ui.activity.ChatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ycs on 2016/11/18.
 */

public class MapFragment extends BaseFragment {

    private MapView mapView;
    private BaiduMap baiduMap;
    private List<QueryAllValidUsersResponse.ReturnValueBean> returnValue;
    private BitmapDescriptor bd = BitmapDescriptorFactory
            .fromResource(R.mipmap.usertwo);
    private BitmapDescriptor bd2 = BitmapDescriptorFactory
            .fromResource(R.mipmap.userone);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_map, container, false);
        mapView = (MapView) view.findViewById(R.id.mapview);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (baiduMap == null) {
            baiduMap = mapView.getMap();
            MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(12.0f);
            baiduMap.setMapStatus(msu);
        } else {
            baiduMap.clear();
        }
        baiduMap.setMyLocationConfigeration(new MyLocationConfiguration(MyLocationConfiguration.LocationMode.FOLLOWING, true, null));
        // 开启定位图层
        baiduMap.setMyLocationEnabled(true);
        // 定位初始化
        LocationClient mLocClient = new LocationClient(getContext());
        mLocClient.registerLocationListener(myListener);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            public boolean onMarkerClick(final Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                if (bundle != null) {
                    final String name = bundle.getString("name");
                    String content = bundle.getString("content");
                    final String userId = bundle.getString("userId");
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.map_marker, null);
                    TextView tv_title = (TextView) view.findViewById(R.id.tv_map_marker_name);
                    TextView tv_content = (TextView) view.findViewById(R.id.tv_map_marker_content);
                    if (!TextUtils.isEmpty(name)) {
                        tv_title.setText(name);
                    }
                    if (!TextUtils.isEmpty(content)&&!TextUtils.equals("null",content)) {
                        tv_content.setText(content);
                    }
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            EaseUser user = new EaseUser(userId);
                            user.setNick(name);
                            UserDataManager.getManager().setUser(user);
                            baiduMap.hideInfoWindow();
//                            Intent intent = new Intent(getActivity(), ChatActivity.class);
//                            intent.putExtra(EaseConstant.EXTRA_USER_ID, userId);
//                            startActivity(intent);
                            if (!userId.equals(MySharedpreferences.getUser().getUsername())) {
                                ChatActivity.startSelf(getActivity(), name, userId);
                            }
                        }
                    });
                    LatLng ll = marker.getPosition();
                    InfoWindow mInfoWindow = new InfoWindow(view, ll, -47);
                    baiduMap.showInfoWindow(mInfoWindow);
                }
                return true;
            }
        });
        request();
    }

    public void request() {
        List<String> list = new ArrayList<>();
        RequestYxyw.queryAllValidUsers(list, new RequestTask.ResultCallback<QueryAllValidUsersResponse>() {
            @Override
            public void onError(Exception e) {

            }

            @Override
            public void onResponse(QueryAllValidUsersResponse response) {
                if (response != null && response.getReturnValue() != null) {
                    returnValue = response.getReturnValue();
                    initOverlay();
                }
            }
        });
    }

    public void initOverlay() {
        for (QueryAllValidUsersResponse.ReturnValueBean bean : returnValue) {
            String userPosition = (String) bean.getUserPosition();
            if (!TextUtils.isEmpty(userPosition)) {
                String[] strings = userPosition.split("\\|");
                try {
                    LatLng ll = new LatLng(Double.parseDouble(strings[0]), Double.parseDouble(strings[1]));
                    MarkerOptions ooA;
                    if ("1".equals(bean.getUserState())) {
                        ooA = new MarkerOptions().position(ll).icon(bd).draggable(true);
                    } else {
                        ooA = new MarkerOptions().position(ll).icon(bd2).draggable(true);
                    }
                    Marker mMarker = (Marker) (baiduMap.addOverlay(ooA));
                    Bundle bundle = new Bundle();
                    bundle.putString("name", bean.getUserName());
                    bundle.putString("content", strings[2]);
                    bundle.putString("userId", bean.getUserAccountnum());
                    mMarker.setExtraInfo(bundle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public MyLocationListenner myListener = new MyLocationListenner();
    boolean isFirstLoc = true; // 是否首次定位

    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mapView == null) {
                return;
            }
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            baiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(18.0f);
                baiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        bd.recycle();
    }
}
