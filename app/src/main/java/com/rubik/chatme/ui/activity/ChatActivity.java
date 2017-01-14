package com.rubik.chatme.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.rubik.chatme.R;
import com.rubik.chatme.dao.FbUserDao;
import com.rubik.chatme.firebase.ChatRoom;
import com.rubik.chatme.helper.FirebaseHelper;
import com.rubik.chatme.model.FbUser;
import com.rubik.chatme.model.Message;
import com.rubik.chatme.model.User;
import com.rubik.chatme.ui.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * Created by kiennguyen on 1/6/17.
 */

public class ChatActivity extends BaseActivity {
    public static final String FRIEND = "friend";

    @Inject
    ChatRoom chatRoom;

    @Inject
    FbUserDao fbUserDao;

    @BindView(R.id.fragment_chat_enterMsg)
    EditText editMessage;

    @BindView(R.id.fragment_chat_progress)
    ProgressBar progressBar;

    @BindView(R.id.fragment_chat_recyclerView)
    RecyclerView recyclerView;

    private List<Message> messageList = new ArrayList<>();
    private MessageAdapter adapter;
    private FbUser fbUser;
    private User me;
    private User friend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAppComponent().inject(this);

        fbUserDao.getFbUSer().subscribe(new Consumer<FbUser>() {
            @Override
            public void accept(FbUser fbUser) throws Exception {
                ChatActivity.this.fbUser = fbUser;
            }
        });

        me = FirebaseHelper.setupFirebaseUser(fbUser);
        friend = (User) getIntent().getSerializableExtra(FRIEND);

        getSupportActionBar().setTitle(friend.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chatRoom.init(me, friend);

        adapter = new MessageAdapter(messageList, me, friend);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        chatRoom.asObservable().subscribe(new Consumer<Message>() {
            @Override
            public void accept(Message message) throws Exception {
                if (progressBar.getVisibility() == View.VISIBLE) {
                    progressBar.setVisibility(View.GONE);
                }
                if (message.getWho().equals(fbUser.fbId)) {
                    message.setType(MessageAdapter.MSG_ME);
                } else {
                    message.setType(MessageAdapter.MSG_FRIEND);
                }
                adapter.add(message);
                recyclerView.scrollToPosition(messageList.size()-1);
            }
        });
    }

    @OnClick(R.id.fragment_chat_send)
    public void sendMessageClicked() {
        String message = editMessage.getText().toString().trim();
        if (!TextUtils.isEmpty(message)) {
            me.sendMessage(message);
            editMessage.setText("");
        }
    }

    @Override
    public int getLayout() {
        return R.layout.activity_chat;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return false;
    }
}
