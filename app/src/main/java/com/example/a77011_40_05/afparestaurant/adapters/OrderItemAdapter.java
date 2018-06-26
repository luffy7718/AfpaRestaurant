package com.example.a77011_40_05.afparestaurant.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.holders.OrderItemHolder;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.models.OrderItem;
import com.example.a77011_40_05.afparestaurant.models.OrdersItems;

public class OrderItemAdapter extends RecyclerView.Adapter<OrderItemHolder> {
    OrdersItems ordersItems;
    Activity activity;

    public OrderItemAdapter(Activity activity) {
        this.ordersItems = new OrdersItems();
        this.activity = activity;
    }

    @NonNull
    @Override
    public OrderItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_order,parent,false);
        return new OrderItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemHolder holder, int position) {
        OrderItem orderItem = ordersItems.get(position);
        holder.setOrderItem(orderItem, activity,this);
    }

    @Override
    public int getItemCount() {
        if(ordersItems == null){
            return 0;
        }else{
            return ordersItems.size();
        }
    }

    public OrdersItems getOrdersItems() {
        return ordersItems;
    }

    public  void loadMeals(OrdersItems ordersItems) {
        this.ordersItems = ordersItems;
        notifyDataSetChanged();
    }

    public void remove(int position){
        ordersItems.remove(position);
        notifyDataSetChanged();
    }
}
