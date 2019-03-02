package com.example.u14.msg;

import android.app.Application;
import android.util.Log;

import com.onesignal.OSNotification;
import com.onesignal.OSNotificationAction;
import com.onesignal.OSNotificationOpenResult;
import com.onesignal.OneSignal;

import org.json.JSONObject;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        OneSignal.startInit(this)
                .autoPromptLocation(false)
                .setNotificationReceivedHandler(new MyNotificationReceivedHandler())
                .setNotificationOpenedHandler(new MyNotificationOpenedHandler())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }

    private class MyNotificationReceivedHandler implements OneSignal.NotificationReceivedHandler {
        @Override
        public void notificationReceived(OSNotification notification) {
            JSONObject data = notification.payload.additionalData;
            String notificationId = notification.payload.notificationID;

            Log.i("OneSignal", "notificationReceived: "+notificationId);

            if(data != null) {
                String customKey = data.optString("customkey",null);
                Log.i("OneSignal", "customKey: "+customKey);
            }


        }
    }

    private class MyNotificationOpenedHandler implements OneSignal.NotificationOpenedHandler {
        @Override
        public void notificationOpened(OSNotificationOpenResult result) {
            OSNotificationAction.ActionType actionType = result.action.type;
            JSONObject data = result.notification.payload.additionalData;
            String id = result.notification.payload.notificationID;
            Log.i("OneSignal", "notificationOpened: "+id);

        }
    }
}
