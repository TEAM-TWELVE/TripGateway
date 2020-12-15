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

@Service
public class StatisticsService {

    @Value("${StatisticsEndpointPort}")
    String statisticsEndpointPort;



    public List<City> getStatistics() throws IOException, JSONException {

        List<City> list = new ArrayList<>();

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet("http://localhost:" + statisticsEndpointPort);

            try (CloseableHttpResponse response = client.execute(request)) {

                HttpEntity entity = response.getEntity();

                if(entity != null) {
                    String result = EntityUtils.toString(entity);
                    JSONArray array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject json = (JSONObject) array.get(i);
                        var name = json.getString("name");
                        var noOfSearches = json.getInt("noOfSearches");
                        list.add(new City(name, noOfSearches));
                    }
                }
            }
        }
        return list;
    }
}
