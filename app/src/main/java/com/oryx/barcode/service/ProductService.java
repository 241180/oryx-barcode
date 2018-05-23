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
import com.oryx.barcode.http.JsonHttpResponseHandlerExt;
import com.oryx.barcode.model.ProductVO;
import com.oryx.barcode.model.UserVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ProductService{
    public static ProductVO findByCodeAndFormat(String host, String code, String format, final TextView descriptionField) {
        RequestParams rp = new RequestParams();
        rp.add("xcode", code);
        rp.add("xformat", format);

        String targetUrl = StaticServer.PRODUCT_GET_URL.replace("localhost", host);

        JsonHttpResponseHandlerExt<ProductVO> jsonHttpResponseHandlerExt = new JsonHttpResponseHandlerExt(ProductVO.class);
        HttpHelper.post(targetUrl, rp, jsonHttpResponseHandlerExt);

        return jsonHttpResponseHandlerExt.getObject();
    }

    public static ProductVO create(String host, String format, ProductVO productVO) {
        Gson gson = new Gson();
        String jsonStringProduct = gson.toJson(productVO);

        RequestParams rp = new RequestParams();
        rp.add("xformat", format);
        rp.add("product", jsonStringProduct);

        String targetUrl = StaticServer.PRODUCT_CREATE_URL.replace("localhost", host);
        JsonHttpResponseHandlerExt<ProductVO> jsonHttpResponseHandlerExt = new JsonHttpResponseHandlerExt(ProductVO.class);
        HttpHelper.post(targetUrl, rp, jsonHttpResponseHandlerExt);

        return jsonHttpResponseHandlerExt.getObject();
    }

    public static Boolean delete(String host, String format, String xcode) {
        RequestParams rp = new RequestParams();
        rp.add("xformat", format);
        rp.add("xcode", xcode);

        String targetUrl = StaticServer.PRODUCT_DELETE_URL.replace("localhost", host);

        JsonHttpResponseHandlerExt<Boolean> jsonHttpResponseHandlerExt = new JsonHttpResponseHandlerExt(Boolean.class);
        HttpHelper.post(targetUrl, rp, jsonHttpResponseHandlerExt);

        return jsonHttpResponseHandlerExt.getObject();
    }
}
