package com.kogitune.applicationlist;

import android.app.ActivityManager;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.LruCache;
import android.widget.Toast;

import java.util.List;

/**
 * Created by takam on 2014/09/29.
 */
public class AppListLoader extends AsyncTaskLoader<List<App>> {
    private final Context context;
    private final AppIconCache appIconCache;
    DiskCacheAppList diskCacheAppList;

    AppListLoader(Context context) {
        super(context);
        this.context = context;
        final int memClass = ((ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE)).getMemoryClass();

        final int cacheSize = 1024 * 1024 * memClass / 6;
        LruCache<String, Drawable> memoryIconCache = new LruCache<String, Drawable>(cacheSize);

        appIconCache = new AppIconCache(this.context.getExternalCacheDir(), memoryIconCache);
    }

    @Override
    public List<App> loadInBackground() {
        if (diskCacheAppList == null) {
            diskCacheAppList = new DiskCacheAppList(context);
        }
        List<App> appList = diskCacheAppList.getAppList(appIconCache);
        if (appList != null && appList.size() > 0) {
            return appList;
        }
        appList = new AppListCreator(context, appIconCache).getAppList();
        diskCacheAppList.save(appList);

        return appList;
    }

}
