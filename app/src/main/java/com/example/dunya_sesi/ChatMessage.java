package com.example.dunya_sesi;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(using = ChatMessageDeserializer.class)
public class ChatMessage {

    String sender_id;
    String reciever_id;
    String message;
    String time_contact;

    public ChatMessage (String sender_id, String reciever_id, String message, String time_contact) {
        this.sender_id = sender_id;
        this.reciever_id = reciever_id;
        this.message = message;
        this.time_contact = time_contact;
    }
}
