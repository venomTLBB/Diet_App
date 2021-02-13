package com.example.restrictive_diet.Profile_Package;

import android.content.Context;

import com.example.restrictive_diet.DatabaseHelper;

public class Model_Profile
{
    private String _name;
    private int _age;
    private String _gender;
    private int _heightFeet;
    private int _heightInches;

    private int _startWeight;
    private int _currWeight;
    private int _targetWeight;
    private int _iweeklyGoal;
    private int _iweeklyActivity;
    private int _selectedDiet;

    private int _basicMetobolicRate;
    private int _dailyCaloricTarget;
    private int _dailyCaloricMaintenance;

    private Context _context;

    //private DatabaseHelper _helper;

    public Model_Profile(String name, int age, String gender, int heightFeet, int heightInches, int sWeight, int currWeight, int targetWeight,
                         int weeklyGoal, int weeklyActivity, int selectedDiet, Context context)
    {
        _name = name;
        _age = age;
        _gender = gender;
        //_height = height;
        _heightFeet = heightFeet;
        _heightInches = heightInches;
        _startWeight = sWeight;
        _currWeight = currWeight;
        _targetWeight = targetWeight;
        _iweeklyGoal = weeklyGoal;
        _iweeklyActivity = weeklyActivity;
        _selectedDiet = selectedDiet;
        //_helper = new DatabaseHelper(context);
        _context = context;

        _basicMetobolicRate = bmrMath();
        _dailyCaloricMaintenance = dcmMath();
        _dailyCaloricTarget = dctMath();
    }

    public String getName()
    {
        return this._name;
    }

    public void setName(String name)
    {
        this._name = name;
        updateProfile(_context);
    }

    public int getAge()
    {
        return this._age;
    }

    public void setAge(int age)
    {
        this._age = age;
        updateCaloricData();
        updateProfile(_context);
    }

    public String getGender()
    {
        return this._gender;
    }

    public void setGender(String gender)
    {
        this._gender = gender;
        updateCaloricData();
        updateProfile(_context);
    }

    public int getHeightFeet()
    {
        return this._heightFeet;
    }

    public void setHeightFeet(int heightFeet)
    {
        this._heightFeet = heightFeet;
        updateCaloricData();
        updateProfile(_context);
    }

    public int getHeightInches() { return this._heightInches; }

    public void setHeightInches(int heightInches)
    {
        this._heightInches = heightInches;
        updateCaloricData();
        updateProfile(_context);
    }

    public String getHeightString() { return this._heightFeet + "'" + this._heightInches + "\""; }

    public int getStartWeight()
    {
        return this._startWeight;
    }

    public void setStartWeight(int startWeight)
    {
        this._startWeight = startWeight;
        updateProfile(_context);
    }

    public int getCurrWeight()
    {
        return this._currWeight;
    }

    public void setCurrWeight(int currWeight)
    {
        this._currWeight = currWeight;
        updateCaloricData();
        updateProfile(_context);
    }

    public int getTargetWeight()
    {
        return this._targetWeight;
    }

    public void setTargetWeight(int targetWeight)
    {
        this._targetWeight = targetWeight;
        updateProfile(_context);
    }

    public int getWeeklyGoal()
    {
        return this._iweeklyGoal;
    }

    public void setWeeklyGoal(int weeklyGoal)
    {
        this._iweeklyGoal = weeklyGoal;
        updateCaloricData();
        updateProfile(_context);
    }

    public int getWeeklyActivity()
    {
        return this._iweeklyActivity;
    }

    public void setWeeklyActivity(int weeklyActivity)
    {
        this._iweeklyActivity = weeklyActivity;
        updateCaloricData();
        updateProfile(_context);
    }

    public int getSelectedDiet()
    {
        return this._selectedDiet;
    }

    public void setSelectedDiet(int selectedDiet)
    {
        this._selectedDiet = selectedDiet;
        updateProfile(_context);
    }

    public int getBasicMetabolicRate() {return this._basicMetobolicRate; }

    public void setBasicMetabolicRate(int bmr)
    {
        this._basicMetobolicRate = bmr;
        updateProfile(_context);
    }

    public int getDailyCaloricTarget() {return this._dailyCaloricTarget; }

    public void setDailyCaloricTarget(int dct)
    {
        this._dailyCaloricTarget = dct;
        updateProfile(_context);
    }

    public int getDailyCaloricMaintenance()
    {
        return this._dailyCaloricMaintenance;
    }

    public void setDailyCaloricMaintenance(int dcm)
    {
        this._dailyCaloricMaintenance = dcm;
        updateProfile(_context);
    }

    //HELPER METHODS

    private int convertedHeightInches()
    {
        return getHeightFeet()*12 + getHeightInches();
    }

    private int bmrMath()
    {
        double test;
        if(getGender() == "Male")
        {
            //Male BMR
            test = (4.536*getCurrWeight()) + (15.88*convertedHeightInches()) - (5*getAge()) + 5;
        }
        else
        {
            //Women BMR
            test = (4.536*getCurrWeight()) + (15.88*convertedHeightInches()) - (5*getAge()) - 161;
        }
        return (int)test;
    }

    private int dcmMath()
    {
        double activityMod, dcm;
        switch (getWeeklyActivity())
        {
            case 0:
                activityMod = 1.2;
                break;
            case 1:
                activityMod = 1.375;
                break;
            case 2:
                activityMod = 1.55;
                break;
            case 3:
                activityMod = 1.725;
                break;
            case 4:
                activityMod = 1.9;
                break;
            default:
                activityMod = 1.2;
                break;
        }

        dcm = getBasicMetabolicRate() * activityMod;
        return (int)dcm;
    }

    private int dctMath()
    {
        int weeklyGoalMod;

        switch(getWeeklyGoal())
        {
            case 0:
                weeklyGoalMod = 1000;
                break;
            case 1:
                weeklyGoalMod = 500;
                break;
            case 2:
                weeklyGoalMod = 0;
                break;
            case 3:
                weeklyGoalMod = -500;
                break;
            case 4:
                weeklyGoalMod = -1000;
                break;
            default:
                weeklyGoalMod = 0;
                break;
        }

        return getDailyCaloricMaintenance() + weeklyGoalMod;
    }

    private void updateCaloricData()
    {
        setBasicMetabolicRate(bmrMath());
        setDailyCaloricMaintenance(dcmMath());
        setDailyCaloricTarget(dctMath());
    }

    // Helper
    public boolean isEmpty(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        return helper.profileTableIsEmpty();
    } // Checks if profile table is empty.
    public void addProfile(Context context) {
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.addProfile(this);
    } // Adds current Profile as entry to table.
    private void updateProfile(Context context)
    {
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.updateProfile(this);
    } // Updates the profile based on current object data.
}
