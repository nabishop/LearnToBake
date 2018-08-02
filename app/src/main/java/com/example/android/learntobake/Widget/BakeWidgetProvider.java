package com.example.android.learntobake.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.android.learntobake.Layouts.MainActivity;
import com.example.android.learntobake.Layouts.RecipeDetails;
import com.example.android.learntobake.Models.RecipeItem;
import com.example.android.learntobake.R;

/**
 * Implementation of App Widget functionality.
 */
public class BakeWidgetProvider extends AppWidgetProvider {

    private static RecipeItem recipeItem;

    public static RecipeItem getRecipeItem() {
        return recipeItem;
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId, RecipeItem recipeItem) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.bake_widget_provider);
        if (recipeItem == null) {
            views.setTextViewText(R.id.widget_good_name, "Click Me to Launch the App");
        } else {
            views.setTextViewText(R.id.widget_good_name, recipeItem.getName());
        }

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 10, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.widget_good_name, pendingIntent);

        Intent intent1 = new Intent(context, IngredientsWidgetService.class);
        intent1.putExtra(BakeWidgetService.KEY_WIDGET_RECIPE_ITEM, recipeItem);
        views.setRemoteAdapter(R.id.widget_list_view, intent1);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId, recipeItem);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, BakeWidgetProvider.class));
        if (intent.getAction().equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            recipeItem = intent.getParcelableExtra(BakeWidgetService.KEY_WIDGET_RECIPE_ITEM);
        }
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
        onUpdate(context, appWidgetManager, appWidgetIds);
        super.onReceive(context, intent);
    }
}

