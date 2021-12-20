package com.mba.busapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

/**
 * 버스 노선도 화면
 */
public class BusRouteActivity extends AppCompatActivity implements OnMapReadyCallback {
    private NaverMapManager mapManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busroute);

        // 네이버맵 Listener 연결
        MapView mapView = findViewById(R.id.busroute_navermap);
        mapView.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mapManager = new NaverMapManager(naverMap, this);

        mapManager.setCameraPosition(new LatLng(37.233972549267705, 127.18874893910944));
        // mapManager.enableLocationButton(); 문제있네...

    }
}
