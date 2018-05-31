package com.ilives.baseprj.common.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ilives.baseprj.app.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class Utils {

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static final SimpleDateFormat SERVER_DATE_FORMAT = new SimpleDateFormat(Constants.DATE_FORMAT_SERVER);
    public static final SimpleDateFormat CLIENT_DATE_FORMAT = new SimpleDateFormat(Constants.DATE_FORMAT_CLIENT);
    public static final SimpleDateFormat DATE_LIST_DATE_FORMAT = new SimpleDateFormat(Constants.DATE_FORMAT_FOR_DATE_LIST);

    /**
     * Dp to px int.
     *
     * @param pContext the p context
     * @param dp       the dp
     * @return the int
     */
    public static int dpToPx(Context pContext, int dp) {
        DisplayMetrics displayMetrics = pContext.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    /**
     * Px to dp int.
     *
     * @param pContext the p context
     * @param px       the px
     * @return the int
     */
    public static int pxToDp(Context pContext, int px) {
        DisplayMetrics displayMetrics = pContext.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    /**
     * Dp to px int.
     *
     * @param context the p context
     * @param sp      the dp
     * @return the int
     */
    public static int spToPx(Context context, float sp) {
        int px = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
        return px;
    }

    /**
     * Generate header string.
     *
     * @param pToken the p token
     * @return the string
     */
    public static String generateHeader(String pToken) {
        return "Bearer " + pToken;
    }

    /**
     * Request permissions for App
     *
     * @param activity    current activity
     * @param permissions permissions
     * @param requestCode request code
     */
    public static void requestPermission(Activity activity, String[] permissions, int requestCode) {
        List<String> notGrantedPermissions = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                notGrantedPermissions.add(permission);
            }
        }
        if (!notGrantedPermissions.isEmpty()) {
            String[] permissionRequest = new String[notGrantedPermissions.size()];
            notGrantedPermissions.toArray(permissionRequest);
            ActivityCompat.requestPermissions(activity, permissionRequest, requestCode);
        }
    }

    /**
     * Check the permission is granted or not
     *
     * @param activity    the activity
     * @param permissions the permissions
     * @return true if all permissions is granted, false in otherwise
     */
    public static boolean isPermissionsGranted(Context activity, String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    public static Bitmap makeBitmapFromDrawable(Context context, @DrawableRes int resId, int maxSize) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resId);
        int width, height;
        if (bitmap.getWidth() < bitmap.getHeight()) {
            height = Math.min(bitmap.getHeight(), maxSize);
            width = height * bitmap.getWidth() / bitmap.getHeight();
        } else {
            width = Math.min(bitmap.getWidth(), maxSize);
            height = width * bitmap.getHeight() / bitmap.getWidth();
        }
        Bitmap resizeBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        bitmap.recycle();
        return resizeBitmap;
    }

    public static Bitmap makeCircleBitmap(int color, boolean isFill, float strokeWidth, int size) {
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.TRANSPARENT);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(color);
        paint.setStyle(isFill ? Paint.Style.FILL : Paint.Style.STROKE);
        float radius = ((float) size - (isFill ? 0 : strokeWidth)) / 2;
        if (!isFill) {
            paint.setStrokeWidth(strokeWidth);
        }
        canvas.drawCircle(canvas.getWidth() / 2, canvas.getHeight() / 2, radius, paint);
        return bitmap;
    }

    /**
     * Hides the soft keyboard
     */
    public static void hideSoftKeyboard(Activity context) {
        if (context.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void forceHideSoftKeyboard(Context context) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0); // hide
        }
    }

    /**
     * Shows the soft keyboard
     */
    public static void showSoftKeyboard(View view) {
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            view.requestFocus();
            inputMethodManager.showSoftInput(view, 0);
        }
    }

    public static void forceHideKeyboard(Context context) {
        if (context instanceof Activity) {
            hideSoftKeyboard((Activity) context);
        } else {
            forceHideSoftKeyboard(context);
        }
    }

    public static Date getGtmDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
        dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

        //Local time zone
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

        //Time in GMT
        try {
            return dateFormatLocal.parse(dateFormatGmt.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String formatHeight(String height) {
        try {
            double value = Double.valueOf(height);
            return formatHeight(value);
        } catch (Exception e) {
            return height;
        }
    }

    public static String formatHeight(double height) {
        return String.format("%.2f", height);
    }


    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }

    public static boolean isValidLatLng(double lat, double lng) {
        if (lat < -90 || lat > 90) {
            return false;
        } else if (lng < -180 || lng > 180) {
            return false;
        }
        return true;
    }

    public static boolean isValidLatitude(double lat) {
        return lat >= -90 && lat <= 90;
    }

    public static boolean isValidLongitude(double lat) {
        return lat >= -180 && lat <= 180;
    }

    public static boolean isValidString(String value) {
        return value != null && !value.isEmpty();
    }

    public static int getAlphaColor(float alpha, int color) {
        return Color.argb((int) (alpha * 255), Color.red(color), Color.green(color), Color.blue(color));
    }

    public static int getColorWithoutAlpha(int color) {
        return Color.rgb(Color.red(color), Color.green(color), Color.blue(color));
    }

    public static float getAlphaOfColor(int color) {
        return (float) Color.alpha(color) / 255f;
    }

    /**
     * Stop the data processing thread
     */
    public static void stopDataHandler(HandlerThread handlerThread, Handler handler) {
        if (handlerThread != null && handlerThread.isAlive()) {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
            }
            if (Build.VERSION.SDK_INT >= 18) {
                handlerThread.quitSafely();
            } else {
                handlerThread.quit();
            }

            try {
                handlerThread.join(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap textAsBitmap(String text, float textSize, int textColor) {
        Paint paint = new Paint(ANTI_ALIAS_FLAG);
        paint.setTextSize(textSize);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        canvas.drawText(text, 0, baseline, paint);
        return image;
    }

    @Nullable
    public static Double parseNumber(EditText editText) {
        if (editText != null) {
            try {
                return Double.parseDouble(editText.getText().toString());
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }


    public static Double parseNumber(String numText, double defaultValue) {
        if (numText != null) {
            try {
                return Double.parseDouble(numText);
            } catch (Exception e) {
                return defaultValue;
            }
        }
        return defaultValue;
    }


    public static String convertLocalTime(String serverTime) {
        try {
            SERVER_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = SERVER_DATE_FORMAT.parse(serverTime);
            CLIENT_DATE_FORMAT.setTimeZone(TimeZone.getDefault());
            return CLIENT_DATE_FORMAT.format(date);
        } catch (Exception e) {
            Log.e("Datum", "Fail to parse time string");
        }
        return null;
    }

    public static String convertDateListName(String serverTime) {
        try {
            SERVER_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = SERVER_DATE_FORMAT.parse(serverTime);
            DATE_LIST_DATE_FORMAT.setTimeZone(TimeZone.getDefault());
            return DATE_LIST_DATE_FORMAT.format(date);
        } catch (Exception e) {
            Log.e("Datum", "Fail to parse time string");
        }
        return serverTime;
    }

    public static String formatTime(int timeInSecond) {
        String timeString = "";
        int hour = timeInSecond / 3600;
        if (hour > 0) {
            timeString += String.format("%02d", hour) + ":";
        }
        int minute = (timeInSecond % 3600) / 60;
        timeString += String.format("%02d", minute) + ":" + String.format("%02d", timeInSecond % 60);
        return timeString;
    }

    public static String getUtcTime() {
        final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT_SERVER + " z");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return dateFormat.format(Calendar.getInstance().getTime()).replace(" UTC", "");
    }


    /**
     * Tests if the line segment from {@code (x1,y1)} to
     * {@code (x2,y2)} intersects the line segment from {@code (x3,y3)}
     * to {@code (x4,y4)}.
     *
     * @param x1 the X coordinate of the start point of the first
     *           specified line segment
     * @param y1 the Y coordinate of the start point of the first
     *           specified line segment
     * @param x2 the X coordinate of the end point of the first
     *           specified line segment
     * @param y2 the Y coordinate of the end point of the first
     *           specified line segment
     * @param x3 the X coordinate of the start point of the second
     *           specified line segment
     * @param y3 the Y coordinate of the start point of the second
     *           specified line segment
     * @param x4 the X coordinate of the end point of the second
     *           specified line segment
     * @param y4 the Y coordinate of the end point of the second
     *           specified line segment
     * @return <code>true</code> if the first specified line segment
     * and the second specified line segment intersect
     * each other; <code>false</code> otherwise.
     * @since 1.2
     */
    public static boolean linesIntersect(double x1, double y1,
                                         double x2, double y2,
                                         double x3, double y3,
                                         double x4, double y4) {
        return ((relativeCCW(x1, y1, x2, y2, x3, y3) *
                relativeCCW(x1, y1, x2, y2, x4, y4) <= 0)
                && (relativeCCW(x3, y3, x4, y4, x1, y1) *
                relativeCCW(x3, y3, x4, y4, x2, y2) <= 0));
    }

    /**
     * Returns an indicator of where the specified point
     * {@code (px,py)} lies with respect to the line segment from
     * {@code (x1,y1)} to {@code (x2,y2)}.
     * The return value can be either 1, -1, or 0 and indicates
     * in which direction the specified line must pivot around its
     * first end point, {@code (x1,y1)}, in order to point at the
     * specified point {@code (px,py)}.
     * <p>A return value of 1 indicates that the line segment must
     * turn in the direction that takes the positive X axis towards
     * the negative Y axis.  In the default coordinate system used by
     * Java 2D, this direction is counterclockwise.
     * <p>A return value of -1 indicates that the line segment must
     * turn in the direction that takes the positive X axis towards
     * the positive Y axis.  In the default coordinate system, this
     * direction is clockwise.
     * <p>A return value of 0 indicates that the point lies
     * exactly on the line segment.  Note that an indicator value
     * of 0 is rare and not useful for determining colinearity
     * because of floating point rounding issues.
     * <p>If the point is colinear with the line segment, but
     * not between the end points, then the value will be -1 if the point
     * lies "beyond {@code (x1,y1)}" or 1 if the point lies
     * "beyond {@code (x2,y2)}".
     *
     * @param x1 the X coordinate of the start point of the
     *           specified line segment
     * @param y1 the Y coordinate of the start point of the
     *           specified line segment
     * @param x2 the X coordinate of the end point of the
     *           specified line segment
     * @param y2 the Y coordinate of the end point of the
     *           specified line segment
     * @param px the X coordinate of the specified point to be
     *           compared with the specified line segment
     * @param py the Y coordinate of the specified point to be
     *           compared with the specified line segment
     * @return an integer that indicates the position of the third specified
     * coordinates with respect to the line segment formed
     * by the first two specified coordinates.
     * @since 1.2
     */
    public static int relativeCCW(double x1, double y1,
                                  double x2, double y2,
                                  double px, double py) {
        x2 -= x1;
        y2 -= y1;
        px -= x1;
        py -= y1;
        double ccw = px * y2 - py * x2;
        if (ccw == 0.0) {
            // The point is colinear, classify based on which side of
            // the segment the point falls on.  We can calculate a
            // relative value using the projection of px,py onto the
            // segment - a negative value indicates the point projects
            // outside of the segment in the direction of the particular
            // endpoint used as the origin for the projection.
            ccw = px * x2 + py * y2;
            if (ccw > 0.0) {
                // Reverse the projection to be relative to the original x2,y2
                // x2 and y2 are simply negated.
                // px and py need to have (x2 - x1) or (y2 - y1) subtracted
                //    from them (based on the original values)
                // Since we really want to get a positive answer when the
                //    point is "beyond (x2,y2)", then we want to calculate
                //    the inverse anyway - thus we leave x2 & y2 negated.
                px -= x2;
                py -= y2;
                ccw = px * x2 + py * y2;
                if (ccw < 0.0) {
                    ccw = 0.0;
                }
            }
        }
        return (ccw < 0.0) ? -1 : ((ccw > 0.0) ? 1 : 0);
    }

    public static String readableFileSize(long size) {
        if (size <= 0) return "0";
        final String[] units = new String[]{"B", "kB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }


    public static void hideNavigationBar(Dialog dialog) {
        if (dialog.getWindow() != null && dialog.getWindow().getDecorView() != null) {
            if (Build.VERSION.SDK_INT < 19) { // lower api
                View v = dialog.getWindow().getDecorView();
                v.setSystemUiVisibility(View.GONE);
            } else {
                //for new api versions.
                View decorView = dialog.getWindow().getDecorView();
                int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
                decorView.setSystemUiVisibility(uiOptions);
            }
        }
    }

    public static String readTextFileFromAssets(Context context, String fileName) {
        BufferedReader reader = null;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));

            // do reading, usually loop until end of file reading
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                //process line
                builder.append(mLine);
            }
        } catch (IOException e) {
            //log the exception
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
        }
        return builder.toString();
    }

    public static void putParamsToJson(@NonNull JSONObject object, String key, Object value) {
        if (value != null) {
            try {
                object.put(key, value);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isJson(String str) {
        try {
            final JSONObject obj = new JSONObject(str);
        } catch (JSONException e) {
            return false;
        }
        return true;
    }
}
