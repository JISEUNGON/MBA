package com.mba.busapp;

import android.content.Context;
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
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.UiSettings;

import java.util.ArrayList;
import java.util.Arrays;

public class BusResultActivity extends AppCompatActivity implements OnMapReadyCallback {

    //Naver 맵
    MapView mapView;
    NaverMapManager naverMapManager;

    // 역 정류장 정보
    String[] WEEKEND_STATIONS;
    String[] MJSTATION_WEEKDAY_STATIONS;
    String[] CITY_WEEKDAY_STATIONS;
    String[] GHSTATION_WEEKDAY_STATIONS;

    //출력 데이터
    boolean toSchool;                   //학교 to 정류장 or 정류장 to 학교
    String targetStation;               //최종 목적지
    String currentTime;                 //현재시간
    int busArrivalTimeLeft;             //버스 도착 남은 시간
    int arrivalTimeLeft;                //최종 목적지 도착 남은 시간
    DateFormat busArrivalTime;          //버스 도착시간
    DateFormat arrivalTime;             //최정목적지 도착시간
    String routeType;                   //버스 노선도
    ArrayList<String> lastStations;     //마지막 노선
    ArrayList<String> restStations;     //남은 노선


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

        //intent로 넘겨올 값 :  도착정보 (ArrivalData)
        Intent intent = getIntent();
        ArrivalData arrivalData = (ArrivalData) intent.getSerializableExtra("ArrivalData");

        //데이터 세팅
        toSchool = arrivalData.isToSchool();                                                    //학교 to 정류장 or 정류장 to 학교
        targetStation = arrivalData.getTargetStation();                                         //최종 목적지
        currentTime = arrivalData.getCurrentTime().getTime();                                   //현재시간
        arrivalTime = arrivalData.getArrivalTime();                                             //목적지 도착 시간
        busArrivalTime = arrivalData.getBusArrivalTime();                                       //버스 도착 시간
        busArrivalTimeLeft = DateFormat.compare(busArrivalTime, new DateFormat(currentTime));   //버스 도착까지 남은 시간
        arrivalTimeLeft = DateFormat.compare(arrivalTime, new DateFormat(currentTime));         //목적지 도착까지 남은 시간
        routeType = arrivalData.getRouteType();                                                 //노선 종류
        lastStations = arrivalData.getLastStations();                                           //명지대 자연캠퍼스 대체할 명지대 내부 정류장 list (toSchool 이 false 일때는 무시하시면 됩니다)
        restStations = arrivalData.getRestStaions();                                            //목적지까지 남은 정류장 list

        //출력데이터
        Log.d("DATA START", "***************************************************");
        Log.d("toSchool",toSchool+ "");
        Log.d("목적지",targetStation);
        Log.d("현재시간", currentTime);
        Log.d("최종목적지 도착시간", arrivalTime.getTime());
        Log.d("최종목적지까지 소요시간", arrivalTimeLeft + "초");
        Log.d("버스 예정 도착시간", busArrivalTime.getTime());
        Log.d("버스 도착까지 남은 시간", busArrivalTimeLeft + "초");
        Log.d("버스노선타입", routeType);
        Log.d("마지막 정거장", lastStations.toString());
        Log.d("남은 정거장", restStations.toString());
        Log.d("DATA END", "***************************************************");

        // 네이버맵 Listener 연결
        mapView = findViewById(R.id.busresult_navermap);
        mapView.getMapAsync(this);

        // 역 정보 저장
        MJSTATION_WEEKDAY_STATIONS = getResources().getStringArray(R.array.MJSTATION_WEEKDAY_STATIONS);
        CITY_WEEKDAY_STATIONS = getResources().getStringArray(R.array.CITY_WEEKDAY_STATIONS);
        GHSTATION_WEEKDAY_STATIONS = getResources().getStringArray(R.array.GHSTATION_WEEKDAY_STATIONS);
        WEEKEND_STATIONS = getResources().getStringArray(R.array.WEEKEND_VACTION_STATIONS);

        // 리스트뷰 연결
        lv = (ListView)findViewById(R.id.listView);
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

        // 타이머 변수
        int timer_time = busArrivalTimeLeft-60;
        if(timer_time <=0){
            timer_time = 1;
        }

        //타미어 설정
        CountDownTimer BusTimer = new CountDownTimer(timer_time*1000,1000) {

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

            // 마지막 실행 시
            @Override
            public void onFinish() {
                typeAndTimer = busType + "  " + "곧 도착";
                listAdapter.changeBuss(1,typeAndTimer);
                listAdapter.notifyDataSetChanged();
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
            listAdapter.addBussPass(ContextCompat.getDrawable(this,R.drawable.bus_pass_final),lastStations.get(lastStations.size()-1));

            //리스트에 중간 노선 추가
            for(int i =0; i<restStations.size()-1; i++){
                buspassList.add(restStations.get(i));
            }
            for(int i =0; i<lastStations.size(); i++){
                buspassList.add(lastStations.get(i));
            }
        }
        // 학교 -> 정류장
        else {
            // 출발지 와 도착지 출력
            listAdapter.addBussPass(ContextCompat.getDrawable(this,R.drawable.buss_pass_start), "명지대학교 자연캠퍼스");
            listAdapter.addBussPass(ContextCompat.getDrawable(this,R.drawable.buss_pass_line), typeAndTimer);
            listAdapter.addBussPass(ContextCompat.getDrawable(this,R.drawable.buss_pass_line), lefttimeAndexpand);
            listAdapter.addBussPass(ContextCompat.getDrawable(this,R.drawable.bus_pass_final), targetStation);

            //리스트에 중간 노선 추가
            for(int i =0; i<restStations.size()-1; i++){
                buspassList.add(restStations.get(i));
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
                        listAdapter.addBussPass(ContextCompat.getDrawable(context,R.drawable.bus_pass_final),lastStations.get(lastStations.size()-1));
                        listAdapter.notifyDataSetChanged();
                    }
                    else if(toSchool == false){
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

                            // 마지막 정류장이 학교인 경우
                            if(toSchool){
                                listAdapter.addBussPass(ContextCompat.getDrawable(context,R.drawable.bus_pass_final),lastStations.get(lastStations.size()-1));
                            }
                            // 마지막 정류장이 target인 경우
                            else{
                                listAdapter.addBussPass(ContextCompat.getDrawable(context,R.drawable.bus_pass_final), targetStation);
                            }
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
        TextView remaining_time = (TextView) findViewById(R.id.tvRemainTime);
        // 초를 분으로 변경
        int sec_to_min =  arrivalTimeLeft / 60;
        String temp = sec_to_min + "분";
        remaining_time.setText(temp);

        // 몇시 도착 예정인지 표시
        TextView arrival_time = (TextView) findViewById(R.id.tvArrivalTime);
        long start_time = System.currentTimeMillis();
        temp = currentTime + " 출발 ~ " + arrivalTime.getTime() + " 도착";
        arrival_time.setText(temp);
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        naverMapManager = new NaverMapManager(naverMap, this);

        ArrayList<String> rest = (ArrayList<String>) restStations.clone();
        if (toSchool) {
            rest.add(0, targetStation);
            rest.remove(rest.size() - 1);
            for (String st: lastStations)
                rest.add(st);
        }
        else
            rest.add(0, "명지대");
        naverMapManager.enableMarker(rest);
        naverMapManager.enablePoly(rest);
        naverMapManager.setCameraPosition(naverMapManager.getMarker(rest.get(0)).getPosition(), 15, CameraAnimation.None);
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setCompassEnabled(false);
        uiSettings.setLocationButtonEnabled(false);
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
