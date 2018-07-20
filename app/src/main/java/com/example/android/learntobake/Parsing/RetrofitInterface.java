package com.example.android.learntobake.Parsing;

import com.example.android.learntobake.Models.RecipeItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RetrofitInterface {
    @GET(ParsingInfo.JSON_EXTENSION)
    Call<ArrayList<RecipeItem>> getRecipesList();
}
