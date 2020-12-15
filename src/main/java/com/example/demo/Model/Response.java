package com.example.demo.Model;

import java.util.List;

public class Response {
    private List<HotelDTO> hotelDTOS;
    private List<City> topCities;

    public Response(List<HotelDTO> hotelDTOS, List<City> topCities) {
        this.hotelDTOS = hotelDTOS;
        this.topCities = topCities;
    }

    public Response() {
    }

    public List<HotelDTO> getHotelDTOS() {
        return hotelDTOS;
    }

    public void setHotelDTOS(List<HotelDTO> hotelDTOS) {
        this.hotelDTOS = hotelDTOS;
    }

    public List<City> getTopCities() {
        return topCities;
    }

    public void setTopCities(List<City> topCities) {
        this.topCities = topCities;
    }
}
