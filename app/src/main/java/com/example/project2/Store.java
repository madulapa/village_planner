package com.example.project2;


import java.io.Serializable;

public class Store implements Serializable {
    int image;
    int openHour;
    int openMinutes;
    int closeHour;
    int closeMinutes;
    String title;
    String openHours;
    String closeHours;
    int estTime;

    public Store(){

    }

    public Store(int image, int openHour, int openMinutes, int closeHour, int closeMinutes, String title) {
        this.image = image;
        this.openHour = openHour;
        this.openMinutes = openMinutes;
        this.closeHour = closeHour;
        this.closeMinutes = closeMinutes;
        this.title = title;
        this.estTime = 5;
    }
    public Store(int image, String openHours, String closeHours, String title, int waitTime) {
        this.image = image;
        this.title = title;
        this.openHours = openHours;
        this.closeHours = closeHours;
//        this.estTime = waitTime;
        this.estTime = 5;
    }
    public Store(String title){
        this.title = title;
        this.estTime = 5;
    }
    //TODO: calculate wait time
    public void setWaitTime(){}

    public int getImage() {
        return image;
    }

    public int getOpenHour() {
        return openHour;
    }

    public int getOpenMinutes() {
        return openMinutes;
    }

    public int getCloseHour() {
        return closeHour;
    }

    public int getCloseMinutes() {
        return closeMinutes;
    }

    public String getTitle() {
        return title;
    }
    public String getOpenHours() {
        return openHours;
    }
    public String getCloseHours() {
        return closeHours;
    }

    public String getHoursAsString(){
        return openHours + " - " + closeHours;
    }

    //TODO: calculate estimated wait time
    public String getEstimatedWaitTime(){
       // return "Est. Wait Time: " + Integer.toString(estTime) + " minutes";
        return "Est. Wait Time: " + estTime + " minutes";
    }
    @Override
    public String toString(){
        return title;
    }
}
