package com.example.a77011_40_05.afparestaurant.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.holders.MealHolder;
import com.example.a77011_40_05.afparestaurant.holders.OrderHolder;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;

public class OrderAdapter extends RecyclerView.Adapter<OrderHolder> {
    Meals meals;
    Activity activity;

    public OrderAdapter(Activity activity) {
        this.meals = new Meals();
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_order,parent,false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.setMeal(meal,activity,this);
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

    public void remove(int position){
        meals.remove(position);
        notifyDataSetChanged();
    }
}
