package com.ilives.baseprj.common.views;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.ilives.baseprj.R;
import com.ilives.baseprj.databinding.DialogPopupBinding;


public class PopupDialog extends Dialog {

    DialogPopupBinding rootView;
    private Context mContext;
    private View.OnClickListener mOnclickListener;

    public PopupDialog(Context context) {
        super(context);
        this.mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.rootView = DataBindingUtil.setContentView((Activity) context, R.layout.dialog_popup);
        //getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //getWindow().setBackgroundDrawable(new ColorDrawable(mContext.getResources().getColor(R.color.colorTransparent)));
        setCancelable(true);
    }

    public PopupDialog setTitle(String pTitle){
        this.rootView.tvDialogMessageTitle.setText(pTitle);
        return this;
    }

    public PopupDialog setContent(String pContent){
        this.rootView.tvDialogMessageContent.setText(pContent);
        return this;
    }

    public PopupDialog setActionListener(View.OnClickListener pListener){
        mOnclickListener = pListener;
        this.rootView.btnDialogOk.setOnClickListener(mOnclickListener);
        this.rootView.btnDialogCancel.setOnClickListener(view -> dismiss());
        return this;
    }
}
