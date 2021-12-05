package com.elvis.shopping.list.utils;

import android.util.Log;

public class LOG {

    private static LOG instance;
    private boolean isDebugMode;

    private LOG(){}

    public static LOG getInstance() {
        if(instance == null) {
            instance = new LOG();
        }
        return instance;
    }

    public static void i(String TAG, String message){
        LOG.getInstance().info(TAG, message);
    }

    private void info(String TAG, String message){
        if(isDebugMode) {
            Log.i(TAG, "[ ==========> " + message + " <========== ]");
        }
    }

    public boolean isDebugMode() {
        return isDebugMode;
    }

    public void setDebugMode(boolean debugMode) {
        isDebugMode = debugMode;
    }
}
