package com.mba.busapp;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.map.CameraAnimation;
import com.naver.maps.map.CameraUpdate;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.UiSettings;
import com.naver.maps.map.util.FusedLocationSource;

import java.util.List;

/**
 * NaverMAP Wrapper
 * 이것도 싱글톤이 되나?
 */
public class NaverMapManager {
    private NaverMap naverMap;
    private AppCompatActivity appCompatActivity;

    public NaverMapManager(NaverMap naverMap, AppCompatActivity appCompatActivity) {
        this.naverMap = naverMap;
        this.appCompatActivity = appCompatActivity;
    }


    /**
     * 위치 UI 기능 활성화
     */
    public void enableLocationButton() {
        UiSettings uiSettings = naverMap.getUiSettings();
        uiSettings.setLocationButtonEnabled(true);
    }

    /**
     * 네이버맵 위치 버튼 사용
     */
    public void enableLocation() {
        naverMap.setLocationSource(new FusedLocationSource(appCompatActivity, 1000));
    }

    /**
     * 네이버맵 현 위치 이동
     * @param position 이동할 위치
     */
    public void setCameraPosition(LatLng position) {
        CameraUpdate cameraUpdate = CameraUpdate.scrollAndZoomTo(position,15).animate(CameraAnimation.Fly, 3000);
        naverMap.moveCamera(cameraUpdate);
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
}
