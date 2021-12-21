package com.mba.busapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

public class BusSearchActivity  extends AppCompatActivity implements OnMapReadyCallback {
    private NaverMapManager mapManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussearch);

        // 네이버맵 Listener 연결
        MapView mapView = findViewById(R.id.bussearch_navermap);
        mapView.getMapAsync(this);
    }

    /**
     * NaverMap callback 함수
     * @param naverMap
     */
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mapManager = new NaverMapManager(naverMap, this);

        mapManager.setCameraPosition(new LatLng(37.233972549267705, 127.18874893910944));
        mapManager.enableLocationButton();
//        mapManager.enableMarker_MjuStation();
        mapManager.enableMarker_DownTown();
//        mapManager.enablePoly_MjuStation();
        mapManager.enablePoly_DownTown();

    }

    /**
     * 버스 결과 화면으로 이동
     * @param v
     */
    public void btn_moveNext(View v) {
        startActivity(new Intent(getApplicationContext(), BusResultActivity.class));
    }
}
