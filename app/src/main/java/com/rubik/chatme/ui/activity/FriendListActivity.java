package com.rubik.chatme.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.rubik.chatme.ChatMeApplication;
import com.rubik.chatme.R;
import com.rubik.chatme.dao.FbUserDao;
import com.rubik.chatme.firebase.FriendList;
import com.rubik.chatme.model.FbUser;
import com.rubik.chatme.model.User;
import com.rubik.chatme.ui.adapter.FriendListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by kiennguyen on 1/8/17.
 */

public class FriendListActivity extends BaseActivity {
    @Inject
    FriendList friendList;

    @Inject
    FbUserDao fbUserDao;

    @BindView(R.id.activity_friend_list_recycler)
    RecyclerView recyclerView;

    private List<User> userList = new ArrayList<>();
    private FriendListAdapter adapter;
    private FbUser currentUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        ButterKnife.bind(this);
        ChatMeApplication.getContext().getAppComponent().inject(this);

        getSupportActionBar().setTitle("Friends");

        currentUser = fbUserDao.getFbUSer();
        friendList.init();

        adapter = new FriendListAdapter(userList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        friendList.addFriend(getUserInfo());
        friendList.asObservable().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                if (!currentUser.fbId.equals(user.getId())) {
                    adapter.add(user);
                }
            }
        });

        adapter.asObservable().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                Intent intent = new Intent(FriendListActivity.this, ChatActivity.class);
                intent.putExtra(ChatActivity.FRIEND, user);
                startActivity(intent);
            }
        });
    }

    private User getUserInfo() {
        FbUser fbUser = fbUserDao.getFbUSer();
        if (fbUser == null) {
            return null;
        }
        User user = new User();
        user.setName(fbUser.name);
        user.setId(fbUser.fbId);
        user.setGender(fbUser.gender);
        user.setEmail(fbUser.email);
        user.setAvatar(String.format("https://graph.facebook.com/%s/picture?type=large", fbUser.fbId));
        return user;
    }
}
