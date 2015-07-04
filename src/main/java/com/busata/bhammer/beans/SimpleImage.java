package com.busata.bhammer.beans;


public class SimpleImage {
    private int mImage;
    private String mText;

    public SimpleImage(String text, int image) {
        this.mImage = image;
        this.mText = text;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        this.mText = text;
    }

    public int getImage() {
        return mImage;
    }

    public void setImage(int image) {
        this.mImage = image;
    }
}
