package com.sbapplication.service.impl;

import com.sbapplication.api.response.WetherResponse;
import com.sbapplication.cache.AppCache;
import com.sbapplication.constants.Placeholder;
import com.sbapplication.service.RedisService;
import com.sbapplication.service.WetherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class WetherServiceImpl implements WetherService {

    @Value("${wether.api.key}")
    private String apiKey;
    //private static final String url = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;

    @Override
    public WetherResponse getWether(String city) {
        WetherResponse weatherResponse = redisService.get("weather_of_" + city, WetherResponse.class);
        if (weatherResponse != null) {
            System.out.println("dfrrdrhesresh");
            return weatherResponse;
        } else {
            String finalAPI = appCache.getAppCache().get(AppCache.keys.WETHER_API.toString()).replace(Placeholder.CITY, city).replace(Placeholder.API_KEY, apiKey);
            ResponseEntity<WetherResponse> response = restTemplate.exchange(finalAPI, HttpMethod.POST, null, WetherResponse.class);
            WetherResponse body = response.getBody();
            if (body != null) {
                redisService.set("weather_of_" + city, body, 300l);
            }
            return body;
        }
    }

}
