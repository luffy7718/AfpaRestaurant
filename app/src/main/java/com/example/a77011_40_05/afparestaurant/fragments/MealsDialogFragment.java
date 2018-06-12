package com.example.a77011_40_05.afparestaurant.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
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
import com.example.a77011_40_05.afparestaurant.models.Meal;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.models.Step;
import com.example.a77011_40_05.afparestaurant.models.Steps;
import com.example.a77011_40_05.afparestaurant.utils.App;
import com.example.a77011_40_05.afparestaurant.utils.Constants;

public class MealsDialogFragment extends DialogFragment {

    Context context;
    MealAdapter mealAdapter;
    RecyclerView rvwMealsList;
    ViewSwitcher vwsMealSelector;
    Button btnNext, btnPrevious, btnValide;
    LinearLayout lltResume;
    int idStep;
    Meals meals;

    public MealsDialogFragment() {
        // Required empty public constructor
    }

    public static MealsDialogFragment newInstance(int idStep) {
        MealsDialogFragment fragment = new MealsDialogFragment();
        Bundle args = new Bundle();
        args.putInt("idStep",idStep);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            if(getArguments().containsKey("idStep")){
                idStep = getArguments().getInt("idStep");
            }else{
                Log.e(Constants._TAG_LOG,"ERROR: Pas de idStep");
            }

            if(idStep != 0 ){
                Log.e(Constants._TAG_LOG,"Steps: "+idStep);
            }
        }
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getDialog().setTitle(getStepName());
        addButtonToDialogTitle(getDialog());
        View view = inflater.inflate(R.layout.fragment_meals_dialog, container, false);
        rvwMealsList = view.findViewById(R.id.rvwMealsList);
        vwsMealSelector = view.findViewById(R.id.vwsMealSelector);
        btnPrevious = view.findViewById(R.id.btnPrevious);
        btnNext = view.findViewById(R.id.btnNext);
        btnValide = view.findViewById(R.id.btnValide);
        lltResume = view.findViewById(R.id.lltResume);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buildResume();
                vwsMealSelector.showNext();
            }
        });

        btnPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vwsMealSelector.showPrevious();
            }
        });

        btnValide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeActivity home = (HomeActivity) getActivity();
                OrderFragment frag = (OrderFragment) home.getLastFragment();
                frag.addOrders(idStep,meals);
                dismiss();
            }
        });

        mealAdapter = new MealAdapter(getActivity());
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        rvwMealsList.setLayoutManager(llm);
        rvwMealsList.setAdapter(mealAdapter);

        getMeals();
        return view;
    }

    public static void addButtonToDialogTitle(final Dialog mdialog) {


        final TextView title = (TextView) mdialog.findViewById(android.R.id.title);

        title.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_delete, 0);


        title.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= title.getRight() - title.getTotalPaddingRight()) {
                        mdialog.cancel();

                        return true;
                    }
                }
                return true;
            }
        });


    }

    private void getMeals(){
        Meals meals = App.getMeals();
        Meals newList = new Meals();
        for(Meal meal: meals){
            if(meal.getIdStep() == idStep){
                newList.add(meal);
            }
        }
        mealAdapter.loadMeals(newList);
    }

    private String getStepName(){
        Steps steps = App.getSteps();
        for(Step step: steps){
            if(step.getIdStep() == idStep){
                return step.getName();
            }
        }
        return "Step";
    }

    private void buildResume(){
        meals = new Meals();
        lltResume.removeAllViews();
        for(Meal meal: mealAdapter.getMeals()){
            if(meal.getQuantity() > 0){
                meals.add(meal);
                LinearLayout llt = new LinearLayout(context);
                llt.setOrientation(LinearLayout.HORIZONTAL);
                llt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                TextView txtName = new TextView(context);
                txtName.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,1));
                txtName.setText(meal.getName());
                TextView txtQuantity = new TextView(context);
                txtQuantity.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT,3));
                txtQuantity.setText(meal.getQuantity()+"");
                llt.addView(txtName);
                llt.addView(txtQuantity);
                lltResume.addView(llt);
            }
        }
    }
}
