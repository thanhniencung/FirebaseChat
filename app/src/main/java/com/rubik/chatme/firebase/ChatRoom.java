package com.rubik.chatme.firebase;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.rubik.chatme.model.Message;
import com.rubik.chatme.model.User;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by kiennguyen on 1/7/17.
 */

public class ChatRoom extends FirebaseDB {
    public static final String TAG = "ChatRoom";
    private User me;
    private User friend;
    protected DatabaseReference roomRoot;
    private ReplaySubject<Message> notifier = ReplaySubject.create();

    public ChatRoom() {
        super();
    }

    public void init(final User me, User friend) {
        this.me = me;
        this.friend = friend;

        roomRoot = root.child(getRoomName());

        this.me.setDatabaseReference(roomRoot);
        this.friend.setDatabaseReference(roomRoot);

        roomRoot.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.i(ChatRoom.TAG, "onChildAdded() call");
                Message message = dataSnapshot.getValue(Message.class);
                notifier.onNext(message);
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
    }

    public Observable<Message> asObservable() {
        return notifier;
    }

    private String getRoomName() {
        try {
            double idMe = Double.parseDouble(me.getId());
            double idFriend = Double.parseDouble(friend.getId());
            if (idMe > idFriend) {
                return "Room_" + me.getId() + "_" + friend.getId();
            } else {
                return "Room_" + friend.getId() + "_" + me.getId();
            }

        } catch (Exception exp) {

        }
        return "";
    }
}
