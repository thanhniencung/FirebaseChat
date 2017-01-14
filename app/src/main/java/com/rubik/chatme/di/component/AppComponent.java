package com.rubik.chatme.di.component;

import com.rubik.chatme.di.module.AppModule;
import com.rubik.chatme.ui.activity.ChatActivity;
import com.rubik.chatme.ui.activity.FriendListActivity;
import com.rubik.chatme.ui.activity.LoginActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by kiennguyen on 1/2/17.
 */
@Singleton
@Component(
        modules = AppModule.class
)
public interface AppComponent {
    void inject(LoginActivity loginActivity);

    void inject(ChatActivity chatActivity);

    void inject(FriendListActivity friendListActivity);
}
