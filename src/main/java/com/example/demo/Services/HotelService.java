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

@Service
public class HotelService {

    @Value("${HotelEndpointPort}")
    String hotelEndpointPort;



    public List<Hotel> getHotelAddresses(String city) throws IOException, JSONException {

        List<Hotel> list = new ArrayList<>();

        try (CloseableHttpClient client = HttpClients.createDefault()) {

            HttpGet request = new HttpGet("http://localhost:" + hotelEndpointPort + "/hotel/" + URLEncoder.encode(city, StandardCharsets.UTF_8));

            try (CloseableHttpResponse response = client.execute(request)) {

                HttpEntity entity = response.getEntity();

                if(entity != null) {
                    String result = EntityUtils.toString(entity);
                    JSONArray array = new JSONArray(result);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject json = (JSONObject) array.get(i);
                        var name = json.getString("name");
                        var newCity = json.getString("address");
                        var stars = json.getInt("stars");

                        list.add(new Hotel(name, newCity, stars));
                    }
                }
            }
        }
        return list;
    }

    public List<HotelDTO> getHotelDTOs(List<Hotel> hotels) {
        List<HotelDTO> hotelsToReturn = new ArrayList<>();

        for (Hotel hotel: hotels) {
            var hotelDTO = new HotelDTO(hotel.getName(), hotel.getAddress(), hotel.getStars(), hotel.getFoodPlaces());
            hotelsToReturn.add(hotelDTO);
        }

        return hotelsToReturn;
    }
}
