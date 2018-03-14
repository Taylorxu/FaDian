package com.powerge.wise.basestone.heart.network;

import com.powerge.wise.basestone.heart.util.LogUtils;

import rx.Observable;
import rx.functions.Func1;


public class FlatMapTopRes<Data> implements Func1<ResultModel<Data>, Observable<Data>> {
    @Override
    public Observable<Data> call(ResultModel<Data> response) {
        if (response.getReturnState().equals("0")) {
            return Observable.just(response.getReturnValue());
        } else {
            return Observable.error(response.getError());
        }

    }
}
