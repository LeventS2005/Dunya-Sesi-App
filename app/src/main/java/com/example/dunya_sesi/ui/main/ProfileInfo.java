package com.example.dunya_sesi.ui.main;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * A class to store user info loaded from the database
 * */
public class ProfileInfo {

    public String username;
    public String caption;
    String profileImageUrl;

    public ProfileInfo (String username, String caption, String profileImageUrl) {
        this.username = username;
        this.caption = caption;
        this.profileImageUrl = profileImageUrl;
    }

    public ProfileInfo(String userProfileJson){
        // load user from json result
        this.username = userProfileJson;
        StringBuilder response = new StringBuilder(userProfileJson);
        response.deleteCharAt(0);
        response.deleteCharAt(response.length() - 1);
        try {
            Map<String,Object> result =
                    new ObjectMapper().readValue(response.toString(), HashMap.class);
            this.username = result.get("username").toString();
            this.caption = result.get("caption").toString();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
