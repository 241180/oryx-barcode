package com.oryx.barcode.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.oryx.barcode.context.StaticServer;
import com.oryx.barcode.helper.HttpHelper;
import com.oryx.barcode.model.ProductVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProductService extends Service {
    public static void findByCodeAndFormat(String host, String code, String format, final TextView descriptionField) {
        RequestParams rp = new RequestParams();
        rp.add("xcode", code);
        rp.add("xformat", format);

        String targetUrl = StaticServer.PRODUCT_GET_URL.replace("localhost", host);

        HttpHelper.post(targetUrl, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                    descriptionField.setText(serverResp.getString("description"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
            }
        });
    }

    public static void create(String host, String format, ProductVO productVO) {
        Gson gson = new Gson();
        String jsonStringProduct = gson.toJson(productVO);

        RequestParams rp = new RequestParams();
        rp.add("xformat", format);
        rp.add("product", jsonStringProduct);

        String targetUrl = StaticServer.PRODUCT_CREATE_URL.replace("localhost", host);

        HttpHelper.post(targetUrl, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONObject serverResp = new JSONObject(response.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline
            }
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
