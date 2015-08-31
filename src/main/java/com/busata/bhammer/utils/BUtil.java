package com.busata.bhammer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.ScrollView;

import com.busata.bhammer.R;
import com.busata.bhammer.dialogs.BDialog;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;


public class BUtil {

    public static final int DEFAULT_ANIM_DURATION = 300;

    /**
     * Get a value by percentage of the Screen.
     *
     * @param activity - Current Activity
     * @param value    - The percentage
     * @return - A value represent the percentage
     */
    public static int getYPercentageFromScreen(Activity activity, float value) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return (int) (size.y * value) / 100;
    }

    public static boolean checkVersion(int version) {
        return android.os.Build.VERSION.SDK_INT >= version;
    }

    public static String addZero(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }

    public static void hiddenKeyboard(Activity activity) {
        activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public static void playNotification(Context context) {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
    }

    public static long getTimestamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static boolean isPrime(int number) {
        if (number == 1) return false;

        if (Math.abs(number) == 2) return true;

        if (number % 2 == 0) return false;

        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0)
                return false;
        }
        return true;
    }

    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static float spToPx(Context context, int sp){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static int getMeasuredHeight(View parent, View view) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec, heightSpec);
        return view.getMeasuredHeight();
    }

    public static BDialog createProgress(Activity activity) {
        BDialog bDialog = new BDialog(activity);
        bDialog.addView(new ProgressBar(activity));
        bDialog.getWindow().getAttributes().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        bDialog.getWindow().getAttributes().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        bDialog.setOnlyView();

        return bDialog;
    }
}