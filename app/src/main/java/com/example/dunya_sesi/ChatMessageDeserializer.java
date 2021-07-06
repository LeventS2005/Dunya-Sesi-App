package com.example.dunya_sesi;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class ChatMessageDeserializer extends StdDeserializer<ChatMessage> {

    public ChatMessageDeserializer() {
        this(null);
    }

    public ChatMessageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public ChatMessage deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);

        String sender_id = (node.get("from_id")).asText();
        String reciever_id =  (node.get("to_id")).asText();
        String message =  (node.get("sms")).asText();
        String time_contact =  (node.get("time_contact")).asText();

        return new ChatMessage(sender_id, reciever_id, message, time_contact);
    }
}
