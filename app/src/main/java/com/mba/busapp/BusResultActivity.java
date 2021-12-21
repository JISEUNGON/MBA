package com.mba.busapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

public class BusResultActivity extends AppCompatActivity implements OnMapReadyCallback {
    NaverMapManager naverMapManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busresult);

        // 네이버맵 Listener 연결
        MapView mapView = findViewById(R.id.busresult_navermap);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMapManager = new NaverMapManager(naverMap, this);
        naverMapManager.setCameraPosition(new LatLng(37.233972549267705, 127.18874893910944));

    }
}
