package com.dev.web.utils;

import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class JSONHelper {

    public Map<String, Object[]> loadData(String countryValue) throws IOException {
        // Read JSON data from file
        String jsonStr = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/src/main/resources/testData/data.json")));

        // Parse JSON data to JSONObject
        JSONObject json = new JSONObject(jsonStr);
        Map<String, Object[]> dataMap = new HashMap<>();
        JSONArray countries = json.getJSONArray("countries");

        for (int i = 0; i < countries.length(); i++) {
            JSONObject country = countries.getJSONObject(i);
            if (country.getString("name").equals(countryValue)) {
                // Get the "lite", "classic", and "premium" arrays for specific country
                JSONArray lite = country.getJSONArray(Constants.LITE_PACKAGE);
                JSONArray classic = country.getJSONArray(Constants.CLASSIC_PACKAGE);
                JSONArray premium = country.getJSONArray(Constants.PREMIUM_PACKAGE);

                dataMap.put(Constants.LITE_PACKAGE, new Object[]{lite.getJSONObject(0).get("price"), lite.getJSONObject(0).get("currency")});
                dataMap.put(Constants.CLASSIC_PACKAGE, new Object[]{classic.getJSONObject(0).get("price"), classic.getJSONObject(0).get("currency")});
                dataMap.put(Constants.PREMIUM_PACKAGE, new Object[]{premium.getJSONObject(0).get("price"), premium.getJSONObject(0).get("currency")});
            }
        }
        return dataMap;
    }
}