package com.oryx.barcode.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class JsonHttpResponseHandlerExt<E> extends JsonHttpResponseHandler {
    final class DateTypeAdapter
            extends TypeAdapter<Date> {

        private DateTypeAdapter() {
        }

        @Override
        public void write(JsonWriter out, Date value) throws IOException {
            out.value(value != null?value.getTime():null);
        }

        @Override
        public Date read(JsonReader in) throws IOException {
            if(in!=null && in.peek() != JsonToken.NULL){
                return new Date(in.nextLong());
            }else if(in.peek() == JsonToken.NULL){
                in.nextNull();
            }

            return null;
        }
    }

    final class BooleanTypeAdapter
            extends TypeAdapter<Boolean> {

        private BooleanTypeAdapter() {
        }

        @Override
        public void write(JsonWriter out, Boolean value) throws IOException {
            out.value(value);
        }

        @Override
        public Boolean read(JsonReader in) throws IOException {
            if(in!=null && in.peek() != JsonToken.NULL){
                return new Boolean(in.nextBoolean());
            }else if(in.peek() == JsonToken.NULL){
                in.nextNull();
            }

            return null;
        }
    }

    private final Class<E> clazz;
    private boolean isFinished = false;
    private E object;
    private static GsonBuilder gsonBuilder;
    private static Gson gson;

    protected Gson getGson(){
        if(this.gson == null){
            this.gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Date.class, new DateTypeAdapter());
            gsonBuilder.registerTypeAdapter(Date.class, new BooleanTypeAdapter());
            this.gson = gsonBuilder.serializeNulls().create();
        }

        return this.gson;
    }

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
        return getGson().fromJson(json, classOfT);
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
