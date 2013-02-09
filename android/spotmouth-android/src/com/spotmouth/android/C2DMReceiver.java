package com.spotmouth.android;

import java.io.IOException;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.c2dm.C2DMBaseReceiver;

public class C2DMReceiver extends C2DMBaseReceiver {
	private static final String TAG = "C2DMReceiver";

	public C2DMReceiver() {
		// send the email address you set up earlier
		super(DeviceRegistrar.SENDER_ID);
	}

	@Override
	public void onRegistered(Context context, String registrationId)
			throws IOException {
		Log.d(TAG, "successfully registered with C2DM server; registrationId: "
				+ registrationId);
		DeviceRegistrar.registerWithServer(context, registrationId);
	}

	@Override
	public void onUnregistered(Context context) {
		Log.d(TAG, "successfully unregistered with C2DM server");
		String deviceRegistrationID = Prefs.getKey(context,
				DeviceRegistrar.KEY_DEVICE_REGISTRATION_ID);
		DeviceRegistrar.unregisterWithServer(context, deviceRegistrationID);
	}

	@Override
	public void onError(Context context, String errorId) {
		// notify the user
		Log.e(TAG, "error with C2DM receiver: " + errorId);

		if ("ACCOUNT_MISSING".equals(errorId)) {
			// no Google account on the phone; ask the user to open the account
			// manager and add a google account and then try again
			// TODO

		} else if ("AUTHENTICATION_FAILED".equals(errorId)) {
			// bad password (ask the user to enter password and try. Q: what
			// password - their google password or the sender_id password? ...)
			// i _think_ this goes hand in hand with google account; have them
			// re-try their google account on the phone to ensure it's working
			// and then try again
			// TODO

		} else if ("TOO_MANY_REGISTRATIONS".equals(errorId)) {
			// user has too many apps registered; ask user to uninstall other
			// apps and try again
			// TODO

		} else if ("INVALID_SENDER".equals(errorId)) {
			// this shouldn't happen in a properly configured system
			// TODO: send a message to app publisher?, inform user that service
			// is down

		} else if ("PHONE_REGISTRATION_ERROR".equals(errorId)) {
			// the phone doesn't support C2DM; inform the user
			// TODO

		} // else: SERVICE_NOT_AVAILABLE is handled by the super class and does
		// exponential backoff retries

	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		String message = intent.getStringExtra("message");
		String title = "spotmouth alert";
		int iconId = R.drawable.icon;
		// création de la notification :
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Notification notification = new Notification(iconId, message, System
				.currentTimeMillis());

		// création de l'activité à démarrer lors du clic :
		//Intent notifIntent = new Intent(context.getApplicationContext(),
			//	C2DMActivity.class);
		//PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
			//	notifIntent, 0);

		
		
		//i think this will open
		Intent notificationIntent = new Intent(this, spotmouth.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		
		
		//PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		
       // Intent notificationIntent = new Intent(this, C2DMActivity.class);
       // PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.defaults |= Notification.DEFAULT_SOUND;
		//notification.flags |= Notification.FLAG_AUTO_CANCEL; 
		// affichage de la notification dans le menu déroulant :
		notification.setLatestEventInfo(context, title, message, contentIntent);
// la notification
																// disparaitra
																// une fois
																// cliquée

		// lancement de la notification :
		notificationManager.notify(1, notification);

	}

	protected void onMessage3(Context context, Intent intent) {
		String message = null;

		Bundle extras = intent.getExtras();
		if (extras != null) {
			// parse the message and do something with it.
			// For example, if the server sent the payload as
			// "data.message=xxx", here you would have an extra called "message"
			message = extras.getString("message");
		}
		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		int iconId = R.drawable.icon;
		final int NOTIF_ID = 1234;

		// Notification notifyDetails = new
		// Notification(iconId,"spotmouth!",System.currentTimeMillis());
		// PendingIntent myIntent = PendingIntent.getActivity(context, 0, new
		// Intent(Intent.ACTION_VIEW), 0);
		// notifyDetails.setLatestEventInfo(context,"spotmouth", message,
		// myIntent);

		// notificationManager.notify(1, notifyDetails);
		Notification note = new Notification(R.drawable.icon,
				"spotmouth alert", System.currentTimeMillis());
		note.flags |= Notification.FLAG_AUTO_CANCEL;

		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, C2DMActivity.class), 0);

		// pendingIntent.putExtra("details", message);

		note.setLatestEventInfo(context, "spotmouth alert", message,
				pendingIntent);

		notificationManager.notify(NOTIF_ID, note);
		// http://mobile.tutsplus.com/tutorials/android/android-sdk-alert/

		// Log.i(getClass().getSimpleName(), "Sucessfully Changed Time");

		Toast.makeText(this, "this is a negative onClick", Toast.LENGTH_LONG)
				.show();

	}

	protected void onMessage2(Context context, Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			// parse the message and do something with it.
			// For example, if the server sent the payload as
			// "data.message=xxx", here you would have an extra called "message"
			String message = extras.getString("message");

			Log.i(TAG, "received message: " + message);
			// MainActivity.setMessage(message);

			// String message = intent.getExtras().getString("message");
			// message
			// AlertDialog alertDialog;
			// alertDialog = new AlertDialog.Builder(context).create();
			// alertDialog = new AlertDialog(context);

			// alertDialog.setTitle("spotmouth notification");
			// alertDialog.setMessage(message);
			// alertDialog.show();

			// String message = intent.getStringExtra("message");
			// String message = intent.getExtras().getString("message");
			String title = "spotmouth notification";
			int iconId = R.drawable.icon;
			// création de la notification :
			NotificationManager notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			Notification notification = new Notification(iconId, message,
					System.currentTimeMillis());

			CharSequence contentTitle = "My notification";
			CharSequence contentText = "Hello World!";
			Intent notificationIntent = new Intent(this, C2DMActivity.class);
			PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
					notificationIntent, 0);

			notification.setLatestEventInfo(context, contentTitle, contentText,
					contentIntent);

			notificationManager.notify(1, notification);

		}
	}

}
