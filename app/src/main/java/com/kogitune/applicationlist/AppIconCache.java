package com.kogitune.applicationlist;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.LruCache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by takam on 2014/10/04.
 */
public class AppIconCache {
    private final File cacheDir;
    private final LruCache<String, Drawable> memoryIconCache;

    public AppIconCache(File cacheDir, LruCache memoryIconCache) {
        this.cacheDir = cacheDir;
        this.memoryIconCache = memoryIconCache;
    }

    public String getImageFilePath(String packageName) {
        return cacheDir.getAbsolutePath() + File.pathSeparator + packageName + ".png";
    }

    public Drawable get(String packageName) {
        Drawable cachedImage = memoryIconCache.get(packageName);
        if (cachedImage != null) {
            return cachedImage;
        }
        cachedImage = Drawable.createFromPath(getImageFilePath(packageName));
        if (cachedImage != null) {
            putMemory(packageName, cachedImage);
        }
        return cachedImage;
    }


    public void putMemory(String packageName, Drawable drawable) {
        memoryIconCache.put(packageName, drawable);
    }

    public void putDisk(String packageName, Drawable drawable) {
        FileOutputStream out = null;
        try {
            if (new File(getImageFilePath(packageName)).exists() == false) {
                out = new FileOutputStream(getImageFilePath(packageName));
                ((BitmapDrawable) drawable).getBitmap().compress(Bitmap.CompressFormat.PNG, 90, out);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
