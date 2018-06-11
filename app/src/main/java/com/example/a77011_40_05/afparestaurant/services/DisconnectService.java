package com.example.a77011_40_05.afparestaurant.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.a77011_40_05.afparestaurant.activities.HomeActivity;
import com.example.a77011_40_05.afparestaurant.activities.LoginActivity;
import com.example.a77011_40_05.afparestaurant.interfaces.SWInterface;
import com.example.a77011_40_05.afparestaurant.models.Push;
import com.example.a77011_40_05.afparestaurant.utils.Constants;
import com.example.a77011_40_05.afparestaurant.utils.Functions;
import com.example.a77011_40_05.afparestaurant.utils.RetrofitApi;
import com.example.a77011_40_05.afparestaurant.utils.Session;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DisconnectService extends Service {

    private static final String TAG = Constants._TAG_LOG+" Service";

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand()");
        return START_NOT_STICKY;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Log.i(TAG, "onTaskRemoved()");
        SWInterface swInterface = RetrofitApi.getInterface();

        Log.e(Constants._TAG_LOG,"Logout");
        Call<Push> call = swInterface.logout(Functions.getAuth(), Session.getMyUser().getIdStaff());
        call.enqueue(new Callback<Push>() {
            @Override
            public void onResponse(Call<Push> call, Response<Push> response) {
                if (response.isSuccessful()) {
                    Push push = response.body();
                    if (push.getStatus() == 1) {
                       Log.e(Constants._TAG_LOG,"FIN");
                       //stopSelf();
                       //super.onTaskRemoved(rootIntent);
                    } else {
                        //Toast.makeText(context, push.getData(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e(Constants._TAG_LOG, response.toString());
                }
            }

            @Override
            public void onFailure(Call<Push> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.i(TAG, "onLowMemory()");
    }
}
