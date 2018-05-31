package com.ilives.baseprj.common.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.trello.rxlifecycle.components.support.RxFragment;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.base
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 16:15
 * -------------^_^-------------
 **/
public abstract class BaseFragment <T extends BaseFragmentContract.Presenter> extends RxFragment {

    private FragmentLifecycleProvider mFragmentLifecycleProvider;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentLifecycleProvider = this;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return this.initRootView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.loadData(savedInstanceState);
        this.initUI(savedInstanceState);
    }

    public FragmentLifecycleProvider getFragmentLifecycleProvider() {
        return mFragmentLifecycleProvider;
    }

    public abstract T getPresenter();

    protected abstract View initRootView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState);

    protected abstract void initUI(Bundle savedInstanceState);

    protected abstract void loadData(Bundle savedInstanceState);
}

