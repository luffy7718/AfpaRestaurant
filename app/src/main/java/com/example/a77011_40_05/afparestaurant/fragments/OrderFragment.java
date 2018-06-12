package com.example.a77011_40_05.afparestaurant.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.adapters.MealAdapter;
import com.example.a77011_40_05.afparestaurant.adapters.OrderAdapter;
import com.example.a77011_40_05.afparestaurant.adapters.StepAdapter;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.models.Push;
import com.example.a77011_40_05.afparestaurant.models.Step;
import com.example.a77011_40_05.afparestaurant.models.Steps;
import com.example.a77011_40_05.afparestaurant.models.Tables;
import com.example.a77011_40_05.afparestaurant.utils.App;
import com.example.a77011_40_05.afparestaurant.utils.Constants;
import com.example.a77011_40_05.afparestaurant.utils.Functions;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

    Context context;
    int idTable;
    int guests;
    StepAdapter stepAdapter;
    boolean isFirstPage = true;

    HashMap<Integer,Meals> orders;

    //ELEMENTS
    Button btnOrders;
    Button btnBill;
    ViewSwitcher vwsOrder;
    RecyclerView rvwStepsList;
    LinearLayout lltOrder;


    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance(Bundle args) {
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Commandes");
        if (getArguments() != null) {
            if(getArguments().containsKey("guests")){
                guests = getArguments().getInt("guests");
            }else{
                Log.e(Constants._TAG_LOG,"ERROR: Pas de convives");
            }

            if(getArguments().containsKey("idTable")){
                idTable = getArguments().getInt("idTable");
            }else{
                Log.e(Constants._TAG_LOG,"ERROR: Pas d'idTable");
            }

            if(guests != 0 && idTable != 0){
                Log.e(Constants._TAG_LOG,"Table: "+idTable+" pour "+guests+" personnes");
            }
        }

        context = getActivity();
        buildOrders();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        btnOrders = view.findViewById(R.id.btnOrders);
        btnBill = view.findViewById(R.id.btnBill);
        vwsOrder = view.findViewById(R.id.vwsOrder);
        rvwStepsList = view.findViewById(R.id.rvwStepsList);
        lltOrder = view.findViewById(R.id.lltOrder);

        stepAdapter = new StepAdapter(getActivity());
        stepAdapter.loadSteps(App.getSteps());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,2);
        rvwStepsList.setLayoutManager(layoutManager);
        rvwStepsList.setAdapter(stepAdapter);

        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!isFirstPage){
                   vwsOrder.showPrevious();
                   isFirstPage = true;
               }
            }
        });

        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFirstPage){
                    showOrdres();
                    vwsOrder.showNext();
                    isFirstPage = false;
                }
            }
        });

        return view;
    }

    public void buildOrders(){
        //Log.e(Constants._TAG_LOG,"buildOrder()");
        orders = new HashMap<>();
        Steps steps = App.getSteps();
        for(Step step: steps){
            Log.e(Constants._TAG_LOG,"Step:"+step.getName());
            int idStep = step.getIdStep();
            Meals meals = new Meals();
            orders.put(idStep,meals);
        }
        //Log.e(Constants._TAG_LOG,orders.toString());
    }

    @SuppressLint("NewApi")
    private void showOrdres(){
        //Log.e(Constants._TAG_LOG,"showOrdres()");
        lltOrder.removeAllViews();
        Steps steps = App.getSteps();
        for(Step step: steps){
            //Log.e(Constants._TAG_LOG,step.getIdStep()+": "+step.getName());
            View view = LayoutInflater.from(context).inflate(R.layout.item_step_orders,null,false);
            TextView txtOrderName = view.findViewById(R.id.txtOrderName);
            RecyclerView rvwOrderList = view.findViewById(R.id.rvwOrderList);

            txtOrderName.setText(step.getName());

            OrderAdapter orderAdapter = new OrderAdapter(getActivity());
            orderAdapter.loadMeals(orders.get(step.getIdStep()));
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
            meal.setQuantity(end);
        }
    }
}
