package com.example.android.learntobake.Parsing;

import android.util.Log;

import com.example.android.learntobake.Models.RecipeItem;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class Parsing {
    private static final String JSON_LOCATION = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static ArrayList<RecipeItem> getRecipes() {
        ArrayList<RecipeItem> recipes = new ArrayList<>();
        Gson gson = new GsonBuilder().create();
        String jsonString = getJson(JSON_LOCATION);
        if (jsonString == null) {
            System.out.println("ERROR PARSING");
        } else {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                for (int i = 0; i < jsonArray.length(); i++) {
                    RecipeItem recipeItem = gson.fromJson(jsonArray.getJSONObject(i).toString(),
                            RecipeItem.class);
                    recipes.add(recipeItem);
                }
                return recipes;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e("Parsing", "Error parsing in getRecipes. " + e.getMessage());
                return null;
            }
        }
        return recipes;
    }

    private static String getJson(String source) {
        URLConnection urlConnection = null;
        String response = null;
        InputStream inputStream = null;
        try {
            URL url = new URL(source);
            urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(10000);
            inputStream = urlConnection.getInputStream();
            response = readStream(inputStream);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("Parsing", "Error getting String from website. " + e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String readStream(InputStream inputStream) {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            String currentLine = bufferedReader.readLine();
            while (currentLine != null) {
                builder.append(currentLine);
                currentLine = bufferedReader.readLine();
            }
            return builder.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
