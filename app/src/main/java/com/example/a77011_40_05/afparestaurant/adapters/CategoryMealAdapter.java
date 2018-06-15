package com.example.a77011_40_05.afparestaurant.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.holders.CategoryMealHolder;
import com.example.a77011_40_05.afparestaurant.models.CategoryMeal;
import com.example.a77011_40_05.afparestaurant.models.CategoriesMeals;

public class CategoryMealAdapter extends RecyclerView.Adapter<CategoryMealHolder> {
    CategoriesMeals categoriesMeals;
    Activity activity;
    boolean gridMode = true;

    public CategoryMealAdapter(Activity activity) {
        this.categoriesMeals = new CategoriesMeals();
        this.activity = activity;
    }

    @NonNull
    @Override
    public CategoryMealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(gridMode){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_category_meal,parent,false);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_category_meal_horizontal,parent,false);
        }
        return new CategoryMealHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryMealHolder holder, int position) {
        CategoryMeal categoryMeal = categoriesMeals.get(position);
        holder.setStep(categoryMeal,activity);
    }

    public void setHorizontalMode(){
        gridMode = false;
    }

    @Override
    public int getItemCount() {
        if(categoriesMeals == null){
            return 0;
        }else{
            return categoriesMeals.size();
        }
    }

    public  void loadSteps(CategoriesMeals categoriesMeals) {
        this.categoriesMeals = categoriesMeals;
        notifyDataSetChanged();
    }
}
