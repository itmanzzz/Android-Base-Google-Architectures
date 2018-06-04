package com.ilives.baseprj.common.views;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.ilives.baseprj.R;


public class PopupDialog extends Dialog {
    private Context mContext;
    private View.OnClickListener mOnclickListener;

    public PopupDialog(Context context) {
        super(context);
        this.mContext = context;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialog_popup);
    }

    public PopupDialog setTitle(String pTitle){
        ((TextView)findViewById(R.id.tv_dialog_message_title)).setText(pTitle);
        return this;
    }

    public PopupDialog setContent(String pContent){
        ((TextView)findViewById(R.id.tv_dialog_message_content)).setText(pContent);
        return this;
    }

    public PopupDialog setActionListener(View.OnClickListener pListener){
        mOnclickListener = pListener;
        findViewById(R.id.btn_dialog_ok).setOnClickListener(mOnclickListener);
        findViewById(R.id.btn_dialog_cancel).setOnClickListener(view -> dismiss());
        return this;
    }
}

