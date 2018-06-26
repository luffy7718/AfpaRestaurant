package com.example.a77011_40_05.afparestaurant.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.adapters.TableAdapter;
import com.example.a77011_40_05.afparestaurant.interfaces.SWInterface;
import com.example.a77011_40_05.afparestaurant.models.Push;
import com.example.a77011_40_05.afparestaurant.models.Tables;
import com.example.a77011_40_05.afparestaurant.utils.App;
import com.example.a77011_40_05.afparestaurant.utils.Constants;
import com.example.a77011_40_05.afparestaurant.utils.Functions;
import com.example.a77011_40_05.afparestaurant.utils.RetrofitApi;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TablesSelectorFragment extends Fragment {

    Context context;
    SWInterface swInterface;

    RecyclerView rvwTablesList;
    TableAdapter tableAdapter;

    public TablesSelectorFragment() {
        // Required empty public constructor
    }

    public static TablesSelectorFragment newInstance() {
        TablesSelectorFragment fragment = new TablesSelectorFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        swInterface = RetrofitApi.getInterface();
        context = getActivity();
        getActivity().setTitle("Listes des Tables");
        tableAdapter = new TableAdapter(getActivity());

        getTables();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tables_selector, container, false);

        rvwTablesList = view.findViewById(R.id.rvwTablesList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context,6);
        rvwTablesList.setLayoutManager(layoutManager);
        rvwTablesList.setAdapter(tableAdapter);

        return view;
    }

    private void getTables(){
        Call<Push> call = swInterface.getTables(Functions.getAuth());

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {

                if (response.isSuccessful()) {
                    Log.e(Constants.TAG_LOG, response.body().toString());
                    Push push = response.body();
                    if(push.getStatus()==1) {
                        Gson gson = new Gson();
                        Tables tables = gson.fromJson(push.getData(),Tables.class);
                        App.setTables(tables);
                        tableAdapter.loadTables(tables);
                        Log.e(Constants.TAG_LOG,"DATA RECIEVE");
                    }
                } else {
                    Log.e(Constants.TAG_LOG,"Erreur : getStaff " + response.toString());
                }
            }

            @Override
            public void onFailure(Call<Push> call, Throwable t) {

            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e(Constants.TAG_LOG,"onResume()");
        getTables();
    }
}
