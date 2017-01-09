package com.rubik.chatme.dao;

import com.rubik.chatme.model.FbUser;
import com.rubik.chatme.model.FbUser_Relation;
import com.rubik.chatme.model.OrmaDatabase;
import com.rubik.chatme.rx.RxFuncions;

import java.util.concurrent.Callable;

import javax.inject.Inject;

import io.reactivex.Flowable;

/**
 * Created by kiennguyen on 1/4/17.
 */

public class FbUserDao extends BaseDao {
    OrmaDatabase orma;

    @Inject
    public FbUserDao(OrmaDatabase orma) {
        this.orma = orma;
    }

    private FbUser_Relation FBUserRelation() {
        return orma.relationOfFbUser();
    }

    public Flowable<Long> insert(final FbUser fbUser) {
        return Flowable.defer(new Callable<Flowable<Long>>() {
            @Override
            public Flowable<Long> call() throws Exception {
                return Flowable.just(FBUserRelation().inserter().execute(fbUser));
            }
        }).compose(RxFuncions.<Long>applySchedulers());
    }

    public FbUser getFbUSer() {
        try {
            return FBUserRelation().selector().get(0);
        } catch (Exception e) {}
        return null;
    }
}
