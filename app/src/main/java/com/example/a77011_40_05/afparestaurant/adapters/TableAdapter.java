package com.example.a77011_40_05.afparestaurant.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.holders.TableHolder;
import com.example.a77011_40_05.afparestaurant.models.Table;
import com.example.a77011_40_05.afparestaurant.models.Tables;

public class TableAdapter extends RecyclerView.Adapter<TableHolder> {
    Tables tables;
    Activity activity;

    public TableAdapter(Activity activity) {
        this.tables = new Tables();
        this.activity = activity;
    }

    @NonNull
    @Override
    public TableHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_table,parent,false);
        return new TableHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableHolder holder, int position) {
        Table table = tables.get(position);
        holder.setTable(table,activity);
    }

    @Override
    public int getItemCount() {
        if(tables == null){
            return 0;
        }else{
            return tables.size();
        }
    }

    public  void loadTables(Tables tables) {
        this.tables = tables;
        notifyDataSetChanged();
    }
}
