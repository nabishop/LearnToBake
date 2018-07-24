package com.example.android.learntobake.Layouts;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.android.learntobake.Adapters.RecipeListAdapter;
import com.example.android.learntobake.Models.RecipeItem;
import com.example.android.learntobake.Parsing.RetrofitBuilder;
import com.example.android.learntobake.Parsing.RetrofitInterface;
import com.example.android.learntobake.R;
import com.example.android.learntobake.Utils.SimpleIdlingResource;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipesAdapterOnClickHandler {

    // recipe list
    private ArrayList<RecipeItem> recipes;

    // recycler view things
    @BindView(R.id.recycler_view_recipe_list)
    RecyclerView recipeListRecyclerView;
    private RecipeListAdapter recipeListAdapter;

    // Idling resource for testing
    private SimpleIdlingResource idlingResource;

    // keys
    private static final String RECIPE_LIST_KEY = "recipe_list";
    private static final String RECIPE_INTENT_KEY = "recipe_key";

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (recipes != null) {
            outState.putParcelableArrayList(RECIPE_LIST_KEY, recipes);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        recipeListAdapter = new RecipeListAdapter(this);
        System.out.println("Recycler View: " + recipeListRecyclerView);
        recipeListRecyclerView.setAdapter(recipeListAdapter);

        // handles tablet mode
        handleTabletMode();

        // saved instance
        if (savedInstanceState != null) {
            System.out.println("Saved instance");
            recipes = savedInstanceState.getParcelableArrayList(RECIPE_LIST_KEY);
            System.out.println(recipes);
            recipeListAdapter.setRecipes(recipes);
        } else {
            System.out.println("getting data");
            getRecipeData();
        }
    }

    @Override
    public void onRecipeClick(RecipeItem recipeItem) {
        Intent launchDetails = new Intent(this, RecipeDetails.class);
        launchDetails.putExtra(getRecipeIntentKey(), recipeItem);
        startActivity(launchDetails);
    }

    private void handleTabletMode() {
        if (getResources().getBoolean(R.bool.tabletMode)) {
            GridLayoutManager gridLayoutManager;
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridLayoutManager = new GridLayoutManager(this, 3);
            } else {
                gridLayoutManager = new GridLayoutManager(this, 2);
            }
            recipeListRecyclerView.setLayoutManager(gridLayoutManager);
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recipeListRecyclerView.setLayoutManager(linearLayoutManager);
        }
    }

    private void getRecipeData() {
        RetrofitInterface retrofitInterface = RetrofitBuilder.getRecipes();
        final Call<ArrayList<RecipeItem>> recipeCall = retrofitInterface.getRecipesList();

        getIdlingResource();
        idlingResource.setIsIdleNow(false);

        recipeCall.enqueue(new Callback<ArrayList<RecipeItem>>() {
            @Override
            public void onResponse(Call<ArrayList<RecipeItem>> call, Response<ArrayList<RecipeItem>> response) {
                recipes = response.body();
                System.out.println(recipes);
                if (recipes == null) {
                    createErrorToast();
                } else {
                    recipeListAdapter.setRecipes(recipes);
                }
                idlingResource.setIsIdleNow(true);
            }

            @Override
            public void onFailure(Call<ArrayList<RecipeItem>> call, Throwable t) {
                createErrorToast();
            }
        });
    }


    private SimpleIdlingResource getIdlingResource() {
        if (idlingResource == null) {
            idlingResource = new SimpleIdlingResource();
        }
        return idlingResource;
    }

    private void createErrorToast() {
        Toast.makeText(getBaseContext(), "Error getting recipe data", Toast.LENGTH_LONG).show();
    }

    public static String getRecipeIntentKey() {
        return RECIPE_INTENT_KEY;
    }
}
