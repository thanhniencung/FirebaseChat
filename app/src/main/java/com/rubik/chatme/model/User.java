package com.rubik.chatme.model;

import com.google.firebase.database.DatabaseReference;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kiennguyen on 1/7/17.
 */

public class User implements Serializable {
    private String name;
    private String avatar;
    private String gender;
    private String email;
    private String id;

    private DatabaseReference root;

    public User() {}

    public User(String name, String avatar, String gender, String email, String id) {
        this.name = name;
        this.avatar = avatar;
        this.gender = gender;
        this.email = email;
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDatabaseReference(DatabaseReference databaseReference) {
        this.root = databaseReference;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void sendMessage(String message) {
        String tempkey = root.push().getKey();
        DatabaseReference messageRoot = root.child(tempkey);
        Map<String,Object> map2 = new HashMap<>();
        map2.put("name", getName());
        map2.put("message", message);
        map2.put("who", getId());
        map2.put("time", System.currentTimeMillis());

        messageRoot.updateChildren(map2);
    }
}
