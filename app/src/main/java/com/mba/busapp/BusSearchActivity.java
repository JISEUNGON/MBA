package com.mba.busapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
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
import com.naver.maps.map.overlay.Marker;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

public class BusSearchActivity  extends AppCompatActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {
    private NaverMapManager mapManager;
    private int switch_counter;

    //컴포넌트
    private Spinner spinner;
    private TextView schoolStation;
    private TextView selectedStation;
    private TextView selectedStationLocation;
    private ImageView selectedStationImg;
    private int[] imageID;

    private String[] items = {"정류장을 선택하세요", "이마트·상공회의소", "진입로", "동부경찰서", "용인시장", "중앙공영주차장", "명지대역", "진입로(명지대방향)","이마트·상공회의소(명지대방향)", "기흥역"};
    private String[] location = {"이마트·상공희의소 버스 정류장", "역북동행정복지센터 버스 정류장" ,"금호 부동산중개 앞", "행텐 주니어 용인점 앞", "안경창고 싸군 용인점 앞", "명지대사거리 버스 정류장", "역북동행정복지센터 버스 정류장", "이마트·상공희의소 버스 정류장", "기흥역 5번 출구 앞" };
    private int [] textViewLength = {170, 70, 100, 85, 140, 85, 180, 130, 70};

    List<String> list = new ArrayList<>(Arrays.asList(items));


    //intent로 넘길 값
    private String currentTime;
    private String targetStation;
    private boolean toSchool;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussearch);
        switch_counter = 0; //switch 카운터 0으로 초기화



        //ID로 컴포넌트 연결
        spinner = (Spinner) findViewById(R.id.spinner);
        selectedStation = (TextView) findViewById(R.id.tvStations);
        selectedStationLocation = (TextView) findViewById(R.id.tvStationLocation);
        selectedStationImg = (ImageView) findViewById(R.id.ivStations);
        schoolStation = (TextView) findViewById(R.id.tvSchool);

        //이미지별 ID 저장
        imageID = setImageID();

        //어뎁터 생성
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this,R.layout.spinner_item,list){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }
            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position==0) {
                    // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

        };

        //어뎁터 적용
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        spinner.setOnItemSelectedListener(this);
        spinner.setAdapter(spinnerArrayAdapter);

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

        // Marker OnClick 이벤트 연결
        mapManager.addMarkerClickEventListener(new NaverMapManager.MarkerClickListener() {
            @Override
            public void onClick(@NonNull Marker selectedMarker) {
                // idx 탐색
                int idx = 0;
                for(int i = 0; i < items.length; i++) {
                    if (items[i].contains(selectedMarker.getCaptionText())) {
                        idx = i;
                    }
                }

                ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) selectedStation.getLayoutParams();
                params.width = dpToPx((Context) getOuter(), textViewLength[idx-1]);
                selectedStation.setLayoutParams(params);

                //정류장 설명 text 세팅
                selectedStationLocation.setText(location[idx-1]);

                //정류장 image 세팅
                selectedStationImg.setImageResource(imageID[idx-1]);

                // spinner 세팅
                spinner.setSelection(idx);
                //정류장 이름 text 세팅
                if(items[idx]=="이마트·상공회의소(명지대방향)"){
                    //정류장 이름 text 세팅
                    selectedStation.setText("이마트·상공회의소\n(명지대방향)");
                }
                else{
                    selectedStation.setText(items[idx]);
                }
            }
        });
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
    };
    /**
     * 학교 -> 타겟 정류장 / 타겟 정류장 -> 학교 SWITCH
     * @param v view
     */
    public void btn_switch(View v) {
        switch_counter++;
        ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams)spinner.getLayoutParams();
        ConstraintLayout.LayoutParams params2 = (ConstraintLayout.LayoutParams) schoolStation.getLayoutParams();
        schoolStation.setLayoutParams(params1);
        spinner.setLayoutParams(params2);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        //Toast.makeText(this.getApplicationContext(), items[i], Toast.LENGTH_SHORT).show();
        if(i==0){
            selectedStation.setText("");
            selectedStationLocation.setText("");
            selectedStationImg.setImageResource(imageID[9]);

        }
        else{
            //정류장 이름 text 길이 세팅
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams)selectedStation.getLayoutParams();
            params.width = dpToPx(this, textViewLength[i-1]);
            selectedStation.setLayoutParams(params);

            //정류장 설명 text 세팅
            selectedStationLocation.setText(location[i-1]);

            //정류장 image 세팅
            selectedStationImg.setImageResource(imageID[i-1]);

            //정류장 이름 text 세팅
            if(items[i]=="이마트·상공회의소(명지대방향)"){
                //정류장 이름 text 세팅
                selectedStation.setText("이마트·상공회의소\n(명지대방향)");
            }
            else{
                selectedStation.setText(items[i]);
            }
        }

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

    //이미지 ID 저장하기
    public int[] setImageID(){
        int [] imageID = new int[10];
        imageID[0] = R.drawable.mju_chamber;
        imageID[1] = R.drawable.mju_entry_to_station;
        imageID[2] = R.drawable.mju_police_office;
        imageID[3] = R.drawable.mju_market;
        imageID[4] = R.drawable.mju_parking_lot;
        imageID[5] = R.drawable.mju_station;
        imageID[6] = R.drawable.mju_entry_to_school;
        imageID[7] = R.drawable.mju_emart;
        imageID[8] = R.drawable.mju_kh_station_;
        imageID[9] = R.drawable.mju_ready;

        return imageID;
    }

    public int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float)dp * density);
    }

    public Object getOuter() {
        return this;
    }
}
