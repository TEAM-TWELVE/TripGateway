package com.example.demo.Services;

import com.example.demo.Model.Hotel;
import com.example.demo.Model.HotelDTO;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jackson.JsonObjectDeserializer;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.Logger;

@Service
public class HotelService {

    private final Logger logger = Logger.getLogger(HotelService.class);

    @Value("${HotelEndpointPort}")
    String hotelEndpointPort;



    public List<Hotel> getHotelAddresses(String city) {
        logger.info("getHotelAddresses called.");
        List<Hotel> list = new ArrayList<>();

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            logger.info("Successfully entered try block with CloseableHttpClient initialization.");
            HttpGet request = new HttpGet("http://localhost:" + hotelEndpointPort + "/hotel/" + URLEncoder.encode(city, StandardCharsets.UTF_8));
            logger.info("Created HttpGet instance.");

            try (CloseableHttpResponse response = client.execute(request)) {
                logger.info("Successfully entered try block with CloseableHttpResponse initialization.");
                HttpEntity entity = response.getEntity();
                logger.info("HttpEntity initialized.");

                if(entity != null) {
                    logger.info("HttpEntity was NOT null.");
                    String result = EntityUtils.toString(entity);
                    JSONArray array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject json = (JSONObject) array.get(i);
                        var name = json.getString("name");
                        var newCity = json.getString("address");
                        var stars = json.getInt("stars");

                        list.add(new Hotel(name, newCity, stars));
                        logger.info("Added new Hotel instance to List<Hotel> return object.");
                    }
                }
            } catch (JSONException e) {
                logger.error("Could not initialize CloseableHttpResponse.", e);
            }
        } catch (IOException e) {
            logger.error("Could not initialize CloseableHttpClient.", e);
        }
        logger.info("Exiting getHotelAddresses");
        return list;
    }

    public List<HotelDTO> getHotelDTOs(List<Hotel> hotels) {
        logger.info("getHotelDTOs called.");
        List<HotelDTO> hotelsToReturn = new ArrayList<>();

        for (Hotel hotel: hotels) {
            var hotelDTO = new HotelDTO(hotel.getName(), hotel.getAddress(), hotel.getStars(), hotel.getFoodPlaces());
            hotelsToReturn.add(hotelDTO);
            logger.info("Added new HotelDTO to List<HotelDTO> return object.");
        }
        logger.info("Exiting getHotelDTOs.");
        return hotelsToReturn;
    }
}
