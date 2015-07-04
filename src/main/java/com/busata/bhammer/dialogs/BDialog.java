package com.busata.bhammer.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.busata.bhammer.R;


public class BDialog extends Dialog {
    private Context mContext;
    private TextView mTvTitle;
    private TextView mTvMessage;
    private LinearLayout mButtonLayout;
    private LinearLayout mContentView;
    private ViewGroup mContent;

    public BDialog(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public BDialog(Context context, String title, String message) {
        super(context);
        mContext = context;
        init();

        setTitle(title);
        setMessage(message);
    }

    public BDialog(Context context, String title) {
        super(context);
        mContext = context;
        init();

        setTitle(title);
    }

    private void init() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.bhammer_dialog);
        getWindow().setBackgroundDrawable(new ColorDrawable(0));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(lp);

        mTvTitle = (TextView) findViewById(R.id.title);
        mTvMessage = (TextView) findViewById(R.id.message);
        mButtonLayout = (LinearLayout) findViewById(R.id.buttonLayout);
        mContentView = (LinearLayout) findViewById(R.id.content_view);
        mContent = (ViewGroup) findViewById(R.id.content);

    }

    public BDialog addButton(String text, final View.OnClickListener listener){
        return addButton(text, listener, false);
    }

    public BDialog addButton(String text, final View.OnClickListener listener, boolean withColor) {
        Button btn = new Button(mContext);

        btn.setTypeface(null, Typeface.BOLD);
        btn.setBackgroundResource(R.drawable.btn_flat_selector);
        btn.setText(text);
        btn.setTextColor(getContext().getResources()
                .getColor(withColor ? R.color.accent_color : R.color.text_color));
        btn.setTextSize(14);
        btn.setOnClickListener(listener);

        btn.setMinimumWidth(mContext.getResources().getDimensionPixelOffset(R.dimen.btn_min_width_dialog));
        btn.setMinWidth(mContext.getResources().getDimensionPixelOffset(R.dimen.btn_min_width_dialog));

        btn.setMinimumHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        btn.setMinHeight(ViewGroup.LayoutParams.WRAP_CONTENT);


        mButtonLayout.addView(btn);
        return this;
    }

    public BDialog setTitle(String title) {
        mTvTitle.setText(title);

        if (title.length() > 0) mTvTitle.setVisibility(View.VISIBLE);
        else mTvTitle.setVisibility(View.GONE);

        return this;
    }

    public BDialog setMessage(String message) {
        mTvMessage.setText(message);

        if (message.length() > 0) mTvMessage.setVisibility(View.VISIBLE);
        else mTvMessage.setVisibility(View.GONE);

        return this;
    }

    /**
     * Added a view (EditText, Button, TExtView, ...)
     * @param view
     * @return
     */
    public BDialog addView(View view) {
        mContentView.addView(view);
        return this;
    }

    public BDialog addView(View view, LinearLayout.LayoutParams params) {
        mContentView.addView(view, params);
        return this;
    }

    public void setPaddingContentView(int left, int top, int right, int bottom){
        mContentView.setPadding(left, top, right, bottom);
    }

    public void setScrollVisible(boolean visible){
        findViewById(R.id.scroll).setVerticalScrollBarEnabled(visible);
    }

    public void setOnlyView(){
        mButtonLayout.setVisibility(View.GONE);
        mTvTitle.setVisibility(View.GONE);
    }
}