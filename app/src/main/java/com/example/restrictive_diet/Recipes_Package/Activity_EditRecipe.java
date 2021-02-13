package com.example.restrictive_diet.Recipes_Package;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import com.example.restrictive_diet.DailyTracker_Package.Adapter_FoodListView;
import com.example.restrictive_diet.DailyTracker_Package.Model_FoodItem;
import com.example.restrictive_diet.DatabaseHelper;
import com.example.restrictive_diet.R;

import java.util.ArrayList;

public class Activity_EditRecipe extends AppCompatActivity {

    private ListView listView;
    private Adapter_EditRecipeListView adapter;
    private Button bClose, bAddFood;
    private String recipeName;
    private DatabaseHelper db;
    private Model_Recipe recipe;
    private ArrayList<Model_FoodItem> arrayFoodItem;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activty_edit_recipe);
        Bundle bundle = getIntent().getExtras();
        recipeName = bundle.getString("LogRecipeName");
        db = new DatabaseHelper(this);
        recipe = db.getRecipe(recipeName);

        listView = findViewById(R.id.listViewIngredients);
        bClose = findViewById(R.id.buttonDone);
        bAddFood = findViewById(R.id.buttonAddFood);

        arrayFoodItem = new ArrayList<>(recipe.getIngredients());

        adapter = new Adapter_EditRecipeListView(this, arrayFoodItem);
        listView.setAdapter(adapter);
    }
}
