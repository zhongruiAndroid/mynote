package com.zr.note.base;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/9/5.
 */
public interface BasePresenter<V extends BaseView> {
    void attach(V view);
    void detach();
}
