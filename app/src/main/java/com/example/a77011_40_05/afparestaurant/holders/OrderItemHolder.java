package com.example.a77011_40_05.afparestaurant.holders;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.activities.HomeActivity;
import com.example.a77011_40_05.afparestaurant.adapters.OrderItemAdapter;
import com.example.a77011_40_05.afparestaurant.fragments.OrderFragment;
import com.example.a77011_40_05.afparestaurant.interfaces.SWInterface;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.OrderItem;
import com.example.a77011_40_05.afparestaurant.models.Push;
import com.example.a77011_40_05.afparestaurant.utils.Constants;
import com.example.a77011_40_05.afparestaurant.utils.Functions;
import com.example.a77011_40_05.afparestaurant.utils.RetrofitApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderItemHolder extends RecyclerView.ViewHolder {


    private TextView txtName;
    private ImageButton btnRemove;
    private OrderItem orderItem;
    private SWInterface swInterface;

    public OrderItemHolder(View view) {
        super(view);
        txtName = view.findViewById(R.id.txtName);
        btnRemove = view.findViewById(R.id.btnRemove);
        swInterface = RetrofitApi.getInterface();
    }

    public void setOrderItem(OrderItem orderItem, Activity activity, OrderItemAdapter parent) {
        this.orderItem = orderItem;

        txtName.setText(orderItem.getName());

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeOrderItem(activity);
                parent.remove(getAdapterPosition());
            }
        });

    }

    private void removeOrderItem(Activity activity){
        Call<Push> call = swInterface.removeOrderItem(Functions.getAuth(),this.orderItem.getIdOrderItem());
        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {
                if (response.isSuccessful()) {
                    Log.e(Constants.TAG_LOG, "addOrder : " + response.body
                            ().toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        Log.e(Constants.TAG_LOG,orderItem.getName()+" suppression: "+push.getData());
                        HomeActivity home = (HomeActivity) activity;
                        OrderFragment frag = (OrderFragment) home.getLastFragment();
                        frag.showOrdres();
                    } else {
                        Log.e("push.getdata = ", push.getData());
                    }

                } else {
                    //todo:gérer les code erreur de retour
                    Log.e(Constants.TAG_LOG, "ERROR code :" + response.code());
                }

            }

            @Override
            public void onFailure(Call<Push> call, Throwable t) {
                Log.e("error", "");

            }
        });
    }
}