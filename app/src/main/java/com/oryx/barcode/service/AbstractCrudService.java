package com.oryx.barcode.service;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.oryx.barcode.context.StaticServer;
import com.oryx.barcode.gson.GsonResponse;
import com.oryx.barcode.helper.HttpHelper;
import com.oryx.barcode.http.JsonHttpResponseHandlerExt;
import com.oryx.barcode.model.EntityVO;

public abstract class AbstractCrudService<E extends EntityVO> implements ICrudService<E> {
    public GsonResponse<E> create(String host, String format, E E) {
        Gson gson = new Gson();
        String jsonStringProduct = gson.toJson(E);

        RequestParams rp = new RequestParams();
        rp.add("xformat", format);
        rp.add("product", jsonStringProduct);

        String targetUrl = StaticServer.PRODUCT_CREATE_URL.replace("localhost", host);
        JsonHttpResponseHandlerExt<GsonResponse<E>> jsonHttpResponseHandlerExt = new JsonHttpResponseHandlerExt(GsonResponse.class);
        HttpHelper.post(targetUrl, rp, jsonHttpResponseHandlerExt);

        return jsonHttpResponseHandlerExt.getObject();
    }

    public GsonResponse<E> update(String host, String format, E E) {
        Gson gson = new Gson();
        String jsonStringProduct = gson.toJson(E);

        RequestParams rp = new RequestParams();
        rp.add("xformat", format);
        rp.add("product", jsonStringProduct);

        String targetUrl = StaticServer.PRODUCT_UPDATE_URL.replace("localhost", host);
        JsonHttpResponseHandlerExt<GsonResponse<E>> jsonHttpResponseHandlerExt = new JsonHttpResponseHandlerExt(GsonResponse.class);
        HttpHelper.post(targetUrl, rp, jsonHttpResponseHandlerExt);

        return jsonHttpResponseHandlerExt.getObject();
    }

    public GsonResponse<E> delete(String host, String format, String xcode) {
        RequestParams rp = new RequestParams();
        rp.add("xformat", format);
        rp.add("xcode", xcode);

        String targetUrl = StaticServer.PRODUCT_DELETE_URL.replace("localhost", host);

        JsonHttpResponseHandlerExt<GsonResponse<E>> jsonHttpResponseHandlerExt = new JsonHttpResponseHandlerExt(GsonResponse.class);
        HttpHelper.post(targetUrl, rp, jsonHttpResponseHandlerExt);

        return jsonHttpResponseHandlerExt.getObject();
    }
}
