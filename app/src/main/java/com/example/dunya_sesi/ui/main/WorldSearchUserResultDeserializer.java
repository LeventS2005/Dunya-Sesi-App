package com.example.dunya_sesi.ui.main;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class WorldSearchUserResultDeserializer extends StdDeserializer<WorldSearchUserResult> {

    public WorldSearchUserResultDeserializer() {
        this(null);
    }

    public WorldSearchUserResultDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public WorldSearchUserResult deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        String email = (node.get("email")).asText();
        String username =  (node.get("username")).asText();
        String caption =  (node.get("caption")).asText();
        String profile_photo_url =  (node.get("profile_photo_url")).asText();

        return new WorldSearchUserResult(email, username, caption, profile_photo_url);
    }
}
