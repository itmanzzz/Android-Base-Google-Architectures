package com.ilives.baseprj.common.util;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.util
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/31/18
 * ❖ Time: 16:46
 * -------------^_^-------------
 **/
public class AppUtils {
    public static void startActivity(@NonNull Context context, Class clazz, Bundle bundle) {
        Intent intent = new Intent(context, clazz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }
}
