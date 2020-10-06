package com.chat_Danial.flashchatnewfirebase;

/**
 * Created by club on 4/5/2018.
 */

public class InstantMessage {
    private String message;
    private String author;

    public InstantMessage(String message, String author) {
        this.message = message;
        this.author = author;
    }
    public InstantMessage(){}

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}