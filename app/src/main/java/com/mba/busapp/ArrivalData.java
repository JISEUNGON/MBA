package com.mba.busapp;

//도착 정보를 담는 클래스
//버스의 도착 시간, 목적지에 도착할 시간, 해당 버스의 노선도 정보를 담는다

import java.util.ArrayList;

public class ArrivalData {
    DateFormat busArrivalTime;
    DateFormat arrivalTime;
    String routeType;
    ArrayList<String> lastStations;

    //Constructor
    public ArrivalData(String time){
        busArrivalTime = new DateFormat(time);
        arrivalTime = new DateFormat(time);
    }
    public ArrivalData(String busArrival, String arrival){
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

    //Getter Setter
    public DateFormat getBusArrivalTime() {
        return busArrivalTime;
    }

    public DateFormat getArrivalTime() {
        return arrivalTime;
    }

    public String getRouteType() {
        return routeType;
    }

    public ArrayList getLastStations() {
        if (lastStations == null) {
            lastStations = new ArrayList<>();
        }
        return lastStations;
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
