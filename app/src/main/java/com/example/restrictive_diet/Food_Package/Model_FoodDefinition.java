package com.example.restrictive_diet.Food_Package;

import android.content.Context;

import com.example.restrictive_diet.DatabaseHelper;

import java.util.HashMap;
import java.util.Vector;

public class Model_FoodDefinition {
    // ***MEMBERS***

    // VALUES
    private String _foodName;
    private double _calories;
    private double _carb;
    private double _fat;
    private double _protein;
    private double _sodium;
    private double _sugar;
    private int _isDefault;
    private int _isHidden;
    private Vector<Integer> _offLimitsToDiet; // An array of all diets that forbid this item.
    private String _foodType;

    public static final String MEAT = "MEAT";
    public static final String FISH = "FISH";
    public static final String DAIRY = "DAIRY";
    public static final String FRUIT = "FRUIT";
    public static final String VEGETABLE = "VEGETABLE";
    public static final String GRAIN = "GRAIN";
    public static final String OIL = "OIL";

    public static final double GRAMTOOUNCE = 0.035274;
    public static final double GRAMTOTBSP = 0.06763;
    public static final double GRAMTOCUP = 0.0046;

    public static enum DIET
    {

    }

    private HashMap<String, Double> _servingTypeMap;

    // SQL HELPER
    private DatabaseHelper _helper;

    // ***FUNCTIONS***

    // CONSTRUCTORS
    // Load food from table by string.
    /*
    public Model_FoodDefinition(String foodName, Context context)
    {
        _helper = new DatabaseHelper(context);

        _foodName = foodName;
        _helper.getFoodDefinition(this);
    }
    */
    // Load food from table by ID.
    /*
    public Model_FoodDefinition(int id, Context context)
    {
        _helper = new DatabaseHelper(context);

        // TO-DO
    }
    */
    // Create new food from inputs.


    //CONSTRUCTOR FOR TESTING
    public Model_FoodDefinition(String foodName, double calories, double carb, double fat, double protein, double sodium, double sugar,
                                int isDefault, int isHidden, String foodType, Vector<Integer> offLimitsToDiet)
    {
        _foodName = foodName;
        _calories = calories;
        _carb = carb;
        _fat = fat;
        _protein = protein;
        _sodium = sodium;
        _sugar = sugar;

        _isDefault = isDefault;
        _isHidden = isHidden;

        _foodType = foodType;

        _offLimitsToDiet = offLimitsToDiet;
        _servingTypeMap = new HashMap<String, Double>();
    }

    //TODO Add offLimitsDiet vector to constructor and implement db support
    public Model_FoodDefinition(String foodName, double calories, double carb, double fat, double protein,
                                double sodium, double sugar, int isDefault, int isHidden, String foodType,
                                Vector<Integer> offLimitsToDiet, Context context)
    {
        _foodName = foodName;
        _calories = calories;
        _carb = carb;
        _fat = fat;
        _protein = protein;
        _sodium = sodium;
        _sugar = sugar;

        _isDefault = isDefault;
        _isHidden = isHidden;

        _foodType = foodType;

        _offLimitsToDiet = offLimitsToDiet;
        _servingTypeMap = new HashMap<String, Double>();

        _helper = new DatabaseHelper(context);
    }
    // Create new food from string array.
    public Model_FoodDefinition(String[] strings, Context context)
    {
        _foodName = strings[0];
        _calories = Double.parseDouble(strings[1]);
        _carb = Double.parseDouble(strings[2]);
        _fat = Double.parseDouble(strings[3]);
        _protein = Double.parseDouble(strings[4]);
        _sodium = Double.parseDouble(strings[5]);
        _sugar = Double.parseDouble(strings[6]);
        _isDefault = Integer.parseInt(strings[7]);
        _isHidden = Integer.parseInt(strings[8]);
        _foodType = strings[9];

        if(strings.length == 11) {
            String offLimitsStr = strings[10];
            Vector<Integer> offLimitsToDiet = new Vector<Integer>();
            for (int i = 0; i < offLimitsStr.length(); i++) {
                char temp = offLimitsStr.charAt(i);
                offLimitsToDiet.add(Character.getNumericValue(temp));
                i++;
            }
            _offLimitsToDiet = offLimitsToDiet;

            _servingTypeMap = new HashMap<String, Double>();

            _helper = new DatabaseHelper(context);
        }
    }
    /*
    // Create new food from name.
    public Model_FoodDefinition(String foodName, Context context)
    {
        _helper = new DatabaseHelper(context);
        _helper.getFoodDefinition(this, _helper.getFoodIDFromString("apple"));
    }
    */

    // Getters
    public String getFoodName() { return _foodName; }
    public double getCalories() { return _calories; }
    public double getCarbs() { return _carb; }
    public double getFat() { return _fat; }
    public double getProtein() { return _protein; }
    public double getSodium() { return _sodium; }
    public double getSugar() { return _sugar; }
    public int getDefault() { return _isDefault; }
    public int getHidden() { return _isHidden; }
    public String getType() { return _foodType; }
    public Vector<Integer> getOffLimitsToDiet() { return _offLimitsToDiet; }

    // Setters
    public void setFoodName(String foodName)
    {
        _foodName = foodName;
    }
    public void setCalories(double caloriesPerGram)
    {
        _calories = caloriesPerGram;
    }
    public void setCarbs(double carbs)
    {
        _carb = carbs;
    }
    public void setFat(double fat)
    {
        _fat = fat;
    }
    public void setProtein(double protein)
    {
        _protein = protein;
    }
    public void setSodium(double sodium)
    {
        _sodium = sodium;
    }
    public void setSugar(double sugar)
    {
        _sugar = sugar;
    }
    public void setDefault(int vdefault) { _isDefault = vdefault; }
    public void setHidden(int hidden) { _isHidden = hidden; }
    public void setType(String type) { _foodType = type; }
    public void setOffLimitsToDiet(Vector<Integer> offLimitsToDiet) { _offLimitsToDiet = offLimitsToDiet; }

    // Other
    public void addAsNewFood() { _helper.addNewFood(this); } ;

    // Helper
    //public void updateFood(Model_FoodDefinition food) { _helper.updateFood(food); }
}
