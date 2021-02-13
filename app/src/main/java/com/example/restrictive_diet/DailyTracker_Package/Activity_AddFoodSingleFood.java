package com.example.restrictive_diet.DailyTracker_Package;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import android.content.Context;

import com.example.restrictive_diet.DatabaseHelper;
import com.example.restrictive_diet.Food_Package.Model_FoodDefinition;
import com.example.restrictive_diet.MainActivity;
import com.example.restrictive_diet.R;

public class Activity_AddFoodSingleFood extends AppCompatActivity {

    private TextView tvFoodName, tvCaloriesPerServing, tvOutputCaloricTotal;
    private TextView labelCaloriesPerServing, labelServingType, labelServingAmount, labelTotalCalories;
    private EditText etServingAmount;
    private Spinner spinnerServingType;
    private Button addFood;

    private String logDay, logMealType, logFoodName, spinnerValue;

    private Model_FoodItem foodItem;
    private Model_FoodDefinition foodDefinition;
    private Model_DailyTracker tracker;
    String[] dateValues;
    LocalDate localDate;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food_single_food);
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        DatabaseHelper db = new DatabaseHelper(this);
        context = this;
        //UPSTREAM LOGS
        logDay = bundle.getString("LogDay");
        logMealType = bundle.getString("LogMealType");
        logFoodName = bundle.getString("LogFoodName");

        dateValues = logDay.split("-");

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        localDate = LocalDate.parse(logDay, dtf);
        tracker = db.getTracker(localDate);
        /*
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            javaDate = dateFormat.parse(logDay);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        */
        foodDefinition = db.getFoodDefinition(logFoodName);

        //SETTING LABELS, VIEWS
        labelCaloriesPerServing = findViewById(R.id.textView_Label_CaloriesPerServing);
        labelServingType = findViewById(R.id.textView_Label_ServingType);
        labelServingAmount = findViewById(R.id.textView_Label_ServingAmount);
        labelTotalCalories = findViewById(R.id.textView_Label_TotalCalories);

        tvFoodName = findViewById(R.id.textViewFoodName);
        tvCaloriesPerServing = findViewById(R.id.textViewCaloriesPerServing);
        tvOutputCaloricTotal = findViewById(R.id.textView_Output_TotalCalories);

        etServingAmount = findViewById(R.id.editTextServingAmount);
        spinnerServingType = findViewById(R.id.spinnerServingType);

        addFood = findViewById(R.id.buttonAddRecipe);

        //POPULATING SPINNER
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.servingType_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServingType.setAdapter(adapter);

        tvFoodName.setText(foodDefinition.getFoodName());
        tvCaloriesPerServing.setText(String.valueOf(foodDefinition.getCalories()));



        spinnerServingType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                spinnerValue = spinnerServingType.getSelectedItem().toString();
                //tvFoodName.setText(spinnerValue);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        etServingAmount.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_DONE){
                    int test, total;
                    double cals;
                    test = Integer.parseInt(etServingAmount.getText().toString());
                    cals = foodDefinition.getCalories();
                    total = test * (int)cals;
                    tvOutputCaloricTotal.setText(String.valueOf(total));
                }
                return false;
            }
        });

        addFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String foodName = logFoodName;
                String servingType = spinnerValue;
                int numOfServings = Integer.parseInt(etServingAmount.getText().toString());
                double calTotal = foodDefinition.getCalories() * numOfServings;
                LocalDate lDate = localDate;

               foodItem = new Model_FoodItem(foodName, servingType, numOfServings, calTotal, lDate,
                       foodDefinition.getCarbs(), foodDefinition.getFat(), foodDefinition.getProtein(),
                       foodDefinition.getSodium(), foodDefinition.getSugar());

               if(logMealType.equals("Morning"))
               {
                   tracker.addFoodToMorning(foodItem, Activity_AddFoodSingleFood.this);
                   tracker.updateMorningCaloricTotal(Activity_AddFoodSingleFood.this);
                   Intent intent = new Intent(Activity_AddFoodSingleFood.this, MainActivity.class);
                   startActivity(intent);
               }
               else if(logMealType.equals("Noon"))
               {
                   tracker.addFoodToMidday(foodItem, Activity_AddFoodSingleFood.this);
                   tracker.updateMidDayCaloricTotal(Activity_AddFoodSingleFood.this);
                   Intent intent = new Intent(Activity_AddFoodSingleFood.this, MainActivity.class);
                   startActivity(intent);
               }
               else
               {
                   tracker.addFoodToEvening(foodItem, Activity_AddFoodSingleFood.this);
                   tracker.updateEveningCaloricTotal(Activity_AddFoodSingleFood.this);
                   Intent intent = new Intent(Activity_AddFoodSingleFood.this, MainActivity.class);
                   startActivity(intent);
               }
            }
        });
    }



}
