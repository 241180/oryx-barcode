package com.oryx.barcode.context;

import com.oryx.barcode.activity.map.IMapLocation;
import com.oryx.barcode.model.UserVO;

public class StaticServer {
    public static boolean logOut = false;
    public static String token;

    public static IMapLocation location;
    public static UserVO currentUser;
    public static String host = "10.0.2.2";

    public static String serverName = "Oryx";
    public static String serverhost = "localhost";
    public static String serverport = "8680";
    public static String BASE_URL = StaticServer.getBaseUrl();
    //authentification
    public static String AUTH_URL = StaticServer.BASE_URL + "protected/authentication";
    public static String AUTH_CONNECT_URL = AUTH_URL + "/connect";
    public static String AUTH_IS_CONNECTED_URL = AUTH_URL + "/isconnected";
    public static String AUTH_DISCONNECT_URL = AUTH_URL + "/disconnect";
    public static String AUTH_SIGNUP_URL = AUTH_URL + "/create";
    public static String SEND_REGISTRATION_T0_SERVER = AUTH_URL + "/registerToken";

    //products
    public static String PRODUCT_BASE_URL = StaticServer.BASE_URL + "protected/products";
    public static String PRODUCT_GET_URL = StaticServer.PRODUCT_BASE_URL + "/fetch";
    public static String PRODUCT_CREATE_URL = StaticServer.PRODUCT_BASE_URL + "/create";
    public static String PRODUCT_UPDATE_URL = StaticServer.PRODUCT_BASE_URL + "/update";
    public static String PRODUCT_DELETE_URL = StaticServer.PRODUCT_BASE_URL + "/delete";

    public static String getBaseUrl() {
        return "http://" + serverhost + ":" + serverport + "/oryx-server/";
    }
}
