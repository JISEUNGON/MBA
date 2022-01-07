package com.mba.busapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.MapView;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

/**
 * 버스 노선도 화면
 */
public class BusRouteActivity extends AppCompatActivity implements OnMapReadyCallback {
    private NaverMapManager mapManager;
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private String[] itemList;

    // 넘겨온 값 저장할 변수
    String day;
    String semaster;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busroute);

        // 넘겨온 값 저장
        Intent intent = getIntent();
        day  = intent.getStringExtra("day");
        semaster  = intent.getStringExtra("semester");
        //Toast.makeText(getApplicationContext(), day, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getApplicationContext(), semaster, Toast.LENGTH_SHORT).show();


        // 네이버맵 Listener 연결
        MapView mapView = findViewById(R.id.busroute_navermap);
        mapView.getMapAsync(this);

        // sliding panel 설정
        slidingUpPanelLayout = (SlidingUpPanelLayout) findViewById(R.id.busroute_slidingpanel);
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

        // spinner 값 설정
        itemList = getResources().getStringArray(R.array.ROUTES_MJU_CITY_BUS);

        Spinner spinner = (Spinner) findViewById(R.id.routes_spinner);
        spinner.setAdapter(ArrayAdapter.createFromResource(this, R.array.ROUTES_MJU_CITY_BUS, R.layout.support_simple_spinner_dropdown_item));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(mapManager != null) {
                    showMarker(itemList[i]);
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
        mapManager.setCameraPosition(new LatLng(37.233972549267705, 127.18874893910944), 15);
        mapManager.enableMarker_MjuStation();
        mapManager.enablePoly_MjuStation();
        mapManager.enableLocation();
    }


    public void btn_slidingView(View v) {
        Log.d("[Button]", "slidingView Clicked!");
        slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
    }

    public void showMarker(String route) {
        mapManager.disableMarkers();
        switch (route) {
            case "명지대역":
                mapManager.enableMarker_MjuStation();
                mapManager.enablePoly_MjuStation();
                mapManager.setCameraPosition(new LatLng(37.2329535, 127.1892392));
                break;
            case "용인 시내":
                mapManager.enableMarker_DownTown();
                mapManager.enablePoly_DownTown();
                break;
            case "기흥역":
                mapManager.enableMarker_Giheung();
                mapManager.enablePoly_Giheung();
                break;
            case "방학":
                mapManager.enableMarker_Vacation();
                mapManager.enablePoly_Vaction();
                break;
            default:
                Log.e("[DEBUG]", "[BusRouteActivity]<showMarker> " + route);
        }
    }
}
