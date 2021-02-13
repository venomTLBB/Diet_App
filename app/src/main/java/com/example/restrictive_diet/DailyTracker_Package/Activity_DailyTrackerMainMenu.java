package com.example.restrictive_diet.DailyTracker_Package;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Calendar;

import com.example.restrictive_diet.DatabaseHelper;
import com.example.restrictive_diet.Profile_Package.Model_Profile;
import com.example.restrictive_diet.R;

import java.util.Vector;

public class Activity_DailyTrackerMainMenu extends AppCompatActivity {

    private Model_Profile userProfile;
    private TableLayout tlMorning, tlMidDay, tlEvening;
    private Button bAddFoodMorning, bAddFoodMidDay, bAddFoodEvening, bDetail, bPrevDay, bCurrDay, bNextDay;
    private TextView tvCaloricTracker, morningCaloricTotal, midDayCaloricTotal, eveningCaloricTotal;

    private Model_DailyTracker tracker;
    private Vector<Model_FoodItem> morningFoodList, midDayFoodList, eveningFoodList;
    /*
    private Date currentDate;
    private Instant testInstant;
    private java.util.Date testDate;
    */
    private LocalDate dateToday;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_tracker__food_);
        getSupportActionBar().hide();

        DatabaseHelper db = new DatabaseHelper(this);

        //TODO create static utility class to fetch DB objects when requested in Activities
        userProfile = db.getProfile(this);

        //TODO move dbHelper methods into a Static class
        // Check if a food tracker for today's date exists and load it in if so.  If not, create a new one.
        Clock clock = Clock.systemUTC();
        dateToday = LocalDate.now(clock);

        this.initializeViews();

        if(db.trackerExistsForDate(dateToday) == true)
        {
            tracker = db.getTracker(dateToday);
        }
        else
        {
            tracker = new Model_DailyTracker(dateToday, 1000,
                    new Vector<Model_FoodItem>(), new Vector<Model_FoodItem>(), new Vector<Model_FoodItem>(), this);
        }

        //currentDate = new Date(localDate.getYear(),localDate.getMonthValue(),localDate.getDayOfMonth());
        //testDate = Calendar.getInstance().getTime();
        //testInstant = Instant.now();

        //setFoodLists();
        //setFoodTracker();

        this.initializeOnClickListeners();
        this.populateViews();

        setFoodLists(tlMorning, tracker.get_morningFoodVector());
        setFoodLists(tlMidDay, tracker.get_midDayFoodVector());
        setFoodLists(tlEvening, tracker.get_eveningFoodVector());

        //setMorningFootList();
    }

    private void initializeViews()
    {
        tlMorning = findViewById(R.id.BreakfastTable);
        tlMidDay = findViewById(R.id.LunchTable);
        tlEvening = findViewById(R.id.DinnerTable);
        tvCaloricTracker = findViewById(R.id.CaloricTracker);

        bAddFoodMorning = findViewById(R.id.button_AddFoodMorning);
        bAddFoodMidDay = findViewById(R.id.button_AddFoodMidDay);
        bAddFoodEvening = findViewById(R.id.button_AddFoodEvening);

        morningCaloricTotal = findViewById(R.id.label_morningCaloricTotal);
        midDayCaloricTotal = findViewById(R.id.label_midDayCaloricTotal);
        eveningCaloricTotal = findViewById(R.id.label_eveningCaloricTotal);

        bPrevDay = findViewById(R.id.buttonPrevDay);
        bCurrDay = findViewById(R.id.buttonCurrentDay);
        bNextDay = findViewById(R.id.buttonNextDay);
    }

    private void initializeOnClickListeners()
    {
        bAddFoodMorning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_DailyTrackerMainMenu.this, Activity_AddFoodMainMenu.class);
                intent.putExtra("LogDay", dateToday.toString());
                intent.putExtra("LogMealType", "Morning");
                intent.putExtra("LogDietType", Integer.toString(userProfile.getSelectedDiet()));
                startActivity(intent);
                //addMorningFoodToTable();
            }
        });

        bAddFoodMidDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_DailyTrackerMainMenu.this, Activity_AddFoodMainMenu.class);
                intent.putExtra("LogDay",dateToday.toString());
                intent.putExtra("LogMealType", "Noon");
                intent.putExtra("LogDietType", Integer.toString(userProfile.getSelectedDiet()));
                startActivity(intent);
            }
        });

        bAddFoodEvening.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_DailyTrackerMainMenu.this, Activity_AddFoodMainMenu.class);
                intent.putExtra("LogDay",dateToday.toString());
                intent.putExtra("LogMealType", "Night");
                intent.putExtra("LogDietType", Integer.toString(userProfile.getSelectedDiet()));
                startActivity(intent);
            }
        });
    }

    private void populateViews()
    {
        tvCaloricTracker.setText("Daily Caloric Total: "+ String.valueOf(tracker.get_dailyCaloricTotal()));
        morningCaloricTotal.setText("Total: " + tracker.get_morningCaloricTotal());
        midDayCaloricTotal.setText("Total: " + tracker.get_midDayCaloricTotal());
        eveningCaloricTotal.setText("Total: " + tracker.get_eveningCaloricTotal());

        //bCurrDay.setText(dateToday.toString());
    }

    private void setFoodLists(TableLayout tableLayout, Vector<Model_FoodItem> foodList)
    {
        for (Model_FoodItem foodItem: foodList)
        {
            TableRow tableRow = new TableRow(this);
            TextView foodName = new TextView(this);
            TextView calories= new TextView(this);
            TextView carb = new TextView(this);
            TextView fat = new TextView(this);
            TextView protein = new TextView(this);
            TextView sugar = new TextView(this);

            foodName.setText(foodItem.get_foodName() + "  " + foodItem.get_numberOfServings() + " " + foodItem.get_servingType());
            calories.setText(String.valueOf(foodItem.get_totalCalories()));
            carb.setText(String.valueOf(foodItem.get_carb()));
            fat.setText(String.valueOf(foodItem.get_fat()));
            protein.setText(String.valueOf(foodItem.get_protein()));
            sugar.setText(String.valueOf(foodItem.get_sugar()));

            foodName.setPadding(50,0,0,0);
            foodName.setTextColor(getResources().getColor(R.color.colorWhite));
            calories.setTextColor(getResources().getColor(R.color.colorWhite));
            carb.setTextColor(getResources().getColor(R.color.colorWhite));
            fat.setTextColor(getResources().getColor(R.color.colorWhite));
            protein.setTextColor(getResources().getColor(R.color.colorWhite));
            sugar.setTextColor(getResources().getColor(R.color.colorWhite));

            foodName.setGravity(Gravity.LEFT);
            calories.setGravity(Gravity.CENTER);
            carb.setGravity(Gravity.CENTER);
            fat.setGravity(Gravity.CENTER);
            protein.setGravity(Gravity.CENTER);
            sugar.setGravity(Gravity.CENTER);

            foodName.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            calories.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            carb.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            fat.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            protein.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            sugar.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
            tableRow.addView(foodName);
            tableRow.addView(calories);
            tableRow.addView(carb);
            tableRow.addView(fat);
            tableRow.addView(protein);
            tableRow.addView(sugar);
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    private void addMorningFoodToTable()
    {
        TableRow tableRow = new TableRow(this);
        TextView textView1 = new TextView(this);
        TextView textView2 = new TextView(this);
        TextView textView3 = new TextView(this);
        TextView textView4 = new TextView(this);
        TextView textView5 = new TextView(this);
        TextView textView6 = new TextView(this);

        textView1.setText("What");
        textView2.setText("150");
        textView3.setText("20");
        textView4.setText("33");
        textView5.setText("50");
        textView6.setText("0");

        textView1.setPadding(50,0,0,0);
        textView1.setTextColor(getResources().getColor(R.color.colorWhite));
        textView2.setTextColor(getResources().getColor(R.color.colorWhite));
        textView3.setTextColor(getResources().getColor(R.color.colorWhite));
        textView4.setTextColor(getResources().getColor(R.color.colorWhite));
        textView5.setTextColor(getResources().getColor(R.color.colorWhite));
        textView6.setTextColor(getResources().getColor(R.color.colorWhite));

        textView1.setGravity(Gravity.LEFT);
        textView2.setGravity(Gravity.CENTER);
        textView3.setGravity(Gravity.CENTER);
        textView4.setGravity(Gravity.CENTER);
        textView5.setGravity(Gravity.CENTER);
        textView6.setGravity(Gravity.CENTER);

        textView1.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        textView2.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        textView3.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        textView4.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        textView5.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        textView6.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
        tableRow.addView(textView1);
        tableRow.addView(textView2);
        tableRow.addView(textView3);
        tableRow.addView(textView4);
        tableRow.addView(textView5);
        tableRow.addView(textView6);
        tlMorning.addView(tableRow, new TableLayout.LayoutParams(TableLayout.LayoutParams.FILL_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
    }

    public void setFoodTracker()
    {
        tracker = new Model_DailyTracker(dateToday, userProfile.getDailyCaloricTarget(), morningFoodList,
                midDayFoodList, eveningFoodList, this);
    }

    //TEST METHOD
    public void setFoodLists()
    {
        Model_FoodItem chickenBreast = new Model_FoodItem("Chicken", "Ounces", 3, 250, dateToday,
                0,5,42,555,0);
        Model_FoodItem porkChop = new Model_FoodItem("Pork Chop", "Ounces", 5, 453, dateToday,
                0,10,40,220,0);
        Model_FoodItem Noodles = new Model_FoodItem("Noodles", "Ounces", 4, 550, dateToday,
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
