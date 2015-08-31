package com.busata.bhammer.utils;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.busata.bhammer.R;
import com.busata.bhammer.dialogs.BDatePicker;
import com.busata.bhammer.dialogs.BDialog;
import com.busata.bhammer.dialogs.BLDatePicker;
import com.busata.bhammer.dialogs.BNumberPicker;
import com.busata.bhammer.dialogs.BTimePicker;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;


public class BViewUtil {
    /**
     * Change height of a View with animation
     *
     * @param view       - The View
     * @param fromHeight - The current height
     * @param toHeight   - The next height
     * @return - The Animation
     */
    public static Animation changeHeightAnimation(final View view, final int fromHeight,
                                                  final int toHeight, int duration) {
        return changeHeightAnimation(view, fromHeight, toHeight, duration, false);
    }

    /**
     * Change height of a View with animation
     *
     * @param view       - The View
     * @param fromHeight - The current height
     * @param toHeight   - The next height
     * @return - The Animation
     */
    public static Animation changeHeightAnimation(final View view, final int fromHeight,
                                                  final int toHeight) {
        return changeHeightAnimation(view, fromHeight, toHeight, BUtil.DEFAULT_ANIM_DURATION, false);
    }

    /**
     * Change height of a View with animation
     *
     * @param view       - The View
     * @param fromHeight - The current height
     * @param toHeight   - The next height
     * @param invisible  - If View is invisible
     * @return - The Animation
     */
    public static Animation changeHeightAnimation(final View view, final int fromHeight,
                                                  final int toHeight, boolean invisible) {
        return changeHeightAnimation(view, fromHeight, toHeight, BUtil.DEFAULT_ANIM_DURATION, invisible);
    }

    /**
     * Change height of a View with animation
     *
     * @param view       - The View
     * @param fromHeight - The current height
     * @param toHeight   - The next height
     * @param duration   - The duration of the animation
     * @param invisible  - If View is invisible
     * @return - The Animation
     */
    public static Animation changeHeightAnimation(final View view, final int fromHeight,
                                                  final int toHeight, int duration, boolean invisible) {

        if (invisible) view.setVisibility(View.INVISIBLE);

        final boolean toMore = toHeight > fromHeight;
        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) view.getLayoutParams().height = toHeight;

