package com.oryx.context;

import java.util.UUID;

public class IServer {
    public static IUser currentUser;
    public static String host = "10.0.2.2";

    public static String serverName = "Oryx";
    public static String serverhost = "localhost";
    public static String serverport = "8680";

    public static String getBaseUrl() {
        return "http://" + serverhost + ":" + serverport + "/oryx-server/";
    }

    public static String BASE_URL = IServer.getBaseUrl();
    //authentification
    public static String LOGIN_URL = IServer.BASE_URL + "login";
    public static String LOGIN_CONNECT_URL = LOGIN_URL + "/connect";

    public static String LOGOUT_URL = IServer.BASE_URL + "logout";
    public static String SIGNIN_URL = IServer.BASE_URL + "signin";

    //products
    public static String PRODUCT_BASE_URL = IServer.BASE_URL + "protected/products";
    public static String PRODUCT_GET_URL = IServer.PRODUCT_BASE_URL + "/fetch";
    public static String PRODUCT_CREATE_URL = IServer.PRODUCT_BASE_URL + "/create";
    public static String PRODUCT_UPDATE_URL = IServer.PRODUCT_BASE_URL + "/update";
    public static String PRODUCT_DELETE_URL = IServer.PRODUCT_BASE_URL + "/delete";
}
