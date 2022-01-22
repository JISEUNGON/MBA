package com.mba.busapp;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.Bundle;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Marker;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

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
    private MapView mapView;
    private int switch_counter;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    //컴포넌트
    private Spinner spinner;
    private TextView schoolStation;
    private TextView selectedStation;
    private TextView selectedStationLocation;
    private ImageView selectedStationImg;
    private ImageButton button;


    private String[] items = {"정류장을 선택하세요", "이마트·상공회의소", "진입로", "동부경찰서", "용인시장", "중앙공영주차장", "명지대역", "진입로(명지대방향)","이마트·상공회의소(명지대방향)", "기흥역"};
    private String[] location = {"이마트·상공희의소 버스 정류장", "역북동행정복지센터 버스 정류장" ,"금호 부동산중개 앞", "행텐 주니어 용인점 앞", "안경창고 싸군 용인점 앞", "명지대사거리 버스 정류장", "역북동행정복지센터 버스 정류장", "이마트·상공희의소 버스 정류장", "기흥역 5번 출구 앞" };
    private int[] textViewLength = {170, 70, 100, 85, 140, 85, 180, 130, 70};
    private int[] imageID;

    //노선별 소요 에상 시간
    private int[] MJSTATION_REQUIRED_TIME;
    private int[] CITY_REQUIRED_TIME;
    private int[] WEEKEND_REQUIRED_TIME;

    List<String> list = new ArrayList<>(Arrays.asList(items));


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        class NewRunnable implements Runnable {
            @Override
            public void run() {
                while (true) { // 코드 작성
                    try {
                        int SDK_INT = android.os.Build.VERSION.SDK_INT;
                        if (SDK_INT > 8) {
                            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                                    .permitAll().build();
                            StrictMode.setThreadPolicy(policy);
                            //your codes here
                            MJSTATION_REQUIRED_TIME = BusManager.getBusInfo_mju_station();
                            CITY_REQUIRED_TIME = BusManager.getBusInfo_mju_downtown();
                            WEEKEND_REQUIRED_TIME = BusManager.getBusInfo_vacation_or_weekend();
                        }
                        //5분에 한번씩 거리 array 업데이트
                        Thread.sleep(300000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussearch);

        NewRunnable nr = new NewRunnable() ;
        Thread t = new Thread(nr) ;
        t.start();

        switch_counter = 0; //switch 카운터 0으로 초기화

        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.bussearch_slidingpanel);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        //ID로 컴포넌트 연결
        spinner = (Spinner) findViewById(R.id.spinner);
        selectedStation = (TextView) findViewById(R.id.tvStations);
        selectedStationLocation = (TextView) findViewById(R.id.tvStationLocation);
        selectedStationImg = (ImageView) findViewById(R.id.ivStations);
        schoolStation = (TextView) findViewById(R.id.tvSchool);
        button = (ImageButton) findViewById(R.id.btnSearch);

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
        mapView = findViewById(R.id.bussearch_navermap);
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
        mapManager.enableLocation();
        mapManager.enableMarker_ALL();

        // Marker OnClick 이벤트 연결
        mapManager.addMarkerClickEventListener(new NaverMapManager.MarkerClickListener() {
            @Override
            public void onClick(@NonNull Marker selectedMarker) {
                // idx 탐색
                int idx = 0;
                for(int i = 0; i < items.length; i++) {
                    if (items[i].contentEquals(selectedMarker.getCaptionText().replace("\n", ""))) {
                        idx = i;
                    }
                }
                if (idx == 0) return; // 명지대 선택시

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
                if(items[idx].equals("이마트·상공회의소(명지대방향)")){
                    //정류장 이름 text 세팅
                    selectedStation.setText("이마트·상공회의소\n(명지대방향)");
                }
                else{
                    selectedStation.setText(items[idx]);
                }

                slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
            }
        });
    }

    /**
     * 버스 결과 화면으로 이동
     * @param v view
     */
    public void btn_moveNext(View v){
        String time = setCurrentTime(Calendar.getInstance().getTime());
        String targetStation = spinner.getSelectedItem().toString();
        boolean toSchool = isToSchool(switch_counter);

        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        String [] dateData = time.split("_");
        String currentTime = dateData[4] + ":" + dateData[5];
        String currentDay = dateData[3];

        //알고리즘 객체 생성
        BusAlgorithm busAlgorithm = new BusAlgorithm(this);

        //도착 정보 객체 구하기
        ArrivalData arrivalData = busAlgorithm.getArrivalData(toSchool, targetStation, currentTime, currentDay, MJSTATION_REQUIRED_TIME, CITY_REQUIRED_TIME, WEEKEND_REQUIRED_TIME);

        
        //버스가 끊겼을 때 or 노선이 없을 때 출력할 다이얼로그
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("알림");
        dialog.setMessage("금일 버스 운행이 종료되었습니다.");
        dialog.setPositiveButton("이전으로",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 처리할 코드 작성
                        // DO NOTHING
                    }
                });

        if(arrivalData!=null){
            //광역버스까지 비교 후
            arrivalData = busAlgorithm.compareRedBusArrivalTime(arrivalData, currentTime, toSchool, targetStation);

            //만약 버스가 끊겼으면
            if (DateFormat.compare(arrivalData.getBusArrivalTime(), new DateFormat(currentTime)) < 0) {
                //오류 처리 알람
                dialog.show();
            }
            //버스가 끊기지 않았다면 : Result 페이지로 이동
            else{
                Intent intent = new Intent(BusSearchActivity.this, BusResultActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("ArrivalData",arrivalData);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

    /**
     * 학교 -> 타겟 정류장 / 타겟 정류장 -> 학교 SWITCH
     * @param v view
     */
    public void btn_switch(View v) {
        //카운터 증가 (toSchool 유무 판단 변수)
        switch_counter++;
        //레이아웃 변경 (위치 교환)
        ConstraintLayout.LayoutParams params1 = (ConstraintLayout.LayoutParams) spinner.getLayoutParams();
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

            // 마커 클릭이벤트
            Marker marker = mapManager.getMarker(items[i]);
            marker.performClick();
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
