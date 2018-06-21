package com.example.a77011_40_05.afparestaurant.utils;

import android.app.Application;

import com.example.a77011_40_05.afparestaurant.models.CategoriesMeals;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.models.Tables;

public class App extends Application {

    public static Meals meals;
    public static CategoriesMeals categoriesMeals;
    public static Tables tables;

    public static Tables getTables() {
        return tables;
    }

    public static void setTables(Tables tables) {
        App.tables = tables;
    }

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
