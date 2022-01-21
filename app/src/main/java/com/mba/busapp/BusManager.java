package com.mba.busapp;

import android.os.Build;
import android.os.StrictMode;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BusManager {

    /**
     * 명지대역 셔틀버스
     * * 실시간으로 값을 받아옵니다
     *
     * @return [x, x, x, x, x,]
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int[] getBusInfo_mju_station() {
        return getRouteInfo("https://yax35ivans.apigw.ntruss.com/mba/v1/OjJo45tmXK/json");
    }


    /**
     * 시내방향 셔틀버스
     * * 실시간으로 값을 받아옵니다.
     *
     * @return [x, x, x, x, x,] int[]
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int[] getBusInfo_mju_downtown() {
        return getRouteInfo("https://yax35ivans.apigw.ntruss.com/mba/v1/9KC9LrEB87/json");
    }

    /**
     * 방학 및 공휴일에 운행하는 노선
     * @return
     */
    public static int[] getBusInfo_vacation_or_weekend(){
        return getRouteInfo("https://yax35ivans.apigw.ntruss.com/mba/v1/ur857mfyes/json");
    }
    
    /**
     * 진입로(명지대 방향)에 도착하는 광역버스 정보를 API를 통하여 가져옵니다.
     *
     * @return 가장 빨리 도착하는 버스(minute)
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static int getBusInfo_city() {
        try {
            int SDK_INT = android.os.Build.VERSION.SDK_INT;
            if (SDK_INT > 8) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                        .permitAll().build();
                StrictMode.setThreadPolicy(policy);
                URL url = new URL("https://yax35ivans.apigw.ntruss.com/mba/v1/TLAVUb32yo/json");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/xml");
                BufferedReader rd;
                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                    Stream<String> streamOfString = new BufferedReader(inputStreamReader).lines();
                    String streamToString = streamOfString.collect(Collectors.joining());

                    String timeLeft = JsonParser.parseString(streamToString).getAsJsonObject().get("body").toString();
                    if (timeLeft.equals("")) return -1;
                    else return Integer.parseInt(timeLeft) * 60;
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                    Log.e("[BusManager]", "<getBusInfo_city>" + rd.readLine());
                }
            }
        } catch (Exception e) {
            Log.e("[BusManager]", "<getBusInfo_city>" + e.getMessage());
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * helper
     * @param endPointUrl
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static int[] getRouteInfo(String endPointUrl) {
        try {
            URL url = new URL(endPointUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                Stream<String> streamOfString = new BufferedReader(inputStreamReader).lines();
                String streamToString = streamOfString.collect(Collectors.joining());

                return Arrays.stream(
                        new JSONObject(streamToString).getString("body").split(",")
                ).mapToInt(Integer::parseInt)
                        .toArray();
            }
        } catch (Exception e) {
            Log.e("[BusManager]", "<getBusRouteInfo>" + e.getMessage());
        }

        return null;
    }
}
