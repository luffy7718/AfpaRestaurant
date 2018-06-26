package com.example.a77011_40_05.afparestaurant.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.activities.HomeActivity;
import com.example.a77011_40_05.afparestaurant.adapters.OrderItemAdapter;
import com.example.a77011_40_05.afparestaurant.adapters.CategoryMealAdapter;
import com.example.a77011_40_05.afparestaurant.interfaces.SWInterface;
import com.example.a77011_40_05.afparestaurant.models.CategoriesMeals;
import com.example.a77011_40_05.afparestaurant.models.CategoryMeal;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.models.Push;
import com.example.a77011_40_05.afparestaurant.utils.App;
import com.example.a77011_40_05.afparestaurant.utils.Constants;
import com.example.a77011_40_05.afparestaurant.utils.Functions;
import com.example.a77011_40_05.afparestaurant.utils.RetrofitApi;
import com.example.a77011_40_05.afparestaurant.utils.Session;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Order4Fragment extends Fragment {

    Context context;
    int idTable;
    public int guests;
    CategoryMealAdapter categoryMealAdapter;
    boolean isFirstPage = true;
    OrderItemAdapter orderItemAdapter;
    HashMap<Integer, Meals> orders;
    CategoriesMeals categoriesMeals;
    //ELEMENTS
    Button btnOrders;
    Button btnBill;
    Button btncompleted;
    ViewSwitcher vwsOrder;
    RecyclerView rvwStepsList;
    LinearLayout lltOrder;
    RecyclerView rvwOrderList;
    TextView txtOrderName;
    ImageView imgAddMeal;
    TextView txtNbGuest;
    TextView txtNbMeal;
    SWInterface swInterface;
    int done = -1;
    public int idOrder;
    Meals meals;

    public Order4Fragment() {
        // Required empty public constructor
    }


    public static Order4Fragment newInstance(Bundle args) {
        Order4Fragment fragment = new Order4Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Commande");
        if (getArguments() != null) {
            if (getArguments().containsKey("guests")) {
                guests = getArguments().getInt("guests");
            } else {
                Log.e(Constants.TAG_LOG, "ERROR: Pas de convives");
            }

            if (getArguments().containsKey("idTable")) {
                idTable = getArguments().getInt("idTable");
            } else {
                Log.e(Constants.TAG_LOG, "ERROR: Pas d'idTable");
            }

            if (guests != 0 && idTable != 0) {
                Log.e(Constants.TAG_LOG, "Table: " + idTable + " pour " + guests + " personnes");
            }
        }

        context = getActivity();
        swInterface = RetrofitApi.getInterface();
        buildOrders();
        done = 0;
        addOrder(done);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order4, container, false);
        btnOrders = view.findViewById(R.id.btnOrders);
        btnBill = view.findViewById(R.id.btnBill);
        vwsOrder = view.findViewById(R.id.vwsOrder);
        rvwStepsList = view.findViewById(R.id.rvwStepsList);
        lltOrder = view.findViewById(R.id.lltOrder);
        categoryMealAdapter = new CategoryMealAdapter(getActivity());
        categoryMealAdapter.loadSteps(App.getCategoriesMeals());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        rvwStepsList.setLayoutManager(layoutManager);
        rvwStepsList.setAdapter(categoryMealAdapter);
        btncompleted = view.findViewById(R.id.btncompleted);
        btncompleted.setOnClickListener(v -> {

            orderCompleted(1);
            HomeActivity home= (HomeActivity) getActivity();
            home.changeFragment(Constants.FRAG_HOME,null);

        });
        btnOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFirstPage) {
                    vwsOrder.showPrevious();
                    isFirstPage = true;
                }
            }
        });

        btnBill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirstPage) {
                    showOrdres();
                    vwsOrder.showNext();
                    isFirstPage = false;
                }
            }
        });

        return view;
    }

    public void buildOrders() {
        orders = new HashMap<>();
        CategoriesMeals categoriesMeals = App.getCategoriesMeals();
        for (CategoryMeal categoryMeal : categoriesMeals) {
            Log.e(Constants.TAG_LOG, "CategoryMeal:" + categoryMeal.getName());
            int idStep = categoryMeal.getIdCategoryMeal();
            meals = new Meals();
            orders.put(idStep, meals);
        }
    }


    @SuppressLint("NewApi")
    public void showOrdres() {
        //Log.e(Constants.TAG_LOG,"showOrdres()");
        lltOrder.removeAllViews();
        categoriesMeals = App.getCategoriesMeals();
        for (CategoryMeal categoryMeal : categoriesMeals) {
            //Log.e(Constants.TAG_LOG,categoryMeal.getIdStep()+": "+categoryMeal.getName());
            View view = LayoutInflater.from(context).inflate(R.layout.item_step_orders, null,
                    false);
            txtOrderName = view.findViewById(R.id.txtOrderName);
            imgAddMeal = view.findViewById(R.id.imgAddMeal);
            rvwOrderList = view.findViewById(R.id.rvwOrderList);
            txtNbGuest = view.findViewById(R.id.txtNbGuest);
            txtNbMeal = view.findViewById(R.id.txtNbMeal);
            txtOrderName.setText(categoryMeal.getName());

            imgAddMeal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    HomeActivity home = (HomeActivity) context;
                    home.showMealsDialog(categoryMeal.getIdCategoryMeal());

                }
            });
            txtNbMeal.setText("0" + "/");
            txtNbGuest.setText("" + guests);
            orderItemAdapter = new OrderItemAdapter(getActivity());
            //orderItemAdapter.loadMeals(orders.get(categoryMeal.getIdCategoryMeal()));
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

    public int getIdOrder() {
        return idOrder;
    }


    public void addOrders(int idStep, Meals list) {
        for (Meal meal : list) {
            int end = meal.getQuantity();
            meal.setQuantity(1);
            for (int i = 0; i < end; i++) {
                orders.get(idStep).add(meal);
                addOrderItem(meal.getIdMeal());
            }
            meal.setQuantity(0);

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
    private void orderCompleted(int done) {


        Call<Push> call = swInterface.orderCompleted(Functions.getAuth(),idOrder,done);

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

    private void addOrderItem(int idMeal) {

        Log.e(Constants.TAG_LOG, "" + App.getMeal().getIdMeal());

        Call<Push> call = swInterface.addOrderItem(Functions.getAuth(), idOrder
                , idMeal);

        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {
                if (response.isSuccessful()) {
                    Log.e(Constants.TAG_LOG, "addOrderItem : " + response.body
                            ().toString());
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                        /*Gson gson = new Gson();
                        mealBdd = gson.fromJson(push.getData(), Meal.class);
                        App.setMeal(mealBdd);*/


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
