package com.busata.bhammer.views.snackbar;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.ViewGroup;

/**
 * A handler for multiple {@link Snackbar}s
 */
public class SnackbarManager {

    private static final String TAG = SnackbarManager.class.getSimpleName();
    private static final Handler MAIN_THREAD = new Handler(Looper.getMainLooper());

    private static Snackbar currentSnackbar;

    private SnackbarManager() {
    }

    /**
     * Displays a  in the current {@link android.app.Activity}, dismissing
     * the current Snackbar being displayed, if any. Note that the Activity will be obtained from
     * the Snackbar's {@link android.content.Context}. If the Snackbar was created with
     * {@link android.app.Activity#getApplicationContext()} then you must explicitly pass the target
     * Activity using {@link #show(Snackbar, android.app.Activity, boolean)}
     *
     * @param snackbar instance of  to display
     */
    public static void show(@NonNull Snackbar snackbar) {
        try {
            show(snackbar, (Activity) snackbar.getContext(), false);
        } catch (ClassCastException e) {
            Log.e(TAG, "Couldn't get Activity from the Snackbar's Context. Try calling " +
                    "#show(Snackbar, Activity) instead", e);
        }
    }

    /**
     * Displays a  in the current {@link android.app.Activity}, dismissing
     * the current Snackbar being displayed, if any. Note that the Activity will be obtained from
     * the Snackbar's {@link android.content.Context}. If the Snackbar was created with
     * {@link android.app.Activity#getApplicationContext()} then you must explicitly pass the target
     * Activity using {@link #show(Snackbar, android.app.Activity, boolean)}
     *
     * @param snackbar instance of  to display
     */
    public static void show(@NonNull Snackbar snackbar, final boolean forceAnim) {
        try {
            show(snackbar, (Activity) snackbar.getContext(), forceAnim);
        } catch (ClassCastException e) {
            Log.e(TAG, "Couldn't get Activity from the Snackbar's Context. Try calling " +
                    "#show(Snackbar, Activity) instead", e);
        }
    }

    /**
     * Displays a  in the current {@link android.app.Activity}, dismissing
     * the current Snackbar being displayed, if any
     *
     * @param snackbar instance of  to display
     * @param activity target {@link android.app.Activity} to display the Snackbar
     */
    public static void show(@NonNull final Snackbar snackbar, @NonNull final Activity activity, final boolean forceAnim) {
        MAIN_THREAD.post(new Runnable() {
            @Override
            public void run() {
                if (currentSnackbar != null) {
                    if (currentSnackbar.isShowing() && !currentSnackbar.isDimissing()) {
                        currentSnackbar.dismissAnimation(false);
                        currentSnackbar.dismissByReplace();
                        currentSnackbar = snackbar;
                        currentSnackbar.showAnimation(forceAnim);
                        currentSnackbar.showByReplace(activity);

                        return;
                    }
                    currentSnackbar.dismiss();
                }
                currentSnackbar = snackbar;
                currentSnackbar.show(activity);
            }
        });
    }

    /**
     * Displays a  in the specified {@link android.view.ViewGroup}, dismissing
     * the current Snackbar being displayed, if any
     *
     * @param snackbar instance of  to display
     * @param parent   parent {@link android.view.ViewGroup} to display the Snackbar
     */
    public static void show(@NonNull Snackbar snackbar, @NonNull ViewGroup parent) {
        show(snackbar, parent, Snackbar.shouldUsePhoneLayout(snackbar.getContext()), false);
    }

    /**
     * Displays a  in the specified {@link android.view.ViewGroup}, dismissing
     * the current Snackbar being displayed, if any
     *
     * @param snackbar instance of  to display
     * @param parent   parent {@link android.view.ViewGroup} to display the Snackbar
     */
    public static void show(@NonNull Snackbar snackbar, @NonNull ViewGroup parent, final boolean forceAnim) {
        show(snackbar, parent, Snackbar.shouldUsePhoneLayout(snackbar.getContext()), forceAnim);
    }

    /**
     * Displays a  in the specified {@link android.view.ViewGroup}, dismissing
     * the current Snackbar being displayed, if any
     *
     * @param snackbar       instance of  to display
     * @param parent         parent {@link android.view.ViewGroup} to display the Snackbar
     * @param usePhoneLayout true: use phone layout, false: use tablet layout
     */
    public static void show(@NonNull final Snackbar snackbar, @NonNull final ViewGroup parent,
                            final boolean usePhoneLayout, final boolean forceAnim) {
        MAIN_THREAD.post(new Runnable() {
            @Override
            public void run() {
                if (currentSnackbar != null) {
                    if (currentSnackbar.isShowing() && !currentSnackbar.isDimissing()) {
                        currentSnackbar.dismissAnimation(false);
                        currentSnackbar.dismissByReplace();
                        currentSnackbar = snackbar;
                        currentSnackbar.showAnimation(forceAnim);
                        currentSnackbar.showByReplace(parent, usePhoneLayout);

                        return;
                    }
                    currentSnackbar.dismiss();
                }
                currentSnackbar = snackbar;
                currentSnackbar.show(parent, usePhoneLayout);
            }
        });
    }

    /**
     * Dismisses the  shown by this manager.
     */
    public static void dismiss() {
        if (currentSnackbar != null) {
            MAIN_THREAD.post(new Runnable() {
                @Override
                public void run() {
                    currentSnackbar.dismiss();
                }
            });
        }
    }

    /**
     * Return the current Snackbar
     */
    public static Snackbar getCurrentSnackbar() {
        return currentSnackbar;
    }
}
