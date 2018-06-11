package com.example.a77011_40_05.afparestaurant.fragments;

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
import android.widget.ViewSwitcher;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.adapters.MealAdapter;
import com.example.a77011_40_05.afparestaurant.adapters.StepAdapter;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.models.Push;
import com.example.a77011_40_05.afparestaurant.models.Tables;
import com.example.a77011_40_05.afparestaurant.utils.App;
import com.example.a77011_40_05.afparestaurant.utils.Constants;
import com.example.a77011_40_05.afparestaurant.utils.Functions;
import com.google.gson.Gson;

import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderFragment extends Fragment {

    Context context;
    int idTable;
    int guests;
    StepAdapter stepAdapter;
    boolean isFirstPage = true;

    //ELEMENTS
    Button btnOrders;
    Button btnBill;
    ViewSwitcher vwsOrder;
    RecyclerView rvwStepsList;


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
                    vwsOrder.showNext();
                    isFirstPage = false;
                }
            }
        });


        return view;
    }
}
