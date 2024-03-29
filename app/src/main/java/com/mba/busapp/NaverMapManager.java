package com.mba.busapp;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Camera;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.overlay.Overlay;
import com.naver.maps.map.overlay.OverlayImage;
import com.naver.maps.map.overlay.PathOverlay;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;

import java.util.ArrayList;
import java.util.List;

/**
 * NaverMAP Wrapper
 * 이것도 싱글톤이 되나?
 */
public class NaverMapManager {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1000;
    private NaverMap naverMap;
    private AppCompatActivity appCompatActivity;
    private ArrayList<Marker> markers;
    private PathOverlay pathOverlay;
    private MarkerClickListener markerClickListener;
    private boolean clickEvent;
    private int poly_width;

    @FunctionalInterface
    interface MarkerClickListener {
        void onClick(Marker marker);
    }
    
    public NaverMapManager(NaverMap naverMap, AppCompatActivity appCompatActivity) {
        this.naverMap = naverMap;
        this.appCompatActivity = appCompatActivity;
        this.markers = new ArrayList<>();
        this.pathOverlay = new PathOverlay();
        this.clickEvent = true;
        this.poly_width = 13;
        this.markerClickListener = null;

        // 대중교통 그룹 추가
        naverMap.setLayerGroupEnabled(NaverMap.LAYER_GROUP_TRANSIT, true);
    }

    /**
     * restStation에 있는 정류장만 Poly 활성화
     * @param restStations
     */
    public void enablePoly(ArrayList<String> restStations) {
        pathOverlay.setCoords(StationInfo.getInstance().getPolyList_restStation(restStations));
        pathOverlay.setWidth(this.poly_width); // Poly 넓이
        pathOverlay.setColor(Color.argb(0xFF, 0x5C, 0xD1, 0xE5)); // Poly 색
        pathOverlay.setOutlineWidth(1);
        pathOverlay.setMap(naverMap); // 현 지도에 표시
    }

    /**
     * restStation에 있는 정류장의 Marker만 화럿ㅇ화
     * @param rest
     */
    public void enableMarker(ArrayList<String> rest) {
        for(String station: rest) {
            Marker marker = new Marker();
            marker.setIcon(MarkerIcons.BLUE); // 아이콘 색상
            marker.setPosition(StationInfo.getInstance().getLatLng(station)); // 위치 설정
            // cpation 설정 ( 이 있으면 2줄로 표시
            if(station.contains("(")) {
                String[] groups = station.split("\\(");
                marker.setCaptionText(groups[0] + "\n(" + groups[1]);
            } else {
                marker.setCaptionText(station);
            }

            marker.setMap(naverMap); // 현 지도에 표시
            markers.add(marker);
        }
    }

    /**
     * 마커 클릭시 호출되는 함수
     * @param listener Listener
     */
    public void addMarkerClickEventListener(MarkerClickListener listener) {
        this.markerClickListener = listener;
    }

    /**
     * 마커 클릭 이벤트 disable
     */
    public void disableMarker_clickEvent() {
        this.clickEvent = false;
    }

    public Marker getMarker(String station) {
        for(Marker marker: markers) {
            if(station.contentEquals(marker.getCaptionText().replace("\n", ""))) {
                return marker;
            }
        }
        return null;
    }

    /**
     * 명지대역 노선 마커 활성화
     */
    public void enableMarker_MjuStation() {
        String[] stations = StationInfo.getInstance().getStationList_MjuStation();
        for(String station: stations) {
            Marker marker = new Marker();
            marker.setOnClickListener(overlay -> markerOnClickEvent(marker)); // 클릭이벤트
            marker.setIcon(MarkerIcons.BLUE); // 아이콘 색상
            marker.setPosition(StationInfo.getInstance().getLatLng(station)); // 위치 설정

            // cpation 설정 ( 이 있으면 2줄로 표시
            if(station.contains("(")) {
                String[] groups = station.split("\\(");
                marker.setCaptionText(groups[0] + "\n(" + groups[1]);
            } else {
                marker.setCaptionText(station); 
            }

            marker.setMap(naverMap); // 현 지도에 표시
            markers.add(marker);
        }
    }

