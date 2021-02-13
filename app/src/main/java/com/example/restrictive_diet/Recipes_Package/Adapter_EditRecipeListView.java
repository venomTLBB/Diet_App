package com.example.restrictive_diet.Recipes_Package;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.restrictive_diet.DailyTracker_Package.Activity_AddFoodSingleFood;
import com.example.restrictive_diet.DailyTracker_Package.Adapter_FoodListView;
import com.example.restrictive_diet.DailyTracker_Package.Model_FoodItem;
import com.example.restrictive_diet.Food_Package.Model_FoodDefinition;
import com.example.restrictive_diet.MainActivity;
import com.example.restrictive_diet.R;

import java.util.ArrayList;

public class Adapter_EditRecipeListView extends ArrayAdapter<Model_FoodItem>
{
    private Activity _context;
    private ArrayList<Model_FoodItem> _foodList;
    private String _logDate, _logMealType;
    private double calories, carbs, fats, protiens, sodum, sugars;

    public Adapter_EditRecipeListView(@NonNull Activity context, ArrayList<Model_FoodItem> foodList)
    {
        super(context, R.layout.listview_layout_edit_recipe, foodList);
        this._context = context;
        this._foodList = foodList;
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

        Model_FoodItem foodDefinition = _foodList.get(position);

        calories = foodDefinition.get_totalCalories() * 100;
        carbs = foodDefinition.get_carb() * 100;
        fats = foodDefinition.get_fat() * 100;
        protiens = foodDefinition.get_protein() * 100;
        sodum = foodDefinition.get_sodium() * 100;
        sugars = foodDefinition.get_sugar() * 100;

        calories = foodDefinition.get_totalCalories();
        carbs = foodDefinition.get_carb();
        fats = foodDefinition.get_fat();
        protiens = foodDefinition.get_protein();
        sodum = foodDefinition.get_sodium();
        sugars = foodDefinition.get_sugar();

        viewHolder.tvFoodName.setText(foodDefinition.get_foodName());
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
                Intent intent = new Intent(_context, MainActivity.class);
                intent.putExtra("LogDay", _logDate);
                intent.putExtra("LogMealType", _logMealType);
                //intent.putExtra("LogFoodName", _displayedFoodList.get(position).getFoodName());
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
