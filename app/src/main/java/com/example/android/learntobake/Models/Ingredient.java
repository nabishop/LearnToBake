package com.example.android.learntobake.Models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Ingredient implements Parcelable {
    private double quantity;
    private String measurement;
    private String name;

    public Ingredient(double quantity, String measurement, String name) {
        this.quantity = quantity;
        this.measurement = measurement;
        this.name = name;
    }

    protected Ingredient(Parcel in) {
        this.quantity = in.readDouble();
        this.measurement = in.readString();
        this.name = in.readString();
    }

    public String getName() {
        return name;
    }

    public double getQuantity() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(quantity);
        dest.writeString(measurement);
        dest.writeString(name);
    }

    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>() {

        @Override
        public Ingredient createFromParcel(Parcel source) {
            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
}
