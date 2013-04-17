package com.nerdery.android.codechallenge.presentation.activity;

import com.nerdery.android.codechallenge.R;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * Main activity of the application
 *
 * @author areitz
 */
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
