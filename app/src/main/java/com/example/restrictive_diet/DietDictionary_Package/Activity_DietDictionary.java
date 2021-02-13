package com.example.restrictive_diet.DietDictionary_Package;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.restrictive_diet.DatabaseHelper;
import com.example.restrictive_diet.Food_Package.Model_FoodDefinition;
import com.example.restrictive_diet.MainActivity;
import com.example.restrictive_diet.Profile_Package.Model_Profile;
import com.example.restrictive_diet.R;

import java.util.ArrayList;
import java.util.Vector;

public class Activity_DietDictionary extends AppCompatActivity {


    private Spinner sFoodType, sDietType;
    private ListView lvDictionaryFoods;
    private Button bAddFood, bDone;
    private Vector<Model_FoodDefinition> vectorFood;
    private ArrayList<Model_FoodDefinition> arrayFoodDefinitions;
    private Adapter_FoodDictionaryListView adapter;

    private DatabaseHelper db;
    private Model_Profile user;
    String foodType;
    int dietTypeint;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_dictionary);
        getSupportActionBar().hide();

        db = new DatabaseHelper(this);
        user = db.getProfile(this);
        dietTypeint = user.getSelectedDiet();

        initializeViews();
        populateSpinners(); //populates spinner values
        initializeOnSelectListeners(); //Sets the spinner listeners
        initializeOnClickListeners(); //Sets buttons to listen

        vectorFood = db.getFoodList("MEAT", dietTypeint);
        arrayFoodDefinitions = new ArrayList<>(vectorFood);

        adapter = new Adapter_FoodDictionaryListView(this, arrayFoodDefinitions, "1", "1");
        lvDictionaryFoods.setAdapter(adapter);
    }

    private void initializeViews()
    {
        sFoodType = findViewById(R.id.spinnerFoodType);
        sDietType = findViewById(R.id.spinnerDietType);

        lvDictionaryFoods = findViewById(R.id.listViewDictionaryFoods);

        bAddFood = findViewById(R.id.buttonAddRecipe);
        bDone = findViewById(R.id.buttonDone);
    }

    private void populateSpinners()
    {
        //Food Type Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.foodType_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sFoodType.setAdapter(adapter);
        foodType = sFoodType.getSelectedItem().toString();

        //Diet Spinner
        adapter = ArrayAdapter.createFromResource(this, R.array.diet_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sDietType.setAdapter(adapter);
        sDietType.setSelection(dietTypeint);
    }

    private void initializeOnClickListeners()
    {
        bAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_DietDictionary.this, Activity_AddFoodToDictionary.class);
                startActivity(intent);
            }
        });
        bDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_DietDictionary.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initializeOnSelectListeners()
    {
        sFoodType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                foodType = sFoodType.getItemAtPosition(position).toString().toUpperCase();


                if(foodType.equals("NONE"))
                {
                    vectorFood = db.getFoodList(dietTypeint);
                }
                else
                {
                    vectorFood = db.getFoodList(foodType, dietTypeint);
                }
                arrayFoodDefinitions = new ArrayList<>(vectorFood);
                adapter = new Adapter_FoodDictionaryListView(Activity_DietDictionary.this, arrayFoodDefinitions, "1", "1");
                lvDictionaryFoods.setAdapter(adapter);
                //FILTER LIST BASED ON NEW SELECTION
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        sDietType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                //FILTER LIST BASED ON NEW SELECTION
                dietTypeint = position;
                if(foodType.equals("NONE"))
                {
                    vectorFood = db.getFoodList(dietTypeint);
                }
                else
                {
                    vectorFood = db.getFoodList(foodType, dietTypeint);
                }
                arrayFoodDefinitions = new ArrayList<>(vectorFood);
                adapter = new Adapter_FoodDictionaryListView(Activity_DietDictionary.this, arrayFoodDefinitions, "1", "1");
                lvDictionaryFoods.setAdapter(adapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });
    }
}
