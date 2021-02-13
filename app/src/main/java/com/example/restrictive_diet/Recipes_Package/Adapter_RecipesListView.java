package com.example.restrictive_diet.Recipes_Package;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.restrictive_diet.DailyTracker_Package.Model_FoodItem;
import com.example.restrictive_diet.R;
import androidx.annotation.NonNull;

import java.util.ArrayList;

public class Adapter_RecipesListView extends ArrayAdapter<Model_Recipe>
{
    private Activity _context;
    private ArrayList<Model_Recipe> _recipeList;

    double calories, carbs, fats, protiens, sodium, sugars;

    public Adapter_RecipesListView(@NonNull Activity context, ArrayList<Model_Recipe> recipeList)
    {
        super(context, R.layout.listview_layout_recipe, recipeList);
        this._context = context;
        this._recipeList = recipeList;
    }

    public View getView(final int position, View convertView, ViewGroup parent)
    {
        View r = convertView;
        ViewHolder viewHolder = null;;

        if(r == null)
        {
            LayoutInflater layoutInflater = _context.getLayoutInflater();
            r = layoutInflater.inflate(R.layout.listview_layout_recipe, null, true);
            viewHolder = new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) r.getTag();
        }
        final Model_Recipe recipe = _recipeList.get(position);

        viewHolder.tvRecipeName.setText(recipe.getRecipeName().toUpperCase());

        setTotals(recipe);

        viewHolder.tvOutputCalories.setText(String.valueOf((int)calories));
        viewHolder.tvOutputCarbs.setText(String.valueOf((int)carbs));
        viewHolder.tvOutputFats.setText(String.valueOf((int)fats));
        viewHolder.tvOuputProtiens.setText(String.valueOf((int)protiens));
        viewHolder.tvOutputSodium.setText(String.valueOf((int)sodium));
        viewHolder.tvOutputSugars.setText(String.valueOf((int)sugars));

        //viewHolder.tvServingType.setText(foodList.get(position).get_servingType());
        //viewHolder.tvServingAmount.setText(String.valueOf(foodList.get(position).get_numberOfServings()));
        //viewHolder.tvCalories.setText(String.valueOf(_foodDictionaryList.get(position).getCalories()));

        r.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(_context, Activity_EditRecipe.class);
                intent.putExtra("LogRecipeName", recipe.getRecipeName());
                _context.startActivity(intent);
            }
        });
        return r;
    }

    public void setTotals(Model_Recipe recipe)
    {
        for (Model_FoodItem foodItem: recipe.getIngredients())
        {
            calories += foodItem.get_totalCalories();
            carbs += foodItem.get_carb();
            fats += foodItem.get_fat();
            protiens += foodItem.get_protein();
            sodium += foodItem.get_sodium();
            sugars += foodItem.get_sugar();
        }
    }

    class ViewHolder
    {
        TextView tvRecipeName, tvOutputCalories, tvOutputCarbs, tvOutputFats, tvOuputProtiens, tvOutputSodium, tvOutputSugars;

        ViewHolder(View v)
        {
            tvRecipeName = v.findViewById(R.id.textViewRecipe);
            tvOutputCalories = v.findViewById(R.id.textView_Output_Calories);
            tvOutputCarbs = v.findViewById(R.id.textView_Output_Carbs);
            tvOutputFats = v.findViewById(R.id.textView_Output_Fats);
            tvOuputProtiens = v.findViewById(R.id.textView_Output_Proteins);
            tvOutputSodium = v.findViewById(R.id.textView_Output_Sodium);
            tvOutputSugars = v.findViewById(R.id.textView_Output_Sugars);

            //tvServingType = v.findViewById(R.id.textView_Label_ServingType);
            //tvServingAmount = v.findViewById(R.id.textView_Label_ServingAmount);
            //tvCalories = v.findViewById(R.id.textViewCalories);
        }
    }


}
