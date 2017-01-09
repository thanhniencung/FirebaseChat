package com.rubik.chatme.firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by kiennguyen on 1/8/17.
 */

public class FirebaseDB {
    protected DatabaseReference root;

    public FirebaseDB() {
        root = FirebaseDatabase.getInstance()
                .getReference();
    }
}
