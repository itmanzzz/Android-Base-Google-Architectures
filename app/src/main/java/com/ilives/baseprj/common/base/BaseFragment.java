package com.ilives.baseprj.common.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

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

    public FragmentLifecycleProvider getFragmentLifecycleProvider() {
        return mFragmentLifecycleProvider;
    }

    public abstract T getPresenter();
}

