package com.kogitune.applicationlist;

import android.content.Context;
import android.util.LruCache;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by takam on 2014/09/30.
 */
public class DiskCacheAppList {
    private final Context mContext;

    public DiskCacheAppList(Context context) {
        mContext = context;
    }

    public List<App> getAppList(AppIconCache iconCache) {
        List<App> appList = new ArrayList<App>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(mContext.openFileInput("app_list")));
            String lineBuffer;
            while ((lineBuffer = br.readLine()) != null) {
                String[] infos = lineBuffer.split(",");
                App app = new App(new AppIcon(iconCache, infos[0]), infos[0], infos[1], infos[2]);
                appList.add(app);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return appList;
    }

    public void save(List<App> list) {
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(mContext.openFileOutput("app_list", Context.MODE_PRIVATE)));
            for (App app : list) {
                bw.write(app.packageName);
                bw.write(",");
                bw.write(app.name.replace(",", ""));
                bw.write(",");
                bw.write(app.className);
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
