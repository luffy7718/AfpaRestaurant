package com.example.a77011_40_05.afparestaurant.utils;

import android.app.Application;

import com.example.a77011_40_05.afparestaurant.models.MealCategories;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.models.Step;
import com.example.a77011_40_05.afparestaurant.models.Steps;

public class App extends Application {

    public static Meals meals;
    public static MealCategories mealCategories;
    public static Steps steps;

    public static Meals getMeals() {
        return meals;
    }

    public static void setMeals(Meals meals) {
        App.meals = meals;
    }

    public static MealCategories getMealCategories() {
        return mealCategories;
    }

    public static void setMealCategories(MealCategories mealCategories) {
        App.mealCategories = mealCategories;
    }

    public static Steps getSteps() {
        return steps;
    }

    public static void setSteps(Steps steps) {
        App.steps = steps;
    }


}
