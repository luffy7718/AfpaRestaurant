package com.example.a77011_40_05.afparestaurant.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a77011_40_05.afparestaurant.R;

import java.util.ArrayList;

public class PermissionActivity extends AppCompatActivity {

    ArrayList<String> permissions;
    ArrayList<String> permissionRequest;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        askForPermission();
    }

    @Override
    public void onRequestPermissionsResult(int RequestCode, @NonNull String permission[], @NonNull int[] PermissionResult) {
        switch (RequestCode) {
            case 100: {

                //utilisation spécifique en fonction de l'application
                gotoHome();
            }
            break;
        }
    }

    private void askForPermission() {
        permissions = new ArrayList<>();
        permissionRequest = new ArrayList<>();

        permissions.add(Manifest.permission.ACCESS_NETWORK_STATE);
        permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        
        callPermission();
    }

    private void callPermission(){

        for(int i=0;i < permissions.size();i++) {

            String key = permissions.get(i);

            if (ContextCompat.checkSelfPermission(context, key)
                    != PackageManager.PERMISSION_GRANTED) {

                permissionRequest.add(key);
            }
        }

        if(permissionRequest.size() >0)
        {
            String[] permissionString = new String[permissionRequest.size()];

            for(int i=0; i< permissionRequest.size() ;i++)
            {
                permissionString[i] = permissionRequest.get(i);
            }

            ActivityCompat.requestPermissions(PermissionActivity.this,
                    permissionString, 100);
        }
        else
        {
            gotoHome();
        }
    }

    private void gotoHome(){
        //Session.setUsersList(users);
        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
        finish();
    }
}

