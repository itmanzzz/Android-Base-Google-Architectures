package com.ilives.baseprj.common.base;

import com.ilives.baseprj.R;
import com.ilives.baseprj.common.models.Error;
import com.ilives.baseprj.common.models.ToastType;
import com.ilives.baseprj.common.views.OurAlertDialog;
import com.ilives.baseprj.services.network.RetrofitException;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.base
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 16:02
 * -------------^_^-------------
 **/
public abstract class BaseActivityPresenter <V extends BaseActivityContract.View> implements BaseActivityContract.Presenter<V> {

    protected V mView;

    @Override
    public void bindView(V pView) {
        mView = pView;
    }

    @Override
    public void unbindView() {
        mView = null;
    }

    @Override
    public boolean handleCommonError(Throwable error) {
        if (error != null) {
            if (error instanceof RetrofitException) {
                RetrofitException.Kind kind = ((RetrofitException) error).getKind();
                if (kind == RetrofitException.Kind.NETWORK || kind == RetrofitException.Kind.UNEXPECTED) {
                    if (mView != null) {
                        mView.showToast(ToastType.ERROR, kind == RetrofitException.Kind.NETWORK ? R.string.msg_error_no_internet : R.string.msg_unexpected_error);
                    }
                    return true;
                }
            }
        } else {
            if (mView != null) {
                mView.showToast(ToastType.ERROR, R.string.msg_unexpected_error);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean handleCommonError(Error error) {
        if (error != null) {
            if (error.getCode() == 401) {
                //Token expire
                if (mView != null) {
                    mView.showAlert(OurAlertDialog.DialogType.ERROR, error.getFirstMessage(),
                            null, null,
                            R.string.action_ok, (dialog, which) -> {
                                dialog.dismiss();
                                // TODO Unauthenticated
                               // mView.getActivityContext().startActivity(new Intent(mView.getActivityContext(), LoginActivity.class));
                                mView.getActivityContext().finish();
                            });
                }
                return true;
            }
        }
        return false;
    }
}

