package com.example.project2;

import java.util.ArrayList;
import java.util.List;

public class User {
    public User(String nameStr,String emailStr, String phoneStr, String passStr){
        name = nameStr;
        email = emailStr;
        phone = phoneStr;
        pass = passStr;
        reminders = new ArrayList<>();
    };
    public String name;
    public String email;
    public String phone;
    public String pass;
    public List<String> reminders;
}