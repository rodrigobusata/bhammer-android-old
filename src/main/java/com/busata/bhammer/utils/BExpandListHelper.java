package com.busata.bhammer.utils;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;

import com.busata.bhammer.R;
import com.busata.bhammer.beans.BasicExpandedItem;

import java.util.List;


public class BExpandListHelper {

    protected Context mContext;
    protected boolean mOnlyOne;
    protected RecyclerView mRecyclerView;
    protected int ANIM_DURATION = 300;

    public BExpandListHelper(Context context, RecyclerView recyclerView) {
        mContext = context;
        mRecyclerView = recyclerView;
    }

    public void toggle(final View contentAll, final View view, final int position, List<? extends BasicExpandedItem> items) {
        if (mOnlyOne) collapseOthers(position, items);

        BasicExpandedItem item = items.get(position);
        item.setOpened(!item.isOpened());

        if (item.isOpened()) expand(contentAll, view);
        else collapse(contentAll, view);
    }

    protected void collapseOthers(int position, List<? extends BasicExpandedItem> items) {
        for (int index = 0; index < mRecyclerView.getChildCount(); ++index)
            if (index != (position - ((LinearLayoutManager) mRecyclerView.getLayoutManager()).findFirstVisibleItemPosition()))
                collapse(mRecyclerView.getChildAt(index).findViewById(R.id.contentAll),
                        mRecyclerView.getChildAt(index).findViewById(R.id.content));

        for (BasicExpandedItem item : items)
            if (position == -1 || item != items.get(position)) item.setOpened(false);
    }

    protected void collapse(final View contentAll, final View v) {
        if (v.getVisibility() == View.VISIBLE) {
            BViewUtil.changeBackgroundAnim(mContext, contentAll, R.color.flat_pressed,
                    android.R.color.transparent);

            int initialHeight = v.getMeasuredHeight();
            Animation anim = BViewUtil.changeHeightAnimation(v, initialHeight, 0, ANIM_DURATION);
            anim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    v.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }
            });
            v.startAnimation(anim);
        }
    }

    public void collapseAll(List<? extends BasicExpandedItem> items) {
        collapseOthers(-1, items);
    }

    protected void expand(final View contentAll, final View v) {
        if (v.getVisibility() == View.GONE) {
            v.setVisibility(View.VISIBLE);
            v.requestLayout();

            BViewUtil.changeBackgroundAnim(mContext, contentAll, android.R.color.transparent,
                    R.color.flat_pressed);

            int targetHeight = BUtil.getMeasuredHeight(contentAll.findViewById(R.id.header), v);

            v.getLayoutParams().height = 0;
            v.setVisibility(View.VISIBLE);
            v.startAnimation(BViewUtil.changeHeightAnimation(v, 0, targetHeight, ANIM_DURATION));
        }
    }

    public void setOnlyOne(boolean onlyOne) {
        mOnlyOne = onlyOne;
    }
}