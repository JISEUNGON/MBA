package com.mba.busapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

import java.util.Arrays;

public class BusResultActivity extends AppCompatActivity implements OnMapReadyCallback {
    NaverMapManager naverMapManager;

    //넘겨온 변수
    boolean toSchool;       // 학교 to 정류장 or 정류장 to 학교
    String time;     // 현재 시간, 요일
    String targetStation;   // 타겟 정류장

    //시간 데이터 분리
    String currentDay;
    String currentTime;

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

    //버스 출발 시간
    String[] startTimes;
    //버스 예상 도착 시간
    DateFormat[] arrivalData;
    //소요 시간
    int[] timeRequire;
    
    //출력 데이터
    //버스 도착시간
    DateFormat busArrivalTime;
    //학교 or 타겟정류장 도착 시간
    DateFormat arrivalTime;
    String busType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busresult);


        //intent로 넘겨올 값 :  targetStation, toSchool, time
        Intent intent = getIntent();
        time = intent.getStringExtra("currentTime");
        toSchool = intent.getBooleanExtra("toSchool", true);
        targetStation = intent.getStringExtra("targetStation");

        //time 을 split해서 currentDay, currentTime에 각각 요일, 시간 정보를 담는다.
        String [] dateData = time.split("_");
        currentTime = dateData[4] + ":" + dateData[5];
        currentDay = dateData[3];

        loadData();
