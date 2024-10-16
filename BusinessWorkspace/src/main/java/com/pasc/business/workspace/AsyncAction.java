package com.pasc.business.workspace;

import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;

/**
 * 异步操作.
 * Created by chenruihan410 on 2018/09/10.
 */
public abstract class AsyncAction<T> implements FlowableOnSubscribe<T> {

    @Override
    public void subscribe(FlowableEmitter<T> flowableEmitter) throws Exception {
        try {
            if (!flowableEmitter.isCancelled()) {
                onAction(flowableEmitter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            flowableEmitter.tryOnError(e);
        } finally {
            flowableEmitter.onComplete();
        }
    }

    protected <T> void onNextNotNull(FlowableEmitter<T> emitter, T object) {
        if (object != null) {
            emitter.onNext(object);
        }
    }

    protected abstract void onAction(FlowableEmitter<T> emitter) throws Exception;
}
