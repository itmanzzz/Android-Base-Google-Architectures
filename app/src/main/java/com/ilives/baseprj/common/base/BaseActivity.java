package com.ilives.baseprj.common.base;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.WindowManager;

import com.ilives.baseprj.R;
import com.ilives.baseprj.app.Constants;
import com.ilives.baseprj.app.MyApplication;
import com.ilives.baseprj.common.models.ToastType;
import com.ilives.baseprj.common.views.DLoading;
import com.ilives.baseprj.common.views.LoadingDialog;
import com.ilives.baseprj.common.views.OurAlertDialog;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import es.dmoral.toasty.Toasty;

import static com.ilives.baseprj.common.models.ToastType.WARNING;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.base
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 16:18
 * -------------^_^-------------
 **/
public abstract class BaseActivity<T extends BaseActivityContract.Presenter> extends RxAppCompatActivity
        implements BaseActivityContract.View {

    /**
     * The constant TIME_OUT_FOR_SHOW_LOADING.
     */
    public static final int TIME_OUT_FOR_SHOW_LOADING = 30000;
    private LoadingDialog mProgressDialog;
    private SharedPreferences mSharedPreferences;

    private boolean mIsAlertDialogCancelable = true;
    protected Handler mOsHandler;
    private Object mLockObj = new Object();
    private DLoading loading;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        initData(savedInstanceState);
        super.onCreate(savedInstanceState);
        loading = new DLoading(this);
        mOsHandler = new Handler();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //Hide navigation view
        //hideNavigationBar();
        initRootView(savedInstanceState);
        loadData(savedInstanceState);
        initUI(savedInstanceState);
    }

    @CallSuper
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransitionExit();
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransitionEnter();
    }

    @CallSuper
    @Override
    protected void onPause() {
        super.onPause();
    }

    @CallSuper
    @Override
    protected void onResume() {
        super.onResume();
        //hideNavigationBar();
    }

    @Override
    public ActivityLifecycleProvider getLifecycleProvider() {
        return this;
    }

    /**
     * Gets presenter.
     *
     * @return the presenter
     */
    public abstract T getPresenter();

    @CallSuper
    @Override
    protected void onDestroy() {
        mOsHandler.removeCallbacksAndMessages(null);
        if (getPresenter() != null) {
            getPresenter().unbindView();
            getPresenter().onDestroy();
        }
        super.onDestroy();
    }


    @Override
    public void showAlert(OurAlertDialog.DialogType type, @StringRes int stringId, @StringRes int positiveBtnLabel, DialogInterface.OnClickListener listener) {
        showAlert(type, stringId, null, null, positiveBtnLabel, listener);
    }

    @Override
    public void showToast(ToastType type, String message) {
        switch (type) {
            case SUCCESS:
                Toasty.success(this, message).show();
                break;
            case INFO:
                Toasty.info(this, message).show();
                break;
            case WARNING:
                Toasty.warning(this, message).show();
                break;
            case ERROR:
                Toasty.error(this, message).show();
                break;
        }
    }

    @Override
    public void showToast(ToastType type, @StringRes int messageId) {
        showToast(type, getString(messageId));
    }

    @Override
    public void showAlert(OurAlertDialog.DialogType type, Object message, Object negativeLabel, DialogInterface.OnClickListener negativeListener, Object positiveLabel, DialogInterface.OnClickListener positiveListener) {
        if (message == null) return;
        runOnUiThread(() -> {
            synchronized (mLockObj) {
                OurAlertDialog dialog = new OurAlertDialog.Builder(this)
                        .setDialoType(type)
                        .setMessage(getText(message))
                        .setNegativeLabel(getText(negativeLabel))
                        .setNegativeListener(negativeListener)
                        .setPositiveLabel(getText(positiveLabel))
                        .setPositiveListener(positiveListener)
                        .build();
                dialog.setCanceledOnTouchOutside(mIsAlertDialogCancelable);
                dialog.show();
                mIsAlertDialogCancelable = true;
            }
        });
    }

    private String getText(Object textObj) {
        if (textObj != null) {
            if (textObj instanceof Integer) {
                try {
                    return getString((Integer) textObj);
                } catch (Exception e) {
                }
            }
            return textObj.toString();
        }
        return null;
    }

    @Override
    public void setAlertDialogCancelable(boolean isCancelable) {
        mIsAlertDialogCancelable = isCancelable;
    }

    @Override
    public void showLoading() {
        runOnUiThread(() -> {
            synchronized (mLockObj) {
                if (mProgressDialog == null) {
                    mProgressDialog = new LoadingDialog(this);
                }
                mProgressDialog.show();

                //Try dismiss loading after a time period for prevent loading mToastDialog is shown persistent.
                mOsHandler.removeCallbacks(mDismissLoadingTask);
                mOsHandler.postDelayed(mDismissLoadingTask, TIME_OUT_FOR_SHOW_LOADING);
            }
        });
    }

    @Override
    public void hideLoading() {
        runOnUiThread(() -> {
            synchronized (mLockObj) {
                if (mProgressDialog != null) {
                    mProgressDialog.dismiss();
                }
            }
        });
    }

    private void showALoading() {
        if (isFinishing()) {
            return;
        }

        if (!loading.isShowing())
            loading.show();
    }

    private void hideALoading() {
        if (isFinishing()) {
            return;
        }

        if (loading.isShowing())
            loading.dismiss();
    }

    @Override
    public Activity getActivityContext() {
        return this;
    }

    protected Runnable mDismissLoadingTask = () -> hideLoading();

    protected void logOut() {
        showAlert(OurAlertDialog.DialogType.CONFIRM, R.string.msg_confirm_logout,
                R.string.action_cancel, null,
                R.string.action_ok, (dialog, which) -> {
                    if (mSharedPreferences == null) {
                        mSharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, MODE_PRIVATE);
                    }
                    String token = mSharedPreferences.getString(Constants.SHARED_PREFERENCE_TOKEN, "");
//                    UserRepository.getInstance().doLogout()
//                            .subscribeOn(Schedulers.newThread())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe(res -> {
//                            }, error -> {
//                            }, () -> {
//                            });
                    mSharedPreferences.edit()
                            .remove(Constants.SHARED_PREFERENCE_TOKEN)
                            .remove(Constants.SHARED_PREFERENCE_USER_ID)
                            .apply();
                    // TODO Clear all local data and back to login
                    //startActivity(new Intent(this, LoginActivity.class));
                    finish();
                    dialog.dismiss();
                });
    }


    public boolean preCheckConnection() {
        if (!MyApplication.isOnline()) {
            showToast(WARNING, R.string.msg_error_no_internet);
            return false;
        }
        return true;
    }

    /**
     * Full screen options
     */
    public void hideNavigationBar() {
        if (Build.VERSION.SDK_INT < 19) { // lower api
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
            decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
                if ((visibility & View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0 ||
                        (visibility & View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY) == 0) {
                    decorView.setSystemUiVisibility(uiOptions);
                }
            });
        }
    }

    protected abstract void initData(Bundle savedInstanceState);

    protected abstract void initRootView(Bundle savedInstanceState);

    protected abstract void initUI(Bundle savedInstanceState);

    protected abstract void loadData(Bundle savedInstanceState);

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     */
    protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.transition.slide_from_right, R.transition.slide_to_left);
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     */
    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.transition.slide_from_left, R.transition.slide_to_right);
    }
}