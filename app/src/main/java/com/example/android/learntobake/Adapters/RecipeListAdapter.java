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

import butterknife.BindView;
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
        View view = inflater.inflate(R.layout.main_activity_reciple_item, parent, false);
        return new RecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        if (recipes == null) {
            return 0;
        } else {
            return recipes.size();
        }
    }


    public void setRecipes(ArrayList<RecipeItem> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    public class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.main_activity_recipe_tv)
        TextView recipeName;

        @BindView(R.id.main_activity_recipe_iv)
        ImageView recipePicture;

        public RecipeListViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recipesAdapterOnClickHandler.onRecipeClick(recipes.get(getAdapterPosition()));
        }
    }


}
