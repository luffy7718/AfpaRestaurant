package com.example.a77011_40_05.afparestaurant.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.holders.MealHolder;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;

public class MealAdapter extends RecyclerView.Adapter<MealHolder> {
    Meals meals;
    Activity activity;

    public MealAdapter(Activity activity) {
        this.meals = new Meals();
        this.activity = activity;
    }

    @NonNull
    @Override
    public MealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 500));
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_meal,parent,false);
        return new MealHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.setMeal(meal,activity);
    }

    @Override
    public int getItemCount() {
        if(meals == null){
            return 0;
        }else{
            return meals.size();
        }
    }

    public Meals getMeals() {
        return meals;
    }

    public  void loadMeals(Meals meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }
}
