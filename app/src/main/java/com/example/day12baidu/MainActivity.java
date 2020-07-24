package com.example.day12baidu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMapOptions;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.Overlay;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.PolylineOptions;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final LatLng GEO_SHANGHAI = new LatLng(31.227, 121.481);
    private boolean isPermissionRequested;
    private MapView bmapView;
    private BaiduMap baiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        requestPermission();
        initMarker();
        inithuixian();
    }

    private void inithuixian() {
        //构建折线点坐标
        LatLng p1 = new LatLng(39.97923, 116.357428);
        LatLng p2 = new LatLng(39.94923, 116.397428);
        LatLng p3 = new LatLng(39.97923, 116.437428);
        List<LatLng> points = new ArrayList<LatLng>();
        points.add(p1);
        points.add(p2);
        points.add(p3);

//设置折线的属性
        OverlayOptions mOverlayOptions = new PolylineOptions()
                .width(10)
                .color(0xAAFF0000)
                .points(points);
//在地图上绘制折线
//mPloyline 折线对象
        Overlay mPolyline = baiduMap.addOverlay(mOverlayOptions);
    }

    private void initMarker() {
//定义Maker坐标点
        final LatLng point = new LatLng(39.963175, 116.400244);
//构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_marka);
//构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .draggable(true)// 拖动
                .icon(bitmap);
//在地图上添加Marker，并显示
        baiduMap.addOverlay(option);

        baiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            //marker被点击时回调的方法
            //若响应点击事件，返回true，否则返回false
            //默认返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng position = marker.getPosition();
                double longitude = position.longitude;//经度
                double latitude = position.latitude;//纬度
                Toast.makeText(MainActivity.this, "经度 "+longitude +"维度 "+latitude, Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        baiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {

            //在Marker拖拽过程中回调此方法，这个Marker的位置可以通过getPosition()方法获取
            //marker 被拖动的Marker对象
            @Override
            public void onMarkerDrag(Marker marker) {
                //对marker处理拖拽逻辑
            }

            //在Marker拖动完成后回调此方法， 这个Marker的位可以通过getPosition()方法获取
            //marker 被拖拽的Marker对象
            @Override
            public void onMarkerDragEnd(Marker marker) {
                LatLng position = marker.getPosition();
                double longitude = position.longitude;
                double latitude = position.latitude;
                Toast.makeText(MainActivity.this, "经度 "+longitude +"维度 "+latitude, Toast.LENGTH_SHORT).show();
            }

            //在Marker开始被拖拽时回调此方法， 这个Marker的位可以通过getPosition()方法获取
            //marker 被拖拽的Marker对象
            @Override
            public void onMarkerDragStart(Marker marker) {

            }
        });


    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23 && !isPermissionRequested) {
            isPermissionRequested = true;
            ArrayList<String> permissionsList = new ArrayList<>();
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            };
            for (String perm : permissions) {
                if (PackageManager.PERMISSION_GRANTED != checkSelfPermission(perm)) {
                    permissionsList.add(perm);
                    // 进入到这里代表没有权限.
                }
            }

            if (permissionsList.isEmpty()) {
                return;
            } else {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), 0);
            }
        }
    }

    private void initView() {
        bmapView = (MapView) findViewById(R.id.bmapView);
        bmapView.setOnClickListener(this);

        baiduMap = bmapView.getMap();


       /* BaiduMapOptions options = new BaiduMapOptions();
        //设置地图模式为卫星地图
        options.mapType(BaiduMap.MAP_TYPE_SATELLITE);
        MapView mapView = new MapView(this, options);
        setContentView(bmapView);*/

       /* MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18.0f);
        baiduMap.setMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));*/

/*
//上海普通地图
        LatLng GEO_SHANGHAI = new LatLng(31.227, 121.481);
        MapStatusUpdate statusUpdate = MapStatusUpdateFactory.newLatLng(GEO_SHANGHAI);
        baiduMap.setMapStatus(statusUpdate);
*/
//卫星地图
  //      baiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

//开启交通图
 //       baiduMap.setTrafficEnabled(true);

//        baiduMap.setTrafficEnabled(true);
//        baiduMap.setCustomTrafficColor("#ffba0101", "#fff33131", "#ffff9e19", "#00000000");
////  对地图状态做更新，否则可能不会触发渲染，造成样式定义无法立即生效。
//        MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(13);
//        baiduMap.animateMapStatus(u);

        //开启热力图
     //   baiduMap.setBaiduHeatMapEnabled(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bmapView:

                break;
        }
    }
}
