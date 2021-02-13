package com.example.restrictive_diet;

import android.os.Bundle;

import com.example.restrictive_diet.DailyTracker_Package.Activity_DailyTrackerMainMenu;
import com.example.restrictive_diet.DailyTracker_Package.Model_DailyTracker;
import com.example.restrictive_diet.DailyTracker_Package.Model_FoodItem;
import com.example.restrictive_diet.DietDictionary_Package.Activity_DietDictionary;
import com.example.restrictive_diet.Profile_Package.Activity_Profile;
import com.example.restrictive_diet.Recipes_Package.Activity_Recipes;
import com.example.restrictive_diet.Restaurants_Package.Activity_Restaurants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private Button bProfile, bDailyTracker, bDietDictionary, bRecipes, bRestaurants;
    private Vector<Model_FoodItem> morningFoodList, midDayFoodList, eveningFoodList;
    private LocalDate dateToday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // End of table population.

        setContentView(R.layout.activity_main);

        initilize();
        initializeViews();
        initializeOnClickListeners();
        setFoodList();

        //TEST DATA: POPULATING TRACKER TO LOAD ON DAILYTRACKER PAGE
        DatabaseHelper db = new DatabaseHelper(this);
        Clock clock = Clock.systemUTC();
        dateToday = LocalDate.now(clock);

        Model_DailyTracker tracker;
        if(db.trackerExistsForDate(dateToday) == true)
        {
            tracker = db.getTracker(dateToday);
            bRestaurants.setText("Tracker Found");
        }
        else
        {
            Vector<Model_FoodItem> m1, m2, m3;
            m1 = new Vector<>();
            m2 = new Vector<>();
            m3 = new Vector<>();
            tracker = new Model_DailyTracker(dateToday, 1000, m1, m2, m3, this);
            bRestaurants.setText("No Tracker");
        }

        /*
        for (Model_FoodItem foodItem: morningFoodList)
        {
            tracker.addFoodToMorning(foodItem, this);
        }

        for (Model_FoodItem foodItem: midDayFoodList)
        {
            tracker.addFoodToMidday(foodItem, this);
        }

        for (Model_FoodItem foodItem: eveningFoodList)
        {
            tracker.addFoodToEvening(foodItem, this);
        }
        */
        // END OF TEST DATA

        //bRestaurants.setText(String.valueOf(tracker.get_morningFoodVector().size()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void initializeViews()
    {
        bProfile = findViewById(R.id.mm_b_profile);
        bDailyTracker = findViewById(R.id.mm_b_dailyTracker);
        bDietDictionary = findViewById(R.id.mm_b_dietDictionary);
        bRecipes = findViewById(R.id.mm_b_recipes);
        bRestaurants = findViewById(R.id.mm_b_restaurants);
    }

    private void initializeOnClickListeners()
    {
        bProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfile();
            }
        });
        bDailyTracker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDailyTracker();
            }
        });
        bDietDictionary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDietDictionary();
            }
        });
        bRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecipes();
            }
        });
        bRestaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRestaurants();
            }
        });
    }


    private void openProfile()
    {
        Intent intent = new Intent(this, Activity_Profile.class);
        startActivity(intent);
    }

    private void openDailyTracker()
    {
        Intent intent = new Intent(this, Activity_DailyTrackerMainMenu.class);
        startActivity(intent);
    }

    private void openDietDictionary()
    {
        Intent intent = new Intent(this, Activity_DietDictionary.class);
        startActivity(intent);
    }

    private void openRecipes()
    {
        Intent intent = new Intent(this, Activity_Recipes.class);
        startActivity(intent);
    }

    private void openRestaurants()
    {
        Intent intent = new Intent(this, Activity_Restaurants.class);
        startActivity(intent);
    }

    private void initilize()
    {
        // Populate tables from csv files.
        DatabaseHelper dbh = new DatabaseHelper(this);
        dbh.populateFoodDictionary(this);

        // Hide Toolbar with name
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().hide();
    }

    private void setFoodList()
    {
        Model_FoodItem chickenBreast = new Model_FoodItem("Chicken_Today", "Ounces", 3, 250, dateToday,
                0,5,42,555,0);
        Model_FoodItem porkChop = new Model_FoodItem("Pork Chop_Wow", "Ounces", 5, 453, dateToday,
                0,10,40,220,0);
        Model_FoodItem Noodles = new Model_FoodItem("Noodles_Damn", "Ounces", 4, 550, dateToday,
                25,10,0,21,10);

        morningFoodList = new Vector<>();
        morningFoodList.addElement(chickenBreast);
        morningFoodList.addElement(porkChop);
        morningFoodList.addElement(Noodles);

        midDayFoodList = new Vector<>();
        midDayFoodList.addElement(chickenBreast);
        midDayFoodList.addElement(porkChop);
        midDayFoodList.addElement(Noodles);

        eveningFoodList = new Vector<>();
        eveningFoodList.addElement(chickenBreast);
        eveningFoodList.addElement(porkChop);
        eveningFoodList.addElement(Noodles);
    }
}
