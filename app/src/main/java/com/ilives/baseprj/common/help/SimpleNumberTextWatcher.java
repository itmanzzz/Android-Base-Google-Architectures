package com.ilives.baseprj.common.help;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.help
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 17:10
 * -------------^_^-------------
 **/
public abstract class SimpleNumberTextWatcher implements TextWatcher {

    protected double mLastNumber = 0;
    private double mUpdateNumber;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private Runnable mNotifyTask = new Runnable() {
        @Override
        public void run() {
            if (mUpdateNumber != mLastNumber) {
                onNumberChanged(mUpdateNumber);
                mLastNumber = mUpdateNumber;
            }
        }
    };

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        try {
            mUpdateNumber = Double.parseDouble(s.toString());
            mHandler.removeCallbacks(mNotifyTask);
            mHandler.postDelayed(mNotifyTask, 500);
        } catch (NumberFormatException ne) {
            ne.printStackTrace();
        }
    }

    public abstract void onNumberChanged(double number);
}

