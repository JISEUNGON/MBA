package com.mba.busapp;

//도착 정보를 담는 클래스
//버스의 도착 시간, 목적지에 도착할 시간, 해당 버스의 노선도 정보를 담는다

import java.io.Serializable;
import java.util.ArrayList;

//주어진 버스 타임에 대한 모든 데이터를 가지는 클래스
@SuppressWarnings("serial")
public class ArrivalData implements Serializable {
    //현재 시간, 도착 정류장
    //버스가 올시간, 도착 정류장에 도착할 시간
    //남은 정류장, 마지막 정류장
    DateFormat busArrivalTime;
    DateFormat arrivalTime;
    DateFormat currentTime;
    String routeType;
    String targetStation;
    boolean toSchool;
    ArrayList<String> restStations;
    ArrayList<String> lastStations;

    //Constructor
    public ArrivalData(String currentTime, String targetStation){
        this.targetStation = targetStation;
        busArrivalTime = new DateFormat(currentTime);
        arrivalTime = new DateFormat(currentTime);
    }
    public ArrivalData(String busArrival, String arrival, String targetStation){
        this.targetStation = targetStation;
        busArrivalTime = new DateFormat(busArrival);
        arrivalTime = new DateFormat(arrival);
    }

    //버스 도착 시간 계산
    public void addBusArrivalTime(int time){
        busArrivalTime.addSecTime(time);
    }
    //목적지 최종 도착 시간 계산
    public void addArrivalTime(int time){
        arrivalTime.addSecTime(time);
    }
    //남은 정류장 추가
    public void addRestStations(String station){
        if (restStations == null) {
            restStations = new ArrayList<>();
        }
        restStations.add(station);
    }

    //Getter Setter
    public DateFormat getBusArrivalTime() {
        return busArrivalTime;
    }

    public DateFormat getArrivalTime() {
        return arrivalTime;
    }

    public String getTargetStation() {return targetStation;}

    public boolean isToSchool(){return toSchool;}


    public DateFormat getCurrentTime() {return currentTime;}

    public String getRouteType() {
        return routeType;
    }
    public ArrayList getRestStaions() {
        if (restStations == null) {
            restStations = new ArrayList<>();
        }
        return restStations;
    }

    public ArrayList getLastStations() {
        if (lastStations == null) {
            lastStations = new ArrayList<>();
        }
        return lastStations;
    }

    public void setToSchool(boolean toSchool){this.toSchool = toSchool;}

    public void setTargetStation(String targetStation){this.targetStation = targetStation;}

    public void setCurrentTime(DateFormat currentTime){
        this.currentTime = currentTime;
    }

    public void setRouteType(String type, String currentTime){

        this.routeType = type;
        lastStations = new ArrayList<>();

        switch (routeType){
            case "명지대역노선" :
                //현재시간이 18:00 이후이면 명진당까지만
                if(DateFormat.compare(new DateFormat(currentTime), new DateFormat("18:00")) > 0){
                    lastStations.add("명진당");
                }
                else{
                    lastStations.add("명진당");
                    lastStations.add("제3공학관");
                }
                break;
            case "기흥역노선" :
                lastStations.add("버스관리사무소");
                break;
            case "시내노선":
                //현재시간이 18:00 이후이면 1공학관까지만
                if(DateFormat.compare(new DateFormat(currentTime), new DateFormat("18:00")) > 0){
                    lastStations.add("제1공학관");
                }
                else{
                    lastStations.add("제1공학관");
                    lastStations.add("제3공학관");
                }
                break;
            case "주말방학노선" :
                lastStations.add("제1공학관");
                lastStations.add("명현관");
                break;
            case "광역버스" :
                lastStations.add("명지대 버스정류장");
                break;

        }
    }
}
