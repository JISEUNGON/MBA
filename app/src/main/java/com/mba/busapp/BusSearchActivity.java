package com.mba.busapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class BusSearchActivity  extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {
    private NaverMapManager mapManager;
    private int switch_counter;
    private Spinner spinner;
    private TextView textView;
    private String[] items = {"상공회의소", "진입로", "동부경찰서", "용인시장", "중앙공영주차장", "명지대역", "진입로(명지대방향)", "기흥역"};


    //intent로 넘길 값
    private String currentTime;
    private String targetStation;
    private boolean toSchool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussearch);

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.spinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        textView = (TextView) findViewById(R.id.textView);
        switch_counter = 0;


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
//        mapManager.enableMarker_MjuStation();'
        mapManager.enableMarker_DownTown();
//        mapManager.enablePoly_MjuStation();
        mapManager.enablePoly_DownTown();

    }

    /**
     * 버스 결과 화면으로 이동
     * @param v view
     */
    public void btn_moveNext(View v) {
        currentTime = setCurrentTime(Calendar.getInstance().getTime());
        targetStation = spinner.getSelectedItem().toString();
        toSchool = isToSchool(switch_counter);

        Intent intent = new Intent(this, BusResultActivity.class);
        intent.putExtra("currentTime", currentTime);
        intent.putExtra("toSchool", toSchool);
        intent.putExtra("targetStation",targetStation);

        startActivity(intent);
    }
    /**
     * 학교 -> 타겟 정류장 / 타겟 정류장 -> 학교 SWITCH
     * @param v view
     */
    public void btn_switch(View v) {
        switch_counter++;
        ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams)spinner.getLayoutParams();
        ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams)textView.getLayoutParams();
        textView.setLayoutParams(params1);
        spinner.setLayoutParams(params2);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Toast.makeText(this.getApplicationContext(), items[i], Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    //현재시간 세팅
    public String setCurrentTime(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_EE_HH_mm",Locale.KOREAN);
        TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
        dateFormat.setTimeZone(tz);

        return dateFormat.format(date);
    }

    //정류장 -> 학교 세팅
    public boolean isToSchool(int switch_counter){
        return (switch_counter%2==0);
    }

}
