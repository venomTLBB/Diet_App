package com.example.restrictive_diet.Profile_Package;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AdapterView;

import com.example.restrictive_diet.DatabaseHelper;
import com.example.restrictive_diet.R;

public class Activity_Profile extends AppCompatActivity {

    private Spinner spinner;
    private Model_Profile userProfile;
    private EditText editText;
    private EditText etName, etAge, etGender, etStartWeight, etCurrWeight, etTargetWeight;
    private Spinner spinHeight, spinWeeklyGoal, spinWeeklyActivity, spinDiet;
    private TextView tvCalMan, tvCalTarget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();

        DatabaseHelper helper = new DatabaseHelper(this);
        userProfile = helper.getProfile(this);

        ArrayAdapter<CharSequence> adapter;

        editText = findViewById(R.id.pd_et_name);

        etName = findViewById(R.id.pd_et_name);
        etAge = findViewById(R.id.pd_et_age);
        etGender = findViewById(R.id.pd_et_gender);
        etStartWeight = findViewById(R.id.fit_et_startWeight);
        etCurrWeight = findViewById(R.id.fitness_et_currWeight);
        etTargetWeight = findViewById(R.id.fit_et_targetWeight);

        tvCalMan = findViewById(R.id.math_output_Maintenance);
        tvCalTarget = findViewById(R.id.math_output_dailyCaloricTarget);

        spinHeight = findViewById(R.id.pd_spinner_hight);
        spinWeeklyGoal = findViewById(R.id.spinner_weeklyGoal);
        spinWeeklyActivity = findViewById(R.id.fit_spinner_weeklyActivity);
        spinDiet = findViewById(R.id.fit_spinner_diet);

        populateSpinners();
        populateActivity(userProfile);
        setEditListeners();
        setSpinnerListeners();
    }

    public void populateSpinners()
    {
        //Diet List Spinner
        spinner = findViewById(R.id.fit_spinner_diet);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.diet_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Weekly Weight Goal Spinner
        spinner = findViewById(R.id.spinner_weeklyGoal);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.goal_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Height Spinner
        spinner = findViewById(R.id.pd_spinner_hight);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.hight_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //Activity Spinner
        spinner = findViewById(R.id.fit_spinner_weeklyActivity);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.activity_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    public void populateActivity(Model_Profile userProfile)
    {
        ArrayAdapter<CharSequence> adapter;

        editText = findViewById(R.id.pd_et_name);
        editText.setText(userProfile.getName());

        editText = findViewById(R.id.pd_et_age);
        editText.setText(String.valueOf(userProfile.getAge()));

        editText = findViewById(R.id.pd_et_gender);
        editText.setText(userProfile.getGender());

        spinner = findViewById(R.id.pd_spinner_hight);
        adapter = ArrayAdapter.createFromResource(this,
                R.array.hight_array, android.R.layout.simple_spinner_item);
        spinner.setSelection(adapter.getPosition(userProfile.getHeightString()));

        editText = findViewById(R.id.fit_et_startWeight);
        editText.setText(String.valueOf(userProfile.getStartWeight()));

        editText = findViewById(R.id.fitness_et_currWeight);
        editText.setText(String.valueOf(userProfile.getCurrWeight()));

        editText = findViewById(R.id.fit_et_targetWeight);
        editText.setText(String.valueOf(userProfile.getTargetWeight()));

        spinner = findViewById(R.id.spinner_weeklyGoal);
        spinner.setSelection(userProfile.getWeeklyGoal());

        spinner = findViewById(R.id.fit_spinner_weeklyActivity);
        spinner.setSelection(userProfile.getWeeklyActivity());

        spinner = findViewById(R.id.fit_spinner_diet);
        spinner.setSelection(userProfile.getSelectedDiet());

        tvCalMan.setText(String.valueOf(userProfile.getDailyCaloricMaintenance()));
        tvCalTarget.setText(String.valueOf(userProfile.getDailyCaloricTarget()));
    }

    public void setEditListeners()
    {
        etName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    //do something
                    editText = findViewById(R.id.pd_et_name);
                    userProfile.setName(editText.getText().toString());
                }
                return false;
            }
        });

        etAge.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    //do something
                    editText = findViewById(R.id.pd_et_age);
                    userProfile.setAge(Integer.parseInt(editText.getText().toString()));
                    updateCaloricTarget();
                }
                return false;
            }
        });

        etGender.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    //do something
                    editText = findViewById(R.id.pd_et_gender);
                    userProfile.setGender(editText.getText().toString());
                    updateCaloricTarget();
                }
                return false;
            }
        });

        etStartWeight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    //do something
                    editText = findViewById(R.id.fit_et_startWeight);
                    userProfile.setStartWeight(Integer.parseInt(editText.getText().toString()));
                }
                return false;
            }
        });

        etCurrWeight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    //do something
                    editText = findViewById(R.id.fitness_et_currWeight);
                    userProfile.setCurrWeight(Integer.parseInt(editText.getText().toString()));
                    updateCaloricTarget();
                }
                return false;
            }
        });

        etTargetWeight.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId==EditorInfo.IME_ACTION_DONE){
                    //do something
                    editText = findViewById(R.id.fit_et_targetWeight);
                    userProfile.setTargetWeight(Integer.parseInt(editText.getText().toString()));
                }
                return false;
            }
        });
    }

    public void setSpinnerListeners()
    {
        spinHeight.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String[] heightArray = getResources().getStringArray(R.array.heightParse_array);
                String test2 = heightArray[position];
                String[] splitString = test2.split(":");

                userProfile.setHeightFeet(Integer.parseInt(splitString[0]));
                userProfile.setHeightInches(Integer.parseInt(splitString[1]));

                updateCaloricTarget();
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinWeeklyGoal.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                userProfile.setWeeklyGoal(position);
                updateCaloricTarget();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinWeeklyActivity.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                userProfile.setWeeklyActivity(position);
                updateCaloricTarget();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinDiet.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                userProfile.setSelectedDiet(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }
    public void populateUserProfile(Model_Profile profile)
    {
        profile = new Model_Profile("Dylan Kuchar", 25, "Male", 5, 7, 172, 162, 150,
                2, 1, 3, this);
    }

    public void updateCaloricTarget()
    {
        tvCalMan.setText(String.valueOf(userProfile.getDailyCaloricMaintenance()));
        tvCalTarget.setText(String.valueOf(userProfile.getDailyCaloricTarget()));
    }

}
