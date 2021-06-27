package com.example.dunya_sesi.ui.main;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = WorldSearchUserResultDeserializer.class)
public class WorldSearchUserResult {

    String email;
    String username;
    String caption;
    String profile_photo_url;

    public WorldSearchUserResult(String email, String newUsername, String newCaption, String profile_photo_url) {
        this.email = email;
        this.username = newUsername;
        this.caption = newCaption;
        this.profile_photo_url = profile_photo_url;
    }
}