    /**
     * 명지대역 모든 노선 활성화
     */
    public void enablePoly_MjuStation() {
        pathOverlay.setCoords(StationInfo.getInstance().getPolyList_MjuStation());
        pathOverlay.setWidth(this.poly_width); // Poly 넓이
        pathOverlay.setColor(Color.argb(0xFF, 0x5C, 0xD1, 0xE5)); // Poly 색
        pathOverlay.setOutlineWidth(1);
        pathOverlay.setMap(naverMap); // 현 지도에 표시
    }

    /**
     * 용인시내 노선 마커 활성화
     */
    public void enableMarker_DownTown() {
        String[] stations = StationInfo.getInstance().getStationList_DownTown();
        for(String station: stations) {
            Marker marker = new Marker();
            marker.setOnClickListener(overlay -> markerOnClickEvent(marker)); // 클릭이벤트
            marker.setIcon(MarkerIcons.BLUE); // 아이콘 색상
            marker.setPosition(StationInfo.getInstance().getLatLng(station)); // 위치 설정

            // cpation 설정 ( 이 있으면 2줄로 표시
            if(station.contains("(")) {
                String[] groups = station.split("\\(");
                marker.setCaptionText(groups[0] + "\n(" + groups[1]);
            } else {
                marker.setCaptionText(station);
            }

            marker.setMap(naverMap); // 현 지도에 표시
            markers.add(marker);
        }
    }

    /**
     * 시내 모든 노선 활성화
     */
    public void enablePoly_DownTown() {
        pathOverlay.setCoords(StationInfo.getInstance().getPolyList_DownTown());
        pathOverlay.setWidth(this.poly_width); // Poly 넓이
        pathOverlay.setColor(Color.argb(0xFF, 0x5C, 0xD1, 0xE5)); // Poly 색
        pathOverlay.setOutlineWidth(1);
        pathOverlay.setMap(naverMap); // 현 지도에 표시
    }

    /**
     * 기흥역 노선 마커 활성화
     */
    public void enableMarker_Giheung() {
        String[] stations = StationInfo.getInstance().getStationList_Giheung();
        for(String station: stations) {
            Marker marker = new Marker();
            marker.setOnClickListener(overlay -> markerOnClickEvent(marker)); // 클릭이벤트
            marker.setIcon(MarkerIcons.BLUE); // 아이콘 색상
            marker.setPosition(StationInfo.getInstance().getLatLng(station)); // 위치 설정

            // cpation 설정 ( 이 있으면 2줄로 표시
            if(station.contains("(")) {
                String[] groups = station.split("\\(");
                marker.setCaptionText(groups[0] + "\n(" + groups[1]);
            } else {
                marker.setCaptionText(station);
            }

            marker.setMap(naverMap); // 현 지도에 표시
            markers.add(marker);
        }
    }

    /**
     * 기흥역 경로 활성화
     */
    public void enablePoly_Giheung() {
        pathOverlay.setCoords(StationInfo.getInstance().getPolyList_Giheung());
        pathOverlay.setWidth(this.poly_width); // Poly 넓이
        pathOverlay.setColor(Color.argb(0xFF, 0x5C, 0xD1, 0xE5)); // Poly 색
        pathOverlay.setOutlineWidth(1);
        pathOverlay.setMap(naverMap); // 현 지도에 표시
    }

    /**
     * 방학 중 셔틀버스 마커 활성화
     */
    public void enableMarker_Vacation() {
        String[] stations = StationInfo.getInstance().getStationList_Vacation();
        for(String station: stations) {
            Marker marker = new Marker();
            marker.setOnClickListener(overlay -> markerOnClickEvent(marker)); // 클릭이벤트
            marker.setIcon(MarkerIcons.BLUE); // 아이콘 색상
            marker.setPosition(StationInfo.getInstance().getLatLng(station)); // 위치 설정


            // cpation 설정 ( 이 있으면 2줄로 표시
            if(station.contains("(")) {
                String[] groups = station.split("\\(");
                marker.setCaptionText(groups[0] + "\n(" + groups[1]);
            } else {
                marker.setCaptionText(station);
            }

            marker.setMap(naverMap); // 현 지도에 표시
            markers.add(marker);
        }
    }

