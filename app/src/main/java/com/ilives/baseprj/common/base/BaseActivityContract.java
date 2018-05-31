package com.ilives.baseprj.common.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.annotation.StringRes;

import com.ilives.baseprj.common.models.Error;
import com.ilives.baseprj.common.models.ToastType;
import com.ilives.baseprj.common.views.OurAlertDialog;
import com.trello.rxlifecycle.ActivityLifecycleProvider;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.base
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 16:01
 * -------------^_^-------------
 **/
public interface BaseActivityContract {

    public interface View {
        void showToast(ToastType type, String message);
        void showToast(ToastType type, @StringRes int messageId);
        void showAlert(OurAlertDialog.DialogType type, @StringRes int stringId, @StringRes int positiveBtnLabel, DialogInterface.OnClickListener listener);
        /**
         * Show alert dialog
         * @param type type of dialog
         * @param message message is show in dialog, String or {@link StringRes}
         * @param negativeLabel label of negative button, String or {@link StringRes}, pass null if don't want to show this button
         * @param negativeListener Callback of negative button, pass null if only dismiss dialog
         * @param positiveLabel label of negative button, String or {@link StringRes}, default is OK if don't pass anything
         * @param positiveListener Callback of positive button, pass null if only dismiss dialog
         */
        void showAlert(OurAlertDialog.DialogType type, Object message,
                       Object negativeLabel, DialogInterface.OnClickListener negativeListener,
                       Object positiveLabel, DialogInterface.OnClickListener positiveListener);
        void setAlertDialogCancelable(boolean isCancelable);
        Activity getActivityContext();
        void showLoading();
        void hideLoading();
        ActivityLifecycleProvider getLifecycleProvider();
    }

    public interface Presenter<T extends View> {
        void onDestroy();
        void bindView(T pView);
        void unbindView();

        /**
         * This method is used to handle common error
         * @param error error
         * @return true if this error is handle and false in otherwise
         */
        boolean handleCommonError(Throwable error);

        boolean handleCommonError(Error error);
    }

    public interface Interactor {

    }
}
