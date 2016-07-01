package com.example.swchalu.gank.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.swchalu.gank.App;

/**
 * Created by swchalu on 2016/7/1.
 */
public class NetworkUtils {

    /**
     * 判断是否有网络连接
     *
     * @return
     */
    public static boolean isNetworkConnected() {

        ConnectivityManager mConnectivityManager = (ConnectivityManager) App.getInstace()
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable();
        }


        return false;
    }

    /**
     * 判断Wi-Fi网络是否有用
     *
     * @param context
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo mWiFiNetworkInfo = mConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            if (mWiFiNetworkInfo != null) {
                return mWiFiNetworkInfo.isAvailable();
            }
        }
        return false;
    }
}
