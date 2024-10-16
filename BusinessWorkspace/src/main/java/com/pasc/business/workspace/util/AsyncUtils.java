package com.pasc.business.workspace.util;

import android.support.annotation.NonNull;

import com.pasc.business.workspace.AsyncAction;
import com.pasc.business.workspace.AsyncCallback;
import com.trello.rxlifecycle2.LifecycleTransformer;

import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 异步工具.
 * Created by chenruihan410 on 2018/09/10.
 */
public class AsyncUtils {

    @NonNull
    public static <T> Disposable asyncCall(LifecycleTransformer<T> lifecycleTransformer, AsyncAction<T> asyncAction, final AsyncCallback<T> asyncCallback) {
        Flowable<T> flowable = Flowable.create(asyncAction, BackpressureStrategy.BUFFER);
        return asyncCallFlowable(flowable, lifecycleTransformer, asyncCallback);
    }

    public static <T> Disposable asyncCallFlowable(Flowable<T> flowable, LifecycleTransformer<T> lifecycleTransformer, final AsyncCallback<T> asyncCallback) {
        if (lifecycleTransformer != null) {
            flowable = flowable.compose(lifecycleTransformer);
        }
        return flowable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<T>() {
                    @Override
                    public void accept(T result) throws Exception {
                        if (asyncCallback != null) {
                            asyncCallback.onNext(result);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (asyncCallback != null) {
                            asyncCallback.onError(throwable);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (asyncCallback != null) {
                            asyncCallback.onEnd();
                        }
                    }
                }, new Consumer<Subscription>() {
                    @Override
                    public void accept(Subscription subscription) throws Exception {
                        if (asyncCallback != null) {
                            subscription.request(Long.MAX_VALUE);
                            asyncCallback.onStart();
                        }
                    }
                });
    }
}
