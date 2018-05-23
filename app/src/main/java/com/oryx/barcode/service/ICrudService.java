package com.oryx.barcode.service;

import android.widget.TextView;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.oryx.barcode.context.StaticServer;
import com.oryx.barcode.gson.GsonResponse;
import com.oryx.barcode.helper.HttpHelper;
import com.oryx.barcode.http.JsonHttpResponseHandlerExt;
import com.oryx.barcode.model.EntityVO;

public interface ICrudService<E extends EntityVO>{
    public GsonResponse<E> create(String host, String format, E E) ;
    public GsonResponse<E> update(String host, String format, E E) ;
    public GsonResponse<E> delete(String host, String format, String xcode);
}
