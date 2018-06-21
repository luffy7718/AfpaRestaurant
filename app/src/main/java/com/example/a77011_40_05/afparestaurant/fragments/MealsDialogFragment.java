package com.example.a77011_40_05.afparestaurant.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.activities.HomeActivity;
import com.example.a77011_40_05.afparestaurant.adapters.MealAdapter;
import com.example.a77011_40_05.afparestaurant.adapters.OrderAdapter;
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.models.CategoryMeal;
import com.example.a77011_40_05.afparestaurant.models.CategoriesMeals;
import com.example.a77011_40_05.afparestaurant.models.Table;
import com.example.a77011_40_05.afparestaurant.models.Tables;
import com.example.a77011_40_05.afparestaurant.utils.App;
import com.example.a77011_40_05.afparestaurant.utils.Constants;
import com.example.a77011_40_05.afparestaurant.utils.Functions;

public class MealsDialogFragment extends DialogFragment {

    Context context;
    MealAdapter mealAdapter;
    RecyclerView rvwMealsList;
    ViewSwitcher vwsMealSelector;
    Button btnNext, btnPrevious, btnValide;
    LinearLayout lltResume;
    int idCategoryMeal;
    Meals meals;
    TextView txtNbMeal;
    TextView txtNbGuest;
    public int guests;
    int idTable;
    OrderAdapter orderAdapter;

    public MealsDialogFragment() {
        // Required empty public constructor
    }

    public static MealsDialogFragment newInstance(Bundle args) {
        MealsDialogFragment fragment = new MealsDialogFragment();

        //args.putInt("idCategoryMeal", idStep);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            if (getArguments().containsKey("idCategoryMeal")) {
                idCategoryMeal = getArguments().getInt("idCategoryMeal");
            } else {
                Log.e(Constants.TAG_LOG, "ERROR: Pas de idCategoryMeal");
            }

            if (idCategoryMeal != 0) {
                Log.e(Constants.TAG_LOG, "CategoriesMeals: " + idCategoryMeal);
            }

            if (getArguments().containsKey("guests")) {
                guests = getArguments().getInt("guests");
            } else {
                Log.e(Constants.TAG_LOG, "ERROR: Pas de convives");
            }


            if (guests != 0) {
                Log.e(Constants.TAG_LOG, "Table: " + idTable + " pour " + guests + " personnes");
            }

        }
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().setTitle(getStepName());
        addButtonToDialogTitle(getDialog(), context);
        View view = inflater.inflate(R.layout.fragment_meals_dialog, container, false);
        rvwMealsList = view.findViewById(R.id.rvwMealsList);
        vwsMealSelector = view.findViewById(R.id.vwsMealSelector);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        btnValide = view.findViewById(R.id.btnValide);
        lltResume = view.findViewById(R.id.lltResume);
        txtNbMeal = view.findViewById(R.id.txtNbMeal);
        txtNbGuest = view.findViewById(R.id.txtNbGuest);
        txtNbMeal.setText("" + "/");
        txtNbGuest.setText("" + guests);
        btnNext.setOnClickListener(v -> {
            buildResume();
            vwsMealSelector.showNext();
        });

        btnPrevious.setOnClickListener(v -> vwsMealSelector.showPrevious());

        btnValide.setOnClickListener(v -> {
            HomeActivity home = (HomeActivity) getActivity();
            String mode = Functions.getPreferenceString(context, "commandMode");

            switch (mode) {
                case "Menu et Commandes":
                    OrderFragment frag = (OrderFragment) home.getLastFragment();
                    frag.addOrders(idCategoryMeal, meals);
                    frag.showOrdres();
                    break;
                case "Liste papier":
                    Order2Fragment frag2 = (Order2Fragment) home.getLastFragment();
                    frag2.addOrders(idCategoryMeal, meals);
                    break;
                case "Claude":
                    Order3Fragment frag3 = (Order3Fragment) home.getLastFragment();
                    frag3.addOrders(idCategoryMeal, meals);
                    break;
                default:
                    Log.e(Constants.TAG_LOG, "WARNING: Default case");
                    OrderFragment fragDefault = (OrderFragment) home.getLastFragment();
                    fragDefault.addOrders(idCategoryMeal, meals);
                    break;
            }
            getDialog().dismiss();
        });

        mealAdapter = new MealAdapter(getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvwMealsList.setLayoutManager(llm);
        rvwMealsList.setAdapter(mealAdapter);

        getMeals();
        orderAdapter = new OrderAdapter(getActivity());

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void addButtonToDialogTitle(final Dialog mdialog, Context context) {
        final TextView title = mdialog.findViewById(android.R.id.title);
        title.setTextColor(context.getResources().getColor(R.color.textColor));
        title.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
        title.setPadding(60, 30, 20, 30);

        title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_delete_white, 0);
        title.setOnTouchListener((view, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                if (event.getRawX() >= title.getRight() - title.getTotalPaddingRight()) {
                    mdialog.cancel();
                    return true;
                }
            }
            return true;
        });
    }

    private void getMeals() {
        Log.e(Constants.TAG_LOG, "getMeals for " + idCategoryMeal);
        Meals meals = App.getMeals();
        Meals newList = new Meals();
        for (Meal meal : meals) {
            if (meal.getIdCategoryMeal() == idCategoryMeal) {
                Log.e(Constants.TAG_LOG, "Plat: " + meal.getName());
                newList.add(meal);
            }
        }
        mealAdapter.loadMeals(newList);
    }

    private String getStepName() {
        CategoriesMeals categoriesMeals = App.getCategoriesMeals();
        for (CategoryMeal categoryMeal : categoriesMeals) {
            if (categoryMeal.getIdCategoryMeal() == idCategoryMeal) {
                return categoryMeal.getName();
            }
        }
        return "CategoryMeal";
    }

    @SuppressLint("SetTextI18n")
    private void buildResume() {
        meals = new Meals();
        lltResume.removeAllViews();
        for (Meal meal : mealAdapter.getMeals()) {
            if (meal.getQuantity() > 0) {
                meals.add(meal);
                LinearLayout llt = new LinearLayout(context);
                llt.setOrientation(LinearLayout.HORIZONTAL);
                llt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                        .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                TextView txtName = new TextView(context);
                txtName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams
                        .MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
                txtName.setText(meal.getName());
                TextView txtQuantity = new TextView(context);
                txtQuantity.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout
                        .LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 3));
                txtQuantity.setText(meal.getQuantity() + "");
                llt.addView(txtName);
                llt.addView(txtQuantity);
                lltResume.addView(llt);
            }
        }
    }
}
