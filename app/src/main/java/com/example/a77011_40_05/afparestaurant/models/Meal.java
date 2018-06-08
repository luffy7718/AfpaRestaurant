package com.example.a77011_40_05.afparestaurant.models;

public class Meal {

    int idMeal;
    String name;
    String description;
    int idMealCategory;
    int idStep;

    public Meal() {
    }

    public int getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(int idMeal) {
        this.idMeal = idMeal;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdMealCategory() {
        return idMealCategory;
    }

    public void setIdMealCategory(int idMealCategory) {
        this.idMealCategory = idMealCategory;
    }

    public int getIdStep() {
        return idStep;
    }

    public void setIdStep(int idStep) {
        this.idStep = idStep;
    }
}
