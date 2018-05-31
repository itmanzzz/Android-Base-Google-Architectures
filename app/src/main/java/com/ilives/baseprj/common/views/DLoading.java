package com.ilives.baseprj.common.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.ilives.baseprj.R;


/**
 * ***************************************
 * ❖ INNOVATION LIVES
 * ❖ Developed by F-Society
 * ❖ Created by Johnny
 * ***************************************
 **/

@SuppressWarnings("ConstantConditions")
public class DLoading extends Dialog {

    private int countLoading = 0;
    private Activity mContext;

    public DLoading(Context context) {
        super(context);
        this.mContext = (Activity) context;
        initLoadingProgress();
    }

    private void initLoadingProgress() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.common_loading);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.setCancelable(false);
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        if (mContext.isFinishing()) {
            return;
        }
        if (countLoading == 0) {
            super.show();
        }
        countLoading++;
    }

    @Override
    public void dismiss() {
        if (mContext.isFinishing()) {
            return;
        }
        countLoading--;
        if (countLoading > 0 || !super.isShowing()) return;
        super.dismiss();
    }
}
