package com.example.demo.Model;

import java.util.ArrayList;
import java.util.List;

public class HotelDTO {

    private String name, address;
    private int stars;
    private List<FoodPlace> foodPlaces;

    public HotelDTO(String name, String address, int stars, List<FoodPlace> foodPlaces) {
        this.name = name;
        this.address = address;
        this.stars = stars;
        this.foodPlaces = foodPlaces;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public List<FoodPlace> getFoodPlaces() {
        return foodPlaces;
    }

    public void setFoodPlaces(List<FoodPlace> foodPlaces) {
        this.foodPlaces = foodPlaces;
    }
}
