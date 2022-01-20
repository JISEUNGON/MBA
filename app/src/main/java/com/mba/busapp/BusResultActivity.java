package com.mba.busapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

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

    // 역 정류장 정보
    String[] WEEKEND_STATIONS;
    String[] MJSTATION_WEEKDAY_STATIONS;
    String[] CITY_WEEKDAY_STATIONS;
    String[] GHSTATION_WEEKDAY_STATIONS;

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

    // 리스트 뷰
    ListView lv;

    // 리스트뷰 어댑터
    BussList listAdapter;

    // 정류소 답을 리스트
    ArrayList<String> buspassList;

    // Context
    Context context;

    // 확대 했는지 확인
    boolean isexpand;

    // 타이머
    int min, sec;
    String lefttime;

    // 버스 종류와 타이머
    String typeAndTimer;

    // 타이머 시작
    boolean isstart;

    // 버스 타입
    String busType;


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

        currentTime = "15:00";

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

        // 역 정보 저장
        MJSTATION_WEEKDAY_STATIONS = getResources().getStringArray(R.array.MJSTATION_WEEKDAY_STATIONS);
        CITY_WEEKDAY_STATIONS = getResources().getStringArray(R.array.CITY_WEEKDAY_STATIONS);
        GHSTATION_WEEKDAY_STATIONS = getResources().getStringArray(R.array.GHSTATION_WEEKDAY_STATIONS);
        WEEKEND_STATIONS = getResources().getStringArray(R.array.WEEKEND_VACTION_STATIONS);

        // 리스트뷰 연결
        lv = (ListView)findViewById(R.id.lv01);
        listAdapter = new BussList();

        lv.setAdapter(listAdapter);

        // ArrayList 생성
        buspassList = new ArrayList<String>();
        
        // 버스 타입 설정
        if(routeType.equals("광역버스")){
            busType = "광역버스";
        }
        else{
            busType = "셔틀버스";
        }

        // 버스 종류 + 타이머
        typeAndTimer = busType + "  " +lefttime;

        // 예상 소요 시간 + 몇개 정류장 남았는지 확인
        String lefttimeAndexpand = "예상 소요시간: " + (arrivalTimeLeft-busArrivalTimeLeft)/60 + "분  자세히 보기";


        // 리스드뷰 변수 설정
        isexpand = false;
        context = this;

        // 타미어 시간 설정
        min = busArrivalTimeLeft / 60;
        sec = busArrivalTimeLeft % 60;
        isstart = true;

        lefttime = min + "분 " + sec + " 초";

        //타미어 설정
        CountDownTimer BusTimer = new CountDownTimer(busArrivalTimeLeft*1000,1000) {

            // 시작 시
            @Override
            public void onTick(long millisUntilFinished) {


                // 0초 이상이면
                if (sec != 0) {
                    //1초씩 감소
                    sec--;

                    // 시간, 분 설정
                    lefttime = min + "분 " + sec + " 초";
                    // 버스 종류 + 타이머 재설정
                    typeAndTimer = busType + "  " +lefttime;

                    listAdapter.changeBuss(1,typeAndTimer);
                    listAdapter.notifyDataSetChanged();


                    // 0분 이상이면
                } else if (min != 0) {
                    // 1분 = 60초
                    sec = 60;
                    sec--;
                    min--;

                    // 시간, 분 설정
                    lefttime = min + "분 " + sec + " 초";
                    // 버스 종류 + 타이머 재설정
                    typeAndTimer = busType + "  " +lefttime;
                    listAdapter.changeBuss(1,typeAndTimer);
                    listAdapter.notifyDataSetChanged();

                }
            }

            // 마지막 시
            @Override
            public void onFinish() {

            }
        }.start();


        //===============================
        // *****리스트뷰 출력******
        //===============================
        // 리스트뷰에 넘겨줄 데이터 판별하기기
        // 정류장 -> 학교
        if(toSchool){

            // 출발지 와 도착지 출력
            listAdapter.addBussPass(ContextCompat.getDrawable(this,R.drawable.buss_pass_start), targetStation);
            listAdapter.addBussPass(ContextCompat.getDrawable(this,R.drawable.buss_pass_line), typeAndTimer);
            listAdapter.addBussPass(ContextCompat.getDrawable(this,R.drawable.buss_pass_line), lefttimeAndexpand);
            listAdapter.addBussPass(ContextCompat.getDrawable(this,R.drawable.bus_pass_final), "명지대학교 자연캠퍼스");


            // 인덱스 생성
            int start_i = 0;

            // 노선 탐색
            if(routeType.equals("주말방학노선")){
                // 몇번쨰에 있는지 확인
                for(int i = 0; i <  WEEKEND_STATIONS.length; i++){
                    if(WEEKEND_STATIONS[i].equals(targetStation)){
                        start_i = i + 1;
                    }
                }
                // 리스트에 추가
                for(; start_i < WEEKEND_STATIONS.length; start_i++){
                    buspassList.add(WEEKEND_STATIONS[start_i]);
                }
            }
            else if(routeType.equals("명지대역노선")){
                // 몇번쨰에 있는지 확인
                for(int i = 0; i <  MJSTATION_WEEKDAY_STATIONS.length; i++){
                    if(targetStation.equals(MJSTATION_WEEKDAY_STATIONS[i])){
                        start_i = i +1;
                    }
                }
                // 리스트에 추가
                for(; start_i < MJSTATION_WEEKDAY_STATIONS.length; start_i++){
                    buspassList.add(MJSTATION_WEEKDAY_STATIONS[start_i]);
                }
            }
            else if(routeType.equals("시내노선")){
                // 몇번째에 있는지 확인
                for(int i = 0; i <  CITY_WEEKDAY_STATIONS.length; i++){
                    if(targetStation.equals(CITY_WEEKDAY_STATIONS[i])){
                        start_i = i +1;
                    }
                }
                // 리스트에 추가
                for(; start_i < CITY_WEEKDAY_STATIONS.length; start_i++){
                    buspassList.add(CITY_WEEKDAY_STATIONS[start_i]);
                }
            }
            else if(routeType.equals("기흥역노선")){
                // 몇번째에 있는지 확인
                for(int i = 0; i <  GHSTATION_WEEKDAY_STATIONS.length; i++){
                    if(targetStation.equals(GHSTATION_WEEKDAY_STATIONS[i])){
                        start_i = i +1;
                    }
                }
                // 리스트에 추가
                for(; start_i < GHSTATION_WEEKDAY_STATIONS.length; start_i++){
                    buspassList.add(GHSTATION_WEEKDAY_STATIONS[start_i]);
                }
            }



        }
        // 학교 -> 정류장
        else {

            // 출발지 와 도착지 출력
            listAdapter.addBussPass(ContextCompat.getDrawable(this,R.drawable.buss_pass_start), "명지대학교 자연캠퍼스");
            listAdapter.addBussPass(ContextCompat.getDrawable(this,R.drawable.buss_pass_line), typeAndTimer);
            listAdapter.addBussPass(ContextCompat.getDrawable(this,R.drawable.buss_pass_line), lefttimeAndexpand);
            listAdapter.addBussPass(ContextCompat.getDrawable(this,R.drawable.bus_pass_final), targetStation);

            // 인덱스 생성
            int end_i = 0;

            if(routeType.equals("주말방학노선")){
                // 몇번쨰에 있는지 확인
                for(int i = 0; i <  WEEKEND_STATIONS.length; i++){
                    if(WEEKEND_STATIONS[i].equals(targetStation)){
                        end_i = i;
                    }
                }
                // 리스트에 추가
                for(int i =WEEKEND_STATIONS.length -1; i >= end_i; i--){
                    buspassList.add(WEEKEND_STATIONS[i]);
                }

            }
            else if(routeType.equals("명지대역노선")){
                // 몇번쨰에 있는지 확인
                for(int i = 0; i <  MJSTATION_WEEKDAY_STATIONS.length; i++){
                    if(MJSTATION_WEEKDAY_STATIONS[i].equals(targetStation)){
                        end_i = i;
                    }
                }
                // 리스트에 추가
                for(int i =MJSTATION_WEEKDAY_STATIONS.length -1; i >= end_i; i--){
                    buspassList.add(MJSTATION_WEEKDAY_STATIONS[i]);
                }

            }
            else if(routeType.equals("시내노선")){
                // 몇번쨰에 있는지 확인
                for(int i = 0; i <  CITY_WEEKDAY_STATIONS.length; i++){
                    if(CITY_WEEKDAY_STATIONS[i].equals(targetStation)){
                        end_i = i;
                    }
                }
                // 리스트에 추가
                for(int i =CITY_WEEKDAY_STATIONS.length -1; i >= end_i; i--){
                    buspassList.add(CITY_WEEKDAY_STATIONS[i]);
                }

            }
            else if(routeType.equals("기흥역노선")){
                // 몇번쨰에 있는지 확인
                for(int i = 0; i <  GHSTATION_WEEKDAY_STATIONS.length; i++){
                    if(GHSTATION_WEEKDAY_STATIONS[i].equals(targetStation)){
                        end_i = i;
                    }
                }
                // 리스트에 추가
                for(int i =GHSTATION_WEEKDAY_STATIONS.length -1; i >= end_i; i--){
                    buspassList.add(GHSTATION_WEEKDAY_STATIONS[i]);
                }

            }

        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 축소 하는 경우
                if(isexpand == true && position == 2){
                    isexpand = false;
                    //Toast.makeText(context, "축소", Toast.LENGTH_SHORT).show();
                    // 버스 삭제
                    listAdapter.removeAllBus();


                    if(toSchool){
                        // 출발지 와 도착지 출력
                        listAdapter.addBussPass(ContextCompat.getDrawable(context,R.drawable.buss_pass_start), targetStation);
                        listAdapter.addBussPass(ContextCompat.getDrawable(context,R.drawable.buss_pass_line), typeAndTimer);
                        listAdapter.addBussPass(ContextCompat.getDrawable(context,R.drawable.buss_pass_line), lefttimeAndexpand);
                        listAdapter.addBussPass(ContextCompat.getDrawable(context,R.drawable.bus_pass_final), "명지대학교 자연캠퍼스");
                        listAdapter.notifyDataSetChanged();
                    }
                    else{
                        // 출발지 와 도착지 출력
                        listAdapter.addBussPass(ContextCompat.getDrawable(context,R.drawable.buss_pass_start), "명지대학교 자연캠퍼스");
                        listAdapter.addBussPass(ContextCompat.getDrawable(context,R.drawable.buss_pass_line), typeAndTimer);
                        listAdapter.addBussPass(ContextCompat.getDrawable(context,R.drawable.buss_pass_line), lefttimeAndexpand);
                        listAdapter.addBussPass(ContextCompat.getDrawable(context,R.drawable.bus_pass_final), targetStation);
                        listAdapter.notifyDataSetChanged();

                    }
                    // 리스트뷰 갱신
                    listAdapter.notifyDataSetChanged();


                }
                // 확대하는 경우
                else if(isexpand == false && position == 2){
                    isexpand = true;
                    //Toast.makeText(context, "확대", Toast.LENGTH_SHORT).show();
                    // 마지막 리스트 생략
                    listAdapter.removeBussPass(listAdapter.getCount()-1);
                    listAdapter.notifyDataSetChanged();

                    // 리스트뷰 출력
                    for(int i = 0 ; i < buspassList.size(); i++){

                        // 도착지 출력
                        if(i == buspassList.size()-1){
                            listAdapter.addBussPass(ContextCompat.getDrawable(context,R.drawable.bus_pass_final), "명지대학교 자연캠퍼스");
                            listAdapter.notifyDataSetChanged();
                        }
                        // 거쳐가는 정류장 출력
                        else{
                            listAdapter.addBussPass(ContextCompat.getDrawable(context,R.drawable.bus_pass_mid), (String)buspassList.get(i));
                            listAdapter.addBussPass(ContextCompat.getDrawable(context,R.drawable.buss_pass_line), "");
                            listAdapter.notifyDataSetChanged();
                        }

                    }

                }


            }
        });

        // 도착시 까지 남은 시간 표시
        TextView remaining_time = (TextView) findViewById(R.id.remaintime);
        // 초를 분으로 변경
        int sec_to_min =  arrivalTimeLeft / 60;
        String temp = sec_to_min + "분";
        remaining_time.setText(temp);

        // 몇시 도착 예정인지 표시
        TextView arrival_time = (TextView) findViewById(R.id.arrivaltime);
        long start_time = System.currentTimeMillis();
        temp = currentTime + " ~ " + arrivalTime.getTime();
        arrival_time.setText(temp);
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
