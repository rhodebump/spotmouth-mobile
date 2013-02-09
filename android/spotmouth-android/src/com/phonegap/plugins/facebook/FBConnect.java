/*
 * PhoneGap is available under *either* the terms of the modified BSD license *or* the
 * MIT License (2008). See http://opensource.org/licenses/alphabetical for full text.
 *
 * Copyright (c) 2005-2010, Nitobi Software Inc.
 * Copyright (c) 2010, IBM Corporation
 */
package com.phonegap.plugins.facebook;

import org.json.JSONArray;

import android.content.Intent;
import android.os.Bundle;

import com.facebook.android.Facebook;
import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;

public class FBConnect extends Plugin {
	public String callbackId;
	//http://developer.android.com/reference/android/webkit/WebViewClient.html
	/*
	 * Creating and setting a WebViewClient subclass. It will be called when things happen that impact the rendering of the content,
	 *  eg, errors or form submissions. You can also intercept URL loading here (via shouldOverrideUrlLoading()).
	 * 
	 */

    /**
     * Executes the request and returns PluginResult.
     *
     * @param action        The action to execute.
     * @param args          JSONArry of arguments for the plugin.
     * @param callbackId    The callback id used when calling back into JavaScript.
     * @return              A PluginResult object with a status and message.
     */
    public PluginResult execute(String action, JSONArray args, String callbackId) {
    	System.out.println("in FacebookLogin plugin, execute callbackId=" + callbackId);
    	this.callbackId = callbackId;
       // PluginResult.Status status = PluginResult.Status.OK;
        //String result = "";

        try {
            if (action.equals("connect")) {
            	this.dologin(args.getString(0));
                //result = this.showWebPage(args.getString(0), args.optBoolean(1));
            } else if (action.equals("logout")) {
            	Facebook mFacebook = new Facebook(args.getString(0));
            	mFacebook.logout(super.ctx);
            	
            }
			PluginResult r = new PluginResult(PluginResult.Status.NO_RESULT);
			r.setKeepCallback(true);
			return r;
        } catch (Exception e) {
        	e.printStackTrace();
            return new PluginResult(PluginResult.Status.ERROR);
        } 
    }

	//private String  appId;
	public void dologin(String  appId) {
		//this.appId =  appId;		
		// Display camera
		Intent intent = new Intent().setClass(this.ctx,com.facebook.android.LoginActivity.class);

        intent.putExtra("facebookAppId", appId);

        this.ctx.startActivityForResult((Plugin) this, intent,0);
        
	}
	
	
    /**
     * Identifies if action to be executed returns a value and should be run synchronocdusly.
     *
     * @param action    The action to execute
     * @return          T=returns value
     */
    public boolean isSynch(String action) {
        return false;
    }

    /**
     * Called by AccelBroker when listener is to be shut down.
     * Stop listener.
     */
    public void onDestroy() {
    }

    public String getStringProperty(String name, String defaultValue,Intent intent) {
    	Bundle bundle = intent.getExtras();
    	if (bundle == null) {
    		return defaultValue;
    	}
    	String p = bundle.getString(name);
    	if (p == null) {
    		return defaultValue;
    	}
    	return p;
    }
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		System.out.println("facebook onActivityResult ");

		String accesstoken = getStringProperty("accesstoken",null,intent);
		System.out.println("accesstoken= " + accesstoken);
		//accesstoken
		//sendJavascript("FBConnect.onConnect('" + accesstoken + "');");
		
		sendJavascript( "FBConnect._onLocationChange('" + accesstoken + "');");
		//ChildBrowser._onLocationChange
		//sendJavascript( "FBConnect._onLocationChange('test');");
		
		//sendJavascript("FBConnect.onConnect();");
		//this.success(new PluginResult(PluginResult.Status.OK,this.callbackId);
	}

}
