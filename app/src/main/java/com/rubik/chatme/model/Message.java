package com.rubik.chatme.model;

/**
 * Created by kiennguyen on 1/6/17.
 */

public class Message {
    private String name;
    private String message;
    private String who;
    private long time;
    private int type;

    public Message() {
    }

    public Message(String name, String message, String who, long time, int type) {
        this.name = name;
        this.message = message;
        this.who = who;
        this.time = time;
        this.type = type;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