//
//        //정류장 -> 학교인 경우
//        if(toSchool){
//            //주말
//            if(isWeekend(currentDay)){
//                //기흥역 버스
//                if(targetStation.equals("기흥역")){
//                    //주말엔 기흥역 노선이 없습니다.
//                    //에러 처리
//                }
//                // 그 외 버스
//                else{
//                    startTimes = Search.FindClosestBus(currentTime, WEEKEND_TIMETABLE);
//                }
//
//                arrivalData = compareArrivalTime(targetStation, startTimes, currentTime, true);
//
//                arrivalTime = arrivalData[1];
//                busArrivalTime = arrivalData[0];
//            }
//            //평일
//            else{
//                //1. startTimes: 가까운 버스 출발 시간 구하기
//
//                //기흥역 버스
//                if(targetStation.equals("기흥역")){
//                    startTimes = Search.FindClosestBus(currentTime, GHSTATION_WEEKDAY_TIMETABLE);
//                }
//                //시내, 명지대역 노선이 겹치는 버스
//                else if(Arrays.asList(CITY_WEEKDAY_STATIONS).contains(targetStation)&&Arrays.asList(MJSTATION_WEEKDAY_STATIONS).contains(targetStation)){
//                    startTimes = Search.FindClosestBus(currentTime, INTEGRATED_WEEKDAY_TIMETABLE);
//                }
//                //시내
//                else if(Arrays.asList(CITY_WEEKDAY_STATIONS).contains(targetStation)){
//                    startTimes = Search.FindClosestBus(currentTime, CITY_WEEKDAY_TIMETABLE);
//                }
//                //명지대역
//                else{
//                    startTimes = Search.FindClosestBus(currentTime, MJSTATION_WEEKDAY_TIMETABLE);
//                }
//
//                arrivalData = compareArrivalTime(targetStation, startTimes, currentTime, false);
//
//                arrivalTime = arrivalData[1];
//                busArrivalTime = arrivalData[0];
//            }
//        }
//
//        //학교 -> 정류장인 경우
//        else{
//            //주말
//            if(isWeekend(currentDay)){
//
//            }
//            //평일
//            else{
//
//            }
//        }


        //정류장이 진입로일 경우, 빨간버스와 추가 비교

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

    public boolean isWeekend(String days){
        return (days=="토"||days=="일");
    }




    //arrivalData :
    // arrivalData[0] = 학교 도착 예정 시간
    // arrivalData[1] = 버스가 정류장에 도착할 예정시간
    public DateFormat[] getWeekendArrivalTime(String targetStation, String startTime){

        DateFormat [] arrivalData = new DateFormat[2];

        //사용자가 학교 도착 예정 시간
        DateFormat arrivalTime = new DateFormat(startTime);
        //버스가 정류장에 도착할 예정시간
        DateFormat busArrivalTime = new DateFormat(startTime);

        int stationIndex = 0;

        //timeRequire = BusManager.getBusInfo_weekend();
        for (int i = 0; i < WEEKEND_STATIONS.length; i++) {
            if (WEEKEND_STATIONS[i].equals(targetStation)) {
                stationIndex = i;
            }
        }
        for(int i =0; i<=stationIndex; i++){
            busArrivalTime.addTime(timeRequire[i]);
        }

        //학교 도착예정 시간 구하기
        for(int i =0; i< timeRequire.length; i++){
            arrivalTime.addTime(timeRequire[i]);
        }
        arrivalData[0] = busArrivalTime;
        arrivalData[1] = arrivalTime;

        return arrivalData;

    }
    //arrivalData :
    // arrivalData[0] = 학교 도착 예정 시간
    // arrivalData[1] = 버스가 정류장에 도착할 예정시간
    public DateFormat[] getWeekdayArrivalTime(String targetStation, String startTime){

        DateFormat [] arrivalData = new DateFormat[2];

        //사용자가 학교 도착 예정 시간
        DateFormat arrivalTime = new DateFormat(startTime);
        //버스가 정류장에 도착할 예정시간
        DateFormat busArrivalTime = new DateFormat(startTime);

        int stationIndex = 0;

        //1. 기흥역 셔틀 버스
        if(targetStation=="기흥역"){
            //timeRequire = BusManager.getBusInfo_gh_station;
            busArrivalTime.addTime(timeRequire[0]);
        }

        //2. 명지대역 버스
        else if(Arrays.asList(MJSTATION_WEEKDAY_TIMETABLE).contains(startTime)){
            timeRequire = BusManager.getBusInfo_mju_station();
            for (int i = 0; i < MJSTATION_WEEKDAY_STATIONS.length; i++) {
                if (MJSTATION_WEEKDAY_STATIONS[i].equals(targetStation)) {
                    stationIndex = i;
                }
            }
            //구해진 stationIndex까지 즉 출발점부터 start 정류장까지 걸리는 시간을 더한다.
            for(int i =0; i<=stationIndex; i++){
                busArrivalTime.addTime(timeRequire[i]);
            }
        }
        //버스가 시내 버스라면
        else {
            timeRequire = BusManager.getBusInfo_mju_downtown();
            for (int i = 0; i < CITY_WEEKDAY_STATIONS.length; i++) {
                if (CITY_WEEKDAY_STATIONS[i].equals(targetStation)) {
                    stationIndex = i;
                }
            }
            for(int i =0; i<=stationIndex; i++){
                busArrivalTime.addTime(timeRequire[i]);
            }

        }
        //학교 도착예정 시간 구하기
        for(int i =0; i< timeRequire.length; i++){
            arrivalTime.addTime(timeRequire[i]);
        }
        arrivalData[0] = busArrivalTime;
        arrivalData[1] = arrivalTime;

        return arrivalData;

    }


    //도착 버스 시간 비교 메서드
    //INPUT : 도착 정류장, 2개의 버스출발시간(타겟시간 이전버스, 타겟시간 이후버스)
    //직전 버스, 이후 버스 중 어떤 버스가 먼저 도착할 지 판단한 후 먼저 도착하는 버스의 도착시간을 반환한다.
    public DateFormat[] compareArrivalTime(String startStation, String [] startTimes, String currentTime, boolean isWeekend){

        DateFormat[] arrivalData;

        DateFormat[] bus1ArrivalData;
        DateFormat[] bus2ArrivalData;
        
        //주말이면
        if (isWeekend) {
            bus1ArrivalData = getWeekendArrivalTime(startStation, startTimes[0]);
            bus2ArrivalData = getWeekendArrivalTime(startStation, startTimes[1]);
        }
        //평일이면
        else{
            bus1ArrivalData = getWeekdayArrivalTime(startStation, startTimes[0]);
            bus2ArrivalData = getWeekdayArrivalTime(startStation, startTimes[1]);
        }

        //이전에 출발한 버스 시간 > 타겟 시간 : 아직 도착 전이기 때문에 ArrivalTime은 이전에 출발한 버스가 도착할 시간이 된다.
        if(DateFormat.compare(bus1ArrivalData[0].getTime(), currentTime) > 0){
            arrivalData = bus1ArrivalData;
        }
        //이전에 출발한 버스 시간 < 타겟 시간 : ArrivalTime은 이후에 출발한 버스가 도착할 시간이 된다.
        else{
            arrivalData = bus2ArrivalData;
        }
        return arrivalData;
    }
}
