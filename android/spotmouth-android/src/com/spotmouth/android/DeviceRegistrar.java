package com.spotmouth.android;
import java.net.URI;
import java.util.UUID;

import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.util.Log;
public class DeviceRegistrar {
    public static final String SENDER_ID = "rhodebump@gmail.com";
    public static final String KEY_DEVICE_REGISTRATION_ID = "deviceRegistrationID";
    private static final String APP_SERVER_URL = "www.spotmouth.com";
    private static final String REGISTER_URI = "/api/registerAndroidRegistration";
    private static final String UNREGISTER_URI = "/api/unregisterAndroidRegistration";

    public static String TAG = "DeviceRegistrar";
    
    public static void registerWithServer(Context context, String registrationId)
    {
   
    	DeviceUuidFactory duf = new DeviceUuidFactory(context);
    	 UUID id = duf.getDeviceUuid();
    	 String uuid = id.toString();
    	 
		//TelephonyManager operator = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		//String uuid = operator.getDeviceId();
            
            //connect with 3rd party server and register the device
            //TODO: do this on a thread
            try {
                    Log.d(TAG, "attempting to register with 3rd party app server...");
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    String url = "http://" + APP_SERVER_URL + REGISTER_URI + "?deviceuid=" + uuid + "&registrationid=" + registrationId + "&remove=1";
                    Log.d(TAG, "calling url=" + url);
                    request.setURI(new URI(url));
                    HttpResponse response = client.execute(request);
                    StatusLine status = response.getStatusLine();
                    if (status == null) 
                            throw new IllegalStateException("no status from request");
                    if (status.getStatusCode() != 200)
                            throw new IllegalStateException(status.getReasonPhrase());
            } catch (Exception e) {
                    Log.e(TAG, "unable to register: " + e.getMessage());
                    //TODO: notify the user
                    return;
            }
            
            //store for later
            Prefs.addKey(context, KEY_DEVICE_REGISTRATION_ID, registrationId);
            Log.d(TAG, "successfully registered device with 3rd party app server");
    }
    
    public static void unregisterWithServer(Context context, String registrationId)
    {
		TelephonyManager operator = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String uuid = operator.getDeviceId();

            //connect with 3rd party server and unregister the device
            //TODO: do this on a thread
            try {
                    Log.d(TAG, "attempting to unregister with 3rd party app server...");
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet();
                    request.setURI(new URI(APP_SERVER_URL + UNREGISTER_URI + "?deviceuid=" + uuid));
                    HttpResponse response = client.execute(request);
                    StatusLine status = response.getStatusLine();
                    if (status == null) 
                            throw new IllegalStateException("no status from request");
                    if (status.getStatusCode() != 200)
                            throw new IllegalStateException(status.getReasonPhrase());
            } catch (Exception e) {
                    Log.e(TAG, "unable to unregister: " + e.getMessage());
                    //TODO: notify the user
                    return;
            }
            
            //remove local key so app doesn't try to accidentally use it
            Prefs.removeKey(context, KEY_DEVICE_REGISTRATION_ID);
            Log.d(TAG, "succesfully unregistered with 3rd party app server");
    }
    
    
}
