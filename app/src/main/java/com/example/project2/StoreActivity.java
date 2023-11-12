package com.example.project2;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreActivity extends AppCompatActivity {
    private String name;
    private String openTime;
    private String closeTime;
    private int currentWaitTime;
    public int currNumCustomers; //pull from DB
    private String loc;
    public HashMap<String, String> storeLocs;
    public HashMap<String, Integer> storeOpenTimes;
    public HashMap<String, Integer> storeCloseTimes;
    public HashMap<String, Double> storeLat;
    public HashMap<String, Double> storeLong;
    public List<Store> stores = new ArrayList<Store>(); //nia
    TextView nameView;
    TextView hoursView;
    ImageView imageView;
    Store store;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);
        Intent intent = getIntent();
        store = (Store) getIntent().getSerializableExtra("store");
        initStoreDetails();
    }
    public StoreActivity(){
        currNumCustomers = 0;
        storeLocs = new HashMap<>();
        storeOpenTimes = new HashMap<>();
        storeCloseTimes = new HashMap<>();
        storeLat = new HashMap<>();
        storeLong = new HashMap<>();
        storeLocs.put("amazon", "3096 McClintock Ave #1415, Los Angeles, CA 90089");
        storeLocs.put("bank of america", "3201 S Hoover St STE 1860, Los Angeles, CA 90089");
        storeLocs.put("cava", "3201 S Hoover St Suite 1840, Los Angeles, CA 90089");
        storeLocs.put("chinese street food", "3201 S Hoover St #1870, Los Angeles, CA 90007");
        storeLocs.put("city tacos", "835 W Jefferson Blvd Ste 1735, Los Angeles, CA 90089");
        storeLocs.put("corepower yoga", "3131 S Hoover St Unit 9-D, Los Angeles, CA 90007");
        storeLocs.put("cvs", "3131 S Hoover St #1910, Los Angeles, CA 90089");
        storeLocs.put("dulce", "3096 McClintock Ave Ste 1420, Los Angeles, CA 90007");
        storeLocs.put("fedex", "929 W Jefferson Blvd Suite 1670, Los Angeles, CA 90089");
        storeLocs.put("fruit + candy", "3201 S Hoover St #1815, Los Angeles, CA 90089");
        storeLocs.put("greenleaf", "929 W Jefferson Blvd #1650, Los Angeles, CA 90089");
        storeLocs.put("honeybird", "3201 S Hoover St #1835, Los Angeles, CA 90089");
        storeLocs.put("il giardino", "3201 S Hoover St #1850, Los Angeles, CA 90089");
        storeLocs.put("insomnia cookies", "929 W Jefferson Blvd #1620, Los Angeles, CA 90089");
        storeLocs.put("kaitlyn", "835 W Jefferson Blvd #1715, Los Angeles, CA 90007");
        storeLocs.put("kobunga", "929 W Jefferson Blvd Suite 1610, Los Angeles, CA 90007");
        storeLocs.put("lululemon", "3201 S Hoover St, Los Angeles, CA 90089");
        storeLocs.put("mac repair clinic", "929 W Jefferson Blvd #1660, Los Angeles, CA 90007");
        storeLocs.put("ramen kenjo", "929 W Jefferson Blvd, Los Angeles, CA 90007");
        storeLocs.put("rock and reillys", "3201 S Hoover St, Los Angeles, CA 90089");
        storeLocs.put("the sammiche shoppe", "3201 S Hoover St 1845 Suite 1845, Los Angeles, CA 90089");
        storeLocs.put("simply nail bar", "3201 S Hoover St #1830, Los Angeles, CA 90089");
        storeLocs.put("sole bicycle", "835 W Jefferson Blvd #1750, Los Angeles, CA 90089");
        storeLocs.put("starbucks", "3201 S Hoover St, Los Angeles, CA 90089");
        storeLocs.put("stout burgers & beers", "835 W Jefferson Blvd Suite 1710, Los Angeles, CA 90089");
        storeLocs.put("sunlife organics", "929 W Jefferson Blvd #1640, Los Angeles, CA 90089");
        storeLocs.put("target", "3131 S Hoover St #1910, Los Angeles, CA 90089");
        storeLocs.put("trader joes", "3131 S Hoover St Ste 1920, Los Angeles, CA 90089");
        storeLocs.put("usc credit union", "3096 McClintock Ave Suite 1430, Los Angeles, CA 90089");
        storeLocs.put("usc roski eye institute", "835 W Jefferson Blvd #1720, Los Angeles, CA 90007");
        storeLocs.put("village cobbler", "3201 S Hoover St Suite 1865, Los Angeles, CA 90089");
        storeLocs.put("workshop salon and boutique", "3131 S Hoover St Suite 1930, Los Angeles, CA 90089");


        storeOpenTimes.put("amazon", 9);
        storeOpenTimes.put("bank of america", 10);
        storeOpenTimes.put("cava", 1045);
        storeOpenTimes.put("chinese street food", 11);
        storeOpenTimes.put("city tacos", 1130);
        storeOpenTimes.put("corepower yoga", 6);
        storeOpenTimes.put("cvs", 7);
        storeOpenTimes.put("dulce", 8);
        storeOpenTimes.put("fedex", 9);
        storeOpenTimes.put("fruit + candy", 10);
        storeOpenTimes.put("greenleaf", 11);
        storeOpenTimes.put("honeybird", 11);
        storeOpenTimes.put("il giardino", 11);
        storeOpenTimes.put("insomnia cookies", 11);
        storeOpenTimes.put("kaitlyn", 11);
        storeOpenTimes.put("kobunga", 11);
        storeOpenTimes.put("lululemon", 10);
        storeOpenTimes.put("mac repair clinic", 11);
        storeOpenTimes.put("ramen kenjo", 11);
        storeOpenTimes.put("rock and reillys", 12);
        storeOpenTimes.put("the sammiche shoppe", 11);
        storeOpenTimes.put("simply nail bar", 10);
        storeOpenTimes.put("sole bicycle", 10);
        storeOpenTimes.put("starbucks", 5);
        storeOpenTimes.put("stout burgers & beers", 11);
        storeOpenTimes.put("sunlife organics", 8);
        storeOpenTimes.put("target", 7);
        storeOpenTimes.put("trader joes", 8);
        storeOpenTimes.put("usc credit union", 9);
        storeOpenTimes.put("usc roski eye institute", 8);
        storeOpenTimes.put("village cobbler", 9);
        storeOpenTimes.put("workshop salon and boutique", 10);


        storeCloseTimes.put("amazon", 21);
        storeCloseTimes.put("bank of america", 16);
        storeCloseTimes.put("cava", 22);
        storeCloseTimes.put("chinese street food", 1);
        storeCloseTimes.put("city tacos", 21);
        storeCloseTimes.put("corepower yoga", 22);
        storeCloseTimes.put("cvs", 22);
        storeCloseTimes.put("dulce", 21);
        storeCloseTimes.put("fedex", 18);
        storeCloseTimes.put("fruit + candy", 20);
        storeCloseTimes.put("greenleaf", 21);
        storeCloseTimes.put("honeybird", 20);
        storeCloseTimes.put("il giardino", 21);
        storeCloseTimes.put("insomnia cookies", 3);
        storeCloseTimes.put("kaitlyn", 19);
        storeCloseTimes.put("kobunga", 21);
        storeCloseTimes.put("lululemon", 19);
        storeCloseTimes.put("mac repair clinic", 18);
        storeCloseTimes.put("ramen kenjo", 22);
        storeCloseTimes.put("rock and reillys", 2);
        storeCloseTimes.put("the sammiche shoppe", 20);
        storeCloseTimes.put("simply nail bar", 19);
        storeCloseTimes.put("sole bicycle", 18);
        storeCloseTimes.put("starbucks", 23);
        storeCloseTimes.put("stout burgers & beers", 21);
        storeCloseTimes.put("sunlife organics", 19);
        storeCloseTimes.put("target", 22);
        storeCloseTimes.put("trader joes", 22);
        storeCloseTimes.put("usc credit union", 18);
        storeCloseTimes.put("usc roski eye institute", 19);
        storeCloseTimes.put("village cobbler", 18);
        storeCloseTimes.put("workshop salon and boutique", 19);

        storeLat.put("amazon", 34.02505);
        storeLat.put("bank of america", 34.02537);
        storeLat.put("cava", 34.02537);
        storeLat.put("chinese street food", 34.02551);
        storeLat.put("city tacos", 34.02414);
        storeLat.put("corepower yoga", 34.02620);
        storeLat.put("cvs", 34.02346);
        storeLat.put("dulce", 34.02535);
        storeLat.put("fedex", 34.02412);
        storeLat.put("fruit + candy", 34.02346);
        storeLat.put("greenleaf", 34.02453);
        storeLat.put("honeybird", 34.02346);
        storeLat.put("il giardino", 34.02346);
        storeLat.put("insomnia cookies", 34.02453);
        storeLat.put("kaitlyn", 34.02379);
        storeLat.put("lululemon",34.02527);
        storeLat.put("mac repair clinic", 34.02453);
        storeLat.put("ramen kenjo", 34.02527);
        storeLat.put("rock and reillys", 34.02537);
        storeLat.put("the sammiche shoppe", 34.02346);
        storeLat.put("simply nail bar", 34.02346);
        storeLat.put("sole bicycle", 34.02379);
        storeLat.put("starbucks", 34.02537);
        storeLat.put("stout burgers & beers", 34.02414);
        storeLat.put("sunlife organics", 34.024924);
        storeLat.put("target", 34.02346);
        storeLat.put("trader joes", 34.02620);
        storeLat.put("usc credit union", 34.02574);
        storeLat.put("usc roski eye institute", 34.02379);
        storeLat.put("village cobbler", 34.02537);
        storeLat.put("workshop salon and boutique", 34.02620);
        storeLat.put("kobunga",34.02527);

        storeLong.put("amazon", -118.28709);
        storeLong.put("bank of america", -118.28403);
        storeLong.put("cava", -118.28403);
        storeLong.put("chinese street food", -118.28387);
        storeLong.put("city tacos", -118.28453);
        storeLong.put("corepower yoga", -118.28406);
        storeLong.put("cvs", -118.28361);
        storeLong.put("dulce", -118.28541);
        storeLong.put("fedex", -118.28485);
        storeLong.put("fruit + candy", -118.28361);
        storeLong.put("greenleaf", -118.28572);
        storeLong.put("honeybird", -118.28361);
        storeLong.put("il giardino", -118.28361);
        storeLong.put("insomnia cookies", -118.28572);
        storeLong.put("kaitlyn", -118.28407);
        storeLong.put("kobunga", -118.28591);
        storeLong.put("lululemon", -118.28403);
        storeLong.put("mac repair clinic", -118.28572);
        storeLong.put("ramen kenjo", -118.28591);
        storeLong.put("rock and reillys", -118.28403);
        storeLong.put("the sammiche shoppe", -118.28361);
        storeLong.put("simply nail bar", -118.28361);
        storeLong.put("sole bicycle", -118.28407);
        storeLong.put("starbucks", -118.28403);
        storeLong.put("stout burgers & beers", -118.28453);
        storeLong.put("sunlife organics", -118.28513);
        storeLong.put("target", -118.28361);
        storeLong.put("trader joes", -118.28406);
        storeLong.put("usc credit union", -118.28521);
        storeLong.put("usc roski eye institute", -118.28407);
        storeLong.put("village cobbler", -118.28403);
        storeLong.put("workshop salon and boutique", -118.28406);
    } //0 arg constructor for Intent purposes
    public StoreActivity(String name){
        this.name = name;
        storeLocs = new HashMap<>();
        storeOpenTimes = new HashMap<>();
        storeCloseTimes = new HashMap<>();
        storeLat = new HashMap<>();
        storeLong = new HashMap<>();
        storeLocs.put("amazon", "3096 McClintock Ave #1415, Los Angeles, CA 90089");
        storeLocs.put("bank of america", "3201 S Hoover St STE 1860, Los Angeles, CA 90089");
        storeLocs.put("cava", "3201 S Hoover St Suite 1840, Los Angeles, CA 90089");
        storeLocs.put("chinese street food", "3201 S Hoover St #1870, Los Angeles, CA 90007");
        storeLocs.put("city tacos", "835 W Jefferson Blvd Ste 1735, Los Angeles, CA 90089");
        storeLocs.put("corepower yoga", "3131 S Hoover St Unit 9-D, Los Angeles, CA 90007");
        storeLocs.put("cvs", "3131 S Hoover St #1910, Los Angeles, CA 90089");
        storeLocs.put("dulce", "3096 McClintock Ave Ste 1420, Los Angeles, CA 90007");
        storeLocs.put("fedex", "929 W Jefferson Blvd Suite 1670, Los Angeles, CA 90089");
        storeLocs.put("fruit + candy", "3201 S Hoover St #1815, Los Angeles, CA 90089");
        storeLocs.put("greenleaf", "929 W Jefferson Blvd #1650, Los Angeles, CA 90089");
        storeLocs.put("honeybird", "3201 S Hoover St #1835, Los Angeles, CA 90089");
        storeLocs.put("il giardino", "3201 S Hoover St #1850, Los Angeles, CA 90089");
        storeLocs.put("insomnia cookies", "929 W Jefferson Blvd #1620, Los Angeles, CA 90089");
        storeLocs.put("kaitlyn", "835 W Jefferson Blvd #1715, Los Angeles, CA 90007");
        storeLocs.put("kobunga", "929 W Jefferson Blvd Suite 1610, Los Angeles, CA 90007");
        storeLocs.put("lululemon", "3201 S Hoover St, Los Angeles, CA 90089");
        storeLocs.put("mac repair clinic", "929 W Jefferson Blvd #1660, Los Angeles, CA 90007");
        storeLocs.put("ramen kenjo", "929 W Jefferson Blvd, Los Angeles, CA 90007");
        storeLocs.put("rock and reillys", "3201 S Hoover St, Los Angeles, CA 90089");
        storeLocs.put("the sammiche shoppe", "3201 S Hoover St 1845 Suite 1845, Los Angeles, CA 90089");
        storeLocs.put("simply nail bar", "3201 S Hoover St #1830, Los Angeles, CA 90089");
        storeLocs.put("sole bicycle", "835 W Jefferson Blvd #1750, Los Angeles, CA 90089");
        storeLocs.put("starbucks", "3201 S Hoover St, Los Angeles, CA 90089");
        storeLocs.put("stout burgers & beers", "835 W Jefferson Blvd Suite 1710, Los Angeles, CA 90089");
        storeLocs.put("sunlife organics", "929 W Jefferson Blvd #1640, Los Angeles, CA 90089");
        storeLocs.put("target", "3131 S Hoover St #1910, Los Angeles, CA 90089");
        storeLocs.put("trader joes", "3131 S Hoover St Ste 1920, Los Angeles, CA 90089");
        storeLocs.put("usc credit union", "3096 McClintock Ave Suite 1430, Los Angeles, CA 90089");
        storeLocs.put("usc roski eye institute", "835 W Jefferson Blvd #1720, Los Angeles, CA 90007");
        storeLocs.put("village cobbler", "3201 S Hoover St Suite 1865, Los Angeles, CA 90089");
        storeLocs.put("workshop salon and boutique", "3131 S Hoover St Suite 1930, Los Angeles, CA 90089");


        storeOpenTimes.put("amazon", 9);
        storeOpenTimes.put("bank of america", 10);
        storeOpenTimes.put("cava", 1045);
        storeOpenTimes.put("chinese street food", 11);
        storeOpenTimes.put("city tacos", 1130);
        storeOpenTimes.put("corepower yoga", 6);
        storeOpenTimes.put("cvs", 7);
        storeOpenTimes.put("dulce", 8);
        storeOpenTimes.put("fedex", 9);
        storeOpenTimes.put("fruit + candy", 10);
        storeOpenTimes.put("greenleaf", 11);
        storeOpenTimes.put("honeybird", 11);
        storeOpenTimes.put("il giardino", 11);
        storeOpenTimes.put("insomnia cookies", 11);
        storeOpenTimes.put("kaitlyn", 11);
        storeOpenTimes.put("kobunga", 11);
        storeOpenTimes.put("lululemon", 10);
        storeOpenTimes.put("mac repair clinic", 11);
        storeOpenTimes.put("ramen kenjo", 11);
        storeOpenTimes.put("rock and reillys", 12);
        storeOpenTimes.put("the sammiche shoppe", 11);
        storeOpenTimes.put("simply nail bar", 10);
        storeOpenTimes.put("sole bicycle", 10);
        storeOpenTimes.put("starbucks", 5);
        storeOpenTimes.put("stout burgers & beers", 11);
        storeOpenTimes.put("sunlife organics", 8);
        storeOpenTimes.put("target", 7);
        storeOpenTimes.put("trader joes", 8);
        storeOpenTimes.put("usc credit union", 9);
        storeOpenTimes.put("usc roski eye institute", 8);
        storeOpenTimes.put("village cobbler", 9);
        storeOpenTimes.put("workshop salon and boutique", 10);


        storeCloseTimes.put("amazon", 21);
        storeCloseTimes.put("bank of america", 16);
        storeCloseTimes.put("cava", 22);
        storeCloseTimes.put("chinese street food", 1);
        storeCloseTimes.put("city tacos", 21);
        storeCloseTimes.put("corepower yoga", 22);
        storeCloseTimes.put("cvs", 22);
        storeCloseTimes.put("dulce", 21);
        storeCloseTimes.put("fedex", 18);
        storeCloseTimes.put("fruit + candy", 20);
        storeCloseTimes.put("greenleaf", 21);
        storeCloseTimes.put("honeybird", 20);
        storeCloseTimes.put("il giardino", 21);
        storeCloseTimes.put("insomnia cookies", 3);
        storeCloseTimes.put("kaitlyn", 19);
        storeCloseTimes.put("kobunga", 21);
        storeCloseTimes.put("lululemon", 19);
        storeCloseTimes.put("mac repair clinic", 18);
        storeCloseTimes.put("ramen kenjo", 22);
        storeCloseTimes.put("rock and reillys", 2);
        storeCloseTimes.put("the sammiche shoppe", 20);
        storeCloseTimes.put("simply nail bar", 19);
        storeCloseTimes.put("sole bicycle", 18);
        storeCloseTimes.put("starbucks", 23);
        storeCloseTimes.put("stout burgers & beers", 21);
        storeCloseTimes.put("sunlife organics", 19);
        storeCloseTimes.put("target", 22);
        storeCloseTimes.put("trader joes", 22);
        storeCloseTimes.put("usc credit union", 18);
        storeCloseTimes.put("usc roski eye institute", 19);
        storeCloseTimes.put("village cobbler", 18);
        storeCloseTimes.put("workshop salon and boutique", 19);

        storeLat.put("amazon", 34.02505);
        storeLat.put("bank of america", 34.02537);
        storeLat.put("cava", 34.02537);
        storeLat.put("chinese street food", 34.02551);
        storeLat.put("city tacos", 34.02414);
        storeLat.put("corepower yoga", 34.02620);
        storeLat.put("cvs", 34.02346);
        storeLat.put("dulce", 34.02535);
        storeLat.put("fedex", 34.02412);
        storeLat.put("fruit + candy", 34.02346);
        storeLat.put("greenleaf", 34.02453);
        storeLat.put("honeybird", 34.02346);
        storeLat.put("il giardino", 34.02346);
        storeLat.put("insomnia cookies", 34.02453);
        storeLat.put("kaitlyn", 34.02379);
        storeLat.put("lululemon",34.02527);
        storeLat.put("mac repair clinic", 34.02453);
        storeLat.put("ramen kenjo", 34.02527);
        storeLat.put("rock and reillys", 34.02537);
        storeLat.put("the sammiche shoppe", 34.02346);
        storeLat.put("simply nail bar", 34.02346);
        storeLat.put("sole bicycle", 34.02379);
        storeLat.put("starbucks", 34.02537);
        storeLat.put("stout burgers & beers", 34.02414);
        storeLat.put("sunlife organics", 34.024924);
        storeLat.put("target", 34.02346);
        storeLat.put("trader joes", 34.02620);
        storeLat.put("usc credit union", 34.02574);
        storeLat.put("usc roski eye institute", 34.02379);
        storeLat.put("village cobbler", 34.02537);
        storeLat.put("workshop salon and boutique", 34.02620);
        storeLat.put("kobunga",34.02527);

        storeLong.put("amazon", -118.28709);
        storeLong.put("bank of america", -118.28403);
        storeLong.put("cava", -118.28403);
        storeLong.put("chinese street food", -118.28387);
        storeLong.put("city tacos", -118.28453);
        storeLong.put("corepower yoga", -118.28406);
        storeLong.put("cvs", -118.28361);
        storeLong.put("dulce", -118.28541);
        storeLong.put("fedex", -118.28485);
        storeLong.put("fruit + candy", -118.28361);
        storeLong.put("greenleaf", -118.28572);
        storeLong.put("honeybird", -118.28361);
        storeLong.put("il giardino", -118.28361);
        storeLong.put("insomnia cookies", -118.28572);
        storeLong.put("kaitlyn", -118.28407);
        storeLong.put("kobunga", -118.28591);
        storeLong.put("lululemon", -118.28403);
        storeLong.put("mac repair clinic", -118.28572);
        storeLong.put("ramen kenjo", -118.28591);
        storeLong.put("rock and reillys", -118.28403);
        storeLong.put("the sammiche shoppe", -118.28361);
        storeLong.put("simply nail bar", -118.28361);
        storeLong.put("sole bicycle", -118.28407);
        storeLong.put("starbucks", -118.28403);
        storeLong.put("stout burgers & beers", -118.28453);
        storeLong.put("sunlife organics", -118.28513);
        storeLong.put("target", -118.28361);
        storeLong.put("trader joes", -118.28406);
        storeLong.put("usc credit union", -118.28521);
        storeLong.put("usc roski eye institute", -118.28407);
        storeLong.put("village cobbler", -118.28403);
        storeLong.put("workshop salon and boutique", -118.28406);
    }
    public String displayStoreDetails(){
        return "Store name: " + getStoreName() + "\n Opening time: " + getOpenTime_asString() +
                " AM \n Closing time: " + getCloseTime_asString() + " PM\n Current wait: "
                + Integer.toString(getCurrentWaitTime()) + " minutes\n Current number of customers: " +
                Integer.toString(currNumCustomers) + "\n Store Location: " + getLocation();
    }

    public void displayRoute(){
        //not sure yet how to do this
    }
    public String getLocation() {
        loc = storeLocs.get(name);
        return loc;
    }
    public String getStoreName() { return name; }
    public String getOpenTime_asString() {
        openTime = Integer.toString(getOpenTime_asInt());
        if(openTime.length() == 4) openTime = openTime.substring(0, 2) + ":" + openTime.substring(2, 4) + " AM";
        else if(openTime.length() == 2) openTime = openTime + ":00 AM";
        else if(openTime.length() == 1) openTime = "0" + openTime + ":00 AM";
        else openTime = openTime.substring(0, 1) + ":" + openTime.substring(1, 3) + " AM";
        return openTime;
    }
    public int getOpenTime_asInt(){
        return storeOpenTimes.get(name);
    }
    public int getCloseTime_asInt(){
        return storeCloseTimes.get(name);
    }

    public String getCloseTime_asString() {
        int x = storeCloseTimes.get(name);
        boolean am = true;
        if(x > 12) {
            x -= 12;
            closeTime = Integer.toString(x);
            if(closeTime.length() == 4) closeTime = closeTime.substring(0, 2) + ":" + closeTime.substring(2, 4) + " PM";
            else if(closeTime.length() == 2) closeTime = closeTime + ":00 PM";
            else if(closeTime.length() == 1) closeTime = "0" + closeTime + ":00 PM";
            else closeTime = closeTime.substring(0, 1) + ":" + closeTime.substring(1, 3) + " PM";
        }
        else if(x == 12){
            closeTime = "12:00 PM";
        }
        else if(x == 0){
            closeTime = "12:00 AM";
        }
        else{
            closeTime = Integer.toString(x);
            if(closeTime.length() == 4) closeTime = closeTime.substring(0, 2) + ":" + closeTime.substring(2, 4) + " AM";
            else if(closeTime.length() == 2) closeTime = closeTime + ":00 AM";
            else if(closeTime.length() == 1) closeTime = "0" + closeTime + ":00 AM";
            else closeTime = closeTime.substring(0, 1) + ":" + closeTime.substring(1, 3) + " AM";
        }
        return closeTime;
    }
    public int getCurrentWaitTime() { return getCurrNumCustomers() * 2; }
    private int getCurrNumCustomers(){
        //pull from DB
        return currNumCustomers;
    }
    public void toHomeActivity(View view){
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
    public void createReminder(View view){
        int hour, min; //time you want to arrive at the store by
        Spinner AMPMSpinner = (Spinner) findViewById(R.id.spinner_ampm);
        Spinner hourSpinner = (Spinner) findViewById(R.id.spinner_hour);
        hour = Integer.valueOf(hourSpinner.getSelectedItem().toString());
        if(AMPMSpinner.getSelectedItem() == "PM") hour += 12;
        Spinner minSpinner = (Spinner) findViewById(R.id.spinner_minutes);
        min = Integer.valueOf(minSpinner.getSelectedItem().toString());
        Reminder r = new Reminder(hour, min, store);
        //TODO: validate reminder
        //TODO: add reminder to database
        Intent intent = new Intent(this, RemindersActivity.class);
        intent.putExtra("reminder", r);
        startActivity(intent);
    }
    public void initStoreDetails(){
        nameView = (TextView)findViewById(R.id.name);
        hoursView = (TextView)findViewById(R.id.hours);
        imageView = (ImageView)findViewById(R.id.image);

        nameView.setText(store.getTitle());
        hoursView.setText(store.getHoursAsString());
        imageView.setImageResource(store.getImage());
    }
}

