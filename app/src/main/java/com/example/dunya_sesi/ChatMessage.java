package com.example.dunya_sesi;

// One message, each message that is either sent or recieved
public class ChatMessage {

    String message;
    String sender;

    public ChatMessage (String newMessage, String sender) {
        this.message = newMessage;
        this.sender = sender;
    }
}
