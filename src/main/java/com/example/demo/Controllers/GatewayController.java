package com.example.demo.Controllers;

import com.example.demo.KafkaProducer.ProducerService;
import com.example.demo.Model.HotelDTO;
import com.example.demo.Model.Response;
import com.example.demo.Services.FoodService;
import com.example.demo.Services.GeoService;
import com.example.demo.Services.HotelService;
import com.example.demo.Services.StatisticsService;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;


@RestController
public class GatewayController {

    private final Logger logger = Logger.getLogger(GatewayController.class);

    @Autowired
    HotelService hotelService;
    @Autowired
    GeoService geoService;
    @Autowired
    FoodService foodService;
    @Autowired
    StatisticsService statisticsService;
    @Autowired
    private ProducerService service;

    @GetMapping("/{city}")
    public Response getRecommendations(@PathVariable String city) throws IOException, JSONException {
        logger.info("/city GET getRecommendations called.");
        var hotels = hotelService.getHotelAddresses(city);
        service.sendObject(city);
        geoService.getHotelCoordinates(hotels);
        foodService.getRestaurants(hotels);
        Response response = new Response(hotelService.getHotelDTOs(hotels), statisticsService.getStatistics());
        logger.info("/city GET successfully finished.");
        return response;
    }

}
