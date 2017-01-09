package com.rubik.chatme.model;

import android.support.annotation.Nullable;

import com.github.gfx.android.orma.annotation.Column;
import com.github.gfx.android.orma.annotation.Table;

/**
 * Created by kiennguyen on 1/4/17.
 */

@Table
public class FbUser {
    @Column
    @Nullable
    public String fbId;

    @Column
    @Nullable
    public String gender;

    @Column
    @Nullable
    public String email;

    @Column
    @Nullable
    public String name;
}
