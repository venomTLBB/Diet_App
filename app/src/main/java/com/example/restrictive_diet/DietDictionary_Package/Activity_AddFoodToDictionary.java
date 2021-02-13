package com.example.restrictive_diet.DietDictionary_Package;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.restrictive_diet.DatabaseHelper;
import com.example.restrictive_diet.Food_Package.Model_FoodDefinition;
import com.example.restrictive_diet.R;

import java.util.Vector;

public class Activity_AddFoodToDictionary extends AppCompatActivity {

    EditText etFoodName, etCalsPer100g, etCarbs, etFats, etProtein, etSodium, etSugar;
    Spinner sFoodType, sDietType;
    Button  bCancel, bAddFood;
    DatabaseHelper db;
    Model_FoodDefinition foodDefinition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_to_dictionary);
        getSupportActionBar().hide();
        db = new DatabaseHelper(this);

        etFoodName = findViewById(R.id.editTextFoodName);
        etCalsPer100g = findViewById(R.id.editText_CaloriesPer100g);
        etCarbs = findViewById(R.id.editText_Carbs);
        etFats = findViewById(R.id.editText_Fats);
        etProtein = findViewById(R.id.editText_Protein);
        etSodium = findViewById(R.id.editText_Sodium);
        etSugar = findViewById(R.id.editText_Sugar);

        sFoodType = findViewById(R.id.spinnerFoodType);
        sDietType = findViewById(R.id.spinnerDietType);

        bCancel = findViewById(R.id.buttonCancel);
        bAddFood = findViewById(R.id.buttonAddRecipe);

        populateSpinners();
        initializeOnClickListeners();
    }



    private void populateSpinners()
    {
        //Food Type Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.foodType_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sFoodType.setAdapter(adapter);

        //Diet Spinner
        adapter = ArrayAdapter.createFromResource(this, R.array.diet_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sDietType.setAdapter(adapter);
    }

    private void initializeOnClickListeners()
    {
        bAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodDefinition = createFoodDefinition();
                db.addNewFood(foodDefinition);
                Intent intent = new Intent(Activity_AddFoodToDictionary.this, Activity_DietDictionary.class);
                startActivity(intent);
            }
        });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_AddFoodToDictionary.this, Activity_DietDictionary.class);
                startActivity(intent);
            }
        });
    }

    private Model_FoodDefinition createFoodDefinition()
    {
        String cal, carb, fat, protien, sodium, sugar;
        Vector<Integer> restrictedDiet = new Vector<>();
        String foodType = sFoodType.getSelectedItem().toString();
        String foodName = etFoodName.getText().toString();
        cal = etCalsPer100g.getText().toString();
        carb = etCarbs.getText().toString();
        fat = etFats.getText().toString();
        protien = etProtein.getText().toString();
        sodium = etSodium.getText().toString();
        sugar = etSugar.getText().toString();

        foodDefinition = new Model_FoodDefinition(foodName, Double.valueOf(cal)/100, Double.valueOf(carb)/100, Double.valueOf(fat)/100,
                Double.valueOf(protien)/100, Double.valueOf(sodium)/100, Double.valueOf(sugar)/100, 0, 0, foodType, restrictedDiet,
                        Activity_AddFoodToDictionary.this);

        return foodDefinition;
    }
}
