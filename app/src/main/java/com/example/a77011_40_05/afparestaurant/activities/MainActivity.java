package com.example.a77011_40_05.afparestaurant.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.a77011_40_05.afparestaurant.R;
import com.example.a77011_40_05.afparestaurant.interfaces.SWInterface;
import com.example.a77011_40_05.afparestaurant.models.CategoriesMeals;
import com.example.a77011_40_05.afparestaurant.models.Meals;
import com.example.a77011_40_05.afparestaurant.models.Push;
import com.example.a77011_40_05.afparestaurant.utils.App;
import com.example.a77011_40_05.afparestaurant.utils.Constants;
import com.example.a77011_40_05.afparestaurant.utils.Functions;
import com.example.a77011_40_05.afparestaurant.utils.RetrofitApi;
import com.google.gson.Gson;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    Context context;
    SWInterface swInterface;
    Meals meals;
    CategoriesMeals categoriesMeals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swInterface = RetrofitApi.getInterface();
        getDataFromDB();
    }

    private void goToLogin() {
        Intent intent;
        if (Build.VERSION.SDK_INT >= 23) {
            intent = new Intent(getApplicationContext(), PermissionActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), LoginActivity.class);
        }

        startActivity(intent);
        finish();
    }

    @SuppressWarnings("unchecked")
    @SuppressLint("CheckResult")
    private void getDataFromDB() {//DB:Data Base
        Log.e(Constants.TAG_LOG, "getDataFromDB");
        Observable.just(swInterface).subscribeOn(Schedulers.computation())
                .flatMap(s -> {
                    Observable<Push> stepsObservable
                            = s.getCategoriesMeals(Functions.getAuth()).subscribeOn(Schedulers.io());
                    Observable<Push> mealsObservable
                            = s.getMeals(Functions.getAuth()).subscribeOn(Schedulers.io());
                    return Observable.concatArray(stepsObservable, mealsObservable);
                }).observeOn(AndroidSchedulers.mainThread()).subscribe(this::handleResults,
                this::handleError);

    }

    private void handleResults(Object object) {
        if (object instanceof Push) {
            Push push = (Push) object;
            Log.e(Constants.TAG_LOG, push.toString());
            Gson gson=new Gson();

            switch (push.getType()) {
                case "categories_meals":
                    categoriesMeals = gson.fromJson(push.getData(), CategoriesMeals.class);
                    App.setCategoriesMeals(categoriesMeals);
                    break;
                case "meals":
                    meals = gson.fromJson(push.getData(), Meals.class);
                    App.setMeals(meals);
                    break;
            }
            Log.e(Constants.TAG_LOG,"CategoriesMeals:"+String.valueOf(categoriesMeals !=null)+" | Meals:"+String.valueOf(meals!=null));
            if(categoriesMeals != null && meals!=null) {
                Log.e(Constants.TAG_LOG,"APP loaded");
                goToLogin();
            }
        }


    }

    private void handleError(Throwable t) {
        Log.e("Observer", "" + t.toString());
        Toast.makeText(this, "ERROR GETTING DATA",
                Toast.LENGTH_LONG).show();
    }
}
