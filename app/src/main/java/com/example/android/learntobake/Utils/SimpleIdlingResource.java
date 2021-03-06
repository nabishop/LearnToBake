package com.example.android.learntobake.Utils;

import android.support.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleIdlingResource implements IdlingResource {
    private volatile ResourceCallback callback;

    private AtomicBoolean isIdleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIdleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.callback = callback;
    }

    public void setIsIdleNow(Boolean isIdleNow) {
        this.isIdleNow.set(isIdleNow);
        if (isIdleNow() && callback != null) {
            callback.onTransitionToIdle();
        }
    }
}
