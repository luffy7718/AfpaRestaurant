package com.example.a77011_40_05.afparestaurant.holders;

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.activities.HomeActivity;
import com.example.a77011_40_05.afparestaurant.models.CategoryMeal;

public class CategoryMealHolder extends RecyclerView.ViewHolder {

    private CardView cvStepsBody;
    private TextView txtStep;
    private CategoryMeal categoryMeal;

    public CategoryMealHolder(View view) {
        super(view);
        cvStepsBody = view.findViewById(R.id.cvStepsBody);
        txtStep = view.findViewById(R.id.txtStep);
    }

    public void setStep(CategoryMeal categoryMeal, Activity activity){
        this.categoryMeal = categoryMeal;

        txtStep.setText(categoryMeal.getName());

        cvStepsBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMeal(activity);
            }
        });
    }

    public void selectMeal(Activity activity){
        HomeActivity home = (HomeActivity) activity;
        home.showMealsDialog(categoryMeal.getIdCategoryMeal());
    }
}