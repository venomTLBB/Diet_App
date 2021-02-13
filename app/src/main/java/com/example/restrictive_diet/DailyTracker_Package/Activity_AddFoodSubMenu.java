package com.example.restrictive_diet.DailyTracker_Package;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.restrictive_diet.DatabaseHelper;
import com.example.restrictive_diet.Food_Package.Model_FoodDefinition;
import com.example.restrictive_diet.R;
import com.example.restrictive_diet.Recipes_Package.Adapter_RecipesListView;
import com.example.restrictive_diet.Recipes_Package.Model_Recipe;

import java.util.ArrayList;
import java.util.Vector;

public class Activity_AddFoodSubMenu extends AppCompatActivity{

    private ListView listViewFoodView;
    private ArrayList<Model_FoodDefinition> arrayFoodDefinitions;
    private TextView tvTitle;
    private Adapter_FoodListView adapterFoodListView;
    private Adapter_RecipesListView adapterRecipe;
    private Vector<Model_FoodDefinition> vectorFood;
    private Vector<Model_Recipe> vectorRecipe;
    String logDay, logFoodType, logMealType, logDietType;

    //private SearchView svFood;

    private Vector<Integer> vectorRestrictedDiet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_add_food_sub_menu);

        //Variables coming upstream from AddFoodMainMenu Activity
        Bundle bundle = getIntent().getExtras();
        logDay = bundle.getString("LogDay");
        logMealType = bundle.getString("LogMealType");
        logFoodType = bundle.getString("LogFoodType");
        logDietType = bundle.getString("LogDietType");

        DatabaseHelper db = new DatabaseHelper(this);
        //THIS IS WHERE THE SORTING STARTS FOR DIET DICTIONARY

        //TODO: Write methods to fetch foods from FoodDictionary specified by Diet Restrictions and Food Type

        listViewFoodView = findViewById(R.id.listViewFood);

        vectorFood = db.getFoodList(logFoodType, Integer.parseInt(logDietType));
        vectorRecipe = db.getAllRecipes();
        if(logFoodType.equals("RECIPE"))
        {
            adapterRecipe = new Adapter_RecipesListView(this, new ArrayList<>(vectorRecipe));
            listViewFoodView.setAdapter(adapterRecipe);
        }
        else
        {
            arrayFoodDefinitions = new ArrayList<>(vectorFood);
            adapterFoodListView = new Adapter_FoodListView(this, arrayFoodDefinitions, logDay, logMealType);
            listViewFoodView.setAdapter(adapterFoodListView);
        }




    }
}
