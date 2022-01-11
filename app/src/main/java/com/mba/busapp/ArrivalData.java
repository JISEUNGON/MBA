package com.mba.busapp;

//도착 정보를 담는 클래스
//버스의 도착 시간, 목적지에 도착할 시간, 해당 버스의 노선도 정보를 담는다

public class ArrivalData {
    DateFormat busArrivalTime;
    DateFormat arrivalTime;
    String routeType;

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

    public void setRouteType(String type){
        this.routeType = type;
    }
}
