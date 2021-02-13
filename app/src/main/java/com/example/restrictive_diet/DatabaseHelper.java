package com.example.restrictive_diet;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.restrictive_diet.DailyTracker_Package.Model_DailyTracker;
import com.example.restrictive_diet.DailyTracker_Package.Model_FoodItem;
import com.example.restrictive_diet.Food_Package.Model_FoodDefinition;
import com.example.restrictive_diet.Profile_Package.Model_Profile;
import com.example.restrictive_diet.Recipes_Package.Model_Recipe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Clock;
import java.time.LocalDate;
import java.util.Vector;

public class DatabaseHelper extends SQLiteOpenHelper
{
    // ********MEMBERS********

    // Basic
    private final static int _version = 1;
    private final static String _databaseName = "Database";

    // Tables
    private final String _tableProfile = "Profile";
    private final String _tableFoodDictionary = "FoodDictionary";
    private final String _tableDailyTracker = "DailyTracker";
    private final String _tableRecipes = "Recipes";

    // ID Column
    private final String _id = "ID";

    // Profile Table
    private final String _username = "Name";
    private final String _age = "Age";
    private final String _gender = "Gender";
    private final String _heightFeet = "HeightFeet";
    private final String _heightInches = "HeightInches";
    private final String _startWeight = "StartWeight";
    private final String _currWeight = "CurrentWeight";
    private final String _targetWeight = "TargetWeight";
    private final String _iweeklyGoal = "WeeklyGoal";
    private final String _iweeklyActivity = "WeeklyActivity";
    private final String _selectedDiet = "SelectedDiet";
    private final String _basicMetabolicRate = "BasicMetabolicRate";
    private final String _dailyCaloricTarget = "DailyCaloricTarget";
    private final String _dailyCaloricMaintenance = "DailyCaloricMaintenance";

    // FoodDictionary Table
    private final String _foodName = "FoodName";
    private final String _calories = "Calories";
    private final String _carb = "Carbs";
    private final String _fat = "Fat";
    private final String _protein = "Protein";
    private final String _sodium = "Sodium";
    private final String _sugar = "Sugar";
    private final String _isDefault = "IsDefault";
    private final String _isHidden = "IsHidden";
    private final String _foodType = "FoodType";
    private final String _offLimitsTo = "OffLimitsTo";

    // Daily Tracker Table
    private final String _timeOfDay = "TimeOfDay"; // Morning / Midday / Evening
    private final String _date = "Date";
    //private final String _grandTotalCalories = "GrandTotalCalories";
    private final String _dailyTotalCalories = "DailyTotalCalories";
    private final String _morningTotalCalories = "MorningTotalCalories";
    private final String _middayTotalCalories = "MiddayTotalCalories";
    private final String _eveningTotalCalories = "EveningTotalCalories";
    private final String _consumedCalories = "TotalCalories";
    private final String _consumedCarbs = "TotalCarbs";
    private final String _consumedFat = "TotalFat";
    private final String _consumedProtein = "TotalProtein";
    private final String _consumedSodium = "TotalSodium";
    private final String _consumedSugar = "TotalSugar";
    private final String _foodAmount = "FoodAmount";
    private final String _servingType = "ServingType";
    private final String _morning = "Morning";
    private final String _midday = "Midday";
    private final String _evening = "Evening";
    private final String _foodItem = "FoodItem";

    // Recipe Table
    private final String _recipeName = "RecipeName";
    private final String _ingredient = "Ingredient";
    private final String _totalCalories = "TotalCalories";
    private final String _totalCarbs = "TotalCarbs";
    private final String _totalFat = "TotalFat";
    private final String _totalProtein = "TotalProtein";
    private final String _totalSodium = "TotalSodium";
    private final String _totalSugar = "TotalSugar";
    private final String _ingredientCalories = "IngredientCalories";
    private final String _ingredientCarbs = "IngredientCarbs";
    private final String _ingredientFat = "IngredientFat";
    private final String _ingredientProtein = "IngredientProtein";
    private final String _ingredientSodium = "IngredientSodium";
    private final String _ingredientSugar = "IngredientSugar";
    private final String _numberOfServings = "NumberOfServings";
    private final String _recipeServingType = "ServingType";

    private final String CREATE_PROFILE_TABLE = "CREATE TABLE " + _tableProfile + "(" + _id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + _username + " TEXT," + _age + " INT," + _gender + " TEXT," + _heightFeet + " INT," + _heightInches + " INT," + _startWeight + " INT," + _currWeight + " INT,"
            + _targetWeight + " INT," + _iweeklyGoal + " INT," + _iweeklyActivity + " INT," + _selectedDiet + " INT," + _basicMetabolicRate + " INT," + _dailyCaloricTarget + " INT,"
            + _dailyCaloricMaintenance + " INT"
            + ")";

    private final String CREATE_FOODDEFINITION_TABLE = "CREATE TABLE " + _tableFoodDictionary + "(" + _id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + _foodName + " TEXT," + _calories + " DOUBLE," + _carb + " DOUBLE," + _fat + " DOUBLE," + _protein + " DOUBLE," + _sodium + " DOUBLE," + _sugar + " DOUBLE,"
            + _isDefault + " INT," + _isHidden + " INT," + _foodType + " TEXT," + _offLimitsTo + " TEXT"
            + ")";

    private final String CREATE_DAILYTRACKER_TABLE = "CREATE TABLE " + _tableDailyTracker + "(" + _id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + _timeOfDay + " TEXT," + _date + " DATE," + _consumedCalories + " DOUBLE," + _consumedCarbs + " DOUBLE," + _consumedFat + " DOUBLE,"
            + _consumedProtein + " DOUBLE," + _consumedSodium + " DOUBLE," + _consumedSugar + " DOUBLE," + _foodItem + " TEXT," + _foodAmount + " INT,"
            + _servingType + " TEXT," + _dailyTotalCalories + " INT," + _morningTotalCalories + " INT," + _middayTotalCalories + " INT,"
            + _eveningTotalCalories + " INT"
            + ")";

