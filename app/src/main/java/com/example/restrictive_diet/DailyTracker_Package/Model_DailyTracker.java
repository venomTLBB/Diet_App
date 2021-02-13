package com.example.restrictive_diet.DailyTracker_Package;

import android.content.Context;

import com.example.restrictive_diet.DatabaseHelper;

import java.time.LocalDate;
import java.util.Vector;

public class Model_DailyTracker
{
    private LocalDate _date;
    private int _caloricTarget;
    private int _dailyCaloricTotal;
    private int _morningCaloricTotal;
    private int _midDayCaloricTotal;
    private int _eveningCaloricTotal;

    private Vector<Model_FoodItem> _morningFoodVector;
    private Vector<Model_FoodItem> _midDayFoodVector;
    private Vector<Model_FoodItem> _eveningFoodVector;

    public Model_DailyTracker(LocalDate _date, int _caloricTarget, int _dailyCaloricTotal, int _morningCaloricTotal, int _midDayCaloricTotal, int _eveningCaloricTotal,
                              Vector<Model_FoodItem> _morningFoodVector, Vector<Model_FoodItem> _midDayFoodVector, Vector<Model_FoodItem> _eveningFoodVector)
    {
        this._date = _date;
        this._caloricTarget = _caloricTarget;
        this._dailyCaloricTotal = _dailyCaloricTotal;
        this._morningCaloricTotal = _morningCaloricTotal;
        this._midDayCaloricTotal = _midDayCaloricTotal;
        this._eveningCaloricTotal = _eveningCaloricTotal;
        this._morningFoodVector = _morningFoodVector;
        this._midDayFoodVector = _midDayFoodVector;
        this._eveningFoodVector = _eveningFoodVector;
    }

    public Model_DailyTracker(LocalDate _date, int _caloricTarget, Vector<Model_FoodItem> _morningFoodVector,
                              Vector<Model_FoodItem> _midDayFoodVector, Vector<Model_FoodItem> _eveningFoodVector, Context context)
    {
        this._date = _date;
        this._caloricTarget = _caloricTarget;
        this._morningFoodVector = _morningFoodVector;
        this._midDayFoodVector = _midDayFoodVector;
        this._eveningFoodVector = _eveningFoodVector;

        //Sum calories;
        this._dailyCaloricTotal = 0;
        updateMorningCaloricTotal(context);
        updateMidDayCaloricTotal(context);
        updateEveningCaloricTotal(context);
    }

    // For temp models.
    public void addFoodToMorningNoUpdate(Model_FoodItem food) { _morningFoodVector.add(food); }
    public void addFoodToMiddayNoUpdate(Model_FoodItem food)
    {
        _midDayFoodVector.add(food);
    }
    public void addFoodToEveningNoUpdate(Model_FoodItem food)
    {
        _eveningFoodVector.add(food);
    }
    public void set_morningCaloricTotalNoUpdate(int _morningCaloricTotal) { this._morningCaloricTotal = _morningCaloricTotal; }
    public void set_midDayCaloricTotalNoUpdate(int _midDayCaloricTotal) { this._midDayCaloricTotal = _midDayCaloricTotal; }
    public void set_eveningCaloricTotalNoUpdate(int _eveningCaloricTotal) { this._eveningCaloricTotal = _eveningCaloricTotal; }

    public void addFoodToMorning(Model_FoodItem food, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _morningFoodVector.add(food);
        helper.updateTrackerTable(this);
    }
    public void addFoodToMidday(Model_FoodItem food, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _midDayFoodVector.add(food);
        helper.updateTrackerTable(this);
    }
    public void addFoodToEvening(Model_FoodItem food, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _eveningFoodVector.add(food);
        helper.updateTrackerTable(this);
    }
    public void removeFoodFromMorning(int index, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _morningFoodVector.remove(index);
        helper.updateTrackerTable(this);
    }
    public void removeFoodFromMorning(Model_FoodItem food, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _morningFoodVector.remove(food);
        helper.updateTrackerTable(this);
    }
    public void removeFoodFromMidday(int index, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _midDayFoodVector.remove(index);
        helper.updateTrackerTable(this);
    }
    public void removeFoodFromMidday(Model_FoodItem food, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _midDayFoodVector.remove(food);
        helper.updateTrackerTable(this);
    }
    public void removeFoodFromEvening(int index, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _eveningFoodVector.remove(index);
        helper.updateTrackerTable(this);
    }
    public void removeFoodFromEvening(Model_FoodItem food, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        _eveningFoodVector.remove(food);
        helper.updateTrackerTable(this);
    }

