/*
 * Copyright 2010 Facebook, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.facebook.android;

import android.content.Intent;
import android.os.Bundle;

import com.spotmouth.android.R;

import com.facebook.android.SessionEvents.AuthListener;
import com.facebook.android.SessionEvents.LogoutListener;
import com.phonegap.api.PhonegapActivity;
import com.phonegap.api.Plugin;


public class LoginActivity extends  PhonegapActivity { 

    // Your Facebook Application ID must be set before running this example
    // See http://www.facebook.com/developers/createapp.php
   // public static final String APP_ID = "175729095772478";

    private LoginButton mLoginButton;


    private Facebook mFacebook;
    //private AsyncFacebookRunner mAsyncRunner;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String appid = getStringProperty("facebookAppId",null);
        if (appid == null) {
            Util.showAlert(this, "Warning", "Facebook Applicaton ID must be " +
                    "specified before running this example: see Example.java");
        }

        setContentView(R.layout.main);
        mLoginButton = new LoginButton(getApplicationContext());
        
        	//(LoginButton) findViewById(R.id.login);

       // mLoginButton = new LoginButton();
        
       	mFacebook = new Facebook(appid);
       	//AsyncFacebookRunner	mAsyncRunner = new AsyncFacebookRunner(mFacebook);

        SessionStore.restore(mFacebook, this);
        SessionEvents.addAuthListener(new SampleAuthListener());
        SessionEvents.addLogoutListener(new SampleLogoutListener());
        mLoginButton.init(this, mFacebook);

        mLoginButton.dologin();
        
        
        

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        mFacebook.authorizeCallback(requestCode, resultCode, data);
    }

    public class SampleAuthListener implements AuthListener {

        public void onAuthSucceed() {
        		System.out.println("my token=" + mFacebook.getAccessToken());
        		//LoginActivity.this.getIntent().putExtra("accesstoken", mFacebook.getAccessToken());
        		Intent intent = new Intent();
        		//intent.setAction(Intent.);
               // Intent intent = new Intent("com.facebook.android.LoginActivity");
              //  intent.putExtra("facebookAppId", appId);
                intent.putExtra("accesstoken",mFacebook.getAccessToken()); 
                //stats.putString("weight", "190 lbs");
                //stats.putString("reach", "74\"");
                //setResult(RESULT_OK, "accesstoken", stats);

                //LoginActivity.this.setResult(0, LoginActivity.this);
                
                LoginActivity.this.setResult(RESULT_OK, intent) ;
                
        		LoginActivity.this.finish();   
        }

        public void onAuthFail(String error) {
           
        }
    }

    public class SampleLogoutListener implements LogoutListener {
        public void onLogoutBegin() {

        }

        public void onLogoutFinish() {

        }
    }



    public String getStringProperty(String name, String defaultValue) {
    	Bundle bundle = this.getIntent().getExtras();
    	if (bundle == null) {
    		return defaultValue;
    	}
    	String p = bundle.getString(name);
    	if (p == null) {
    		return defaultValue;
    	}
    	return p;
    }

	@Override
	public void addService(String arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendJavascript(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startActivityForResult(Plugin arg0, Intent arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}





}
