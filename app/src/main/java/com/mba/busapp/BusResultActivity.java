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

    //넘겨온 변수
    boolean toSchool;       // 학교 to 정류장 or 정류장 to 학교
    String time;     // 현재 시간, 요일
    String targetStation;   // 타겟 정류장

    //시간 데이터 분리
    String currentDay;

    //시간표, 정류장 데이터
    String [] MJSTATION_WEEKDAY_TIMETABLE;
    String [] CITY_WEEKDAY_TIMETABLE;
    String [] INTEGRATED_WEEKDAY_TIMETABLE;
    String [] GHSTATION_WEEKDAY_TIMETABLE;
    String [] WEEKEND_TIMETABLE;

    String [] MJSTATION_WEEKDAY_STATIONS;
    String [] CITY_WEEKDAY_STATIONS;
    String [] GHSTATION_WEEKDAY_STATIONS;
    String [] WEEKEND_STATIONS;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busresult);
        loadData();

        // 네이버맵 Listener 연결
        MapView mapView = findViewById(R.id.busresult_navermap);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMapManager = new NaverMapManager(naverMap, this);
        naverMapManager.setCameraPosition(new LatLng(37.233972549267705, 127.18874893910944), 15);

    }

    public void loadData(){
        //정류장 데이터 세팅
        MJSTATION_WEEKDAY_STATIONS = getResources().getStringArray(R.array.MJSTATION_WEEKDAY_STATIONS);
        CITY_WEEKDAY_STATIONS = getResources().getStringArray(R.array.CITY_WEEKDAY_STATIONS);
        GHSTATION_WEEKDAY_STATIONS = getResources().getStringArray(R.array.GHSTATION_WEEKDAY_STATIONS);
        WEEKEND_STATIONS = getResources().getStringArray(R.array.WEEKEND_STATIONS);

        //시간표 데이터 세팅
        MJSTATION_WEEKDAY_TIMETABLE = getResources().getStringArray(R.array.MJSTATION_WEEKDAY_TIMETABLE);
        CITY_WEEKDAY_TIMETABLE = getResources().getStringArray(R.array.CITY_WEEKDAY_TIMETABLE);
        INTEGRATED_WEEKDAY_TIMETABLE = getResources().getStringArray(R.array.INTEGRATED_WEEKDAY_TIMETABLE);
        GHSTATION_WEEKDAY_TIMETABLE = getResources().getStringArray(R.array.GHSTATION_WEEKDAY_TIMETABLE);
        WEEKEND_TIMETABLE = getResources().getStringArray(R.array.WEEKEND_TIMETABLE);
    }
}
