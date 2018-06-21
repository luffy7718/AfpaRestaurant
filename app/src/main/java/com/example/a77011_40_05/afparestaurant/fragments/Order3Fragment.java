package com.example.a77011_40_05.afparestaurant.fragments;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.adapters.CategoryMealAdapter;
import com.example.a77011_40_05.afparestaurant.adapters.OrderAdapter;
import com.example.a77011_40_05.afparestaurant.models.CategoriesMeals;
import com.example.a77011_40_05.afparestaurant.models.CategoryMeal;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.utils.App;
import com.example.a77011_40_05.afparestaurant.utils.Constants;

import java.util.HashMap;


public class Order3Fragment extends Fragment {

    Context context;
    int idTable;
    public int guests;
    CategoryMealAdapter categoryMealAdapter;
    OrderAdapter orderAdapter;
    Meals orders;

    //ELEMENTS
    RecyclerView rvwStepsList;
    RecyclerView rvwOrderList;
    LinearLayout lltOrder;

    public Order3Fragment() {
        // Required empty public constructor
    }

    public static Order3Fragment newInstance(Bundle args) {
        Order3Fragment fragment = new Order3Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if(getArguments().containsKey("guests")){
                guests = getArguments().getInt("guests");
            }else{
                Log.e(Constants.TAG_LOG,"ERROR: Pas de convives");
            }

            if(getArguments().containsKey("idTable")){
                idTable = getArguments().getInt("idTable");
            }else{
                Log.e(Constants.TAG_LOG,"ERROR: Pas d'idTable");
            }

            if(guests != 0 && idTable != 0){
                Log.e(Constants.TAG_LOG,"Table: "+idTable+" pour "+guests+" personnes");
            }
        }

        context = getActivity();
        orders = new Meals();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order3, container, false);
        rvwStepsList = view.findViewById(R.id.rvwStepsList);
        rvwOrderList = view.findViewById(R.id.rvwOrderList);
        lltOrder = view.findViewById(R.id.lltOrder);

        categoryMealAdapter = new CategoryMealAdapter(getActivity());
        categoryMealAdapter.loadSteps(App.getCategoriesMeals());
        categoryMealAdapter.setHorizontalMode();
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvwStepsList.setLayoutManager(llm);
        rvwStepsList.setAdapter(categoryMealAdapter);


        orderAdapter = new OrderAdapter(getActivity());
        orderAdapter.loadMeals(orders);
        LinearLayoutManager llmv = new LinearLayoutManager(getActivity());
        llmv.setOrientation(LinearLayoutManager.VERTICAL);
        rvwOrderList.setLayoutManager(llmv);
        rvwOrderList.setAdapter(orderAdapter);

        return view;
    }

    public int getGuests() {
        return guests;
    }

    public void addOrders(int idStep, Meals list){
        Meals listApdapter = orderAdapter.getMeals();
        for(Meal meal: list){
            int end = meal.getQuantity();
            meal.setQuantity(1);
            for(int i = 0; i<end;i++){
                listApdapter.add(meal);
            }
            meal.setQuantity(0);
        }
        orderAdapter.notifyDataSetChanged();
    }
}
