package com.example.demo.Model;

public class City {
    private String name;
    private int noOfSearches;

    public City(String name, int noOfSearches) {
        this.name = name;
        this.noOfSearches = noOfSearches;
    }

    public City() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNoOfSearches() {
        return noOfSearches;
    }

    public void setNoOfSearches(int noOfSearches) {
        this.noOfSearches = noOfSearches;
    }
}
