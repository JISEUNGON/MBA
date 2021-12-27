package com.mba.busapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/**
 * 버스 노선도 화면
 */
public class BusRouteActivity extends AppCompatActivity implements OnMapReadyCallback {
    private NaverMapManager mapManager;
    private SlidingUpPanelLayout slidingUpPanelLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busroute);

        // 네이버맵 Listener 연결
        MapView mapView = findViewById(R.id.busroute_navermap);
        mapView.getMapAsync(this);

        // sliding panel 설정
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.busroute_slidingpanel);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mapManager = new NaverMapManager(naverMap, this);
        mapManager.setCameraPosition(new LatLng(37.233972549267705, 127.18874893910944), 15);
        // mapManager.enableLocationButton(); 문제있네...

    }

    public void btn_newActivity(View v) {
        Log.d("[Button]", "netActivity Clicked!");
        startActivity(new Intent(getApplicationContext(), BusTableView.class));
    }

    public void btn_slidingView(View v) {
        Log.d("[Button]", "slidingView Clicked!");
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }
}
