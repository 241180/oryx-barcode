package com.oryx.barcode.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.oryx.barcode.context.StaticServer;
import com.oryx.barcode.helper.ServiceHelper;
import com.oryx.barcode.service.AuthorizationService;

public class InstanceIdService extends FirebaseInstanceIdService {
    private static final String TAG = "InstanceIdService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        StaticServer.token = token;
        if(StaticServer.currentUser != null) {
            ServiceHelper.authorizationService.sendRegistrationToServer(this,StaticServer.host, StaticServer.currentUser.getEmail(), token);
        }
    }
}
