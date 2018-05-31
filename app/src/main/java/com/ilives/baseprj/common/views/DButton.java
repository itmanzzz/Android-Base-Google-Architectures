package com.ilives.baseprj.common.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;

import com.ilives.baseprj.R;
import com.ilives.baseprj.common.util.FontCache;

/**
 * Default App's button
 * Including font type
 */
public class DButton extends android.support.v7.widget.AppCompatButton {
    private static final String TAG = DButton.class.getSimpleName();

    public DButton(Context context) {
        super(context);
        Typeface customFont = FontCache.getTypeface("fonts/Gotham_Light.otf", context);
        setTypeface(customFont);
    }

    public DButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DButton, defStyleAttr, 0);
        Typeface customFont;
        int str = a.getInt(R.styleable.DButton_btnFront, 0);
        Log.d(TAG, "init: " + str);
        if (str == 1) {
            customFont = FontCache.getTypeface("fonts/Gotham_Light.otf", context);
        } else if (str == 2) {
            customFont = FontCache.getTypeface("fonts/Gotham_Bold.otf", context);
        } else {
            customFont = FontCache.getTypeface("fonts/Gotham_Light.otf", context);
        }
        setTypeface(customFont);

        a.recycle();
    }
}
