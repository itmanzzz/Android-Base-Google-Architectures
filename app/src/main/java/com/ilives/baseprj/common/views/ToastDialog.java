package com.ilives.baseprj.common.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.ilives.baseprj.R;
import com.ilives.baseprj.common.models.ToastType;
import com.ilives.baseprj.databinding.DialogToastBinding;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.views
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 16:08
 * -------------^_^-------------
 **/
public class ToastDialog extends Dialog {

    DialogToastBinding rootView;
    private Handler mHandler = new Handler();
    private Runnable mDismissTask = () -> {
        try {
            dismiss();
        } catch (Exception e) {
            e.printStackTrace();
        }
    };

    public ToastDialog(Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.rootView = DataBindingUtil.setContentView((Activity) getContext(), R.layout.dialog_toast);
        Window window = getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);
        window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.getAttributes().windowAnimations = R.style.ToastDialogTheme;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            boolean hasMenuKey = ViewConfiguration.get(context).hasPermanentMenuKey();
            boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);

            if(!hasMenuKey && !hasBackKey) {
                // Do whatever you need to do, this device has a navigation bar
                int marginBottom = getNavigationBarHeight(); //getContext().getResources().getDimensionPixelSize(R.dimen.activity_vertical_margin);
                int margin = getContext().getResources().getDimensionPixelSize(R.dimen.activity_margin);
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.rootView.tvDialogMessageContent.getLayoutParams();
                layoutParams.setMargins(0, 0, 0, (marginBottom + margin) / 2);
                this.rootView.tvDialogMessageContent.setLayoutParams(layoutParams);
            }
        }
        setCancelable(true);
    }

    public void showMessage(String message, ToastType type, long delayTime) {
        int colorId = type == ToastType.SUCCESS ? R.color.toast_success :
                type == ToastType.ERROR ? R.color.toast_error :
                        type == ToastType.INFO ? R.color.toast_info : R.color.toast_warning;
        this.rootView.tvDialogMessageContent.setText(message);
        GradientDrawable bgDrawable = new GradientDrawable();
        bgDrawable.setCornerRadius(getContext().getResources().getDimension(R.dimen.dialog_corner));
        bgDrawable.setColor(getContext().getResources().getColor(colorId));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            this.rootView.tvDialogMessageContent.setBackground(bgDrawable);
        }
        show();
        mHandler.removeCallbacks(mDismissTask);
        mHandler.postDelayed(mDismissTask, delayTime);
    }

    private int getNavigationBarHeight() {
        Resources resources = getContext().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }
}
