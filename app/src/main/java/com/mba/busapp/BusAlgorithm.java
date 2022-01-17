package com.mba.busapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

public class BusAlgorithm {
    boolean toSchool;       // 학교 to 정류장 or 정류장 to 학교
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

    //다이얼로그
    AlertDialog.Builder dialog;

    //버스 출발 시간
    String[] startTimes;

    //버스 도착 정보
    ArrivalData arrivalData;

    Context context;

    public BusAlgorithm(Context context){
        this.context = context;

        loadData();

        dialog = new AlertDialog.Builder(context);
        Intent intent1 = new Intent(context, BusSearchActivity.class);
        dialog.setTitle("알림");
        dialog.setPositiveButton("이전으로",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // 처리할 코드 작성
                        context.startActivity(intent1);
                    }
                });

    }

    public ArrivalData compareRedBusArrivalTime(ArrivalData shuttleBusArrivalData, boolean toSchool, String targetStation){

        int busArrivalTimeLeft = DateFormat.compare(shuttleBusArrivalData.getBusArrivalTime(), new DateFormat(currentTime));

        if ((toSchool && targetStation.equals("진입로(명지대방향)"))) {
            int redBusTimeLeft = BusManager.getBusInfo_city();
            //광역버스가 더 빨리 오거나, 셔틀버스가 끊겼을 때
            if (busArrivalTimeLeft > redBusTimeLeft || busArrivalTimeLeft < 0) {
                busArrivalTimeLeft = redBusTimeLeft;

                ArrivalData redBusArrivalData = new ArrivalData(currentTime);
                redBusArrivalData.setRouteType("광역버스");
                //버스 도착 시간 수정 -> 현재시간 + 광역버스 대기시간
                redBusArrivalData.addBusArrivalTime(busArrivalTimeLeft);
                //목적지 도착 시간 수정 -> 현재시간 + 버스 대기시간 + 학교까지 이동 시간
                redBusArrivalData.addArrivalTime(busArrivalTimeLeft);
                redBusArrivalData.addArrivalTime(MJSTATION_REQUIRED_TIME[MJSTATION_REQUIRED_TIME.length - 2]);
                redBusArrivalData.addArrivalTime(MJSTATION_REQUIRED_TIME[MJSTATION_REQUIRED_TIME.length - 1]);

                return redBusArrivalData;
            }
            //셔틀버스가 더 빨리 올 때
            return shuttleBusArrivalData;
        }
        //목적지가 진입로가 아닐 때
        return shuttleBusArrivalData;
    }

    public ArrivalData getArrivalData(boolean toSchool, String targetStation, String currentTime, String currentDay, int[] mjRequiredTime, int[] cityRequiredTime, int[] weekendRequiredTime){

        this.toSchool = toSchool;
        this.targetStation = targetStation;
        this.currentTime = currentTime;
        this.currentDay = currentDay;
        MJSTATION_REQUIRED_TIME = mjRequiredTime;
        CITY_REQUIRED_TIME = cityRequiredTime;
        WEEKEND_REQUIRED_TIME = weekendRequiredTime;

        //정류장 -> 학교인 경우
        if(toSchool) {
            //학기 or 계절학기일 경우
            if (isSeasonalSemester() || isSemester()) {
                Log.d("기간", "계절학기/학기");
                //주말
                if (isWeekend(currentDay)) {
                    //기흥역 버스
                    if (targetStation.equals("기흥역")) {
                        //계절학기/학기 중 주말엔 기흥역 노선이 없습니다.
                        //에러 처리
                        dialog.setMessage("주말에는 기흥역 노선이 없습니다.");
                        dialog.show();
                    }
                    // 그 외 버스
                    else {
                        startTimes = Search.FindClosestBus(currentTime, WEEKEND_TIMETABLE);
                        arrivalData = compareArrivalTimeToSchool(targetStation, startTimes, currentTime, true);
                    }
                }
                //평일
                else {
                    //1. startTimes: 가까운 버스 출발 시간 구하기

                    //기흥역 버스
                    if (targetStation.equals("기흥역")) {
                        if(isSemester()) {
                            startTimes = Search.FindClosestBus(currentTime, GHSTATION_WEEKDAY_TIMETABLE);
                            arrivalData = compareArrivalTimeToSchool(targetStation, startTimes, currentTime, false);

                        }
                        else{
                            //계절학기 중 평일에는 기흥역 노선이 없습니다.
                            //에러처리
                            dialog.setMessage("계절학기 평일에는 기흥역 노선이 없습니다.");
                            dialog.show();
                        }
                    }
                    //시내, 명지대역 노선이 겹치는 버스
                    else if (Arrays.asList(CITY_WEEKDAY_STATIONS).contains(targetStation) && Arrays.asList(MJSTATION_WEEKDAY_STATIONS).contains(targetStation)) {
                        startTimes = Search.FindClosestBus(currentTime, INTEGRATED_WEEKDAY_TIMETABLE);
                        arrivalData = compareArrivalTimeToSchool(targetStation, startTimes, currentTime, false);

                    }
                    //시내
                    else if (Arrays.asList(CITY_WEEKDAY_STATIONS).contains(targetStation)) {
                        startTimes = Search.FindClosestBus(currentTime, CITY_WEEKDAY_TIMETABLE);
                        arrivalData = compareArrivalTimeToSchool(targetStation, startTimes, currentTime, false);

                    }
                    //명지대역
                    else {
                        startTimes = Search.FindClosestBus(currentTime, MJSTATION_WEEKDAY_TIMETABLE);
                        arrivalData = compareArrivalTimeToSchool(targetStation, startTimes, currentTime, false);
                    }
                }
            }
            //(동/하계)방학일 경우
            else{
                Log.d("기간", "방학");
                if (targetStation.equals("기흥역")) {
                    //방학 중엔 기흥역 노선이 없습니다.
                    //에러 처리
                    dialog.setMessage("방학에는 기흥역 노선이 없습니다.");
                    dialog.show();
                }
                // 그 외 버스
                else {
                    startTimes = Search.FindClosestBus(currentTime, WEEKEND_TIMETABLE);
                    arrivalData = compareArrivalTimeToSchool(targetStation, startTimes, currentTime, true);
                }
            }
            Log.d("startTimes", Arrays.toString(startTimes));
        }

        //학교 -> 정류장인 경우
        else{
            //학기 or 계절학기일 경우
            Log.d("기간", "계절학기/학기");
            if (isSeasonalSemester() || isSemester()) {
                //주말
                if (isWeekend(currentDay)) {
                    //기흥역 버스
                    if (targetStation.equals("기흥역")) {
                        //계절학기/학기 중 주말엔 기흥역 노선이 없습니다.
                        //에러 처리
                        dialog.setMessage("주말에는 기흥역 노선이 없습니다.");
                        dialog.show();
                    }
                    // 그 외 버스
                    else {
                        startTimes = Search.FindClosestBus(currentTime, WEEKEND_TIMETABLE);
                        arrivalData = getWeekendVacationArrivalTime(targetStation, startTimes[1], false);
                    }
                }

                //평일
                else {
                    if (targetStation.equals("기흥역")) {
                        if(isSemester()) {
                            startTimes = Search.FindClosestBus(currentTime, GHSTATION_WEEKDAY_TIMETABLE);
                            arrivalData = getWeekdayArrivalTime(targetStation, startTimes[1], false);
                        }
                        else{
                            //계절학기 중 평일에는 기흥역 노선이 없습니다.
                            //에러처리
                            dialog.setMessage("계절학기 평일에는 기흥역 노선이 없습니다.");
                            dialog.show();
                        }
                    }
                    //시내, 명지대역 노선이 겹치는 버스
                    else if (Arrays.asList(CITY_WEEKDAY_STATIONS).contains(targetStation) && Arrays.asList(MJSTATION_WEEKDAY_STATIONS).contains(targetStation)) {
                        startTimes = Search.FindClosestBus(currentTime, INTEGRATED_WEEKDAY_TIMETABLE);
                        arrivalData = getWeekdayArrivalTime(targetStation, startTimes[1], false);
                    }
                    //시내
                    else if (Arrays.asList(CITY_WEEKDAY_STATIONS).contains(targetStation)) {
                        startTimes = Search.FindClosestBus(currentTime, CITY_WEEKDAY_TIMETABLE);
                        arrivalData = getWeekdayArrivalTime(targetStation, startTimes[1], false);
                    }
                    //명지대역
                    else {
                        startTimes = Search.FindClosestBus(currentTime, MJSTATION_WEEKDAY_TIMETABLE);
                        arrivalData = getWeekdayArrivalTime(targetStation, startTimes[1], false);
                    }
                }
            }
            //(동/하계) 방학 기간일 때
            else{
                Log.d("기간", "방학");
                if (targetStation.equals("기흥역")) {
                    //방학 중 기흥역 노선이 없습니다.
                    //에러 처리
                    dialog.setMessage("방학에는 기흥역 노선이 없습니다.");
                    dialog.show();
                }
                // 그 외 버스
                else {
                    startTimes = Search.FindClosestBus(currentTime, WEEKEND_TIMETABLE);
                    arrivalData = getWeekendVacationArrivalTime(targetStation, startTimes[1], false);
                }
            }
            Log.d("startTimes", Arrays.toString(startTimes));
        }
        return arrivalData;
    }

    private void loadData(){
        if(isSemester()){
            //학기 중 평일: 모든 노선 O
            MJSTATION_WEEKDAY_STATIONS = context.getResources().getStringArray(R.array.MJSTATION_WEEKDAY_STATIONS);
            MJSTATION_WEEKDAY_TIMETABLE = context.getResources().getStringArray(R.array.MJSTATION_SEMESTER_WEEKDAY_TIMETABLE);
            CITY_WEEKDAY_STATIONS = context.getResources().getStringArray(R.array.CITY_WEEKDAY_STATIONS);
            CITY_WEEKDAY_TIMETABLE = context.getResources().getStringArray(R.array.CITY_SEMESTER_WEEKDAY_TIMETABLE);
            INTEGRATED_WEEKDAY_TIMETABLE = context.getResources().getStringArray(R.array.INTEGRATED_SEMESTER_WEEKDAY_TIMETABLE);
            GHSTATION_WEEKDAY_STATIONS = context.getResources().getStringArray(R.array.GHSTATION_WEEKDAY_STATIONS);
            GHSTATION_WEEKDAY_TIMETABLE = context.getResources().getStringArray(R.array.GHSTATION_SEMESTER_WEEKDAY_TIMETABLE);
            WEEKEND_STATIONS = context.getResources().getStringArray(R.array.WEEKEND_VACTION_STATIONS);
            WEEKEND_TIMETABLE = context.getResources().getStringArray(R.array.INTEGRATED_VACTION_OR_WEEKEND_TIMETABLE);
        }
        else if(isSeasonalSemester()){
            //계절 학기 중 평일: 모든 노선 O
            MJSTATION_WEEKDAY_STATIONS = context.getResources().getStringArray(R.array.MJSTATION_WEEKDAY_STATIONS);
            MJSTATION_WEEKDAY_TIMETABLE = context.getResources().getStringArray(R.array.MJSTATION_SEMESTER_WEEKDAY_TIMETABLE);
            CITY_WEEKDAY_STATIONS = context.getResources().getStringArray(R.array.CITY_WEEKDAY_STATIONS);
            CITY_WEEKDAY_TIMETABLE = context.getResources().getStringArray(R.array.CITY_VACATION_SEMESTER_WEEKDAY_TIMETABLE);
            INTEGRATED_WEEKDAY_TIMETABLE = context.getResources().getStringArray(R.array.INTEGRATED_VACATION_SEMESTER_WEEKDAY_TIMETABLE);
            WEEKEND_STATIONS = context.getResources().getStringArray(R.array.WEEKEND_VACTION_STATIONS);
            WEEKEND_TIMETABLE = context.getResources().getStringArray(R.array.INTEGRATED_VACTION_OR_WEEKEND_TIMETABLE);
        }
        else{
            //주말 and 방학: 명지대/시내 통합노선
            WEEKEND_STATIONS = context.getResources().getStringArray(R.array.WEEKEND_VACTION_STATIONS);
            WEEKEND_TIMETABLE = context.getResources().getStringArray(R.array.INTEGRATED_VACTION_OR_WEEKEND_TIMETABLE);
        }
    }

    private boolean isWeekend(String days){
        return (days.equals("토")||days.equals("일"));
    }


    private boolean isSemester() {
        /**
         * 2022.03.02 ~ 06.14
         * 2022.09.01 ~ 12.13
         */
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date today = dateFormat.parse(String.valueOf(LocalDate.now()));

            //TEST
            //today = dateFormat.parse("2023-01-12"); //방학
            //today = dateFormat.parse("2022-03-05");     //학기
            //today = dateFormat.parse("2022-06-22");     //계절학기

            Date spring_start = dateFormat.parse("2022-03-02");
            Date spring_end = dateFormat.parse("2022-06-14");
            Date autumn_start = dateFormat.parse("2022-09-01");
            Date autumn_end = dateFormat.parse("2022-12-13");

            return spring_start.before(today) && spring_end.after(today) || autumn_start.before(today) && autumn_end.after(today);

        } catch (Exception e) {

        }
        return true;
    }

    private boolean isSeasonalSemester() {
        /**
         * 2022.06.20 ~ 2022.07.08
         * 2022.12.22 ~ 2023.01.11
         */
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            Date today = dateFormat.parse(String.valueOf(LocalDate.now()));

            //TEST
            //today = dateFormat.parse("2023-01-12"); //방학
            //today = dateFormat.parse("2022-03-05");     //학기
            //today = dateFormat.parse("2022-06-22");     //계절학기


            Date summer_start = dateFormat.parse("2022-06-20");
            Date summer_end = dateFormat.parse("2022-07-08");
            Date winter_start = dateFormat.parse("2022-12-22");
            Date winter_end = dateFormat.parse("2023-01-11");

            return summer_start.before(today) && summer_end.after(today) || winter_start.before(today) && winter_end.after(today);

        } catch (Exception e) {

        }

        return true;
    }


    //arrivalData :
    // arrivalData[0] = 학교 도착 예정 시간
    // arrivalData[1] = 버스가 정류장에 도착할 예정시간
    private ArrivalData getWeekendVacationArrivalTime(String targetStation, String startTime, boolean toSchool){

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
    private ArrivalData getWeekdayArrivalTime(String targetStation, String startTime, boolean toSchool){

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
    private ArrivalData compareArrivalTimeToSchool(String startStation, String [] startTimes, String currentTime, boolean isWeekend){

        ArrivalData arrivalData;

        ArrivalData bus1ArrivalData;
        ArrivalData bus2ArrivalData;

        //주말이면
        if (isWeekend) {
            bus1ArrivalData = getWeekendVacationArrivalTime(startStation, startTimes[0], true);
            bus2ArrivalData = getWeekendVacationArrivalTime(startStation, startTimes[1], true);
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

}
