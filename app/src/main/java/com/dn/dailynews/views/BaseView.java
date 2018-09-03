package com.dn.dailynews.views;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseView extends FrameLayout {

    protected CompositeDisposable subscriptions = new CompositeDisposable();

    public BaseView(@NonNull Context context) {
        this(context, null);
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void inflateView(@LayoutRes int layoutId) {
        LayoutInflater.from(getContext()).inflate(layoutId, this, true);
        ButterKnife.bind(this);
    }

    @CallSuper
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @CallSuper
    @Override
    protected void onDetachedFromWindow() {
        subscriptions.clear();
        super.onDetachedFromWindow();
    }
}
