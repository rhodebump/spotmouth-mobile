package com.spotmouth.android;

import android.os.Bundle;
import android.util.Log;
import com.google.android.c2dm.*;
import com.phonegap.DroidGap;

public class spotmouth extends DroidGap {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// super.loadUrl("file:///android_asset/www/index-gwt.html");
		
		Log.v("spotmouth", "loading index.html");
		
		super.loadUrl("file:///android_asset/www/index.html");
		Log.v("spotmouth", "calling doNotificationRegister");
		//doNotificationRegister();
		C2DMessaging.register(this /*the application context*/, DeviceRegistrar.SENDER_ID);
		Log.v("spotmouth", "C2DMessaging.register");
		

	}





    
	//good resource:  http://www.scotthawker.com/articles/c2dm.html
    
    
}
