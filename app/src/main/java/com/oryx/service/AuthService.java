package com.oryx.service;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.oryx.context.IServer;
import com.oryx.context.IUser;
import com.oryx.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import cz.msebera.android.httpclient.Header;

public class AuthService {
    public static IUser connect(String host, String login, String password){
        RequestParams rp = new RequestParams();
        rp.add("login", login);
        rp.add("password", password);

        String targetUrl = IServer.LOGIN_CONNECT_URL.replace("localhost", host);

        HttpUtils.post(targetUrl, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                Gson gson = new Gson();
                IServer.currentUser = gson.fromJson(response.toString(), IUser.class);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                IServer.currentUser = null;
            }

            @Override
            protected Object parseResponse(byte[] responseBody) throws JSONException {
                return super.parseResponse(responseBody);
            }
        });

        return null;
    }

    public static boolean isConnected(String login, String password){
        return true;
    }

    public static boolean isConnected(UUID userId){
        return true;
    }

    public static boolean disConnect(UUID userId){
        return true;
    }
}
