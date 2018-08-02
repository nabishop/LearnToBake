package com.example.android.learntobake.Widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.learntobake.Layouts.MainActivity;
import com.example.android.learntobake.Models.RecipeItem;
import com.example.android.learntobake.R;

public class IngredientsWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new Creator(this.getApplicationContext(), intent);
    }

    class Creator implements RemoteViewsService.RemoteViewsFactory {
        private RecipeItem recipeItem;
        private Context context;

        public Creator(Context context, Intent intent) {
            this.context = context;
            this.recipeItem = intent.getParcelableExtra(BakeWidgetService.KEY_WIDGET_RECIPE_ITEM);
        }

        @Override
        public void onCreate() {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.bake_widget_provider);
            if (recipeItem != null) {
                remoteViews.setTextViewText(R.id.widget_good_name, recipeItem.getName());
            } else {
                remoteViews.setTextViewText(R.id.widget_good_name, "Click Me To Launch the App");
                remoteViews.setOnClickFillInIntent(R.id.widget_good_name, new Intent(getApplicationContext(), MainActivity.class));
            }
        }

        @Override
        public void onDataSetChanged() {
            this.recipeItem = BakeWidgetProvider.getRecipeItem();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (recipeItem != null) {
                return recipeItem.getIngredients().size();
            }
            return 0;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.ingredient_individual_view);
            if (recipeItem != null) {
                remoteViews.setTextViewText(R.id.ingredient_view_amount, String.valueOf(recipeItem.getIngredients().get(position).getQuantity()));
                remoteViews.setTextViewText(R.id.ingredient_view_measurement, recipeItem.getIngredients().get(position).getMeasure());
                remoteViews.setTextViewText(R.id.ingredient_view_name, recipeItem.getIngredients().get(position).getName());
            }
            else{

            }
            remoteViews.setOnClickFillInIntent(R.id.ingredient_view_name, new Intent());
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
