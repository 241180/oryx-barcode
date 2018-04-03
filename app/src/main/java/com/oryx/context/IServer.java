package com.oryx.context;

import java.util.UUID;

public class IServer {
    public static String serverName = "Oryx";
    public static String serverhost = "localhost";
    public static String serverport = "8680";

    public static String getBaseUrl() {
        return "http://" + serverhost + ":" + serverport + "/oryx-server/";
    }
}