                else if (toMore) {
                    int currentHeight = (int) (toHeight * interpolatedTime);
                    view.getLayoutParams().height = currentHeight > fromHeight ? currentHeight
                            : fromHeight;

                } else {
                    int difference = fromHeight - toHeight;
                    int currentHeight = fromHeight - (int) (difference * interpolatedTime);

                    view.getLayoutParams().height = currentHeight < fromHeight ? currentHeight
                            : fromHeight;
                }
                view.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        anim.setDuration(duration);
        return anim;
    }

    public static Toast makeToast(Context context, String message, int duration) {
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast, null);

        TextView text = (TextView) layout.findViewById(R.id.text);
        text.setText(message);

        Toast toast = new Toast(context);
        toast.setDuration(duration);
        toast.setView(layout);

        return toast;
    }

    public static void showNumberPicker(Activity activity, final TextView textView, int minValue, int maxValue) {
        int value;
        try {
            value = Integer.parseInt(textView.getText().toString());
        } catch (NumberFormatException e) {
            value = -1;
            e.printStackTrace();
        }
        showNumberPicker(activity, textView, minValue, maxValue, value);
    }

    public static void showNumberPicker(Activity activity, final TextView textView, int minValue, int maxValue, int value) {
        final BDialog dialog = new BDialog(activity);
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final BNumberPicker np = (BNumberPicker) inflater.inflate(R.layout.number_picker, null);

        np.setMinValue(minValue);
        np.setMaxValue(maxValue);
        np.setWrapSelectorWheel(false);
        np.setValue(value);
        np.setFormatter(new BNumberPicker.Formatter() {
            @Override
            public String format(int i) {
                return String.format("%02d", i);
            }
        });

        dialog.addView(np)
                .addButton(activity.getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                })
                .addButton(activity.getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        textView.setText(String.format("%02d", Integer.parseInt(String.valueOf(np.getValue()))));
                        dialog.dismiss();
                    }
                }, true).show();

    }

    public static void showTimePicker(Context context, final TextView textView, final int type, final boolean is24Hours) {
        showTimePicker(context, textView, type, is24Hours, false);
    }

    public static void showTimePicker(Context context, final TextView textView, final int type, final boolean is24Hours, boolean forceOld) {
        String value = textView.getText().toString();
        int hour = -1;
        int min = -1;

        if (!value.isEmpty()) {
            boolean pm = !is24Hours && "PM".equals(value.substring(value.length() - 2, value.length()));
            value = value.replace(" AM", "").replace(" PM", "");
            value = type == BTimePicker.MM ? "00:" + value : type == BTimePicker.HH ?
                    value + ":00" : value;

            String items[] = value.split(":");

            if (items.length > 1) {
                hour = Integer.parseInt(items[0]);
                if (!is24Hours) hour += pm ? 12 : 0;

                min = Integer.parseInt(items[1]);
            }
        }
        showTimePicker(context, textView, type, is24Hours, hour, min, forceOld);
    }

    public static void showTimePicker(Context context, final TextView textView, final int type, final boolean is24Hours, int hour, int min, boolean forceOld) {
        final BDialog dialog = new BDialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        dialog.addButton(context.getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        if (BUtil.checkVersion(Build.VERSION_CODES.LOLLIPOP) && !forceOld) {
            final TimePicker tp = (TimePicker) inflater.inflate(R.layout.time_lpicker, null);
            tp.setIs24HourView(is24Hours);
            if (hour >= 0) tp.setCurrentHour(hour);
            if (min >= 0) tp.setCurrentMinute(min);

            dialog.addView(tp)
                    .addButton(context.getString(R.string.ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int hour = (is24Hours ? tp.getCurrentHour() : (tp.getCurrentHour() > 12
                                    ? tp.getCurrentHour() - 12 : tp.getCurrentHour()));

                            String amPm = is24Hours ? "" : (tp.getCurrentHour() >= 12 ? " PM" : " AM");

                            switch (type) {
                                case BTimePicker.HH_MM:
                                    textView.setText(String.valueOf(BUtil.addZero(hour) + ":"
                                            + BUtil.addZero(tp.getCurrentMinute()) + amPm));
                                    break;
                                case BTimePicker.HH:
                                    textView.setText(String.valueOf(BUtil.addZero(hour) + amPm));
                                    break;
                                case BTimePicker.MM:
                                    textView.setText(String.valueOf(BUtil.addZero(tp.getCurrentMinute())));
                                    break;

                            }
                            dialog.dismiss();
                        }
                    }, true).show();

        } else {
            final BTimePicker tp = (BTimePicker) inflater.inflate(R.layout.time_picker, null);
            tp.setIs24HourView(is24Hours);
            if (hour >= 0) tp.setCurrentHour(hour);
            if (min >= 0) tp.setCurrentMinute(min);
            tp.setType(type);

            dialog.addView(tp)
                    .addButton(context.getString(R.string.ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            int hour = (is24Hours ? tp.getCurrentHour() : (tp.getCurrentHour() > 12
                                    ? tp.getCurrentHour() - 12 : tp.getCurrentHour()));

                            String amPm = is24Hours ? "" : (tp.getCurrentHour() >= 12 ? " PM" : " AM");

                            switch (type) {
                                case BTimePicker.HH_MM:
                                    textView.setText(String.valueOf(BUtil.addZero(hour) + ":"
                                            + BUtil.addZero(tp.getCurrentMinute()) + amPm));
                                    break;
                                case BTimePicker.HH:
                                    textView.setText(String.valueOf(BUtil.addZero(hour) + amPm));
                                    break;
                                case BTimePicker.MM:
                                    textView.setText(String.valueOf(BUtil.addZero(tp.getCurrentMinute())));
                                    break;

                            }
                            dialog.dismiss();
                        }
                    }, true).show();
        }
    }

    public static void showDatePicker(Activity activity, final TextView textView, final int type) {
        showDatePicker(activity, textView, type, false);
    }

    public static void showDatePicker(Activity activity, final TextView textView, final int type, boolean forceOld) {
        int dd = -1;
        int mm = -1;
        int yyyy = -1;

        String stringDate = textView.getText().toString();
        if (!stringDate.isEmpty()) {
            try {
                if (type == BDatePicker.MM_YYYY) {
                    String[] items = stringDate.split("/");
                    mm = Integer.parseInt(items[0]);
                    yyyy = Integer.parseInt(items[1]);

                } else {
                    DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(activity.getApplicationContext());
                    stringDate = type == BDatePicker.DD_MM ? stringDate + "/1900" :
                            type == BDatePicker.YYYY ? "01/01/" + stringDate : stringDate;

                    Date date = dateFormat.parse(stringDate);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(date.getTime());

                    dd = calendar.get(Calendar.DAY_OF_MONTH);
                    mm = calendar.get(Calendar.MONTH);
                    yyyy = calendar.get(Calendar.YEAR);
                }

            } catch (Exception e) {
                dd = -1;
                mm = -1;
                yyyy = -1;
                e.printStackTrace();
            }
        }
        showDatePicker(activity, textView, type, dd, mm, yyyy, forceOld);
    }

    /**
     * @param activity A Activity atual
     * @param textView O EditText que receberá o valor
     * @param type     1 - DD/MM/AAAA, 2 - MM/AAAA, 3 - DD/MM, 4 - AAAA
     */
    public static void showDatePicker(final Activity activity, final TextView textView, final int type, int dd, int mm, int yyyy, boolean forceOld) {
        final BDialog dialog = new BDialog(activity);
        dialog.setScrollVisible(false);

        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(activity.getApplicationContext());

        dialog.addButton(activity.getString(R.string.cancel), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        if (BUtil.checkVersion(Build.VERSION_CODES.LOLLIPOP) && !forceOld) {
            final BLDatePicker dp = (BLDatePicker) inflater.inflate(R.layout.date_lpicker, null);
            if (dd > 0 || mm > 0 || yyyy > 0) dp.updateDate(yyyy, mm, dd);
            dp.setSpinnersShown(true);

            dialog.addView(dp)
                    .addButton(activity.getString(R.string.ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Date date = new Date(dp.getYear() - 1900, dp.getMonth(), dp.getDayOfMonth());
                            String data = "";

                            switch (type) {
                                case BDatePicker.DD_MM_YYYY:
                                    data = dateFormat.format(date);
                                    break;
                                case BDatePicker.MM_YYYY:
                                    data = BUtil.addZero(dp.getMonth() + 1) + "/" + dp.getYear();
                                    break;
                                case BDatePicker.DD_MM:
                                    String date2 = dateFormat.format(new Date(dp.getYear(),
                                            dp.getMonth(), dp.getDayOfMonth()));
                                    data = date2.substring(0, date2.length() - 5);
                                    break;
                                case BDatePicker.YYYY:
                                    data = dp.getYear() + "";
                                    break;
                                default:
                                    break;
                            }

                            textView.setText(data);
                            dialog.dismiss();
                        }
                    }, true).show();


        } else {
            final BDatePicker dp = (BDatePicker) inflater.inflate(R.layout.date_picker, null);
            dp.setType(type);
            dialog.setPaddingContentView(0, 0, 0, 0);
            if (dd > 0 || mm > 0 || yyyy > 0) dp.updateDate(yyyy, mm, dd);

            dialog.addView(dp)
                    .addButton(activity.getString(R.string.ok), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Date date = new Date(dp.getYear() - 1900, dp.getMonth(), dp.getDayOfMonth());
                            String data = "";

                            switch (type) {
                                case BDatePicker.DD_MM_YYYY:
                                    data = dateFormat.format(date);
                                    break;
                                case BDatePicker.MM_YYYY:
                                    data = BUtil.addZero(dp.getMonth() + 1) + "/" + dp.getYear();
                                    break;
                                case BDatePicker.DD_MM:
                                    String date2 = dateFormat.format(new Date(dp.getYear(),
                                            dp.getMonth(), dp.getDayOfMonth()));
                                    data = date2.substring(0, date2.length() - 5);
                                    break;
                                case BDatePicker.YYYY:
                                    data = dp.getYear() + "";
                                    break;
                                default:
                                    break;
                            }

                            textView.setText(data);
                            dialog.dismiss();
                        }
                    }, true).show();
        }
        dialog.show();
    }

    public static void createDialogInfo(Activity mActivity, String title_text, String message) {
        final BDialog dialog = new BDialog(mActivity);
        dialog.setTitle(title_text)
                .setMessage(message)
                .addButton(mActivity.getString(R.string.ok), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                }, true).show();
    }

    public static BDialog createProgress(Context context) {
        BDialog dialog = new BDialog(context);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setAttributes(lp);
        dialog.setOnlyView();

        dialog.addView(new ProgressBar(context));
        dialog.setCancelable(false);
        return dialog;

    }

    public static void setCheckedSwitch(ViewGroup contentSwitch, SwitchCompat swEnabled, boolean enabled) {
        contentSwitch.removeView(swEnabled);
        swEnabled.setChecked(enabled);
        contentSwitch.addView(swEnabled);

    }

    public static void changeBackgroundAnim(Context context, final View view, int fromColor, int toColor) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                context.getResources().getColor(fromColor),
                context.getResources().getColor(toColor));
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                view.setBackgroundColor((Integer) animator.getAnimatedValue());
            }

        });
        colorAnimation.start();
    }

    public static int getMeasuredHeight(View parent, View view) {
        int widthSpec = View.MeasureSpec.makeMeasureSpec(parent.getWidth(), View.MeasureSpec.EXACTLY);
        int heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthSpec, heightSpec);
        return view.getMeasuredHeight();
    }

    public static void runBeforeSwipeDismiss(RecyclerView recyclerView, int position) {

        LinearLayoutManager llm = (LinearLayoutManager) recyclerView
                .getLayoutManager();

        int firstPosition = llm.findFirstVisibleItemPosition();

        int toPosition = position;
        if (firstPosition < position) {
            toPosition = position > 0 ? position - 1 : 0;

            View toView = llm.findViewByPosition(toPosition);
            if ((toView.getHeight() / 2) + toView.getY() <= 0) toPosition++;
        }

        recyclerView.smoothScrollToPosition(toPosition);
    }

    public static BDialog createProgress(Activity activity) {
        BDialog bDialog = new BDialog(activity);
        bDialog.addView(new ProgressBar(activity));
        bDialog.getWindow().getAttributes().width = ViewGroup.LayoutParams.WRAP_CONTENT;
        bDialog.getWindow().getAttributes().height = ViewGroup.LayoutParams.WRAP_CONTENT;
        bDialog.setOnlyView();

        return bDialog;
    }

    public static Animation changeTextSizeAnimation(final TextView textView, final int toSize,
                                                    int duration) {
        return changeTextSizeAnimation(textView, (int) textView.getTextSize(), toSize, duration);
    }

    /**
     * Change TextView's TextSize with animation
     *
     * @param textView The TextView
     * @param fromSize Current Size
     * @param toSize   Future Size
     * @param duration Duration
     * @return Animation
     */
    public static Animation changeTextSizeAnimation(final TextView textView, final int fromSize,
                                                    final int toSize, int duration) {
        final boolean toMore = toSize > fromSize;
        Animation anim = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, toSize);

                else if (toMore) {
                    int currentSize = (int) (toSize * interpolatedTime);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, currentSize > fromSize ? currentSize : fromSize);

                } else {
                    int difference = fromSize - toSize;
                    int currentSize = fromSize - (int) (difference * interpolatedTime);

                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, currentSize < fromSize ? currentSize : fromSize);
                }
                textView.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        anim.setDuration(duration);
        return anim;
    }

    /**
     * Chnage text color with animation
     * @param context - Context
     * @param textView  - The TextView
     * @param fromColor - From color id
     * @param toColor - To color Id
     * @param duration - The duration
     */

    public static void changeTextColorAnim(Context context, final TextView textView, int fromColor, int toColor, int duration) {
        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(),
                context.getResources().getColor(fromColor),
                context.getResources().getColor(toColor));
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                textView.setTextColor((Integer) animator.getAnimatedValue());
            }

        });
        colorAnimation.setDuration(duration);
        colorAnimation.start();

    }

    public static Animation showWithGrow(Context context, final View view) {
        return showWithGrow(context, view, 500);
    }

    public static Animation showWithGrow(Context context, final View view, int duration) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.grow_in);
        anim.setDuration(duration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return anim;
    }

    public static Animation hiddenWithGrow(Context context, final View view, final boolean isGone) {
        return hiddenWithGrow(context, view, isGone, 500);
    }

    public static Animation hiddenWithGrow(Context context, final View view, final boolean isGone, int duration) {
        Animation anim = AnimationUtils.loadAnimation(context, R.anim.grow_out);
        anim.setDuration(duration);
        anim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(isGone ? View.GONE : View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        return anim;
    }

    public static void scroolToTop(ScrollView scrollView) {
        int x = 0;
        int y = 0;
        ObjectAnimator xTranslate = ObjectAnimator.ofInt(scrollView, "scrollX", x);
        ObjectAnimator yTranslate = ObjectAnimator.ofInt(scrollView, "scrollY", y);

        AnimatorSet animators = new AnimatorSet();
        animators.setDuration(1000L);
        animators.playTogether(xTranslate, yTranslate);
        animators.start();
    }
}
