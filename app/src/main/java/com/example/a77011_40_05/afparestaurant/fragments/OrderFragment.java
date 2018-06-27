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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.activities.HomeActivity;
import com.example.a77011_40_05.afparestaurant.adapters.OrderItemAdapter;
import com.example.a77011_40_05.afparestaurant.interfaces.SWInterface;
import com.example.a77011_40_05.afparestaurant.models.CategoriesMeals;
import com.example.a77011_40_05.afparestaurant.models.CategoryMeal;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.models.OrderItem;
import com.example.a77011_40_05.afparestaurant.models.OrdersItems;
import com.example.a77011_40_05.afparestaurant.models.Push;
import com.example.a77011_40_05.afparestaurant.utils.App;
import com.example.a77011_40_05.afparestaurant.utils.Constants;
import com.example.a77011_40_05.afparestaurant.utils.Functions;
import com.example.a77011_40_05.afparestaurant.utils.RetrofitApi;
import com.example.a77011_40_05.afparestaurant.utils.Session;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    public int guests;
    int idTable;
    int idOrder = 0;
    HashMap<Integer, OrdersItems> orders;
    SWInterface swInterface;
    Button btncompleted;
    //ELEMENTS
    Context context;
    LinearLayout lltOrder;
    TextView txtNbGuest;
    TextView txtNbMeal;


    public OrderFragment() {
        // Required empty public constructor
    }

    public static OrderFragment newInstance(Bundle args) {
        OrderFragment fragment = new OrderFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActivity().setTitle("Commande");
        if (getArguments() != null) {
            if (getArguments().containsKey("guests") && getArguments().containsKey("idTable")) {
                guests = getArguments().getInt("guests");
                idTable = getArguments().getInt("idTable");
            } else if (getArguments().containsKey("idOrder")) {
                idOrder = getArguments().getInt("idOrder");
            } else {
                Log.e(Constants.TAG_LOG, "OrderFragment: pas d'arguments.");
            }
        }

        context = getActivity();
        swInterface = RetrofitApi.getInterface();
        buildOrders();
        if (idOrder == 0) {
            addOrder(0);
            getActivity().setTitle("Table n° " + App.getTableNumber(idTable));
        } else {
            getOrder();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        lltOrder = view.findViewById(R.id.lltOrder);
        btncompleted = view.findViewById(R.id.btncompleted);
        btncompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderCompleted(1);
                HomeActivity home = (HomeActivity) getActivity();
                home.changeFragment(Constants.FRAG_HOME, null);
            }
        });

        showOrdres();
        return view;
    }

    private void buildOrders() {
        orders = new HashMap<>();
        CategoriesMeals categoriesMeals = App.getCategoriesMeals();
        for (CategoryMeal categoryMeal : categoriesMeals) {
            Log.e(Constants.TAG_LOG, "CategoryMeal:" + categoryMeal.getName());
            int idCategoryMeal = categoryMeal.getIdCategoryMeal();
            OrdersItems ordersItems = new OrdersItems();
            orders.put(idCategoryMeal, ordersItems);
        }
    }

    private void buildOrders(String json) {
        Gson gson = new Gson();
        OrdersItems orderItemFromDB = gson.fromJson(json, OrdersItems.class);
        orders = new HashMap<>();
        CategoriesMeals categoriesMeals = App.getCategoriesMeals();
        for (CategoryMeal categoryMeal : categoriesMeals) {
            Log.e(Constants.TAG_LOG, "CategoryMeal:" + categoryMeal.getName());
            int idCategoryMeal = categoryMeal.getIdCategoryMeal();
            OrdersItems ordersItems = new OrdersItems();
            for (OrderItem orderItem : orderItemFromDB) {
                if (idCategoryMeal == orderItem.getIdCategoryMeal()) {
                    ordersItems.add(orderItem);
                }
            }
            orders.put(idCategoryMeal, ordersItems);
        }
    }

    @SuppressLint("NewApi")
    public void showOrdres() {
        lltOrder.removeAllViews();
        CategoriesMeals categoriesMeals = App.getCategoriesMeals();
        for (CategoryMeal categoryMeal : categoriesMeals) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_step_orders, null,
                    false);

            LinearLayout lltHeaderOrder = view.findViewById(R.id.lltHeaderOrder);
            TextView txtOrderName = view.findViewById(R.id.txtOrderName);
            RecyclerView rvwOrderList = view.findViewById(R.id.rvwOrderList);
            txtNbGuest = view.findViewById(R.id.txtNbGuest);
            txtNbMeal = view.findViewById(R.id.txtNbMeal);
            txtOrderName.setText(categoryMeal.getName());
            lltHeaderOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeActivity home = (HomeActivity) getActivity();
                    home.showMealsDialog(categoryMeal.getIdCategoryMeal());
                }
            });
            txtNbMeal.setText(orders.get(categoryMeal.getIdCategoryMeal()).size() + "/");
            txtNbGuest.setText("" + guests);
            OrderItemAdapter orderItemAdapter = new OrderItemAdapter(getActivity());
            orderItemAdapter.loadMeals(orders.get(categoryMeal.getIdCategoryMeal()));
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rvwOrderList.setLayoutManager(llm);
            rvwOrderList.setAdapter(orderItemAdapter);

            lltOrder.addView(view);
        }
    }

    public int getGuests() {
        return guests;
    }

    public void addOrders(int idStep, Meals list) {
        //orders.get(idStep).clear();
        for (Meal meal : list) {
            int end = meal.getQuantity();
            meal.setQuantity(1);
            for (int i = 0; i < end; i++) {
                //orders.get(idStep).add(meal);
                addOrderItem(meal);
            }
            meal.setQuantity(0);
            //showOrdres();
        }
    }

    private void addOrder(int done) {
        Call<Push> call = swInterface.addOrder(Functions.getAuth(), idTable, Session
                .getMyUser().getIdStaff(), guests, Functions.today(), done);

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {
                if (response.isSuccessful()) {
                    Log.e(Constants.TAG_LOG, "addOrder : " + response.body
                            ().toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        idOrder = Integer.parseInt(push.getData());

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

    private void getOrder() {
        Call<Push> call = swInterface.getOrder(Functions.getAuth(), idOrder);
        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {
                if (response.isSuccessful()) {
                    Log.e(Constants.TAG_LOG, "getOrder : " + response.body().toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        Gson gson = new Gson();
                        JsonObject json = gson.fromJson(push.getData(), JsonObject.class);
                        if (json.has("idTable")) {
                            idTable = Integer.parseInt(json.get("idTable").getAsString());
                            getActivity().setTitle("Table n° " + App.getTableNumber(idTable));
                        } else {
                            Log.e(Constants.TAG_LOG, "idTable non-trouvé");
                        }
                        if (json.has("guests")) {
                            guests = Integer.parseInt(json.get("guests").getAsString());
                        } else {
                            Log.e(Constants.TAG_LOG, "guests non-trouvé");
                        }
                        if (json.has("meals")) {
                            try {
                                Log.e(Constants.TAG_LOG, "Meals reçu");
                                Log.e(Constants.TAG_LOG, json.get("meals").toString());
                                buildOrders(json.get("meals").toString());
                                showOrdres();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            Log.e(Constants.TAG_LOG, "meals non-trouvé");
                        }

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

    private void orderCompleted(int done) {
        Call<Push> call = swInterface.orderCompleted(Functions.getAuth(), idOrder, done);
        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {
                if (response.isSuccessful()) {
                    Log.e(Constants.TAG_LOG, "orderCompleted : " + response.body().toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {


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

    private void addOrderItem(Meal meal) {

        Log.e(Constants.TAG_LOG, "" + App.getMeal().getIdMeal());

        Call<Push> call = swInterface.addOrderItem(Functions.getAuth(), idOrder
                , meal.getIdMeal());

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {
                if (response.isSuccessful()) {
                    Log.e(Constants.TAG_LOG, "addOrderItem : " + response.body
                            ().toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        int idOrderItem = Integer.parseInt(push.getData());
                        OrderItem orderItem = new OrderItem(meal);
                        orderItem.setIdOrderItem(idOrderItem);
                        orders.get(orderItem.getIdCategoryMeal()).add(orderItem);
                        showOrdres();
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
