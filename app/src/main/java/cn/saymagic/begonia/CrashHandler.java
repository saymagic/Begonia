/**
 * @(#)CrashHandler.java, 2013-4-9.
 *
 * Copyright 2013 Netease, Inc. All rights reserved.
 * NETEASE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package cn.saymagic.begonia;

import android.util.Log;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;


public final class CrashHandler implements UncaughtExceptionHandler {

    private static final String TAG = "CrashHandler";

    private Thread.UncaughtExceptionHandler defaultHandler;

    private static CrashHandler instance = new CrashHandler();

    private Map<String, String> infos = new HashMap<String, String>();

    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private CrashHandler() {}

    public static CrashHandler getInstance() {
        if (instance == null) {
            instance = new CrashHandler();
        }
        return instance;
    }

    public void init() {
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        handleException(ex);
        defaultHandler.uncaughtException(thread, ex);
    }

    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        writeCrashInfoToFile(ex);
        return true;
    }

    private void writeCrashInfoToFile(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry: infos.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\n");
        }

        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);
        Log.e("BegoniaCrash", sb.toString());
    }
}