    private final String CREATE_RECIPE_TABLE = "CREATE TABLE " + _tableRecipes + "(" + _id + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + _recipeName + " TEXT," + _ingredient + " TEXT," + _totalCalories + " DOUBLE," + _totalCarbs + " DOUBLE," + _totalFat + " DOUBLE,"
            + _totalProtein + " DOUBLE," + _totalSodium + " DOUBLE," + _totalSugar + " DOUBLE," + _ingredientCalories + " DOUBLE,"
            + _ingredientCarbs + " DOUBLE," + _ingredientFat + " DOUBLE," + _ingredientProtein + " DOUBLE," + _ingredientSodium + " DOUBLE,"
            + _ingredientSugar + " DOUBLE," + _numberOfServings + " INT," + _recipeServingType + " TEXT"
            + ")";

    // ********METHODS********

    // CONSTRUCTOR
    public DatabaseHelper(Context ctx) {
        super(ctx, _databaseName, null, _version);
    }

    // OVERRIDES
    @Override
    public void onCreate(SQLiteDatabase database)
    {
        // Create all tables.
        database.execSQL(CREATE_PROFILE_TABLE);
        database.execSQL(CREATE_FOODDEFINITION_TABLE);
        database.execSQL(CREATE_DAILYTRACKER_TABLE);
        database.execSQL(CREATE_RECIPE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        // Drop all old tables and run onCreate().
        db.execSQL("DROP TABLE IF EXISTS " + _tableProfile);
        db.execSQL("DROP TABLE IF EXISTS " + _tableFoodDictionary);
        db.execSQL("DROP TABLE IF EXISTS " + _tableDailyTracker);
        db.execSQL("DROP TABLE IF EXISTS " + _tableRecipes);
        onCreate(db);
    }

    // PROFILE
    private ContentValues profileValues(Model_Profile prof)
    {
        ContentValues values = new ContentValues();
        values.put(_username, prof.getName());
        values.put(_age, prof.getAge());
        values.put(_gender, prof.getGender());
        values.put(_heightFeet, prof.getHeightFeet());
        values.put(_heightInches, prof.getHeightInches());
        values.put(_startWeight, prof.getStartWeight());
        values.put(_currWeight, prof.getCurrWeight());
        values.put(_targetWeight, prof.getTargetWeight());
        values.put(_iweeklyGoal, prof.getWeeklyGoal());
        values.put(_iweeklyActivity, prof.getWeeklyActivity());
        values.put(_selectedDiet, prof.getSelectedDiet());
        values.put(_basicMetabolicRate, prof.getBasicMetabolicRate());
        values.put(_dailyCaloricTarget, prof.getDailyCaloricTarget());
        values.put(_dailyCaloricMaintenance, prof.getDailyCaloricMaintenance());
        return values;
    }
    public void addProfile(Model_Profile prof)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = profileValues(prof);
        db.insert(_tableProfile, null, values);
    }
    public void updateProfile(Model_Profile prof)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = profileValues(prof);
        db.update(_tableProfile, values, _id + "=1", null);
    }
    /*
    public void getProfile(Model_Profile prof)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + _tableProfile, null);
        cursor.moveToFirst();
        prof.setName(cursor.getString(cursor.getColumnIndex(_username)));
        prof.setAge(cursor.getInt(cursor.getColumnIndex(_age)));
        prof.setGender(cursor.getString(cursor.getColumnIndex(_gender)));
        prof.setHeightFeet(cursor.getInt(cursor.getColumnIndex(_heightFeet)));
        prof.setHeightInches(cursor.getInt(cursor.getColumnIndex(_heightInches)));
        prof.setStartWeight(cursor.getInt(cursor.getColumnIndex(_startWeight)));
        prof.setCurrWeight(cursor.getInt(cursor.getColumnIndex(_currWeight)));
        prof.setTargetWeight(cursor.getInt(cursor.getColumnIndex(_targetWeight)));
        prof.setWeeklyGoal(cursor.getInt(cursor.getColumnIndex(_iweeklyGoal)));
        prof.setWeeklyActivity(cursor.getInt(cursor.getColumnIndex(_iweeklyActivity)));
        prof.setSelectedDiet(cursor.getInt(cursor.getColumnIndex(_selectedDiet)));
        prof.setBasicMetabolicRate(cursor.getInt(cursor.getColumnIndex(_basicMetabolicRate)));
        prof.setDailyCaloricTarget(cursor.getInt(cursor.getColumnIndex(_dailyCaloricTarget)));
        prof.setDailyCaloricMaintenance(cursor.getInt(cursor.getColumnIndex(_dailyCaloricMaintenance)));
    }
    */
    public boolean profileTableIsEmpty()
    {
        boolean isEmptyReturn = true;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + _tableProfile, null);
        if(cursor != null)
        {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            if(count != 0)
                isEmptyReturn = false;
        }
        return isEmptyReturn;
    }
    public Model_Profile getProfile(Context context) // If a getProfile does not exist, return default.  If it does, return getProfile.
    {
        Model_Profile prof = new Model_Profile("Default Jones", 18, "Male", 6,2,250,250,200,2,2,1,context);
        if(profileTableIsEmpty())
        {
            addProfile(prof);
            return prof;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + _tableProfile, null);

        cursor.moveToFirst();
        prof.setName(cursor.getString(cursor.getColumnIndex(_username)));
        prof.setAge(cursor.getInt(cursor.getColumnIndex(_age)));
        prof.setGender(cursor.getString(cursor.getColumnIndex(_gender)));
        prof.setHeightFeet(cursor.getInt(cursor.getColumnIndex(_heightFeet)));
        prof.setHeightInches(cursor.getInt(cursor.getColumnIndex(_heightInches)));
        prof.setStartWeight(cursor.getInt(cursor.getColumnIndex(_startWeight)));
        prof.setCurrWeight(cursor.getInt(cursor.getColumnIndex(_currWeight)));
        prof.setTargetWeight(cursor.getInt(cursor.getColumnIndex(_targetWeight)));
        prof.setWeeklyGoal(cursor.getInt(cursor.getColumnIndex(_iweeklyGoal)));
        prof.setWeeklyActivity(cursor.getInt(cursor.getColumnIndex(_iweeklyActivity)));
        prof.setSelectedDiet(cursor.getInt(cursor.getColumnIndex(_selectedDiet)));
        prof.setBasicMetabolicRate(cursor.getInt(cursor.getColumnIndex(_basicMetabolicRate)));
        prof.setDailyCaloricTarget(cursor.getInt(cursor.getColumnIndex(_dailyCaloricTarget)));
        prof.setDailyCaloricMaintenance(cursor.getInt(cursor.getColumnIndex(_dailyCaloricMaintenance)));

        return prof;
    }

    // FOODDICTIONARY
    private ContentValues foodDictionaryValues(Model_FoodDefinition food)
    {
        ContentValues values = new ContentValues();
        values.put(_foodName, food.getFoodName());
        values.put(_calories, food.getCalories());
        values.put(_carb, food.getCarbs());
        values.put(_fat, food.getFat());
        values.put(_protein, food.getProtein());
        values.put(_sodium, food.getSodium());
        values.put(_sugar, food.getSugar());
        values.put(_isDefault, food.getDefault());
        values.put(_isHidden, food.getHidden());
        values.put(_foodType, food.getType());

        if(food.getOffLimitsToDiet() != null) {
            String offLimitsTo = "";
            for (int i = 0; i < food.getOffLimitsToDiet().size(); i++) {
                offLimitsTo += food.getOffLimitsToDiet().elementAt(i);
                if (i < food.getOffLimitsToDiet().size() - 1)
                    offLimitsTo += '-';
            }
            values.put(_offLimitsTo, offLimitsTo);
        }

        return values;
    }
    public void addNewFood(Model_FoodDefinition food)
    {
        // Add food to table if it doesn't already exist.
        SQLiteDatabase db = this.getWritableDatabase();
        // Check if food exists on the table.
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + _tableFoodDictionary + " WHERE " + _foodName + "=\'" + food.getFoodName() + "\'", null);
        if(cursor != null)
        {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            // If it exists, return.
            if(count != 0)
            {
                return;
            }
        }
        // Otherwise, add it.
        ContentValues values = foodDictionaryValues(food);
        db.insert(_tableFoodDictionary, null, values);
    }
    public Model_FoodDefinition getFoodDefinition(String foodName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + _tableFoodDictionary + " WHERE " + _foodName + "=\'" + foodName + "\'", null);
        Model_FoodDefinition food = new Model_FoodDefinition(foodName, 0,0,0,0,0,0,0,0,"",null);
        cursor.moveToFirst();
        food.setCalories(cursor.getDouble(cursor.getColumnIndex(_calories)));
        food.setCarbs(cursor.getDouble(cursor.getColumnIndex(_carb)));
        food.setFat(cursor.getDouble(cursor.getColumnIndex(_fat)));
        food.setProtein(cursor.getDouble(cursor.getColumnIndex(_protein)));
        food.setSodium(cursor.getDouble(cursor.getColumnIndex(_sodium)));
        food.setSugar(cursor.getDouble(cursor.getColumnIndex(_sugar)));
        food.setDefault(cursor.getInt(cursor.getColumnIndex(_isDefault)));
        food.setHidden(cursor.getInt(cursor.getColumnIndex(_isHidden)));
        food.setType(cursor.getString(cursor.getColumnIndex(_foodType)));

        String offLimitsStr = cursor.getString(cursor.getColumnIndex(_offLimitsTo));
        Vector<Integer> offLimitsToDiet = new Vector<Integer>();
        if (offLimitsStr != null) {
            for (int i = 0; i < offLimitsStr.length(); i++) {
                char temp = offLimitsStr.charAt(i);
                offLimitsToDiet.add(Character.getNumericValue(temp));
                i++;
            }
        }
        food.setOffLimitsToDiet(offLimitsToDiet);

        return food;
    }
    public Model_FoodDefinition getFoodDefinition(int foodID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + _tableFoodDictionary + " WHERE " + _id + "=" + foodID, null);
        Model_FoodDefinition food = new Model_FoodDefinition("", 0,0,0,0,0,0,0,0,"",null);
        cursor.moveToFirst();
        food.setFoodName(cursor.getString(cursor.getColumnIndex(_foodName)));
        food.setCalories(cursor.getDouble(cursor.getColumnIndex(_calories)));
        food.setCarbs(cursor.getDouble(cursor.getColumnIndex(_carb)));
        food.setFat(cursor.getDouble(cursor.getColumnIndex(_fat)));
        food.setProtein(cursor.getDouble(cursor.getColumnIndex(_protein)));
        food.setSodium(cursor.getDouble(cursor.getColumnIndex(_sodium)));
        food.setSugar(cursor.getDouble(cursor.getColumnIndex(_sugar)));
        food.setDefault(cursor.getInt(cursor.getColumnIndex(_isDefault)));
        food.setHidden(cursor.getInt(cursor.getColumnIndex(_isHidden)));
        food.setType(cursor.getString(cursor.getColumnIndex(_foodType)));

        String offLimitsStr = cursor.getString(cursor.getColumnIndex(_offLimitsTo));
        Vector<Integer> offLimitsToDiet = new Vector<Integer>();
        if (offLimitsStr != null) {
            for (int i = 0; i < offLimitsStr.length(); i++) {
                char temp = offLimitsStr.charAt(i);
                offLimitsToDiet.add(Character.getNumericValue(temp));
                i++;
            }
        }
        food.setOffLimitsToDiet(offLimitsToDiet);

        return food;
    }
    public void populateFoodDictionary(Context context)
    {
        AssetManager assets = context.getAssets();
        String fileName = "foodDictionary.csv";
        BufferedReader reader = null;
        String currentLine = "";

        // Since this is only populating the defaults, wipe all the current defaults that are not hidden on the list for a fresh start.
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(_tableFoodDictionary, _isDefault + "=1 AND " + _isHidden + "=0", null);

        try
        {
            reader = new BufferedReader(new InputStreamReader(assets.open(fileName)));
            currentLine = reader.readLine(); // Skip the first line, which is just the column labels.
            while ((currentLine = reader.readLine()) != null)
            {
                Log.d("d", currentLine);

                String[] parsedLine = currentLine.split(",");

                Model_FoodDefinition food = new Model_FoodDefinition(parsedLine, context);

                addNewFood(food);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getFoodIDFromString(String foodName)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + _tableFoodDictionary + " WHERE " + _foodName + "=\'" + foodName + "\'", null);
        cursor.moveToFirst();
        int retID = cursor.getInt(cursor.getColumnIndex(_id));
        return retID;
    }
    /*
    public int getFoodDefinition(Model_FoodDefinition food, int ID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + _tableFoodDictionary + " WHERE " + _id + "=" + ID, null);
        cursor.moveToFirst();
        food.setCalories(cursor.getDouble(cursor.getColumnIndex(_calories)));
        food.setCarbs(cursor.getDouble(cursor.getColumnIndex(_carb)));
        food.setFat(cursor.getDouble(cursor.getColumnIndex(_fat)));
        food.setProtein(cursor.getDouble(cursor.getColumnIndex(_protein)));
        food.setSodium(cursor.getDouble(cursor.getColumnIndex(_sodium)));
        food.setSugar(cursor.getDouble(cursor.getColumnIndex(_sugar)));
        food.setDefault(cursor.getInt(cursor.getColumnIndex(_isDefault)));
        food.setHidden(cursor.getInt(cursor.getColumnIndex(_isHidden)));
        food.setType(cursor.getString(cursor.getColumnIndex(_foodType)));
        // TO-DO: Set vector.
    }
    */
    public Vector<Model_FoodDefinition> getFoodList() // Gets entire food list.
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Vector<Model_FoodDefinition> foodList = new Vector<Model_FoodDefinition>();

        // Get all foods
        Cursor cursor = db.rawQuery("SELECT * FROM " + _tableFoodDictionary + " WHERE " + _isHidden + "=0", null);
        // For every resulting food, add to vector.
        while(cursor.moveToNext())
        {
            Model_FoodDefinition food = getFoodDefinition(cursor.getString(cursor.getColumnIndex(_foodName)));
            foodList.add(food);
        }

        return foodList;
    }
    public Vector<Model_FoodDefinition> getFoodList(String foodType) // Gets food list by foodType.
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Vector<Model_FoodDefinition> foodList = new Vector<Model_FoodDefinition>();

        // Get all foods of the given type.
        Cursor cursor = db.rawQuery("SELECT * FROM " + _tableFoodDictionary + " WHERE " + _foodType + "=\'" + foodType + "\'" + " AND " + _isHidden + "=0", null);
        // For every resulting food, add to vector.
        while(cursor.moveToNext())
        {
            Model_FoodDefinition food = getFoodDefinition(cursor.getString(cursor.getColumnIndex(_foodName)));
            foodList.add(food);
        }

        return foodList;
    }
    public Vector<Model_FoodDefinition> getFoodList(int dietType) // Gets list by dietType.
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Vector<Model_FoodDefinition> foodList = new Vector<Model_FoodDefinition>();

        // Get all non-hidden foods.
        Cursor cursor = db.rawQuery("SELECT * FROM " + _tableFoodDictionary + " WHERE " + _isHidden + "=0", null);

        // For every resulting food, parse the forbidden diet string into Vector<Integer>.
        while(cursor.moveToNext())
        {
            Model_FoodDefinition food = getFoodDefinition(cursor.getString(cursor.getColumnIndex(_foodName)));

            String offLimitsStr = cursor.getString(cursor.getColumnIndex(_offLimitsTo));
            Vector<Integer> offLimitsToDiet = new Vector<Integer>();
            if (offLimitsStr != null) // If the string is not null, parse it into a vector of all forbidden diets.
            {
                for (int i = 0; i < offLimitsStr.length(); i++)
                {
                    char temp = offLimitsStr.charAt(i);
                    offLimitsToDiet.add(Character.getNumericValue(temp));
                    i++;
                }
                // If the dietType is NOT on the list of forbidden diets, add it.
                if(offLimitsToDiet.contains(dietType) != true)
                {
                    foodList.add(food);
                }
            }
            else // If offLimitsStr is null/nothing is forbidden, just add it to the vector.
            {
                foodList.add(food);
            }
        }

        return foodList;
    }
    public Vector<Model_FoodDefinition> getFoodList(String foodType, int dietType) // Gets food list by foodType, dietType.
    // Gets a list of all food of a given food type that are allowed by the diet.
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Vector<Model_FoodDefinition> foodList = new Vector<Model_FoodDefinition>();

        // Get all foods of the given type.
        Cursor cursor = db.rawQuery("SELECT * FROM " + _tableFoodDictionary + " WHERE " + _foodType + "=\'" + foodType + "\'" + " AND " + _isHidden + "=0", null);

        // For every resulting food, parse the forbidden diet string into Vector<Integer>.
        while(cursor.moveToNext())
        {
            Model_FoodDefinition food = getFoodDefinition(cursor.getString(cursor.getColumnIndex(_foodName)));

            String offLimitsStr = cursor.getString(cursor.getColumnIndex(_offLimitsTo));
            Vector<Integer> offLimitsToDiet = new Vector<Integer>();
            if (offLimitsStr != null) // If the string is not null, parse it into a vector of all forbidden diets.
            {
                for (int i = 0; i < offLimitsStr.length(); i++)
                {
                    char temp = offLimitsStr.charAt(i);
                    offLimitsToDiet.add(Character.getNumericValue(temp));
                    i++;
                }
                // If the dietType is NOT on the list of forbidden diets, add it.
                if(offLimitsToDiet.contains(dietType) != true)
                {
                    foodList.add(food);
                }
            }
            else // If offLimitsStr is null/nothing is forbidden, just add it to the vector.
            {
                foodList.add(food);
            }
        }

        return foodList;
    }
    public void deleteFoodDefinition(String foodName) // Deletes a particular food definition by string.
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Model_FoodDefinition food = getFoodDefinition(foodName);
        // If default, set to hidden.  If not default, delete entirely.
        if(food.getDefault() == 1) // Set hidden
        {
            db.delete(_tableFoodDictionary, _foodName + "=\'" + foodName + "\'", null);
            food.setHidden(1);
            addNewFood(food);
        }
        else // Delete
        {
            db.delete(_tableFoodDictionary, _foodName + "=\'" + foodName + "\'", null);
        }
    }
    public void deleteFoodDefinition(Model_FoodDefinition food) // Deletes a particular food definition by model.
    {
        SQLiteDatabase db = this.getWritableDatabase();
        // If default, set to hidden.  If not default, delete entirely.
        if(food.getDefault() == 1) // Set hidden
        {
            db.delete(_tableFoodDictionary, _foodName + "=\'" + food.getFoodName() + "\'", null);
            food.setHidden(1);
            addNewFood(food);
        }
        else // Delete
        {
            db.delete(_tableFoodDictionary, _foodName + "=\'" + food.getFoodName() + "\'", null);
        }
    }

    // DAILY TRACKER
    private ContentValues dailyTrackerValuesMorning(Model_DailyTracker tracker, String dateStr, int i)
    {
        ContentValues values = new ContentValues();
        values.put(_date, dateStr);
        values.put(_timeOfDay, _morning);
        values.put(_dailyTotalCalories, tracker.get_dailyCaloricTotal());
        values.put(_morningTotalCalories, tracker.get_morningCaloricTotal());
        values.put(_middayTotalCalories, tracker.get_midDayCaloricTotal());
        values.put(_eveningTotalCalories, tracker.get_eveningCaloricTotal());
        values.put(_consumedCalories, tracker.get_morningFoodVector().elementAt(i).get_totalCalories());
        values.put(_consumedCarbs, tracker.get_morningFoodVector().elementAt(i).get_carb());
        values.put(_consumedFat, tracker.get_morningFoodVector().elementAt(i).get_fat());
        values.put(_consumedProtein, tracker.get_morningFoodVector().elementAt(i).get_protein());
        values.put(_consumedSodium, tracker.get_morningFoodVector().elementAt(i).get_sodium());
        values.put(_consumedSugar, tracker.get_morningFoodVector().elementAt(i).get_sugar());

        // Food item ID and serving amount.
        values.put(_foodItem, tracker.get_morningFoodVector().elementAt(i).get_foodName());
        values.put(_foodAmount, tracker.get_morningFoodVector().elementAt(i).get_numberOfServings());
        values.put(_servingType, tracker.get_morningFoodVector().elementAt(i).get_servingType());

        return values;
    }
    private ContentValues dailyTrackerValuesMidday(Model_DailyTracker tracker, String dateStr, int i)
    {
        ContentValues values = new ContentValues();
        values.put(_date, dateStr);
        values.put(_timeOfDay, _midday);
        values.put(_dailyTotalCalories, tracker.get_dailyCaloricTotal());
        values.put(_morningTotalCalories, tracker.get_morningCaloricTotal());
        values.put(_middayTotalCalories, tracker.get_midDayCaloricTotal());
        values.put(_eveningTotalCalories, tracker.get_eveningCaloricTotal());
        values.put(_consumedCalories, tracker.get_midDayFoodVector().elementAt(i).get_totalCalories());
        values.put(_consumedCarbs, tracker.get_midDayFoodVector().elementAt(i).get_carb());
        values.put(_consumedFat, tracker.get_midDayFoodVector().elementAt(i).get_fat());
        values.put(_consumedProtein, tracker.get_midDayFoodVector().elementAt(i).get_protein());
        values.put(_consumedSodium, tracker.get_midDayFoodVector().elementAt(i).get_sodium());
        values.put(_consumedSugar, tracker.get_midDayFoodVector().elementAt(i).get_sugar());

        values.put(_foodItem, tracker.get_midDayFoodVector().elementAt(i).get_foodName());
        values.put(_foodAmount, tracker.get_midDayFoodVector().elementAt(i).get_numberOfServings());
        values.put(_servingType, tracker.get_midDayFoodVector().elementAt(i).get_servingType());

        return values;
    }
    private ContentValues dailyTrackerValuesEvening(Model_DailyTracker tracker, String dateStr, int i)
    {
        ContentValues values = new ContentValues();
        values.put(_date, dateStr);
        values.put(_timeOfDay, _evening);
        values.put(_dailyTotalCalories, tracker.get_dailyCaloricTotal());
        values.put(_morningTotalCalories, tracker.get_morningCaloricTotal());
        values.put(_middayTotalCalories, tracker.get_midDayCaloricTotal());
        values.put(_eveningTotalCalories, tracker.get_eveningCaloricTotal());
        values.put(_consumedCalories, tracker.get_eveningFoodVector().elementAt(i).get_totalCalories());
        values.put(_consumedCarbs, tracker.get_eveningFoodVector().elementAt(i).get_carb());
        values.put(_consumedFat, tracker.get_eveningFoodVector().elementAt(i).get_fat());
        values.put(_consumedProtein, tracker.get_eveningFoodVector().elementAt(i).get_protein());
        values.put(_consumedSodium, tracker.get_eveningFoodVector().elementAt(i).get_sodium());
        values.put(_consumedSugar, tracker.get_eveningFoodVector().elementAt(i).get_sugar());

        values.put(_foodItem, tracker.get_eveningFoodVector().elementAt(i).get_foodName());
        values.put(_foodAmount, tracker.get_eveningFoodVector().elementAt(i).get_numberOfServings());
        values.put(_servingType, tracker.get_eveningFoodVector().elementAt(i).get_servingType());

        return values;
    }
    public void updateTrackerTable(Model_DailyTracker tracker)
    {
        // Delete all rows matching the tracker date and add new ones based on the tracker's current values.
        SQLiteDatabase db = this.getWritableDatabase();
        String trackerDate = tracker.get_date().toString();
        // Delete rows with that date.
        //db.rawQuery("DELETE FROM " + _tableDailyTracker + " WHERE " + _date + "=\'" + trackerDate + "\'", null);
        db.delete(_tableDailyTracker, _date + "=\'" + trackerDate + "\'", null);
        // Add rows from tracker.

        // Morning
        if(tracker.get_morningFoodVector().isEmpty())
        {
            ContentValues morningVal = new ContentValues();
            morningVal.put(_date, trackerDate);
            morningVal.put(_timeOfDay, _morning);
            morningVal.put(_dailyTotalCalories, tracker.get_eveningCaloricTotal());
            morningVal.put(_morningTotalCalories, tracker.get_morningCaloricTotal());
            morningVal.put(_middayTotalCalories, tracker.get_midDayCaloricTotal());
            morningVal.put(_eveningTotalCalories, tracker.get_eveningCaloricTotal());
            db.insert(_tableDailyTracker, null, morningVal);
        }
        for(int i = 0; i < tracker.get_morningFoodVector().size(); i++)
        {
            ContentValues morningVal = dailyTrackerValuesMorning(tracker, trackerDate, i);
            db.insert(_tableDailyTracker, null, morningVal);
        }
        // Midday
        if(tracker.get_midDayFoodVector().isEmpty())
        {
            ContentValues midVal = new ContentValues();
            midVal.put(_date, trackerDate);
            midVal.put(_timeOfDay, _midday);
            midVal.put(_dailyTotalCalories, tracker.get_eveningCaloricTotal());
            midVal.put(_morningTotalCalories, tracker.get_morningCaloricTotal());
            midVal.put(_middayTotalCalories, tracker.get_midDayCaloricTotal());
            midVal.put(_eveningTotalCalories, tracker.get_eveningCaloricTotal());
            db.insert(_tableDailyTracker, null, midVal);
        }
        for(int i = 0; i < tracker.get_midDayFoodVector().size(); i++)
        {
            ContentValues midVal = dailyTrackerValuesMidday(tracker, trackerDate, i);
            db.insert(_tableDailyTracker, null, midVal);
        }
        // Evening
        if(tracker.get_eveningFoodVector().isEmpty())
        {
            ContentValues eveningVal = new ContentValues();
            eveningVal.put(_date, trackerDate);
            eveningVal.put(_timeOfDay, _evening);
            eveningVal.put(_dailyTotalCalories, tracker.get_eveningCaloricTotal());
            eveningVal.put(_morningTotalCalories, tracker.get_morningCaloricTotal());
            eveningVal.put(_middayTotalCalories, tracker.get_midDayCaloricTotal());
            eveningVal.put(_eveningTotalCalories, tracker.get_eveningCaloricTotal());
            db.insert(_tableDailyTracker, null, eveningVal);
        }
        for(int i = 0; i < tracker.get_eveningFoodVector().size(); i++)
        {
            ContentValues eveningVal = dailyTrackerValuesEvening(tracker, trackerDate, i);
            db.insert(_tableDailyTracker, null, eveningVal);
        }
    }
    public Model_DailyTracker getTracker(LocalDate date)
    {
        Model_DailyTracker tracker = new Model_DailyTracker(date, 0,0,0,0,0, new Vector<Model_FoodItem>(), new Vector<Model_FoodItem>(), new Vector<Model_FoodItem>());
        SQLiteDatabase db = this.getReadableDatabase();
        String trackerDate = date.toString();

        // Morning
        Cursor cursor1 = db.rawQuery("SELECT * FROM " + _tableDailyTracker + " WHERE " + _date + "=\'" + trackerDate + "\' AND " + _timeOfDay + "=\'" + _morning + "\'", null);
        //cursor1.moveToFirst();
        while(cursor1.moveToNext())
        {
            tracker.set_morningCaloricTotalNoUpdate(cursor1.getInt(cursor1.getColumnIndex(_morningTotalCalories)));

            // Putting this here because it has to go somewhere.
            tracker.set_dailyCaloricTotalNoUpdate(cursor1.getInt(cursor1.getColumnIndex(_dailyTotalCalories)));

            // Grab the ID and pull up food information from database.
            //int id = cursor1.getInt(cursor1.getColumnIndex(_id));

            if(cursor1.isNull(cursor1.getColumnIndex(_foodItem)))
                break;

            String foodName = cursor1.getString(cursor1.getColumnIndex(_foodItem));
            String servingType = cursor1.getString(cursor1.getColumnIndex(_servingType));
            int numOfServings = cursor1.getInt(cursor1.getColumnIndex(_foodAmount));
            double calories = cursor1.getDouble(cursor1.getColumnIndex(_consumedCalories));
            double carbs = cursor1.getDouble(cursor1.getColumnIndex(_consumedCarbs));
            double fat = cursor1.getDouble(cursor1.getColumnIndex(_consumedFat));
            double protein = cursor1.getDouble(cursor1.getColumnIndex(_consumedProtein));
            double sodium = cursor1.getDouble(cursor1.getColumnIndex(_consumedSodium));
            double sugar = cursor1.getDouble(cursor1.getColumnIndex(_consumedSugar));

            Model_FoodItem food = new Model_FoodItem(foodName, servingType, numOfServings, calories, date, carbs, fat, protein, sodium, sugar);
            tracker.addFoodToMorningNoUpdate(food);
        }

        // Midday
        Cursor cursor2 = db.rawQuery("SELECT * FROM " + _tableDailyTracker + " WHERE " + _date + "=\'" + trackerDate + "\' AND " + _timeOfDay + "=\'" + _midday + "\'", null);
        //cursor2.moveToFirst();
        while(cursor2.moveToNext())
        {
            tracker.set_midDayCaloricTotalNoUpdate(cursor2.getInt(cursor2.getColumnIndex(_middayTotalCalories)));
            //int id = cursor2.getInt(cursor2.getColumnIndex(_id));

            if(cursor2.isNull(cursor2.getColumnIndex(_foodItem)))
                break;

            String foodName = cursor2.getString(cursor2.getColumnIndex(_foodItem));
            String servingType = cursor2.getString(cursor2.getColumnIndex(_servingType));
            int numOfServings = cursor2.getInt(cursor2.getColumnIndex(_foodAmount));
            double calories = cursor2.getDouble(cursor2.getColumnIndex(_consumedCalories));
            double carbs = cursor2.getDouble(cursor2.getColumnIndex(_consumedCarbs));
            double fat = cursor2.getDouble(cursor2.getColumnIndex(_consumedFat));
            double protein = cursor2.getDouble(cursor2.getColumnIndex(_consumedProtein));
            double sodium = cursor2.getDouble(cursor2.getColumnIndex(_consumedSodium));
            double sugar = cursor2.getDouble(cursor2.getColumnIndex(_consumedSugar));

            Model_FoodItem food = new Model_FoodItem(foodName, servingType, numOfServings, calories, date, carbs, fat, protein, sodium, sugar);
            tracker.addFoodToMiddayNoUpdate(food);
        }

        // Evening
        Cursor cursor3 = db.rawQuery("SELECT * FROM " + _tableDailyTracker + " WHERE " + _date + "=\'" + trackerDate + "\' AND " + _timeOfDay + "=\'" + _evening + "\'", null);
        //cursor3.moveToFirst();
        while(cursor3.moveToNext())
        {
            tracker.set_eveningCaloricTotalNoUpdate(cursor3.getInt(cursor3.getColumnIndex(_eveningTotalCalories)));
            //int id = cursor3.getInt(cursor3.getColumnIndex(_id));

            if(cursor3.isNull(cursor3.getColumnIndex(_foodItem)))
                break;

            String foodName = cursor3.getString(cursor3.getColumnIndex(_foodItem));
            String servingType = cursor3.getString(cursor3.getColumnIndex(_servingType));
            int numOfServings = cursor3.getInt(cursor3.getColumnIndex(_foodAmount));
            double calories = cursor3.getDouble(cursor3.getColumnIndex(_consumedCalories));
            double carbs = cursor3.getDouble(cursor3.getColumnIndex(_consumedCarbs));
            double fat = cursor3.getDouble(cursor3.getColumnIndex(_consumedFat));
            double protein = cursor3.getDouble(cursor3.getColumnIndex(_consumedProtein));
            double sodium = cursor3.getDouble(cursor3.getColumnIndex(_consumedSodium));
            double sugar = cursor3.getDouble(cursor3.getColumnIndex(_consumedSugar));

            Model_FoodItem food = new Model_FoodItem(foodName, servingType, numOfServings, calories, date, carbs, fat, protein, sodium, sugar);
            tracker.addFoodToEveningNoUpdate(food);
        }

        return tracker;
    }
    public boolean trackerExistsForDate(LocalDate date)
    {
        boolean trackerExists = false;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + _tableDailyTracker + " WHERE " + _date + "=\'" + date + "\'", null);
        if(cursor != null)
        {
            cursor.moveToFirst();
            int count = cursor.getInt(0);
            if(count != 0)
                trackerExists = true;
        }
        return trackerExists;
    }
    public void populateTrackers(Context context) // Deletes all previous models to make way for the test ones.
    {
        AssetManager assets = context.getAssets();
        String fileName = "modelFoodTracker.csv";
        BufferedReader reader = null;
        String currentLine = "";

        // Delete all previous models.
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(_tableDailyTracker, null, null);

        try
        {
            reader = new BufferedReader(new InputStreamReader(assets.open(fileName)));
            currentLine = reader.readLine(); // Skip the first line, which is just the column labels.
            while ((currentLine = reader.readLine()) != null)
            {
                Log.d("d", currentLine);

                String[] parsedLine = currentLine.split(",");

                // Get values from line and add them to the database.
                String timeOfDay = parsedLine[0];
                String date = parsedLine[1];
                double dailyTotalCalories = Double.parseDouble(parsedLine[2]);
                double totalCalories = Double.parseDouble(parsedLine[3]);
                double totalCarbs = Double.parseDouble(parsedLine[4]);
                double totalFat = Double.parseDouble(parsedLine[5]);
                double totalProtein = Double.parseDouble(parsedLine[6]);
                double totalSodium = Double.parseDouble(parsedLine[7]);
                double totalSugar = Double.parseDouble(parsedLine[8]);
                int foodAmount = Integer.parseInt(parsedLine[9]);
                String servingType = parsedLine[10];
                String foodItem = parsedLine[11];

                // U/C Below:
                double morningTotalCalories = Double.parseDouble(parsedLine[12]);
                double middayTotalCalories = Double.parseDouble(parsedLine[13]);
                double eveningTotalCalories = Double.parseDouble(parsedLine[14]);

                ContentValues values = new ContentValues();
                values.put(_date, date);
                values.put(_timeOfDay, timeOfDay);
                values.put(_dailyTotalCalories, dailyTotalCalories);
                values.put(_morningTotalCalories, morningTotalCalories);
                values.put(_middayTotalCalories, middayTotalCalories);
                values.put(_eveningTotalCalories, eveningTotalCalories);
                values.put(_consumedCalories, totalCalories);
                values.put(_consumedCarbs, totalCarbs);
                values.put(_consumedFat, totalFat);
                values.put(_consumedProtein, totalProtein);
                values.put(_consumedSodium, totalSodium);
                values.put(_consumedSugar, totalSugar);

                // Food item ID and serving amount.
                values.put(_foodItem, foodItem);
                values.put(_foodAmount, foodAmount);
                values.put(_servingType, servingType);

                db.insert(_tableDailyTracker, null, values);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Recipes
    public ContentValues recipeValues(Model_Recipe recipe, int i)
    {
        ContentValues values = new ContentValues();
        values.put(_recipeName, recipe.getRecipeName());
        values.put(_totalCalories, recipe.getCalories());
        values.put(_totalCarbs, recipe.getCarbs());
        values.put(_totalFat, recipe.getFat());
        values.put(_totalProtein, recipe.getProtein());
        values.put(_totalSodium, recipe.getSodium());
        values.put(_totalSugar, recipe.getSugar());

        values.put(_ingredient, recipe.getIngredients().elementAt(i).get_foodName());
        values.put(_ingredientCalories, recipe.getIngredients().elementAt(i).get_totalCalories());
        values.put(_ingredientCarbs, recipe.getIngredients().elementAt(i).get_carb());
        values.put(_ingredientFat, recipe.getIngredients().elementAt(i).get_fat());
        values.put(_ingredientProtein, recipe.getIngredients().elementAt(i).get_protein());
        values.put(_ingredientSodium, recipe.getIngredients().elementAt(i).get_sodium());
        values.put(_ingredientSugar, recipe.getIngredients().elementAt(i).get_sugar());
        values.put(_recipeServingType, recipe.getIngredients().elementAt(i).get_servingType());
        values.put(_numberOfServings, recipe.getIngredients().elementAt(i).get_numberOfServings());

        return values;
    }
    public void updateRecipe(Model_Recipe recipe)
    {
        // Delete all rows matching the recipe name and add new ones based on the recipe's current values.
        SQLiteDatabase db = this.getWritableDatabase();
        //db.rawQuery("DELETE FROM " + _tableDailyTracker + " WHERE " + _date + "=\'" + trackerDate + "\'", null);
        db.delete(_tableRecipes, _recipeName + "=\'" + recipe.getRecipeName() + "\'", null);

        // Add rows from recipe ingredients.
        if(recipe.getIngredients().isEmpty())
        {
            ContentValues values = new ContentValues();
            values.put(_recipeName, recipe.getRecipeName());
            values.put(_totalCalories, recipe.getCalories());
            values.put(_totalCarbs, recipe.getCarbs());
            values.put(_totalFat, recipe.getFat());
            values.put(_totalProtein, recipe.getProtein());
            values.put(_totalSodium, recipe.getSodium());
            values.put(_totalSugar, recipe.getSugar());
            db.insert(_tableRecipes, null, values);
        }
        for(int i = 0; i < recipe.getIngredients().size(); i++)
        {
            ContentValues values = recipeValues(recipe, i);
            db.insert(_tableRecipes, null, values);
        }
    }
    public Model_Recipe getRecipe(String recipeName)
    {
        Model_Recipe recipe = new Model_Recipe("", new Vector<Model_FoodItem>(), 0,0,0,0,0,0);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + _tableRecipes + " WHERE " + _recipeName + "=\'" + recipeName + "\'", null);

        while(cursor.moveToNext())
        {
            recipe.setRecipeNameNoUpdate(cursor.getString(cursor.getColumnIndex(_recipeName)));
            recipe.setCaloriesNoUpdate(cursor.getDouble(cursor.getColumnIndex(_totalCalories)));
            recipe.setCarbsNoUpdate(cursor.getDouble(cursor.getColumnIndex(_totalCarbs)));
            recipe.setFatNoUpdate(cursor.getDouble(cursor.getColumnIndex(_totalFat)));
            recipe.setProteinNoUpdate(cursor.getDouble(cursor.getColumnIndex(_totalProtein)));
            recipe.setSodiumNoUpdate(cursor.getDouble(cursor.getColumnIndex(_totalSodium)));
            recipe.setSugarNoUpdate(cursor.getDouble(cursor.getColumnIndex(_totalSugar)));

            if(cursor.isNull(cursor.getColumnIndex(_ingredient)))
                break;

            Clock clock = Clock.systemUTC();
            LocalDate dateToday = LocalDate.now(clock);

            String foodName = cursor.getString(cursor.getColumnIndex(_ingredient));
            String servingType = cursor.getString(cursor.getColumnIndex(_recipeServingType));
            int numberOfServings = cursor.getInt(cursor.getColumnIndex(_numberOfServings));
            double calories = cursor.getDouble(cursor.getColumnIndex(_ingredientCalories));
            double carbs = cursor.getDouble(cursor.getColumnIndex(_ingredientCarbs));
            double fat = cursor.getDouble(cursor.getColumnIndex(_ingredientFat));
            double protein = cursor.getDouble(cursor.getColumnIndex(_ingredientProtein));
            double sodium = cursor.getDouble(cursor.getColumnIndex(_ingredientSodium));
            double sugar = cursor.getDouble(cursor.getColumnIndex(_ingredientSugar));
            Model_FoodItem food = new Model_FoodItem(foodName, servingType, numberOfServings, calories, dateToday, carbs, fat, protein, sodium, sugar); //getFoodDefinition(cursor.getInt(cursor.getColumnIndex(_ingredient)));
            recipe.addIngredientNoUpdate(food);
        }
        return recipe;
    }
    public Vector<Model_Recipe> getAllRecipes()
    {
        Vector<Model_Recipe> recipeList = new Vector<Model_Recipe>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + _tableRecipes, null);

        while(cursor.moveToNext())
        {
            Model_Recipe recipe = new Model_Recipe("", new Vector<Model_FoodItem>(), 0,0,0,0,0,0);
            recipe.setRecipeNameNoUpdate(cursor.getString(cursor.getColumnIndex(_recipeName)));
            recipe.setCaloriesNoUpdate(cursor.getDouble(cursor.getColumnIndex(_totalCalories)));
            recipe.setCarbsNoUpdate(cursor.getDouble(cursor.getColumnIndex(_totalCarbs)));
            recipe.setFatNoUpdate(cursor.getDouble(cursor.getColumnIndex(_totalFat)));
            recipe.setProteinNoUpdate(cursor.getDouble(cursor.getColumnIndex(_totalProtein)));
            recipe.setSodiumNoUpdate(cursor.getDouble(cursor.getColumnIndex(_totalSodium)));
            recipe.setSugarNoUpdate(cursor.getDouble(cursor.getColumnIndex(_totalSugar)));

            if(cursor.isNull(cursor.getColumnIndex(_ingredient)))
                break;

            Clock clock = Clock.systemUTC();
            LocalDate dateToday = LocalDate.now(clock);

            String foodName = cursor.getString(cursor.getColumnIndex(_ingredient));
            String servingType = cursor.getString(cursor.getColumnIndex(_recipeServingType));
            int numberOfServings = cursor.getInt(cursor.getColumnIndex(_numberOfServings));
            double calories = cursor.getDouble(cursor.getColumnIndex(_ingredientCalories));
            double carbs = cursor.getDouble(cursor.getColumnIndex(_ingredientCarbs));
            double fat = cursor.getDouble(cursor.getColumnIndex(_ingredientFat));
            double protein = cursor.getDouble(cursor.getColumnIndex(_ingredientProtein));
            double sodium = cursor.getDouble(cursor.getColumnIndex(_ingredientSodium));
            double sugar = cursor.getDouble(cursor.getColumnIndex(_ingredientSugar));
            Model_FoodItem food = new Model_FoodItem(foodName, servingType, numberOfServings, calories, dateToday, carbs, fat, protein, sodium, sugar); //getFoodDefinition(cursor.getInt(cursor.getColumnIndex(_ingredient)));
            recipe.addIngredientNoUpdate(food);

            recipeList.add(recipe);
        }

        return recipeList;
    }
}