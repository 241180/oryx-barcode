package com.oryx.barcode.http;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class JsonHttpResponseHandlerExt<E> extends JsonHttpResponseHandler {
    private final Class<E> clazz;
    private boolean isFinished = false;
    private E object;
    public JsonHttpResponseHandlerExt(Class<E> clazz) {
        this.clazz = clazz;
    }

    public E getObject() {
        while (!isFinished());
        return object;
    }

    public void setObject(E object) {
        this.object = object;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    private <T> E fromJson(String json, Class<E> classOfT) throws JsonSyntaxException {
        Gson gson = new Gson();
        return gson.fromJson(json, classOfT);
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        super.onSuccess(statusCode, headers, response);
        this.object = (E) fromJson(response.toString(), clazz);
        isFinished = true;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        super.onSuccess(statusCode, headers, response);
        isFinished = true;
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        isFinished = true;
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
        super.onFailure(statusCode, headers, throwable, errorResponse);
        isFinished = true;
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
        isFinished = true;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, String responseString) {
        super.onSuccess(statusCode, headers, responseString);
        isFinished = true;
    }
}
