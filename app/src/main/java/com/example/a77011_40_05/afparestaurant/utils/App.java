package com.example.a77011_40_05.afparestaurant.utils;

import android.app.Application;

import com.example.a77011_40_05.afparestaurant.models.CategoriesMeals;
import com.example.a77011_40_05.afparestaurant.models.Meals;

public class App extends Application {

    public static Meals meals;
    public static CategoriesMeals categoriesMeals;

    public static Meals getMeals() {
        return meals;
    }

    public static void setMeals(Meals meals) {
        App.meals = meals;
    }

    public static CategoriesMeals getCategoriesMeals() {
        return categoriesMeals;
    }

    public static void setCategoriesMeals(CategoriesMeals categoriesMeals) {
        App.categoriesMeals = categoriesMeals;
    }


}
