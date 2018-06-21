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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.activities.HomeActivity;
import com.example.a77011_40_05.afparestaurant.adapters.OrderAdapter;
import com.example.a77011_40_05.afparestaurant.adapters.CategoryMealAdapter;
import com.example.a77011_40_05.afparestaurant.models.CategoriesMeals;
import com.example.a77011_40_05.afparestaurant.models.CategoryMeal;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.utils.App;
import com.example.a77011_40_05.afparestaurant.utils.Constants;

import java.util.HashMap;

public class OrderFragment extends Fragment {

    Context context;
    int idTable;
    public int guests;
    CategoryMealAdapter categoryMealAdapter;
    boolean isFirstPage = true;
    OrderAdapter orderAdapter;
    HashMap<Integer, Meals> orders;
     CategoriesMeals categoriesMeals;
    //ELEMENTS
    Button btnOrders;
    Button btnBill;
    ViewSwitcher vwsOrder;
    RecyclerView rvwStepsList;
    LinearLayout lltOrder;
    RecyclerView rvwOrderList;
    TextView txtOrderName;
    ImageView imgAddMeal;
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

        categoryMealAdapter = new CategoryMealAdapter(getActivity());
        categoryMealAdapter.loadSteps(App.getCategoriesMeals());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(context, 2);
        rvwStepsList.setLayoutManager(layoutManager);
        rvwStepsList.setAdapter(categoryMealAdapter);

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
            Meals meals = new Meals();
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
            txtNbMeal.setText("" + "/");
            txtNbGuest.setText("" + guests);
            orderAdapter = new OrderAdapter(getActivity());
            orderAdapter.loadMeals(orders.get(categoryMeal.getIdCategoryMeal()));
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rvwOrderList.setLayoutManager(llm);
            rvwOrderList.setAdapter(orderAdapter);

            lltOrder.addView(view);
        }

    }


    public int getGuests() {
        return guests;
    }

    public void addOrders(int idStep, Meals list) {
        orders.get(idStep).clear();
        for (Meal meal : list) {
            int end = meal.getQuantity();
            meal.setQuantity(1);
            for (int i = 0; i < end; i++) {
                orders.get(idStep).add(meal);
            }
            meal.setQuantity(0);
        }
    }
}
