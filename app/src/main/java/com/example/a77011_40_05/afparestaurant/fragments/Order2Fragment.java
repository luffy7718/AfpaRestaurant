package com.example.a77011_40_05.afparestaurant.fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.activities.HomeActivity;
import com.example.a77011_40_05.afparestaurant.adapters.OrderAdapter;
import com.example.a77011_40_05.afparestaurant.models.CategoriesMeals;
import com.example.a77011_40_05.afparestaurant.models.CategoryMeal;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.utils.App;
import com.example.a77011_40_05.afparestaurant.utils.Constants;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Order2Fragment extends Fragment {

    int guests;
    int idTable;
    int idOrder;
    HashMap<Integer,Meals> orders;

    Context context;
    LinearLayout lltOrder;

    public Order2Fragment() {
        // Required empty public constructor
    }

    public static Order2Fragment newInstance(Bundle args) {
        Order2Fragment fragment = new Order2Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Commandes");
        if (getArguments() != null) {
            if(getArguments().containsKey("guests") && getArguments().containsKey("idTable")){
                guests = getArguments().getInt("guests");
                idTable = getArguments().getInt("idTable");
                buildOrders();
            }else if(getArguments().containsKey("idOrder")){
                idOrder = getArguments().getInt("idOrder");
                buildOrders();
            }else{
                Log.e(Constants.TAG_LOG,"OrderFragment: pas d'arguments.");
            }
        }

        context = getActivity();
        buildOrders();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order2, container, false);
        lltOrder = view.findViewById(R.id.lltOrder);

        showOrdres();
        return view;
    }

    private void buildOrders(){
        orders = new HashMap<>();
        CategoriesMeals categoriesMeals = App.getCategoriesMeals();
        for(CategoryMeal categoryMeal : categoriesMeals){
            Log.e(Constants.TAG_LOG,"CategoryMeal:"+ categoryMeal.getName());
            int idStep = categoryMeal.getIdCategoryMeal();
            Meals meals = new Meals();
            orders.put(idStep,meals);
        }
    }

    @SuppressLint("NewApi")
    private void showOrdres(){
        lltOrder.removeAllViews();
        CategoriesMeals categoriesMeals = App.getCategoriesMeals();
        for(CategoryMeal categoryMeal : categoriesMeals){
            View view = LayoutInflater.from(context).inflate(R.layout.item_step_orders,null,false);
            LinearLayout lltHeaderOrder = view.findViewById(R.id.lltHeaderOrder);
            TextView txtOrderName = view.findViewById(R.id.txtOrderName);
            RecyclerView rvwOrderList = view.findViewById(R.id.rvwOrderList);

            txtOrderName.setText(categoryMeal.getName());
            lltHeaderOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeActivity home = (HomeActivity) getActivity();
                    home.showMealsDialog(categoryMeal.getIdCategoryMeal());
                }
            });

            OrderAdapter orderAdapter = new OrderAdapter(getActivity());
            orderAdapter.loadMeals(orders.get(categoryMeal.getIdCategoryMeal()));
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rvwOrderList.setLayoutManager(llm);
            rvwOrderList.setAdapter(orderAdapter);

            lltOrder.addView(view);
        }
    }

    public void addOrders(int idStep, Meals list){
        orders.get(idStep).clear();
        for(Meal meal: list){
            int end = meal.getQuantity();
            meal.setQuantity(1);
            for(int i = 0; i<end;i++){
                orders.get(idStep).add(meal);
            }
            meal.setQuantity(0);
            showOrdres();
        }
    }

}
