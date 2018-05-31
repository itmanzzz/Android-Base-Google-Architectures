package com.ilives.baseprj.common.base;

import com.trello.rxlifecycle.FragmentLifecycleProvider;


/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.base
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 16:02
 * -------------^_^-------------
 **/
public class BaseFragmentContract {
    public interface View {
        void showLoading();
        void hideLoading();
        FragmentLifecycleProvider getLifecycleProvider();
    }

    public interface Presenter<T> {
        void onDestroy();
        void bindView(T pView);
        void unbindView();
    }

    public interface Interactor {

    }
}
