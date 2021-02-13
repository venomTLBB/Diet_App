package com.example.restrictive_diet.DailyTracker_Package;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.restrictive_diet.Food_Package.Model_FoodDefinition;
import com.example.restrictive_diet.R;


public class Activity_AddFoodMainMenu extends AppCompatActivity {

    Button bMeats, bFish, bDairy, bFruits, bVegetables, bGrains, bOils, bRecipes;
    String logDay, logFoodType, logMealType, logDietType;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_main_menu);
        //Hiding Toolbar
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        logDay = bundle.getString("LogDay");
        logMealType = bundle.getString("LogMealType");
        logDietType = bundle.getString("LogDietType");

        this.initializeViews();
        this.initializeOnClickListeners();

        /*
        bMeats.setText(message);
        bFish.setText(String.valueOf(m2));

        DatabaseHelper dbh = new DatabaseHelper(this);
        Date testDate = new Date(2019, 12, 4);

        Model_DailyTracker test2 = new Model_DailyTracker(testDate, 1, 7, -1, 9, 10, new Vector<Model_FoodItem>(), new Vector<Model_FoodItem>(), new Vector<Model_FoodItem>());
        dbh.getTracker(test2, new Date(2019, 12, 4));

        bFish.setText(String.valueOf(test2.get_morningCaloricTotal()));
        */
    }

    private void initializeViews()
    {
        bMeats = findViewById(R.id.buttonMeats);
        bFish = findViewById(R.id.buttonFish);
        bDairy = findViewById(R.id.buttonDairy);
        bFruits = findViewById(R.id.buttonFruits);
        bVegetables = findViewById(R.id.buttonVegetables);
        bGrains = findViewById(R.id.buttonGrains);
        bOils = findViewById(R.id.buttonOils);
        bRecipes = findViewById(R.id.buttonRecipes);
    }

    private void initializeOnClickListeners()
    {
        bMeats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_AddFoodMainMenu.this, Activity_AddFoodSubMenu.class);
                intent.putExtra("LogDay", logDay);
                intent.putExtra("LogMealType", logMealType);
                intent.putExtra("LogDietType", logDietType);
                intent.putExtra("LogFoodType", Model_FoodDefinition.MEAT);
                startActivity(intent);
            }
        });

        bFish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_AddFoodMainMenu.this, Activity_AddFoodSubMenu.class);
                intent.putExtra("LogDay", logDay);
                intent.putExtra("LogMealType", logMealType);
                intent.putExtra("LogDietType", logDietType);
                intent.putExtra("LogFoodType", Model_FoodDefinition.FISH);
                startActivity(intent);
            }
        });

        bDairy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_AddFoodMainMenu.this, Activity_AddFoodSubMenu.class);
                intent.putExtra("LogDay", logDay);
                intent.putExtra("LogMealType", logMealType);
                intent.putExtra("LogDietType", logDietType);
                intent.putExtra("LogFoodType", Model_FoodDefinition.DAIRY);
                startActivity(intent);
            }
        });

        bFruits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_AddFoodMainMenu.this, Activity_AddFoodSubMenu.class);
                intent.putExtra("LogDay", logDay);
                intent.putExtra("LogMealType", logMealType);
                intent.putExtra("LogDietType", logDietType);
                intent.putExtra("LogFoodType", Model_FoodDefinition.FRUIT);
                startActivity(intent);
            }
        });

        bVegetables.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_AddFoodMainMenu.this, Activity_AddFoodSubMenu.class);
                intent.putExtra("LogDay", logDay);
                intent.putExtra("LogMealType", logMealType);
                intent.putExtra("LogDietType", logDietType);
                intent.putExtra("LogFoodType", Model_FoodDefinition.VEGETABLE);
                startActivity(intent);
            }
        });

        bGrains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_AddFoodMainMenu.this, Activity_AddFoodSubMenu.class);
                intent.putExtra("LogDay", logDay);
                intent.putExtra("LogMealType", logMealType);
                intent.putExtra("LogDietType", logDietType);
                intent.putExtra("LogFoodType", Model_FoodDefinition.GRAIN);
                startActivity(intent);
            }
        });

        bOils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_AddFoodMainMenu.this, Activity_AddFoodSubMenu.class);
                intent.putExtra("LogDay", logDay);
                intent.putExtra("LogMealType", logMealType);
                intent.putExtra("LogDietType", logDietType);
                intent.putExtra("LogFoodType", Model_FoodDefinition.OIL);
                startActivity(intent);
            }
        });

        bRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_AddFoodMainMenu.this, Activity_AddFoodSubMenu.class);
                intent.putExtra("LogDay", logDay);
                intent.putExtra("LogMealType", logMealType);
                intent.putExtra("LogDietType", logDietType);
                intent.putExtra("LogFoodType", "RECIPE");
                startActivity(intent);
            }
        });
    }
}
