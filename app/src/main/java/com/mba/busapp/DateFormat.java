package com.mba.busapp;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@FunctionalInterface
interface Compare {
    public int compareTo(String src, String dest);
}

@SuppressWarnings("serial")
public class DateFormat implements Compare, Serializable {

    int totalSec;
    String timeFormat;

    //DateFormat으로 받은 HH:MM format을 비교할 수 있도록 분으로 변환한다.
    public DateFormat(String date){
        timeFormat = date;
        String[] time = date.split(":");
        totalSec = Integer.parseInt(time[0])*3600 + Integer.parseInt(time[1])*60;
    }

    //파라메터가 없을 경우 현재 시간으로 데이터를 생성한다.
    public DateFormat(){
        long now = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date = new Date(now);
        timeFormat = format.format(date);
        String[] time = timeFormat.split(":");
        totalSec = Integer.parseInt(time[0])*3600 + Integer.parseInt(time[1])*60;
    }

    public DateFormat(int totalSec){
        this.totalSec = totalSec;
        int hour = totalSec / 3600;
        int min = (totalSec - hour * 3600)/60;

        if (hour < 10 && min >= 10) this.timeFormat = "0" + hour + ":" + min;
        else if (hour < 10 && min < 10) this.timeFormat = "0" + hour + ":0" + min;
        else if (hour >= 10 && min < 10) this.timeFormat = hour + ":0" + min;
        else this.timeFormat = hour + ":" + min;
    }
    //분 형태의 시간을 반환한다.
    public int getTotalSec() {
        return totalSec;
    }

    //xx:xx 포멧 형태의 시간을 반환한다.
    public String getTime(){
        return timeFormat;
    }

    //파라메터로 주어진 시간(초단위)를 더한다.
    public void addSecTime(int time){
        totalSec = time + totalSec;

        DateFormat newDf = new DateFormat(totalSec);
        timeFormat = newDf.timeFormat;
    }

    public int compareTo(String src, String dest) {
        DateFormat srcTime = new DateFormat(src);
        DateFormat destTime = new DateFormat(dest);
        return srcTime.getTotalSec() - destTime.getTotalSec();
    }

    /**
     * 객체 생성하지 않고 비교하기 위한 compare
     * @param src String
     * @param target String
     * @return  양수  if src > target
     *           0   if src == target
     *          음수  if src < target
     */
    public static int compare(String src, String target) {
        DateFormat srcTime = new DateFormat(src);
        DateFormat destTime = new DateFormat(target);

        return srcTime.getTotalSec() - destTime.getTotalSec();
    }

    public static int compare(DateFormat src, DateFormat target) {
        return src.getTotalSec() - target.getTotalSec();
    }

    /**
     * 시간 덧셈 후 String
     * @param offset Full Format 시간 (08:00, 09:15 .. ETC)
     * @param delta minute
     * @return offset + delta
     */
    public static String addSecTime(String offset, int delta) {
        DateFormat src = new DateFormat(offset);
        return new DateFormat(src.getTotalSec() + delta).getTime();
    }

}
