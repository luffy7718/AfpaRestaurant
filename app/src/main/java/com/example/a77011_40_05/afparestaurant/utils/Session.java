package com.example.a77011_40_05.afparestaurant.utils;

import com.example.a77011_40_05.afparestaurant.models.User;

import java.util.HashMap;

public class Session {

    private static User myUser;

    private static Boolean connectionChecked = false;

    public static Boolean getConnectionChecked() {
        return connectionChecked;
    }

    public static void setConnectionChecked(Boolean connectionChecked) {
        Session.connectionChecked = connectionChecked;
    }

    public static User getMyUser() {
        return myUser;
    }

    public static void setMyUser(User myUser) {
        Session.myUser = myUser;
    }
}

