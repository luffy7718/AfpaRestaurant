package com.example.a77011_40_05.afparestaurant.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.holders.StepHolder;
import com.example.a77011_40_05.afparestaurant.models.Step;
import com.example.a77011_40_05.afparestaurant.models.Steps;

public class StepAdapter extends RecyclerView.Adapter<StepHolder> {
    Steps steps;
    Activity activity;

    public StepAdapter(Activity activity) {
        this.steps = new Steps();
        this.activity = activity;
    }

    @NonNull
    @Override
    public StepHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_step,parent,false);
        return new StepHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StepHolder holder, int position) {
        Step step = steps.get(position);
        holder.setStep(step,activity);
    }

    @Override
    public int getItemCount() {
        if(steps == null){
            return 0;
        }else{
            return steps.size();
        }
    }

    public  void loadSteps(Steps steps) {
        this.steps = steps;
        notifyDataSetChanged();
    }
}
