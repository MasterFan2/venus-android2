package com.chinajsbn.venus.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

/**
 * Created by Fan on 2014-12-12.
 */
public class NetworkUtil {

    /**
     * 获取网络类型
     * @param context
     * @return
     */
    private static com.chinajsbn.venus.utils.entity.NetInfo.NetType getNetworkType(Context context) {

        com.chinajsbn.venus.utils.entity.NetInfo.NetType netType = com.chinajsbn.venus.utils.entity.NetInfo.NetType.T_UNKNOWN;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            String type = networkInfo.getTypeName();

            if (type.equalsIgnoreCase("WIFI")) {
                netType = com.chinajsbn.venus.utils.entity.NetInfo.NetType.T_WIFI;
            } else if (type.equalsIgnoreCase("MOBILE")) {

                if(networkInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_LTE){
                    netType = com.chinajsbn.venus.utils.entity.NetInfo.NetType.T_4G;
                }else {

                    String proxyHost = android.net.Proxy.getDefaultHost();

                    netType = TextUtils.isEmpty(proxyHost) ?
                            (isFastMobileNetwork(context) ? com.chinajsbn.venus.utils.entity.NetInfo.NetType.T_3G  : com.chinajsbn.venus.utils.entity.NetInfo.NetType.T_2G)
                            : com.chinajsbn.venus.utils.entity.NetInfo.NetType.T_WAP;
                }
            }
            //else {T_UNKNOW}
        } else {
            netType = com.chinajsbn.venus.utils.entity.NetInfo.NetType.T_NO_CONN;
        }

        return netType;
    }

    /**
     * 获取网络信息
     * @param context
     * @return
     */
    public static com.chinajsbn.venus.utils.entity.NetInfo getNetworkInfo(Context context){

        //
        com.chinajsbn.venus.utils.entity.NetInfo info = new com.chinajsbn.venus.utils.entity.NetInfo();
        com.chinajsbn.venus.utils.entity.NetInfo.NetType netWorkType = getNetworkType(context);
        info.setType(netWorkType);

        //
        return info;
    }

    /**
     * 是否是快速网络 3G or 2G
     * @param context context
     * @return 3G or 2G
     */
    private static boolean isFastMobileNetwork(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context .getSystemService(Context.TELEPHONY_SERVICE);
        switch (telephonyManager.getNetworkType()) {
            case TelephonyManager.NETWORK_TYPE_1xRTT:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_CDMA:
                return false; // ~ 14-64 kbps
            case TelephonyManager.NETWORK_TYPE_EDGE:
                return false; // ~ 50-100 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
                return true; // ~ 400-1000 kbps
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
                return true; // ~ 600-1400 kbps
            case TelephonyManager.NETWORK_TYPE_GPRS:
                return false; // ~ 100 kbps
            case TelephonyManager.NETWORK_TYPE_HSDPA:
                return true; // ~ 2-14 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPA:
                return true; // ~ 700-1700 kbps
            case TelephonyManager.NETWORK_TYPE_HSUPA:
                return true; // ~ 1-23 Mbps
            case TelephonyManager.NETWORK_TYPE_UMTS:
                return true; // ~ 400-7000 kbps
            case TelephonyManager.NETWORK_TYPE_EHRPD:
                return true; // ~ 1-2 Mbps
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
                return true; // ~ 5 Mbps
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return true; // ~ 10-20 Mbps
            case TelephonyManager.NETWORK_TYPE_IDEN:
                return false; // ~25 kbps
            case TelephonyManager.NETWORK_TYPE_LTE:
                return true; // ~ 10+ Mbps
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return false;
            default:
                return false;
        }
    }

    /**
     * 获取网络连接类型
     *
     * @param context
     * @return
     */
    private static String getConnectType(Context context) {

        ConnectivityManager connManager = (ConnectivityManager) context .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = connManager.getActiveNetworkInfo();
        //
        if (mNetworkInfo != null) return mNetworkInfo.getTypeName();
        else                      return null;
    }

    /**
     * 是否有网络连接
     *
     * @param context Context
     * @return <p>
     *         true:有网络连接
     *         false:无网络连接
     *         </p>
     */
    public static boolean hasConnection(Context context) {
        return getConnectType(context) != null;
    }
}
