package com.example.android.learntobake.Layouts;

import android.content.res.Configuration;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.example.android.learntobake.Adapters.RecipeListAdapter;
import com.example.android.learntobake.Models.RecipeItem;
import com.example.android.learntobake.Parsing.RetrofitBuilder;
import com.example.android.learntobake.Parsing.RetrofitInterface;
import com.example.android.learntobake.R;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Call;


public class MainActivity extends AppCompatActivity implements RecipeListAdapter.RecipesAdapterOnClickHandler {

    // recipe list
    private ArrayList<RecipeItem> recipes;

    // recycler view things
    @BindView(R.id.recycler_view_recipe_list)
    RecyclerView recipleListRecyclerView;
    private RecipeListAdapter recipeListAdapter;

    // Idling resource for testing

    // saved instance keys
    private static final String RECIPLE_LIST_KEY = "recipe_list";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recipeListAdapter = new RecipeListAdapter(this);
        recipleListRecyclerView.setAdapter(recipeListAdapter);

        // handles tablet mode
        handleTabletMode();

        // saved instance
        if (savedInstanceState != null) {
            recipes = savedInstanceState.getParcelableArrayList(RECIPLE_LIST_KEY);
            recipeListAdapter.setRecipes(recipes);
        } else {
            RetrofitInterface retrofitInterface = RetrofitBuilder.getRecipes();
            final Call<ArrayList<RecipeItem>> recipeCall = retrofitInterface.getRecipesList();

        }

    }

    @Override
    public void onRecipeClick(RecipeItem recipeItem) {

    }

    public void handleTabletMode() {
        if (getResources().getBoolean(R.bool.tabletMode)) {
            GridLayoutManager gridLayoutManager;
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                gridLayoutManager = new GridLayoutManager(this, 3);
            } else {
                gridLayoutManager = new GridLayoutManager(this, 2);
            }
            recipleListRecyclerView.setLayoutManager(gridLayoutManager);
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recipleListRecyclerView.setLayoutManager(linearLayoutManager);
        }
    }
}
