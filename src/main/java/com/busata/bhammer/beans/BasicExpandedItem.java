package com.busata.bhammer.beans;

public class BasicExpandedItem {
    protected boolean mOpened;
    private int mContentHeight;

    public boolean isOpened() {
        return mOpened;
    }

    public void setOpened(boolean opened) {
        this.mOpened = opened;
    }

    public int getContentHeight() {
        return mContentHeight;
    }

    public void setContentHeight(int contentHeight) {
        this.mContentHeight = contentHeight;
    }
}
