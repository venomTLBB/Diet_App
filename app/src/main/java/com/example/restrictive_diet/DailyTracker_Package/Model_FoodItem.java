package com.example.restrictive_diet.DailyTracker_Package;

import android.content.Context;

import com.example.restrictive_diet.Food_Package.Model_FoodDefinition;

import java.time.LocalDate;


public class Model_FoodItem
{
    private String _foodName;
    private String _servingType;
    private int _numberOfServings;
    private double _totalCalories;
    private LocalDate _date;
    private double _carb;
    private double _fat;
    private double _protein;
    private double _sodium;
    private double _sugar;

    public Model_FoodItem(String _foodName, String _servingType, int _numberOfServings, double _totalCalories, LocalDate _date,
                          double _carb, double _fat, double _protein, double _sodium, double _sugar)
    {
        this._foodName = _foodName;
        this._servingType = _servingType;
        this._numberOfServings = _numberOfServings;
        this._totalCalories = _totalCalories;
        this._date = _date;
        this._carb = _carb;
        this._fat = _fat;
        this._protein = _protein;
        this._sodium = _sodium;
        this._sugar = _sugar;
    }
    /*
    public Model_FoodItem(String foodName, int numberOfServings, String servingType, Date date, Context context)
    {
        Model_FoodDefinition temp = new Model_FoodDefinition(foodName, context);
        this._date = date;
        this._foodName = temp.getFoodName();
        this._servingType = servingType;
        this._numberOfServings = numberOfServings;
        this._totalCalories = numberOfServings * temp.getCalories();
        this._carb = numberOfServings * temp.getCarbs();
        this._fat = numberOfServings * temp.getFat();
        this._protein = numberOfServings * temp.getProtein();
        this._sodium = numberOfServings * temp.getSodium();
        this._sugar = numberOfServings * temp.getSugar();
    }
    */
    public String get_foodName() {
        return _foodName;
    }

    public void set_foodName(String _foodName) {
        this._foodName = _foodName;
    }

    public String get_servingType() {
        return _servingType;
    }

    public void set_servingType(String _servingType) {
        this._servingType = _servingType;
    }


    public int get_numberOfServings() {
        return _numberOfServings;
    }

    public void set_numberOfServings(int _numberOfServings) {
        this._numberOfServings = _numberOfServings;
    }

    public LocalDate get_date() {
        return _date;
    }

    public void set_date(LocalDate _date) {
        this._date = _date;
    }

    public double get_totalCalories() {
        return _totalCalories;
    }

    public void set_totalCalories(double _totalCalories) {
        this._totalCalories = _totalCalories;
    }

    public double get_carb() {
        return _carb;
    }

    public void set_carb(int _carb) {
        this._carb = _carb;
    }

    public double get_fat() {
        return _fat;
    }

    public void set_fat(int _fat) {
        this._fat = _fat;
    }

    public double get_protein() {
        return _protein;
    }

    public void set_protein(int _protein) {
        this._protein = _protein;
    }

    public double get_sodium() {
        return _sodium;
    }

    public void set_sodium(int _sodium) {
        this._sodium = _sodium;
    }

    public double get_sugar() {
        return _sugar;
    }

    public void set_sugar(int _sugar) {
        this._sugar = _sugar;
    }

}
