package com.pasc.business.workspace.helper;

import android.app.ActivityManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.pasc.business.workspace.R;
import com.pasc.lib.base.AppProxy;
import com.pasc.lib.base.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 网络变化管理器，负责管理网络变化的通知，如果需要知道当前手机的网络连接类型，调用getNetWorkType函数
 * 如果需要时刻监听网络的断开与连接，请调用：addOnNetworkChangeListener
 * <p>Created by chendaixi947 on 2018-07-13.</p>
 */
public class NetworkManager {
    private static final NetworkManager gInstance = new NetworkManager();

    private NetworkManager() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        AppProxy.getInstance().getApplication().registerReceiver(broadcastReceiver, intentFilter);
    }

    public static NetworkManager getInstance() {
        return gInstance;
    }

    /**
     * 上一次的网络连接类型
     */
    private static int lastConnectType = -2;
    private List<OnNetworkChangeListener> onNetworkChangeListeners = new ArrayList<>();
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            int currentType = -1;
            if (networkInfo != null && networkInfo.isConnected()) {
                currentType = networkInfo.getType();
            } else {
                currentType = -1;
                if (isAppForeground(AppProxy.getInstance().getApplication())) {
                    ToastUtils.toastMsg(R.string.workspace_network_unavailable);
                }
            }
            if (lastConnectType == -2) {
                lastConnectType = currentType;
            } else {
                if (lastConnectType != currentType) {
                    lastConnectType = currentType;
                    for (OnNetworkChangeListener onNetworkChangeListener : onNetworkChangeListeners) {
                        onNetworkChangeListener.onNetworkChange(currentType);
                    }
                }
            }
        }
    };


    private static boolean isAppForeground(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList = activityManager.getRunningAppProcesses();
        if (runningAppProcessInfoList == null) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo processInfo : runningAppProcessInfoList) {
            if (processInfo.processName.equals(context.getPackageName())
                    && (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否有网络
     */
    public static boolean hasNet() {
        return getNetWorkType() != -1 && getNetWorkType() != -2;
    }

    /**
     * 获取当前的网络连接类型 {@link NetworkInfo#getType()}
     * 注意：手机网络并不是只有wifi和移动流量。
     *
     * @return 网络类型包括：wifi，移动数据，蓝牙共享，网线，VPN等等，
     */
    public static int getNetWorkType() {
        return lastConnectType;
    }

    /**
     * 添加网络变化监听器
     */
    public void addOnNetworkChangeListener(OnNetworkChangeListener onNetworkChangeListener) {
        onNetworkChangeListeners.add(onNetworkChangeListener);
    }

    /**
     * 移除网络变化监听器
     */
    public void removeOnNetworkChangeListener(OnNetworkChangeListener onNetworkChangeListener) {
        onNetworkChangeListeners.remove(onNetworkChangeListener);
    }

    public interface OnNetworkChangeListener {
        /**
         * 网络变化的回调
         */
        void onNetworkChange(int networkType);
    }
}
