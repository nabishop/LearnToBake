package com.example.android.learntobake.Models;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private int quantity;
    private String measurement;
    private String name;

    public Ingredient(int quantity, String measurement, String name) {
        this.quantity = quantity;
        this.measurement = measurement;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getMeasurement() {
        return measurement;
    }

    @Override
    public String toString() {
        String ingredient = "INGREDIENT: QUANTITY: " + quantity + ", MEASUREMENT: " + measurement + ", NAME: " + name;
        return ingredient;
    }
}
