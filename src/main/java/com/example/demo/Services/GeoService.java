package com.example.demo.Services;

import com.example.demo.Model.Coordinate;
import com.example.demo.Model.Hotel;
import com.google.gson.Gson;
import okhttp3.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.BasicHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class GeoService {

    @Value("${GeoEndPointPort}")
    String GeoEndPointPort;

    public void getHotelCoordinates(List<Hotel> hotels) throws IOException, JSONException {

        for (Hotel hotel : hotels) {
            var address = hotel.getAddress();

            try (CloseableHttpClient client = HttpClients.createDefault()) {

                HttpGet request = new HttpGet("http://localhost:" + GeoEndPointPort + "/api/geo/" + URLEncoder.encode(address, StandardCharsets.UTF_8));
                request.setHeader("Content-Type", "application/json;charset=utf-8");

                try (CloseableHttpResponse response = client.execute(request)) {

                    HttpEntity entity = response.getEntity();

                    if (entity != null) {
                        String result = EntityUtils.toString(entity);
                        JSONObject json = new JSONObject(result);
                        var lng = json.getDouble("long");
                        var lat = json.getDouble("lat");
                        hotel.setCoordinate(new Coordinate(lat, lng));
                    }
                }
            }
        }
    }
}
