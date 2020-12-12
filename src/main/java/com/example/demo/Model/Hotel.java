package com.example.demo.Model;

import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private String name, address;
    private int stars;
    private List<FoodPlace> foodPlaces = new ArrayList<>();
    private Coordinate coordinate;

    public Hotel(String name, String address, int stars) {
        this.name = name;
        this.address = address;
        this.stars = stars;
    }


    public Hotel() {
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

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", stars=" + stars +
                ", foodPlaces=" + foodPlaces +
                ", coordinate=" + coordinate +
                '}';
    }
}
