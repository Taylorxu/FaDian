package com.powerge.wise.basestone.heart.network;

import rx.Observable;
import rx.functions.Func1;

/**
 * Created by 王冰 on 2016/12/29.
 */

public class FlatMapTopRes<Data> implements Func1<TopResponse<Data>, Observable<Data>> {
    @Override
    public Observable<Data> call(TopResponse<Data> response) {
        if (response.getCode()==200) {
            return Observable.just(response.getData());
        } else {
            return Observable.error(response.getError());
        }

    }
}
