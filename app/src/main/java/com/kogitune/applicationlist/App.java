package com.kogitune.applicationlist;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.LruCache;

import java.lang.ref.WeakReference;

/**
 * Created by takam on 2014/09/29.
 */
public class App {
    String packageName;
    String name;
    String className;
    AppIcon icon;

    public App(AppIcon icon, String packageName, String name, String className) {
        this.icon = icon;
        this.packageName = packageName;
        this.name = name;
        this.className = className;
    }

    public Drawable getIcon(Context context) {
        return icon.get(context);
    }
}
