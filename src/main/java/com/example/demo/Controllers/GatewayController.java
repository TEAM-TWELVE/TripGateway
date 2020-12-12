package com.example.demo.Controllers;

import com.example.demo.Model.Hotel;
import com.example.demo.Model.HotelDTO;
import com.example.demo.Model.Trip;
import com.example.demo.Services.FoodService;
import com.example.demo.Services.GeoService;
import com.example.demo.Services.HotelService;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
public class GatewayController {

    @Autowired
    HotelService hotelService;
    @Autowired
    GeoService geoService;
    @Autowired
    FoodService foodService;

    @GetMapping("/{city}")
    public List<HotelDTO> getRecommendations(@PathVariable String city) throws IOException, JSONException {
        var hotels = hotelService.getHotelAddresses(city);
        geoService.getHotelCoordinates(hotels);
        foodService.getRestaurants(hotels);
        return hotelService.getHotelDTOs(hotels);
    }
}