    /**
     * 방학 중 셔틀버스 노선 활성화
     */
    public void enablePoly_Vaction() {
        pathOverlay.setCoords(StationInfo.getInstance().getPolyList_Vacation());
        pathOverlay.setWidth(this.poly_width); // Poly 넓이
        pathOverlay.setColor(Color.argb(0xFF, 0x5C, 0xD1, 0xE5)); // Poly 색
        pathOverlay.setOutlineWidth(1);
        pathOverlay.setMap(naverMap); // 현 지도에 표시
    }

    /**
     * 모든 Marker 활성화
     */
    public void enableMarker_ALL() {
        String[] stations = StationInfo.getInstance().getStationList_ALL();
        for(String station: stations) {
            Marker marker = new Marker();
            marker.setOnClickListener(overlay -> markerOnClickEvent(marker)); // 클릭이벤트
            marker.setIcon(MarkerIcons.BLUE); // 아이콘 색상
            marker.setPosition(StationInfo.getInstance().getLatLng(station)); // 위치 설정

            // cpation 설정 ( 이 있으면 2줄로 표시
            if(station.contains("(")) {
                String[] groups = station.split("\\(");
                marker.setCaptionText(groups[0] + "\n(" + groups[1]);
            } else {
                marker.setCaptionText(station);
            }

            marker.setMap(naverMap); // 현 지도에 표시
            markers.add(marker);
        }
    }

    /**
     * 지도에 있는 모든 Marker 제거
     */
    public void disableMarkers() {
        for(Marker marker: markers) marker.setMap(null);
    }

    /**
     * 네이버맵 위치 버튼 사용
     */
    public void enableLocation() {
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);
        naverMap.setLocationSource(new FusedLocationSource(appCompatActivity, LOCATION_PERMISSION_REQUEST_CODE));
    }

    /**
     * 네이버맵 현 위치 이동 + zoom
     * @param position 이동할 위치
     */
    public void setCameraPosition(LatLng position, int zoom, CameraAnimation animation) {
        CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(position, zoom).animate(animation, 3000);
        naverMap.moveCamera(cameraUpdate);
    }

    /**
     * 네이버맵 현 위치 이동
     * @param position 이동할 위치
     */
    public void setCameraPosition(LatLng position) {
        CameraUpdate cameraUpdate = CameraUpdate.scrollTo(position).animate(CameraAnimation.Easing, 3000);
        naverMap.moveCamera(cameraUpdate);
    }

    /**
     * 사용자가 클릭한 Marker를 가져오는 메소드
     * @return 선택된 Marker, 없으면 null
     */
    public Marker getSelectedMarker() {
        for(Marker marker: markers) {
            if(marker.getIconTintColor() == Color.RED) return marker;
        }

        return null;
    }

    /**
     * 현 위치 받아오는 Helper 
     * 위치를 추정하는 기법이 여러개 있기 때문에, 그 중 기기에서 사용하고 있는 방법을 확인 후 위치를 받아옴
     * @return 현 위치
     */
    private Location getLastKnownLocation() {
        LocationManager mLocationManager = (LocationManager) appCompatActivity.getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;

        // Provider 기준 탐색
        for (String provider : providers) {
            if (ActivityCompat.checkSelfPermission(appCompatActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(appCompatActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return null;
            }
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    /**
     * 위치 권한 허용 코드
     * @return false if 거부
     */
    private boolean GPS_permission_granted() {
        int permissionCheck = ContextCompat.checkSelfPermission(appCompatActivity, Manifest.permission.ACCESS_FINE_LOCATION);

        // 허용한 적 없다면 허용 요청
        if( permissionCheck == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(appCompatActivity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        }

        // 실제로 허용했는지 확인
        try {
            Location location = getLastKnownLocation();
        } catch (Exception e) {
            // Toast.makeText
            Log.e("[NaverMapManager]", "<GPS_permission_granted()> 위치 허용 안됨!");
            return false;
        }
        return true;
    }

    /**
     * 마커 클릭 이벤트
     * @param marker 클린된 마커
     */
    private boolean markerOnClickEvent(Marker marker) {
        if (!this.clickEvent) return false;

        for(Marker m: markers) m.setIcon(MarkerIcons.BLUE); // 다른 마커 색 초기화
        setCameraPosition(marker.getPosition());
        marker.setIcon(MarkerIcons.RED); // 선택된 마커 파란색으로

        if(this.markerClickListener != null) this.markerClickListener.onClick(marker);
        return true;
    }

}
