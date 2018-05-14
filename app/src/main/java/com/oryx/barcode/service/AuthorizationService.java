package com.oryx.barcode.service;

import android.content.Context;

import com.loopj.android.http.RequestParams;
import com.oryx.barcode.context.StaticServer;
import com.oryx.barcode.helper.HttpHelper;
import com.oryx.barcode.http.JsonHttpResponseHandlerExt;
import com.oryx.barcode.model.UserVO;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AuthorizationService {
    public UserVO connect(Context context, String host, String login, String password) {
        RequestParams rp = new RequestParams();
        rp.add("login", login);
        rp.add("password", password);

        String targetUrl = StaticServer.AUTH_CONNECT_URL.replace("localhost", host);

        JsonHttpResponseHandlerExt<UserVO> jsonHttpResponseHandlerExt = new JsonHttpResponseHandlerExt<UserVO>(UserVO.class) {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                StaticServer.logOut = false;
                super.onSuccess(statusCode, headers, response);
            }
        };

        HttpHelper.post(targetUrl, rp, jsonHttpResponseHandlerExt);
        return jsonHttpResponseHandlerExt.getObject();
    }

    public Boolean isConnected(Context context, String host, String userId) {
        RequestParams rp = new RequestParams();
        rp.add("userId", userId.toString());

        String targetUrl = StaticServer.AUTH_IS_CONNECTED_URL.replace("localhost", host);
        JsonHttpResponseHandlerExt<Boolean> jsonHttpResponseHandlerExt = new JsonHttpResponseHandlerExt<Boolean>(Boolean.class);

        HttpHelper.post(targetUrl, rp, jsonHttpResponseHandlerExt);

        return jsonHttpResponseHandlerExt.getObject();
    }

    public Boolean disConnect(Context context, String host, String userId) {
        RequestParams rp = new RequestParams();
        rp.add("userId", userId.toString());

        String targetUrl = StaticServer.AUTH_DISCONNECT_URL.replace("localhost", host);

        JsonHttpResponseHandlerExt<Boolean> jsonHttpResponseHandlerExt = new JsonHttpResponseHandlerExt<Boolean>(Boolean.class);

        HttpHelper.post(targetUrl, rp, jsonHttpResponseHandlerExt);

        StaticServer.currentUser = null;
        StaticServer.logOut = true;

        return jsonHttpResponseHandlerExt.getObject();
    }

    public UserVO create(Context context, String host, String firstName, String lastName, String email, String phone, String address, String password) {
        final UserVO[] userVO = {null};

        RequestParams rp = new RequestParams();
        rp.add("firstName", firstName);
        rp.add("lastName", lastName);
        rp.add("email", email);
        rp.add("phone", phone);
        rp.add("address", address);
        rp.add("password", password);

        String targetUrl = StaticServer.AUTH_SIGNUP_URL.replace("localhost", host);

        JsonHttpResponseHandlerExt<UserVO> jsonHttpResponseHandlerExt = new JsonHttpResponseHandlerExt<UserVO>(UserVO.class);
        HttpHelper.post(targetUrl, rp, jsonHttpResponseHandlerExt);

        return jsonHttpResponseHandlerExt.getObject();
    }

    public UserVO sendRegistrationToServer(Context context, String host, String email, String token) {
        final UserVO[] userVO = {null};

        RequestParams rp = new RequestParams();
        rp.add("email", email);
        rp.add("token", token);

        String targetUrl = StaticServer.SEND_REGISTRATION_T0_SERVER.replace("localhost", host);

        JsonHttpResponseHandlerExt<UserVO> jsonHttpResponseHandlerExt = new JsonHttpResponseHandlerExt<UserVO>(UserVO.class);
        HttpHelper.post(targetUrl, rp, jsonHttpResponseHandlerExt);

        return jsonHttpResponseHandlerExt.getObject();
    }
}
