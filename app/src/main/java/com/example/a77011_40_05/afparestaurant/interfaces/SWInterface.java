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


    @FormUrlEncoded
    @POST("addOrder.php")
    Call<Push> addOrder(
            @Header("Authorization") String authorization,
            @Field("idTable") int idTable,
            @Field("idStaff") int idStaff,
            @Field("guests") int guests,
            @Field("date") String date,
            @Field("done") int done);

    @FormUrlEncoded
    @POST("orderCompleted.php")
    Call<Push> orderCompleted(
            @Header("Authorization") String authorization,
            @Field("idOrder") int idOrder,
            @Field("done") int done);

    @FormUrlEncoded
    @POST("addOrderItem.php")
    Call<Push> addOrderItem(
            @Header("Authorization") String authorization,
            @Field("idOrder") int idOrder,
            @Field("idMeal") int idMeal);

    @FormUrlEncoded
    @POST("removeOrderItem.php")
    Call<Push> removeOrderItem(
            @Header("Authorization") String authorization,
            @Field("idOrderItem") int idOrderItem);

    @FormUrlEncoded
    @POST("getOrder.php")
    Call<Push> getOrder(
            @Header("Authorization") String authorization,
            @Field("idOrder") int idOrder);

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

    @POST("getCategoriesMeals.php")
    Observable<Push> getCategoriesMeals(
            @Header("Authorization") String authorization
    );
}
