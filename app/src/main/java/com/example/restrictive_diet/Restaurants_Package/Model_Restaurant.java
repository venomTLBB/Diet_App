package com.example.restrictive_diet.Restaurants_Package;

import java.util.Vector;

public class Model_Restaurant {
    // ********VALUES********
    private String _restaurantName;
    private String _linkToWebsite;
    private Vector<Model_MenuItem> _menuItems;

    // ********FUNCTIONS********
    // CONSTRUCTORS
    public Model_Restaurant(String restaurantName, String linkToWebsite, Vector<Model_MenuItem> menuItems)
    {
        _restaurantName = restaurantName;
        _linkToWebsite = linkToWebsite;
        _menuItems = menuItems;
    }

    // GETTERS
    public String getRestaurantName() { return _restaurantName; }
    public String getLinkToWebsite() { return _linkToWebsite; }
    public Vector<Model_MenuItem> getMenuItems() { return _menuItems; }

    // SETTERS
    public void setRestaurantName(String restaurantName) { _restaurantName = restaurantName; }
    public void setLinkToWebsite(String linkToWebsite) { _linkToWebsite = linkToWebsite; }
    public void setMenuItems(Vector<Model_MenuItem> menuItems) { _menuItems = menuItems; }
}
