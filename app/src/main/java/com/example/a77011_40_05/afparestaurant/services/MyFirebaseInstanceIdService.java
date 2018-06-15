package com.example.a77011_40_05.afparestaurant.services;

import android.util.Log;

import com.example.a77011_40_05.afparestaurant.interfaces.SWInterface;
import com.example.a77011_40_05.afparestaurant.models.Push;
import com.example.a77011_40_05.afparestaurant.utils.Constants;
import com.example.a77011_40_05.afparestaurant.utils.Functions;
import com.example.a77011_40_05.afparestaurant.utils.RetrofitApi;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

    public MyFirebaseInstanceIdService() {
    }

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(Constants.TAG_LOG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(final String refreshedToken) {
        Functions.addPreferenceString(getApplicationContext(),"token",refreshedToken);

        SWInterface swInterface = RetrofitApi.getInterface();
        String idDevice = Functions.getPreferenceString(getApplicationContext(),"idDevice");

        if(idDevice.equals("")){
            Call<Push> call = swInterface.addDevice(Functions.getAuth(),refreshedToken);
            call.enqueue(new Callback<Push>() {
                @Override
                public void onResponse(Call<Push> call, Response<Push> response) {
                    if(response.isSuccessful()){
                        Push push = response.body();
                        if(push.getStatus() == 1){
                            String newId = push.getData();
                            Functions.addPreferenceString(getApplicationContext(),"idDevice",newId);
                            Log.d(Constants.TAG_LOG,"New idDevice: "+newId);
                        }else{
                            Log.e(Constants.TAG_LOG,"Error: addDevice.ph | "+push.getData());
                        }
                    }else{
                        Log.e(Constants.TAG_LOG,"Error: "+response.toString());
                    }
                }

                @Override
                public void onFailure(Call<Push> call, Throwable t) {
                    Log.e(Constants.TAG_LOG,"Error: "+t.getMessage());
                }
            });
        }else{
            Call<Push> call = swInterface.setDevice(Functions.getAuth(), Integer.parseInt(idDevice),refreshedToken);
            call.enqueue(new Callback<Push>() {
                @Override
                public void onResponse(Call<Push> call, Response<Push> response) {
                    if(response.isSuccessful()){
                        Push push = response.body();
                        if(push.getStatus() == 1){
                            Log.d(Constants.TAG_LOG,"Token add to DB: "+refreshedToken);
                        }else{
                            Log.e(Constants.TAG_LOG,"Error: addDevice.ph | "+push.getData());
                        }
                    }else{
                        Log.e(Constants.TAG_LOG,"Error: "+response.toString());
                    }
                }

                @Override
                public void onFailure(Call<Push> call, Throwable t) {
                    Log.e(Constants.TAG_LOG,"Error: "+t.getMessage());
                }
            });
        }



    }
}
