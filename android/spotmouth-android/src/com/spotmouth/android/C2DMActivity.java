package com.spotmouth.android;

import android.app.Activity;
import android.os.Bundle;

//from http://code.google.com/p/persey-group/source/browse/trunk/C2DM/src/com/perseygroup/apps/C2DMActivity.java?r=7
public class C2DMActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
}
