package com.example.restrictive_diet.DailyTracker_Package;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.restrictive_diet.Food_Package.Model_FoodDefinition;
import com.example.restrictive_diet.R;
import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Adapter_FoodListView extends ArrayAdapter<Model_FoodDefinition> implements Filterable
{

    private Activity _context;
    private ArrayList<Model_FoodDefinition> _originalFoodList;
    private ArrayList<Model_FoodDefinition> _displayedFoodList;
    private String _logDate, _logMealType;
    private double calories, carbs, fats, protiens, sodum, sugars;

    public Adapter_FoodListView(@NonNull Activity context, ArrayList<Model_FoodDefinition> foodList,
                                String logDate, String logMealType)
    {
        super(context, R.layout.listview_layout_food, foodList);

        this._context = context;
        this._originalFoodList = foodList;
        this._displayedFoodList = foodList;
        this._logDate = logDate;
        this._logMealType = logMealType;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View r = convertView;
        ViewHolder viewHolder = null;

        if(r == null)
        {
            LayoutInflater layoutInflater = _context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listview_layout_food, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) r.getTag();
        }

        Model_FoodDefinition foodDefinition = _displayedFoodList.get(position);

        calories = foodDefinition.getCalories() * 100;
        carbs = foodDefinition.getCarbs() * 100;
        fats = foodDefinition.getFat() * 100;
        protiens = foodDefinition.getProtein() * 100;
        sodum = foodDefinition.getSodium() * 100;
        sugars = foodDefinition.getSugar() * 100;

        viewHolder.tvFoodName.setText(foodDefinition.getFoodName());
        viewHolder.outputCalories.setText(String.valueOf((int)calories));
        viewHolder.outputCarbs.setText(String.valueOf((int)carbs));
        viewHolder.outputFats.setText(String.valueOf((int)fats));
        viewHolder.outputProtiens.setText(String.valueOf((int)protiens));
        viewHolder.outputSodium.setText(String.valueOf((int)sodum));
        viewHolder.outputSugar.setText(String.valueOf((int)sugars));

        r.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(_context, Activity_AddFoodSingleFood.class);
                intent.putExtra("LogDay", _logDate);
                intent.putExtra("LogMealType", _logMealType);
                intent.putExtra("LogFoodName", _displayedFoodList.get(position).getFoodName());
                _context.startActivity(intent);
            }
        });
        return r;
    }

    class ViewHolder
    {
        TextView tvFoodName, outputCalories, outputCarbs, outputFats, outputProtiens, outputSodium, outputSugar;

        ViewHolder(View v)
        {
            tvFoodName = v.findViewById(R.id.textViewFoodName);
            outputCalories = v.findViewById(R.id.textView_Output_Calories);
            outputCarbs = v.findViewById(R.id.textView_Output_Carbs);
            outputFats = v.findViewById(R.id.textView_Output_Fats);
            outputProtiens = v.findViewById(R.id.textView_Output_Proteins);
            outputSodium = v.findViewById(R.id.textView_Output_Sodium);
            outputSugar = v.findViewById(R.id.textView_Output_Sugars);
        }
    }
}
