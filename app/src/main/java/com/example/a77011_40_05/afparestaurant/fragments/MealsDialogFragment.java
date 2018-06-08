package com.example.a77011_40_05.afparestaurant.fragments;

import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.adapters.MealAdapter;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.utils.App;
import com.example.a77011_40_05.afparestaurant.utils.Constants;

public class MealsDialogFragment extends DialogFragment {

    Context context;
    MealAdapter mealAdapter;
    RecyclerView rvwMealsList;
    int idStep;

    public MealsDialogFragment() {
        // Required empty public constructor
    }

    public static MealsDialogFragment newInstance(int idStep) {
        MealsDialogFragment fragment = new MealsDialogFragment();
        Bundle args = new Bundle();
        args.putInt("idStep",idStep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            if(getArguments().containsKey("idStep")){
                idStep = getArguments().getInt("idStep");
            }else{
                Log.e(Constants._TAG_LOG,"ERROR: Pas de idStep");
            }

            if(idStep != 0 ){
                Log.e(Constants._TAG_LOG,"Steps: "+idStep);
            }
        }

        context = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meals_dialog, container, false);
        rvwMealsList = view.findViewById(R.id.rvwMealsList);
        mealAdapter = new MealAdapter(getActivity());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,1);
        rvwMealsList.setLayoutManager(layoutManager);
        rvwMealsList.setAdapter(mealAdapter);
        getMeals();
        return view;
    }

    private void getMeals(){
        Meals meals = App.getMeals();
        Meals newList = new Meals();
        for(Meal meal: meals){
            if(meal.getIdStep() == idStep){
                newList.add(meal);
            }
        }
        mealAdapter.loadMeals(newList);
    }
}
