package com.example.project2;

import java.io.Serializable;
import java.time.LocalTime;

public class Reminder implements Serializable {
    int arrivalHour;
    int arrivalMinute;
    int leaveHour;
    int leaveMinute;
    int waitTime;
    String id;
    Store dest;

    public Reminder(){
        arrivalHour = 0;
        arrivalMinute = 0;
        leaveHour = 0;
        leaveMinute = 0;
        waitTime = 0;
        id = "";
        dest = null;
    }

    public Reminder(int arrivalHour, int arrivalMinute, Store dest) {
        this.arrivalHour = arrivalHour;
        this.arrivalMinute = arrivalMinute;
        this.dest = dest;
        if(arrivalMinute < 10){
            this.leaveHour = arrivalHour-1;
            this.leaveMinute = 50+arrivalMinute;
        } else {
            this.leaveHour = arrivalHour;
            this.leaveMinute = arrivalMinute-10;
        }
    }
    public String getTimeAsString(int hour, int minute){
        String m = "AM";
        String min = String.valueOf(minute);
        if(hour >= 12) m = "PM"; hour -= 12;
        if(minute < 10) min = "0" + min;
        return String.valueOf(hour) + ":" + min + " " + m;
    }
    public int getArrivalHour() {
        return arrivalHour;
    }

    public int getArrivalMinute() {
        return arrivalMinute;
    }

    public int getLeaveHour() {
        return leaveHour;
    }

    public int getLeaveMinute() {
        return leaveMinute;
    }

    public Store getDest() {
        return dest;
    }

    public String getId() { return id; }

    public void setId(String id){
        this.id = id;
    }
}
