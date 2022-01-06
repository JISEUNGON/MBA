package com.mba.busapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;


/**
 * 메인화면
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Btnlistener, 버스 찾기 버튼
     * @param v
     */
    public void btn_searchBus(View v) {
        startActivity(new Intent(getApplicationContext(), BusSearchActivity.class));
    }

    /**
     * Btnlistener, 버스 노선도 버튼
     * @param v
     */
    public void btn_busRoute(View v) {
        startActivity(new Intent(getApplicationContext(), BusSelectActivity.class));
    }

}