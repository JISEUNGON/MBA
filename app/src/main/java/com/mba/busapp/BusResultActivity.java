package com.mba.busapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.Arrays;

public class BusResultActivity extends AppCompatActivity implements OnMapReadyCallback {
    MapView mapView;
    NaverMapManager naverMapManager;

    //넘겨온 변수
    boolean toSchool;       // 학교 to 정류장 or 정류장 to 학교
    String time;     // 현재 시간, 요일
    String targetStation;   // 타겟 정류장

    //시간 데이터 분리
    String currentDay;
    String currentTime;
    
    int[] MJSTATION_REQUIRED_TIME;
    int[] CITY_REQUIRED_TIME;
    int[] WEEKEND_REQUIRED_TIME;
    int[] GHSTATION_REQUIRED_TIME = {900,900};

    //출력 데이터 (초 단위)
    int busArrivalTimeLeft;
    int arrivalTimeLeft;
    //버스 도착시간
    DateFormat busArrivalTime;
    //학교 or 타겟정류장 도착 시간
    DateFormat arrivalTime;
    //버스 노선도
    String routeType;
    //마지막 노선
    ArrayList<String> lastStations;
    //남은 노선
    ArrayList<String> restStations;
    

    @Override
    @SuppressWarnings("unchecked")
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busresult);

        //intent로 넘겨올 값 :  targetStation, toSchool, time
        Intent intent = getIntent();
        time = intent.getStringExtra("currentTime");
        toSchool = intent.getBooleanExtra("toSchool", true);
        targetStation = intent.getStringExtra("targetStation");
        CITY_REQUIRED_TIME = intent.getIntArrayExtra("downtown_timerequire");
        MJSTATION_REQUIRED_TIME = intent.getIntArrayExtra("mjustation_timerequire");
        WEEKEND_REQUIRED_TIME = intent.getIntArrayExtra("vacation_or_weekend_timerequire");

        //time 을 split해서 currentDay, currentTime에 각각 요일, 시간 정보를 담는다.
        String [] dateData = time.split("_");
        currentTime = dateData[4] + ":" + dateData[5];
        currentDay = dateData[3];

        Log.d("넘겨온 데이터", "***************************************************");
        Log.d("명지대역 노선 구간별 시간", Arrays.toString(MJSTATION_REQUIRED_TIME));
        Log.d("시내 노선 구간별 시간", Arrays.toString(CITY_REQUIRED_TIME));
        Log.d("주말 노선 구간별 시간", Arrays.toString(WEEKEND_REQUIRED_TIME));
        Log.d("기흥역 노선 구간별 시간", Arrays.toString(GHSTATION_REQUIRED_TIME));
        Log.d("방향", toSchool + "");
        Log.d("정류장", targetStation);
        Log.d("현재 시간", currentTime);
        Log.d("요일", currentDay);

        //알고리즘 객체 생성
        BusAlgorithm busAlgorithm = new BusAlgorithm(this);

        //도착 정보 객체 구하기
        ArrivalData arrivalData = busAlgorithm.getArrivalData(toSchool, targetStation, currentTime, currentDay, MJSTATION_REQUIRED_TIME, CITY_REQUIRED_TIME, WEEKEND_REQUIRED_TIME);


        if(arrivalData!=null) {
            //정류장이 진입로일 경우, 빨간버스와 추가 비교
            arrivalData = busAlgorithm.compareRedBusArrivalTime(arrivalData, currentTime, toSchool, targetStation);

            arrivalTime = arrivalData.getArrivalTime();
            busArrivalTime = arrivalData.getBusArrivalTime();
            busArrivalTimeLeft = DateFormat.compare(busArrivalTime, new DateFormat(currentTime));
            arrivalTimeLeft = DateFormat.compare(arrivalTime, new DateFormat(currentTime));
            routeType = arrivalData.getRouteType();
            lastStations = arrivalData.getLastStations();
            restStations = arrivalData.getRestStaions();

            //끝 값 처리: 버스가 끊겼을 때
            if (busArrivalTimeLeft < 0) {
                //오류 처리 알람
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                Intent intent1 = new Intent(this, BusSearchActivity.class);
                dialog.setTitle("알림");
                dialog.setMessage("금일 버스 운행이 종료되었습니다.");
                dialog.setPositiveButton("이전으로",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // 처리할 코드 작성
                                startActivity(intent1);
                            }
                        });
                dialog.show();
            }

            //출력데이터
            Log.d("출력할 데이터", "***************************************************");
            Log.d("현재시간", currentTime);
            Log.d("최종목적지 도착시간", arrivalTime.getTime());
            Log.d("최종목적지까지 소요시간", arrivalTimeLeft + "초");
            Log.d("버스 예정 도착시간", busArrivalTime.getTime());
            Log.d("버스 도착까지 남은 시간", busArrivalTimeLeft + "초");
            Log.d("버스노선타입", routeType);
            Log.d("마지막 정거장", lastStations.toString());
            Log.d("남은 정거장", restStations.toString());



        }

        // 네이버맵 Listener 연결
        mapView = findViewById(R.id.busresult_navermap);
        mapView.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMapManager = new NaverMapManager(naverMap, this);
        naverMapManager.setCameraPosition(new LatLng(37.233972549267705, 127.18874893910944), 15);

    }

    @Override
    public void onStart()
    {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause()
    {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onStop()
    {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory()
    {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
