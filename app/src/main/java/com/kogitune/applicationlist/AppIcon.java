package com.kogitune.applicationlist;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import hugo.weaving.DebugLog;

/**
 * Created by takam on 2014/10/01.
 */
public class AppIcon {
    private final AppIconCache appIconCache;
    private final String packageName;

    AppIcon(AppIconCache cache, String packageName) {
        appIconCache = cache;
        this.packageName = packageName;
    }

    Drawable get(Context context) {
        Drawable drawable = appIconCache.get(packageName);
        if (drawable == null) {
            try {
                drawable = context.getPackageManager().getApplicationIcon(packageName);
                appIconCache.putMemory(packageName, drawable);
                appIconCache.putDisk(packageName, drawable);
                return drawable;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        return drawable;
    }
}
