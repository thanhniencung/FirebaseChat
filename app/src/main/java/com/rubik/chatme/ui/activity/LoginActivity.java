package com.rubik.chatme.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.rubik.chatme.R;
import com.rubik.chatme.dao.FbUserDao;
import com.rubik.chatme.helper.CircleTransform;
import com.rubik.chatme.helper.ImageLoader;
import com.rubik.chatme.helper.ViewHelper;
import com.rubik.chatme.model.FbUser;

import org.json.JSONObject;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.reactivex.subscribers.DefaultSubscriber;


/**
 * Created by kiennguyen on 1/1/17.
 */

public class LoginActivity extends  BaseActivity implements
        FacebookCallback<LoginResult> {

    private CallbackManager callbackManager;

    @Inject
    FbUserDao fbUserDao;

    @BindView(R.id.ivBgLogin)
    ImageView ivBgLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getAppComponent().inject(this);

        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, this);

        ViewHelper.setStateToView(findViewById(R.id.btn_login_fb));
        ImageLoader.loadImageWithTransform(R.drawable.icon_login,
                ivBgLogin, new CircleTransform());

        fbUserDao.getFbUSer().subscribe(new DefaultSubscriber<FbUser>() {
            @Override
            public void onNext(FbUser fbUser) {
                if (verifyUser(fbUser)) {
                    gotoFriendListActivity();
                }
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private boolean verifyUser(FbUser fbUser) {
        if (fbUser == null) {
            return false;
        }
        if (fbUser.fbId.equals(null) ||
            fbUser.email.equals(null) ||
            fbUser.gender.equals(null) ||
            fbUser.name.equals(null)) {
            return false;
        }
        return true;
    }

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @OnClick(R.id.btn_login_fb)
    public void facebookLoginClicked() {
        loginFacebook();
    }

    private void loginFacebook() {
        LoginManager.getInstance().logInWithReadPermissions(this,
                Arrays.asList("public_profile", "email"));
    }

    @Override
    public void onSuccess(LoginResult loginResult) {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject data, GraphResponse response) {
                        fbUserDao.insert(parseFbUSer(data))
                                .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                gotoFriendListActivity();
                            }
                        });
                    }
                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,gender,birthday");
        request.setParameters(parameters);
        request.executeAsync();

    }

    private void gotoFriendListActivity() {
        startActivity(new Intent(LoginActivity.this,
                FriendListActivity.class));
        finish();
    }

    private FbUser parseFbUSer(JSONObject data) {
        FbUser fbUser = new FbUser();
        fbUser.fbId = data.optString("id");
        fbUser.email = data.optString("email");
        fbUser.gender = data.optString("gender");
        fbUser.name = data.optString("name");
        return fbUser;
    }

    @Override
    public void onCancel() {

    }

    @Override
    public void onError(FacebookException error) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (callbackManager != null) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
}