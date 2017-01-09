package com.rubik.chatme.di.module;

import android.content.Context;
import android.support.annotation.NonNull;

import com.github.gfx.android.orma.migration.ManualStepMigration;
import com.rubik.chatme.ChatMeApplication;
import com.rubik.chatme.firebase.ChatRoom;
import com.rubik.chatme.firebase.FriendList;
import com.rubik.chatme.model.OrmaDatabase;
import com.rubik.chatme.model.User;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by kiennguyen on 1/2/17.
 */

@Module
public class AppModule {
    private final ChatMeApplication app;

    public AppModule(ChatMeApplication application) {
        app = application;
    }

    @Singleton
    @Provides
    public Context provideContext() {
        return app;
    }

    @Provides
    public ChatRoom provideChatRoom() {
        return new ChatRoom();
    }

    @Provides
    public FriendList provideFriendList() {
        return new FriendList();
    }

    @Singleton
    @Provides
    public OrmaDatabase provideOrmaDatabase(Context context) {
        return OrmaDatabase.builder(context).build();
    }
}
