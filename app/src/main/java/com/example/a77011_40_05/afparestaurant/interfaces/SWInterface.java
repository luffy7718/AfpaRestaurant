package com.example.a77011_40_05.afparestaurant.interfaces;

import com.example.a77011_40_05.afparestaurant.models.Push;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SWInterface {

    /******************************************
     * CALL
     ******************************************/
    @FormUrlEncoded
    @POST("/afpa_restaurant/forceLogin.php")//forceLogin.php ou login.php
    Call<Push> login(
            @Header("Authorization") String authorization,
            @Field("login") String login,
            @Field("password") String password,
            @Field("idDevice") int idDevice);

    @FormUrlEncoded
    @POST("logout.php")
    Call<Push> logout(
            @Header("Authorization") String authorization,
            @Field("idStaff") int idStaff);

    @FormUrlEncoded
    @POST("addDevice.php")
    Call<Push> addDevice(
            @Header("Authorization") String authorization,
            @Field("token") String token);

    @FormUrlEncoded
    @POST("setDevice.php")
    Call<Push> setDevice(
            @Header("Authorization") String authorization,
            @Field("idDevice") int idDevice,
            @Field("token") String token);

    @POST("getTables.php")
    Call<Push> getTables(
            @Header("Authorization") String authorization);

    /******************************************
     * OBSERVABLE
     ******************************************/

    @POST("getJobs.php")
    Observable<Push> getJobs(
            @Header("Authorization") String authorization
    );

    @POST("getMeals.php")
    Observable<Push> getMeals(
            @Header("Authorization") String authorization
    );

    @POST("getMealcategories.php")
    Observable<Push> getMealcategories(
            @Header("Authorization") String authorization
    );

    @POST("getSteps.php")
    Observable<Push> getSteps(
            @Header("Authorization") String authorization
    );
}
