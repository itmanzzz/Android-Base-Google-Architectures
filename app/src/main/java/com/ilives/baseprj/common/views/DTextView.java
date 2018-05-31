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
 * Default App's TextView
 * Including font type
 */
public class DTextView extends android.support.v7.widget.AppCompatTextView {
    private static final String TAG = DTextView.class.getSimpleName();

    public DTextView(Context context) {
        super(context);
        Typeface customFont = FontCache.getTypeface("fonts/Gotham_Light.otf", context);
        setTypeface(customFont);
    }

    public DTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DTextView, defStyleAttr, 0);
        Typeface customFont;
        int str = a.getInt(R.styleable.DTextView_txtFont, 0);
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
