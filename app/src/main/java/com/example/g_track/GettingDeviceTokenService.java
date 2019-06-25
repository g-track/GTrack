package com.example.g_track;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessagingService;

public class GettingDeviceTokenService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        String deviceTOken = FirebaseInstanceId.getInstance().getToken();
        Log.i("My Device ","Token ISSS:"+deviceTOken);
    }
}
