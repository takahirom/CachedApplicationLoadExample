package com.kogitune.applicationlist;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.util.LruCache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by takam on 2014/09/30.
 */
public class AppListCreator {
    private final Context context;
    private final AppIconCache memoryIconCache;

    public AppListCreator(Context context, AppIconCache iconCache) {
        this.context = context;
        memoryIconCache = iconCache;
    }

    public List<App> getAppList() {
        List<Map> myData = new ArrayList<Map>();
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> list = pm.queryIntentActivities(mainIntent, 0);
        List<App> appList = new ArrayList<App>();
        for (ResolveInfo resolveInfo : list) {
            appList.add(new App(new AppIcon(memoryIconCache, resolveInfo.activityInfo.packageName)
                    , resolveInfo.activityInfo.packageName, resolveInfo.loadLabel(pm).toString(), resolveInfo.activityInfo.name));
        }
        return appList;
    }

}