    public LocalDate get_date() {
        return _date;
    }

    public void set_date(LocalDate _date, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        this._date = _date;
        helper.updateTrackerTable(this);
    }

    public int get_caloricTarget() {
        return _caloricTarget;
    }

    public void set_caloricTarget(int _caloricTarget, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        this._caloricTarget = _caloricTarget;
        helper.updateTrackerTable(this);
    }

    public int get_dailyCaloricTotal() {
        return _dailyCaloricTotal;
    }

    public void set_dailyCaloricTotal(int _dailyCaloricTotal, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        this._dailyCaloricTotal = _dailyCaloricTotal;
        helper.updateTrackerTable(this);
    }

    public int get_morningCaloricTotal() {
        return _morningCaloricTotal;
    }

    public void set_morningCaloricTotal(int morningCaloricTotal, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        this._morningCaloricTotal = morningCaloricTotal;
        helper.updateTrackerTable(this);

    }

    public int get_midDayCaloricTotal() {
        return _midDayCaloricTotal;
    }

    public void set_midDayCaloricTotal(int midDayCaloricTotal, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        this._midDayCaloricTotal = midDayCaloricTotal;
        helper.updateTrackerTable(this);
    }

    public int get_eveningCaloricTotal() {
        return _eveningCaloricTotal;
    }

    public void set_eveningCaloricTotal(int eveningCaloricTotal, Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        this._eveningCaloricTotal = eveningCaloricTotal;
        helper.updateTrackerTable(this);
    }

    public Vector<Model_FoodItem> get_morningFoodVector() {
        return _morningFoodVector;
    }

    public void set_morningFoodVector(Vector<Model_FoodItem> _morningFoodVector) {
        this._morningFoodVector = _morningFoodVector;
    }

    public Vector<Model_FoodItem> get_midDayFoodVector() {
        return _midDayFoodVector;
    }

    public void set_midDayFoodVector(Vector<Model_FoodItem> _midDayFoodVector) {
        this._midDayFoodVector = _midDayFoodVector;
    }

    public Vector<Model_FoodItem> get_eveningFoodVector() {
        return _eveningFoodVector;
    }

    public void set_eveningFoodVector(Vector<Model_FoodItem> _eveningFoodVector) {
        this._eveningFoodVector = _eveningFoodVector;
    }

    public void updateMorningCaloricTotal(Context context)
    {
        this._morningCaloricTotal = 0;
        for(Model_FoodItem foodItem : this._morningFoodVector)
        {
            this._morningCaloricTotal += foodItem.get_totalCalories();
        }
        updateDailyCaloricTotal(context);
    }

    public void updateMidDayCaloricTotal(Context context)
    {
        this._midDayCaloricTotal = 0;
        for(Model_FoodItem foodItem : this._midDayFoodVector)
        {
            this._midDayCaloricTotal += foodItem.get_totalCalories();
        }
        updateDailyCaloricTotal(context);
    }

    public void updateEveningCaloricTotal(Context context)
    {
        this._eveningCaloricTotal = 0;
        for(Model_FoodItem foodItem : this._eveningFoodVector)
        {
            this._eveningCaloricTotal += foodItem.get_totalCalories();
        }
        updateDailyCaloricTotal(context);
    }

    public void updateDailyCaloricTotal(Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        this._dailyCaloricTotal = this._morningCaloricTotal + this._midDayCaloricTotal + this._eveningCaloricTotal;
        helper.updateTrackerTable(this);
    }

    public void set_dailyCaloricTotalNoUpdate(int aDouble) {
        _dailyCaloricTotal = aDouble;
    }
}