package com.example.a77011_40_05.afparestaurant.models;

public class OrderItem extends Meal {

    int idOrderItem;

    public OrderItem() {
    }

    public OrderItem(Meal meal) {
        super(meal);
    }

    public int getIdOrderItem() {
        return idOrderItem;
    }

    public void setIdOrderItem(int idOrderItem) {
        this.idOrderItem = idOrderItem;
    }
}
