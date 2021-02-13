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

public class Activity_EditFoodDefinition extends AppCompatActivity {

    EditText etFoodName, etCalsPer100g, etCarbs, etFats, etProtein, etSodium, etSugar;
    Spinner sFoodType, sDietType;
    Button bCancel, bEditFood, bDeleteFood;
    DatabaseHelper db;
    Model_FoodDefinition foodDefinition;
    String logFoodName;
    int spinnerPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food_definition);
        getSupportActionBar().hide();
        Bundle bundle = getIntent().getExtras();
        logFoodName = bundle.getString("LogFoodName");

        db = new DatabaseHelper(this);
        foodDefinition = db.getFoodDefinition(logFoodName);

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
        bDeleteFood = findViewById(R.id.buttonDeleteFood);
        bEditFood = findViewById(R.id.buttonEditFood);

        populateSpinners();
        initializeOnClickListeners();
        populateEditText();
    }

    private void populateEditText()
    {
        etFoodName.setText(foodDefinition.getFoodName());
        etCalsPer100g.setText(String.valueOf(foodDefinition.getCalories()*100));
        etCarbs.setText(String.valueOf(foodDefinition.getCarbs()*100));
        etFats.setText(String.valueOf(foodDefinition.getFat()*100));
        etProtein.setText(String.valueOf(foodDefinition.getProtein()*100));
        etSodium.setText(String.valueOf(foodDefinition.getSodium()*100));
        etSugar.setText(String.valueOf(foodDefinition.getSugar()*100));
        sFoodType.setSelection(spinnerPosition);
    }

    private void populateSpinners()
    {
        //Food Type Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.foodType_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sFoodType.setAdapter(adapter);
        spinnerPosition = adapter.getPosition(foodDefinition.getType());


        //Diet Spinner
        adapter = ArrayAdapter.createFromResource(this, R.array.diet_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sDietType.setAdapter(adapter);
    }

    private void initializeOnClickListeners()
    {
        bEditFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                foodDefinition = createFoodDefinition();
                db.deleteFoodDefinition(logFoodName);
                db.addNewFood(foodDefinition);
            }
        });
        bCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_EditFoodDefinition.this, Activity_DietDictionary.class);
                startActivity(intent);
            }
        });
        bDeleteFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteFoodDefinition(logFoodName);
                Intent intent = new Intent(Activity_EditFoodDefinition.this, Activity_DietDictionary.class);
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
                Activity_EditFoodDefinition.this);

        return foodDefinition;
    }
}

