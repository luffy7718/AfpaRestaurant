package com.example.a77011_40_05.afparestaurant.fragments;


import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.utils.Functions;

public class SettingsFragment extends Fragment {

    Context context;
    Spinner spiCommandMode;
    String[] modes = new String[]{"Menu et Commandes","Liste papier","Claude"};

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance(Bundle args) {
        SettingsFragment fragment = new SettingsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        spiCommandMode = view.findViewById(R.id.spiCommandMode);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, modes);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiCommandMode.setAdapter(arrayAdapter);

        int selected = stringPosition(Functions.getPreferenceString(context,"commandMode"));
        if(selected != -1){
            spiCommandMode.setSelection(selected);
        }

        spiCommandMode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object item = parent.getItemAtPosition(position);
                if(item != null){
                    Functions.addPreferenceString(context,"commandMode",item.toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return view;
    }

    private int stringPosition(String s){
        int position = -1;
        for(int i = 0; i<modes.length; i++){
            if(modes[i].equals(s)){
                position = i;
            }
        }
        return position;
    }

}
