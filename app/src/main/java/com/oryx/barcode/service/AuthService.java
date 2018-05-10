package com.oryx.barcode.service;

import android.util.Log;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.oryx.barcode.context.IServer;
import com.oryx.barcode.context.IUser;
import com.oryx.barcode.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import cz.msebera.android.httpclient.Header;

public class AuthService {
    public static IUser connect(String host, String login, String password) {
        RequestParams rp = new RequestParams();
        rp.add("login", login);
        rp.add("password", password);

        String targetUrl = IServer.AUTH_CONNECT_URL.replace("localhost", host);

        HttpUtils.post(targetUrl, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                Gson gson = new Gson();
                IServer.currentUser = gson.fromJson(response.toString(), IUser.class);
                IServer.logOut = false;
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

    public static boolean isConnected(String host, UUID userId) {
        RequestParams rp = new RequestParams();
        rp.add("userId", userId.toString());

        String targetUrl = IServer.AUTH_IS_CONNECTED_URL.replace("localhost", host);

        HttpUtils.post(targetUrl, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            protected Object parseResponse(byte[] responseBody) throws JSONException {
                return super.parseResponse(responseBody);
            }
        });

        return true;
    }

    public static boolean disConnect(String host, UUID userId) {
        RequestParams rp = new RequestParams();
        rp.add("userId", userId.toString());

        String targetUrl = IServer.AUTH_DISCONNECT_URL.replace("localhost", host);

        HttpUtils.post(targetUrl, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                IServer.currentUser = null;
                IServer.logOut = true;
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            protected Object parseResponse(byte[] responseBody) throws JSONException {
                return super.parseResponse(responseBody);
            }
        });

        return true;
    }

    public static IUser signup(String host, String firstName, String lastName, String email, String phone, String address, String password) {
        RequestParams rp = new RequestParams();
        rp.add("firstName", firstName);
        rp.add("lastName", lastName);
        rp.add("email", email);
        rp.add("phone", phone);
        rp.add("address", address);
        rp.add("password", password);

        String targetUrl = IServer.AUTH_SIGNUP_URL.replace("localhost", host);

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

    public static IUser sendRegistrationToServer(String host, String email, String token) {
        RequestParams rp = new RequestParams();
        rp.add("email", email);
        rp.add("token", token);

        String targetUrl = IServer.SEND_REGISTRATION_T0_SERVER.replace("localhost", host);

        HttpUtils.post(targetUrl, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                Gson gson = new Gson();
                //IServer.currentUser = gson.fromJson(response.toString(), IUser.class);
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
}
