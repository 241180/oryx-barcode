package com.oryx.barcode.gson;

import com.oryx.barcode.model.EntityVO;

/**
 * Created by smbarki on 23/05/2018.
 */
public class GsonResponse<E extends EntityVO> {
    private int errorCode = 0;
    private String message;
    private E object;

    public GsonResponse(int errorCode, String message, E object) {
        this.errorCode = errorCode;
        this.message = message;
        this.object = object;
    }

    public GsonResponse(String message, E object) {
        this.errorCode = 0;
        this.message = message;
        this.object = object;
    }

    public GsonResponse(E object) {
        this.errorCode = 0;
        this.message = "Finished with sucess";
        this.object = object;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public E getObject() {
        return object;
    }

    public void setObject(E object) {
        this.object = object;
    }
}
