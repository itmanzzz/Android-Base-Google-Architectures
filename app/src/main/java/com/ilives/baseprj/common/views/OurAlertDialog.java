package com.ilives.baseprj.common.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ilives.baseprj.R;
import com.ilives.baseprj.databinding.DialogAlertBinding;


public class OurAlertDialog extends Dialog {

    DialogAlertBinding rootView;

    public enum DialogType {
        CONFIRM,
        WARNING,
        ERROR
    }


    private DialogType mDialogType;
    private String mMessage;
    private String mNegativeLabel;
    private String mPositiveLabel;
    private DialogInterface.OnClickListener mNegativeListener;
    private DialogInterface.OnClickListener mPositiveListener;

    public OurAlertDialog(@NonNull Context context) {
        super(context);
        if (getWindow() != null) {
            getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
            getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.rootView = DataBindingUtil.setContentView((Activity) getContext(), R.layout.dialog_alert);
        boolean hasNegativeButton = mNegativeLabel != null && !mNegativeLabel.trim().isEmpty();
        if (hasNegativeButton) {
            this.rootView.btnNegative.setVisibility(View.VISIBLE);
            this.rootView.btnNegative.setText(mNegativeLabel.trim());
        }
        int positiveBgId;
        if (hasNegativeButton) {
            positiveBgId = mDialogType == DialogType.CONFIRM ? R.drawable.bg_dialog_btn_confirm_right :
                    mDialogType == DialogType.WARNING ? R.drawable.bg_dialog_btn_warning_right : R.drawable.bg_dialog_btn_error_right;
        } else {
            positiveBgId = mDialogType == DialogType.CONFIRM ? R.drawable.bg_dialog_btn_confirm :
                    mDialogType == DialogType.WARNING ? R.drawable.bg_dialog_btn_warning : R.drawable.bg_dialog_btn_error;
        }
        this.rootView.btnPositive.setBackgroundResource(positiveBgId);
        this.rootView.btnPositive.setText(mPositiveLabel != null ? mPositiveLabel : getContext().getString(R.string.action_ok));
        this.rootView.tvDialogMessageContent.setText(mMessage);

        this.rootView.btnPositive.setOnClickListener(view -> this.onPositiveBtnClick());
        this.rootView.btnNegative.setOnClickListener(view -> this.onNegativeBtnClick());
    }

    public void onNegativeBtnClick() {
        if (mNegativeListener != null) {
            mNegativeListener.onClick(this, -1);
        } else {
            dismiss();
        }
    }

    public void onPositiveBtnClick() {
        if (mPositiveListener != null) {
            mPositiveListener.onClick(this, 1);
        } else {
            dismiss();
        }
    }

    public static class Builder {
        private DialogType mDialogType;
        private String mMessage;
        private String mNegativeLabel;
        private String mPositiveLabel;
        private DialogInterface.OnClickListener mNegativeListener;
        private DialogInterface.OnClickListener mPositiveListener;
        private Context mContext;

        public Builder(@NonNull Context context) {
            mContext = context;
        }

        public Builder setDialoType(DialogType type) {
            mDialogType = type;
            return this;
        }

        public Builder setMessage(String message) {
            mMessage = message;
            return this;
        }

        public Builder setNegativeLabel(String negativeLabel) {
            mNegativeLabel = negativeLabel;
            return this;
        }

        public Builder setPositiveLabel(String positiveLabel) {
            mPositiveLabel = positiveLabel;
            return this;
        }


        public Builder setMessage(@StringRes int messageId) {
            mMessage = mContext.getString(messageId);
            return this;
        }

        public Builder setNegativeLabel(@StringRes int negativeLabelId) {
            mNegativeLabel = mContext.getString(negativeLabelId);
            return this;
        }

        public Builder setPositiveLabel(@StringRes int positiveLabelId) {
            mPositiveLabel = mContext.getString(positiveLabelId);
            return this;
        }

        public Builder setNegativeListener(OnClickListener negativeListener) {
            mNegativeListener = negativeListener;
            return this;
        }

        public Builder setPositiveListener(OnClickListener positiveListener) {
            mPositiveListener = positiveListener;
            return this;
        }

        public OurAlertDialog build() {
            OurAlertDialog ourAlertDialog = new OurAlertDialog(mContext);
            ourAlertDialog.mDialogType = mDialogType;
            ourAlertDialog.mMessage = mMessage;
            ourAlertDialog.mPositiveLabel = mPositiveLabel;
            ourAlertDialog.mPositiveListener = mPositiveListener;
            ourAlertDialog.mNegativeLabel = mNegativeLabel;
            ourAlertDialog.mNegativeListener = mNegativeListener;
            return ourAlertDialog;
        }
    }
}
