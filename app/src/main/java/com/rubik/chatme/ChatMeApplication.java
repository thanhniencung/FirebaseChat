package com.rubik.chatme;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.rubik.chatme.di.component.AppComponent;
import com.rubik.chatme.di.component.DaggerAppComponent;
import com.rubik.chatme.di.module.AppModule;

/**
 * Created by kiennguyen on 1/1/17.
 */

public class ChatMeApplication extends Application {
    private static ChatMeApplication app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;

        FacebookSdk.sdkInitialize(this);
        AppEventsLogger.activateApp(this);
    }

    public static ChatMeApplication getContext() {
        return app;
    }

    public AppComponent getAppComponent() {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
