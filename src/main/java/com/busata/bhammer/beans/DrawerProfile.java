package com.busata.bhammer.beans;

import android.app.Activity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.busata.bhammer.views.CircleImageView;
import com.busata.bhammer.R;

public class DrawerProfile {

    private CircleImageView mImage;
    private TextView mName;
    private TextView mEmail;
    private ViewGroup mBack;

    public DrawerProfile(Activity activity, String name, String email) {
        mImage = (CircleImageView) activity.findViewById(R.id.profileImage);
        mName = (TextView) activity.findViewById(R.id.profileName);
        mEmail = (TextView) activity.findViewById(R.id.profileEmail);

        mName.setText(name);
        mEmail.setText(email);
    }

    public DrawerProfile(Activity activity, String name, String email, int image) {
        mImage = (CircleImageView) activity.findViewById(R.id.profileImage);
        mName = (TextView) activity.findViewById(R.id.profileName);
        mEmail = (TextView) activity.findViewById(R.id.profileEmail);

        mImage.setImageResource(image);
        mName.setText(name);
        mEmail.setText(email);
    }

    public DrawerProfile(Activity activity, String name, String email, int image, int profileBack) {
        mImage = (CircleImageView) activity.findViewById(R.id.profileImage);
        mName = (TextView) activity.findViewById(R.id.profileName);
        mEmail = (TextView) activity.findViewById(R.id.profileEmail);
        mBack = (ViewGroup) activity.findViewById(R.id.header);


        mImage.setImageResource(image);
        mName.setText(name);
        mEmail.setText(email);
        mBack.setBackgroundResource(profileBack);
    }

    public DrawerProfile(int profileBack, Activity activity, String name, String email) {
        mName = (TextView) activity.findViewById(R.id.profileName);
        mEmail = (TextView) activity.findViewById(R.id.profileEmail);
        mBack = (ViewGroup) activity.findViewById(R.id.header);

        mName.setText(name);
        mEmail.setText(email);
        mBack.setBackgroundResource(profileBack);
    }
}