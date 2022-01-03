package com.mba.busapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

public class BusSearchActivity  extends AppCompatActivity implements OnMapReadyCallback {
    private NaverMapManager mapManager;
    private int switch_counter;
    private Spinner spinner;
    private TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussearch);

        spinner = (Spinner) findViewById(R.id.spinner);
        textView = (TextView) findViewById(R.id.textView);

        // 네이버맵 Listener 연결
        MapView mapView = findViewById(R.id.bussearch_navermap);
        mapView.getMapAsync(this);
    }

    /**
     * NaverMap callback 함수
     * @param naverMap 네이버맵
     */
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mapManager = new NaverMapManager(naverMap, this);

        mapManager.setCameraPosition(new LatLng(37.233972549267705, 127.18874893910944), 15);
        mapManager.enableLocationButton();
//        mapManager.enableMarker_MjuStation();
        mapManager.enableMarker_DownTown();
//        mapManager.enablePoly_MjuStation();
        mapManager.enablePoly_DownTown();

    }

    /**
     * 버스 결과 화면으로 이동
     * @param v view
     */
    public void btn_moveNext(View v) {
        startActivity(new Intent(getApplicationContext(), BusResultActivity.class));
    }

    public void btn_switch(View v) {
        ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams)spinner.getLayoutParams();
        ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams)textView.getLayoutParams();
        textView.setLayoutParams(params1);
        spinner.setLayoutParams(params2);
    }
}
