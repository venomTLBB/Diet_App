package com.example.restrictive_diet.Recipes_Package;

import android.content.Context;

import com.example.restrictive_diet.DailyTracker_Package.Model_FoodItem;
import com.example.restrictive_diet.DatabaseHelper;

import java.util.Vector;

public class Model_Recipe {
    // ********MEMBERS********
    private String _recipeName;
    private Vector<Model_FoodItem> _ingredients;
    private double _totalCalories;
    private double _totalCarbs;
    private double _totalFat;
    private double _totalProtein;
    private double _totalSodium;
    private double _totalSugar;

    // ********FUNCTIONS********
    // CONSTRUCTORS
    public Model_Recipe(String recipeName, Vector<Model_FoodItem> ingredients, double totalCalories, double totalCarbs,
                        double totalFat, double totalProtein, double totalSodium, double totalSugar)
    {
        _recipeName = recipeName;
        _ingredients = ingredients;
        _totalCalories = totalCalories;
        _totalCarbs = totalCarbs;
        _totalFat = totalFat;
        _totalProtein = totalProtein;
        _totalSodium = totalSodium;
        _totalSugar = totalSugar;
        updateNutritionTotals();
    }

    // GETTERS
    public String getRecipeName() { return _recipeName; }
    public Vector<Model_FoodItem> getIngredients() { return _ingredients; }
    public double getCalories() { return _totalCalories; }
    public double getCarbs() { return _totalCarbs; }
    public double getFat() { return _totalFat; }
    public double getProtein() { return _totalProtein; }
    public double getSodium() { return _totalSodium; }
    public double getSugar() { return _totalSugar; }

    // SETTERS
    // No Update
    public void setRecipeNameNoUpdate(String recipeName) { _recipeName = recipeName; }
    public void setIngredientsNoUpdate(Vector<Model_FoodItem> ingredients) { _ingredients = ingredients; }
    public void setCaloriesNoUpdate(double totalCalories) { _totalCalories = totalCalories; }
    public void setCarbsNoUpdate(double totalCarbs) { _totalCarbs = totalCarbs; }
    public void setFatNoUpdate(double totalFat) { _totalFat = totalFat; }
    public void setProteinNoUpdate(double totalProtein) { _totalProtein = totalProtein; }
    public void setSodiumNoUpdate(double totalSodium) { _totalSodium = totalSodium; }
    public void setSugarNoUpdate(double totalSugar) { _totalSugar = totalSugar; }
    public void addIngredientNoUpdate(Model_FoodItem food) { _ingredients.add(food); }

    // Update
    public void setRecipeName(String recipeName, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _recipeName = recipeName;
        helper.updateRecipe(this);
    }
    public void setIngredients(Vector<Model_FoodItem> ingredients, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _ingredients = ingredients;
        updateNutritionTotals();
        helper.updateRecipe(this);
    }
    public void setCalories(double totalCalories, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _totalCalories = totalCalories;
        updateNutritionTotals();
        helper.updateRecipe(this);
    }
    public void setCarbs(double totalCarbs, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _totalCarbs = totalCarbs;
        updateNutritionTotals();
        helper.updateRecipe(this);
    }
    public void setFat(double totalFat, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _totalFat = totalFat;
        updateNutritionTotals();
        helper.updateRecipe(this);
    }
    public void setProtein(double totalProtein, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _totalProtein = totalProtein;
        updateNutritionTotals();
        helper.updateRecipe(this);
    }
    public void setSodium(double totalSodium, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _totalSodium = totalSodium;
        updateNutritionTotals();
        helper.updateRecipe(this);
    }
    public void setSugar(double totalSugar, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _totalSugar = totalSugar;
        updateNutritionTotals();
        helper.updateRecipe(this);
    }
    public void addIngredient(Model_FoodItem food, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _ingredients.add(food);
        updateNutritionTotals();
        helper.updateRecipe(this);
    }
    public void deleteIngredient(Model_FoodItem food, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _ingredients.remove(food);
        updateNutritionTotals();
        helper.updateRecipe(this);
    }

    // OTHER
    private void updateNutritionTotals()
    {
        double _totalCalories = 0;
        double _totalCarbs = 0;
        double _totalFat = 0;
        double _totalProtein = 0;
        double _totalSodium = 0;
        double _totalSugar = 0;
        // Loop through the ingredient vector to get totals..
        for(int i = 0; i < _ingredients.size(); i++)
        {
            _totalCalories += _ingredients.elementAt(i).get_totalCalories();
            _totalCarbs += _ingredients.elementAt(i).get_carb();
            _totalFat += _ingredients.elementAt(i).get_fat();
            _totalProtein += _ingredients.elementAt(i).get_protein();
            _totalSodium += _ingredients.elementAt(i).get_sodium();
            _totalSugar += _ingredients.elementAt(i).get_sugar();
        }
    }
}
