package com.example.restrictive_diet.Recipes_Package;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.restrictive_diet.DailyTracker_Package.Model_FoodItem;
import com.example.restrictive_diet.DatabaseHelper;
import com.example.restrictive_diet.DietDictionary_Package.Activity_AddFoodToDictionary;
import com.example.restrictive_diet.DietDictionary_Package.Activity_DietDictionary;
import com.example.restrictive_diet.MainActivity;
import com.example.restrictive_diet.R;
import java.time.Clock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Vector;

public class Activity_Recipes extends AppCompatActivity {

    private Button bDone, bAddRecipe;
    private ListView listViewRecipes;
    private DatabaseHelper db;
    private Vector<Model_Recipe> recipeVector;
    private Adapter_RecipesListView adapter;
    private LocalDate dateToday;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        getSupportActionBar().hide();

        db = new DatabaseHelper(this);
        recipeVector = db.getAllRecipes();
        //LOAD IN RECIPE

        bDone = findViewById(R.id.buttonDone);
        bAddRecipe = findViewById(R.id.buttonAddRecipe);
        listViewRecipes = findViewById(R.id.listViewRecipes);

        Clock clock = Clock.systemUTC();
        dateToday = LocalDate.now(clock);

        Model_FoodItem chickenBreast = new Model_FoodItem("Chicken Breast", "Ounces", 3, 250, dateToday,
                0,5,42,555,0);
        Model_FoodItem roastCarrots = new Model_FoodItem("Roasted Carrots", "Ounces", 4, 120, dateToday,
                0,10,40,220,0);
        Model_FoodItem oliveOil = new Model_FoodItem("Olive Oil", "TBSP", 1, 120, dateToday,
                0,30,0,0,0);
        Model_FoodItem beer = new Model_FoodItem("Harpoon IPA", "Ounces", 12, 180, dateToday,
                14,0,0,0,0);
        Vector<Model_FoodItem> foodVector = new Vector<>();
        foodVector.addElement(chickenBreast);
        foodVector.addElement(roastCarrots);
        foodVector.addElement(oliveOil);
        foodVector.addElement(beer);

        Model_Recipe r1 = new Model_Recipe("Chicken Dinner", foodVector,
                0,0,0,0,0,0);
        recipeVector.add(r1);

        db.updateRecipe(r1);
        recipeVector = db.getAllRecipes();
        adapter = new Adapter_RecipesListView(this, new ArrayList<>(recipeVector));
        listViewRecipes.setAdapter(adapter);


        initializeOnClickListeners();
    }

    private void initializeOnClickListeners()
    {
        bAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Recipes.this, Activity_AddFoodToDictionary.class);
                startActivity(intent);
            }
        });
        bDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Recipes.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
