package com.rubik.chatme.helper;

import com.rubik.chatme.model.FbUser;
import com.rubik.chatme.model.User;

/**
 * Created by kiennguyen on 1/9/17.
 */

public class FirebaseHelper {
    public static User setupFirebaseUser(FbUser fbUser) {
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
