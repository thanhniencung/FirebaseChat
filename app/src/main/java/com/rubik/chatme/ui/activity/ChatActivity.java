package com.rubik.chatme.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.rubik.chatme.ChatMeApplication;
import com.rubik.chatme.R;
import com.rubik.chatme.dao.FbUserDao;
import com.rubik.chatme.firebase.ChatRoom;
import com.rubik.chatme.model.FbUser;
import com.rubik.chatme.model.Message;
import com.rubik.chatme.model.User;
import com.rubik.chatme.ui.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
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

    @BindView(R.id.fragment_chat_send)
    ImageView ivSend;

    @BindView(R.id.fragment_chat_recyclerView)
    RecyclerView recyclerView;

    private List<Message> messageList = new ArrayList<>();
    private MessageAdapter adapter;
    private String currentUserId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAppComponent().inject(this);

        currentUserId = fbUserDao.getFbUSer().fbId;

        final User me = getUserInfo();
        final User friend = (User) getIntent().getSerializableExtra(FRIEND);
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
                if (message.getWho().equals(currentUserId)) {
                    message.setType(MessageAdapter.MSG_ME);
                } else {
                    message.setType(MessageAdapter.MSG_FRIEND);
                }
                adapter.add(message);
                recyclerView.scrollToPosition(messageList.size()-1);
            }
        });

        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editMessage.getText().toString().trim();
                if (!TextUtils.isEmpty(message)) {
                    me.sendMessage(message);
                    editMessage.setText("");
                }
            }
        });
    }

    @Override
    public int getLayout() {
        return R.layout.activity_chat;
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
