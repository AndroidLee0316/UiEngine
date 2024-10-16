package com.pasc.business.workspace.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

public class PermissionUtils {

    public static void gotoApplicationDetails(Context context) {
        Intent appIntent;

        //魅族(目前发现魅族 M6 Note（Android 7.1.2，Flyme 6.1.4.7A）出现在应用信息页打开权限不管用的情况，必须在管家中打开方可生效，所以魅族手机暂定跳转手机管家)
        appIntent = context.getPackageManager()
                .getLaunchIntentForPackage("com.meizu.safe");
        if (appIntent != null) {
            context.startActivity(appIntent);
            return;
        }

        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            intent.setAction(Intent.ACTION_VIEW);
            intent.setClassName("com.android.settings",
                    "com.android.settings.InstalledAppDetails");
            intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(intent);
    }
}
