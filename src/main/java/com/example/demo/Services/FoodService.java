package com.example.demo.Services;

import com.example.demo.Model.Coordinate;
import com.example.demo.Model.FoodPlace;
import com.example.demo.Model.Hotel;
import okhttp3.OkHttpClient;
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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.apache.log4j.Logger;

@Service
public class FoodService {

    private final Logger logger = Logger.getLogger(FoodService.class);

    @Value("${FoodEndpointPort}")
    String FoodEndpointPort;

    public void getRestaurants(List<Hotel> hotels) {
        logger.info("getRestaurants called.");
        for (Hotel hotel : hotels) {
            var coordinates = hotel.getCoordinate();

            try (CloseableHttpClient client = HttpClients.createDefault()) {
                logger.info("Successfully entered try block with CloseableHttpClient initialization.");
                HttpGet request = new HttpGet("http://localhost:" + FoodEndpointPort + "/api/restaurant?lat=" + coordinates.getLat() + "&lng=" + coordinates.getLng());
                request.setHeader("Content-Type", "application/json;charset=utf-8");
                logger.info("Created HttpGet instance with Content-Type application/json.");

                try (CloseableHttpResponse response = client.execute(request)) {
                    logger.info("Successfully entered try block with CloseableHttpResponse initialization.");
                    HttpEntity entity = response.getEntity();
                    logger.info("HttpEntity initialized.");

                    if (entity != null) {
                        logger.info("HttpEntity was NOT null.");
                        String result = EntityUtils.toString(entity);
                        JSONArray array = new JSONArray(result);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject json = (JSONObject) array.get(i);
                            var name = json.getString("name");
                            var address = json.getString("address");
                            var rating = json.getDouble("rating");
                            hotel.getFoodPlaces().add(new FoodPlace(name, address, rating));
                            logger.info("Added new FoodPlace instance to List<FoodPlace>.");
                        }
                    }
                } catch (JSONException e) {
                    logger.error("Could not initialize CloseableHttpResponse.", e);
                }
            } catch (IOException e) {
                logger.error("Could not initialize CloseableHttpClient.", e);
            }
        }
        logger.info("Exiting getRestaurants");
    }

}
