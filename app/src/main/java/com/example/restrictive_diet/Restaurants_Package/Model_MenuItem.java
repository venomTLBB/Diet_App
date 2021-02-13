package com.example.restrictive_diet.Restaurants_Package;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

public class Model_MenuItem {
    // ********VALUES********
    private String _recipeName;
    private String _imageFileName;
    private Drawable _image;

    // ********FUNCTIONS********
    // CONSTRUCTORS
    public Model_MenuItem(String recipeName, String imageFileName, Context context)
    {
        _recipeName = recipeName;
        _imageFileName = imageFileName;

        Resources resources = context.getResources();
        int resourceID = resources.getIdentifier(_imageFileName, "drawable", context.getPackageName());
        _image = ContextCompat.getDrawable(context, resourceID);
    }

    // GETTERS
    public String getRecipeName() { return _recipeName; }
    public String getImageFileName() { return _imageFileName; }
    public Drawable getDrawable() { return _image; }

    // SETTERS
    public void setRecipeName(String recipeName) { _recipeName = recipeName; }
    public void setImageFileName(String imageFileName) { _imageFileName = imageFileName; }
}
