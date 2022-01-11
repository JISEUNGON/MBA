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

    //시간표, 정류장, 소요시간 데이터
    String [] MJSTATION_WEEKDAY_TIMETABLE;
    String [] CITY_WEEKDAY_TIMETABLE;
    String [] INTEGRATED_WEEKDAY_TIMETABLE;
    String [] GHSTATION_WEEKDAY_TIMETABLE;
    String [] WEEKEND_TIMETABLE;

    String [] MJSTATION_WEEKDAY_STATIONS;
    String [] CITY_WEEKDAY_STATIONS;
    String [] GHSTATION_WEEKDAY_STATIONS;
    String [] WEEKEND_STATIONS;
    
    int[] MJSTATION_REQUIRED_TIME;
    int[] CITY_REQUIRED_TIME;
    int[] WEEKEND_REQUIRED_TIME;
    int[] GHSTATION_REQUIRED_TIME = {900,900};

    //버스 출발 시간
    String[] startTimes;

    //출력 데이터 (초 단위)
    int busArrivalTimeLeft;
    int arrivalTimeLeft;

    //버스 도착 정보
    ArrivalData arrivalData;
    //버스 도착시간
    DateFormat busArrivalTime;
    //학교 or 타겟정류장 도착 시간
    DateFormat arrivalTime;
    //버스 노선도
    String routeType;

    @Override
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

        loadData();

        //정류장 -> 학교인 경우
        if(toSchool){
            //주말
            if(isWeekend(currentDay)){
                //기흥역 버스
                if(targetStation.equals("기흥역")){
                    //주말엔 기흥역 노선이 없습니다.
                    //에러 처리
                }
                // 그 외 버스
                else{
                    startTimes = Search.FindClosestBus(currentTime, WEEKEND_TIMETABLE);
                }

                arrivalData = compareArrivalTimeToSchool(targetStation, startTimes, currentTime, true);
            }
            //평일
            else{
                //1. startTimes: 가까운 버스 출발 시간 구하기

                //기흥역 버스
                if(targetStation.equals("기흥역")){
                    startTimes = Search.FindClosestBus(currentTime, GHSTATION_WEEKDAY_TIMETABLE);
                }
                //시내, 명지대역 노선이 겹치는 버스
                else if(Arrays.asList(CITY_WEEKDAY_STATIONS).contains(targetStation)&&Arrays.asList(MJSTATION_WEEKDAY_STATIONS).contains(targetStation)){
                    startTimes = Search.FindClosestBus(currentTime, INTEGRATED_WEEKDAY_TIMETABLE);
                }
                //시내
                else if(Arrays.asList(CITY_WEEKDAY_STATIONS).contains(targetStation)){
                    startTimes = Search.FindClosestBus(currentTime, CITY_WEEKDAY_TIMETABLE);
                }
                //명지대역
                else{
                    startTimes = Search.FindClosestBus(currentTime, MJSTATION_WEEKDAY_TIMETABLE);
                }
                arrivalData = compareArrivalTimeToSchool(targetStation, startTimes, currentTime, false);
            }
            Log.d("startTimes", Arrays.toString(startTimes));
        }

        //학교 -> 정류장인 경우
        else{
            //주말
            if(isWeekend(currentDay)){
                //기흥역 버스
                if(targetStation.equals("기흥역")){
                    //주말엔 기흥역 노선이 없습니다.
                    //에러 처리
                }
                // 그 외 버스
                else{
                    startTimes = Search.FindClosestBus(currentTime, WEEKEND_TIMETABLE);
                }

                //학교 -> target에 대한 도착 정보를 구한다.
                arrivalData = getWeekendArrivalTime(targetStation, startTimes[1], false);
            }
            //평일
            else{
                if(targetStation.equals("기흥역")){
                    startTimes = Search.FindClosestBus(currentTime, GHSTATION_WEEKDAY_TIMETABLE);
                }
                //시내, 명지대역 노선이 겹치는 버스
                else if(Arrays.asList(CITY_WEEKDAY_STATIONS).contains(targetStation)&&Arrays.asList(MJSTATION_WEEKDAY_STATIONS).contains(targetStation)){
                    startTimes = Search.FindClosestBus(currentTime, INTEGRATED_WEEKDAY_TIMETABLE);
                }
                //시내
                else if(Arrays.asList(CITY_WEEKDAY_STATIONS).contains(targetStation)){
                    startTimes = Search.FindClosestBus(currentTime, CITY_WEEKDAY_TIMETABLE);
                }
                //명지대역
                else{
                    startTimes = Search.FindClosestBus(currentTime, MJSTATION_WEEKDAY_TIMETABLE);
                }

                //학교 -> target에 대한 도착 정보를 구한다.
                arrivalData = getWeekdayArrivalTime(targetStation, startTimes[1], false);
            }
        }

        arrivalTime = arrivalData.getArrivalTime();
        busArrivalTime = arrivalData.getBusArrivalTime();
        routeType = arrivalData.getRouteType();

        //정류장이 진입로일 경우, 빨간버스와 추가 비교
        busArrivalTimeLeft = DateFormat.compare(busArrivalTime, new DateFormat(currentTime));

        if((toSchool&&targetStation.equals("진입로(명지대방향)"))){
            int redBusTimeLeft = BusManager.getBusInfo_city();
            //광역버스가 더 빨리 오거나, 셔틀버스가 끊겼을 때
            if(busArrivalTimeLeft>redBusTimeLeft||busArrivalTimeLeft<0){
                busArrivalTimeLeft = redBusTimeLeft;
                //버스 도착 시간 수정 -> 현재시간 + 광역버스 대기시간
                busArrivalTime = new DateFormat(currentTime);
                busArrivalTime.addSecTime(busArrivalTimeLeft);
                routeType = "광역버스";
                //학교 도착시간 수정 -> 현재시간 + 버스 대기시간 + 진입로 ~ 학교 경로 시간
                arrivalTime = new DateFormat(currentTime);
                //버스 대기시간 추가
                arrivalTime.addSecTime(busArrivalTimeLeft);
                //진입로 ~ 학교까지 시간 추가
                arrivalTime.addSecTime(MJSTATION_REQUIRED_TIME[MJSTATION_REQUIRED_TIME.length-2]);
                arrivalTime.addSecTime(MJSTATION_REQUIRED_TIME[MJSTATION_REQUIRED_TIME.length-1]);
            }
        }

        arrivalTimeLeft = DateFormat.compare(arrivalTime, new DateFormat(currentTime));


        //끝 값 처리: 버스가 끊겼을 때
        if(busArrivalTimeLeft<0){
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
        Log.d("버스 도착까지 남은 시간",busArrivalTimeLeft + "초");
        Log.d("버스노선타입", routeType);



        // 네이버맵 Listener 연결
        mapView = findViewById(R.id.busresult_navermap);
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
        WEEKEND_STATIONS = getResources().getStringArray(R.array.WEEKEND_VACTION_STATIONS);

        //시간표 데이터 세팅
        MJSTATION_WEEKDAY_TIMETABLE = getResources().getStringArray(R.array.MJSTATION_SEMESTER_WEEKDAY_TIMETABLE);
        CITY_WEEKDAY_TIMETABLE = getResources().getStringArray(R.array.CITY_SEMESTER_WEEKDAY_TIMETABLE);
        INTEGRATED_WEEKDAY_TIMETABLE = getResources().getStringArray(R.array.INTEGRATED_SEMESTER_WEEKDAY_TIMETABLE);
        GHSTATION_WEEKDAY_TIMETABLE = getResources().getStringArray(R.array.GHSTATION_SEMESTER_WEEKDAY_TIMETABLE);
        WEEKEND_TIMETABLE = getResources().getStringArray(R.array.INTEGRATED_VACTION_OR_WEEKEND_TIMETABLE);
    }

    public boolean isWeekend(String days){
        return (days.equals("토")||days.equals("일"));
    }




    //arrivalData :
    // arrivalData[0] = 학교 도착 예정 시간
    // arrivalData[1] = 버스가 정류장에 도착할 예정시간
    public ArrivalData getWeekendArrivalTime(String targetStation, String startTime, boolean toSchool){

        //사용자가 학교 도착 예정 시간, 버스가 정류장에 도착할 예정시간
        ArrivalData arrivalData = new ArrivalData(startTime);
        arrivalData.setRouteType("주말방학노선");
        //정류장 index
        int stationIndex = 0;
        
        for (int i = 0; i < WEEKEND_STATIONS.length; i++) {
            if (WEEKEND_STATIONS[i].equals(targetStation)) {
                stationIndex = i;
            }
        }
        
        //target -> 학교일 때
        if(toSchool) {
            //버스 도착 예정 시간 구하기
            for (int i = 0; i <= stationIndex; i++) {
                arrivalData.addBusArrivalTime(WEEKEND_REQUIRED_TIME[i]);
            }

            //학교 도착예정 시간 구하기
            for (int i = 0; i < WEEKEND_REQUIRED_TIME.length; i++) {
                arrivalData.addArrivalTime(WEEKEND_REQUIRED_TIME[i]);
            }
        }
        //학교 -> target일 때
        else{
            //target 도착예정 시간 구하기
            for (int i = 0; i <= stationIndex; i++) {
                arrivalData.addArrivalTime(WEEKEND_REQUIRED_TIME[i]);
            }
        }
        return arrivalData;
    }
    //arrivalData :
    // arrivalData[0] = 학교 도착 예정 시간
    // arrivalData[1] = 버스가 정류장에 도착할 예정시간
    public ArrivalData getWeekdayArrivalTime(String targetStation, String startTime, boolean toSchool){

        //사용자가 학교 도착 예정 시간, 버스가 정류장에 도착할 예정시간
        ArrivalData arrivalData = new ArrivalData(startTime);

        int stationIndex = 0;

        if(toSchool) {
            //1. 기흥역 셔틀 버스
            if (targetStation.equals("기흥역")) {
                arrivalData.setRouteType("기흥역노선");
                for (int i = 0; i < GHSTATION_WEEKDAY_STATIONS.length; i++) {
                    if (GHSTATION_WEEKDAY_STATIONS[i].equals(targetStation)) {
                        stationIndex = i;
                    }
                }
                //구해진 stationIndex까지 즉 출발점부터 start 정류장까지 걸리는 시간을 더한다.
                for (int i = 0; i <= stationIndex; i++) {
                    arrivalData.addBusArrivalTime(GHSTATION_REQUIRED_TIME[i]);
                }
                //학교 도착예정 시간 구하기
                for (int i = 0; i < GHSTATION_REQUIRED_TIME.length; i++) {
                    arrivalData.addArrivalTime(GHSTATION_REQUIRED_TIME[i]);
                }
            }

            //2. 명지대역 버스
            else if (Arrays.asList(MJSTATION_WEEKDAY_TIMETABLE).contains(startTime)) {
                arrivalData.setRouteType("명지대역노선");
                for (int i = 0; i < MJSTATION_WEEKDAY_STATIONS.length; i++) {
                    if (MJSTATION_WEEKDAY_STATIONS[i].equals(targetStation)) {
                        stationIndex = i;
                    }
                }
                //구해진 stationIndex까지 즉 출발점부터 start 정류장까지 걸리는 시간을 더한다.
                for (int i = 0; i <= stationIndex; i++) {
                    arrivalData.addBusArrivalTime(MJSTATION_REQUIRED_TIME[i]);
                }
                //학교 도착예정 시간 구하기
                for (int i = 0; i < MJSTATION_REQUIRED_TIME.length; i++) {
                    arrivalData.addArrivalTime(MJSTATION_REQUIRED_TIME[i]);
                }
            }
            //3.시내 버스
            else {
                arrivalData.setRouteType("시내노선");
                for (int i = 0; i < CITY_WEEKDAY_STATIONS.length; i++) {
                    if (CITY_WEEKDAY_STATIONS[i].equals(targetStation)) {
                        stationIndex = i;
                    }
                }
                for (int i = 0; i <= stationIndex; i++) {
                    arrivalData.addBusArrivalTime(CITY_REQUIRED_TIME[i]);
                }

                //학교 도착예정 시간 구하기
                for (int i = 0; i < CITY_REQUIRED_TIME.length; i++) {
                    arrivalData.addArrivalTime(CITY_REQUIRED_TIME[i]);
                }
            }
        }

        else{
            //버스 도착 예정 시간 = 버스 출발 시간
            if (targetStation.equals("기흥역")) {
                arrivalData.setRouteType("기흥역노선");
                for (int i = 0; i < GHSTATION_WEEKDAY_STATIONS.length; i++) {
                    if (GHSTATION_WEEKDAY_STATIONS[i].equals(targetStation)) {
                        stationIndex = i;
                    }
                }
                //target 도착예정 시간 구하기
                for (int i = 0; i <= stationIndex; i++) {
                    arrivalData.addArrivalTime(GHSTATION_REQUIRED_TIME[i]);
                }
            }

            //2. 명지대역 버스
            else if (Arrays.asList(MJSTATION_WEEKDAY_TIMETABLE).contains(startTime)) {
                arrivalData.setRouteType("명지대역노선");
                for (int i = 0; i < MJSTATION_WEEKDAY_STATIONS.length; i++) {
                    if (MJSTATION_WEEKDAY_STATIONS[i].equals(targetStation)) {
                        stationIndex = i;
                    }
                }
                //target 도착예정 시간 구하기
                for (int i = 0; i <= stationIndex; i++) {
                    arrivalData.addArrivalTime(MJSTATION_REQUIRED_TIME[i]);
                }
            }
            //3.시내 버스
            else {
                arrivalData.setRouteType("시내노선");
                for (int i = 0; i < CITY_WEEKDAY_STATIONS.length; i++) {
                    if (CITY_WEEKDAY_STATIONS[i].equals(targetStation)) {
                        stationIndex = i;
                    }
                }
                //target 도착예정 시간 구하기
                for (int i = 0; i <= stationIndex; i++) {
                    arrivalData.addArrivalTime(CITY_REQUIRED_TIME[i]);
                }
            }
        }

        return arrivalData;

    }


    //도착 버스 시간 비교 메서드
    //INPUT : 도착 정류장, 2개의 버스출발시간(타겟시간 이전버스, 타겟시간 이후버스)
    //직전 버스, 이후 버스 중 어떤 버스가 먼저 도착할 지 판단한 후 먼저 도착하는 버스의 도착시간을 반환한다.
    public ArrivalData compareArrivalTimeToSchool(String startStation, String [] startTimes, String currentTime, boolean isWeekend){

        ArrivalData arrivalData;

        ArrivalData bus1ArrivalData;
        ArrivalData bus2ArrivalData;
        
        //주말이면
        if (isWeekend) {
            bus1ArrivalData = getWeekendArrivalTime(startStation, startTimes[0], true);
            bus2ArrivalData = getWeekendArrivalTime(startStation, startTimes[1], true);
        }
        //평일이면
        else{
            bus1ArrivalData = getWeekdayArrivalTime(startStation, startTimes[0], true);
            bus2ArrivalData = getWeekdayArrivalTime(startStation, startTimes[1], true);
        }

        //이전에 출발한 버스 시간 > 타겟 시간 : 아직 도착 전이기 때문에 ArrivalTime은 이전에 출발한 버스가 도착할 시간이 된다.
        if(DateFormat.compare(bus1ArrivalData.getBusArrivalTime().getTime(), currentTime) > 0){
            arrivalData = bus1ArrivalData;
        }
        //이전에 출발한 버스 시간 < 타겟 시간 : ArrivalTime은 이후에 출발한 버스가 도착할 시간이 된다.
        else{
            arrivalData = bus2ArrivalData;
        }
        return arrivalData;
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
