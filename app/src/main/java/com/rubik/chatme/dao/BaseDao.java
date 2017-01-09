package com.rubik.chatme.dao;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by kiennguyen on 1/4/17.
 */

public class BaseDao {
    protected static <T> Observable<T> makeObservable(final Callable<T> func) {
        return Observable.create(
                new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> e) throws Exception {
                try {
                    e.onNext(func.call());
                } catch(Exception exp) {
                    exp.printStackTrace();
                }
            }
        });
    }
}
