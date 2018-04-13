package com.oryx.service;

import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.oryx.context.IServer;
import com.oryx.model.ProductVO;
import com.oryx.utils.HttpUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProductService {
    public static void getProduct(String host, String code, String format, final TextView descriptionField) {
        RequestParams rp = new RequestParams();
        rp.add("xcode", code);
        rp.add("xformat", format);

        String targetUrl = IServer.PRODUCT_GET_URL.replace("localhost", host);

        HttpUtils.post(targetUrl, rp, new JsonHttpResponseHandler() {
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

    public static void createProduct(String host, ProductVO productVO) {
        Gson gson = new Gson();
        String jsonStringProduct = gson.toJson(productVO);

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonStringProduct);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestParams rp = new RequestParams();
        rp.add("product", jsonObject.toString());

        String targetUrl = IServer.PRODUCT_CREATE_URL.replace("localhost", host);

        HttpUtils.post(targetUrl, rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                Log.d("asd", "---------------- this is response : " + response);
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
}
