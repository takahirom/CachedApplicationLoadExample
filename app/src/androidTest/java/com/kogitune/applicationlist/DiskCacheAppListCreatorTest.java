package com.kogitune.applicationlist;

import android.test.AndroidTestCase;
import android.util.TimingLogger;

import java.util.ArrayList;
import java.util.List;

import hugo.weaving.DebugLog;

/**
 * Created by takam on 2014/09/29.
 */
public class DiskCacheAppListCreatorTest extends AndroidTestCase{
    public void testSpeed() {
        TimingLogger logger = new TimingLogger("TAG_TEST", "testTimingLogger");
        DiskCacheAppList diskCacheAppList = new DiskCacheAppList(getContext());

        List<App> appList = new ArrayList<App>();
        for (int i = 0;i<10000;i++){
            App foobarApp = new App("foo.bar.foo.foobar", "foobar","foo.bar");
            appList.add(foobarApp);
        }
        logger.addSplit("start save");
        diskCacheAppList.save(appList);
        logger.addSplit("start read");
        diskCacheAppList.getAppList();
        logger.addSplit("read end");

        logger.dumpToLog();
    }

}
