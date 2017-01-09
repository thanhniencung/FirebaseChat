package com.rubik.chatme.helper;

import android.widget.ImageView;

import com.rubik.chatme.ChatMeApplication;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

/**
 * Created by kiennguyen on 1/7/17.
 */

public class ImageLoader {
    public static void loadImage(String url, ImageView imageView) {
        if (url.equals(null) || url.equals("") || imageView == null) {
            return;
        }
        Picasso.with(ChatMeApplication.getContext())
                .load(url).into(imageView);
    }

    public static void loadImageWithTransform(String url, ImageView imageView, Transformation transformation) {
        if (url.equals(null) || url.equals("") || imageView == null) {
            return;
        }
        Picasso.with(ChatMeApplication.getContext())
                .load(url)
                .transform(transformation)
                .into(imageView);
    }

    public static void loadImageWithTransform(int drawable, ImageView imageView, Transformation transformation) {
        Picasso.with(ChatMeApplication.getContext())
                .load(drawable)
                .transform(transformation)
                .into(imageView);
    }
}
