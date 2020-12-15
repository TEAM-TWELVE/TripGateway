package com.example.demo.Services;

import com.example.demo.Model.City;
import com.example.demo.Model.Hotel;
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
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
@Service
public class StatisticsService {

    private final Logger logger = Logger.getLogger(StatisticsService.class);

    @Value("${StatisticsEndpointPort}")
    String statisticsEndpointPort;



    public List<City> getStatistics() throws IOException, JSONException {
        logger.info("getStatistics called.");
        List<City> list = new ArrayList<>();

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            logger.info("Successfully entered try block with CloseableHttpClient initialization.");
            HttpGet request = new HttpGet("http://localhost:" + statisticsEndpointPort);
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
                        var noOfSearches = json.getInt("noOfSearches");
                        list.add(new City(name, noOfSearches));
                        logger.info("Added new City instance to List<City> return object.");
                    }
                }
            } catch (JSONException e) {
                logger.error("Could not initialize CloseableHttpResponse.", e);
            }
        } catch (IOException e) {
            logger.error("Could not initialize CloseableHttpClient.", e);
        }
        logger.info("Exiting getStatistics");
        return list;
    }
}
