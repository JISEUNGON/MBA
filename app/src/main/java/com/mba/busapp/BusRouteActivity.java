package com.mba.busapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * 버스 노선도 화면
 *  - 이전 Activity : BusSelectActivity
*      - intent
 *          day : 평일 / 주말
 *          semester : 학기 / 계절학기 / 방학
 *
 */
public class BusRouteActivity extends AppCompatActivity implements OnMapReadyCallback {
    private NaverMapManager mapManager;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private String[] itemList;
    private Spinner routeSpinner;

    // 넘겨온 값 저장할 변수
    private String day;
    private String semaster;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busroute);

        // 넘겨온 값 저장
        Intent intent = getIntent();
        day  = intent.getStringExtra("day");
        semaster  = intent.getStringExtra("semester");


        // 네이버맵 Listener 연결
        MapView mapView = findViewById(R.id.busroute_navermap);
        mapView.getMapAsync(this);


        // sliding panel 설정
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.busroute_slidingpanel);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        // 네이버지도 클릭시 sliding panel 닫히게 설정
        mapView.setOnClickListener(e -> slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED));

        // spinner 값 설정
        itemList = getResources().getStringArray(R.array.ROUTES_MJU_CITY_BUS);

        routeSpinner = (Spinner) findViewById(R.id.routes_spinner);
        routeSpinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.ROUTES_MJU_CITY_BUS, R.layout.support_simple_spinner_dropdown_item));

        routeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mapManager != null) {
                    showMarker(itemList[i]);
                    makeTable(itemList[i]);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        mapManager = new NaverMapManager(naverMap, this);
        mapManager.setCameraPosition(new LatLng(37.2329535, 127.1892392), 13);
        mapManager.enableMarker_MjuStation();
        mapManager.enablePoly_MjuStation();
        mapManager.enableLocation();

        showMarker((String) routeSpinner.getSelectedItem());
        makeTable((String) routeSpinner.getSelectedItem());
    }


    public void btn_slidingView(View v) {
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    public void showMarker(String route) {
        mapManager.disableMarkers();
        switch (route) {
            case "명지대역":
                if (semaster.equals("방학") || day.equals("주말")) {
                    mapManager.enableMarker_Vacation();
                    mapManager.enablePoly_Vaction();
                } else {
                    mapManager.enableMarker_MjuStation();
                    mapManager.enablePoly_MjuStation();
                }
                mapManager.setCameraPosition(new LatLng(37.2329535, 127.1892392), 13);
                break;
            case "시내":
                if (semaster.equals("방학") || day.equals("주말")) {
                    mapManager.enableMarker_Vacation();
                    mapManager.enablePoly_Vaction();
                } else {
                    mapManager.enableMarker_DownTown();
                    mapManager.enablePoly_DownTown();
                }
                mapManager.setCameraPosition(new LatLng(37.2297982,127.1959162), 13);
                break;
            case "기흥역":
                mapManager.enableMarker_Giheung();
                mapManager.enablePoly_Giheung();
                mapManager.setCameraPosition(new LatLng(37.2454122, 127.1500397), 11);
                break;
            default:
                Log.e("[DEBUG]", "[BusRouteActivity]<showMarker> " + route);
        }
    }

    public void makeTable(String route) {
        String[] depart_times = new String[0];
        String[] estimated_times = new String[0];
        String column_text = "";

        // route에 따라 시간표 생성
        switch (route) {
            case "기흥역":
                /**
                 *        평일      공휴일
                 * 학기     O         X
                 * 계절     X         X
                 * 방학     X         X
                 */
                if (!(semaster.equals("학기") && day.equals("평일"))) {
                    // make nothing
                    depart_times = new String[] {""}; // 멘트 설정?
                    estimated_times = new String[] {""};
                } else {
                    // make something
                    depart_times = getResources().getStringArray(R.array.GHSTATION_SEMESTER_WEEKDAY_TIMETABLE);

                    // 기흥역은 15분 딜레이
                    estimated_times = depart_times.clone();
                    Arrays.stream(estimated_times).map(e -> DateFormat.addTime(e, 15));
                }

                column_text = "기흥역 도착\n예정 시간";
                break;
            case "명지대역":
                /**
                 *        평일      공휴일
                 * 학기     O         X
                 * 계절     O         X
                 * 방학     O         O   * 시내/명지대역 동시 운행
                 */
                if (semaster.equals("방학") || day.equals("주말")) {
                    depart_times = getResources().getStringArray(R.array.INTEGRATED_VACTION_OR_WEEKEND_TIMETABLE);
                    estimated_times = depart_times.clone();
                    Arrays.stream(estimated_times).map(e -> DateFormat.addTime(e, 25));
                } else {
                    if(semaster.equals("계절학기")) {
                        depart_times = getResources().getStringArray(R.array.MJSTATION_VACATION_SEMESTER_WEEKDAY_TIMETABLE);
                    } else if(semaster.equals("학기")) {
                        depart_times = getResources().getStringArray(R.array.MJSTATION_SEMESTER_WEEKDAY_TIMETABLE);
                    } else {
                        Log.e("[ERROR]", "[BusRouteActivity]<makeTable> UnknownKeyword : " + semaster);
                    }
                    estimated_times = depart_times.clone();
                    Arrays.stream(estimated_times).map(e -> DateFormat.addTime(e, 15));
                }

                column_text = "진입로 경유\n예정 시간";
                break;
            case "시내":
                /**
                 *        평일      공휴일
                 * 학기     O         O
                 * 계절     O         O
                 * 방학     O         O   * 시내/명지대역 동시 운행
                 */
                if (semaster.equals("방학") || day.equals("주말")) {
                    depart_times = getResources().getStringArray(R.array.INTEGRATED_VACTION_OR_WEEKEND_TIMETABLE);
                    estimated_times = depart_times.clone();
                    Arrays.stream(estimated_times).map(e -> DateFormat.addTime(e, 25));
                } else {
                    if(semaster.equals("계절학기")) {
                        depart_times = getResources().getStringArray(R.array.CITY_VACATION_SEMESTER_WEEKDAY_TIMETABLE);
                    } else if(semaster.equals("학기")) {
                        depart_times = getResources().getStringArray(R.array.CITY_SEMESTER_WEEKDAY_TIMETABLE);
                    } else {
                        Log.e("[ERROR]", "[BusRouteActivity]<makeTable> UnknownKeyword : " + semaster);
                    }
                    estimated_times = depart_times.clone();
                    Arrays.stream(estimated_times).map(e -> DateFormat.addTime(e, 15));
                }

                column_text = "진입로 경유\n예정 시간";
                break;
        }


        // 테이블 동적뷰 생성
        TableLayout tableLayout = (TableLayout) findViewById(R.id.busroute_tablelayout);
        TableRow.LayoutParams view_layoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        view_layoutParams.setMargins(1, 0, 1, 0); // 사이가 떨어져있어..

        // 이전 View 모두 삭제
        tableLayout.removeAllViews();

        // Column 명 변경
        ((TextView) findViewById(R.id.busroute_col2)).setText(column_text);

        for(int i = 0; i < depart_times.length; i++) {
            // TableRow 생성
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            // Textview 생성
            for(int j = 0; j < 2; j++) {
                String time;
                if (j == 0) time = depart_times[i];
                else time = estimated_times[i];

                TextView view = new TextView(this);
                view.setText(time);
                view.setGravity(Gravity.CENTER);
                view.setTextAppearance(R.style.Binggrae_content);
                view.setLayoutParams(view_layoutParams);
                view.setBackgroundResource(R.drawable.table_outer);


                // TableRow에 textView 추가
                tableRow.addView(view);
            }

            // TableLayout에 Tablerow 추가
            tableLayout.addView(tableRow);
        }
    }
}
