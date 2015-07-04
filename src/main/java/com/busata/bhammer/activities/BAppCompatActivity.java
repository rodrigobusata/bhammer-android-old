package com.busata.bhammer.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.busata.bhammer.R;

public class BAppCompatActivity extends AppCompatActivity {
    protected Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    /**
     * Set Toolbar
     *
     * @param homeAsUpEnabled - if has home as up enabled
     */
    protected void setToolBar(boolean homeAsUpEnabled) {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    /**
     * Set Toolbar
     *
     * @param title - The title to Toolbar
     */
    protected void setToolBar(String title) {
        setToolBar(title, true);
    }

    /**
     * Set Toolbar
     *
     * @param title           - The title to Toolbar
     * @param homeAsUpEnabled - if has home as up enabled
     */
    protected void setToolBar(String title, boolean homeAsUpEnabled) {
        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(homeAsUpEnabled);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}