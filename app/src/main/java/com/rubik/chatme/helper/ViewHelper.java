package com.rubik.chatme.helper;

import android.view.MotionEvent;
import android.view.View;

/**
 * Created by kiennguyen on 1/1/17.
 */

public class ViewHelper {
    public static void setStateToView(View button) {
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        setImageAlpha(v, 0.7f);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        // if inside bounds
                        if (event.getX() > 0 && event.getX() < v.getWidth() && event.getY() > 0 && event.getY() < v.getHeight()) {
                            setImageAlpha(v, 0.7f);
                        } else {
                            setImageAlpha(v, 1);
                        }
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        setImageAlpha(v, 1);
                    }
                }
                return false;
            }
        });
    }

    private static void setImageAlpha(View img, float alpha) {
        img.setAlpha(alpha);
    }
}
