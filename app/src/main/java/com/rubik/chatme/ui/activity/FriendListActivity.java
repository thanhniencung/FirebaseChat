package com.rubik.chatme.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.rubik.chatme.R;
import com.rubik.chatme.dao.FbUserDao;
import com.rubik.chatme.firebase.FriendList;
import com.rubik.chatme.helper.FirebaseHelper;
import com.rubik.chatme.model.FbUser;
import com.rubik.chatme.model.User;
import com.rubik.chatme.ui.adapter.FriendListAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
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

    @BindView(R.id.activity_friend_progress)
    ProgressBar progressBar;

    private List<User> userList = new ArrayList<>();
    private FriendListAdapter adapter;
    private FbUser fbUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAppComponent().inject(this);

        getSupportActionBar().setTitle("Friends");

        fbUserDao.getFbUSer().subscribe(new Consumer<FbUser>() {
            @Override
            public void accept(FbUser fbUser) throws Exception {
                FriendListActivity.this.fbUser = fbUser;
            }
        });

        friendList.init();

        adapter = new FriendListAdapter(userList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        adapter.asObservable().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                Intent intent = new Intent(FriendListActivity.this, ChatActivity.class);
                intent.putExtra(ChatActivity.FRIEND, user);
                startActivity(intent);
            }
        });

        friendList.addFriend(FirebaseHelper.setupFirebaseUser(fbUser));
        friendList.asObservable().subscribe(new Consumer<User>() {
            @Override
            public void accept(User user) throws Exception {
                if (progressBar.getVisibility() == View.VISIBLE) {
                    progressBar.setVisibility(View.GONE);
                }
                if (!fbUser.fbId.equals(user.getId())) {
                    adapter.add(user);
                }
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_friend_list;
    }
}
