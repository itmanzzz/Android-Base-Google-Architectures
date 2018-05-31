package com.ilives.baseprj.common.util;

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

/**
 * -------------^_^-------------
 * ❖ com.ilives.baseprj.common.util
 * ❖ Created by IntelliJ IDEA
 * ❖ Author: Johnny
 * ❖ Date: 5/30/18
 * ❖ Time: 18:15
 * -------------^_^-------------
 **/
public class FontCache {
    private static HashMap<String, Typeface> fontCache = new HashMap<>();

    public static Typeface getTypeface(String fontname, Context context) {
        Typeface typeface = fontCache.get(fontname);

        if (typeface == null) {
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontname);
            } catch (Exception e) {
                return null;
            }

            fontCache.put(fontname, typeface);
        }

        return typeface;
    }
}
