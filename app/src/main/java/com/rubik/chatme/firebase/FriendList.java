package com.rubik.chatme.firebase;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.rubik.chatme.model.User;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by kiennguyen on 1/7/17.
 */

public class FriendList extends FirebaseDB {
    public static final String TAG = "FriendList";
    private static final String FRIEND_LIST_ROOT_TEXT = "friends";
    protected DatabaseReference friendListRoot;
    private ReplaySubject<User> notifier = ReplaySubject.create();

    public FriendList() {
        super();
    }

    public FriendList init() {
        friendListRoot = root.child(FRIEND_LIST_ROOT_TEXT);
        friendListRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i(FriendList.TAG, "onChildAdded() call");
                User user = dataSnapshot.getValue(User.class);
                notifier.onNext(user);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return this;
    }

    public Observable<User> asObservable() {
        return notifier;
    }

    public void addFriend(final User user) {
        friendListRoot.child(user.getId())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        if (!snapshot.exists()) {
                            DatabaseReference messageRoot = friendListRoot.child(user.getId());
                            Map<String, Object> map2 = new HashMap<>();
                            map2.put("name", user.getName());
                            map2.put("avatar", user.getAvatar());
                            map2.put("gender", user.getGender());
                            map2.put("email", user.getEmail());
                            map2.put("id", user.getId());
                            messageRoot.updateChildren(map2);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }
}
