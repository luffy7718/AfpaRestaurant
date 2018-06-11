package com.example.a77011_40_05.afparestaurant.models;

import java.util.ArrayList;
import java.util.Comparator;

public class Meals extends ArrayList<Meal>{

    public static class SortByIdStep implements Comparator<Meal> {
        @Override
        public int compare(Meal m1, Meal m2) {
            return m1.getIdStep()-m2.getIdStep();
        }
    }
}
