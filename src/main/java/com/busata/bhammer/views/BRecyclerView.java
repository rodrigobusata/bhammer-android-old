package com.busata.bhammer.views;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;

import com.busata.bhammer.R;

public class BRecyclerView extends RecyclerView {

    private boolean mWithAnim;
    private Context mContext;

    public BRecyclerView(Context context) {
        super(context);
        mContext = context;
    }

    public BRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public BRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    protected void attachLayoutAnimationParameters(View child, ViewGroup.LayoutParams params, int index, int count) {

        if (mWithAnim) {
            if (getAdapter() != null && getLayoutManager() instanceof GridLayoutManager) {

                GridLayoutAnimationController.AnimationParameters animationParams =
                        (GridLayoutAnimationController.AnimationParameters) params.layoutAnimationParameters;

                if (animationParams == null) {
                    animationParams = new GridLayoutAnimationController.AnimationParameters();
                    params.layoutAnimationParameters = animationParams;
                }

                int columns = ((GridLayoutManager) getLayoutManager()).getSpanCount();

                animationParams.count = count;
                animationParams.index = index;
                animationParams.columnsCount = columns;
                animationParams.rowsCount = count / columns;

                final int invertedIndex = count - 1 - index;
                animationParams.column = columns - 1 - (invertedIndex % columns);
                animationParams.row = animationParams.rowsCount - 1 - invertedIndex / columns;


            } else {
                super.attachLayoutAnimationParameters(child, params, index, count);
            }
        }
    }

    public void setWithAnim(boolean withAnim) {
        mWithAnim = withAnim;
        if (mWithAnim)
            setLayoutAnimation(AnimationUtils.loadLayoutAnimation(mContext, R.anim.grow_in_anim));
    }

    public boolean isWithAnim() {
        return mWithAnim;
    }
}