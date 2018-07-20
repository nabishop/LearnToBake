package com.example.android.learntobake.Adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.learntobake.Models.RecipeItem;
import com.example.android.learntobake.R;
import com.squareup.picasso.Picasso;

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
        RecipeListViewHolder recipeListViewHolder = (RecipeListViewHolder) holder;
        // set ingredient name
        recipeListViewHolder.recipeName.setText(recipes.get(position).getName());
        // set servings
        recipeListViewHolder.recipeServings.setText(recipes.get(position).getServings());

        // try to set image, uses picasso
        if (recipes.get(position).getImage() != null) {
            Uri imageUri = Uri.parse(recipes.get(position).getImage()).buildUpon().build();
            Picasso.with(context).load(imageUri).into(recipeListViewHolder.recipePicture);
        }
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

        @BindView(R.id.main_activity_recipe_servings_tv)
        TextView recipeServings;

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
