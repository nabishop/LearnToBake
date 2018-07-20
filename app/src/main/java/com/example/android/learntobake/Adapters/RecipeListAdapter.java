package com.example.android.learntobake.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.learntobake.Models.RecipeItem;
import com.example.android.learntobake.R;

import java.util.ArrayList;

import butterknife.ButterKnife;

public class RecipeListAdapter extends RecyclerView.Adapter {
    private Context context;
    private ArrayList<RecipeItem> recipes;
    private RecipesAdapterOnClickHandler recipesAdapterOnClickHandler;

    public interface RecipesAdapterOnClickHandler {
        void onRecipeClick(RecipeItem recipeItem);
    }

    public RecipeListAdapter(RecipesAdapterOnClickHandler recipesAdapterOnClickHandler) {
        this.recipesAdapterOnClickHandler = recipesAdapterOnClickHandler;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View item = inflater.inflate(R.layout.activity_main, parent, false);
        return new RecipesA;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class RecipeListViewHolder extends RecyclerView.ViewHolder{
        TextView recipeName;
        ImageView recipePicture;

        public RecipeListViewHolder(View item){

        }}
}
