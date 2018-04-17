package com.oryx.utils;

import android.app.ProgressDialog;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;
import com.oryx.R;
import com.oryx.activity.login.LoginActivity;
import com.oryx.context.IServer;

public class GuiUtils {
    public static void showWorker(Runnable r, long delayMillis) {
        new android.os.Handler().postDelayed(r, delayMillis);
    }
}
